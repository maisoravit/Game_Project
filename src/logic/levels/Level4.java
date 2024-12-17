package logic.levels;

import logic.characters.enemy.DevilSage;
import logic.characters.enemy.Golem;
import logic.characters.enemy.Minotaur;
import logic.characters.enemy.Vampire;
import logic.team.Team;

public class Level4 extends Level {
    public Level4() {
        super(new Team("Enemy"));
        addMember();
    }

    @Override
    protected void addMember() {
        getEnemyTeam().addCharacter(new Golem(true), Team.Line.FRONT);
        getEnemyTeam().addCharacter(new Minotaur(true), Team.Line.MID);
        getEnemyTeam().addCharacter(new Vampire(true), Team.Line.MID);
        getEnemyTeam().addCharacter(new Golem(true), Team.Line.REAR);
    }
}
