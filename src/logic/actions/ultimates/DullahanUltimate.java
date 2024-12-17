package logic.actions.ultimates;

import javafx.application.Platform;
import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.actions.Target;
import logic.actions.UltimateAction;
import logic.characters.BaseCharacter;
import logic.effects.Effect;
import logic.effects.buffs.PowerBoostingMeal;
import logic.effects.buffs.SoulBoost;
import logic.interfaces.Buffable;
import logic.team.Team;
import utils.Assets;

import java.util.ArrayList;

public class DullahanUltimate extends UltimateAction implements Buffable {
    public DullahanUltimate(BaseCharacter character) {
        super(
                character,
                "Soul Share",
                0,
                new Image(Assets.getAsset("/assets/actions/enemy/enemyAction.png")),
                Target.Enemy
        );
        type = ActionType.Physical;
        this.spiritCost = 35;
    }
    @Override
    public void activate(BaseCharacter targetCharacter) {
        if(GameController.getInstance().getPlayerTeam().getFront().contains(targetCharacter)) {
            GameController.getInstance().getPlayerTeam().getFront().forEach(character -> {
                character.takeDamage(getUser(), this.getUser().getStats().getAttack() + 20, type);
            });
        } else if (GameController.getInstance().getPlayerTeam().getMid().contains(targetCharacter)) {
            GameController.getInstance().getPlayerTeam().getMid().forEach(character -> {
                character.takeDamage(getUser(), this.getUser().getStats().getAttack() + 20, type);
            });
        } else if (GameController.getInstance().getPlayerTeam().getRear().contains(targetCharacter)) {
            GameController.getInstance().getPlayerTeam().getRear().forEach(character -> {
                character.takeDamage(getUser(), this.getUser().getStats().getAttack() + 20, type);
            });
        }

        for (BaseCharacter character : GameController.getInstance().getEnemyTeam().getMembers()) {
            giveBuff(character);
        }
    }

    @Override
    public String getDescription() {
        return "Attack all enemy on a target line. Damage base on own ATK + 20. Then give a buff 'Soul Boost' to every character in a same team.";
    }

    @Override
    public Effect giveBuff(BaseCharacter character) {
        boolean hasBuff = false;
        SoulBoost soulBoost = new SoulBoost(character, 3);

        for(int i=0; i<character.getStatusEffect().size(); i++) {
            Effect effect = character.getStatusEffect().get(i);
            if(effect instanceof PowerBoostingMeal) {
                soulBoost = (SoulBoost) effect;
                hasBuff = true;
            }
        }
        if(hasBuff) {
            soulBoost.addDuration(3);
        } else {
            character.getStatusEffect().add(soulBoost);
            soulBoost.activate();
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                character.getCard().getController().setEffect(character.getStatusEffect());
            }
        });
        return soulBoost;
    }
}
