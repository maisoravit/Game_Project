package logic.characters.enemy;

import javafx.scene.image.Image;
import logic.actions.Action;
import logic.actions.normals.SkeletonNormal;
import logic.actions.skills.SkeletonSkill;
import logic.actions.ultimates.SkeletonUltimate;
import logic.characters.Attacker;
import logic.characters.Stats;
import utils.Assets;

import java.util.ArrayList;
import java.util.Arrays;

public class Skeleton extends Attacker {
    public Skeleton() {
        super(
                "",
                "Skeleton",
                new Stats(200, 30, 20, 5, 15),
                new Image(Assets.getAsset("/assets/characters/enemy/skeleton/icon.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/skeleton/skeleton.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/skeleton/skeletonWithBg.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/skeleton/icon.png")),
                300,
                200
        );
        super.setActionList(new ArrayList<Action>(Arrays.asList(new SkeletonNormal(this), new SkeletonSkill(this), new SkeletonUltimate(this))));
    }

    public Skeleton(boolean isBot) {
        super(
                "",
                "Skeleton",
                new Stats(200, 30, 20, 5, 15),
                new Image(Assets.getAsset("/assets/characters/enemy/skeleton/icon.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/skeleton/skeleton.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/skeleton/skeletonWithBg.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/skeleton/icon.png")),
                300,
                200
        );
        super.setActionList(new ArrayList<Action>(Arrays.asList(new SkeletonNormal(this), new SkeletonSkill(this), new SkeletonUltimate(this))));
        this.setBot(isBot);
        this.setActionPattern(new ArrayList<Integer>(Arrays.asList(0,1,0,1,1,0,2)));
    }
}
