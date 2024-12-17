package logic.effects.debuffs;

import javafx.application.Platform;
import javafx.scene.image.Image;
import logic.characters.BaseCharacter;
import logic.effects.TriggerEvent;
import logic.effects.TriggerType;
import utils.Assets;

public class Scare extends Debuff{
    public Scare(BaseCharacter effectReceiver, int duration) {
        super(
                "Scare",
                effectReceiver,
                duration,
                TriggerType.NOT_TRIGGER,
                TriggerEvent.NONE,
                new Image(Assets.getAsset("/assets/effects/scare.png"))
        );
    }

    @Override
    public void clearEffect() {
        effectReceiver.setDamageMultiplier(effectReceiver.getDamageMultiplier() / 0.75);
        effectReceiver.getStatusEffect().remove(this);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                effectReceiver.getCard().getController().setEffect(effectReceiver.getStatusEffect());
            }
        });
    }

    @Override
    public void activate() {
        effectReceiver.setDamageMultiplier(effectReceiver.getDamageMultiplier() * 0.75);
    }

    @Override
    public String getDescription() {
        return "This debuff holder will deal 25% less damage.";
    }
}
