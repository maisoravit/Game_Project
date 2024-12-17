package ui.mainMenu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import logic.levels.Level;
import logic.levels.LevelManager;
import manager.SoundManager;
import router.Config;
import router.Router;
import ui.common.Animation;
import ui.common.BaseController;
import utils.Assets;
import utils.PreDefEffect;

import java.util.ArrayList;
import java.util.Arrays;

public class MainMenuController implements BaseController {
    @FXML
    private Circle level1Circle;
    @FXML
    private Circle level2Circle;
    @FXML
    private Circle level3Circle;
    @FXML
    private Circle level4Circle;
    @FXML
    private Circle level5Circle;
    @FXML
    private Circle level6Circle;
    @FXML
    private Button startButton;
    @FXML
    private ImageView characterImage;

    private Circle selectedCircle;
    private Animation characterAnimation;

    @Override
    public void init() {
        characterAnimation = new Animation(characterImage, 500);
        characterAnimation.addFrame(new Image(Assets.getAsset("/images/closeup/elveneCloseUp_big_normal_mouth.png")));
        characterAnimation.addFrame(new Image(Assets.getAsset("/images/closeup/elveneCloseUp_big_smile_mouth.png")));
        characterAnimation.addFrame(new Image(Assets.getAsset("/images/closeup/elveneCloseUp_close_mouth.png")));
        characterAnimation.addFrame(new Image(Assets.getAsset("/images/closeup/elveneCloseUp_medium_mouth.png")));
        characterAnimation.addFrame(new Image(Assets.getAsset("/images/closeup/elveneCloseUp_o_mouth.png")));
        ArrayList<Circle> circles = new ArrayList<>(
                Arrays.asList(
                        level1Circle,
                        level2Circle,
                        level3Circle,
                        level4Circle,
                        level5Circle,
                        level6Circle
                )
        );

        for (int i = 0; i < circles.size(); i++) {
            setCircleInfo(circles.get(i), i + 1);
            PreDefEffect.applyAnimation1(circles.get(i));
            circles.get(i).addEventHandler(MouseEvent.MOUSE_ENTERED, event -> SoundManager.getInstance().playSFX(SoundManager.SFX.HOVER_LEVEL));
            int finalI = i;
            circles.get(i).addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                SoundManager.getInstance().playSFX(SoundManager.SFX.CLICK_LEVEL);
                LevelManager.getInstance().setCurrentLevel(finalI + 1);
                selectCircle(circles.get(finalI));
            });
        }
        PreDefEffect.applyAnimation2(startButton);
        startButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> SoundManager.getInstance().playSFX(SoundManager.SFX.HOVER_DEFAULT));
    }

    @Override
    public void start() {
        SoundManager.getInstance().playBGM(SoundManager.BGM.MAIN_MENU);
        selectCircle(null);

        PreDefEffect.applyCharacterAnimation(characterImage);

        characterAnimation.start();
    }

    private void setCircleInfo(Circle circle, int level) {
        Level levelInfo = LevelManager.getInstance().getLevelInfo(level);
        circle.setFill(new ImagePattern(levelInfo.getImgIcon()));
    }

    private void selectCircle(Circle circle) {
        if (selectedCircle != null) {
            selectedCircle.getStyleClass().remove("selected");
        }
        if (circle == null) {
            selectedCircle = null;
            startButton.setDisable(true);
            return;
        }
        selectedCircle = circle;
        startButton.setDisable(false);
        selectedCircle.getStyleClass().add("selected");
    }

    public void goButtonHandler() {
        characterAnimation.stop();
        SoundManager.getInstance().playSFX(SoundManager.SFX.CLICK_DEFAULT);
        Router.getInstance().push(Config.AppScene.BATTLE);
    }
}
