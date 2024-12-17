package logic.effects.buffs;

import javafx.application.Platform;
import javafx.scene.image.Image;
import logic.characters.BaseCharacter;
import logic.characters.Stats;
import logic.effects.TriggerEvent;
import logic.effects.TriggerType;
import utils.Assets;

public class SoulBoost extends Buff{
    public SoulBoost(BaseCharacter effectReceiver, int duration) {
        super(
                "Soul Boost",
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
        stats.setAttack(stats.getAttack() - 10);
        stats.setMagic(stats.getMagic() - 10);
        stats.setDefense(stats.getDefense() - 5);
        stats.setMagicDef(stats.getMagicDef() - 5);
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
        newStats.setAttack(newStats.getAttack() + 10);
        newStats.setMagic(newStats.getMagic() + 10);
        newStats.setDefense(newStats.getDefense() + 5);
        newStats.setDefense(newStats.getMagicDef() + 5);
        effectReceiver.setStats(newStats);
    }

    @Override
    public String getDescription() {
        return "Increase a buff holder's ATK and MAT by 10 each. and increase the buff holder's DEF and MDF by 5 each.";
    }
}
