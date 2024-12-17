package ui.battle_scene.components;

import javafx.scene.Parent;
import ui.common.BaseComponent;
import utils.CustomLoader;

public class PlayerControlBar extends BaseComponent {
    public PlayerControlBar(){
        CustomLoader loader = new CustomLoader("PlayerControlBar.fxml");
        node = loader.load();
        controller = loader.getController();
        this.getChildren().add(node);
        this.setMaxWidth(1600);
        this.setMaxHeight(200);
        this.setLayoutY(700);
        controller.setNode(this);
        controller.init();
    }

    public PlayerControlBarController getController() {
        return (PlayerControlBarController) controller;
    }
}
