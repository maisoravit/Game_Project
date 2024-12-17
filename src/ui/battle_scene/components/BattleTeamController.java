package ui.battle_scene.components;

import logic.GameController;
import logic.characters.BaseCharacter;
import logic.team.Team;
import ui.common.BaseComponentController;

import java.util.ArrayList;

/**
 * This class is the controller of the BattleTeam component of the battle scene.
 * BattleTeam contains 3 BattleLines.
 */
public class BattleTeamController extends BaseComponentController {
    private BattleLine battleLine1 = new BattleLine(Team.Line.REAR);
    private BattleLine battleLine2 = new BattleLine(Team.Line.MID);
    private BattleLine battleLine3 = new BattleLine(Team.Line.FRONT);
    private Team team;
    private Card selectedCard;

    public BattleTeamController(Team team) {
        this.team = team;
    }

    @Override
    public void init() {
        getNode().getChildren().add(battleLine1);
        getNode().getChildren().add(battleLine2);
        getNode().getChildren().add(battleLine3);

//      The line in Enemy team is reversed. (Front -> Mid -> Rear) instead of (Rear -> Mid -> Front)
        if (team != null && GameController.isEnemyTeam(team)) {
            battleLine1.setTranslateX(400);
            battleLine2.setTranslateX(200);
            battleLine3.setTranslateX(0);
        } else {
            battleLine1.setTranslateX(0);
            battleLine2.setTranslateX(200);
            battleLine3.setTranslateX(400);
        }
    }

    public void updateCard(BaseCharacter prev_character, BaseCharacter character) {
        battleLine1.getController().getCards().forEach(card -> {
            if (card.getController().getCharacter() == prev_character) {
                card.getController().setCharacter(character);
            }
        });
        battleLine2.getController().getCards().forEach(card -> {
            if (card.getController().getCharacter() == prev_character) {
                card.getController().setCharacter(character);
            }
        });
        battleLine3.getController().getCards().forEach(card -> {
            if (card.getController().getCharacter() == prev_character) {
                card.getController().setCharacter(character);
            }
        });
    }

    public void setTeam(Team team) {
        this.team = team;
        update(team);
    }

    private void update(Team team) {
        battleLine1.getController().clear();
        battleLine2.getController().clear();
        battleLine3.getController().clear();
        team.getRear().forEach(character -> battleLine1.getController().addCharacter(character));
        team.getMid().forEach(character -> battleLine2.getController().addCharacter(character));
        team.getFront().forEach(character -> battleLine3.getController().addCharacter(character));
        boolean isAnyTargetable = false;
        for (int i = 0; i < getAllCards().size(); i++) {
            if (getAllCards().get(i).getController().getCharacter().isTargetable()) {
                isAnyTargetable = true;
                break;
            }
        }
        if (isAnyTargetable) GameController.getInstance().showTargetCharacters();
        for (int i = 0; i < team.getMembers().size(); i++) {
            BaseCharacter character = team.getMembers().get(i);
            character.getCard().getController().updateCardHp();
            if (character.getShield() > 0) character.getCard().getController().getShield().setVisible(true);
            character.getCard().getController().setEffect(character.getStatusEffect());
        }
        for (int i = 0; i < team.getFront().size(); i++) {
            System.out.println(team.getFront().get(i).getName() + " is on Front");
        }
        for (int i = 0; i < team.getMid().size(); i++) {
            System.out.println(team.getMid().get(i).getName() + " is on Mid");
        }
        for (int i = 0; i < team.getRear().size(); i++) {
            System.out.println(team.getRear().get(i).getName() + " is on Rear");
        }
    }

    public void moveCharacterToLine(BaseCharacter character, Team.Line line) {
        team.moveCharacterToLine(character, line);
        update(team);
    }

    public BattleLine getBattleLine1() {
        return battleLine1;
    }

    public BattleLine getBattleLine2() {
        return battleLine2;
    }

    public BattleLine getBattleLine3() {
        return battleLine3;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(Card selectedCard) {
        if (this.selectedCard != null) {
            this.selectedCard.getController().deselect();
        }
        this.selectedCard = selectedCard;
        if (this.selectedCard != null) {
            this.selectedCard.getController().select();
        }
    }

    public ArrayList<Card> getAllCards() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.addAll(battleLine1.getController().getCards());
        cards.addAll(battleLine2.getController().getCards());
        cards.addAll(battleLine3.getController().getCards());
        return cards;
    }
}
