package logic.actions.normals;

import javafx.scene.image.Image;
import logic.actions.ActionType;
import logic.actions.NormalAction;
import logic.actions.Target;
import logic.characters.BaseCharacter;
import logic.effects.Effect;
import logic.effects.debuffs.Poisoned;
import logic.interfaces.Debuffable;
import utils.Assets;

public class EstaaNormal extends NormalAction implements Debuffable {
    public EstaaNormal(BaseCharacter character) {
        super(
                character,
                "Balag Balag Balag!",
                3,
                new Image(Assets.getAsset("/assets/actions/estaa/estaaNormal.png")),
                Target.Enemy
        );
        type = ActionType.Magical;
    }

    @Override
    public void activate(BaseCharacter targetCharacter) {
        targetCharacter.takeDamage(getUser(), getUser().getStats().getMagic(), type);

        giveDebuff(targetCharacter);
    }

    @Override
    public Effect giveDebuff(BaseCharacter character) {
        Effect effect = null;
        for(int i=0; i<character.getStatusEffect().size(); i++) {
            if(character.getStatusEffect().get(i) instanceof Poisoned) {
                Poisoned poisoned = (Poisoned) character.getStatusEffect().get(i);
                poisoned.increasePoisonLevel();
                poisoned.addDuration(1);
                effect = poisoned;
                break;
            }
        }
        return effect;
    }

    @Override
    public String getDescription() {
        return "Attack 1 enemy. Damage base on own MAT. " +
                "And if a target enemy has debuff 'Poisoned', then increase the 'Poisoned' level and debuff duration by 1.";
    }
}
