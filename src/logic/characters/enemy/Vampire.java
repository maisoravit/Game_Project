package logic.characters.enemy;

import javafx.scene.image.Image;
import logic.actions.Action;
import logic.actions.normals.ElveneNormal;
import logic.actions.normals.VampireNormal;
import logic.actions.skills.ElveneSkill;
import logic.actions.skills.VampireSkill;
import logic.actions.ultimates.ElveneUltimate;
import logic.actions.ultimates.VampireUltimate;
import logic.characters.Attacker;
import logic.characters.Stats;
import logic.characters.Support;
import logic.effects.buffs.SoulBoost;
import logic.effects.debuffs.SuckBlood;
import utils.Assets;

import java.util.ArrayList;
import java.util.Arrays;

public class Vampire extends Attacker {
    public Vampire() {
        super(
                "",
                "Vampire",
                new Stats(140, 20, 18, 40, 20),
                new Image(Assets.getAsset("/assets/characters/enemy/vampire/icon.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/vampire/vampire.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/vampire/vampireWithBg.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/vampire/vampireCloseUp.png"))
        );
        super.setActionList(new ArrayList<Action>(
                Arrays.asList(new VampireNormal(this), new VampireSkill(this), new VampireUltimate(this))));
        super.setOwnEffect(new ArrayList<>(Arrays.asList(new SuckBlood(new Skeleton(), 99, this))));
    }

    public Vampire(boolean isBot) {
        super(
                "",
                "Vampire",
                new Stats(140, 20, 18, 40, 20),
                new Image(Assets.getAsset("/assets/characters/enemy/vampire/icon.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/vampire/vampire.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/vampire/vampireWithBg.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/vampire/vampireCloseUp.png"))
        );
        super.setActionList(new ArrayList<Action>(
                Arrays.asList(new VampireNormal(this), new VampireSkill(this), new VampireUltimate(this))));
        this.setBot(isBot);
        this.setActionPattern(new ArrayList<Integer>(Arrays.asList(0, 0, 1, 0, 1, 2, 1)));
    }
}