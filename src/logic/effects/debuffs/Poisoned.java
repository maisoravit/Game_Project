package logic.effects.debuffs;

import javafx.application.Platform;
import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.characters.BaseCharacter;
import logic.characters.isekai.Estaa;
import logic.characters.isekai.Kona;
import logic.effects.Effect;
import logic.effects.TriggerEvent;
import logic.effects.TriggerType;
import utils.Assets;

public class Poisoned extends Debuff {
    private int poisonLevel;

    public Poisoned(BaseCharacter effectReceiver, int duration) {
        super(
                "Poisoned",
                effectReceiver,
                duration,
                TriggerType.TRIGGER,
                TriggerEvent.NEW_TURN,
                new Image(Assets.getAsset("/assets/effects/poisoned.png")) // need correct image
        );
        this.poisonLevel = 1;
    }

    // Method to increment poison level
    public void increasePoisonLevel() {
        if (poisonLevel < 3) {
            poisonLevel++;
        } else {
            effectReceiver.takeDamageDirectly(new Estaa(), 10, ActionType.Magical);
            boolean hasParalyzed = false;
            Paralyzed paralyzed = new Paralyzed(effectReceiver, 1);
            for(int i=0; i<effectReceiver.getStatusEffect().size(); i++) {
                Effect effect = effectReceiver.getStatusEffect().get(i);
                if(effect instanceof Paralyzed) {
                    paralyzed = (Paralyzed) effect;
                    hasParalyzed = true;
                }
            }
            if(hasParalyzed) {
                paralyzed.addDuration(1);
            } else {
                effectReceiver.getStatusEffect().add(paralyzed);
                paralyzed.activate();
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    effectReceiver.getCard().getController().setEffect(effectReceiver.getStatusEffect()); // before trigger event
                    GameController.getInstance().runEffectByEvent(TriggerEvent.RECEIVE_DEBUFF, effectReceiver);
                }
            });
            clearEffect();
        }
    }

    public int getPoisonLevel() {
        return poisonLevel;
    }

    public void setPoisonLevel(int poisonLevel) {
        this.poisonLevel = poisonLevel;
    }

    @Override
    public void activate() {
        if(poisonLevel == 1) {
            effectReceiver.takeDamageDirectly(new Estaa(), 3, ActionType.Magical);
        } else if (poisonLevel == 2) {
            effectReceiver.takeDamageDirectly(new Estaa(), 4, ActionType.Magical);
        } else if (poisonLevel == 3) {
            effectReceiver.takeDamageDirectly(new Estaa(), 5, ActionType.Magical);
        }
    }

    @Override
    public String getDescription() {
        return "Poisoned has 3 level. Each level deal direct damage each turn by 3/4/5 in order depend on level." +
                "Each time this debuff is added duration. Then increase Poisoned level. If Poisoned level is over 3." +
                "Then deal direct 10 damage to a debuff holder and give a debuff 'Paralyzed' for 1 turn. Finally clear Poisoned debuff.";
    }
}
