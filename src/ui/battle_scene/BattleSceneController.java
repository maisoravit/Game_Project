package ui.battle_scene;

import config.Config;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import logic.GameController;
import logic.Phase;
import logic.actions.Action;
import logic.effects.Effect;
import manager.SoundManager;
import router.Router;
import ui.battle_scene.components.*;
import ui.common.PCamera;
import ui.common.BaseController;
import utils.PreDefEffect;

/**
 * This class is the controller for the Battle scene.
 */
public class BattleSceneController implements BaseController {
    @FXML
    private SubScene battleSubScene;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView battleBackground;

    @FXML
    private Button fightButton;

    @FXML
    private StackPane linesLabel;

    @FXML
    private StackPane gameEndPopUp;

    @FXML
    private Text gameEndText;

    @FXML
    private Button gameEndButton;

    @FXML
    private StackPane abandonPopUp;

    @FXML
    private Button abandonYesButton;

    @FXML
    private Button abandonNoButton;

    @FXML
    private Rectangle timerRect;

    @FXML
    private StackPane phaseDisplayPane;

    @FXML
    private StackPane endTurnPane;

    @FXML
    private Button abandonButton;

    private Phase nextPhase;

    private Pannel pannel;

    private PlayerControlBar playerControlBar;

    private NextButton currentNextButton;

    private CharacterControlBar characterControlBar;

    private ActionDetailPopUp actionDetailPopUp;

    private BattleBoard battleBoard;

    @Override
    public void start() {
        GameController.getInstance().initGame();

        battleBoard = new BattleBoard();
        StackPane _stackPane = new StackPane();

        _stackPane.getChildren().add(battleBackground);
        _stackPane.getChildren().add(battleBoard);
        _stackPane.setAlignment(Pos.CENTER);
        GameController.getInstance().setBattleBoard(battleBoard);
        battleSubScene.setRoot(_stackPane);

        setToManageTeamMode();
        GameController.getInstance().setBattleSceneController(this);
    }

    @Override
    public void init() {
        PCamera pCamera = new PCamera();
        battleSubScene.setVisible(true);
        battleSubScene.setCamera(pCamera);
        PreDefEffect.applyIdleAnimation(battleSubScene.getCamera(), battleSubScene);

        pannel = new Pannel();
        pannel.setTranslateX(Config.APP_WIDTH - pannel.getMaxWidth());
        anchorPane.getChildren().add(pannel);

        playerControlBar = new PlayerControlBar();
        anchorPane.getChildren().add(playerControlBar);

        actionDetailPopUp = new ActionDetailPopUp();
        actionDetailPopUp.setTranslateX(Config.APP_WIDTH - actionDetailPopUp.getMaxWidth() - 20);
        actionDetailPopUp.setTranslateY(Config.APP_HEIGHT - actionDetailPopUp.getMaxHeight() - playerControlBar.getMaxHeight() - 20);
        anchorPane.getChildren().add(actionDetailPopUp);

        PreDefEffect.applyAnimation2(fightButton);
        fightButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> SoundManager.getInstance().playSFX(SoundManager.SFX.HOVER_DEFAULT));

