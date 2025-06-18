package Interfaz;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AnimationPanel extends JPanel {
    private BufferedImage spriteSheet;
    private int frameWidth;
    private int frameHeight;
    private int currentFrame = 0;
    private int totalFrames;
    private Timer timer;

    public AnimationPanel(String pj, String character, String animationName) {
        try {
            spriteSheet = ImageIO.read(new File("src/Assets/Images/Sprites/" + pj +"/"+ character +"/" + animationName + ".png"));
            frameHeight = spriteSheet.getHeight();
            frameWidth = frameHeight; // Asumimos que cada frame es cuadrado
            totalFrames = spriteSheet.getWidth() / frameWidth;

            timer = new Timer(120, e -> {
                currentFrame = (currentFrame + 1) % totalFrames;
                repaint();
            });
            timer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setPreferredSize(new Dimension(200, 200)); // Cambiá según el tamaño deseado
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (spriteSheet != null) {
            int x = currentFrame * frameWidth;
            g.drawImage(spriteSheet.getSubimage(x, 0, frameWidth, frameHeight), 0, 0, frameWidth, frameHeight, null);
        }
    }
}
