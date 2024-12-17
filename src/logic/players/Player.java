package logic.players;

public class Player {
    private final int MAX_MANA = 15;
    private int currentMana = 0;
    private final int MAX_SPIRIT = 100;
    private int currentSpirit = 0;

    public Player(){
        this.currentMana = 0;
        this.currentSpirit = 0;
    }

    // Getter method to get the character this player controls
    public Character getCharacter() {
        // Return the character instance associated with the player
        // You'll need to add logic to associate a character with a player
        return null;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void setCurrentMana(int currentMana) {
        if(currentMana < 0) currentMana = 0;
        else if (currentMana > 15) currentMana = 15;
        this.currentMana = currentMana;
    }

    public int getCurrentSpirit() {
        return currentSpirit;
    }

    public void setCurrentSpirit(int currentSpirit) {
        if(currentSpirit < 0) currentSpirit = 0;
        else if(currentSpirit > 100) currentSpirit = 100;
        this.currentSpirit = currentSpirit;
    }
}
