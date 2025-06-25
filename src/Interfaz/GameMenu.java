package Interfaz;

import Characters.BaseCharacter;
import Interfaces.IGameMenu;
import Misc.AttackManager;
import Misc.Music;
import Misc.BackgroundPanel;

import javax.swing.*;
import java.awt.*;
import Misc.AttackingCore;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;


/*
 Clase principal del menú de combate en el juego.
 Se encarga de instanciar personajes, colocar sprites, mostrar habilidades
 y gestionar la lógica inicial del combate.
 */
public class GameMenu implements IGameMenu {
    public List<JLabel> attackLabels = new ArrayList<>(); // Etiquetas para mostrar ataques
    public JLabel textLabel = null;                       // Etiqueta para mensajes generales
    public JPanel skillList = null;                       // Panel donde se listan habilidades
    public BackgroundPanel backgroundPanel = null;        // Fondo de combate
    public JFrame mainWindow;                             // Ventana principal
    public String selectedBoss;                           // Necesario para saber la clase del jefe

    /*
     Constructor principal del menú de combate.
     Carga las clases de personaje y jefe seleccionados, sus sprites, fondo, música, etc.
     */
    public GameMenu(JFrame mainWindow, String selectedCharacter, String selectedBoss) {
        String characterPath = "Characters.Player." + selectedCharacter;
        String bossPath = "Characters.Bosses." + selectedBoss;
        try {
            // Carga las clases dinámicamente con reflexión
            Class<?> foundCharacter = Class.forName(characterPath);
            BaseCharacter character = (BaseCharacter) foundCharacter.getDeclaredConstructor().newInstance();

            Class<?> foundBoss = Class.forName(bossPath);
            BaseCharacter boss = (BaseCharacter) foundBoss.getDeclaredConstructor().newInstance();
            this.selectedBoss = selectedBoss;

            // Crea y aplica el fondo de batalla
            this.backgroundPanel = new BackgroundPanel("/Assets/Images/Sprites/Background/" + selectedBoss + ".png");
            this.backgroundPanel.setLayout(null);
            this.mainWindow = mainWindow;
            mainWindow.setContentPane(  this.backgroundPanel);
            mainWindow.pack();

            // Carga los sprites del jugador y del jefe
            AnimationPanel characterSprite = createCharacterSprite(selectedCharacter, "Idle");
            this.backgroundPanel.add(characterSprite);
            character.setSprite(characterSprite);

            AnimationPanel bossSprite = createBossSprite(selectedBoss, "Idle");
            this.backgroundPanel.add(bossSprite);
            boss.setSprite(bossSprite);

            // Muestra las habilidades del personaje
            JPanel skillList = getSkillList(character);
            skillList.setBounds(400, 100, 200, 80);
            this.backgroundPanel.add(skillList);

            // Etiqueta para mensajes de combate
            JLabel textLabel = getTextLabel();
            textLabel.setText("");
            textLabel.setBounds(160, 560, 5000, 80);
            textLabel.setForeground(Color.white);
            textLabel.setFont(new Font("Arial", Font.BOLD, 25));
            this.backgroundPanel.add(textLabel);

            // Inicia la lógica de combate entre jugador y jefe
            new AttackingCore(this, character, boss);

            mainWindow.repaint();
            mainWindow.revalidate();

            // Reproduce música del jefe
            Music.getInstance().stopMusic();
            Music.getInstance().playMusicFromResource("/Assets/Sounds/" + selectedBoss + ".mp3");
        } catch (Exception e) {
            e.printStackTrace(); // Manejo de errores generales
        }
    }

