package logic.skillCards;

import logic.GameController;
import logic.Phase;
import logic.actions.Action;
import logic.actions.skills.*;
import logic.characters.BaseCharacter;
import logic.characters.isekai.*;

public class SkillCard {
    private Action skillAction;

    private BaseCharacter character;

    public SkillCard(Action skillAction) {
        character = skillAction.getUser();
        this.skillAction = skillAction;
    }

    public void performAction() {
        if(GameController.getInstance().getCurrentPhase() == Phase.action) {
            System.out.println("looking for target...");
            character.resetTarget();
            GameController.getInstance().setCharactersColorToNormal();
            skillAction.setFromSkillCard(true);
            character.getTarget(skillAction);
        }
    }

    public Action getSkillAction() {
        return skillAction;
    }

    public void setSkillAction(Action skillAction) {
        this.skillAction = skillAction;
    }
}
