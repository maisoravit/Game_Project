package logic.interfaces;

import logic.characters.BaseCharacter;
import logic.effects.Effect;
import logic.effects.buffs.Buff;

public interface Buffable {
    Effect giveBuff(BaseCharacter character);
}
