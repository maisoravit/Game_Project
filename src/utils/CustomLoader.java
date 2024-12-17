package utils;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * This class is used to load FXML files.
 * and handle the exception here So we don't have to handle it in the controller.
 */
public class CustomLoader extends FXMLLoader{
    public CustomLoader(String fileName) {
        super(getFXML(fileName));
    }
    public static URL getFXML(String path) {
        if (path == null) throw new IllegalArgumentException("Path cannot be null");
        return Objects.requireNonNull(CustomLoader.class.getResource("/fxml/" + path));
    }

    @Override
    public <T> T load() {
        try {
            return super.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Failed to load FXML file");
        return null;
    }
}
