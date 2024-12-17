package ui.home;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import manager.SoundManager;
import router.Config;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import router.Router;
import ui.common.BaseController;
import utils.Assets;

/**
 * This class is the controller for the Home scene.
 */
public class HomeController implements BaseController {
    @FXML
    private Button startButton;

    @Override
    public void start() {
        SoundManager.getInstance().playBGM(SoundManager.BGM.MAIN_MENU);
    }

    @FXML
    public void buttonHandler() {
        Router.getInstance().push(Config.AppScene.MAIN_MENU);
    }
}
