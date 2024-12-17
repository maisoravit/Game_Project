package logic.levels;

import logic.characters.enemy.*;
import logic.team.Team;

public class Level2 extends Level {
    public Level2() {
        super(new Team("Enemy"));
        addMember();
    }

    @Override
    protected void addMember() {
        getEnemyTeam().addCharacter(new Minotaur(true), Team.Line.FRONT);
        getEnemyTeam().addCharacter(new Vampire(true), Team.Line.MID);
        getEnemyTeam().addCharacter(new Vampire(true), Team.Line.MID);
        getEnemyTeam().addCharacter(new Dullahan(true), Team.Line.REAR);
    }
}
