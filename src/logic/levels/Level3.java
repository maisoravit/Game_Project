package logic.levels;

import logic.characters.enemy.*;
import logic.team.Team;

public class Level3 extends Level {
    public Level3() {
        super(new Team("Enemy"));
        addMember();
    }

    @Override
    protected void addMember() {
        getEnemyTeam().addCharacter(new Golem(true), Team.Line.FRONT);
        getEnemyTeam().addCharacter(new Minotaur(true), Team.Line.MID);
        getEnemyTeam().addCharacter(new Vampire(true), Team.Line.MID);
        getEnemyTeam().addCharacter(new DevilSage(true), Team.Line.REAR);
    }
}
