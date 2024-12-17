package logic.actions.ultimates;

import javafx.application.Platform;
import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.actions.Target;
import logic.actions.UltimateAction;
import logic.characters.BaseCharacter;
import logic.characters.Stats;
import logic.effects.Effect;
import logic.effects.TriggerEvent;
import logic.effects.debuffs.SuckBlood;
import logic.interfaces.Debuffable;
import logic.interfaces.Healable;
import logic.team.Team;
import utils.Assets;

public class VampireUltimate extends UltimateAction implements Debuffable, Healable {
    public VampireUltimate(BaseCharacter character) {
        super(
                character,
                "Blood Blood Blood!! Ahaha haha~~~",
                0,
                new Image(Assets.getAsset("/assets/actions/enemy/enemyAction.png")),
                Target.Enemy
        );
        type = ActionType.Magical;
        this.spiritCost = 25;
    }
    @Override
    public void activate(BaseCharacter targetCharacter) {
        if(GameController.getInstance().getPlayerTeam().getFront().contains(targetCharacter)) {
            GameController.getInstance().getPlayerTeam().getFront().forEach(character -> {
                character.takeDamage(getUser(), this.getUser().getStats().getMagic() + 10, type);
                giveDebuff(character);
                heal(getUser(), 10);
            });
        } else if (GameController.getInstance().getPlayerTeam().getMid().contains(targetCharacter)) {
            GameController.getInstance().getPlayerTeam().getMid().forEach(character -> {
                character.takeDamage(getUser(), this.getUser().getStats().getMagic() + 10, type);
                giveDebuff(character);
                heal(getUser(), 10);
            });
        } else if (GameController.getInstance().getPlayerTeam().getRear().contains(targetCharacter)) {
            GameController.getInstance().getPlayerTeam().getRear().forEach(character -> {
                character.takeDamage(getUser(), this.getUser().getStats().getMagic() + 10, type);
                giveDebuff(character);
                heal(getUser(), 10);
            });
        }
    }

    @Override
    public Effect giveDebuff(BaseCharacter character) {
        boolean hasSuckBlood = false;
        SuckBlood suckBlood = new SuckBlood(character, 2, getUser());
        for(int i=0; i<character.getStatusEffect().size(); i++) {
            Effect effect = character.getStatusEffect().get(i);
            if(effect instanceof SuckBlood && ((SuckBlood) effect).getDebuffGiver() == getUser()) {
                suckBlood = (SuckBlood) effect;
                hasSuckBlood = true;
            }
        }
        if(hasSuckBlood) {
            suckBlood.addDuration(2);
        } else {
            character.getStatusEffect().add(suckBlood);
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                character.getCard().getController().setEffect(character.getStatusEffect()); // before trigger event
                GameController.getInstance().runEffectByEvent(TriggerEvent.RECEIVE_DEBUFF, character);
            }
        });
        return suckBlood;
    }

    @Override
    public void heal(BaseCharacter character, int amount) {
        Stats newStats = character.getStats();
        newStats.setHealth(newStats.getHealth() + amount);
        character.setStats(newStats);
        character.updateCharacterHp();
    }

    @Override
    public String getDescription() {
        return "For every enemy on a target line. Attack them. Damage base on own MAT + 10, give them a debuff 'Suck Blood' for 2 turns and self-heal by 10 HP";
    }
}
