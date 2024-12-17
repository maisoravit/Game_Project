package logic.actions.skills;

import javafx.application.Platform;
import javafx.scene.image.Image;
import logic.actions.ActionType;
import logic.actions.SkillAction;
import logic.actions.Target;
import logic.characters.BaseCharacter;
import logic.characters.Stats;
import logic.effects.Effect;
import logic.effects.debuffs.Burning;
import logic.effects.debuffs.Debuff;
import logic.interfaces.Buffable;
import logic.interfaces.Debuffable;
import logic.players.Player;
import manager.SoundManager;
import utils.Assets;

public class KonaSkill extends SkillAction implements Debuffable {
    public KonaSkill(BaseCharacter character) {
        super(
                character,
                "Infinite Inferno",
                5,
                new Image(Assets.getAsset("/assets/actions/kona/konaSkill.png")),
                Target.Enemy
        );
        type = ActionType.Magical;
    }

    @Override
    public void activate(BaseCharacter targetCharacter) {
        int reduceHp = (int) (0.1 * getUser().getStats().getHealth());
        Stats newStats = getUser().getStats();
        newStats.setHealth(newStats.getHealth() - reduceHp);
        getUser().setStats(newStats);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getUser().updateCharacterHp();
                getUser().updateCheckAlive();
            }
        });

        int additionalDamage = 0;
        boolean hasDebuff = false;
        for(int i=0; i<getUser().getStatusEffect().size(); i++) {
            if(getUser().getStatusEffect().get(i) instanceof Debuff) {
                hasDebuff = true;
                break;
            }
        }
        if(!hasDebuff) {
            additionalDamage += 20;
        }

        targetCharacter.takeDamage(
                getUser(),
                getUser().getStats().getMagic()
                + targetCharacter.getStats().getMagicDef()
                + additionalDamage,
                type
        );

        giveDebuff(targetCharacter);
    }

    @Override
    public void activateEffect() {
        SoundManager.getInstance().playSFX("/sounds/kona/skill.mp3", 1);
    }

    // This Skill just add Duration. Not create a new one
    @Override
    public Effect giveDebuff(BaseCharacter character) {
        Effect effect = null;
        for(int i=0; i<character.getStatusEffect().size(); i++) {
            if(character.getStatusEffect().get(i) instanceof Burning) {
                character.getStatusEffect().get(i).addDuration(1);
                effect = character.getStatusEffect().get(i);
                break;
            }
        }
        return effect;
    }

    @Override
    public String getDescription() {
        return "Decrease own HP by 10% of current HP. " +
                "Then, Attack 1 enemy. Damage base on own MAT + target’s MDF. " +
                "If Kona doesn’t have any debuffs with her, then increase damage of this skill by 20. " +
                "And If target has debuff ‘Burning’ on it, then increase the dubuff duration by 1 turn.";
    }
}
