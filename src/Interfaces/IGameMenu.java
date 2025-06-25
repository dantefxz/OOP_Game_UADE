package Interfaces;

import Characters.BaseCharacter;
import Interfaz.AnimationPanel;

import javax.swing.*;
import java.util.List;

public interface IGameMenu {
    AnimationPanel createBossSprite(String selectedBoss, String animationName);
    AnimationPanel createCharacterSprite(String selectedCharacter,  String animationName);
    JPanel getSkillList(BaseCharacter selectedCharacter);
    JLabel getTextLabel();
    List<JLabel> getAttackLabels();
    void customSprite(String characterType, String className, String animationName);
}
