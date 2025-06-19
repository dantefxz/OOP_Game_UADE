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
    private boolean isBoss;

    // Fijamos un tamaño estándar para el panel
    private final int panelWidth = 256;
    private final int panelHeight = 256;

    public AnimationPanel(String pj, String character, String animationName) {
        try {
            String path;
            isBoss = pj.equals("Bosses");

            if (isBoss) {
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
                if (isBoss) {
                    currentFrame = (currentFrame - 1 + totalFrames) % totalFrames;
                } else {
                    currentFrame = (currentFrame + 1) % totalFrames;
                }
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

            BufferedImage frame = spriteSheet.getSubimage(x, 0, frameWidth, frameHeight);
            Image scaledFrame = frame.getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH);
            g.drawImage(scaledFrame, 0, 0, null);
        }
    }
}
