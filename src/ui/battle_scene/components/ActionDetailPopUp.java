package ui.battle_scene.components;

import ui.common.BaseComponent;
import utils.CustomLoader;

public class ActionDetailPopUp extends BaseComponent {
    public ActionDetailPopUp() {
        CustomLoader loader = new CustomLoader("ActionDetailPopUp.fxml");
        node = loader.load();
        controller = loader.getController();
        setMaxWidth(600);
        setMaxHeight(200);
        getChildren().add(node);

        controller.setNode(this);
        controller.init();
        getController().hideActionDetail();
    }

    @Override
    public ActionDetailPopUpController getController() {
        return (ActionDetailPopUpController) super.getController();
    }
}
