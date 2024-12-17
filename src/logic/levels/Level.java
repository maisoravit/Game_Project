package logic.levels;

import javafx.scene.image.Image;
import logic.team.Team;

/**
 * Level
 * this class stores the information of the level
 * enemy team, action pattern, etc.
 */
public abstract class Level {
    private Team enemyTeam;

    public Level(Team enemyTeam) {
        this.enemyTeam = enemyTeam;
    }
    abstract protected void addMember();
    public Team getEnemyTeam() { return enemyTeam; }
    public Image getImgIcon() {return getEnemyTeam().getRear().get(0).getImgIcon(); }
}
