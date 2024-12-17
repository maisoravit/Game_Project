package logic.effects.debuffs;

import javafx.scene.image.Image;
import logic.actions.ActionType;
import logic.characters.BaseCharacter;
import logic.characters.Stats;
import logic.characters.enemy.Vampire;
import logic.effects.TriggerEvent;
import logic.effects.TriggerType;
import logic.interfaces.Healable;
import utils.Assets;

public class SuckBlood extends Debuff implements Healable {

    private BaseCharacter debuffGiver;

    public SuckBlood(BaseCharacter effectReceiver, int duration, BaseCharacter debuffGiver) {
        super(
                "Suck Blood",
                effectReceiver,
                duration,
                TriggerType.TRIGGER,
                TriggerEvent.NEW_TURN,
                new Image(Assets.getAsset("/assets/effects/suckBlood.png"))
        );
        this.debuffGiver = debuffGiver;
    }

    @Override
    public void activate() {
        effectReceiver.takeDamageDirectly(new Vampire(), 10, ActionType.Magical);
        heal(debuffGiver, 10);
    }

    @Override
    public String getDescription() {
        return "Vampire will directly decrease a debuff holder's HP by 10." +
                "And increase that HP to herself";
    }

    @Override
    public void heal(BaseCharacter character, int amount) {
        Stats newStats = character.getStats();
        newStats.setHealth(newStats.getHealth() + amount);
        character.setStats(newStats);
        character.updateCharacterHp();
    }

    public BaseCharacter getDebuffGiver() {
        return debuffGiver;
    }

    public void setDebuffGiver(BaseCharacter debuffGiver) {
        this.debuffGiver = debuffGiver;
    }
}
