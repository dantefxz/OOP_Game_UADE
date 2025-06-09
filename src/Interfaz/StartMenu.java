package Interfaz;

import Misc.Music;

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
        BossLabel.setLayout(new BoxLayout(BossLabel, BoxLayout.X_AXIS));
        BossLabel.setBackground(Color.BLACK);
        BossLabel.setPreferredSize(new Dimension(150,150));
        BossLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        Dimension buttonSize = new Dimension(210,360);

        for (int n = 1; n < 6; n++){
            JButton Boss = new JButton("Boss "+n);
            Boss.setMaximumSize(buttonSize);
            Boss.setPreferredSize(buttonSize);
            Boss.setMinimumSize(buttonSize);
            Boss.setAlignmentX(Component.LEFT_ALIGNMENT);
            Boss.setBackground(new Color(0,120,255));
            Boss.setOpaque(true);
            Boss.setBorderPainted(false);
            Boss.setFocusPainted(false);
            BossLabel.add(Boss);
            if (n < 5) {
                BossLabel.add(Box.createRigidArea(new Dimension(43, 0)));
            }
        }
        mainMenu.add(BossLabel, BorderLayout.CENTER);

        // Misc
        Music musicPlayer = new Music();
        musicPlayer.playMusicFromResource("/Assets/Sounds/MainMenu.wav");
    }

    private Object SelectBoss(int Boss){
        return null;
    }
}
