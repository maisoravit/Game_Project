package logic.characters;

import logic.characters.isekai.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to store all the characters in the game.
 * It is use for the Team management Pane in the Battle_Scene.
 */
public class CharacterRegistry {
    private static final ArrayList<BaseCharacter> CHARACTER_ARRAY_LIST = new ArrayList<>(
            List.of(
                    new Elvene(),
                    new Estaa(),
                    new Kona(),
                    new Lafy(),
                    new Reirin(),
                    new Mewten()
            )
    );

    public static ArrayList<BaseCharacter> getCharactersList() {
        return CHARACTER_ARRAY_LIST;
    }
}
