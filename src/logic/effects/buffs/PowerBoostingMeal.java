package logic.effects.buffs;

import javafx.application.Platform;
import javafx.scene.image.Image;
import logic.characters.BaseCharacter;
import logic.characters.Stats;
import logic.effects.TriggerEvent;
import logic.effects.TriggerType;
import utils.Assets;

public class PowerBoostingMeal extends Buff{
    public PowerBoostingMeal(BaseCharacter effectReceiver, int duration) {
        super(
                "Power Boosting Meal",
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
        stats.setAttack(stats.getAttack() - 15);
        stats.setMagic(stats.getMagic() - 15);
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
        newStats.setAttack(newStats.getAttack() + 15);
        newStats.setMagic(newStats.getMagic() + 15);
        effectReceiver.setStats(newStats);
    }

    @Override
    public String getDescription() {
        return "Increase a buff holder's ATK and MAT by 15 each.";
    }
}
