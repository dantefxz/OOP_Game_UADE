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
    public setKeybinding(JPanel skillList, JLabel textLabel, List<JLabel> attackLabels, BaseCharacter selectedCharacter, AnimationPanel characterSprite, BaseCharacter selectedBoss){
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
                AttackManager attack = orderedAttacks.get(index);
                int OGTurns = attack.getTurns();
                JLabel currentLabel = attackLabels.get(index);
                currentLabel.setText(key + " - "+ attack.getName() + "("+attack.getTurns()+")");
                if (attack.getTurns() > 1){
                    attackLabels.get(index).setForeground(Color.RED);
                } else {
                    attackLabels.get(index).setForeground(Color.BLACK);
                }

                inputMap.put(KeyStroke.getKeyStroke(key), "attack_" + key);
                actionMap.put("attack_" + key, new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        AttackManager attack = orderedAttacks.get(index);
                        if (attack.getTurns() > 1) {
                            textLabel.setText("No se puede ejecutar. Turnos: " + attack.getTurns());
                            attackLabels.get(index).setForeground(Color.RED);
                            return;
                        }
                        System.out.println("Ejecutando ataque: " + attack.getName());
                        attackLabels.get(index).setForeground(Color.WHITE);
                        double oldBossHealth = selectedBoss.getHealth();
                        attack.execute(selectedCharacter, selectedBoss);
                        double characterHealth = selectedCharacter.getHealth();
                        double bossHealth = selectedBoss.getHealth();
                        boolean critic = (oldBossHealth - bossHealth) > attack.getDamage();
                        textLabel.setText(selectedCharacter.getName() + " (" + characterHealth+") Atacó a "+selectedBoss.getName()+" ("+oldBossHealth+") -> ("+bossHealth+")");
                        if (critic && bossHealth != 0){
                            textLabel.setText(textLabel.getText() + " CRÍTICO");
                        }

                        if (OGTurns > 0){
                            attack.setTurns(OGTurns);
                            attackLabels.get(index).setForeground(Color.RED);
                            currentLabel.setText(key + " - "+ attack.getName() + "("+attack.getTurns()+")");
                        }

                        // para todos :v
                        currentLabel.setText(key + " - "+ attack.getName() + "("+attack.getTurns()+")");
                        if (attack.getTurns() > 1){
                            attackLabels.get(index).setForeground(Color.RED);
                        } else {
                            attackLabels.get(index).setForeground(Color.BLACK);
                        }

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
