package MainExecute;

import Interfaz.*;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import Misc.DB;
import java.io.IOException;

public class Main {
    static JFrame mainWindow; // Ventana principal estática para acceso global
    public static void main(String[] args) throws IOException {
        // Crear ventana principal con título
        mainWindow = new JFrame("Elden Ring²: Java Edition");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Crear ventana principal con título

        // Cargar icono desde recursos y asignarlo a la ventana
        URL iconURL = Main.class.getResource("/Assets/Images/logo.png");
        assert iconURL != null; // Asegura que la ruta no sea nula
        ImageIcon logo = new ImageIcon(iconURL);
        mainWindow.setIconImage(logo.getImage());

        // Ajustar tamaño preferido y deshabilitar redimensionado
        mainWindow.setPreferredSize(new Dimension(1280, 720));
        mainWindow.setResizable(false);
        mainWindow.pack(); // Ajusta el tamaño al contenido preferido
        mainWindow.setLocationRelativeTo(null); // Centra la ventana en pantalla


        new StartMenu(mainWindow);  // Inicializar el menú principal dentro de la ventana
        mainWindow.setVisible(true);    // Muestra la ventana al usuario

        // Cargar o crear base de datos local JSON para progreso
        try {
            DB dataBase = new DB(); // carga o crea el JSON

            // Mostrar en consola datos guardados
            System.out.println("Bosses derrotados: " + dataBase.getBossesDefeated());
            System.out.println("Clase especial desbloqueada: " + dataBase.hasSpecialClass());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método estático para acceder a la ventana principal desde otras clases
    public static JFrame getMainWindow(){
        return mainWindow;
    }
}
