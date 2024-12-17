package logic.actions.ultimates;

import javafx.application.Platform;
import javafx.scene.image.Image;
import logic.GameController;
import logic.actions.ActionType;
import logic.actions.Target;
import logic.actions.UltimateAction;
import logic.characters.BaseCharacter;
import logic.characters.Stats;
import logic.effects.Effect;
import logic.effects.buffs.HalfLifeSacrifice;
import logic.effects.buffs.Purified;
import logic.interfaces.Buffable;
import logic.interfaces.Healable;
import logic.players.Player;
import utils.Assets;

public class LafyUltimate extends UltimateAction implements Buffable, Healable {
    public LafyUltimate(BaseCharacter character) {
        super(
                character,
                "Fairy of The World Tree",
                0,
                new Image(Assets.getAsset("/assets/actions/lafy/lafyUltimate.png")),
                Target.Friend
        );
        type = ActionType.Magical;
        this.spiritCost = 25;
    }

    @Override
    public void activate(BaseCharacter targetCharacter) {
        GameController.getInstance().getPlayerTeam().getMembers().forEach(character -> {
            heal(character, 15);

            giveBuff(character);
        });
    }

    @Override
    public String getDescription() {
        return "Heal everyone in our team by 15 HP. Then give everyone in our team a buff 'Purified' for 2 turns. " +
                "And if a buff 'Half-Life Sacrifice' is never used, then give the buff 'Half-Life Sacrifice' to everyone in our team except Lafy for 5 turns";
    }

    @Override
    public Effect giveBuff(BaseCharacter character) {
        boolean hasPurified = false;
        Purified purified = new Purified(character, 2);
        for(int i=0; i<character.getStatusEffect().size(); i++) {
            Effect effect = character.getStatusEffect().get(i);
            if(effect instanceof Purified) {
                purified = (Purified) effect;
                hasPurified = true;
            }
        }
        if(hasPurified) {
            purified.addDuration(2);
        } else {
            character.getStatusEffect().add(purified);
        }
        purified.activate();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                character.getCard().getController().setEffect(character.getStatusEffect());
            }
        });

        if(character != getUser() && !HalfLifeSacrifice.isUsed()) {
            boolean hasHalfLife = false;
            HalfLifeSacrifice halfLifeSacrifice = new HalfLifeSacrifice(character, 5, getUser());
            for (int i = 0; i < character.getStatusEffect().size(); i++) {
                Effect effect = character.getStatusEffect().get(i);
                if (effect instanceof HalfLifeSacrifice) {
                    halfLifeSacrifice = (HalfLifeSacrifice) effect;
                    hasHalfLife = true;
                }
            }
            if (hasHalfLife) {
                halfLifeSacrifice.addDuration(5);
            } else {
                character.getStatusEffect().add(halfLifeSacrifice);
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    character.getCard().getController().setEffect(character.getStatusEffect());
                }
            });
        }
        return purified;
    }

    @Override
    public void heal(BaseCharacter character, int amount) {
        Stats newStats = character.getStats();
        newStats.setHealth(newStats.getHealth() + amount);
        character.setStats(newStats);
        character.updateCharacterHp();
    }
}
