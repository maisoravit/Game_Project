package logic.actions.skills;

import javafx.scene.image.Image;
import logic.actions.ActionType;
import logic.actions.SkillAction;
import logic.actions.Target;
import logic.characters.BaseCharacter;
import utils.Assets;

public class GolemSkill extends SkillAction {
    public GolemSkill(BaseCharacter character) {
        super(
                character,
                "Stone Shield",
                4,
                new Image(Assets.getAsset("/assets/actions/enemy/enemyAction.png")),
                Target.Self
        );
        type = ActionType.Physical;
    }
    @Override
    public void activate(BaseCharacter targetCharacter) {
        getUser().setShield(getUser().getShield() + 20);
    }

    @Override
    public String getDescription() {
        return "This character receives 20 shield.";
    }
}