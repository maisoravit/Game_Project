package logic.levels;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

public class LevelManager {
    private static LevelManager instance;
    private ArrayList<Level> levels;
    private int currentLevel; // >= 1

    private LevelManager() {
        currentLevel = 1;
        levels = new ArrayList<>(
                Arrays.asList(
                        new Level1(),
                        new Level2(),
                        new Level3(),
                        new Level4(),
                        new Level5(),
                        new Level6()
                )
        );
    }

    public static void createInstance() {
        if (instance == null) {
            instance = new LevelManager();
        }
    }

    public static LevelManager getInstance() {
        return instance;
    }

    //    return a copy of the level
    public Level getLevel() {
        Class<? extends Level> levelClass = levels.get(currentLevel-1).getClass();
        try {
            return levelClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public Level getLevelInfo(int level) {
        if (level < 1 || level > levels.size()) {
            throw new IllegalArgumentException("Invalid level");
        }
        return levels.get(level-1);
    }

    public void setCurrentLevel(int currentLevel) {
        if (currentLevel < 1 || currentLevel > levels.size()) {
            throw new IllegalArgumentException("Invalid level");
        }
        this.currentLevel = currentLevel;
    }
}
