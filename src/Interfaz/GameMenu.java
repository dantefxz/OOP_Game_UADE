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

public class GameMenu implements IGameMenu {
    public List<JLabel> attackLabels = new ArrayList<>();
    public JLabel textLabel = null;
    public JPanel skillList = null;
    public BackgroundPanel backgroundPanel = null;
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

            this.backgroundPanel = new BackgroundPanel("/Assets/Images/Sprites/Background/" + selectedBoss + ".png");
            this.backgroundPanel.setLayout(null);
            mainWindow.setContentPane(  this.backgroundPanel);
            mainWindow.pack();

            AnimationPanel characterSprite = createCharacterSprite(selectedCharacter, "Idle");
            this.backgroundPanel.add(characterSprite);
            character.setSprite(characterSprite);

            AnimationPanel bossSprite = createBossSprite(selectedBoss, "Idle");
            this.backgroundPanel.add(bossSprite);
            boss.setSprite(bossSprite);

            JPanel skillList = getSkillList(character);
            skillList.setBounds(400, 100, 200, 80);
            this.backgroundPanel.add(skillList);

            JLabel textLabel = getTextLabel();
            textLabel.setText("");
            textLabel.setBounds(160, 560, 5000, 80);
            textLabel.setForeground(Color.white);
            textLabel.setFont(new Font("Arial", Font.BOLD, 25));
            this.backgroundPanel.add(textLabel);

            new AttackingCore(this, character, boss);

            mainWindow.repaint();
            mainWindow.revalidate();


            Music.getInstance().stopMusic();
            Music.getInstance().playMusicFromResource("/Assets/Sounds/" + selectedBoss + ".mp3");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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


    public AnimationPanel createCharacterSprite(String selectedCharacter, String animationName) {
        AnimationPanel characterSprite = new AnimationPanel("Player", selectedCharacter, animationName);
        switch (selectedCharacter) {
            case "Warrior", "Tank", "Wizard":
                characterSprite.setBounds(200, 250, 300, 300);
                break;
            case "Cthulhu":
                characterSprite.setBounds(200, -300, 300 * 2, 300 * 2);
                break;
        }
        return characterSprite;
    }


    public AnimationPanel getNecromancerAttack(String selectedBoss, String animationName) {
        AnimationPanel spellsprite = new AnimationPanel("Boss", selectedBoss, animationName);
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

    public JLabel getTextLabel() {
        if (this.textLabel == null) {
            this.textLabel = new JLabel();
        }

        return this.textLabel;
    }

    public List<JLabel> getAttackLabels() {
        return this.attackLabels;
    }

    public void customSprite(String characterType, String className, String animationName) {
        //AnimationPanel spellsprite = new AnimationPanel(characterType, className, animationName);
        switch (className) {
            case "Necromancer":
                //Se crean efectos adicionales para que algunos efectos sean mas epicos
                if (Objects.equals(animationName, "Attack")) {
                    AnimationPanel spellsprite = new AnimationPanel(characterType, className, "AttackSpell");
                    spellsprite.setBounds(150, 200, 300, 300);
                    break;
                } else if (animationName == "SpecialAttack") {
                    AnimationPanel spellsprite = new AnimationPanel(characterType, className, "SpecialAttackSpell");
                    spellsprite.setBounds(245, 350, 150, 150);
                    break;
                }
                break;
            case "Corrupted":
                if (Objects.equals(animationName, "SpecialAttack")) {
                    AnimationPanel spellsprite = new AnimationPanel(characterType, className, "Light");
                    spellsprite.setBounds(150, 200, 150, 150);
                    break;
                }
        }
    }
}