package router;

import ui.battle_scene.BattleScene;
import ui.home.Home;
import ui.mainMenu.MainMenu;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is the configuration of the router.
 * It contains the scenes of the application and the starting scene of the application
 * and the scenes of the application
 */
public class Config {
    private Config() {}
    public static final AppScene START_SCENE = AppScene.HOME;
    public enum AppScene {
        HOME, BATTLE, MAIN_MENU
    }
    public static HashMap<AppScene, IAppScene> scenes = new HashMap<>(
            Map.of(
                    AppScene.HOME, new Home(),
                    AppScene.BATTLE, new BattleScene(),
                    AppScene.MAIN_MENU, new MainMenu()
            )
    );
}

