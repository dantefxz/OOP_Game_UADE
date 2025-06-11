package Characters;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CharacterAnimation extends JPanel {
    protected BufferedImage spriteSheet;
    protected int frameWidth;
    protected int frameHeight;
    protected ArrayList<BufferedImage[]> animations = new ArrayList<>();
    protected int currentRow = 0;
    protected int currentFrame = 0;
    protected int animationSpeed = 100; // milisegundos
    protected Timer timer;
    protected int idleRow = 0;

    public CharacterAnimation(BufferedImage spriteSheet, int frameWidth, int frameHeight) {
        this.spriteSheet = spriteSheet;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;

        extractAnimations();
        startIdleLoop();
    }

    private void extractAnimations() {
        int totalRows = spriteSheet.getHeight() / frameHeight;
        for (int row = totalRows - 1; row >= 0; row--) {
            ArrayList<BufferedImage> rowFrames = new ArrayList<>();
            for (int col = 0; col < spriteSheet.getWidth() / frameWidth; col++) {
                BufferedImage frame = spriteSheet.getSubimage(col * frameWidth, row * frameHeight, frameWidth, frameHeight);
                if (!isFrameEmpty(frame)) {
                    rowFrames.add(frame);
                }
            }
            if (!rowFrames.isEmpty()) {
                animations.add(rowFrames.toArray(new BufferedImage[0]));
            }
        }
    }

    private boolean isFrameEmpty(BufferedImage frame) {
        for (int y = 0; y < frame.getHeight(); y++) {
            for (int x = 0; x < frame.getWidth(); x++) {
                if ((frame.getRGB(x, y) >> 24) != 0x00) {
                    return false;
                }
            }
        }
        return true;
    }

    private void startIdleLoop() {
        playAnimation(idleRow, true);
    }

    public void playAnimation(int row, boolean loop) {
        if (row >= animations.size()) return;

        this.currentRow = row;
        this.currentFrame = 0;

        if (timer != null) timer.stop();

        timer = new Timer(animationSpeed, e -> {
            repaint();
            currentFrame++;
            if (currentFrame >= animations.get(currentRow).length) {
                if (loop) {
                    currentFrame = 0;
                } else {
                    // Vuelve a idle al terminar
                    playAnimation(idleRow, true);
                }
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage frame = animations.get(currentRow)[currentFrame % animations.get(currentRow).length];
        g.drawImage(frame, 0, 0, null);
    }
}
