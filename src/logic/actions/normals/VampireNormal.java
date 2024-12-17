package logic.actions.normals;

import javafx.scene.image.Image;
import logic.actions.ActionType;
import logic.actions.NormalAction;
import logic.actions.Target;
import logic.characters.BaseCharacter;
import utils.Assets;

public class VampireNormal extends NormalAction {
    public VampireNormal(BaseCharacter character) {
        super(
                character,
                "Greeting~~",
                3,
                new Image(Assets.getAsset("/assets/actions/enemy/enemyAction.png")),
                Target.Enemy
        );
        type = ActionType.Magical;
    }
    @Override
    public void activate(BaseCharacter targetCharacter) {
        targetCharacter.takeDamage(getUser(), this.getUser().getStats().getMagic(), type);
    }

    @Override
    public String getDescription() {
        return "Attack 1 enemy. Damage base on own MAT.";
    }
}