package logic.actions.ultimates;

import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.actions.Target;
import logic.actions.UltimateAction;
import logic.characters.BaseCharacter;
import logic.team.Team;
import utils.Assets;

public class SkeletonUltimate extends UltimateAction {
    public SkeletonUltimate(BaseCharacter character) {
        super(
                character,
                "Skull Crash",
                0,
                new Image(Assets.getAsset("/assets/actions/enemy/enemyAction.png")),
                Target.Enemy
        );
        type = ActionType.Physical;
        this.spiritCost = 25;
    }
    @Override
    public void activate(BaseCharacter targetCharacter) {
        if(GameController.getInstance().getPlayerTeam().getFront().contains(targetCharacter)) {
            GameController.getInstance().getPlayerTeam().getFront().forEach(character -> {
                character.takeDamage(getUser(),this.getUser().getStats().getAttack(), type);
            });
        } else if (GameController.getInstance().getPlayerTeam().getMid().contains(targetCharacter)) {
            GameController.getInstance().getPlayerTeam().getMid().forEach(character -> {
                character.takeDamage(getUser(), this.getUser().getStats().getAttack(), type);
            });
        } else if (GameController.getInstance().getPlayerTeam().getRear().contains(targetCharacter)) {
            GameController.getInstance().getPlayerTeam().getRear().forEach(character -> {
                character.takeDamage(getUser(), this.getUser().getStats().getAttack(), type);
            });
        }
    }

    @Override
    public String getDescription() {
        return "Attack all enemy on a target line. Damage base on own ATK";
    }
}
