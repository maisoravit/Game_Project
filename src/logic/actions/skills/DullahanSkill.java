package logic.actions.skills;

import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.actions.SkillAction;
import logic.actions.Target;
import logic.characters.BaseCharacter;
import logic.characters.Stats;
import logic.team.Team;
import utils.Assets;

public class DullahanSkill extends SkillAction {
    public DullahanSkill(BaseCharacter character) {
        super(
                character,
                "Strengthen The Soul",
                5,
                new Image(Assets.getAsset("/assets/actions/enemy/enemyAction.png")),
                Target.Enemy
        );
        type = ActionType.Physical;
    }
    @Override
    public void activate(BaseCharacter targetCharacter) {
        targetCharacter.takeDamage(getUser(), this.getUser().getStats().getAttack(), type);
        Team team = GameController.getInstance().getEnemyTeam();
        for(int i=0; i<team.getMembers().size(); i++) {
            Stats newStats = team.getMembers().get(i).getStats();
            newStats.setAttack(newStats.getAttack() + 1);
            newStats.setMagic(newStats.getMagic() + 1);
            newStats.setAttack(newStats.getDefense() + 1);
            newStats.setMagic(newStats.getMagicDef() + 1);
            team.getMembers().get(i).setStats(newStats);
        }
    }

    @Override
    public String getDescription() {
        return "Attack 1 enemy. Damage base on own ATK. Then increase 1 ATK, 1 MAT, 1 DEF and 1 MDF to every character in team for this battle";
    }
}