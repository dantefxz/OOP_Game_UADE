package Interfaz;
import javax.swing.*;
import java.awt.*;

public class gameMenu extends JPanel {
    private final int TILE_SIZE = 25;
    private final int WIDTH = 20;
    private final int HEIGHT = 20;

    public gameMenu(){
        this.setPreferredSize(new Dimension(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
      //  this.addKeyListener(this);
    }
}
