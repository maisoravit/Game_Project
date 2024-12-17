package utils;

import java.net.URL;
import java.util.Objects;

/**
 * This class is used to get assets from the resources folder.
 * Especially useful for getting images.
 */
public class Assets {
    private Assets() {}
    public static String getAsset(String path) {
        if (path == null) throw new IllegalArgumentException("Path cannot be null");
        URL url = Assets.class.getResource(path);
        if (url == null) {
            url = Assets.class.getResource("/images/no_img.png");
        }

        return Objects.requireNonNull(url).toString();
    }
}
