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
import logic.effects.buffs.PotionOfStrength;
import logic.effects.buffs.PowerBoostingMeal;
import logic.effects.debuffs.Paralyzed;
import logic.interfaces.Buffable;
import logic.interfaces.Debuffable;
import utils.Assets;

import java.util.ArrayList;
import java.util.Random;

public class EstaaUltimate extends UltimateAction implements Buffable, Debuffable {

    public EstaaUltimate(BaseCharacter character) {
        super(
                character,
                "Mysterious Creation",
                0,
                new Image(Assets.getAsset("/assets/actions/estaa/estaaUltimate.png")),
                Target.Friend
            );
        type = ActionType.Magical;
        this.spiritCost = 30;
    }

    @Override
    public void activate(BaseCharacter targetCharacter) {
        for (BaseCharacter character : GameController.getInstance().getPlayerTeam().getMembers()) {
            giveBuff(character);
        }

        Random random = new Random();
        BaseCharacter randomTarget = GameController.getInstance().getEnemyTeam().getMembers().get(
                random.nextInt(GameController.getInstance().getEnemyTeam().getMembers().size())
        );
        giveDebuff(randomTarget);
    }

    @Override
    public String getDescription() {
        return "Give Buff 'Power Boosting Meal' to everyone in our team for 2 turns. " +
                "Then, random 1 enemy and give that enemy a debuff 'Paralyzed' for 1 turn.";
    }

    @Override
    public Effect giveBuff(BaseCharacter character) {
        boolean hasBuff = false;
        PowerBoostingMeal powerBoostingMeal = new PowerBoostingMeal(character, 2);

        for(int i=0; i<character.getStatusEffect().size(); i++) {
            Effect effect = character.getStatusEffect().get(i);
            if(effect instanceof PowerBoostingMeal) {
                powerBoostingMeal = (PowerBoostingMeal) effect;
                hasBuff = true;
            }
        }
        if(hasBuff) {
            powerBoostingMeal.addDuration(2);
        } else {
            character.getStatusEffect().add(powerBoostingMeal);
            powerBoostingMeal.activate();
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                character.getCard().getController().setEffect(character.getStatusEffect());
            }
        });
        return powerBoostingMeal;
    }

    @Override
    public Effect giveDebuff(BaseCharacter character) {
        boolean hasParalyzed = false;
        Paralyzed paralyzed = new Paralyzed(character, 1);
        for(int i=0; i<character.getStatusEffect().size(); i++) {
            Effect effect = character.getStatusEffect().get(i);
            if(effect instanceof Paralyzed) {
                paralyzed = (Paralyzed) effect;
                hasParalyzed = true;
            }
        }
        if(hasParalyzed) {
            paralyzed.addDuration(1);
        } else {
            character.getStatusEffect().add(paralyzed);
            paralyzed.activate();
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                character.getCard().getController().setEffect(character.getStatusEffect()); // before trigger event
                GameController.getInstance().runEffectByEvent(TriggerEvent.RECEIVE_DEBUFF, character);
            }
        });
        return paralyzed;
    }
}
