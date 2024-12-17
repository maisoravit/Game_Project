package logic.actions.normals;

import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.actions.NormalAction;
import logic.actions.Target;
import logic.characters.BaseCharacter;
import logic.team.Team;
import utils.Assets;

public class DullahanNormal extends NormalAction {
    public DullahanNormal(BaseCharacter character) {
        super(
                character,
                "Scythe Attack",
                3,
                new Image(Assets.getAsset("/assets/actions/enemy/enemyAction.png")),
                Target.Enemy
        );
        type = ActionType.Physical;
    }
    @Override
    public void activate(BaseCharacter targetCharacter) {
        Team playerTeam = GameController.getInstance().getPlayerTeam();
        if(playerTeam.getFront().contains(targetCharacter)) {
            for(int i=0; i<playerTeam.getFront().size(); i++) {
                playerTeam.getFront().get(i).takeDamage(getUser(), getUser().getStats().getAttack(), type);
            }
        } else if (playerTeam.getMid().contains(targetCharacter)) {
            for(int i=0; i<playerTeam.getMid().size(); i++) {
                playerTeam.getMid().get(i).takeDamage(getUser(), getUser().getStats().getAttack(), type);
            }
        } else if (playerTeam.getRear().contains(targetCharacter)) {
            for(int i=0; i<playerTeam.getRear().size(); i++) {
                playerTeam.getRear().get(i).takeDamage(getUser(), getUser().getStats().getAttack(), type);
            }
        }
    }

    @Override
    public String getDescription() {
        return "Attack every enemy on the target line. The damage base on own ATK.";
    }
}