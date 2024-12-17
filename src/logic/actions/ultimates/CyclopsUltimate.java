package logic.actions.ultimates;

import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.actions.Target;
import logic.actions.UltimateAction;
import logic.characters.BaseCharacter;
import logic.team.Team;
import utils.Assets;

public class CyclopsUltimate extends UltimateAction {
    public CyclopsUltimate(BaseCharacter character) {
        super(
                character,
                "Mad Strike",
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
                character.takeDamage(getUser(), 2 * (getUser().getStats().getAttack() + getUser().getStats().getDefense()), type);
            });
        } else if (GameController.getInstance().getPlayerTeam().getMid().contains(targetCharacter)) {
            GameController.getInstance().getPlayerTeam().getMid().forEach(character -> {
                character.takeDamage(getUser(), 2 * (getUser().getStats().getAttack() + getUser().getStats().getDefense()), type);
            });
        } else if (GameController.getInstance().getPlayerTeam().getRear().contains(targetCharacter)) {
            GameController.getInstance().getPlayerTeam().getRear().forEach(character -> {
                character.takeDamage(getUser(), 2 * (getUser().getStats().getAttack() + getUser().getStats().getDefense()), type);
            });
        }
    }

    @Override
    public String getDescription() {
        return "Attack every enemy on the target line. The damage base on 2 times the sum of own ATK and own DEF. ";
    }
}
