package ui.battle_scene.components;

import javafx.scene.Parent;
import logic.Phase;
import ui.common.BaseComponent;
import utils.CustomLoader;

public class NextButton extends BaseComponent {
    public NextButton(Phase nextPhase) {
        CustomLoader loader = new CustomLoader("NextButton.fxml");
        node = loader.load();
        controller = loader.getController();
        this.getChildren().add(node);
        this.setMaxWidth(200);
        this.setMaxHeight(50);
        this.setLayoutY(100);
        this.setLayoutX(700);

        controller.setNode(this);
        ((NextButtonController) controller).init(nextPhase);
    }
}
