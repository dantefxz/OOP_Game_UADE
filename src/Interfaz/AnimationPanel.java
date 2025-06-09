package Interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimationPanel extends JPanel {
    private BufferedImage[] frames;
    private int currentFrame = 0;
    private int frameDelay = 100; // milisegundos entre frames
    private Timer timer;

    public AnimationPanel(BufferedImage[] frames) {
        this.frames = frames;
        setPreferredSize(new Dimension(frames[0].getWidth(), frames[0].getHeight()));
        setOpaque(false); // Para que puedas superponerlo si querés

        // Timer para actualizar el frame
        timer = new Timer(frameDelay, e -> {
            currentFrame = (currentFrame + 1) % frames.length;
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (frames != null && frames[currentFrame] != null) {
            g.drawImage(frames[currentFrame], 0, 0, null);
        }
    }

    public void setFrames(BufferedImage[] newFrames) {
        this.frames = newFrames;
        this.currentFrame = 0;
        repaint();
    }

    public void setFrameDelay(int delayMillis) {
        this.frameDelay = delayMillis;
        timer.setDelay(delayMillis);
    }

    public void stopAnimation() {
        timer.stop();
    }

    public void startAnimation() {
        timer.start();
    }
}
