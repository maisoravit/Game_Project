package ui.battle_scene.components;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.transform.Rotate;
import logic.team.Team;
import ui.common.BaseComponent;
import ui.common.BaseComponentController;

/**
 * This class is the BattleBoard component of the battle scene.
 */
public class BattleBoard extends BaseComponent {
    public Group group1 = new Group();
    public BattleBoard() {
        controller = new BattleBoardController();
        group1.getChildren().add(getController().getBatTeam1());
        group1.getChildren().add(getController().getBatTeam2());
        getController().getBatTeam2().setTranslateX(800);

        group1.setRotationAxis(Rotate.X_AXIS);
        group1.setRotate(-50);
        group1.setTranslateZ(10);

        getChildren().add(group1);
        controller.setNode(this);
        controller.init();
    }

    @Override
    public BattleBoardController getController() {
        return (BattleBoardController) super.getController();
    }
}
