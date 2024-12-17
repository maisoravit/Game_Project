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
import logic.effects.buffs.Purified;
import logic.effects.debuffs.Burning;
import logic.interfaces.Buffable;
import logic.interfaces.Healable;
import logic.players.Player;
import manager.SoundManager;
import utils.Assets;

public class LafySkill extends SkillAction implements Buffable, Healable {
    public LafySkill(BaseCharacter character) {
        super(
                character,
                "Fairy's Blessing",
                4,
                new Image(Assets.getAsset("/assets/actions/lafy/lafySkill.png")),
                Target.Friend
        );
        type = ActionType.Magical;
    }

    @Override
    public void activate(BaseCharacter targetCharacter) {
        heal(targetCharacter, 20);

        giveBuff(targetCharacter);
    }

    @Override
    public void activateEffect() {
        SoundManager.getInstance().playSFX("/sounds/lafy/skill.mp3", 0.75);
    }

    @Override
    public String getDescription() {
        return "Heal 1 companion by 20 HP. Then give that companion a buff 'Purified' for 2 turns.";
    }

    @Override
    public Effect giveBuff(BaseCharacter character) {
        boolean hasBuff = false;
        Purified purified = new Purified(character, 2);
        for(int i=0; i<character.getStatusEffect().size(); i++) {
            Effect effect = character.getStatusEffect().get(i);
            if(effect instanceof Purified) {
                purified = (Purified) effect;
                hasBuff = true;
            }
        }
        if(hasBuff) {
            purified.addDuration(2);
        } else {
            character.getStatusEffect().add(purified);
        }
        purified.activate();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                character.getCard().getController().setEffect(character.getStatusEffect());
            }
        });
        return purified;
    }

    @Override
    public void heal(BaseCharacter character, int amount) {
        Stats newStats = character.getStats();
        newStats.setHealth(newStats.getHealth() + amount);
        character.setStats(newStats);
        character.updateCharacterHp();
    }
}
