package logic.actions.normals;

import javafx.scene.image.Image;
import logic.actions.ActionType;
import logic.actions.NormalAction;
import logic.actions.Target;
import logic.characters.BaseCharacter;
import utils.Assets;

public class GolemNormal extends NormalAction {
    public GolemNormal(BaseCharacter character) {
        super(
                character,
                "Rock Slammed",
                3,
                new Image(Assets.getAsset("/assets/actions/enemy/enemyAction.png")),
                Target.Enemy
        );
        type = ActionType.Physical;
    }
    @Override
    public void activate(BaseCharacter targetCharacter) {
        targetCharacter.takeDamage(getUser(), getUser().getStats().getDefense(), type);
        getUser().setShield(getUser().getShield() + 5);
    }

    @Override
    public String getDescription() {
        return "Attack 1 enemy. Damage base on own DEF. Then this character receives 5 shield.";
    }
}