package logic.characters;

import javafx.application.Platform;
import javafx.scene.image.Image;
import logic.GameController;
import logic.Phase;
import logic.actions.ActionType;
import logic.actions.Target;
import logic.actions.UltimateAction;
import logic.effects.Effect;
import logic.actions.Action;
import logic.effects.TriggerEvent;
import ui.battle_scene.components.BattleBoard;
import ui.battle_scene.components.Card;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class is the base class for all characters in the game.
 * It contains the basic information of a character. (Name, Stats, Image)
 */
public abstract class BaseCharacter implements Cloneable {
    protected String name;
    protected String lastname;
    protected Stats stats;
    protected int shield;
    protected Image imgIcon;
    protected Image imgSprite;
    protected Image imgWithBg;
    protected Image imgCloseUp;
    protected ArrayList<Action> actionList;
    protected ArrayList<Effect> OwnEffect;
    protected ArrayList<Effect> statusEffect;
    protected boolean isActionThisTurn;
    protected boolean isTargetable;
    protected boolean isBot;
    protected ArrayList<Integer> ActionPattern;
    protected int actionPatternIndex = 0;
    protected double damageMultiplier;
    protected boolean isModifySpriteSize;
    protected double modifyHeight;
    protected double modifyWidth;

    public BaseCharacter(String lastname, String name, Stats stats, Image imgIcon, Image imgSprite, Image imgWithBg, Image imgCloseUp) {
        this.lastname = lastname;
        this.name = name;
        this.stats = stats;
        this.shield = 0;
        this.imgIcon = imgIcon;
        this.imgSprite = imgSprite;
        this.imgWithBg = imgWithBg;
        this.imgCloseUp = imgCloseUp;
        this.statusEffect = new ArrayList<>();
        this.damageMultiplier = 1;
        this.isActionThisTurn = false;
        this.isTargetable = false;
        this.isBot = false;
        this.isModifySpriteSize = false;
    }

    public BaseCharacter(String lastname, String name, Stats stats, Image imgIcon, Image imgSprite, Image imgWithBg, Image imgCloseUp, double modifyHeight, double modifyWidth) {
        this.lastname = lastname;
        this.name = name;
        this.stats = stats;
        this.shield = 0;
        this.imgIcon = imgIcon;
        this.imgSprite = imgSprite;
        this.imgWithBg = imgWithBg;
        this.imgCloseUp = imgCloseUp;
        this.statusEffect = new ArrayList<>();
        this.damageMultiplier = 1;
        this.isActionThisTurn = false;
        this.isTargetable = false;
        this.isBot = false;
        this.isModifySpriteSize = true;
        this.modifyHeight = modifyHeight;
        this.modifyWidth = modifyWidth;
    }

