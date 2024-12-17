package logic.actions.ultimates;

import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.actions.Target;
import logic.actions.UltimateAction;
import logic.characters.BaseCharacter;
import logic.characters.enemy.DevilSage;
import logic.team.Team;
import utils.Assets;

import java.util.ArrayList;

public class DevilSageUltimate extends UltimateAction {
    public DevilSageUltimate(BaseCharacter character) {
        super(
                character,
                "Follow My Command!",
                0,
                new Image(Assets.getAsset("/assets/actions/enemy/enemyAction.png")),
                Target.Friend
        );
        type = ActionType.Magical;
        this.spiritCost = 40;
    }
    @Override
    public void activate(BaseCharacter targetCharacter) {
        ArrayList<BaseCharacter> enemyTeam = GameController.getInstance().getEnemyTeam().getMembers();
        for(int i=0; i<enemyTeam.size(); i++) {
            if(!(enemyTeam.get(i) instanceof DevilSage)) {
                enemyTeam.get(i).setActionThisTurn(false);
                enemyTeam.get(i).performAction(enemyTeam.get(i).getActionList().get(2));
            }
        }
    }

    @Override
    public String getDescription() {
        return null;
    }
}
