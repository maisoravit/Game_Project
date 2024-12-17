package router;

import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * This interface is used to define the structure of a scene in the game.
 * every scene in the game should implement this interface.
 */
public interface IAppScene {

    public void init() throws Exception;
    public default void start() {};
    public Parent getNode();
}
