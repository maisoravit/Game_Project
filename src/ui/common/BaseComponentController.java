package ui.common;

import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public abstract class BaseComponentController {
    private StackPane node;

    public abstract void init();

    public StackPane getNode() {
        return node;
    }

    public void setNode(StackPane node) {
        this.node = node;
    }
}
