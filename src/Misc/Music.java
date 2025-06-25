package Misc;

import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.InputStream;

public class Music {
    private static Music instance;
    private Player player;
    private Thread musicThread;
    private boolean loop = true;

    private Music() {
        // No es necesario constructor
    }

    public static Music getInstance() {
        if (instance == null) {
            instance = new Music();
        }
        return instance;
    }

    public void playMusicFromResource(String resourcePath) {
        stopMusic();

        loop = true; // Reiniciar el loop

        musicThread = new Thread(() -> {
            while (loop) {
                try {
                    InputStream is = getClass().getResourceAsStream(resourcePath);
                    if (is == null) {
                        System.out.println("No se encontró la música: " + resourcePath);
                        return;
                    }

                    BufferedInputStream bis = new BufferedInputStream(is);
                    player = new Player(bis);
                    player.play();  // Se bloquea hasta que termine, por eso el bucle

                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        });

        musicThread.setDaemon(true); // No bloquea el cierre del programa
        musicThread.start();
    }

    public void stopMusic() {
        loop = false; // Romper el bucle
        if (player != null) {
            player.close(); // Detiene la reproducción
        }
        if (musicThread != null) {
            musicThread.interrupt(); // Detiene el hilo
        }
    }
}
