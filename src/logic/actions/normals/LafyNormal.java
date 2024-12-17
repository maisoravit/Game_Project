package logic.actions.normals;

import javafx.application.Platform;
import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.actions.NormalAction;
import logic.actions.Target;
import logic.characters.BaseCharacter;
import logic.effects.TriggerEvent;
import logic.players.Player;
import utils.Assets;

public class LafyNormal extends NormalAction {
    public LafyNormal(BaseCharacter character) {
        super(
                character,
                "Energy Bloom",
                1,
                new Image(Assets.getAsset("/assets/actions/lafy/lafyNormal.png")),
                Target.Enemy
        );
        type = ActionType.Magical;
    }

    @Override
    public void activate(BaseCharacter targetCharacter) {
        targetCharacter.takeDamage(getUser(), this.getUser().getStats().getMagic(), type);
        GameController.getInstance().getPlayer().setCurrentMana(
                GameController.getInstance().getPlayer().getCurrentMana() + 2
        );
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GameController.getInstance().getPlayerControlBarController().updateManaBar(
                        GameController.getInstance().getPlayer().getCurrentMana()
                );
            }
        });
    }

    @Override
    public String getDescription() {
        return "Attack 1 enemy. Damage base on own MAT. " +
                "Then restore 2 mana.";
    }
}
