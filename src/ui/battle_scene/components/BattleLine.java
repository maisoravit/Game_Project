package ui.battle_scene.components;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.team.Team;
import ui.battle_scene.components.Card;
import ui.common.BaseComponent;
import ui.common.BaseComponentController;

import java.util.ArrayList;

/**
 * This class is the BattleLine component of the battle scene.
 * It is a vertical line that contains cards.
 */
public class BattleLine extends BaseComponent {
    public BattleLine(Team.Line line) {
        setPrefWidth(100);
        setPrefHeight(600);
        setAlignment(Pos.CENTER);
        controller = new BattleLineController();
        controller.setNode(this);
        ((BattleLineController) controller).setLine(line);
        controller.init();
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        setBackground(Background.fill(new Color(1,1,1,0.6)));
    }

    @Override
    public BattleLineController getController() {
        return (BattleLineController) super.getController();
    }
}
