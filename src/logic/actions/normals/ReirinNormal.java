package logic.actions.normals;

import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.actions.NormalAction;
import logic.actions.Target;
import logic.characters.BaseCharacter;
import logic.characters.isekai.Reirin;
import logic.players.Player;
import utils.Assets;

public class ReirinNormal extends NormalAction {
    public ReirinNormal(BaseCharacter character) {
        super(
                character,
                "Opennn--!!",
                3,
                new Image(Assets.getAsset("/assets/actions/reirin/reirinNormal.png")),
                Target.Enemy
        );
        type = ActionType.Magical;
    }

    @Override
    public void activate(BaseCharacter targetCharacter) {
        targetCharacter.takeDamage(getUser(), this.getUser().getStats().getMagic(), type);
        GameController.getInstance().getPlayer().setCurrentSpirit(
            GameController.getInstance().getPlayer().getCurrentSpirit() + 1
        );
    }

    @Override
    public String getDescription() {
        return "Attack 1 enemy. Damage base on own MAT. " +
                "Then gain 1 spirit point.";
    }
}
