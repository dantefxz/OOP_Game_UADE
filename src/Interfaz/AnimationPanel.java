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

    private final int panelWidth = 300;
    private final int panelHeight = 300;

    public AnimationPanel(String pj, String character, String animationName) {
        try {
            isBoss = pj.trim().equalsIgnoreCase("Bosses");
            System.out.println("pj recibido: '" + pj + "' → isBoss = " + isBoss);

            String path;
            if (isBoss) {
                path = "src/Assets/Images/Sprites/Bosses/" + character + "/" + animationName + ".png";
            } else {
                path = "src/Assets/Images/Sprites/Characters/" + character + "/" + animationName + ".png";
            }

            spriteSheet = ImageIO.read(new File(path));
            frameHeight = spriteSheet.getHeight();

            totalFrames = detectarFrames(spriteSheet);
            int separacion = 4;
            frameWidth = (spriteSheet.getWidth() + separacion) / totalFrames - separacion;

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

        int separacion = 4;
        int x;

        if (!isBoss) {
            // Characters: de izquierda a derecha
            x = currentFrame * (frameWidth + separacion);
        } else {
            // Bosses: de derecha a izquierda visualmente
            x = (totalFrames - 1 - currentFrame) * (frameWidth + separacion);
        }

        x = Math.max(0, Math.min(x, spriteSheet.getWidth() - frameWidth));

        BufferedImage frame = spriteSheet.getSubimage(x, 0, frameWidth, frameHeight);
        Image scaledFrame = frame.getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH);

        int xCentrado = (getWidth() - panelWidth) / 2;
        int yCentrado = (getHeight() - panelHeight) / 2;

        g.drawImage(scaledFrame, xCentrado, yCentrado, null);
    }

    private int detectarFrames(BufferedImage spriteSheet) {
        int width = spriteSheet.getWidth();
        int height = spriteSheet.getHeight();
        int count = 0;
        boolean enFrame = false;

        for (int x = 0; x < width; x++) {
            boolean columnaVacia = true;
            for (int y = 0; y < height; y++) {
                int pixel = spriteSheet.getRGB(x, y);
                if ((pixel >> 24) != 0x00) {
                    columnaVacia = false;
                    break;
                }
            }
            if (!columnaVacia && !enFrame) {
                count++;
                enFrame = true;
            } else if (columnaVacia) {
                enFrame = false;
            }
        }

        return count;
    }
}
