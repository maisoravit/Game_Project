package logic.actions.skills;

import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.actions.SkillAction;
import logic.actions.Target;
import logic.characters.BaseCharacter;
import logic.players.Player;
import utils.Assets;

public class ReirinSkill extends SkillAction {
    public ReirinSkill(BaseCharacter character) {
        super(
                character,
                "Please! Protect us Rabbit-san!!",
                4,
                new Image(Assets.getAsset("/assets/actions/reirin/reirinSkill.png")),
                Target.Friend
        );
        type = ActionType.Magical;
    }
    @Override
    public void activate(BaseCharacter targetCharacter) {
        if(GameController.getInstance().getPlayerTeam().getFront().contains(targetCharacter)) {
            GameController.getInstance().getPlayerTeam().getFront().forEach(character -> {
                character.setShield(character.getShield() + 15);
            });
        } else if (GameController.getInstance().getPlayerTeam().getMid().contains(targetCharacter)) {
            GameController.getInstance().getPlayerTeam().getMid().forEach(character -> {
                character.setShield(character.getShield() + 15);
            });
        } else if (GameController.getInstance().getPlayerTeam().getRear().contains(targetCharacter)) {
            GameController.getInstance().getPlayerTeam().getRear().forEach(character -> {
                character.setShield(character.getShield() + 15);
            });
        }
        GameController.getInstance().getPlayer().setCurrentSpirit(
                GameController.getInstance().getPlayer().getCurrentSpirit() + 1
        );
    }

    @Override
    public String getDescription() {
        return "Give 15 shield to every companion on target line. Then gain 1 spirit point.";
    }
}
