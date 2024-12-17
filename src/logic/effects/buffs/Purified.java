package logic.effects.buffs;

import javafx.scene.image.Image;
import logic.characters.BaseCharacter;
import logic.effects.Effect;
import logic.effects.TriggerEvent;
import logic.effects.TriggerType;
import logic.effects.debuffs.Debuff;
import utils.Assets;

import java.util.ArrayList;

public class Purified extends Buff {
    public Purified(BaseCharacter effectReceiver, int duration) {
        super(
                "Purified",
                effectReceiver,
                duration,
                TriggerType.TRIGGER,
                TriggerEvent.RECEIVE_DEBUFF,
                new Image(Assets.getAsset("/assets/effects/purified.png"))
        );
    }

    @Override
    public void activate() {
        for(int i=0; i<effectReceiver.getStatusEffect().size(); i++) {
            Effect effect = effectReceiver.getStatusEffect().get(i);
            if(effect instanceof Debuff) {
                effect.clearEffect();
            }
        }
    }

    @Override
    public String getDescription() {
        return "A buff holder will not receive any debuff during this buff duration";
    }
}
