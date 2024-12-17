package ui.battle_scene.components;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import logic.GameController;
import logic.Phase;
import logic.actions.UltimateAction;
import logic.characters.BaseCharacter;
import manager.SoundManager;
import ui.common.BaseComponentController;
import ui.common.CharacterWrapper;
import ui.common.Selectable;
import utils.Assets;
import utils.CustomCursor;
import utils.PreDefEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * This class is the controller of the Card component of the battle scene.
 * Responsible for the drag&drop, hover, selected&deselected
 */
public class CardController extends BaseComponentController implements Selectable, CharacterWrapper {
    @FXML
    private Rectangle bg_rect;
    @FXML
    private ImageView cardImage;
    @FXML
    private StackPane cardPane;
    @FXML
    private StackPane hpBar;
    @FXML
    private Text hpText;
    @FXML
    private ImageView hit;
    @FXML
    private ImageView shield;
    @FXML
    private ImageView effect1;
    @FXML
    private ImageView effect2;
    @FXML
    private ImageView effect3;
    @FXML
    private ImageView effect4;

    private BaseCharacter character;
    private ArrayList<ImageView> cardStatusEffects;
    private double cardStartX, cardStartY, cardStartZ;
    private boolean isDragged = false;
    private boolean allowDrag = true;

    @Override
    public void init() {
        hit.setVisible(false);
        shield.setVisible(false);
        cardStatusEffects = new ArrayList<>();
        cardStatusEffects.addAll(Arrays.asList(effect1, effect2, effect3, effect4));
        // this make pickup base on geometric shape of this node
        getNode().setPickOnBounds(false);
        //if(character.getShield()>0) shield.setVisible(true);
        getNode().setCursor(Cursor.OPEN_HAND);
        getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
//            BattleBoard.setSelectedCard(getNode());
            getNode().setCursor(Cursor.CLOSED_HAND);
            cardStartX = getNode().getTranslateX();
            cardStartY = getNode().getTranslateY();
            cardStartZ = getNode().getTranslateZ();
            // this will bring the card to the front
            // we also have to bring its parent to the front So it is above all other nodes
            getNode().toFront();
            Parent pr = getNode().getParent();
            while (pr != null && !(pr instanceof BattleLine)) pr = pr.getParent();
            if (pr != null) {
                BattleLine line = (BattleLine) pr;
                line.toFront();
                line.getParent().toFront();
            }
        });
        getNode().addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            if (!allowDrag) {
                getNode().setCursor(CustomCursor.UNAVAILABLE);
                return;
            }
            getNode().setCursor(Cursor.CLOSED_HAND);
            Point2D p = new Point2D(e.getX(), e.getY());
            p = getNode().localToParent(p);

            getNode().setTranslateX(p.getX() - getNode().getWidth() / 2);
            getNode().setTranslateY(p.getY() - getNode().getHeight() / 2);
            isDragged = true;
        });
        getNode().addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            if (isDragged) {
                getNode().setCursor(Cursor.OPEN_HAND);
                Parent _team = getNode().getParent();
                Parent _line = getNode().getParent();
                while (_team != null && !(_team instanceof BattleTeam)) _team = _team.getParent();
                while (_line != null && !(_line instanceof BattleLine)) _line = _line.getParent();
                if (_team == null || _line == null) return;

                // this will return us the node that the card is dragged to.
                // "localToParent" is used to convert the point to the parent's coordinate system
                // "parentToLocal" is used to convert the point to the node's coordinate system
                // we need to juggles between these two methods to get the correct point
                Optional<Node> node = ((BattleTeam) _team).getChildren().stream().filter(n -> {
                    if (n instanceof BattleLine) {
                        if (((BattleLine) n).getController().isContainThisCard(getNode())) {
                            return false;
                        } else {
                            Point2D p = new Point2D(e.getX(), e.getY());
                            Parent pr = getNode();
                            do {
                                if (pr == null) {
                                    System.out.println("parent is null (should never happen");
                                    return false;
                                }
                                p = pr.localToParent(p);
                                pr = pr.getParent();
                            } while (!(pr instanceof BattleTeam));
                            p = n.parentToLocal(p);
                            return n.contains(p);
                        }
                    }
                    return false;
                }).findAny();

                // if the node is empty or the node is not a BattleLine, then we won't move the card
                if ((node.isEmpty()) || !(node.get() instanceof BattleLine)) {
                    getNode().setTranslateX(cardStartX);
                    getNode().setTranslateY(cardStartY);
                    getNode().setTranslateZ(cardStartZ);
                    return;
                }

                getNode().setTranslateX(0);
                getNode().setTranslateY(0);
                getNode().setTranslateZ(0);

                ((BattleTeam) _team).getController()
                        .moveCharacterToLine(
                                getCharacter(),
                                ((BattleLine) node.get()).getController().getLine()
                        );
                isDragged = false;
            }
        });
        // Hover effect
        getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            bg_rect.getStyleClass().add("hovered");
        });
        getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            bg_rect.getStyleClass().remove("hovered");
        });
        getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (character.isTargetable() && GameController.getInstance().getCurrentPhase() == Phase.action) {
                GameController.getInstance().setCharactersColorToNormal();
                GameController.getInstance().getPlayerTeam().getMembers().forEach((character) -> character.setTargetable(false));
                GameController.getInstance().getEnemyTeam().getMembers().forEach((character) -> character.setTargetable(false));
                if (!GameController.getInstance().getCurrentAction().isFromSkillCard()) {
                    GameController.getInstance().getCurrentAction().getUser().setActionThisTurn(true);
                    GameController.getInstance().getCurrentAction().playEffectAndActivate(character);

                    GameController.getInstance().getPlayer().setCurrentMana(
                            GameController.getInstance().getPlayer().getCurrentMana()
                                    - GameController.getInstance().getCurrentAction().getManaCost()
                    );
                    if (GameController.getInstance().getCurrentAction() instanceof UltimateAction) {
                        GameController.getInstance().getPlayer().setCurrentSpirit(
                                GameController.getInstance().getPlayer().getCurrentSpirit()
                                        - GameController.getInstance().getCurrentAction().getSpiritCost()
                        );
                    }
                    GameController.getInstance().getPlayer().setCurrentSpirit(
                            GameController.getInstance().getPlayer().getCurrentSpirit()
                                    + GameController.getInstance().getCurrentAction().getManaCost()
                    );

                } else {
                    GameController.getInstance().getCurrentAction().setFromSkillCard(false);
                    GameController.getInstance().getCurrentAction().playEffectAndActivate(character);
                    GameController.getInstance().getPlayerControlBarController().unsetSkillCard();
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
//                        GameController.getInstance().updateCardsHp();
//                        GameController.getInstance().updateAliveCharacter();
                        GameController.getInstance().getPlayerControlBarController().updateSpiritBar(GameController.getInstance().getPlayer().getCurrentSpirit());
                        GameController.getInstance().getPlayerControlBarController().updateManaBar(GameController.getInstance().getPlayer().getCurrentMana());
                    }
                });
            } else if (GameController.getInstance().getCurrentPhase() == Phase.manageTeam) {
                Parent _team = getNode().getParent();
                while (_team != null && !(_team instanceof BattleTeam)) _team = _team.getParent();
                if (_team instanceof BattleTeam && !GameController.isEnemyTeam((BattleTeam) _team)) {
                    ((BattleTeam) _team).getController().setSelectedCard(getNode());
                }
            } else if (GameController.getInstance().getCurrentPhase() != Phase.action) {
                System.out.println("You can only action in the action phase");
            } else if (!character.isTargetable()) {
                System.out.println("You can not attack this enemy!");
            }
            // show character detail
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
//                        GameController.getInstance().getPlayerControlBarController().getNode().getChildren().remove(
//                                GameController.getInstance().getPlayerControlBarController().getCharacterControlBar()
//                        );
                    GameController.getInstance().getPlayerControlBarController().hideCharacterControlBar();
                    GameController.getInstance().getPlayerControlBarController().showCharacterControlBar(character);
                }
            });
        });
    }

    public void fillBlack() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(-0.6);
        colorAdjust.setBrightness(-0.6);
        cardImage.setEffect(colorAdjust);
    }

    public void fillNormalColor() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(0);
        colorAdjust.setBrightness(0);
        cardImage.setEffect(colorAdjust);
    }

    @Override
    public Card getNode() {
        return (Card) super.getNode();
    }

    @Override
    public void setCharacter(BaseCharacter character) {
        this.character = character;
        cardImage.setImage(character.getImgSprite());
        if (character.isModifySpriteSize()) {
            cardImage.setFitHeight(character.getModifyHeight());
            cardImage.setFitWidth(character.getModifyWidth());
        }
    }

    public void updateCardHp() {
        hpBar.setPrefWidth((double) character.getStats().getHealth() / character.getStats().getMAX_HP() * 96);
        hpText.setText(character.getStats().getHealth() + "/" + character.getStats().getMAX_HP());
    }

    @Override
    public void select() {
        bg_rect.getStyleClass().add("selected");
    }

    @Override
    public void deselect() {
        bg_rect.getStyleClass().remove("selected");
    }

    @Override
    public BaseCharacter getCharacter() {
        return character;
    }

    public void setAllowDrag(boolean allowDrag) {
        this.allowDrag = allowDrag;
    }

    public void invert() {
        cardImage.setScaleX(-1);
    }

    public void setEffect(ArrayList<logic.effects.Effect> statusEffects) {
        for (int i = 0; i < 4; i++) {
            cardStatusEffects.get(i).setVisible(false);
        }
        for (int i = 0; i < statusEffects.size() && i < 4; i++) {
            cardStatusEffects.get(i).setImage(statusEffects.get(i).getIconImg());
            cardStatusEffects.get(i).setVisible(true);
        }
        if (GameController.getInstance().getPlayerControlBarController() != null && GameController.getInstance().getPlayerControlBarController().getCharacterControlBar() != null) {
            GameController.getInstance().getPlayerControlBarController().getCharacterControlBar().getController().setStatusEffects();
        }
    }

    public StackPane getHpBar() {
        return hpBar;
    }

    public void setHpBar(StackPane hpBar) {
        this.hpBar = hpBar;
    }

    public Text getHpText() {
        return hpText;
    }

    public void setHpText(Text hpText) {
        this.hpText = hpText;
    }

    public void setHit(boolean hit) {
        if (hit) {
            SoundManager.getInstance().playSFX("/sounds/hit.mp3", 1);
        }
        this.hit.setVisible(hit);
    }

    public ImageView getShield() {
        return shield;
    }

    public ImageView getCardImage() {
        return cardImage;
    }
}
