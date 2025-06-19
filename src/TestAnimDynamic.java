import Interfaz.AnimationPanel;

import javax.swing.*;

public class TestAnimDynamic {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Animación en el lugar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Usa cualquier animación que tengas: "Idle", "Run", "Attack1", etc.
        AnimationPanel panel = new AnimationPanel("Bosses", "Corrupted_Boss", "Attack");

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
