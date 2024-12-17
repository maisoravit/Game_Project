package ui.battle_scene.components;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import logic.GameController;
import logic.characters.BaseCharacter;
import logic.effects.Effect;
import ui.common.BaseComponentController;

import java.util.ArrayList;
import java.util.Arrays;

public class CharacterControlBarController extends BaseComponentController {

    @FXML
    private StackPane characterName;
    @FXML
    private ImageView characterImage;

    @FXML
    private StackPane HP;
    @FXML
    private StackPane stats;

    @FXML
    private StackPane normalPane;
    @FXML
    private ImageView normalImage;
    @FXML
    private Button useNormalBtn;
    @FXML
    private Button detailNormalBtn;
    @FXML
    private Text normalCost;

    @FXML
    private StackPane skillPane;
    @FXML
    private ImageView skillImage;
    @FXML
    private Button useSkillBtn;
    @FXML
    private Button detailSkillBtn;
    @FXML
    private Text skillCost;

    @FXML
    private StackPane ultimatePane;
    @FXML
    private ImageView ultimateImage;
    @FXML
    private Button useUltimateBtn;
    @FXML
    private Button detailUltimateBtn;
    @FXML
    private Text ultimateCost;

    @FXML
    private StackPane hpBar;
    @FXML
    private Text hpText;
    @FXML
    private Text name;
    @FXML
    private Text atk;
    @FXML
    private Text def;
    @FXML
    private Text mat;
    @FXML
    private Text mdf;

    @FXML
    private ImageView effect1;
    @FXML
    private ImageView effect2;
    @FXML
    private ImageView effect3;
    @FXML
    private ImageView effect4;
    @FXML
    private ImageView effect5;
    @FXML
    private ImageView effect6;
    @FXML
    private ImageView effect7;
    @FXML
    private ImageView effect8;

    private BaseCharacter character;
    private ArrayList<ImageView> statusEffectsImageView;

    private final EventHandler<ActionEvent> useActionHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            System.out.println("button clicked!");
            Button btn = (Button) actionEvent.getSource();
            if (GameController.getInstance().getCurrentAction() != null) {
                GameController.getInstance().getCurrentAction().setFromSkillCard(false);
                if (btn.getText().equals("CANCEL")) {
                    GameController.getInstance().setCurrentAction(null);
                    GameController.getInstance().setCharactersColorToNormal();
                    btn.setText("USE");
                    return;
                }
            }
            if (btn.getProperties().get("actionIndex") == null) {
                System.out.println("No action index found");
                return;
            }
            if (character.performAction(character.getActionList().get((int) btn.getProperties().get("actionIndex")))) {
                btn.setText("CANCEL");
            }
        }
    };

    public void init(BaseCharacter character) {
        this.character = character;
        characterImage.setImage(character.getImgCloseUp());
        setHpBar();
        setStats();
        normalCost.setText("Mana Cost: " + character.getActionList().get(0).getManaCost());
        skillCost.setText("Mana Cost: " + character.getActionList().get(1).getManaCost());
        ultimateCost.setText("Spirit Cost: " + character.getActionList().get(2).getSpiritCost());

        statusEffectsImageView = new ArrayList<ImageView>(Arrays.asList(
                effect1, effect2, effect3, effect4, effect5, effect6, effect7, effect8
        ));
        setStatusEffects();

        normalImage.setImage(character.getActionList().get(0).getIconImg());
        useNormalBtn.setOnAction(useActionHandler);
        useNormalBtn.getProperties().put("actionIndex", 0);
        detailNormalBtn.getProperties().put("actionIndex", 0);
        useNormalBtn.setDisable(character.isActionThisTurn() || character.isBot());

        skillImage.setImage(character.getActionList().get(1).getIconImg());
        useSkillBtn.setOnAction(useActionHandler);
        useSkillBtn.getProperties().put("actionIndex", 1);
        detailSkillBtn.getProperties().put("actionIndex", 1);
        useSkillBtn.setDisable(character.isActionThisTurn() || character.isBot());

        ultimateImage.setImage(character.getActionList().get(2).getIconImg());
        useUltimateBtn.setOnAction(useActionHandler);
        useUltimateBtn.getProperties().put("actionIndex", 2);
        detailUltimateBtn.getProperties().put("actionIndex", 2);
        useUltimateBtn.setDisable(character.isActionThisTurn() || character.isBot());
    }

    public void setHpBar() {
        hpBar.setPrefWidth((double) character.getStats().getHealth() / character.getStats().getMAX_HP() * 120);
        hpText.setText(character.getStats().getHealth() + "/" + character.getStats().getMAX_HP());
    }

    public void setStats() {
        name.setText(character.getName());
        atk.setText("ATK: " + character.getStats().getAttack());
        def.setText("DEF: " + character.getStats().getDefense());
        mat.setText("MAT: " + character.getStats().getMagic());
        mdf.setText("MDF: " + character.getStats().getMagicDef());
    }

    public void setStatusEffects() {
        for (int i = 0; i < 8; i++) {
            statusEffectsImageView.get(i).setVisible(false);
        }
        for (int i = 0; i < character.getStatusEffect().size(); i++) {
            Effect effect = character.getStatusEffect().get(i);
            statusEffectsImageView.get(i).setImage(effect.getIconImg());
            statusEffectsImageView.get(i).addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                GameController.getInstance().getBattleSceneController().setActionDetailPopUp(effect);
            });
            statusEffectsImageView.get(i).setVisible(true);
            statusEffectsImageView.get(i).setCursor(Cursor.HAND);
        }
    }

    public void detailButtonClicked(ActionEvent e) {
        System.out.printf("Detail button clicked! %s\n", e.getSource().toString());
        Button btn = (Button) e.getSource();
        if (btn.getProperties().get("actionIndex") == null) {
            System.out.println("No action index found");
            return;
        }
        GameController.getInstance().getBattleSceneController().setActionDetailPopUp(character.getActionList().get((int) btn.getProperties().get("actionIndex")));
    }

    public BaseCharacter getCharacter() {
        return character;
    }

    public void setCharacter(BaseCharacter character) {
        this.character = character;
    }

    @Override
    public void init() {

    }
}
