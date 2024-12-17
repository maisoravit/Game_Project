package logic.actions.ultimates;

import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.actions.Target;
import logic.actions.UltimateAction;
import logic.characters.BaseCharacter;
import logic.players.Player;
import logic.team.Team;
import utils.Assets;

public class ElveneUltimate extends UltimateAction {
    public ElveneUltimate(BaseCharacter character) {
        super(
                character,
                "Penetrate to The Heart",
                0,
                new Image(Assets.getAsset("/assets/actions/elvene/elveneUltimate.png")),
                Target.AllEnemy
        );
        type = ActionType.Physical;
        this.spiritCost = 25;
    }

    @Override
    public void activate(BaseCharacter targetCharacter) {
        int damageDecreaseByRange = 0;
        Team enemieTeam = GameController.getInstance().getEnemyTeam();
        if(!enemieTeam.getFront().isEmpty()) {
            if(enemieTeam.getMid().contains(targetCharacter)){
                damageDecreaseByRange = 10;
            } else if (enemieTeam.getRear().contains(targetCharacter)) {
                damageDecreaseByRange = 20;
            }
        } else if (!enemieTeam.getMid().isEmpty()) {
            if(enemieTeam.getRear().contains(targetCharacter)){
                damageDecreaseByRange = 10;
            }
        } else {
            // this case rear line is the first line that has enemy
        }

        targetCharacter.takeDamage(
                getUser(),
                getUser().getStats().getAttack()
                + getUser().getStats().getDefense()
                + getUser().getStats().getMagic()
                + getUser().getStats().getMagicDef()
                - damageDecreaseByRange,
                type
        );
    }

    @Override
    public String getDescription() {
        return "Attack 1 enemy in any range. The damage base on own ATK + DEF + MAT + MDF. " +
                "The damage will be decrease by 10 for every 1 line away from the first line that has enemy.";
    }
}
