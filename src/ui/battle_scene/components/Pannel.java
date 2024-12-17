package ui.battle_scene.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import ui.common.BaseComponent;
import ui.common.BaseComponentController;
import utils.CustomLoader;

/**
 * This class is the (LEFT)Pannel component of the battle scene.
 */
public class Pannel extends BaseComponent {
    private Parent _node;
    private PannelController _controller;
    public Pannel() {
        CustomLoader loader = new CustomLoader("Pannel.fxml");
        _node = loader.load();
        _controller = loader.getController();
        this.getChildren().add(_node);
        this.setMaxWidth(850);
        this.setMaxHeight(900);

        _controller.setNode(this);
        _controller.init();
    }

    @Override
    public PannelController getController() { return (PannelController) _controller; }
}
