package logic.actions.skills;

import javafx.scene.image.Image;
import logic.actions.ActionType;
import logic.actions.SkillAction;
import logic.actions.Target;
import logic.characters.BaseCharacter;
import logic.characters.Stats;
import logic.characters.isekai.Mewten;
import logic.players.Player;
import utils.Assets;

public class MewtenSkill extends SkillAction {
    public MewtenSkill(BaseCharacter character) {
        super(
                character,
                "Tenacity of The Brave",
                5,
                new Image(Assets.getAsset("/assets/actions/mewten/mewtenSkill.png")),
                Target.Self
        );
        type = ActionType.Physical;
    }

    @Override
    public void activate(BaseCharacter targetCharacter) {
        Stats newStats = getUser().getStats();
        newStats.setDefense(newStats.getDefense() + 1);
        getUser().setStats(newStats);

        ((Mewten) getUser()).setPerfectGuard(true);
    }

    @Override
    public String getDescription() {
        return "Increase 1 DEF permanently for this battle. Then Mewten can completely block any incoming damage for once";
    }
}
