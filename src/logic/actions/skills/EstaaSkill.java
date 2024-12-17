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
import logic.effects.buffs.PotionOfStrength;
import logic.effects.debuffs.Poisoned;
import logic.interfaces.Buffable;
import logic.interfaces.Debuffable;
import utils.Assets;

public class EstaaSkill extends SkillAction implements Buffable, Debuffable {
    public EstaaSkill(BaseCharacter character) {
        super(
                character,
                "Potion or Poison? Guess it",
                4,
                new Image(Assets.getAsset("/assets/actions/estaa/estaaSkill.png")),
                Target.Both
        );
        type = ActionType.Magical;
    }

    @Override
    public void activate(BaseCharacter targetCharacter) {
        if(GameController.isEnemyTeam(targetCharacter)){
            targetCharacter.takeDamage(getUser(), getUser().getStats().getMagic(), type);
            giveDebuff(targetCharacter);
        } else {
            giveBuff(targetCharacter);
        }
    }

    @Override
    public String getDescription() {
        return "If a target is enemy, then deals damage to that enemy base on own MAT and give debuff 'Poisoned' to that enemy for 2 turns, " +
                "unless the enemy already has 'Poisoned', then increase 'Poisoned' level by 1 instead." +
                "But if a target is our team, then give buff 'Potion of Strength to that character for 2 turns.";
    }

    @Override
    public Effect giveBuff(BaseCharacter character) {
        boolean hasBuff = false;
        PotionOfStrength potionOfStrength = new PotionOfStrength(character, 2);

        for(int i=0; i<character.getStatusEffect().size(); i++) {
            Effect effect = character.getStatusEffect().get(i);
            if(effect instanceof PotionOfStrength) {
                potionOfStrength = (PotionOfStrength) effect;
                hasBuff = true;
            }
        }
        if(hasBuff) {
            potionOfStrength.addDuration(2);
        } else {
            character.getStatusEffect().add(potionOfStrength);
            potionOfStrength.activate();
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                character.getCard().getController().setEffect(character.getStatusEffect());
            }
        });
        return potionOfStrength;
    }

    @Override
    public Effect giveDebuff(BaseCharacter character) {
        boolean hasPoisoned = false;
        Poisoned poisoned = new Poisoned(character, 2);
        for(int i=0; i<character.getStatusEffect().size(); i++) {
            Effect effect = character.getStatusEffect().get(i);
            if(effect instanceof Poisoned) {
                poisoned = (Poisoned) effect;
                hasPoisoned = true;
            }
        }
        if(hasPoisoned) {
            poisoned.increasePoisonLevel();
            poisoned.addDuration(2);
        } else {
            character.getStatusEffect().add(poisoned);
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                character.getCard().getController().setEffect(character.getStatusEffect()); // before trigger event
                GameController.getInstance().runEffectByEvent(TriggerEvent.RECEIVE_DEBUFF, character);
            }
        });
        return poisoned;
    }
}
