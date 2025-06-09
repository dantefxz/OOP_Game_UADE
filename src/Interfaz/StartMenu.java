package Interfaz;

import Misc.Music;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class StartMenu {
    public StartMenu(JFrame mainMenu) {
        //Logo
        URL iconURL = StartMenu.class.getResource("/Assets/Images/logo.png");
        JLabel logoLabel = new JLabel();
        if (iconURL != null) {
            ImageIcon logo = new ImageIcon(iconURL);
            // In-Menu Logo
            Image scaledImage = logo.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
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
        BossLabel.setPreferredSize(new Dimension(150, 150));
        BossLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        Dimension buttonSize = new Dimension(210, 360);

        for (int n = 1; n < 6; n++) {
            JPanel bossPanel = new JPanel();
            bossPanel.setLayout(new BoxLayout(bossPanel, BoxLayout.Y_AXIS));
            bossPanel.setOpaque(false);

            // Crear botón con imagen del jefe
            JButton Boss = new JButton();
            String imagePath = "/Assets/Images/Sprites/Bosses/Boss" + n + "_preview.png";
            URL imageUrl = StartMenu.class.getResource(imagePath);

            if (imageUrl != null) {
                ImageIcon bossIcon = new ImageIcon(imageUrl);
                Image scaledImage = bossIcon.getImage().getScaledInstance(buttonSize.width, buttonSize.height, Image.SCALE_SMOOTH);
                Boss.setIcon(new ImageIcon(scaledImage));
            } else {
                System.out.println("Imagen no encontrada para Boss " + n + ": " + imagePath);
                Boss.setText("Boss " + n);
            }

            Boss.setMaximumSize(buttonSize);
            Boss.setPreferredSize(buttonSize);
            Boss.setMinimumSize(buttonSize);
            Boss.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Marco dorado
            Boss.setBorder(BorderFactory.createLineBorder(new Color(212, 175, 55), 4)); // Dorado metálico
            Boss.setContentAreaFilled(false);
            Boss.setFocusPainted(false);
            Boss.setOpaque(false);
            Boss.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Etiqueta debajo del botón
            JLabel nameLabel = new JLabel("Boss " + n);
            nameLabel.setForeground(Color.WHITE);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Espaciado superior

            // Añadir al panel individual
            bossPanel.add(Boss);
            bossPanel.add(nameLabel);

            BossLabel.add(bossPanel);
            if (n < 5) {
                BossLabel.add(Box.createRigidArea(new Dimension(43, 0)));
            }
        }


        mainMenu.add(BossLabel, BorderLayout.CENTER);

            // Misc
            Music musicPlayer = new Music();
            musicPlayer.playMusicFromResource("/Assets/Sounds/MainMenu.wav");
        }
    /*
    private Object SelectBoss(int Boss){
        return null;
    }
    */
}
