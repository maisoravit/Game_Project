package logic.actions;

import javafx.scene.image.Image;
import logic.characters.BaseCharacter;

public abstract class UltimateAction extends Action{
    public UltimateAction(BaseCharacter user, String name, int manaCost, Image img, Target targetType) {
        super(user, name, manaCost, img, targetType);
    }
}
