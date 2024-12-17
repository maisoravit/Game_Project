package ui.battle_scene.components;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.characters.BaseCharacter;
import ui.common.BaseComponent;
import utils.CustomLoader;

import java.util.Optional;

/**
 * This class is the Card component of the battle scene.
 */
public class Card extends BaseComponent {
    public Card(BaseCharacter ... character) {
        CustomLoader loader = new CustomLoader("Card.fxml");
        node = loader.load(); // node will get add to the children of the stack pane this func return
        controller = loader.getController();
        setMaxHeight(150); // set max height to prevent the card from expanding (which will cause the incorrect alignment in parent nodes)
        getChildren().add(node);
        controller.setNode(this);
        controller.init();
        if (character.length > 0) {
            getController().setCharacter(character[0]);
        }
    }
    @Override
    public CardController getController() {
        return (CardController) controller;
    }
}
