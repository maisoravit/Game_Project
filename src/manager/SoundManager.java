package manager;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.util.Pair;
import utils.Assets;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to manage BGM, sound effects in the game.
 */
public class SoundManager {
    private static SoundManager instance;
    private static MediaPlayer bgmPlayer;
    private static MediaPlayer sfxPlayer;

    public enum BGM {
        MAIN_MENU, BATTLE_SCENE
    }

    public enum SFX {
        CLICK_DEFAULT, HOVER_DEFAULT, CLICK_LEVEL, HOVER_LEVEL
    }

    private HashMap<BGM, Pair<String, Double>> bgmMap = new HashMap<>(
            Map.of(
                    BGM.MAIN_MENU, new Pair<>("/sounds/Adventure.mp3", 0.5),
                    BGM.BATTLE_SCENE, new Pair<>("/sounds/STREAMING-thunderstorm-in-kyoto-zac-tiessen-main-version-23958-01-30.mp3", 0.25)
            )
    );

    private HashMap<SFX, Pair<String, Double>> sfxMap = new HashMap<>(
            Map.of(
                    SFX.CLICK_DEFAULT, new Pair<>("/sounds/click_default.mp3", 0.5),
                    SFX.HOVER_DEFAULT, new Pair<>("/sounds/hover_default.mp3", 0.5),
                    SFX.CLICK_LEVEL, new Pair<>("/sounds/click_level.mp3", 0.5),
                    SFX.HOVER_LEVEL, new Pair<>("/sounds/hover_level.mp3", 0.5)
            )
    );

    private SoundManager() {
    }

    public void playBGM(String path, double volume) {
        // play background music
        if (bgmPlayer != null) {
            bgmPlayer.stop();
        }
        bgmPlayer = new MediaPlayer(new Media(Assets.getAsset(path)));
        bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        bgmPlayer.setVolume(0);
        bgmPlayer.play();
//        fade in effect
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        new KeyValue(bgmPlayer.volumeProperty(), volume)
                )
        );
        timeline.play();
    }

    public void playBGM(BGM bgm) {
        playBGM(bgmMap.get(bgm).getKey(), bgmMap.get(bgm).getValue());
    }

    public void stopBGM() {
        if (bgmPlayer != null) {
            bgmPlayer.stop();
        }
    }

    public void playSFX(String path, double volume) {
        // play sound effect
        if (sfxPlayer != null) {
            sfxPlayer.stop();
        }
        sfxPlayer = new MediaPlayer(new Media(Assets.getAsset(path)));
        sfxPlayer.setVolume(volume);
        sfxPlayer.play();
    }

    public void playSFX(SFX sfx) { playSFX(sfxMap.get(sfx).getKey(), sfxMap.get(sfx).getValue()); }

    public static void createInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
    }

    public static SoundManager getInstance() {
        return instance;
    }
}
