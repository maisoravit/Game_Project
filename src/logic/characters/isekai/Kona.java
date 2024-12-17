package logic.characters.isekai;

import javafx.scene.image.Image;
import logic.actions.Action;
import logic.actions.ActionType;
import logic.actions.normals.KonaNormal;
import logic.actions.skills.KonaSkill;
import logic.actions.ultimates.KonaUltimate;
import logic.characters.Attacker;
import logic.characters.Stats;
import logic.characters.enemy.Skeleton;
import logic.effects.buffs.SoulBoost;
import logic.effects.debuffs.Burning;
import utils.Assets;

import java.util.ArrayList;
import java.util.Arrays;

public class Kona extends Attacker {
    public Kona() {
        super(
                "Draki",
                "Kona",
                new Stats(175, 28, 14, 45, 22),
                new Image(Assets.getAsset("/assets/characters/kona/icon.png")),
                new Image(Assets.getAsset("/assets/characters/kona/kona.png")),
                new Image(Assets.getAsset("/assets/characters/kona/konaWithBg.png")),
                new Image(Assets.getAsset("/assets/characters/kona/konaCloseUp.png"))
        );
        super.setActionList(new ArrayList<Action>(Arrays.asList(new KonaNormal(this), new KonaSkill(this), new KonaUltimate(this))));
        super.setOwnEffect(new ArrayList<>(Arrays.asList(new Burning(new Skeleton(), 99))));
    }
}
