package Misc;

import Characters.BaseCharacter;
import java.util.*;

import Interfaces.IAttackingCore;
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

public class AttackingCore implements IAttackingCore {
    private final JPanel skillList;
    private final JLabel textLabel;
    private final List<JLabel> attackLabels;
    private final GameMenu gameMenu;
    private JRootPane rootPane;

    public AttackingCore(GameMenu gameMenu, BaseCharacter selectedCharacter, BaseCharacter selectedBoss){ // Hacer funcionar el núcleo del juego y heredar GameMenu y las instancias necesarias.
        Map<String, AttackManager> attackList = selectedCharacter.getAttacksList();
        List<AttackManager> orderedAttacks = new ArrayList<>(attackList.values());
        this.skillList = gameMenu.getSkillList(null);
        this.textLabel = gameMenu.getTextLabel();
        this.attackLabels = gameMenu.getAttackLabels();
        this.gameMenu = gameMenu;

        this.rootPane = SwingUtilities.getRootPane(skillList);

        selectedCharacter.setCanAttack(true); // Empieza atacando el jugador siempre
        selectedBoss.setCanAttack(false);

        if (rootPane != null) {
            InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = rootPane.getActionMap();

            inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "restartGame");
            actionMap.put("restartGame", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                  restartGame();
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
                            updateSkillList(orderedAttacks, attackLabels);
                        }

                        new javax.swing.Timer(4000, new AbstractAction() { // ataca el jefe después de 4 segundos
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                ((javax.swing.Timer) e.getSource()).stop(); // Parar timer
                                bossAttack(selectedBoss, selectedCharacter);
                            }
                        }).start();
                    }
                });
            }
        } else {
            System.err.println("No se pudo obtener rootPane para skillList");
        }
    }
    @Override
    public void clearKeybindings() {
        InputMap inputMap = this.rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.rootPane.getActionMap();

        inputMap.clear();
        actionMap.clear();
    }
    @Override
    public void executeAttack(BaseCharacter origin, BaseCharacter target, AttackManager attack) throws IOException {
        BaseCharacter selectedCharacter;
        BaseCharacter selectedBoss;
        System.out.println(origin.getType());
        if (Objects.equals(origin.getType(), "Player")){
            selectedCharacter = origin;
            selectedBoss = target;
        } else {
            selectedCharacter = target;
            selectedBoss = origin;
        }

        double oldTargetHealth = target.getHealth();
        origin.setCanAttack(false);
        origin.getSprite().loadAnimation(attack.getName());
        target.getSprite().loadAnimation("Hurt");
        this.gameMenu.customSprite(origin.getType(), origin.getName(), attack.getName()); // Revisar si hay un sprite extra en el ataque
        attack.execute(origin, target);

        double originHealth = origin.getHealth();
        double targetHealth = target.getHealth();
        boolean critic = (oldTargetHealth - targetHealth) > attack.getDamage();
        this.textLabel.setText(origin.getName() + " (" + originHealth+") Atacó a "+target.getName()+" ("+oldTargetHealth+") -> ("+targetHealth+")");
        if (critic && targetHealth != 0){
            this.textLabel.setText(textLabel.getText() + " CRÍTICO");
        }

        // Para el que ataca
        for (Map.Entry<String, AttackManager> entry : origin.getAttacksList().entrySet()) {
            AttackManager attackValue = entry.getValue();
            if (attackValue.getTurns() > 0) {
                System.out.println("Sustraje xd");
                attackValue.subtractTurn();
            }

        }

        // Checkear si el usuario ganó o perdió
        if (selectedCharacter.getHealth() <= 0){ // el usuario perdió
            textLabel.setText("El usuario ha perdido");
            System.out.println("PERDIO CHARACTER");
            selectedCharacter.getSprite().loadAnimation("Death");
            new javax.swing.Timer(4000, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ((javax.swing.Timer) e.getSource()).stop(); // Parar timer
                    restartGame();
                }
            }).start();
            return;
        }
        if (selectedBoss.getHealth() <= 0) { // el usuario ganó
            textLabel.setText("El usuario ha ganado.");
            selectedBoss.getSprite().loadAnimation("Death");
            new javax.swing.Timer(4000, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ((javax.swing.Timer) e.getSource()).stop();
                    try {
                        DB dataBase = new DB();
                        dataBase.addBossDefeated(selectedBoss.getID());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    restartGame();
                }
            }).start();
            return;
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
    @Override
    public void updateSkillList(List<AttackManager> orderedAttacks, List<JLabel> attackLabels){
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
    @Override
    public void restartGame(){
        try {
            JFrame mainWindow = Main.getMainWindow();
            mainWindow.getContentPane().invalidate();
            mainWindow.getContentPane().removeAll();
            new StartMenu(Main.getMainWindow());
            clearKeybindings();
            mainWindow.repaint();
            mainWindow.revalidate();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    @Override
    public void bossAttack(BaseCharacter selectedBoss, BaseCharacter selectedCharacter){
        Map<String, AttackManager> bossAttacksList = selectedBoss.getAttacksList();
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
}
