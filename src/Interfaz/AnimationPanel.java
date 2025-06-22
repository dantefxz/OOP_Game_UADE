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

    private Container parentContainer; // para auto-remover el panel

    public AnimationPanel(String folder, String name, String animation) {
        this.folder = folder;
        this.name = name;
        this.animation = animation;

        Dimension fixedSize = new Dimension(getWidth(), getHeight());
        setPreferredSize(fixedSize);
        setMinimumSize(fixedSize);
        setMaximumSize(fixedSize);
        setOpaque(false);
        setBounds(0, 0, fixedSize.width, fixedSize.height);
        try {
            loadAnimation(animation);
        } catch (IOException e) {
            System.err.println("No se pudo cargar la animación: " + e.getMessage());
        }
    }

    public void setParentContainer(Container parent) {
        this.parentContainer = parent;
    }

    public void loadAnimation(String animName) throws IOException {
        String name = this.name;
        String folder = this.folder;
        String path = "src/Assets/Images/Sprites/" + folder + "/" + name + "/" + animName + ".png";
        System.out.println("Cargando sprite desde: " + path);
        BufferedImage spriteSheet = ImageIO.read(new File(path));
        frames = cortarFrames(spriteSheet, folder.equalsIgnoreCase("Bosses"));
        frames = normalizarFrames(frames);

        currentFrame = 0;
        loopingIdle = animName.equalsIgnoreCase("Idle");

        if (timer != null) timer.stop();
        timer = new Timer(100, e -> {
            currentFrame++;
            if (currentFrame >= frames.length) {
                if (loopingIdle) {
                    currentFrame = 0;
                } else if (animation.equalsIgnoreCase("AttackSpell") || animation.equalsIgnoreCase("SpecialAttackSpell")) {
                    timer.stop();
                    if (parentContainer != null) {
                        parentContainer.remove(this);
                        parentContainer.repaint();
                    }
                } else {
                    try {
                        loadAnimation("Idle");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
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

    private BufferedImage[] normalizarFrames(BufferedImage[] frames) {
        int maxWidth = 0;
        int height = frames[0].getHeight();

        for (BufferedImage frame : frames) {
            if (frame.getWidth() > maxWidth) {
                maxWidth = frame.getWidth();
            }
        }

        BufferedImage[] normalized = new BufferedImage[frames.length];
        for (int i = 0; i < frames.length; i++) {
            BufferedImage frame = frames[i];
            if (frame.getWidth() == maxWidth) {
                normalized[i] = frame;
            } else {
                BufferedImage newFrame = new BufferedImage(maxWidth, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = newFrame.createGraphics();
                g2d.drawImage(frame, 0, 0, null);
                g2d.dispose();
                normalized[i] = newFrame;
            }
        }
        return normalized;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (frames != null && currentFrame < frames.length && frames[currentFrame] != null) {
            BufferedImage frame = frames[currentFrame];
            g.drawImage(frame, 0, 0, getWidth(), getHeight(), null);
        }
    }
}
