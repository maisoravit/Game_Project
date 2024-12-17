package logic.actions.normals;

import javafx.application.Platform;
import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.actions.NormalAction;
import logic.actions.Target;
import logic.characters.BaseCharacter;
import logic.characters.Stats;
import logic.effects.Effect;
import logic.effects.TriggerEvent;
import logic.effects.debuffs.Burning;
import logic.effects.debuffs.Debuff;
import logic.interfaces.Debuffable;
import logic.interfaces.Healable;
import logic.players.Player;
import manager.SoundManager;
import utils.Assets;

public class KonaNormal extends NormalAction implements Debuffable, Healable {
    public KonaNormal(BaseCharacter character) {
        super(
                character,
                "Blaze You!",
                3,
                new Image(Assets.getAsset("/assets/actions/kona/konaNormal.png")),
                Target.Enemy
        );
        type = ActionType.Magical;
    }

    @Override
    public void activate(BaseCharacter targetCharacter) {
        targetCharacter.takeDamage(getUser(), getUser().getStats().getMagic(), type);

        if(GameController.getInstance().getEnemyTeam().getFront().contains(targetCharacter)) {
            GameController.getInstance().getEnemyTeam().getFront().forEach(character -> {
                giveDebuff(character);
            });
        } else if (GameController.getInstance().getEnemyTeam().getMid().contains(targetCharacter)) {
            GameController.getInstance().getEnemyTeam().getMid().forEach(character -> {
                giveDebuff(character);
            });
        } else if (GameController.getInstance().getEnemyTeam().getRear().contains(targetCharacter)) {
            GameController.getInstance().getEnemyTeam().getRear().forEach(character -> {
                giveDebuff(character);
            });
        }

        heal(getUser(), 6);
    }

    @Override
    public void activateEffect() {
        SoundManager.getInstance().playSFX("/sounds/kona/skill.mp3", 1);
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

    @Override
    public void heal(BaseCharacter character, int amount) {
        Stats newStats = character.getStats();
        newStats.setHealth(newStats.getHealth() + amount);
        character.setStats(newStats);
        character.updateCharacterHp();
    }

    @Override
    public String getDescription() {
        return "Attack 1 enemy. Damage base on own MAT. " +
                "And give debuff ‘Burning’ to all enemy in the same battle line for 2 turns. " +
                "Then Kona HP increase by 6.";
    }
}