    /*
     Crea el sprite del jefe con posicionamiento según el tipo de enemigo.
     */
    public AnimationPanel createBossSprite(String selectedBoss, String animationName) {
        AnimationPanel bossSprite = new AnimationPanel("Boss", selectedBoss, animationName);
        switch (selectedBoss) {
            case "Golem":
                bossSprite.setBounds(700, -180, 300 * 3, 300 * 3);
                break;
            case "Demon":
                bossSprite.setBounds(700, -300, 300 * 3, 300 * 3);
                break;
            case "Knight":
                bossSprite.setBounds(700, 175, 300 * 2, 300 * 2);
                break;
            case "Necromancer":
                bossSprite.setBounds(700, 100, 300 * 2, 300 * 2);
                break;
            case "Corrupted":
                bossSprite.setBounds(700, 345, 300, 300);
                break;
            default:
                bossSprite.setBounds(700, 700, 300, 300);
                break;
        }
        return bossSprite;
    }

    /*
     Crea el sprite del jugador con dimensiones y posición según el personaje elegido.
     */
    public AnimationPanel createCharacterSprite(String selectedCharacter, String animationName) {
        AnimationPanel characterSprite = new AnimationPanel("Player", selectedCharacter, animationName);
        switch (selectedCharacter) {
            case "Warrior", "Tank", "Wizard":
                characterSprite.setBounds(200, 250, 300, 300);
                break;
            case "Cthulhu":
                characterSprite.setBounds(200, -50, 300 * 2, 300 * 2);
                break;
        }
        return characterSprite;
    }

    /*
     Construye el panel de habilidades a partir de los ataques del personaje.
     */
    public JPanel getSkillList(BaseCharacter selectedCharacter) {
        if (this.skillList != null) {
            return this.skillList;
        }
        this.skillList = new JPanel();
        this.skillList.setOpaque(true);
        this.skillList.setBackground(Color.white);
        this.skillList.setLayout(new GridLayout(5, 2));
        Map<String, AttackManager> attackList = selectedCharacter.getAttacksList();
        int currentKey = 0;
        for (Map.Entry<String, AttackManager> entry : attackList.entrySet()) {
            currentKey += 1;
            String attackName = entry.getKey();
            JLabel attackLabel = new JLabel(currentKey + " - " + attackName);
            this.skillList.add(attackLabel);
            this.attackLabels.add(attackLabel);
        }

        return this.skillList;
    }

    /*
     Devuelve la etiqueta para mostrar texto general del combate.
     */
    public JLabel getTextLabel() {
        if (this.textLabel == null) {
            this.textLabel = new JLabel();
        }

        return this.textLabel;
    }

    //Devuelve la lista de etiquetas que representan ataques (para actualizar estilo, etc).
    public List<JLabel> getAttackLabels() {
        return this.attackLabels;
    }

    /*
     Permite cargar animaciones personalizadas dependiendo del personaje y tipo de ataque.
     Útil para añadir efectos visuales especiales (como hechizos o habilidades épicas).
     */
    public void customSprite(String characterType, String className, String animationName) {
        //AnimationPanel spellsprite = new AnimationPanel(characterType, className, animationName);
        switch (className) {
            case "Necromancer":
                //Se crean efectos adicionales para que algunos efectos sean mas epicos
                if (Objects.equals(animationName, "Attack")) {
                    AnimationPanel spellsprite = new AnimationPanel(characterType, className, "AttackSpell");
                    spellsprite.setBounds(150, 200, 300, 300);
                    backgroundPanel.add(spellsprite);
                    break;
                } else if (Objects.equals(animationName, "SpecialAttack")) {
                    AnimationPanel spellsprite = new AnimationPanel(characterType, className, "SpecialAttackSpell");
                    spellsprite.setBounds(245, 350, 150, 150);
                    backgroundPanel.add(spellsprite);
                    break;
                }
                break;
            case "Corrupted":
                if (Objects.equals(animationName, "SpecialAttack")) {
                    AnimationPanel spellsprite = new AnimationPanel(characterType, className, "Light");
                    spellsprite.setBounds(150, 150, 150 * 2, 150 * 2);
                    backgroundPanel.add(spellsprite);
                    break;
                }
        }
        mainWindow.repaint();
        mainWindow.revalidate();
    }
}