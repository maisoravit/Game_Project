package logic.characters.enemy;

import javafx.scene.image.Image;
import logic.actions.Action;
import logic.actions.normals.ElveneNormal;
import logic.actions.normals.DullahanNormal;
import logic.actions.skills.ElveneSkill;
import logic.actions.skills.DullahanSkill;
import logic.actions.ultimates.DullahanUltimate;
import logic.actions.ultimates.ElveneUltimate;
import logic.characters.Attacker;
import logic.characters.Stats;
import logic.characters.Support;
import logic.effects.buffs.SoulBoost;
import logic.effects.debuffs.BrokenArmor;
import utils.Assets;

import java.util.ArrayList;
import java.util.Arrays;

public class Dullahan extends Support {
    public Dullahan() {
        super(
                "",
                "Dullahan",
                new Stats(250, 21, 20, 40, 25),
                new Image(Assets.getAsset("/assets/characters/enemy/dullahan/icon.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/dullahan/dullahan.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/dullahan/dullahanWithBg.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/dullahan/icon.png")),
                400,
                280

        );
        super.setActionList(new ArrayList<Action>(
                Arrays.asList(new DullahanNormal(this), new DullahanSkill(this), new DullahanUltimate(this))));
        super.setOwnEffect(new ArrayList<>(Arrays.asList(new SoulBoost(new Skeleton(), 99))));
    }

    public Dullahan(boolean isBot) {
        super(
                "",
                "Dullahan",
                new Stats(250, 21, 20, 40, 25),
                new Image(Assets.getAsset("/assets/characters/enemy/dullahan/icon.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/dullahan/dullahan.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/dullahan/dullahanWithBg.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/dullahan/icon.png")),
                400,
                280
        );
        super.setActionList(new ArrayList<Action>(
                Arrays.asList(new DullahanNormal(this), new DullahanSkill(this), new DullahanUltimate(this))));
        this.setBot(isBot);
        this.setActionPattern(new ArrayList<Integer>(Arrays.asList(0, 1, 0, 1, 1, 0, 2)));
        super.setOwnEffect(new ArrayList<>(Arrays.asList(new SoulBoost(new Skeleton(), 99))));
    }
}