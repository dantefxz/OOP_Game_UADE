package Misc;

import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * Clase Music:
 * Se encarga de reproducir música en segundo plano utilizando un hilo separado.
 * Utiliza el patrón Singleton para garantizar una única instancia en todo el programa.
 */
public class Music {
    private static Music instance;        // Instancia única
    private Player player;                // Reproductor de música
    private Thread musicThread;          // Hilo encargado de la reproducción
    private boolean loop = true;         // Controla si la música debe repetirse

    private Music() {
        // No hace falta inicializar nada aca
    }

    //Devuelve la instancia única de la clase Music
    public static Music getInstance() {
        if (instance == null) {
            instance = new Music();
        }
        return instance;
    }

/**
 * Reproduce una música desde los recursos del proyecto.
 * El archivo debe estar en formato MP3.
*/
 public void playMusicFromResource(String resourcePath) {
        stopMusic();  // Detiene cualquier música que esté sonando antes de iniciar otra

     loop = true; // Reiniciar el loop

        musicThread = new Thread(() -> {
            while (loop) {
                try {
                    // Carga el archivo desde el classpath
                    InputStream is = getClass().getResourceAsStream(resourcePath);
                    if (is == null) {
                        System.out.println("No se encontró la música: " + resourcePath);
                        return;
                    }

                    // Usa BufferedInputStream para mejorar el rendimiento
                    BufferedInputStream bis = new BufferedInputStream(is);
                    player = new Player(bis);
                    player.play();  // Se bloquea hasta que termine, por eso el bucle

                } catch (Exception e) {
                    e.printStackTrace(); // Muestra cualquier error de reproducción
                    break;
                }
            }
        });

     musicThread.setDaemon(true); // El hilo no evitará que el programa se cierre
     musicThread.start();         // Comienza la reproducción en segundo plano
    }

    /**
     * Detiene la música actual, si se está reproduciendo.
     * También interrumpe el hilo si es necesario.
     */
    public void stopMusic() {
        loop = false; // Sale del bucle de reproducción
        if (player != null) {
            player.close(); // Detiene la reproducción
        }
        if (musicThread != null) {
            musicThread.interrupt(); // Interrumpe el hilo de música
        }
    }
}
