package Interfaces;

import javax.swing.*;
import java.awt.*;

public interface IStartMenu {
    Font loadCustomFont(float size);
    void CharacterMenu();
    void BossMenu();
    JFrame getMainMenu();
}
