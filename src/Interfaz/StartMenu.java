package Interfaz;

import Misc.*;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class StartMenu {
    public StartMenu(JFrame mainMenu){
        //Logo
        URL iconURL = StartMenu.class.getResource("/Assets/Images/logo.png");
        JLabel logoLabel = new JLabel();
        if (iconURL != null) {
            ImageIcon logo = new ImageIcon(iconURL);
            // In-Menu Logo
            Image scaledImage = logo.getImage().getScaledInstance(130,130, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            logoLabel.setOpaque(false);
            logoLabel.setIcon(scaledIcon);


        } else {
            System.out.println("Incorrect Logo");
        }

        //Graphical Items
        JLabel titleLabel = new JLabel();
        URL titleURL = StartMenu.class.getResource("/Assets/Images/Title.png");
        if (titleURL != null) {
            ImageIcon title = new ImageIcon(titleURL);
            Image scaledTitle = title.getImage().getScaledInstance(170, 95, Image.SCALE_SMOOTH);
            titleLabel.setIcon(new ImageIcon(scaledTitle));
            titleLabel.setOpaque(false);
        } else {
            System.out.println("Title.png no encontrado");
        }

        // Header with logo & title
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(mainMenu.getWidth(), 120));
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        logoLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        titleLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        topPanel.add(logoLabel);
        topPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        topPanel.add(titleLabel);

        mainMenu.getContentPane().setBackground(Color.BLACK);
        mainMenu.setLayout(new BorderLayout());
        mainMenu.add(topPanel, BorderLayout.NORTH);
     //   mainMenu.add(logoLabel, BorderLayout.NORTH);

        // Boss selection
        JPanel BossLabel = new JPanel();
        BossLabel.setLayout(new BoxLayout(BossLabel, BoxLayout.Y_AXIS));
        BossLabel.setBackground(Color.BLACK);
        BossLabel.setPreferredSize(new Dimension(150,150));
        BossLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Dimension buttonSize = new Dimension(170,360);

        JButton Boss1 = new JButton("Boss 1");
        Boss1.setMaximumSize(buttonSize);
        Boss1.setPreferredSize(buttonSize);
        Boss1.setMinimumSize(buttonSize);
        Boss1.setAlignmentX(Component.LEFT_ALIGNMENT);
        Boss1.setBackground(new Color(0,120,255));
        Boss1.setOpaque(true);
        Boss1.setBorderPainted(false);
        Boss1.setFocusPainted(false);

        BossLabel.add(Boss1);
        mainMenu.add(BossLabel, BorderLayout.CENTER);

        // Misc
        Music musicPlayer = new Music();
        musicPlayer.playMusicFromResource("/Assets/Sounds/MainMenu.wav");


    }
}
