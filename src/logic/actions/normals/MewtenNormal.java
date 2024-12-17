package logic.actions.normals;

import javafx.scene.image.Image;
import logic.actions.ActionType;
import logic.actions.NormalAction;
import logic.actions.Target;
import logic.characters.BaseCharacter;
import logic.characters.isekai.Mewten;
import logic.players.Player;
import utils.Assets;

public class MewtenNormal extends NormalAction {
    public MewtenNormal(BaseCharacter character) {
        super(
                character,
                "Execution",
                2,
                new Image(Assets.getAsset("/assets/actions/mewten/mewtenNormal.png")),
                Target.Enemy
        );
        type = ActionType.Physical;
    }

    @Override
    public void activate(BaseCharacter targetCharacter) {
        targetCharacter.takeDamage(getUser(), this.getUser().getStats().getAttack(), type);
    }

    @Override
    public String getDescription() {
        return "Attack 1 enenmy. Damage base on own ATK.";
    }
}
