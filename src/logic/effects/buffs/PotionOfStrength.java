package logic.effects.buffs;

import javafx.application.Platform;
import javafx.scene.image.Image;
import logic.characters.BaseCharacter;
import logic.characters.Stats;
import logic.effects.TriggerEvent;
import logic.effects.TriggerType;
import utils.Assets;

public class PotionOfStrength extends Buff {
    public PotionOfStrength(BaseCharacter effectReceiver, int duration) {
        super(
                "Potion of Strength",
                effectReceiver,
                duration,
                TriggerType.NOT_TRIGGER,
                TriggerEvent.NONE,
                new Image(Assets.getAsset("/assets/effects/atk-mat-boost.png"))
        );
    }

    @Override
    public void clearEffect() {
        Stats stats = effectReceiver.getStats();
        stats.setAttack(stats.getAttack() - 20);
        stats.setMagic(stats.getMagic() - 20);
        effectReceiver.setStats(stats);

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
        Stats newStats = effectReceiver.getStats();
        newStats.setAttack(newStats.getAttack() + 20);
        newStats.setMagic(newStats.getMagic() + 20);
        effectReceiver.setStats(newStats);
    }

    @Override
    public String getDescription() {
        return "Increase a buff holder's ATK and MAT by 20 each.";
    }
}
