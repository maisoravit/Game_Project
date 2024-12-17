package logic.actions.skills;

import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.actions.SkillAction;
import logic.actions.Target;
import logic.characters.BaseCharacter;
import logic.players.Player;
import logic.team.Team;
import utils.Assets;

public class ElveneSkill extends SkillAction {
    public ElveneSkill(BaseCharacter character) {
        super(
                character,
                "Hail of Arrows",
                4,
                new Image(Assets.getAsset("/assets/actions/elvene/elveneSkill.png")),
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

        if(enemieTeam.getFront().contains(targetCharacter)) {
            for(int i=0; i<enemieTeam.getFront().size(); i++) {
                enemieTeam.getFront().get(i).takeDamage(getUser(), getUser().getStats().getAttack() - damageDecreaseByRange, type);
            }
        } else if (enemieTeam.getMid().contains(targetCharacter)) {
            for(int i=0; i<enemieTeam.getMid().size(); i++) {
                enemieTeam.getMid().get(i).takeDamage(getUser(), getUser().getStats().getAttack() - damageDecreaseByRange, type);
            }
        } else if (enemieTeam.getRear().contains(targetCharacter)) {
            for(int i=0; i<enemieTeam.getRear().size(); i++) {
                enemieTeam.getRear().get(i).takeDamage(getUser(), getUser().getStats().getAttack() - damageDecreaseByRange, type);
            }
        }

        enemieTeam.getRear().get(0).takeDamageDirectly(getUser(), 8, type);
    }

    @Override
    public String getDescription() {
        return "Attack every enemy on the target line in any range. The damage base on own ATK. " +
                "The damage will be decrease by 5 for every 1 line away from the first line that has enemy." +
                "Besides that directly attack 8 damage to a enemy on the rear line.";
    }
}
