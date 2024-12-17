package logic.characters.isekai;

import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.Action;
import logic.actions.ActionType;
import logic.actions.normals.MewtenNormal;
import logic.actions.skills.MewtenSkill;
import logic.actions.ultimates.MewtenUltimate;
import logic.characters.Attacker;
import logic.characters.BaseCharacter;
import logic.characters.Stats;
import logic.characters.enemy.Skeleton;
import logic.effects.TriggerEvent;
import logic.effects.buffs.SoulBoost;
import logic.effects.debuffs.BrokenArmor;
import utils.Assets;

import java.util.ArrayList;
import java.util.Arrays;

public class Mewten extends Attacker {
    private boolean isPerfectGuard;
    public Mewten() {
        super(
                "Kitsuneko",
                "Mewten",
                new Stats(300, 28, 21, 2, 20),
                new Image(Assets.getAsset("/assets/characters/mewten/mewtenCloseUp.png")),
                new Image(Assets.getAsset("/assets/characters/mewten/mewten.png")),
                new Image(Assets.getAsset("/assets/characters/mewten/mewtenWithBg.png")),
                new Image(Assets.getAsset("/assets/characters/mewten/mewtenCloseUp.png"))
        );
        super.setActionList(new ArrayList<Action>(Arrays.asList(new MewtenNormal(this), new MewtenSkill(this), new MewtenUltimate(this))));
        super.setOwnEffect(new ArrayList<>(Arrays.asList(new BrokenArmor(new Skeleton(), 99))));
        isPerfectGuard = false;
    }

    public boolean isPerfectGuard() {
        return isPerfectGuard;
    }

    public void setPerfectGuard(boolean perfectGuard) {
        isPerfectGuard = perfectGuard;
    }

    @Override
    public void takeDamage(BaseCharacter attacker, int dmg, ActionType type) {
        Thread thread = new Thread(()->{
            System.out.println(name + " is attacked by " + attacker.getName());
            int damage = (int) (dmg * attacker.getDamageMultiplier());
            int defense = 0;
            if(isPerfectGuard) { // override code line
                damage = 0;
                setPerfectGuard(false);
            }
            if(type == ActionType.Magical) {
                defense = stats.getMagicDef();
            } else {
                defense = stats.getDefense();
            }
            damage -= defense;
            if(damage>0 && shield>0) {
                int shield = this.shield;
                setShield(shield - damage);
                damage -= shield;
            }
            if(damage>0) {
                Stats newStats = getStats();
                newStats.setHealth(newStats.getHealth() - damage);
                setStats(newStats);
            }

            getCard().getController().setHit(true);
            try {
                Thread.sleep(500);
            } catch (InterruptedException err) {
                System.out.println("error! take damage thread is interrupted");
                throw new RuntimeException(err);
            }
            getCard().getController().setHit(false);

            if(getStats().getHealth() <= 0) {
                GameController.getInstance().runEffectByEvent(TriggerEvent.HP_LTE_0, this);
            }
            updateCharacterHp();
            updateCheckAlive();
            System.out.println("HP remain " + stats.getHealth());
        });
        thread.start();
    };

    public void takeDamageDirectly(BaseCharacter attacker, int dmg, ActionType type) {
        Thread thread = new Thread(()->{
            System.out.println(name + " is directly attacked by " + attacker.getName());
            int damage = (int) (dmg * attacker.getDamageMultiplier());
            if(isPerfectGuard) { // override code line
                damage = 0;
                setPerfectGuard(false);
            }

            if(damage>0) {
                Stats newStats = getStats();
                newStats.setHealth(newStats.getHealth() - damage);
                setStats(newStats);
            }

            getCard().getController().setHit(true);
            try {
                Thread.sleep(500);
            } catch (InterruptedException err) {
                System.out.println("error! take damage thread is interrupted");
                throw new RuntimeException(err);
            }
            getCard().getController().setHit(false);

            if(getStats().getHealth() <= 0) {
                GameController.getInstance().runEffectByEvent(TriggerEvent.HP_LTE_0, this);
            }
            updateCharacterHp();
            updateCheckAlive();
            System.out.println("HP remain " + stats.getHealth());
        });
        thread.start();
    };
}
