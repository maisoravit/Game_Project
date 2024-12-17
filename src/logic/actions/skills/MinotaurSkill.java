package logic.actions.skills;

import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.actions.SkillAction;
import logic.actions.Target;
import logic.characters.BaseCharacter;
import logic.characters.enemy.Minotaur;
import utils.Assets;

public class MinotaurSkill extends SkillAction {
    public MinotaurSkill(BaseCharacter character) {
        super(
                character,
                "Fury Roar",
                5,
                new Image(Assets.getAsset("/assets/actions/enemy/enemyAction.png")),
                Target.Enemy
        );
        type = ActionType.Physical;
    }
    @Override
    public void activate(BaseCharacter targetCharacter) {
        int damage = getUser().getStats().getAttack();
        ((Minotaur) getUser()).setRageLevel(((Minotaur) getUser()).getRageLevel() + 1);
        if(((Minotaur) getUser()).getRageLevel() >= 5) {
            damage *= 3;
            ((Minotaur) getUser()).setRageLevel(((Minotaur) getUser()).getRageLevel() - 5);
        }
        if(GameController.getInstance().getPlayerTeam().getFront().contains(targetCharacter)) {
            for(BaseCharacter character : GameController.getInstance().getPlayerTeam().getFront()) {
                character.takeDamage(getUser(), damage, type);
            }
        } else if (GameController.getInstance().getPlayerTeam().getMid().contains(targetCharacter)) {
            for(BaseCharacter character : GameController.getInstance().getPlayerTeam().getMid()) {
                character.takeDamage(getUser(), damage, type);
            }
        } else if (GameController.getInstance().getPlayerTeam().getRear().contains(targetCharacter)) {
            for(BaseCharacter character : GameController.getInstance().getPlayerTeam().getRear()) {
                character.takeDamage(getUser(), damage, type);
            }
        }
    }

    @Override
    public String getDescription() {
        return "Increase rage level by 1. After that, if the rage level is greater or equal to 5, " +
                "then decrease 5 rage level and this action will deal 3 time of damage base on own ATK to target. " +
                "But if not, then deal damage base on ATK to target.";
    }
}