package logic.effects.debuffs;

import javafx.application.Platform;
import javafx.scene.image.Image;
import logic.characters.BaseCharacter;
import logic.effects.TriggerEvent;
import logic.effects.TriggerType;
import utils.Assets;

public class Paralyzed extends Debuff {

    public Paralyzed(BaseCharacter effectReceiver, int duration) {
        super(
                "Paralyzed",
                effectReceiver,
                duration,
                TriggerType.TRIGGER,
                TriggerEvent.NEW_TURN,
                new Image(Assets.getAsset("/assets/effects/paralyzed.png"))
        );
    }

    @Override
    public void clearEffect() {
        effectReceiver.setActionThisTurn(false);
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
        effectReceiver.setActionThisTurn(true);
    }

    @Override
    public String getDescription() {
        return "A debuff holder can not action during this debuff duration";
    }
}
