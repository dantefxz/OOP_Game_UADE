package Interfaz;

import Misc.DB;
import Misc.Music;
import Interfaz.GameMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.awt.FontFormatException;
import java.util.Set;

public class StartMenu {
    HashMap<String, Object> Menus = new HashMap<>();
    String selectedCharacter;
    String selectedBoss;
    String[] Boss_Name = {"N/A","Sir Malrik, Champion of the Light", "Malphite, the King of the Mountains", "Mortuus, Master of the Undead", "Ashgore, The Last Ember of Wrath", "Sir Malrik, the Corrupted"};
    String[] Boss_Class = {"N/A", "Knight", "Golem", "Necromancer", "Demon", "Corrupted"};
    String[] Character_Class = {"N/A","Warrior", "Tank", "Wizard", "Secret", "Cthulhu"};
    private Font loadCustomFont(float size) {
        try {
            URL fontUrl = getClass().getResource("/Assets/Fonts/Dungeon.ttf");
            if (fontUrl == null) {
                System.err.println("Fuente Dungeon no encontrada.");
                return new Font("Serif", Font.BOLD, (int) size);
            }

            Font font = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream());
            font = font.deriveFont(size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            return font;
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            return new Font("Serif", Font.BOLD, (int) size);
        }
    }
    public StartMenu(JFrame newMainMenu) throws IOException {
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
        Music.getInstance().playMusicFromResource("/Assets/Sounds/MainMenu.mp3");
        CharacterMenu();

    }
    // Letra personalizada

    public void CharacterMenu() throws IOException {
        // Character selection
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
            String imagePath;
            DB currentDB = new DB();
            boolean specialClass = currentDB.hasSpecialClass();
            if (n == 4) { // special character Sprite
                if (specialClass){
                    imagePath = "/Assets/Images/Sprites/Characters/Player" + n + "_preview.png";
                } else {
                    imagePath = "/Assets/Images/Sprites/Characters/Secret.png";
                }
            }else{
                imagePath = "/Assets/Images/Sprites/Characters/Player" + n + "_preview.png";
            }
            URL imageUrl = StartMenu.class.getResource(imagePath);

            if (imageUrl != null) {
                ImageIcon characterIcon = new ImageIcon(imageUrl);
                Image scaledImage = characterIcon.getImage().getScaledInstance(buttonSize.width, buttonSize.height, Image.SCALE_SMOOTH);
                Character.setIcon(new ImageIcon(scaledImage));
            } else {
                System.out.println("Imagen no encontrada para Character " + n + ": " + imagePath);
                Character.setText(Character_Class[n]);
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
            JLabel nameLabel = new JLabel();

            if (n == 4) { // special character name
                if (specialClass){
                    n = n +1;
                    nameLabel = new JLabel(Character_Class[n]);
                }else{
                    nameLabel = new JLabel(Character_Class[n]);
                }
            }else{
                nameLabel = new JLabel(Character_Class[n]);
            }

            nameLabel.setForeground(Color.WHITE);
            nameLabel.setFont(loadCustomFont(22f));
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Espaciado superior

            // Seleccionar
            int finalN = n;

            if (n == 4) {
                if (specialClass) {
                    int finalN1 = n;
                    Character.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JPanel currentMenu = (JPanel) Menus.get("CharacterMenu");
                            JFrame mainMenu = getMainMenu();
                            mainMenu.remove(currentMenu);
                            Menus.remove("CharacterMenu");
                            selectedCharacter = Character_Class[finalN1];
                            try {
                                BossMenu();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    });
                } else {
                    // Si no tiene acceso al personaje secreto
                    Character.setEnabled(false); // Desactiva el botón
                    Character.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)); // Cambia el cursor
                }
            } else {
                // Personajes normales
                Character.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JPanel currentMenu = (JPanel) Menus.get("CharacterMenu");
                        JFrame mainMenu = getMainMenu();
                        mainMenu.remove(currentMenu);
                        Menus.remove("CharacterMenu");
                        selectedCharacter = Character_Class[finalN];
                        try {
                            BossMenu();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
            }

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

    }
    public void BossMenu() throws IOException {
        JFrame mainMenu = getMainMenu();

        // Boss selection
        JPanel BossLabel = new JPanel();
        BossLabel.setLayout(new BoxLayout(BossLabel, BoxLayout.X_AXIS));
        BossLabel.setBackground(Color.BLACK);
        BossLabel.setPreferredSize(new Dimension(150, 150));
        BossLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        Dimension buttonSize = new Dimension(210, 360);

        // Botón volver
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        bottomPanel.add(Box.createVerticalGlue());

        JButton backButton = new JButton("Volver");
        backButton.setFont(loadCustomFont(18f));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBackground(new Color(50, 50, 50));
        backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setMaximumSize(new Dimension(200, 40));
        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel bossMenu = (JPanel) Menus.get("BossMenu");
                if (bossMenu != null) {
                    mainMenu.remove(bossMenu);
                    Menus.remove("BossMenu");
                }
                try {
                    CharacterMenu();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                mainMenu.remove(bottomPanel);
                mainMenu.revalidate();
                mainMenu.repaint();
            }
        });
        bottomPanel.add(backButton);
        mainMenu.add(bottomPanel, BorderLayout.SOUTH);

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
                Boss.setText(Boss_Name[n]);
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
            JLabel nameLabel = new JLabel(Boss_Name[n]);
            nameLabel.setForeground(Color.WHITE);
            nameLabel.setFont(loadCustomFont(16f));
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Espaciado superior

            // Seleccionar
            DB currentDB = new DB();
            Set<Integer> BossesDefeated = currentDB.getBossesDefeated();

            int finalN = n;
            if (BossesDefeated.contains(finalN - 1) || finalN == 1) {
                Boss.setEnabled(true);
                Boss.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedBoss = Boss_Class[finalN];
                        //JPanel currentMenu = (JPanel) Menus.get("BossMenu");
                        JFrame mainMenu = getMainMenu();
                        mainMenu.getContentPane().removeAll();
                        mainMenu.revalidate();
                        mainMenu.repaint();
                        GameMenu gameMenu = new GameMenu(getMainMenu(), selectedCharacter, selectedBoss);
                        Menus.put("GameMenu", gameMenu);
                        Menus.remove("MainMenu");
                        Menus.remove("BossMenu");
                    }
                });
            }else{
                Boss.setEnabled(true); // Botón habilitado para mostrar mensaje, pero bloqueado lógicamente
                Boss.setText("Boss " + finalN + " 🔒");

                Boss.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null,
                                "Debes vencer a " + (Boss_Name[finalN - 1]) + " antes de enfrentar a " + Boss_Name[finalN],
                                "Boss Bloqueado",
                                JOptionPane.WARNING_MESSAGE);
                    }
                });

            }

            // Añadir al panel individual
            bossPanel.add(Boss);
            bossPanel.add(nameLabel);

            BossLabel.add(bossPanel);
            if (n < 5) {
                BossLabel.add(Box.createRigidArea(new Dimension(43, 0)));
            }
        }

        // Final
        mainMenu.add(BossLabel, BorderLayout.CENTER);
        Menus.put("BossMenu", BossLabel);
        mainMenu.revalidate();
        mainMenu.repaint();


    }

    private JFrame getMainMenu(){
        return (JFrame) Menus.get("MainMenu");
    }

}