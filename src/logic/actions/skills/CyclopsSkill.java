package logic.actions.skills;

import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.actions.NormalAction;
import logic.actions.SkillAction;
import logic.actions.Target;
import logic.characters.BaseCharacter;
import logic.team.Team;
import utils.Assets;

public class CyclopsSkill extends SkillAction {
    public CyclopsSkill(BaseCharacter character) {
        super(
                character,
                "Big Punch",
                5,
                new Image(Assets.getAsset("/assets/actions/enemy/enemyAction.png")),
                Target.Enemy
        );
        type = ActionType.Physical;
    }
    @Override
    public void activate(BaseCharacter targetCharacter) {
        targetCharacter.takeDamage(getUser(), this.getUser().getStats().getAttack(), type);
        Team playerTeam = GameController.getInstance().getPlayerTeam();
        for(int i=0; i<playerTeam.getMembers().size(); i++) {
            playerTeam.getMembers().get(i).takeDamageDirectly(getUser(), 5, type);
        }
    }

    @Override
    public String getDescription() {
        return "Attack 1 enemy. Damage base on own ATK. Then directly attack all enemy for 5 damage.";
    }
}
