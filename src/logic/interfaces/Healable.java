package logic.interfaces;

import logic.characters.BaseCharacter;

public interface Healable {
    void heal(BaseCharacter character, int amount);
}
