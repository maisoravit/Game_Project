package logic.characters;

import javafx.scene.image.Image;
import logic.actions.Action;

import java.util.ArrayList;

/**
 * This class is the base class for all supporters in the game.
 */
public abstract class Support extends BaseCharacter {
    public Support(String lastname, String name, Stats stats, Image imgIcon, Image imgSprite, Image imgWithBg, Image imgCloseUp) {
        super(lastname, name, stats, imgIcon, imgSprite, imgWithBg, imgCloseUp);
    }

    public Support(String lastname, String name, Stats stats, Image imgIcon, Image imgSprite, Image imgWithBg, Image imgCloseUp, double height, double width) {
        super(lastname, name, stats, imgIcon, imgSprite, imgWithBg, imgCloseUp, height, width);
    }

    @Override
    public String toString() {
        return "Supporter{" +
                "name='" + lastname + " " + name + '\'' +
                '}';
    }
}
