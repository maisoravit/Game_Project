package logic.actions.normals;

import javafx.scene.image.Image;
import logic.actions.ActionType;
import logic.actions.NormalAction;
import logic.actions.Target;
import logic.characters.BaseCharacter;
import utils.Assets;

public class SkeletonNormal extends NormalAction {
    public SkeletonNormal(BaseCharacter character) {
        super(
                character,
                "Yahhh!",
                2,
                new Image(Assets.getAsset("/assets/actions/enemy/enemyAction.png")),
                Target.Enemy
        );
        type = ActionType.Physical;
    }
    @Override
    public void activate(BaseCharacter targetCharacter) {
        targetCharacter.takeDamage(getUser(), this.getUser().getStats().getAttack(), type);
    }

    @Override
    public String getDescription() {
        return "Attack 1 enemy. Damage base on own ATK.";
    }
}
