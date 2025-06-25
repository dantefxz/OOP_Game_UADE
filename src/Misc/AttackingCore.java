package Misc;

import Characters.BaseCharacter;
import java.util.*;
import Interfaz.GameMenu;
import Interfaz.StartMenu;
import MainExecute.Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AttackingCore {
    JPanel skillList;
    JLabel textLabel;
    List<JLabel> attackLabels;
    public AttackingCore(GameMenu gameMenu, BaseCharacter selectedCharacter, BaseCharacter selectedBoss){
        Map<String, AttackManager> attackList = selectedCharacter.getAttacksList();
        Map<String, AttackManager> bossAttacksList = selectedBoss.getAttacksList();
        List<AttackManager> orderedAttacks = new ArrayList<>(attackList.values());
        this.skillList = gameMenu.getSkillList(null);
        this.textLabel = gameMenu.getTextLabel();
        this.attackLabels = gameMenu.getAttackLabels();

        JRootPane rootPane = SwingUtilities.getRootPane(skillList);

        selectedCharacter.setCanAttack(true); // Empieza atacando el jugador siempre
        selectedBoss.setCanAttack(false);

        if (rootPane != null) {
            InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = rootPane.getActionMap();

            inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "restartGame");
            actionMap.put("restartGame", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                  restartGame(rootPane);
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
                        } else if (!selectedCharacter.getCanAttack()){
                           return;
                        }

                        try {
                            executeAttack(selectedCharacter, selectedBoss, attack);
                            updateSkillList(orderedAttacks, attackLabels);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                        if (OGTurns > 0){ // Si los turnos originales son 0 y esto se lee, significa que se ejecutó, por ende reiniciar turnos.
                            attack.setTurns(OGTurns);
                        }

                        new javax.swing.Timer(4000, new AbstractAction() { // ataca el jefe después de 2 segundos
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                ((javax.swing.Timer) e.getSource()).stop(); // Parar timer

                                if (!bossAttacksList.isEmpty()) {
                                    List<String> availableKeys = new ArrayList<>(bossAttacksList.keySet());
                                    Random rand = new Random();

                                    while (!availableKeys.isEmpty()) {
                                        String randomKey = availableKeys.get(rand.nextInt(availableKeys.size()));
                                        AttackManager bossAttack = bossAttacksList.get(randomKey);

                                        if (bossAttack.getTurns() <= 0) {
                                            try {
                                                executeAttack(selectedBoss, selectedCharacter, bossAttack);
                                            } catch (IOException ex) {
                                                throw new RuntimeException(ex);
                                            }
                                            System.out.println("El jefe usó: " + randomKey);
                                            break;
                                        } else {
                                            availableKeys.remove(randomKey);
                                        }
                                    }
                                }
                            }
                        }).start();
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

    private void executeAttack(BaseCharacter origin, BaseCharacter target, AttackManager attack) throws IOException {
        double oldTargetHealth = target.getHealth();
        origin.setCanAttack(false);
        origin.getSprite().loadAnimation(attack.getName());
        attack.execute(origin, target);

        double originHealth = origin.getHealth();
        double targetHealth = target.getHealth();
        boolean critic = (oldTargetHealth - targetHealth) > attack.getDamage();
        this.textLabel.setText(origin.getName() + " (" + originHealth+") Atacó a "+target.getName()+" ("+oldTargetHealth+") -> ("+targetHealth+")");
        if (critic && targetHealth != 0){
            this.textLabel.setText(textLabel.getText() + " CRÍTICO");
        }

        // Para el jugador
        for (Map.Entry<String, AttackManager> entry : origin.getAttacksList().entrySet()) {
            AttackManager attackValue = entry.getValue();
            if (attackValue.getTurns() > 0) {
                attackValue.subtractTurn();
            }

        }

        // Para el enemigo
        for (Map.Entry<String, AttackManager> entry : target.getAttacksList().entrySet()) {
            AttackManager attackValue = entry.getValue();
            if (attackValue.getTurns() > 0) {
                attackValue.subtractTurn();
            }

        }

        new javax.swing.Timer(2000, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textLabel.setText("Turno de " + target.getName());
                target.setCanAttack(true);
                ((javax.swing.Timer) e.getSource()).stop();
            }
        }).start();


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

    public static void restartGame(JRootPane rootPane){
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
}
