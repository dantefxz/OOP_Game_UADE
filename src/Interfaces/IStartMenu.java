package Interfaces;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public interface IStartMenu {
    Font loadCustomFont(float size);
    void CharacterMenu()  throws IOException;
    void BossMenu() throws IOException ;
    JFrame getMainMenu();
}
