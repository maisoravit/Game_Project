package ui.battle_scene.components;

import javafx.scene.layout.StackPane;
import logic.team.Team;
import ui.common.BaseComponent;
import ui.common.BaseComponentController;

/**
 * This class is the BattleTeam component of the battle scene.
 * BattleTeam contains 3 BattleLines.
 */
public class BattleTeam extends BaseComponent {

    BattleTeam(Team team) {
        controller = new BattleTeamController(team);
        controller.setNode(this);
        controller.init();
    }

    @Override
    public BattleTeamController getController() {
        return (BattleTeamController) super.getController();
    }
}
