package logic.actions.ultimates;

import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.actions.Target;
import logic.actions.UltimateAction;
import logic.characters.BaseCharacter;
import logic.team.Team;
import utils.Assets;

public class GolemUltimate extends UltimateAction {
    public GolemUltimate(BaseCharacter character) {
        super(
                character,
                "The Great Stone Wall",
                0,
                new Image(Assets.getAsset("/assets/actions/enemy/enemyAction.png")),
                Target.Enemy
        );
        type = ActionType.Physical;
        this.spiritCost = 30;
    }
    @Override
    public void activate(BaseCharacter targetCharacter) {
        getUser().setShield(getUser().getShield() + 40);
    }

    @Override
    public String getDescription() {
        return "This character receives 40 shield.";
    }
}