    @Override
    public BaseCharacter clone() {
        try {
            BaseCharacter clone = (BaseCharacter) super.clone();
            clone.stats = stats.clone();
            clone.actionList = new ArrayList<>();
            for (Action action : actionList) {
                Action cloneAction = action.clone();
                cloneAction.setUser(clone);
                clone.actionList.add(cloneAction);
            }
            clone.statusEffect = new ArrayList<>();
            for (Effect effect : statusEffect) {
                clone.statusEffect.add(effect.clone());
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

// return true mean can perform action, false mean cannot
    public boolean performAction(Action action) {
        System.out.println("perform action");
        System.out.printf("can afford: %b, is action this turn: %b, is bot: %b, current phase: %s\n", canAfford(action), isActionThisTurn(), isBot, GameController.getInstance().getCurrentPhase());
        // check cost for action & character does not action yet
        if((canAfford(action) && !isActionThisTurn() && !isBot && GameController.getInstance().getCurrentPhase() == Phase.action) || (isBot && !isActionThisTurn())){
            System.out.println("looking for target...");
            resetTarget();
            GameController.getInstance().setCharactersColorToNormal();
            getTarget(action);
            return true;
        }
        else{
            System.out.println("not enough cost or already done action");
            // handle later
            return false;
        }
    }

    public void takeDamage(BaseCharacter attacker, int dmg, ActionType type) {
        Thread thread = new Thread(()->{
            System.out.println(name + " is attacked by " + attacker.getName());
            int damage = (int) (dmg * attacker.getDamageMultiplier());
            int defense = 0;
            if(type == ActionType.Magical) {
                defense = stats.getMagicDef();
            } else {
                defense = stats.getDefense();
            }
            damage -= defense;
            if(damage>0 && shield>0) {
                int shield = this.shield;
                setShield(shield - damage);
                damage -= shield;
            }
            if(damage>0) {
                Stats newStats = getStats();
                newStats.setHealth(newStats.getHealth() - damage);
                setStats(newStats);
            }

            getCard().getController().setHit(true);
            try {
                Thread.sleep(500);
            } catch (InterruptedException err) {
                System.out.println("error! take damage thread is interrupted");
                throw new RuntimeException(err);
            }
            getCard().getController().setHit(false);

            if(getStats().getHealth() <= 0) {
                GameController.getInstance().runEffectByEvent(TriggerEvent.HP_LTE_0, this);
            }
            updateCharacterHp();
            updateCheckAlive();
            System.out.println("HP remain " + stats.getHealth());
        });
        thread.start();
    };

    public void takeDamageDirectly(BaseCharacter attacker, int dmg, ActionType type) {
        Thread thread = new Thread(()->{
            System.out.println(name + " is directly attacked by " + attacker.getName());
            int damage = (int) (dmg * attacker.getDamageMultiplier());

            if(damage>0) {
                Stats newStats = getStats();
                newStats.setHealth(newStats.getHealth() - damage);
                setStats(newStats);
            }

            getCard().getController().setHit(true);
            try {
                Thread.sleep(350);
            } catch (InterruptedException err) {
                System.out.println("error! take damage thread is interrupted");
                throw new RuntimeException(err);
            }
            getCard().getController().setHit(false);

            if(getStats().getHealth() <= 0) {
                GameController.getInstance().runEffectByEvent(TriggerEvent.HP_LTE_0, this);
            }
            updateCharacterHp();
            updateCheckAlive();
            System.out.println("HP remain " + stats.getHealth());
        });
        thread.start();
    };

    public boolean canAfford(Action action) {
        if(action instanceof UltimateAction
                && action.checkCost(GameController.getInstance().getPlayer().getCurrentMana(), action.getManaCost())
                && action.checkCost(GameController.getInstance().getPlayer().getCurrentSpirit(), action.getSpiritCost())){
            return true;
        }
        else if(!(action instanceof UltimateAction)
                && action.checkCost(GameController.getInstance().getPlayer().getCurrentMana(), action.getManaCost())){
            return true;
        }
        else{
            return false;
        }
    }

    public void getTarget(Action action) {
        Target targetType = action.getTargetType();
        if(targetType == Target.Enemy) getEnemy(action);
        else if(targetType == Target.AllEnemy) getAllEnemy(action);
        else if(targetType == Target.Friend) getFriend(action);
        else if(targetType == Target.Both) getBoth(action);
        else if(targetType == Target.Self) getSelf(action);
    }

    public void getEnemy(Action action){
        if(!isBot){
            if(!GameController.getInstance().getEnemyTeam().getFront().isEmpty()){
                GameController.getInstance().getEnemyTeam().getFront().forEach((character)->character.setTargetable(true));
            } else if (!GameController.getInstance().getEnemyTeam().getMid().isEmpty()) {
                GameController.getInstance().getEnemyTeam().getMid().forEach((character)->character.setTargetable(true));
            } else if (!GameController.getInstance().getEnemyTeam().getRear().isEmpty()) {
                GameController.getInstance().getEnemyTeam().getRear().forEach((character)->character.setTargetable(true));
            } else {
                System.out.println("error! There is no target");
                throw new RuntimeException("There is no target");
            }
            GameController.getInstance().setCurrentAction(action);
            GameController.getInstance().showTargetCharacters();
            System.out.println("found target!");
        } else {
            if(!GameController.getInstance().getPlayerTeam().getFront().isEmpty()){
                GameController.getInstance().getPlayerTeam().getFront().forEach((character)->character.setTargetable(true));
            } else if (!GameController.getInstance().getPlayerTeam().getMid().isEmpty()) {
                GameController.getInstance().getPlayerTeam().getMid().forEach((character)->character.setTargetable(true));
            } else if (!GameController.getInstance().getPlayerTeam().getRear().isEmpty()) {
                GameController.getInstance().getPlayerTeam().getRear().forEach((character)->character.setTargetable(true));
            } else {
                System.out.println("error! There is no target");
                throw new RuntimeException("There is no target");
            }
            GameController.getInstance().setCurrentAction(action);
            System.out.println("found target!");
            botActivateAction(action);
        }
    }

    public void getAllEnemy(Action action){
        if(!isBot) {
            GameController.getInstance().getBattleBoard().getController().getAllCards().forEach(card -> {
                if(GameController.isEnemyTeam(card.getController().getCharacter())) {
                    card.getController().getCharacter().setTargetable(true);
                }
            });
            GameController.getInstance().setCurrentAction(action);
            GameController.getInstance().showTargetCharacters();
            System.out.println("found target!");
        } else {
            GameController.getInstance().getBattleBoard().getController().getAllCards().forEach(card -> {
                if(!GameController.isEnemyTeam(card.getController().getCharacter())) {
                    card.getController().getCharacter().setTargetable(true);
                }
            });
            GameController.getInstance().setCurrentAction(action);
            System.out.println("found target!");
            botActivateAction(action);
        }
    }

    public void getFriend(Action action){
        if(!isBot) {
            GameController.getInstance().getBattleBoard().getController().getAllCards().forEach(card -> {
                if(!GameController.isEnemyTeam(card.getController().getCharacter())) {
                    card.getController().getCharacter().setTargetable(true);
                }
            });
            GameController.getInstance().setCurrentAction(action);
            GameController.getInstance().showTargetCharacters();
            System.out.println("found target!");
        } else {
            GameController.getInstance().getBattleBoard().getController().getAllCards().forEach(card -> {
                if(GameController.isEnemyTeam(card.getController().getCharacter())) {
                    card.getController().getCharacter().setTargetable(true);
                }
            });
            GameController.getInstance().setCurrentAction(action);
            System.out.println("found target!");
            botActivateAction(action);
        }
    }

    public void getBoth(Action action){
        if(!isBot) {
            // get enemy
            if(!GameController.getInstance().getEnemyTeam().getFront().isEmpty()){
                GameController.getInstance().getEnemyTeam().getFront().forEach((character)->character.setTargetable(true));
            } else if (!GameController.getInstance().getEnemyTeam().getMid().isEmpty()) {
                GameController.getInstance().getEnemyTeam().getMid().forEach((character)->character.setTargetable(true));
            } else if (!GameController.getInstance().getEnemyTeam().getRear().isEmpty()) {
                GameController.getInstance().getEnemyTeam().getRear().forEach((character)->character.setTargetable(true));
            } else {
                System.out.println("error! There is no target");
                throw new RuntimeException("There is no target");
            }
            // get friend
            GameController.getInstance().getBattleBoard().getController().getAllCards().forEach(card -> {
                if(!GameController.isEnemyTeam(card.getController().getCharacter())) {
                    card.getController().getCharacter().setTargetable(true);
                }
            });
            GameController.getInstance().setCurrentAction(action);
            GameController.getInstance().showTargetCharacters();
            System.out.println("found target!");
        } else {
            // bot get enemy (player)
            if(!GameController.getInstance().getPlayerTeam().getFront().isEmpty()){
                GameController.getInstance().getPlayerTeam().getFront().forEach((character)->character.setTargetable(true));
            } else if (!GameController.getInstance().getPlayerTeam().getMid().isEmpty()) {
                GameController.getInstance().getPlayerTeam().getMid().forEach((character)->character.setTargetable(true));
            } else if (!GameController.getInstance().getPlayerTeam().getRear().isEmpty()) {
                GameController.getInstance().getPlayerTeam().getRear().forEach((character)->character.setTargetable(true));
            } else {
                System.out.println("error! There is no target");
                throw new RuntimeException("There is no target");
            }
            // bot get friend
            GameController.getInstance().getBattleBoard().getController().getAllCards().forEach(card -> {
                if(GameController.isEnemyTeam(card.getController().getCharacter())) {
                    card.getController().getCharacter().setTargetable(true);
                }
            });
            GameController.getInstance().setCurrentAction(action);
            System.out.println("found target!");
            botActivateAction(action);
        }
    }

    public void getSelf(Action action){
        this.setTargetable(true);
        if(!isBot) {
            GameController.getInstance().setCurrentAction(action);
            GameController.getInstance().showTargetCharacters();
            System.out.println("found target!");
        } else {
            GameController.getInstance().setCurrentAction(action);
            System.out.println("found target!");
            botActivateAction(action);
        }
    }

    public void botActivateAction(Action action) {
        Random random = new Random();
        ArrayList<BaseCharacter> targets = new ArrayList<>();
        GameController.getInstance().getBattleBoard().getController().getAllCards().forEach(card -> {
            if(card.getController().getCharacter().isTargetable()) targets.add(card.getController().getCharacter());
        });
        BaseCharacter targetCharacter = targets.get(random.nextInt(targets.size()));
        GameController.getInstance().getPlayerTeam().getMembers().forEach((character)->character.setTargetable(false));
        GameController.getInstance().getEnemyTeam().getMembers().forEach((character)->character.setTargetable(false));
        setActionThisTurn(true);
        action.activate(targetCharacter);
    }

    public void resetTarget(){
        GameController.getInstance().getPlayerTeam().getMembers().forEach((character)->character.setTargetable(false));
        GameController.getInstance().getEnemyTeam().getMembers().forEach((character)->character.setTargetable(false));
    }

    public void updateCharacterHp() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getCard().getController().updateCardHp();
            }
        });
    }

    public void updateCheckAlive() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(!isAlive()) {
                    System.out.println("dead card!");
                    System.out.println(getName() + " down");

                    BattleBoard battleBoard = GameController.getInstance().getBattleBoard();
                    if(getCard()==null) {
                        System.out.println(getName() + " card is null when update check alive");
                    }
                    Card card = getCard();
                    battleBoard.getController().getBatTeam1().getController().getBattleLine1().getController().removeCard(card);
                    battleBoard.getController().getBatTeam1().getController().getBattleLine2().getController().removeCard(card);
                    battleBoard.getController().getBatTeam1().getController().getBattleLine3().getController().removeCard(card);
                    battleBoard.getController().getBatTeam2().getController().getBattleLine1().getController().removeCard(card);
                    battleBoard.getController().getBatTeam2().getController().getBattleLine2().getController().removeCard(card);
                    battleBoard.getController().getBatTeam2().getController().getBattleLine3().getController().removeCard(card);
                    System.out.println("remove card!");
                    GameController.getInstance().getPlayerTeam().removeCharacter(card.getController().getCharacter());
                    GameController.getInstance().getEnemyTeam().removeCharacter(card.getController().getCharacter());

                    battleBoard.getController().getBatTeam1().getController().setTeam(
                            GameController.getInstance().getPlayerTeam()
                    );
                    battleBoard.getController().getBatTeam2().getController().setTeam(
                            GameController.getInstance().getEnemyTeam()
                    );
                    if(GameController.getInstance().getPlayerTeam().getMembers().isEmpty()) {
                        GameController.getInstance().gameOver();
                    }
                    else if(GameController.getInstance().getEnemyTeam().getMembers().isEmpty()) {
                        GameController.getInstance().gameWin();
                    }
                }
                GameController.getInstance().setCharactersColorToNormal();
            }
        });

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        this.name = name;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        if (stats == null) {
            throw new IllegalArgumentException("Stats cannot be null.");
        }
        this.stats = stats;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GameController.getInstance().getPlayerControlBarController().getCharacterControlBar().getController().setHpBar();
                GameController.getInstance().getPlayerControlBarController().getCharacterControlBar().getController().setStats();
            }
        });
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        if(shield <= 0) {
            this.shield = 0;
            BaseCharacter character = this;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    getCard().getController().getShield().setVisible(false);
                }
            });
        } else {
            this.shield = shield;
            BaseCharacter character = this;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    getCard().getController().getShield().setVisible(true);
                }
            });
        }

    }

    public Image getImgIcon() {
        return imgIcon;
    }

    public void setImgIcon(Image imgIcon) {
        if (imgIcon == null) {
            throw new IllegalArgumentException("Image icon cannot be null.");
        }
        this.imgIcon = imgIcon;
    }

    public Image getImgSprite() {
        return imgSprite;
    }

    public void setImgSprite(Image imgSprite) {
        if (imgSprite == null) {
            throw new IllegalArgumentException("Image sprite cannot be null.");
        }
        this.imgSprite = imgSprite;
    }

    public Image getImgWithBg() {
        return imgWithBg;
    }

    public void setImgWithBg(Image img) {
        if (img == null) {
            throw new IllegalArgumentException("Image with background cannot be null.");
        }
        this.imgWithBg = img;
    }

    public Image getImgCloseUp() {
        return imgCloseUp;
    }

    public void setImgCloseUp(Image imgCloseUp) {
        if (imgCloseUp == null) {
            throw new IllegalArgumentException("Close up Image cannot be null.");
        }
        this.imgCloseUp = imgCloseUp;
    }

    public ArrayList<Action> getActionList() {
        return actionList;
    }

    public void setActionList(ArrayList<Action> actionList) {
        if (actionList.size() != 3) {
            throw new IllegalArgumentException("Character must has 3 action");
        }
        this.actionList = actionList;
    }

    public ArrayList<Effect> getStatusEffect() {
        return statusEffect;
    }

    public void setStatusEffect(ArrayList<Effect> statusEffect) {
        this.statusEffect = statusEffect;
    }

    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    public void setDamageMultiplier(double damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
    }

    public boolean isActionThisTurn() {
        return isActionThisTurn;
    }

    public void setActionThisTurn(boolean actionThisTurn) {
        isActionThisTurn = actionThisTurn;
        try {
            GameController.getInstance().getPlayerControlBarController().showCharacterControlBar(this);
        } catch (Exception ignored) {}
    }

    public boolean isAlive() {
        return this.getStats().getHealth() > 0;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isTargetable() {
        return isTargetable;
    }

    public void setTargetable(boolean targetable) {
        isTargetable = targetable;
    }

    public boolean isBot() {
        return isBot;
    }

    public void setBot(boolean bot) {
        isBot = bot;
    }

    public ArrayList<Integer> getActionPattern() {
        return ActionPattern;
    }

    public void setActionPattern(ArrayList<Integer> actionPattern) {
        ActionPattern = actionPattern;
    }

    public int getActionPatternIndex() {
        return actionPatternIndex;
    }

    public void setActionPatternIndex(int actionPatternIndex) {
        this.actionPatternIndex = actionPatternIndex;
    }

    public ArrayList<Effect> getOwnEffect() {
        return OwnEffect;
    }

    public void setOwnEffect(ArrayList<Effect> ownEffect) {
        OwnEffect = ownEffect;
    }

    public boolean isModifySpriteSize() {
        return isModifySpriteSize;
    }

    public double getModifyHeight() {
        return modifyHeight;
    }

    public void setModifyHeight(double modifyHeight) {
        this.modifyHeight = modifyHeight;
    }

    public double getModifyWidth() {
        return modifyWidth;
    }

    public void setModifyWidth(double modifyWidth) {
        this.modifyWidth = modifyWidth;
    }

    public Card getCard() {
        ArrayList<Card> Cards = GameController.getInstance().getBattleBoard().getController().getAllCards();
        Card thisCharacterCard = null;
        for(int i=0; i<Cards.size(); i++) {
            if(Cards.get(i).getController().getCharacter() == this) {
                thisCharacterCard = Cards.get(i);
                break;
            }
        }
        if(thisCharacterCard == null) {
            throw new RuntimeException("character card cannot be null");
        }
        return thisCharacterCard;
    }
}

