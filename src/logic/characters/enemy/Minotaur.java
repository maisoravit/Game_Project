package logic.characters.enemy;

import javafx.scene.image.Image;
import logic.actions.Action;
import logic.actions.normals.ElveneNormal;
import logic.actions.normals.MinotaurNormal;
import logic.actions.skills.ElveneSkill;
import logic.actions.skills.MinotaurSkill;
import logic.actions.ultimates.ElveneUltimate;
import logic.actions.ultimates.MinotaurUltimate;
import logic.characters.Attacker;
import logic.characters.Stats;
import utils.Assets;

import java.util.ArrayList;
import java.util.Arrays;

public class Minotaur extends Attacker {
    private int rageLevel;
    public Minotaur() {
        super(
                "",
                "Minotaur",
                new Stats(260, 30, 22, 10, 8),
                new Image(Assets.getAsset("/assets/characters/enemy/minotaur/icon.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/minotaur/minotaur.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/minotaur/minotaurWithBg.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/minotaur/minotaurCloseUp.png")),
                400,
                300
        );
        super.setActionList(new ArrayList<Action>(
                Arrays.asList(new MinotaurNormal(this), new MinotaurSkill(this), new MinotaurUltimate(this)))
        );
        rageLevel = 0;
    }

    public Minotaur(boolean isBot) {
        super(
                "",
                "Minotaur",
                new Stats(260, 30, 22, 10, 8),
                new Image(Assets.getAsset("/assets/characters/enemy/minotaur/icon.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/minotaur/minotaur.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/minotaur/minotaurWithBg.png")),
                new Image(Assets.getAsset("/assets/characters/enemy/minotaur/minotaurCloseUp.png")),
                400,300
        );
        super.setActionList(new ArrayList<Action>(
                Arrays.asList(new MinotaurNormal(this), new MinotaurSkill(this), new MinotaurUltimate(this)))
        );
        rageLevel = 0;
        this.setBot(isBot);
        this.setActionPattern(new ArrayList<Integer>(Arrays.asList(1, 1, 0, 1, 1, 0, 2)));
    }

    public int getRageLevel() {
        return rageLevel;
    }

    public void setRageLevel(int rageLevel) {
        if(rageLevel < 0) rageLevel = 0;
        this.rageLevel = rageLevel;
    }
}