package ui.battle_scene;


import javafx.scene.Parent;
import router.IAppScene;
import ui.common.BaseController;
import utils.CustomLoader;

/**
 * This class is the Battle scene of the game.
 */
public class BattleScene implements IAppScene {
    private Parent _node;
    private BaseController _controller;

    @Override
    public void init() throws Exception {
        CustomLoader loader = new CustomLoader("BattleScene.fxml");
        _node = loader.load();
        _controller = loader.getController();
        _controller.init();
    }

    @Override
    public Parent getNode() {
        return _node;
    }

    @Override
    public void start() {
        _controller.start();
    }
}
