package Interfaz;

import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class StartMenu {
    public StartMenu(JFrame mainMenu){
        //Logo
        URL iconURL = Main.class.getResource("/Assets/Images/logo.png");
        JLabel logoLabel = new JLabel();
        if (iconURL != null) {
            ImageIcon logo = new ImageIcon(iconURL);

            // In-Menu Logo
            Image scaledImage = logo.getImage().getScaledInstance(200,200, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            logoLabel.setOpaque(false);
            logoLabel.setIcon(scaledIcon);
        } else {
            System.out.println("Incorrect Logo");
        }

        //Graphical Items
        mainMenu.getContentPane().setBackground(Color.BLACK);
        mainMenu.setLayout(new BorderLayout());
        mainMenu.add(logoLabel, BorderLayout.NORTH);
        mainMenu.add(new gameMenu(), BorderLayout.CENTER);
    }

}
