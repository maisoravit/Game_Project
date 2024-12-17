package ui.battle_scene.components;

import javafx.scene.Parent;
import ui.common.BaseComponent;
import utils.CustomLoader;

public class PhaseDisplayPane extends BaseComponent {
    public PhaseDisplayPane() {
        CustomLoader loader = new CustomLoader("PhaseDisplayPane.fxml");
        node = loader.load();
        controller = loader.getController();
        this.getChildren().add(node);
        this.setMaxWidth(400);
        this.setMaxHeight(100);
        this.setLayoutY(0);
        this.setLayoutX(600);

        controller.init();
    }

    public PhaseDisplayPaneController getController() {
        return (PhaseDisplayPaneController) controller;
    }
}
