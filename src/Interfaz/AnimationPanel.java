package Interfaz;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AnimationPanel extends JPanel {
    private BufferedImage[] frames;
    private int currentFrame = 0;
    private Timer timer;

    private final String folder;
    private final String name;
    private final String animation;

    private boolean loopingIdle = false;

    public AnimationPanel(String folder, String name, String animation) {
        this.folder = folder;
        this.name = name;
        this.animation = animation;

        try {
            cargarAnimacion(folder, name, animation);
        } catch (IOException e) {
            System.err.println("No se pudo cargar la animación: " + e.getMessage());
        }
    }

    private void cargarAnimacion(String folder, String name, String animName) throws IOException {
        String path = "src/Assets/Images/Sprites/" + folder + "/" + name + "/" + animName + ".png";
        System.out.println("Cargando sprite desde: " + path);

        BufferedImage spriteSheet = ImageIO.read(new File(path));
        frames = cortarFrames(spriteSheet, folder.equalsIgnoreCase("Bosses"));

        int frameHeight = spriteSheet.getHeight();
        int maxFrameWidth = 0;
        for (BufferedImage frame : frames) {
            if (frame.getWidth() > maxFrameWidth) {
                maxFrameWidth = frame.getWidth();
            }
        }

        setPreferredSize(new Dimension(maxFrameWidth, frameHeight));
        setBackground(Color.BLACK);

        currentFrame = 0;
        loopingIdle = animName.equalsIgnoreCase("Idle");

        if (timer != null) timer.stop(); // reiniciar si ya existía
        timer = new Timer(100, e -> {
            currentFrame++;
            if (currentFrame >= frames.length) {
                if (!loopingIdle) {
                    try {
                        cargarAnimacion(folder, name, "Idle");
                    } catch (IOException ex) {
                        System.err.println("No se pudo cargar Idle: " + ex.getMessage());
                    }
                } else {
                    currentFrame = 0; // seguir en Idle
                }
            }
            repaint();
        });
        timer.start();
    }

    private BufferedImage[] cortarFrames(BufferedImage spriteSheet, boolean reverseOrder) {
        int width = spriteSheet.getWidth();
        int height = spriteSheet.getHeight();
        boolean[] columnHasContent = new boolean[width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = spriteSheet.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xff;
                if (alpha > 10) {
                    columnHasContent[x] = true;
                    break;
                }
            }
        }

        ArrayList<Integer> startPoints = new ArrayList<>();
        boolean inContent = false;
        for (int x = 0; x < width; x++) {
            if (columnHasContent[x]) {
                if (!inContent) {
                    startPoints.add(x);
                    inContent = true;
                }
            } else {
                inContent = false;
            }
        }

        ArrayList<BufferedImage> frameList = new ArrayList<>();
        for (int i = 0; i < startPoints.size(); i++) {
            int startX = startPoints.get(i);
            int endX = (i + 1 < startPoints.size()) ? startPoints.get(i + 1) : width;
            int frameWidth = endX - startX;
            if (frameWidth > 1) {
                frameList.add(spriteSheet.getSubimage(startX, 0, frameWidth, height));
            }
        }

        if (reverseOrder) {
            java.util.Collections.reverse(frameList);
        }

        return frameList.toArray(new BufferedImage[0]);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (frames != null && frames[currentFrame] != null) {
            BufferedImage frame = frames[currentFrame];
            int x = (getWidth() - frame.getWidth()) / 2;
            int y = (getHeight() - frame.getHeight()) / 2;
            g.drawImage(frame, x, y, null);
        }
    }
}
