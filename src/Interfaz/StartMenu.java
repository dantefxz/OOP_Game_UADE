package Interfaz;

import Misc.Music;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class StartMenu {
    HashMap<String, Object> Menus = new HashMap<>();
    String selectedCharacter;
    String selectedBoss;
    public StartMenu(JFrame newMainMenu) {
        Menus.put("MainMenu", newMainMenu);
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
        topPanel.setPreferredSize(new Dimension(newMainMenu.getWidth(), 120));
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        logoLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        titleLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        topPanel.add(logoLabel);
        topPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        topPanel.add(titleLabel);

        newMainMenu.getContentPane().setBackground(Color.BLACK);
        newMainMenu.setLayout(new BorderLayout());
        newMainMenu.add(topPanel, BorderLayout.NORTH);
      // Misc
        Music musicPlayer = new Music();
        musicPlayer.playMusicFromResource("/Assets/Sounds/MainMenu.wav");
        CharacterMenu();
    }
    public JPanel CharacterMenu(){
         // Boss selection
        JPanel CharacterLabel = new JPanel();
        CharacterLabel.setLayout(new BoxLayout(CharacterLabel, BoxLayout.X_AXIS));
        CharacterLabel.setBackground(Color.BLACK);
        CharacterLabel.setPreferredSize(new Dimension(150, 150));
        CharacterLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        Dimension buttonSize = new Dimension(210, 360);

        for (int n = 1; n < 5; n++) {
            JPanel characterPanel = new JPanel();
            characterPanel.setLayout(new BoxLayout(characterPanel, BoxLayout.Y_AXIS));
            characterPanel.setOpaque(false);

            JButton Character = new JButton();
            String imagePath = "/Assets/Images/Sprites/Characters/Player" + n + "_preview.png";
            URL imageUrl = StartMenu.class.getResource(imagePath);

            if (imageUrl != null) {
                ImageIcon characterIcon = new ImageIcon(imageUrl);
                Image scaledImage = characterIcon.getImage().getScaledInstance(buttonSize.width, buttonSize.height, Image.SCALE_SMOOTH);
                Character.setIcon(new ImageIcon(scaledImage));
            } else {
                System.out.println("Imagen no encontrada para Character " + n + ": " + imagePath);
                Character.setText("Class " + n);
            }

            Character.setMaximumSize(buttonSize);
            Character.setPreferredSize(buttonSize);
            Character.setMinimumSize(buttonSize);
            Character.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Marco dorado
            Character.setBorder(BorderFactory.createLineBorder(new Color(120,0,212), 4)); // Dorado metálico
            Character.setContentAreaFilled(false);
            Character.setFocusPainted(false);
            Character.setOpaque(false);
            Character.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Etiqueta debajo del botón
            JLabel nameLabel = new JLabel("Character " + n);
            nameLabel.setForeground(Color.WHITE);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Espaciado superior

            // Seleccionar
            int finalN = n;
            Character.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Character " + finalN);
                    JPanel currentMenu = (JPanel) Menus.get("CharacterMenu");
                    JFrame mainMenu = getMainMenu();
                    mainMenu.remove(currentMenu);
                    Menus.remove("CharacterMenu");
                    System.out.println(Menus);
                    selectedCharacter = "Character " + finalN;
                    BossMenu();
                }
            });

            // Añadir al panel individual
            characterPanel.add(Character);
            characterPanel.add(nameLabel);

            CharacterLabel.add(characterPanel);
            if (n < 4) {
                CharacterLabel.add(Box.createRigidArea(new Dimension(127, 0)));
            }
        }
        JFrame mainMenu = (JFrame) Menus.get("MainMenu");
        mainMenu.add(CharacterLabel, BorderLayout.CENTER);
        Menus.put("CharacterMenu", CharacterLabel);

        return CharacterLabel;
    }
    public void BossMenu(){
        // Delete previous Menu

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

            // Seleccionar
            int finalN = n;
            Boss.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Boss " + finalN);
                    JPanel currentMenu = (JPanel) Menus.get("BossMenu");
                    JFrame mainMenu = getMainMenu();
                    mainMenu.remove(currentMenu);
                    Menus.remove("BossMenu");
                    System.out.println(Menus);
                    selectedBoss = "Boss " + finalN;
                    System.out.println("Personaje: " + selectedCharacter + "\n Boss: " + selectedBoss);
                }
            });

            // Añadir al panel individual
            bossPanel.add(Boss);
            bossPanel.add(nameLabel);

            BossLabel.add(bossPanel);
            if (n < 5) {
                BossLabel.add(Box.createRigidArea(new Dimension(43, 0)));
            }
            JFrame mainMenu = getMainMenu();
            mainMenu.add(BossLabel, BorderLayout.CENTER);
            Menus.put("BossMenu", BossLabel);
            mainMenu.revalidate();
            mainMenu.repaint();
        }
       // mainMenu.add(BossLabel, BorderLayout.CENTER);
    }
    private JFrame getMainMenu(){
        return (JFrame) Menus.get("MainMenu");
    }
}

