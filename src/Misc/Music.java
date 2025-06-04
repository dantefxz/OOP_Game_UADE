package Misc;

import javax.sound.sampled.*;
import java.io.InputStream;
import java.io.BufferedInputStream;

public class Music {
    private Clip clip;

    public void playMusicFromResource(String resourcePath) {
        try {
            InputStream audioSrc = getClass().getResourceAsStream(resourcePath);
            if (audioSrc == null) {
                System.out.println("⚠No se encontró la música: " + resourcePath);
                return;
            }

            BufferedInputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);

            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop infinito
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
