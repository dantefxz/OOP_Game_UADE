import Interfaz.*;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import Misc.DB;
import java.io.IOException;
import Characters.Bosses.*;
import Characters.Player.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Create Window
        JFrame mainWindow = new JFrame("Elden Ring²: Java Edition");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        URL iconURL = Main.class.getResource("/Assets/Images/logo.png");
        assert iconURL != null;
        ImageIcon logo = new ImageIcon(iconURL);
        mainWindow.setIconImage(logo.getImage());


        mainWindow.setPreferredSize(new Dimension(1280, 720));
        mainWindow.setResizable(false);
        mainWindow.pack();
        mainWindow.setLocationRelativeTo(null);

        // Create Main Menu
        new StartMenu(mainWindow);
        mainWindow.setVisible(true);
         // Data
        try {
            DB dataBase = new DB(); // carga o crea el JSON

            System.out.println("Bosses derrotados: " + dataBase.getBossesDefeated());
            System.out.println("Clase especial desbloqueada: " + dataBase.hasSpecialClass());

        } catch (IOException e) {
            e.printStackTrace();
        }

        Tank jugador = new Tank("Tank", 120, 30, 10); // Se tien que realizar una cada uno para depende lo que decida
        Necromancer jefe = new Necromancer("Necromancer", 100, 25, 5);
        //Asignacion de clases funcional por el momento pero mejorable


    }
}
