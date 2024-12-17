package logic.actions.ultimates;

import javafx.application.Platform;
import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.actions.Target;
import logic.actions.UltimateAction;
import logic.characters.BaseCharacter;
import logic.effects.Effect;
import logic.effects.TriggerEvent;
import logic.effects.debuffs.BrokenArmor;
import logic.effects.debuffs.Scare;
import logic.interfaces.Debuffable;
import logic.players.Player;
import utils.Assets;

import java.util.ArrayList;

public class ReirinUltimate extends UltimateAction implements Debuffable {
    public ReirinUltimate(BaseCharacter character) {
        super(
                character,
                "Yurei's horrific haunting",
                0,
                new Image(Assets.getAsset("/assets/actions/reirin/reirinUltimate.png")),
                Target.Enemy
        );
        type = ActionType.Magical;
        this.spiritCost = 25;
    }
    @Override
    public void activate(BaseCharacter targetCharacter) {
        targetCharacter.takeDamage(getUser(), getUser().getStats().getMagic() + 15, type);
        ArrayList<BaseCharacter> enemyTeam = GameController.getInstance().getEnemyTeam().getMembers();
        for(int i=0; i<enemyTeam.size(); i++) {
            giveDebuff(enemyTeam.get(i));
        }
        GameController.getInstance().getPlayer().setCurrentSpirit(
                GameController.getInstance().getPlayer().getCurrentSpirit() + 2
        );
    }

    @Override
    public String getDescription() {
        return "Attack 1 enemy. Damage base on own MAT + 15. Then give every enemy a debuff 'Scare' for 2 turns. After that, gain 2 spirit points." +
                "";
    }

    @Override
    public Effect giveDebuff(BaseCharacter character) {
        boolean hasScare = false;
        Scare scare = new Scare(character, 2);
        for(int i=0; i<character.getStatusEffect().size(); i++) {
            Effect effect = character.getStatusEffect().get(i);
            if(effect instanceof Scare) {
                scare = (Scare) effect;
                hasScare = true;
            }
        }
        if(hasScare) {
            scare.addDuration(2);
        } else {
            character.getStatusEffect().add(scare);
            scare.activate();
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                character.getCard().getController().setEffect(character.getStatusEffect()); // before trigger event
                GameController.getInstance().runEffectByEvent(TriggerEvent.RECEIVE_DEBUFF, character);
            }
        });
        return scare;
    }
}
