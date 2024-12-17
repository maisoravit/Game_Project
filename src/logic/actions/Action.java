package logic.actions;

import javafx.scene.image.Image;
import logic.characters.BaseCharacter;
import logic.players.Player;

// Action classes
public abstract class Action implements Cloneable {
    protected String name;
    protected int manaCost;
    protected int spiritCost;
    protected Image iconImg;
    protected Target targetType;
    protected BaseCharacter user;
    protected ActionType type;
    protected boolean isFromSkillCard;

    public Action(BaseCharacter user, String name, int manaCost, Image iconImg, Target targetType) {
        this.user = user;
        this.name = name;
        this.manaCost = manaCost;
        this.iconImg = iconImg;
        this.targetType = targetType;
        this.isFromSkillCard = false;
    }

    @Override
    public Action clone() {
        try {
            Action action = (Action) super.clone();
            action.name = name;
            action.manaCost = manaCost;
            action.iconImg = iconImg;
            action.targetType = targetType;
            return action;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void activate(BaseCharacter targetCharacter);
    public void activateEffect() {};
    public void playEffectAndActivate(BaseCharacter targetCharacter) {
        new Thread(() -> {
            activateEffect();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            activate(targetCharacter);
        }).start();
    };
    public abstract String getDescription();

    public boolean checkCost(int own, int cost) {
        if (own >= cost) {
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        if(manaCost<0) manaCost = 0;
        this.manaCost = manaCost;
    }

    public Image getIconImg() {
        return iconImg;
    }

    public void setIconImg(Image iconImg) {
        if (iconImg == null) {
            throw new IllegalArgumentException("Image icon cannot be null.");
        }
        this.iconImg = iconImg;
    }

    public Target getTargetType() {
        return targetType;
    }

    public void setTargetType(Target targetType) {
        this.targetType = targetType;
    }

    public int getSpiritCost() {
        return spiritCost;
    }

    public void setSpiritCost(int spiritCost) {
        if(spiritCost < 0) spiritCost = 0;
        this.spiritCost = spiritCost;
    }

    public BaseCharacter getUser() {
        return user;
    }

    public void setUser(BaseCharacter user) {
        this.user = user;
    }

    public boolean isFromSkillCard() {
        return isFromSkillCard;
    }

    public void setFromSkillCard(boolean fromSkillCard) {
        isFromSkillCard = fromSkillCard;
    }
}
