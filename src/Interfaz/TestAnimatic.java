package Interfaz;
import Characters.Bosses.NecromancerBossAnimation;

import javax.swing.*;
import java.awt.*;

public class TestAnimatic {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test Necromancer Animation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 300);
            frame.setLayout(new BorderLayout());

            // Animación
            NecromancerBossAnimation necroAnim = new NecromancerBossAnimation();
            necroAnim.setPreferredSize(new Dimension(96, 96));

            // Botones de prueba
            JPanel controlPanel = new JPanel();
            JButton attackBtn = new JButton("Attack");
            JButton damageBtn = new JButton("Damage");
            JButton deathBtn = new JButton("Death");

            attackBtn.addActionListener(e -> necroAnim.playAttack());
            damageBtn.addActionListener(e -> necroAnim.playDamage());
            deathBtn.addActionListener(e -> necroAnim.playDeath());

            controlPanel.add(attackBtn);
            controlPanel.add(damageBtn);
            controlPanel.add(deathBtn);

            // Añadir al frame
            frame.add(necroAnim, BorderLayout.CENTER);
            frame.add(controlPanel, BorderLayout.SOUTH);

            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
