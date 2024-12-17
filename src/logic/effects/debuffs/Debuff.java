package logic.effects.debuffs;


import javafx.scene.image.Image;
import logic.characters.BaseCharacter;
import logic.effects.Effect;
import logic.effects.TriggerEvent;
import logic.effects.TriggerType;

//handle the Poisoned status effect
public abstract class Debuff extends Effect {

    public Debuff(String name, BaseCharacter effectReceiver, int duration, TriggerType triggerType, TriggerEvent triggerEvent, Image iconImg) {
        super(name, effectReceiver, duration, triggerType, triggerEvent, iconImg);
    }
}
