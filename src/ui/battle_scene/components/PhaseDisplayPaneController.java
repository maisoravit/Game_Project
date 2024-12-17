package ui.battle_scene.components;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import ui.common.BaseComponentController;

public class PhaseDisplayPaneController extends BaseComponentController {

    @FXML
    private Text turnDisplay;

    @FXML
    private Text phaseDisplay;

    @Override
    public void init() {
        turnDisplay.setText("Your Turn");
        turnDisplay.setTextAlignment(TextAlignment.CENTER);
        phaseDisplay.setText("Start phase");
        phaseDisplay.setTextAlignment(TextAlignment.CENTER);
    }

    public void setTurnDisplayText(String turn){
        turnDisplay.setText(turn);
    }

    public void setPhaseDisplayText(String phase){
        phaseDisplay.setText(phase);
    }
}
