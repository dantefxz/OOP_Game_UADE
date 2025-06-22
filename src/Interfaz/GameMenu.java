package Interfaz;

import Characters.BaseCharacter;
import Misc.Music;
import Misc.BackgroundPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.net.URL;

public class GameMenu {
    private Image backgroundImage;

    public GameMenu(JFrame mainWindow, String selectedCharacter, String selectedBoss) {
        String characterPath = "Characters.Player." + selectedCharacter;
        String bossPath = "Characters.Bosses." + selectedBoss;
        try {
            System.out.println(selectedCharacter + selectedBoss);
            Class<?> foundCharacter = Class.forName(characterPath);
            BaseCharacter character = (BaseCharacter) foundCharacter.getDeclaredConstructor().newInstance();

            Class<?> foundBoss = Class.forName(bossPath);
            BaseCharacter boss = (BaseCharacter) foundBoss.getDeclaredConstructor().newInstance();

            BackgroundPanel backgroundPanel = new BackgroundPanel("/Assets/Images/Sprites/Background/" + selectedBoss + ".png");
            backgroundPanel.setLayout(null);
            mainWindow.setContentPane(backgroundPanel);

            AnimationPanel characterSprite = getCharacterSprite(selectedCharacter, "Idle");
            backgroundPanel.add(characterSprite);

            AnimationPanel bossSprite = getBossSprite(selectedBoss, "Idle");
            backgroundPanel.add(bossSprite);

            String Animation = "Attack";
            if (selectedBoss.equals("Necromancer")) {
                bossSprite.loadAnimation(Animation);
                if (Animation.equals("Attack")) {
                    AnimationPanel Spell = getNecromancerAttack(selectedBoss, "AttackSpell");
                    backgroundPanel.add(Spell);
                }
                if (Animation.equals("SpecialAttack")) {
                    AnimationPanel Spell = getNecromancerAttack(selectedBoss,"SpecialAttackSpell");
                    backgroundPanel.add(Spell);
                }
            }

            mainWindow.repaint();
            mainWindow.revalidate();
            mainWindow.pack();
            //bossSprite.cargarAnimacion("Attack");
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
                bossSprite.setBounds(700, 130, 300, 300);
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
}