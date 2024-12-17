package logic.characters.isekai;

import javafx.scene.image.Image;
import logic.actions.Action;
import logic.actions.ActionType;
import logic.actions.normals.ReirinNormal;
import logic.actions.skills.ReirinSkill;
import logic.actions.ultimates.ReirinUltimate;
import logic.characters.Attacker;
import logic.characters.Stats;
import logic.characters.enemy.Skeleton;
import logic.effects.buffs.SoulBoost;
import logic.effects.debuffs.Scare;
import utils.Assets;

import java.util.ArrayList;
import java.util.Arrays;

public class Reirin extends Attacker {
    public Reirin() {
        super(
                "Kamiyu",
                "Reirin",
                new Stats(220, 12, 25, 28, 26),
                new Image(Assets.getAsset("/assets/characters/reirin/icon.png")),
                new Image(Assets.getAsset("/assets/characters/reirin/reirin.png")),
                new Image(Assets.getAsset("/assets/characters/reirin/reirinWithBg.png")),
                new Image(Assets.getAsset("/assets/characters/reirin/reirinCloseUp.png"))
        );
        super.setActionList(new ArrayList<Action>(Arrays.asList(new ReirinNormal(this), new ReirinSkill(this), new ReirinUltimate(this))));
        super.setOwnEffect(new ArrayList<>(Arrays.asList(new Scare(new Skeleton(), 99))));
    }
}
