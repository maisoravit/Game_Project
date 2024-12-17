package logic.characters.isekai;

import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.Action;
import logic.actions.ActionType;
import logic.actions.UltimateAction;
import logic.actions.normals.ElveneNormal;
import logic.actions.skills.ElveneSkill;
import logic.actions.ultimates.ElveneUltimate;
import logic.characters.Attacker;
import logic.characters.Stats;
import utils.Assets;

import java.util.ArrayList;
import java.util.Arrays;

public class Elvene extends Attacker {
    public Elvene() {
        super(
                "Aranis",
                "Elvene",
                new Stats(190, 40, 20, 20, 15),
                new Image(Assets.getAsset("/assets/characters/elvene/icon.png")),
                new Image(Assets.getAsset("/assets/characters/elvene/elvene.png")),
                new Image(Assets.getAsset("/assets/characters/elvene/elveneWithBg.png")),
                new Image(Assets.getAsset("/assets/characters/elvene/elveneCloseUp.png"))
        );
        super.setActionList(new ArrayList<Action>(Arrays.asList(new ElveneNormal(this), new ElveneSkill(this), new ElveneUltimate(this))));
    }
}
