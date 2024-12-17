package logic.actions.normals;

import javafx.scene.image.Image;
import logic.actions.ActionType;
import logic.actions.NormalAction;
import logic.actions.Target;
import logic.characters.BaseCharacter;
import logic.characters.enemy.Minotaur;
import utils.Assets;

public class MinotaurNormal extends NormalAction {
    public MinotaurNormal(BaseCharacter character) {
        super(
                character,
                "Worororo!",
                3,
                new Image(Assets.getAsset("/assets/actions/enemy/enemyAction.png")),
                Target.Enemy
        );
        type = ActionType.Physical;
    }
    @Override
    public void activate(BaseCharacter targetCharacter) {
        ((Minotaur) getUser()).setRageLevel(((Minotaur) getUser()).getRageLevel() + 1);
        targetCharacter.takeDamage(getUser(), this.getUser().getStats().getAttack(), type);
    }

    @Override
    public String getDescription() {
        return "Increase rage level by 1. Then attack 1 enemy. Damage base on own ATK.";
    }
}