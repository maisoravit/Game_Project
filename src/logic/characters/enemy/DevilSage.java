package logic.characters.enemy;

import javafx.scene.image.Image;
import logic.actions.Action;
import logic.actions.normals.ElveneNormal;
import logic.actions.normals.DevilSageNormal;
import logic.actions.skills.ElveneSkill;
import logic.actions.skills.DevilSageSkill;
import logic.actions.ultimates.DevilSageUltimate;
import logic.actions.ultimates.ElveneUltimate;
import logic.characters.Attacker;
import logic.characters.Stats;
import logic.characters.Support;
import logic.effects.Effect;
import logic.effects.debuffs.BrokenArmor;
import utils.Assets;

import java.util.ArrayList;
import java.util.Arrays;

public class DevilSage extends Support {
    public DevilSage() {
        super(
                "",
                "Devil Sage",
                new Stats(320, 15, 13, 50, 25),
                new Image(Assets.getAsset("/assets/characters/enemy/devilSage/icon.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/devilSage/devilSage.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/devilSage/devilSageWithBg.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/devilSage/icon.png")),
                350,
                250
        );
        super.setActionList(new ArrayList<Action>(
                Arrays.asList(new DevilSageNormal(this), new DevilSageSkill(this), new DevilSageUltimate(this))));
        super.setOwnEffect(new ArrayList<>(Arrays.asList(new BrokenArmor(new Skeleton(), 99))));
    }

    public DevilSage(boolean isBot) {
        super(
                "",
                "Devil Sage",
                new Stats(320, 15, 13, 50, 25),
                new Image(Assets.getAsset("/assets/characters/enemy/devilSage/icon.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/devilSage/devilSage.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/devilSage/devilSageWithBg.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/devilSage/icon.png")),
                350,
                250
        );
        super.setActionList(new ArrayList<Action>(
                Arrays.asList(new DevilSageNormal(this), new DevilSageSkill(this), new DevilSageUltimate(this))));
        this.setBot(isBot);
        this.setActionPattern(new ArrayList<Integer>(Arrays.asList(0, 1, 0, 1, 1, 0, 2)));
        super.setOwnEffect(new ArrayList<>(Arrays.asList(new BrokenArmor(new Skeleton(), 99))));
    }
}