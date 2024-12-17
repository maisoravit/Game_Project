package ui.battle_scene.components;

import javafx.scene.input.MouseEvent;
import logic.GameController;
import logic.Phase;
import ui.common.BaseComponentController;

public class NextButtonController extends BaseComponentController {

    public void init(Phase nextPhase) {
        if(nextPhase == Phase.draw) {
            getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                GameController.getInstance().drawPhase();
            });
        } else if (nextPhase == Phase.action) {
            getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                GameController.getInstance().actionPhase();
            });
        } else if (nextPhase == Phase.enemyTurn) {
            getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, e-> {
                GameController.getInstance().setCharactersColorToNormal();
                GameController.getInstance().getPlayerControlBarController().unsetSkillCard();
                GameController.getInstance().enemyTurn();
            });
        } else {
            throw new RuntimeException("next button need config");
        }
    }

    @Override
    public void init() {

    }

    @Override
    public NextButton getNode() {
        return (NextButton) super.getNode();
    }
}
