package ui.common;

import javafx.scene.*;
import javafx.scene.transform.Rotate;

/**
 * This class is the PCamera component of the battle scene.
 * Only used in the battle scene.
 * This camera is responsible for the perspective of the battle scene.
 * So we could feel the 3D effect.
 */
public class PCamera extends PerspectiveCamera {
    public Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
    public Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
    public Rotate zRotate = new Rotate(0, Rotate.Z_AXIS);

    public PCamera() {
        PerspectiveCamera _camera = new PerspectiveCamera(false);
        _camera.setFarClip(6000);
        _camera.setNearClip(0.01);
        _camera.getTransforms().addAll(xRotate, yRotate, zRotate);
    }
}

