package Misc;

import Characters.BaseCharacter;
import Interfaz.AnimationPanel;
import Interfaz.StartMenu;
import MainExecute.Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class setKeybinding {
    public setKeybinding(JPanel skillList, BaseCharacter selectedCharacter, AnimationPanel characterSprite, BaseCharacter selectedBoss){
        Map<String, AttackManager> attackList = selectedCharacter.getAttacksList();
        List<AttackManager> orderedAttacks = new ArrayList<>(attackList.values());
        JRootPane rootPane = SwingUtilities.getRootPane(skillList);


        if (rootPane != null) {
            InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = rootPane.getActionMap();

            inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "restartGame");
            actionMap.put("restartGame", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        JFrame mainWindow = Main.getMainWindow();
                        mainWindow.getContentPane().invalidate();
                        mainWindow.getContentPane().removeAll();
                        new StartMenu(Main.getMainWindow());
                        clearKeybindings(rootPane);
                        mainWindow.repaint();
                        mainWindow.revalidate();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            for (int i = 0; i < orderedAttacks.size(); i++) {
                int index = i;
                String key = String.valueOf(i + 1);

                inputMap.put(KeyStroke.getKeyStroke(key), "attack_" + key);
                actionMap.put("attack_" + key, new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        AttackManager attack = orderedAttacks.get(index);
                        System.out.println("Ejecutando ataque: " + attack.getName());
                        attack.execute(selectedCharacter, selectedBoss);
                        try {
                            characterSprite.loadAnimation(attack.getName());
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
            }
        } else {
            System.err.println("No se pudo obtener rootPane para skillList");
        }
    }
    private static void clearKeybindings(JRootPane rootPane) {
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = rootPane.getActionMap();

        inputMap.clear();
        actionMap.clear();
    }
}
