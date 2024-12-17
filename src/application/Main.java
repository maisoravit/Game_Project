package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import logic.GameController;
import logic.levels.LevelManager;
import manager.SoundManager;
import org.w3c.dom.css.Rect;
import router.Router;

import java.awt.*;

/**
 * This is the main class of the application.
 * It is used to start the application.
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) {
        SoundManager.createInstance();
        LevelManager.createInstance();
        GameController.createInstance();
        Router.createInstance(stage);
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
