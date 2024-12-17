package logic.characters.enemy;

import javafx.scene.image.Image;
import logic.actions.Action;
import logic.actions.normals.GolemNormal;
import logic.actions.skills.GolemSkill;
import logic.actions.ultimates.GolemUltimate;
import logic.characters.Attacker;
import logic.characters.Stats;
import utils.Assets;

import java.util.ArrayList;
import java.util.Arrays;

public class Golem extends Attacker {
    public Golem() {
        super(
                "",
                "Golem",
                new Stats(405, 18, 26, 0, 10),
                new Image(Assets.getAsset("/assets/characters/enemy/golem/icon.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/golem/golem.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/golem/golemWithBg.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/golem/icon.png")),
                420,
                325
        );
        super.setActionList(new ArrayList<Action>(
                Arrays.asList(new GolemNormal(this), new GolemSkill(this), new GolemUltimate(this))));
    }

    public Golem(boolean isBot) {
        super(
                "",
                "Golem",
                new Stats(405, 18, 26, 0, 10),
                new Image(Assets.getAsset("/assets/characters/enemy/golem/icon.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/golem/golem.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/golem/golemWithBg.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/golem/icon.png")),
                420,
                325
        );
        super.setActionList(new ArrayList<Action>(
                Arrays.asList(new GolemNormal(this), new GolemSkill(this), new GolemUltimate(this))));
        this.setBot(isBot);
        this.setActionPattern(new ArrayList<Integer>(Arrays.asList(0, 0, 1, 0, 0, 1, 2)));
    }
}
