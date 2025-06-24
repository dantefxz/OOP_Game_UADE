package Interfaz;

import Characters.BaseCharacter;
import Misc.AttackManager;
import Misc.Music;
import Misc.BackgroundPanel;

import javax.swing.*;
import java.awt.*;
import Misc.setKeybinding;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class GameMenu {
    private Image backgroundImage;
    static List<JLabel> attackLabels = new ArrayList<>();

    public GameMenu(JFrame mainWindow, String selectedCharacter, String selectedBoss) {
        String characterPath = "Characters.Player." + selectedCharacter;
        String bossPath = "Characters.Bosses." + selectedBoss;
        try {
            System.out.println(selectedCharacter + selectedBoss);
            Class<?> foundCharacter = Class.forName(characterPath);
            BaseCharacter character = (BaseCharacter) foundCharacter.getDeclaredConstructor().newInstance();
            System.out.println(character.getAttacksList());

            Class<?> foundBoss = Class.forName(bossPath);
            BaseCharacter boss = (BaseCharacter) foundBoss.getDeclaredConstructor().newInstance();

            BackgroundPanel backgroundPanel = new BackgroundPanel("/Assets/Images/Sprites/Background/" + selectedBoss + ".png");
            backgroundPanel.setLayout(null);
            mainWindow.setContentPane(backgroundPanel);
            mainWindow.pack();

            AnimationPanel characterSprite = getCharacterSprite(selectedCharacter, "Idle");
            backgroundPanel.add(characterSprite);

            AnimationPanel bossSprite = getBossSprite(selectedBoss, "Idle");
            backgroundPanel.add(bossSprite);

            JPanel skillList = createSkillList(character);
            skillList.setBounds(400, 100, 200, 80);
            backgroundPanel.add(skillList);

            JLabel textLabel = createTextLabel();
            textLabel.setText("Testando aodisdiusjoaf");
            textLabel.setBounds(160, 560, 5000, 80);
            textLabel.setForeground(Color.white);
            textLabel.setFont(new Font("Arial", Font.BOLD, 25));
            backgroundPanel.add(textLabel);

            new setKeybinding(skillList, textLabel, attackLabels, character, characterSprite, boss);

            mainWindow.repaint();
            mainWindow.revalidate();


            Music.getInstance().stopMusic();
            Music.getInstance().playMusicFromResource("/Assets/Sounds/" + selectedBoss + ".mp3");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static AnimationPanel getBossSprite(String selectedBoss, String animationName) {
        AnimationPanel bossSprite = new AnimationPanel("Bosses", selectedBoss, animationName);
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


    private static AnimationPanel getCharacterSprite(String selectedCharacter,  String animationName) {
        AnimationPanel characterSprite = new AnimationPanel("Characters", selectedCharacter, animationName);
        switch (selectedCharacter) {
            case "Warrior", "Tank", "Wizard":
                characterSprite.setBounds(200, 250, 300, 300);
                break;
            case "Cthulhu":
                characterSprite.setBounds(200, -300, 300 * 3, 300 * 3);
                break;
        }
        return characterSprite;
    }


    private static AnimationPanel getNecromancerAttack(String selectedBoss, String animationName) {
        AnimationPanel spellsprite = new AnimationPanel("Bosses", selectedBoss, animationName);
        switch (animationName) {
            case "AttackSpell":
                spellsprite.setBounds(150, 200, 300, 300);
                break;
            case "SpecialAttackSpell":
                spellsprite.setBounds(245, 350, 150, 150);
                break;
            default:
                spellsprite.setBounds(200, 250, 150, 150);
                break;
        }
        return spellsprite;
    }

    private static JPanel createSkillList(BaseCharacter selectedCharacter){
        JPanel skillList = new JPanel();
        skillList.setOpaque(true);
        skillList.setBackground(Color.white);
        skillList.setLayout(new GridLayout(5,2));
        Map<String, AttackManager> attackList = selectedCharacter.getAttacksList();
        int currentKey = 0;
        for (Map.Entry<String, AttackManager> entry : attackList.entrySet()) {
            currentKey += 1;
            String attackName = entry.getKey();
            JLabel attackLabel = new JLabel(currentKey + " - "+ attackName);
            skillList.add(attackLabel);
            attackLabels.add(attackLabel);
        }

        return skillList;
    }

    private static JLabel createTextLabel(){
        return new JLabel();
    }
}