package logic.actions.skills;

import javafx.scene.image.Image;
import logic.actions.ActionType;
import logic.actions.NormalAction;
import logic.actions.SkillAction;
import logic.actions.Target;
import logic.characters.BaseCharacter;
import utils.Assets;

public class SkeletonSkill extends SkillAction {
    public SkeletonSkill(BaseCharacter character) {
        super(
                character,
                "Bone Cut!",
                3,
                new Image(Assets.getAsset("/assets/actions/enemy/enemyAction.png")),
                Target.Enemy
        );
        type = ActionType.Physical;
    }
    @Override
    public void activate(BaseCharacter targetCharacter) {
        targetCharacter.takeDamage(getUser(), this.getUser().getStats().getAttack() + 15, type);
    }

    @Override
    public String getDescription() {
        return "Attack 1 enemy. damage base on own ATK + 15";
    }
}
