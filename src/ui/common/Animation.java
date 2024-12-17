package ui.common;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Animation {
    private ImageView imageView;
    private int duration;
    private ArrayList<Image> frames;
    private Thread thread;
    private boolean isRunning;

    public Animation(ImageView imageView, int duration) {
        this.imageView = imageView;
        this.duration = duration;
        this.frames = new ArrayList<>();
        this.isRunning = false;
    }

    public void addFrame(Image frame) {
        frames.add(frame);
    }

    public void start() {
        // Start the animation
        isRunning = true;
        thread = new Thread(() -> {
            while (true) {
                for (Image frame : frames) {
                    imageView.setImage(frame);
                    if (!isRunning) {
                        thread.interrupt();
                        break;
                    }
                    try {
                        Thread.sleep(duration);
                    } catch (InterruptedException e) {
                        System.out.println("Animation thread interrupted");
                    }
                }
            }
        });
        thread.start();
    }

    public void stop() {
        // Stop the animation
        isRunning = false;
    }
}