        endTurnPane.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> SoundManager.getInstance().playSFX(SoundManager.SFX.HOVER_DEFAULT));

        abandonButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> SoundManager.getInstance().playSFX(SoundManager.SFX.HOVER_DEFAULT));
        abandonYesButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> SoundManager.getInstance().playSFX(SoundManager.SFX.HOVER_DEFAULT));
        abandonNoButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> SoundManager.getInstance().playSFX(SoundManager.SFX.HOVER_DEFAULT));
    }

    public void handleAbandonButton() {
        SoundManager.getInstance().playSFX(SoundManager.SFX.CLICK_DEFAULT);
        abandonPopUp.setVisible(true);
    }

    public void handleAbandonYesButton() {
        handleGameEndButton();
    }

    public void handleAbandonNoButton(ActionEvent actionEvent) {
        SoundManager.getInstance().playSFX(SoundManager.SFX.CLICK_DEFAULT);
        abandonPopUp.setVisible(false);
    }

    public void fightButtonClicked() {
        pannel.getController().stopThread();
        GameController.getInstance().startPhase();
        setToBattleMode();
    }

    public void updatePhaseDisplay(String turn, String phase) {
        VBox vbox = (VBox) phaseDisplayPane.getChildren().get(1);
        ((Text) vbox.getChildren().get(0)).setText(turn);
        ((Text) vbox.getChildren().get(1)).setText(phase);
    }

    public void setEndTurnButton(Phase phase) {
        nextPhase = phase;
        endTurnPane.setVisible(true);
    }
    
    public void handleEndTurnButton() {
        SoundManager.getInstance().playSFX(SoundManager.SFX.CLICK_DEFAULT);
        if(nextPhase == Phase.draw) {
                GameController.getInstance().drawPhase();
        } else if (nextPhase == Phase.action) {
                GameController.getInstance().actionPhase();
        } else if (nextPhase == Phase.enemyTurn) {
                GameController.getInstance().setCharactersColorToNormal();
                GameController.getInstance().getPlayerControlBarController().unsetSkillCard();
                GameController.getInstance().enemyTurn();
        } else {
            throw new RuntimeException("next button need config");
        }
        endTurnPane.setVisible(false);
    }

    public void removeCurrentNextButton() {
        endTurnPane.setVisible(false);
    }

    public void setToBattleMode() {
        GameController.getInstance().getBattleBoard().getController().getBatTeam1().getController().setSelectedCard(null);
        playerControlBar.setVisible(true);
        phaseDisplayPane.setVisible(true);
        pannel.setVisible(false);
        fightButton.setVisible(false);
        linesLabel.setVisible(false);
        gameEndPopUp.setVisible(false);
        abandonPopUp.setVisible(false);
        timerRect.setVisible(false);
    }

    public void setToManageTeamMode() {
        playerControlBar.setVisible(false);
        phaseDisplayPane.setVisible(false);
        pannel.setVisible(true);
        pannel.getController().startThread();
        fightButton.setVisible(true);
        linesLabel.setVisible(true);
        gameEndPopUp.setVisible(false);
        abandonPopUp.setVisible(false);
        timerRect.setVisible(false);
        phaseDisplayPane.setVisible(false);
        endTurnPane.setVisible(false);
    }

    public void setActionDetailPopUp(Action action) {
        this.actionDetailPopUp.getController().showActionDetail(action);
    }

    public void setActionDetailPopUp(Effect effect) {
        this.actionDetailPopUp.getController().showActionDetail(effect);
    }

    public void setGameEndPopUp(boolean isWin) {
        if (isWin) {
            gameEndText.setText("WIN!!");
            gameEndButton.setStyle("-fx-background-color: #79BC62");
        } else {
            gameEndText.setText("GAME OVER!!");
            gameEndButton.setStyle("-fx-background-color: #E04A40");
        }
        gameEndPopUp.setVisible(true);
    }

    public void handleGameEndButton() {
        SoundManager.getInstance().playSFX(SoundManager.SFX.CLICK_DEFAULT);
        GameController.getInstance().stopThread();
        Router.getInstance().push(router.Config.AppScene.MAIN_MENU);
    }

    public void setPercentTimerRect(double percent) {
        timerRect.setVisible(true);
        percent = Math.min(100, Math.max(0, Math.max(100 - percent, 0)));
        double targetWidth = Config.APP_WIDTH * percent / 100;

        Timeline timeline = new Timeline();

        // Add a KeyFrame for the start of animation
        timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO,
                new KeyValue(timerRect.widthProperty(), timerRect.getWidth())));

        // Add a KeyFrame for the end of animation with ease in out effect
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), // Duration of animation
                    new KeyValue(timerRect.widthProperty(), targetWidth, Interpolator.EASE_BOTH)));

        timeline.play();
    }

    public PlayerControlBar getPlayerControlBar() {
        return playerControlBar;
    }
    public PannelController getPannelController() { return pannel.getController(); }
}
