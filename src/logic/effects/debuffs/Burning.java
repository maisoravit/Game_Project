package logic.effects.debuffs;

import javafx.scene.image.Image;
import logic.actions.ActionType;
import logic.characters.BaseCharacter;
import logic.characters.enemy.Skeleton;
import logic.characters.isekai.Kona;
import logic.effects.TriggerEvent;
import logic.effects.TriggerType;
import utils.Assets;

public class Burning extends Debuff{
    public Burning(BaseCharacter effectReceiver, int duration) {
        super(
                "Burning",
                effectReceiver,
                duration,
                TriggerType.TRIGGER,
                TriggerEvent.NEW_TURN,
                new Image(Assets.getAsset("/assets/effects/burning.png"))
        );
    }

    @Override
    public void activate() {
        effectReceiver.takeDamageDirectly(new Kona(), 5, ActionType.Magical);
    }

    @Override
    public String getDescription() {
        return "Each turn, deal direct damage to a debuff holder by 5 damage";
    }
}
