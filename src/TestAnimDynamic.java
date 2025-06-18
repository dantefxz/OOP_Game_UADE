import Interfaz.AnimationPanel;

import javax.swing.*;

public class TestAnimDynamic {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sprite Animation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Cambiá el nombre para ver otra animación: "Idle", "Run", "Death", etc.
        AnimationPanel panel = new AnimationPanel("Bosses","Knight_Boss","Run");

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
