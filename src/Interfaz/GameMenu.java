package Interfaz;

import Characters.BaseCharacter;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class GameMenu {
    public GameMenu(JFrame mainWindow, String selectedCharacter, String selectedBoss) {
        String classPath = "Characters.Player." + selectedCharacter;
        try {
            System.out.println(selectedCharacter + selectedBoss);
            Class<?> foundClass = Class.forName(classPath);
            Object character = foundClass.getDeclaredConstructor().newInstance();

            // Character creado

            // Falta el boss (Pasar selectedBoss como clase)

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
