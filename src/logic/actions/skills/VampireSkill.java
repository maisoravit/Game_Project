package logic.actions.skills;

import javafx.application.Platform;
import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.actions.SkillAction;
import logic.actions.Target;
import logic.characters.BaseCharacter;
import logic.characters.Stats;
import logic.effects.Effect;
import logic.effects.TriggerEvent;
import logic.effects.debuffs.Burning;
import logic.effects.debuffs.SuckBlood;
import logic.interfaces.Debuffable;
import logic.interfaces.Healable;
import utils.Assets;

public class VampireSkill extends SkillAction implements Debuffable, Healable {
    public VampireSkill(BaseCharacter character) {
        super(
                character,
                "Vampire Instinct",
                5,
                new Image(Assets.getAsset("/assets/actions/enemy/enemyAction.png")),
                Target.Enemy
        );
        type = ActionType.Magical;
    }
    @Override
    public void activate(BaseCharacter targetCharacter) {
        targetCharacter.takeDamage(getUser(), this.getUser().getStats().getMagic() + 10, type);
        giveDebuff(targetCharacter);
        heal(getUser(), 5);
    }

    @Override
    public String getDescription() {
        return "Attack 1 enemy. damage base on own MAT + 10. Then give debuff 'Suck Blood' to the target. After that, self-heal by 5 HP";
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
}