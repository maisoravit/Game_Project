package logic.actions.skills;

import javafx.application.Platform;
import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.actions.SkillAction;
import logic.actions.Target;
import logic.characters.BaseCharacter;
import logic.effects.Effect;
import logic.effects.TriggerEvent;
import logic.effects.debuffs.BrokenArmor;
import logic.interfaces.Debuffable;
import utils.Assets;

import java.util.ArrayList;

public class DevilSageSkill extends SkillAction implements Debuffable {
    public DevilSageSkill(BaseCharacter character) {
        super(
                character,
                "Defense Collapse",
                5,
                new Image(Assets.getAsset("/assets/actions/enemy/enemyAction.png")),
                Target.AllEnemy
        );
        type = ActionType.Magical;
    }
    @Override
    public void activate(BaseCharacter targetCharacter) {
        ArrayList<BaseCharacter> playerTeam = GameController.getInstance().getPlayerTeam().getMembers();
        for(int i=0; i<playerTeam.size(); i++) {
            playerTeam.get(i).takeDamageDirectly(getUser(), 10, type);
            giveDebuff(playerTeam.get(i));
        }
    }

    @Override
    public String getDescription() {
        return "Directly attack all enemy for 10 damage. And give them a debuff 'Broken Armor' for 2 turns.";
    }

    @Override
    public Effect giveDebuff(BaseCharacter character) {
        boolean hasBrokenArmor = false;
        BrokenArmor brokenArmor = new BrokenArmor(character, 2);
        for(int i=0; i<character.getStatusEffect().size(); i++) {
            Effect effect = character.getStatusEffect().get(i);
            if(effect instanceof BrokenArmor) {
                brokenArmor = (BrokenArmor) effect;
                hasBrokenArmor = true;
            }
        }
        if(hasBrokenArmor) {
            brokenArmor.addDuration(2);
        } else {
            character.getStatusEffect().add(brokenArmor);
            brokenArmor.activate();
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                character.getCard().getController().setEffect(character.getStatusEffect()); // before trigger event
                GameController.getInstance().runEffectByEvent(TriggerEvent.RECEIVE_DEBUFF, character);
            }
        });
        return brokenArmor;
    }
}
