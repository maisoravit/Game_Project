package logic.actions.ultimates;

import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.actions.Target;
import logic.actions.UltimateAction;
import logic.characters.BaseCharacter;
import logic.characters.enemy.Minotaur;
import logic.team.Team;
import utils.Assets;

public class MinotaurUltimate extends UltimateAction {
    public MinotaurUltimate(BaseCharacter character) {
        super(
                character,
                "Beast Rage",
                0,
                new Image(Assets.getAsset("/assets/actions/enemy/enemyAction.png")),
                Target.Enemy
        );
        type = ActionType.Physical;
        this.spiritCost = 25;
    }
    @Override
    public void activate(BaseCharacter targetCharacter) {
        ((Minotaur) getUser()).setRageLevel(((Minotaur) getUser()).getRageLevel() + 3);
        targetCharacter.takeDamage(getUser(), getUser().getStats().getAttack() + getUser().getStats().getDefense(), type);
    }

    @Override
    public String getDescription() {
        return "Increase rage level by 3. Then attack 1 enemy. Damage base on own ATK + DEF.";
    }
}
