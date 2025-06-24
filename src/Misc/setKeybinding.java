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
                        if (attack.getTurns() > 0) {
                            textLabel.setText("No se puede ejecutar. Turnos: " + attack.getTurns());
                            return;
                        }
                        executeAttack(selectedCharacter, selectedBoss, attack, textLabel, currentLabel, key);

                        if (OGTurns > 0){ // Si los turnos originales son 0 y esto se lee, significa que se ejecutó, por ende reiniciar turnos.
                            attack.setTurns(OGTurns);
                        }

                        updateSkillList(orderedAttacks, attackLabels);


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

    private static void executeAttack(BaseCharacter origin, BaseCharacter target, AttackManager attack, JLabel textLabel, JLabel currentLabel, String key){
        double oldTargetHealth = target.getHealth();
        attack.execute(origin, target);
        double originHealth = origin.getHealth();
        double targetHealth = target.getHealth();
        boolean critic = (oldTargetHealth - targetHealth) > attack.getDamage();
        textLabel.setText(origin.getName() + " (" + originHealth+") Atacó a "+target.getName()+" ("+oldTargetHealth+") -> ("+targetHealth+")");
        if (critic && targetHealth != 0){
            textLabel.setText(textLabel.getText() + " CRÍTICO");
        }

        // Para el jugador
        for (Map.Entry<String, AttackManager> entry : origin.getAttacksList().entrySet()) {
            AttackManager attackValue = entry.getValue();
            if (attackValue.getTurns() > 0) {
                attackValue.subtractTurn();
            }

        }
    }

    private static void updateSkillList(List<AttackManager> orderedAttacks, List<JLabel> attackLabels){
        for (int i = 0; i < orderedAttacks.size(); i++) {
            int index = i;
            String key = String.valueOf(i + 1);
            AttackManager attack = orderedAttacks.get(index);
            JLabel currentLabel = attackLabels.get(index);
            currentLabel.setText(key + " - " + attack.getName() + "(" + attack.getTurns() + ")");
            if (attack.getTurns() > 0) {
                attackLabels.get(index).setForeground(Color.RED);
            } else {
                attackLabels.get(index).setForeground(Color.BLACK);
            }
        }
    }
}
