package Interfaces;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface IAnimationPanel {
    void setParentContainer(Container parent);
    void loadAnimation(String animName);
    BufferedImage[] cortarFrames(BufferedImage spriteSheet, boolean reverseOrder);
    BufferedImage[] normalizarFrames(BufferedImage[] frames);
    void paintComponent(Graphics g);
}
