package logic.actions;

import javafx.scene.image.Image;
import logic.characters.BaseCharacter;
import logic.interfaces.Buffable;

public abstract class SkillAction extends Action{
    public SkillAction(BaseCharacter user, String name, int manaCost, Image img, Target targetType) {
        super(user, name, manaCost, img, targetType);
    }
}
