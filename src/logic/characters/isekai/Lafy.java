package logic.characters.isekai;

import javafx.scene.image.Image;
import logic.actions.Action;
import logic.actions.ActionType;
import logic.actions.normals.LafyNormal;
import logic.actions.skills.LafySkill;
import logic.actions.ultimates.LafyUltimate;
import logic.characters.Stats;
import logic.characters.Support;
import logic.characters.enemy.Skeleton;
import logic.effects.buffs.HalfLifeSacrifice;
import logic.effects.buffs.Purified;
import logic.effects.buffs.SoulBoost;
import utils.Assets;

import java.util.ArrayList;
import java.util.Arrays;

public class Lafy extends Support {
    public Lafy() {
        super(
                "Hanabi",
                "Lafy",
                new Stats(250, 15, 15, 22, 20),
                new Image(Assets.getAsset("/assets/characters/lafy/icon.png")),
                new Image(Assets.getAsset("/assets/characters/lafy/lafy.png")),
                new Image(Assets.getAsset("/assets/characters/lafy/lafyWithBg.png")),
                new Image(Assets.getAsset("/assets/characters/lafy/lafyCloseUp.png"))
        );
        super.setActionList(new ArrayList<Action>(Arrays.asList(new LafyNormal(this), new LafySkill(this), new LafyUltimate(this))));
        super.setOwnEffect(new ArrayList<>(Arrays.asList(new Purified(new Skeleton(), 99), new HalfLifeSacrifice(new Skeleton(), 99, this))));
    }
}
