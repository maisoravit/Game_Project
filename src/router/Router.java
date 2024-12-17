package router;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static router.Config.scenes;

import router.Config.AppScene;
import utils.Assets;
import utils.PreDefEffect;

/**
 * This class is the router of the application.
 * It is used to navigate between scenes in the application.
 */
public class Router {
    private static Router instance;
    private static Stage stage;
    private static Scene currentScene;
    private static AppScene currentAppScene;
    private static AppScene prevAppScene; // use for animate fade out

    private Router(Stage stage) {
        Router.stage = stage;

        for (IAppScene scene : scenes.values()) {
            try {
                scene.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        currentAppScene = Config.START_SCENE;
        currentScene = new Scene(scenes.get(currentAppScene).getNode(),
                config.Config.APP_WIDTH, config.Config.APP_HEIGHT);
        stage.setScene(currentScene);
        currentScene.getStylesheets().add(Assets.getAsset("/styles/global.css"));
        stage.setTitle(config.Config.APP_NAME);
        stage.setResizable(false);
        stage.show();
        Platform.runLater(() -> scenes.get(currentAppScene).start());
    }

    public static void createInstance(Stage stage) {
        if (instance == null) {
            instance = new Router(stage);
        }
    }

    public static Router getInstance() {
        return instance;
    }

    public void push(AppScene scene) {
        if (currentAppScene == scene) {
            return;
        }
        prevAppScene = currentAppScene;
        currentAppScene = scene;
        PreDefEffect.sceneFadeOutTransition(scenes.get(prevAppScene), () -> {
            scenes.get(currentAppScene).getNode().setOpacity(0);
            currentScene.setRoot(scenes.get(currentAppScene).getNode());
            Platform.runLater(() -> {
                        scenes.get(currentAppScene).start();
                        PreDefEffect.sceneFadeInTransition(scenes.get(currentAppScene), () -> {});
                    }
            );
        });
    }

    public static AppScene getCurrentAppScene() {
        return currentAppScene;
    }
}

