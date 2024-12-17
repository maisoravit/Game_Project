package ui.mainMenu;

import javafx.scene.Parent;
import router.IAppScene;
import ui.common.BaseController;
import utils.CustomLoader;

public class MainMenu implements IAppScene {
    private Parent _node;
    private BaseController _controller;
    @Override
    public void init() throws Exception {
        CustomLoader loader = new CustomLoader("MainMenu.fxml");
        _node = loader.load();
        _controller = loader.getController();
        _controller.init();
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
