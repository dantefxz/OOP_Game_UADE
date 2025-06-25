package Misc;

import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.InputStream;

public class Music {
    private static Music instance;
    private Player player;
    private Thread musicThread;

    private Music() {
        // No se necesita inicialización especial
    }

    public static Music getInstance() {
        if (instance == null) {
            instance = new Music();
        }
        return instance;
    }

    public void playMusicFromResource(String resourcePath) {
        stopMusic();

        musicThread = new Thread(() -> {
            try {
                InputStream is = getClass().getResourceAsStream(resourcePath);
                if (is == null) {
                    System.out.println("⚠ No se encontró la música: " + resourcePath);
                    return;
                }

                BufferedInputStream bis = new BufferedInputStream(is);
                player = new Player(bis);
                player.play();  // No loop automático, pero se puede agregar con un bucle
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        musicThread.setDaemon(true); // Así no bloquea la salida del programa
        musicThread.start();
    }

    public void stopMusic() {
        if (player != null) {
            player.close(); // Esto corta la reproducción
        }
        if (musicThread != null) {
            musicThread.interrupt(); // Detiene el hilo
        }
    }
}
