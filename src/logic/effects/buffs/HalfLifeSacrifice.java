package logic.effects.buffs;

import javafx.scene.image.Image;
import logic.GameController;
import logic.characters.BaseCharacter;
import logic.characters.Stats;
import logic.effects.Effect;
import logic.effects.TriggerEvent;
import logic.effects.TriggerType;
import utils.Assets;

import java.util.ArrayList;

public class HalfLifeSacrifice extends Buff {
    private BaseCharacter buffGiver;

    private static boolean isUsed = false;
    public HalfLifeSacrifice(BaseCharacter effectReceiver, int duration, BaseCharacter buffGiver) {
        super(
                "Half-Life Sacrifice",
                effectReceiver,
                duration,
                TriggerType.TRIGGER,
                TriggerEvent.HP_LTE_0,
                new Image(Assets.getAsset("/assets/effects/half-life-sacrifice.png"))
        );
        this.buffGiver = buffGiver;
    }

    @Override
    public void activate() {
        if(!isUsed) {
            isUsed = true;
            Stats receiverStats = effectReceiver.getStats();
            receiverStats.setHealth((int)(0.5 * receiverStats.getMAX_HP()));
            effectReceiver.setStats(receiverStats);
            effectReceiver.updateCharacterHp();
            effectReceiver.updateCheckAlive();

            Stats giverStats = buffGiver.getStats();
            giverStats.setHealth((int)(0.5 * giverStats.getHealth()));
            buffGiver.setStats(giverStats);
            buffGiver.updateCharacterHp();
            buffGiver.updateCheckAlive();
            System.out.println(buffGiver.getName() + "'s HP remain " + buffGiver.getStats().getHealth());
            System.out.println(effectReceiver.getName() + "'s HP remain " + effectReceiver.getStats().getHealth());
            ArrayList<BaseCharacter> playerCharacters = GameController.getInstance().getPlayerTeam().getMembers();
            for(int i=0; i<playerCharacters.size(); i++) {
                ArrayList<Effect> statusEffects = playerCharacters.get(i).getStatusEffect();
                for(int j=0; j<statusEffects.size(); j++) {
                    if(statusEffects.get(j) instanceof HalfLifeSacrifice) {
                        statusEffects.get(j).clearEffect();
                    }
                }
            }
        }
    }

    @Override
    public String getDescription() {
        return "This buff can use once per battle. This buff will activate when this buff holder's HP is below or equal to 0. " +
                "Then the buff will decrease 50% of Lafy's current HP and the buff holder will set current HP to 50% of max HP.";
    }

    public static boolean isUsed() {
        return isUsed;
    }

    public static void setIsUsed(boolean isUsed) {
        HalfLifeSacrifice.isUsed = isUsed;
    }

    public BaseCharacter getBuffGiver() {
        return buffGiver;
    }
}
