import Interfaz.*;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
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
    }
}
