package ui.battle_scene.components;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import logic.GameController;
import logic.actions.Action;
import logic.actions.NormalAction;
import logic.actions.UltimateAction;
import logic.characters.Attacker;
import logic.characters.BaseCharacter;
import logic.characters.CharacterRegistry;
import manager.SoundManager;
import ui.common.BaseComponentController;
import utils.PreDefEffect;

import java.util.ArrayList;

/**
 * This class is the controller of the (LEFT)Pannel component of the battle scene.
 * Responsible for player's team management.
 */
public class PannelController extends BaseComponentController {
    @FXML
    private HBox attackerBox;

    @FXML
    private HBox supporterBox;

    @FXML
    private StackPane characterDetailPane;

    @FXML
    private Rectangle detailRect;

    @FXML
    private Text detailName;

    @FXML
    private Text detailRole;

    @FXML
    private Text detailHP;

    @FXML
    private Text detailDEF;

    @FXML
    private Text detailMEF;

    @FXML
    private Text detailSpiritCost;

    @FXML
    private Text detailATK;

    @FXML
    private Text detailMAT;

    @FXML
    private VBox actionsDetailBox;

    @FXML
    private StackPane selectErrorPane;

    private Shape selectedCharacter;

    private Thread thread;
    private boolean isRunning;

    @FXML
    public Button selectButton;

    @Override
    public void init() {
        characterDetailPane.setVisible(false);
        selectErrorPane.setVisible(false);
        startThread();
        ArrayList<BaseCharacter> characters = CharacterRegistry.getCharactersList();
        characters.forEach(character -> {
            Rectangle characterFrame = new Rectangle(120, 120);
            characterFrame.setArcHeight(20);
            characterFrame.setArcWidth(20);
            characterFrame.setFill(new ImagePattern(character.getImgIcon()));
            characterFrame.getProperties().put("character", character);

            if (character instanceof Attacker) {
                attackerBox.getChildren().add(characterFrame);
            } else {
                supporterBox.getChildren().add(characterFrame);
            }

            characterFrame.setCursor(Cursor.HAND);
            characterFrame.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                e.consume();
                setSelectedCharacter(characterFrame);
                SoundManager.getInstance().playSFX(SoundManager.SFX.CLICK_LEVEL);
            });
            characterFrame.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> SoundManager.getInstance().playSFX(SoundManager.SFX.HOVER_LEVEL));
            PreDefEffect.applyAnimation1(characterFrame);
        });
            }

    public void handleSelectButton() {
        SoundManager.getInstance().playSFX(SoundManager.SFX.CLICK_DEFAULT);
        selectErrorPane.setVisible(false);
        Card selectedCard = GameController.getInstance().getBattleBoard().getController().getBatTeam1().getController().getSelectedCard();
        if (selectedCharacter != null && selectedCard != null) {
            GameController.getInstance().getPlayerTeam().changeCharacterTo(
                    selectedCard.getController().getCharacter(),
                    ((BaseCharacter) selectedCharacter.getProperties().get("character")).clone()
            );
        } else {
            setSelectErrorPane("⚠ Please Select Character");
        }
    }

    public void setSelectedCharacter(Shape selectedCharacter) {
        if (this.selectedCharacter != null) {
            this.selectedCharacter.setEffect(null);
        }
        this.selectedCharacter = selectedCharacter;
        if (this.selectedCharacter == null) {
            this.characterDetailPane.setVisible(false);
        } else {
            this.characterDetailPane.setVisible(true);
            this.selectedCharacter.setEffect(PreDefEffect.borderGlow());

            BaseCharacter _character = (BaseCharacter) this.selectedCharacter.getProperties().get("character");
            this.detailRect.setFill(new ImagePattern(_character.getImgWithBg()));
            this.detailName.setText(_character.getName());
            this.detailRole.setText("Role: " + (_character instanceof Attacker ? "Attacker" : "Supporter"));
            this.detailHP.setText("HP: " + _character.getStats().getHealth());
            this.detailDEF.setText("DEF: " + _character.getStats().getDefense());
            this.detailMEF.setText("MEF: " + _character.getStats().getMagicDef());
            this.detailSpiritCost.setText("Spirit Cost: null");
            this.detailATK.setText("ATK: " + _character.getStats().getAttack());
            this.detailMAT.setText("MAT: " + _character.getStats().getMagic());

            actionsDetailBox.getChildren().clear();
            _character.getActionList().forEach(action -> {
                actionsDetailBox.getChildren().add(actionDetailItem(action));
            });
//            _character.getStatusEffect().forEach(statusEffect -> {
//                actionsDetailBox.getChildren().add(actionDetailItem(statusEffect));
//            });
        }
    }

    public VBox actionDetailItem(Action action) {
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setFillWidth(true);

        GridPane gridPane = new GridPane();

        String actionType = (action instanceof UltimateAction ? "Ultimate" : action instanceof NormalAction ? "Normal" : "Skill");
        Text actionName = new Text(actionType + ": " + action.getName());
        actionName.getStyleClass().add("normal-text");
        gridPane.add(actionName, 0, 0);

        Text manaCost = new Text("Mana Cost: " + action.getManaCost());
        manaCost.getStyleClass().add("normal-text");
        gridPane.add(manaCost, 1, 0);
        GridPane.setHalignment(gridPane.getChildren().get(1), HPos.RIGHT);
        GridPane.setHgrow(gridPane.getChildren().get(1), Priority.ALWAYS);

        vBox.getChildren().add(gridPane);

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        Rectangle icon = new Rectangle(70, 70, new ImagePattern(action.getIconImg()));
        icon.setArcHeight(20);
        icon.setArcWidth(20);
        hBox.getChildren().add(icon);
        Text actionDescription = new Text(action.getDescription());
        actionDescription.getStyleClass().add("normal-text");
        actionDescription.setWrappingWidth(430);
        actionDescription.setTextAlignment(TextAlignment.JUSTIFY);
        hBox.getChildren().add(actionDescription);
        vBox.getChildren().add(hBox);
        return vBox;
    }

    public void setSelectErrorPane(String message) {
        ((Text) selectErrorPane.getChildren().get(1)).setText(message);
        selectErrorPane.setVisible(true);
    }

    public void stopThread() {
        isRunning = false;
    }

    public void startThread() {
        thread = new Thread(() -> {
            isRunning = true;
            while (true) {
                try {
                    Thread.sleep(10);
                    if (!isRunning) {
                        thread.interrupt();
                        break;
                    }
                } catch (InterruptedException ignored) {}
                try {
                    //        force re-rendering the background components without this the node will cause white background
                    //        (you can try to comment this line and see the result when selecting a character)
                    if (GameController.getInstance().getBattleBoard() != null) {
                        Platform.runLater(() -> {
                            GameController.getInstance().getBattleBoard().getController().getBatTeam2().setVisible(false);
                            GameController.getInstance().getBattleBoard().getController().getBatTeam2().setVisible(true);
                        });
                    }
                } catch (Exception ignored) {}
            }
        });
        thread.start();
    }
}
