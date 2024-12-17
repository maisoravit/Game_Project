package logic.actions.normals;

import javafx.scene.image.Image;
import logic.actions.ActionType;
import logic.actions.NormalAction;
import logic.actions.Target;
import logic.characters.BaseCharacter;
import utils.Assets;

public class CyclopsNormal extends NormalAction {
    public CyclopsNormal(BaseCharacter character) {
        super(
                character,
                "Giant Smash",
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
        return "Attack 1 enemy. Damage Base on own ATK.";
    }
}
