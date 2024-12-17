package logic.actions.ultimates;

import javafx.application.Platform;
import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.actions.Target;
import logic.actions.UltimateAction;
import logic.characters.BaseCharacter;
import logic.characters.Stats;
import logic.effects.Effect;
import logic.effects.TriggerEvent;
import logic.effects.buffs.Purified;
import logic.effects.debuffs.Burning;
import logic.effects.debuffs.Debuff;
import logic.interfaces.Debuffable;
import logic.players.Player;
import manager.SoundManager;
import utils.Assets;

import java.util.ArrayList;

public class KonaUltimate extends UltimateAction implements Debuffable {
    public KonaUltimate(BaseCharacter character) {
        super(
                character,
                "Dragon God Descendant's Flame of Calamity",
                0,
                new Image(Assets.getAsset("/assets/actions/kona/konaUltimate.png")),
                Target.AllEnemy
        );
        type = ActionType.Magical;
        this.spiritCost = 30;
    }

    @Override
    public void activate(BaseCharacter targetCharacter) {
        int reduceHp = (int) (0.2 * getUser().getStats().getHealth());
        Stats newStats = getUser().getStats();
        newStats.setHealth(newStats.getHealth() - reduceHp);
        getUser().setStats(newStats);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getUser().updateCharacterHp();
                getUser().updateCheckAlive();
            }
        });

        int additionalDamage = 0;
        boolean hasDebuff = false;
        for(int i=0; i<getUser().getStatusEffect().size(); i++) {
            if(getUser().getStatusEffect().get(i) instanceof Debuff) {
                hasDebuff = true;
                break;
            }
        }
        if(!hasDebuff) {
            additionalDamage += 15;
        }

        boolean hasPurified = false;
        for(int i=0; i<getUser().getStatusEffect().size(); i++) {
            if(getUser().getStatusEffect().get(i) instanceof Purified) {
                hasPurified = true;
                break;
            }
        }
        if(hasPurified) {
            additionalDamage += 15;
        }

        ArrayList<BaseCharacter> enemyTeam = GameController.getInstance().getEnemyTeam().getMembers();
        for(int i=0; i<enemyTeam.size(); i++){
            giveDebuff(enemyTeam.get(i));
            enemyTeam.get(i).takeDamage(
                    getUser(),
                    this.getUser().getStats().getMagic()
                            + additionalDamage, type
            );
        }
    }

    @Override
    public void activateEffect() { SoundManager.getInstance().playSFX("/sounds/kona/ult.mp3", 0.75); }

    @Override
    public String getDescription() {
        return "Decrease own HP by 20% of current HP. " +
                "Then, give debuff ‘Burning’ to all enemy for 2 turns. " +
                "And attack all enemy. Damage base on own MAT. " +
                "If Kona doesn’t have any debuffs with her, " +
                "then each enemy receive more 15 damage. " +
                "If Kona has buff ‘Purified’ with her, " +
                "then each enemy receive more 15 damage.";
    }

    @Override
    public Effect giveDebuff(BaseCharacter character) {
        boolean hasBurning = false;
        Burning burning = new Burning(character, 2);
        for(int i=0; i<character.getStatusEffect().size(); i++) {
            Effect effect = character.getStatusEffect().get(i);
            if(effect instanceof Burning) {
                burning = (Burning) effect;
                hasBurning = true;
            }
        }
        if(hasBurning) {
            burning.addDuration(2);
        } else {
            character.getStatusEffect().add(burning);
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                character.getCard().getController().setEffect(character.getStatusEffect()); // before trigger event
                GameController.getInstance().runEffectByEvent(TriggerEvent.RECEIVE_DEBUFF, character);
            }
        });
        return burning;
    }
}
