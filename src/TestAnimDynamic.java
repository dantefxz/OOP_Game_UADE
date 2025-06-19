import Interfaz.AnimationPanel;

import javax.swing.*;
import java.awt.*;

public class TestAnimDynamic {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Animación en el lugar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Usa cualquier animación que tengas: "Idle", "Run", "Attack1", etc.
        AnimationPanel panel = new AnimationPanel("Bosses", "Corrupted_Boss", "Attack");

        frame.add(panel);
        panel.setBackground(Color.black);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
