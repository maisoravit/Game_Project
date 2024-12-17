package logic.team;

import logic.GameController;
import logic.characters.Attacker;
import logic.characters.BaseCharacter;
import logic.characters.Support;

import java.util.*;

/**
 * This class is used to store members of a team in the game.
 * It contains the team name and the characters in the team.
 */
public class Team {
    private String teamName;
    private final int MAX_TEAM_SIZE = 4;
    private ArrayList<BaseCharacter> members;
    private HashMap<BaseCharacter, Line> lineHashMap;

    public enum Line {
        FRONT, MID, REAR
    }


    public Team(String teamName) {
        this.teamName = teamName;
        this.members = new ArrayList<>();
        this.lineHashMap = new HashMap<>();
    }

    public ArrayList<BaseCharacter> getFront() {
        return new ArrayList<>(members.stream().filter(character -> lineHashMap.get(character) == Line.FRONT).toList());
    }

    public ArrayList<BaseCharacter> getMid() {
        return new ArrayList<>(members.stream().filter(character -> lineHashMap.get(character) == Line.MID).toList());
    }

    public ArrayList<BaseCharacter> getRear() {
        return new ArrayList<>(members.stream().filter(character -> lineHashMap.get(character) == Line.REAR).toList());
    }

    public ArrayList<BaseCharacter> getMembers() {
        return members;
    }

    public void addCharacter(BaseCharacter character, Line line) {
        if (members.size() == MAX_TEAM_SIZE) {
            throw new IllegalArgumentException("Team is full.");
        }
        members.add(character);
        lineHashMap.put(character, line);
    }

    public boolean removeCharacter(BaseCharacter character) {
        if (!members.contains(character)||!lineHashMap.containsKey(character)){
            return false;
        }
        members.remove(character);
        lineHashMap.remove(character);
        return true;
    }

    /**
     * Constrains:
     * 1. The character must be in the team.
     * 2. If the character is already in the line, do nothing.
     * 3. Front max 2, Mid max 2. if exceed do nothing.
     */
    public void moveCharacterToLine(BaseCharacter character, Line line) {
        if (!members.contains(character)) {
            throw new IllegalArgumentException("Character not found in team.");
        }
        if (
                lineHashMap.get(character) == line
                || line == Line.FRONT && getFront().size() == 2
                || line == Line.MID && getMid().size() == 2
                || line == Line.REAR // Can't move in/out of rear
        ) {
            return;
        }
        lineHashMap.put(character, line);
    }

    /**
     * Change the character in the team to a new character.
     * @param prev_character The character to be replaced.
     * @param new_character The new character to replace the prev_character.
     */
    public void changeCharacterTo(BaseCharacter prev_character, BaseCharacter new_character) {
        if (!members.contains(prev_character)) {
            throw new IllegalArgumentException("Character not found in team.");
        }
//        constraints
//        FRONT, MID only allow Attacker
//        REAR only allow Support
        if (
                lineHashMap.get(prev_character) == Line.FRONT && !(new_character instanceof Attacker)
                || lineHashMap.get(prev_character) == Line.MID && !(new_character instanceof Attacker)
                || lineHashMap.get(prev_character) == Line.REAR && !(new_character instanceof Support)
        ) {
            if (lineHashMap.get(prev_character) == Line.FRONT || lineHashMap.get(prev_character) == Line.MID)
                GameController.getInstance().getBattleSceneController().getPannelController().setSelectErrorPane("⚠ Please Select Attacker Character");
            else
                GameController.getInstance().getBattleSceneController().getPannelController().setSelectErrorPane("⚠ Please Select Support Character");
            return;
        }

        lineHashMap.put(new_character, lineHashMap.get(prev_character));
        lineHashMap.remove(prev_character);
        members.remove(prev_character);
        members.add(new_character);

//        Update UI
        GameController.getInstance().getBattleBoard().getController().getBatTeam1().getController().updateCard(prev_character, new_character);
    }

    public String getTeamName() {
        return teamName;
    }
}
