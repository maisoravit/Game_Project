package utils;

import javafx.animation.*;
import javafx.scene.Camera;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import router.Config;
import router.IAppScene;

/**
 * This class is used to store predefined effects.
 */
public class PreDefEffect {
    private PreDefEffect() {
    }

    public static DropShadow borderGlow() {
        DropShadow borderGlow = new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setColor(javafx.scene.paint.Color.RED);
        borderGlow.setWidth(70);
        borderGlow.setHeight(70);
        return borderGlow;
    }

    public static void sceneFadeInTransition(IAppScene scene, CallBackFunc callBackFunc) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), scene.getNode());
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setOnFinished(event -> callBackFunc.call());
        fadeTransition.play();
    }

    public static void sceneFadeOutTransition(IAppScene scene, CallBackFunc callBackFunc) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), scene.getNode());
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(event -> callBackFunc.call());
        fadeTransition.play();
    }

    public static void applyCharacterAnimation(ImageView character) {
        character.setRotate(0);
        // Wiggle Animation
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2), character);
        rotateTransition.setToAngle(5);
        rotateTransition.setCycleCount(Timeline.INDEFINITE);
        rotateTransition.setAutoReverse(true);

        // Glowing Animation
        Glow glow = new Glow(0.5);
        character.setEffect(glow);

        Timeline pulseAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(glow.levelProperty(), 0.0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(glow.levelProperty(), 0.3)),
                new KeyFrame(Duration.seconds(2), new KeyValue(glow.levelProperty(), 0.0))
        );
        pulseAnimation.setCycleCount(Timeline.INDEFINITE);

        // Parallel Transition
        ParallelTransition idleAnimation = new ParallelTransition();
        idleAnimation.getChildren().addAll(rotateTransition, pulseAnimation);

        idleAnimation.play();
    }

    public static void applyAnimation1(Node node) {
        Glow glow = new Glow(0.0);
        node.setEffect(glow);

        ScaleTransition scaleTransitionEnter = new ScaleTransition(Duration.millis(200), node);
        scaleTransitionEnter.setToX(1.2);
        scaleTransitionEnter.setToY(1.2);

        ScaleTransition scaleTransitionExit = new ScaleTransition(Duration.millis(200), node);
        scaleTransitionExit.setToX(1.0);
        scaleTransitionExit.setToY(1.0);

        double initialAngle = 5;

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(1000), node);
        rotateTransition.setFromAngle(-initialAngle);
        rotateTransition.setToAngle(initialAngle);
        rotateTransition.setCycleCount(Timeline.INDEFINITE);
        rotateTransition.setAutoReverse(true);

        Timeline pulseAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(glow.levelProperty(), 0.0)),
                new KeyFrame(Duration.seconds(1.5), new KeyValue(glow.levelProperty(), 0.6)),
                new KeyFrame(Duration.seconds(2), new KeyValue(glow.levelProperty(), 0.0))
        );
        pulseAnimation.setCycleCount(Timeline.INDEFINITE);

        TranslateTransition floatTransition = new TranslateTransition(Duration.millis(1000), node);
        floatTransition.setFromY(0);
        floatTransition.setToY(-5);
        floatTransition.setCycleCount(Timeline.INDEFINITE);
        floatTransition.setAutoReverse(true);

        pulseAnimation.play();
        floatTransition.play();

        node.setOnMouseEntered(event -> {
            // Start scale up animation
            scaleTransitionEnter.play();
            // Increase glow effect
            glow.setLevel(0.5);
            rotateTransition.play();
            pulseAnimation.stop();
            floatTransition.stop();
        });

        node.setOnMouseExited(event -> {
            // Start scale down animation
            scaleTransitionExit.play();
            // Reset glow effect
            glow.setLevel(0.0);
            rotateTransition.stop();
            node.setRotate(0);
            pulseAnimation.play();
            floatTransition.play();
        });
    }

    public static void applyAnimation2(Node node) {
        ScaleTransition scaleTransitionEnter = new ScaleTransition(Duration.millis(200), node);
        scaleTransitionEnter.setToX(1.2);
        scaleTransitionEnter.setToY(1.2);

        ScaleTransition scaleTransitionExit = new ScaleTransition(Duration.millis(200), node);
        scaleTransitionExit.setToX(1.0);
        scaleTransitionExit.setToY(1.0);

        node.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            scaleTransitionEnter.play();
        });

        node.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            scaleTransitionExit.play();
        });
    }

    public static void applyAnimation3(Node node) {
        ScaleTransition scale1 = new ScaleTransition(Duration.millis(10), node);
        scale1.setToX(1.0);
        scale1.setToY(1.0);
        scale1.setInterpolator(Interpolator.EASE_OUT);
        scale1.setDelay(Duration.millis(200));

        ScaleTransition scale2 = new ScaleTransition(Duration.millis(10), node);
        scale2.setToX(1.01);
        scale2.setToY(1.01);
        scale2.setInterpolator(Interpolator.EASE_OUT);
        scale2.setDelay(Duration.millis(200));

        ScaleTransition scale3 = new ScaleTransition(Duration.millis(10), node);
        scale3.setToX(1.02);
        scale3.setToY(1.02);
        scale3.setInterpolator(Interpolator.EASE_OUT);
        scale3.setDelay(Duration.millis(200));

        ScaleTransition scale4 = new ScaleTransition(Duration.millis(10), node);
        scale4.setToX(1.03);
        scale4.setToY(1.03);
        scale4.setInterpolator(Interpolator.EASE_OUT);
        scale4.setDelay(Duration.millis(200));

        SequentialTransition sequentialTransition = new SequentialTransition(scale1, scale2, scale3, scale4);
        sequentialTransition.setCycleCount(Timeline.INDEFINITE);
        sequentialTransition.setDelay(Duration.millis(200));
        sequentialTransition.play();
    }

    public static void applyIdleAnimation(Camera camera, SubScene subScene) {
//        camera.setTranslateZ(100);
//        TranslateTransition translate = new TranslateTransition(Duration.seconds(1), camera);
//        translate.setCycleCount(1);
//        translate.setInterpolator(Interpolator.EASE_BOTH);
//        subScene.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
//            translate.setToX(event.getSceneX() / 30);
//            translate.setToY(event.getSceneY() / 30);
//            translate.play();
//        });
    }
}


