package Interfaces;

import Characters.BaseCharacter;
import Misc.AttackManager;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public interface IAttackingCore {
    void clearKeybindings();
    void executeAttack(BaseCharacter origin, BaseCharacter target, AttackManager attack) throws IOException ;
    void updateSkillList(List<AttackManager> orderedAttacks, List<JLabel> attackLabels);
    void restartGame();
    void bossAttack(BaseCharacter selectedBoss, BaseCharacter selectedCharacter);
}
