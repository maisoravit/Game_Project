package ui.home;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import router.IAppScene;
import ui.common.BaseController;
import utils.CustomLoader;

/**
 * This class is the Home scene of the game.
 */
public class Home implements IAppScene {
    private Parent _node;
    private BaseController _controller;

    @Override
    public void init() {
        CustomLoader loader = new CustomLoader("Home.fxml");
        _node = loader.load();
        _controller = loader.getController();
//        _controller.start();
    }

    @Override
    public void start() {
        _controller.start();
    }

    @Override
    public Parent getNode() {
        return _node;
    }
}
