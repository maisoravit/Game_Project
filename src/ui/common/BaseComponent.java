package ui.common;

import javafx.scene.Parent;
import javafx.scene.layout.*;

/**
 * This class is the base class for all components in the UI.
 * It is a StackPane that wrap around a node that is loaded from .fxml file.
 */
public abstract class BaseComponent extends StackPane {
    protected Parent node; // this is the node that load from .fxml and should be added to the children of the StackPane
    protected BaseComponentController controller;

//    public abstract void init();

    public BaseComponentController getController() {
        return controller;
    }
}
