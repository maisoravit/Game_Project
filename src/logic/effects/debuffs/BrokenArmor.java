package logic.effects.debuffs;

import javafx.application.Platform;
import javafx.scene.image.Image;
import logic.characters.BaseCharacter;
import logic.characters.Stats;
import logic.effects.TriggerEvent;
import logic.effects.TriggerType;
import utils.Assets;

public class BrokenArmor extends Debuff{
    private int def = 0;
    private int mdf = 0;

    public BrokenArmor(BaseCharacter effectReceiver, int duration) {
        super(
                "Broken Armor",
                effectReceiver,
                duration,
                TriggerType.NOT_TRIGGER,
                TriggerEvent.NONE,
                new Image(Assets.getAsset("/assets/effects/broken-armor.png"))
        );
    }

    @Override
    public void clearEffect() {
        Stats newStats = effectReceiver.getStats();
        newStats.setDefense(newStats.getDefense() + def);
        newStats.setMagicDef(newStats.getMagicDef() + mdf);
        effectReceiver.setStats(newStats);

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
        def = effectReceiver.getStats().getDefense();
        mdf = effectReceiver.getStats().getMagicDef();
        Stats newStats = effectReceiver.getStats();
        newStats.setDefense(0);
        newStats.setMagicDef(0);
        effectReceiver.setShield(0);
        effectReceiver.setStats(newStats);
    }

    @Override
    public String getDescription() {
        return "Decrease a debuff holder's DEF and MDF to 0";
    }
}
