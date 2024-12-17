package logic.characters.isekai;

import javafx.scene.image.Image;
import logic.actions.Action;
import logic.actions.ActionType;
import logic.actions.normals.EstaaNormal;
import logic.actions.skills.EstaaSkill;
import logic.actions.ultimates.EstaaUltimate;
import logic.characters.Stats;
import logic.characters.Support;
import logic.characters.enemy.Skeleton;
import logic.effects.buffs.PotionOfStrength;
import logic.effects.buffs.PowerBoostingMeal;
import logic.effects.buffs.SoulBoost;
import logic.effects.debuffs.Paralyzed;
import logic.effects.debuffs.Poisoned;
import utils.Assets;

import java.util.ArrayList;
import java.util.Arrays;

public class Estaa extends Support {
    public Estaa() {
        super(
                "Jolly",
                "Estaa",
                new Stats(200, 10, 12, 35, 20),
                new Image(Assets.getAsset("/assets/characters/estaa/icon.png")),
                new Image(Assets.getAsset("/assets/characters/estaa/estaa.png")),
                new Image(Assets.getAsset("/assets/characters/estaa/estaaWithBg.png")),
                new Image(Assets.getAsset("/assets/characters/estaa/estaaCloseUp.png"))
        );
        super.setActionList(new ArrayList<Action>(Arrays.asList(new EstaaNormal(this), new EstaaSkill(this), new EstaaUltimate(this))));
        super.setOwnEffect(new ArrayList<>(Arrays.asList(new Paralyzed(new Skeleton(), 99), new Poisoned(new Skeleton(), 99), new PotionOfStrength(new Skeleton(), 99), new PowerBoostingMeal(new Skeleton(), 99))));
    }
}
