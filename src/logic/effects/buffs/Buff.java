package logic.effects.buffs;

import javafx.scene.image.Image;
import logic.characters.BaseCharacter;
import logic.effects.Effect;
import logic.effects.TriggerEvent;
import logic.effects.TriggerType;

public abstract class Buff extends Effect {
    public Buff(String name, BaseCharacter effectReceiver, int duration, TriggerType triggerType, TriggerEvent triggerEvent, Image iconImg) {
        super(name, effectReceiver, duration, triggerType, triggerEvent, iconImg);
    }
}
