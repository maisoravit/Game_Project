package ui.battle_scene.components;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import logic.actions.Action;
import logic.actions.NormalAction;
import logic.actions.UltimateAction;
import logic.effects.Effect;
import logic.effects.buffs.Buff;
import logic.effects.debuffs.Debuff;
import ui.common.BaseComponentController;

/**
 * This class is the controller of the ActionDetailPopUp component of the battle scene.
 * Responsible for showing the detail of an action when click "Detail" button in CharacterControlBar.
 */
public class ActionDetailPopUpController extends BaseComponentController {
    @FXML
    private Rectangle detailRect;
    @FXML
    private Text detailName;
    @FXML
    private Text detailManaCost;
    @FXML
    private Text detailDescription;

    @Override
    public void init() {
    }

    public void showActionDetail(Action action) {
        String actionType = (action instanceof UltimateAction ? "Ultimate" : action instanceof NormalAction ? "Normal" : "Skill");
        detailName.setText(actionType + ": " + action.getName());
        if(action instanceof UltimateAction) {
            detailManaCost.setText("Spirit Cost: " + action.getSpiritCost());
        } else {
            detailManaCost.setText("Mana Cost: " + action.getManaCost());
        }
        detailRect.setFill(new ImagePattern(action.getIconImg()));
        detailDescription.setText(action.getDescription());
        getNode().setVisible(true);
    }

    public void showActionDetail(Effect effect) {
        String actionType = (effect instanceof Buff ? "Buff" : effect instanceof Debuff ? "De-Buff" : "Line-Buff");
        detailName.setText(actionType + ": " + effect.getName());
        detailManaCost.setText(null);
        detailRect.setFill(new ImagePattern(effect.getIconImg()));
        detailDescription.setText(effect.getDescription());
        getNode().setVisible(true);
    }

    public void hideActionDetail() {
        getNode().setVisible(false);
    }
}
