package logic.characters.enemy;

import javafx.scene.image.Image;
import logic.actions.Action;
import logic.actions.normals.ElveneNormal;
import logic.actions.normals.CyclopsNormal;
import logic.actions.skills.ElveneSkill;
import logic.actions.skills.CyclopsSkill;
import logic.actions.ultimates.CyclopsUltimate;
import logic.actions.ultimates.ElveneUltimate;
import logic.characters.Attacker;
import logic.characters.Stats;
import logic.characters.Support;
import utils.Assets;

import java.util.ArrayList;
import java.util.Arrays;

public class Cyclops extends Support {
    public Cyclops() {
        super(
                "",
                "Cyclops",
                new Stats(240, 25, 18, 0, 0),
                new Image(Assets.getAsset("/assets/characters/enemy/cyclops/icon.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/cyclops/cyclops.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/cyclops/cyclopsWithBg.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/cyclops/icon.png"))
                );
        super.setActionList(new ArrayList<Action>(
                Arrays.asList(new CyclopsNormal(this), new CyclopsSkill(this), new CyclopsUltimate(this))));
    }

    public Cyclops(boolean isBot) {
        super(
                "",
                "Cyclops",
                new Stats(240, 25, 18, 0, 0),
                new Image(Assets.getAsset("/assets/characters/enemy/cyclops/icon.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/cyclops/cyclops.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/cyclops/cyclopsWithBg.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/cyclops/icon.png"))
        );
        super.setActionList(new ArrayList<Action>(
                Arrays.asList(new CyclopsNormal(this), new CyclopsSkill(this), new CyclopsUltimate(this))));
        this.setBot(isBot);
        this.setActionPattern(new ArrayList<Integer>(Arrays.asList(0, 1, 0, 1, 1, 0, 2)));
    }
}
