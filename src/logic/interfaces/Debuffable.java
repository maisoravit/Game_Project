package logic.interfaces;

import logic.characters.BaseCharacter;
import logic.effects.Effect;
import logic.effects.debuffs.Debuff;

public interface Debuffable {
    Effect giveDebuff(BaseCharacter character);
}
