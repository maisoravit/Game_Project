package ui.battle_scene.components;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import logic.GameController;
import logic.characters.BaseCharacter;
import logic.team.Team;
import ui.common.BaseComponentController;

import java.util.ArrayList;

/**
 * This class is the controller of the BattleLine component of the battle scene.
 * We keep the cards in a card_container.
 */
public class BattleLineController extends BaseComponentController {
    private ArrayList<Card> cards = new ArrayList<>();
    private StackPane card_container = new StackPane();
    private Team.Line line;
    private final int CARD_GAP = 240;
    @Override
    public void init() {
        card_container.setAlignment(Pos.TOP_CENTER);
        ((StackPane) getNode()).getChildren().add(card_container);
    }

    public void addCharacter(BaseCharacter character) {
        Card card = new Card(character);
        if (line == Team.Line.REAR || GameController.isEnemyTeam(character)) card.getController().setAllowDrag(false);
        addCard(card);
    }

    public void removeCharacter(BaseCharacter character) {
        removeCard(cards.stream().filter(card -> card.getController().getCharacter().equals(character)).findFirst().get());
    }

    public void clear() {
        cards.clear();
        card_container.getChildren().clear();
    }

    public void addCard(Card card) {
        cards.add(card);
        card_container.getChildren().removeAll(cards);

        for (Card c : cards) {
            if(cards.size() == 2){
                card_container.getChildren().add(c);
                c.setTranslateY((cards.indexOf(c)) * CARD_GAP - 80);
                if (GameController.isEnemyTeam(c.getController().getCharacter())) {
                    c.getController().invert();
                }
            } else {
                card_container.getChildren().add(c);
                c.setTranslateY((cards.indexOf(c)) * CARD_GAP);
                if (GameController.isEnemyTeam(c.getController().getCharacter())) {
                    c.getController().invert();
                }
            }
        }
        card_container.setMaxHeight(card.getMaxHeight() * cards.size());
    }

    public void removeCard(Card card) {
        card_container.getChildren().removeAll(cards);
        cards.remove(card);
        for (Card c : cards) {
            card_container.getChildren().add(c);
            c.setTranslateY((cards.indexOf(c)) * CARD_GAP);
        }
        card_container.setMaxHeight(card.getMaxHeight() * cards.size());
    }

    public boolean isContainThisCard(Card card) {
        return cards.contains(card);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

//    Mainly used for getting Team.Line when move card from one line to another
    public Team.Line getLine() {
        return line;
    }

    public void setLine(Team.Line line) {
        this.line = line;
    }
}
