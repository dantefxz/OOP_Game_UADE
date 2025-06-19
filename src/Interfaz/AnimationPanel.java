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

    // Fijamos un tamaño estándar para el panel
    private final int panelWidth = 256;
    private final int panelHeight = 256;

    public AnimationPanel(String pj, String character, String animationName) {
        try {
            String path;
            if (pj.equals("Bosses")) {
                path = "src/Assets/Images/Sprites/Bosses/" + character + "/" + animationName + ".png";
            } else {
                path = "src/Assets/Images/Sprites/Characters/" + character + "/" + animationName + ".png";
            }

            spriteSheet = ImageIO.read(new File(path));

            frameHeight = spriteSheet.getHeight();
            totalFrames = spriteSheet.getWidth() / frameHeight;
            frameWidth = spriteSheet.getWidth() / totalFrames;

            // Timer para animar automáticamente
            timer = new Timer(120, e -> {
                currentFrame = (currentFrame + 1) % totalFrames;
                repaint();
            });
            timer.start();

            setPreferredSize(new Dimension(panelWidth, panelHeight));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (spriteSheet != null) {
            int x = currentFrame * frameWidth;

            // Centro del panel
            int drawX = (panelWidth - frameWidth) / 2;
            int drawY = (panelHeight - frameHeight) / 2;

            g.drawImage(spriteSheet.getSubimage(x, 0, frameWidth, frameHeight),
                    drawX, drawY, frameWidth, frameHeight, null);
        }
    }
}
