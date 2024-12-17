package logic.actions.normals;

import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.actions.NormalAction;
import logic.actions.Target;
import logic.characters.BaseCharacter;
import logic.players.Player;
import logic.team.Team;
import ui.battle_scene.components.BattleTeam;
import utils.Assets;

import java.util.ArrayList;

public class ElveneNormal extends NormalAction {
    public ElveneNormal(BaseCharacter character) {
        super(
                character,
                "No Escape!",
                3,
                new Image(Assets.getAsset("/assets/actions/elvene/elveneNormal.png")),
                Target.AllEnemy
        );
        type = ActionType.Physical;
    }

    @Override
    public void activate(BaseCharacter targetCharacter) {
        int damageDecreaseByRange = 0;
        Team enemieTeam = GameController.getInstance().getEnemyTeam();
        if(!enemieTeam.getFront().isEmpty()) {
            if(enemieTeam.getMid().contains(targetCharacter)){
                damageDecreaseByRange = 5;
            } else if (enemieTeam.getRear().contains(targetCharacter)) {
                damageDecreaseByRange = 10;
            }
        } else if (!enemieTeam.getMid().isEmpty()) {
            if(enemieTeam.getRear().contains(targetCharacter)){
                damageDecreaseByRange = 5;
            }
        } else {
            // this case rear line is the first line that has enemy
        }

        targetCharacter.takeDamage(getUser(), getUser().getStats().getAttack() - damageDecreaseByRange, type);
    }

    @Override
    public String getDescription() {
        return "Attack 1 enemy in any range. The damage base on own ATK. " +
                "The damage will be decrease by 5 for every 1 line away from the first line that has enemy.";
    }
}
