package logic.actions.normals;

import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.actions.NormalAction;
import logic.actions.Target;
import logic.characters.BaseCharacter;
import utils.Assets;

public class DevilSageNormal extends NormalAction {
    public DevilSageNormal(BaseCharacter character) {
        super(
                character,
                "Forbidden Gathering Spell",
                4,
                new Image(Assets.getAsset("/assets/actions/enemy/enemyAction.png")),
                Target.Enemy
        );
        type = ActionType.Magical;
    }

    @Override
    public void activate(BaseCharacter targetCharacter) {
        int additionalDmg = 0;
        for (int i = 0; i < GameController.getInstance().getBattleBoard().getController().getAllCards().size(); i++) {
            BaseCharacter character = GameController.getInstance().getBattleBoard().getController().getAllCards().get(i).getController().getCharacter();
            additionalDmg += character.getStatusEffect().size();
        }
        targetCharacter.takeDamage(getUser(), this.getUser().getStats().getMagic() + additionalDmg, type);
    }

    @Override
    public String getDescription() {
        return "Attack 1 enemy. Damage base on MAT + additional damage. " +
                "The additional damage increase by 5 for every buff or debuff on the field.";
    }
}
