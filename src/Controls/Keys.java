package Controls;

import javax.swing.*;


public class Keys {

    public static void bindKeys(JComponent component,
                                Runnable onAttack,
                                Runnable onHeal,
                                Runnable onUltimate,
                                Runnable onEscape) {

        InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        inputMap.put(KeyStroke.getKeyStroke("pressed J"), "attack");
        inputMap.put(KeyStroke.getKeyStroke("pressed K"), "heal");
        inputMap.put(KeyStroke.getKeyStroke("pressed L"), "ultimate");
        inputMap.put(KeyStroke.getKeyStroke("pressed O"), "escape");

    }
}
