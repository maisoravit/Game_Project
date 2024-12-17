package logic.actions.ultimates;

import javafx.application.Platform;
import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.actions.Target;
import logic.actions.UltimateAction;
import logic.characters.BaseCharacter;
import logic.effects.Effect;
import logic.effects.TriggerEvent;
import logic.effects.debuffs.BrokenArmor;
import logic.effects.debuffs.Burning;
import logic.interfaces.Debuffable;
import logic.players.Player;
import utils.Assets;

import java.util.ArrayList;

public class MewtenUltimate extends UltimateAction implements Debuffable {
    public MewtenUltimate(BaseCharacter character) {
        super(
                character,
                "Armor Shattering Slash!",
                0,
                new Image(Assets.getAsset("/assets/actions/mewten/mewtenUltimate.png")),
                Target.Enemy
        );
        type = ActionType.Physical;
        this.spiritCost = 25;
    }

    @Override
    public void activate(BaseCharacter targetCharacter) {
        if(GameController.getInstance().getEnemyTeam().getFront().contains(targetCharacter)) {
            GameController.getInstance().getEnemyTeam().getFront().forEach(character -> {
                character.takeDamage(getUser(), getUser().getStats().getAttack() + 7, type);
            });
        } else if (GameController.getInstance().getEnemyTeam().getMid().contains(targetCharacter)) {
            GameController.getInstance().getEnemyTeam().getMid().forEach(character -> {
                character.takeDamage(getUser(),getUser().getStats().getAttack() + 7, type);
            });
        } else if (GameController.getInstance().getEnemyTeam().getRear().contains(targetCharacter)) {
            GameController.getInstance().getEnemyTeam().getRear().forEach(character -> {
                character.takeDamage(getUser(),getUser().getStats().getAttack() + 7, type);
            });
        }

        ArrayList<BaseCharacter> enemyTeam = GameController.getInstance().getEnemyTeam().getMembers();
        for(int i=0; i<enemyTeam.size(); i++) {
            giveDebuff(enemyTeam.get(i));
        }
    }

    @Override
    public String getDescription() {
        return "Attack all enemy on a target line. Damage base on own ATK + 7. Then give every enemy a debuff 'Broken Armor' for 2 turns.";
    }

    @Override
    public Effect giveDebuff(BaseCharacter character) {
        boolean hasBrokenArmor = false;
        BrokenArmor brokenArmor = new BrokenArmor(character, 2);
        for(int i=0; i<character.getStatusEffect().size(); i++) {
            Effect effect = character.getStatusEffect().get(i);
            if(effect instanceof BrokenArmor) {
                brokenArmor = (BrokenArmor) effect;
                hasBrokenArmor = true;
            }
        }
        if(hasBrokenArmor) {
            brokenArmor.addDuration(2);
        } else {
            character.getStatusEffect().add(brokenArmor);
            brokenArmor.activate();
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                character.getCard().getController().setEffect(character.getStatusEffect()); // before trigger event
                GameController.getInstance().runEffectByEvent(TriggerEvent.RECEIVE_DEBUFF, character);
            }
        });
        return brokenArmor;
    }
}
