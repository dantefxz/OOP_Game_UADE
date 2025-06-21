package Misc;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class Music {
    private static Music instance;
    private MediaPlayer mediaPlayer;

    // Necesario para inicializar JavaFX en aplicaciones no-JavaFX
    static {
        new JFXPanel();
    }

    private Music() {}

    public static Music getInstance() {
        if (instance == null) {
            instance = new Music();
        }
        return instance;
    }

    public void playMusicFromResource(String resourcePath) {
        stopMusic();

        try {
            URL resource = getClass().getResource(resourcePath);
            if (resource == null) {
                System.out.println("⚠ No se encontró la música: " + resourcePath);
                return;
            }

            Media media = new Media(resource.toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop infinito
            mediaPlayer.setVolume(0.15);
            mediaPlayer.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
