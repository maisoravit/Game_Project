package logic.effects;
import javafx.application.Platform;
import javafx.scene.image.Image;
import logic.characters.BaseCharacter;

public abstract class Effect implements Cloneable {

    protected String name;

    protected BaseCharacter effectReceiver;

    protected int duration;
    protected Image iconImg;
    protected TriggerType triggerType;
    protected TriggerEvent triggerEvent;

    public Effect(String name, BaseCharacter effectReceiver, int duration, TriggerType triggerType, TriggerEvent triggerEvent, Image iconImg){
        this.name = name;
        this.effectReceiver = effectReceiver;
        this.duration = duration;
        this.triggerType = triggerType;
        this.triggerEvent = triggerEvent;
        this.iconImg = iconImg;
    }

    public Effect clone() {
        try {
            return (Effect) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void runDownDuration() {
        duration--;
        if(duration <= 0) clearEffect();
    };

    public void addDuration(int turn) {
        if(turn > 0) duration += turn;
    };

    public void clearEffect() {
        Thread thread = new Thread(()->{
            effectReceiver.getStatusEffect().remove(this);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    effectReceiver.getCard().getController().setEffect(effectReceiver.getStatusEffect());
                }
            });
        });
        thread.start();
    }
    public abstract void activate();

    public abstract String getDescription();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BaseCharacter getEffectReceiver() {
        return effectReceiver;
    }

    public void setEffectReceiver(BaseCharacter effectReceiver) {
        this.effectReceiver = effectReceiver;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Image getIconImg() {
        return iconImg;
    }

    public void setIconImg(Image iconImg) {
        this.iconImg = iconImg;
    }

    public TriggerType getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(TriggerType triggerType) {
        this.triggerType = triggerType;
    }

    public TriggerEvent getTriggerEvent() {
        return triggerEvent;
    }

    public void setTriggerEvent(TriggerEvent triggerEvent) {
        this.triggerEvent = triggerEvent;
    }
}
