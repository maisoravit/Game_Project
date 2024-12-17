package ui.battle_scene.components;

import javafx.scene.Parent;
import logic.characters.BaseCharacter;
import ui.common.BaseComponent;
import utils.CustomLoader;

public class CharacterControlBar extends BaseComponent {

    public CharacterControlBar(BaseCharacter character) {
        CustomLoader loader = new CustomLoader("CharacterControlBar.fxml");
        node = loader.load();
        controller = loader.getController();
        this.getChildren().add(node);

        this.setMaxWidth(900);
        this.setMaxHeight(180);
        this.setLayoutX(700);
        this.setLayoutY(400);

        ((CharacterControlBarController) controller).init(character);
    }

    public CharacterControlBarController getController() {
        return (CharacterControlBarController) controller;
    }
}
