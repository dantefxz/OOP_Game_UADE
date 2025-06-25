package Interfaz;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Clase responsable de manejar la animación de sprites en forma de panel de Swing.
 * Carga una sprite sheet desde disco, corta los frames y reproduce una animación.
 */
public class AnimationPanel extends JPanel {
    private BufferedImage[] frames; // Arreglo de frames individuales de la animación
    private int currentFrame = 0;   // Frame actual que se está mostrando
    private Timer timer;            // Timer que controla el avance de la animación

    private final String folder;    // Carpeta del personaje
    private final String name;      // Nombre del personaje
    private final String animation; // Animación inicial

    private boolean loopingIdle = false;

    private Container parentContainer; // para auto-remover el panel

    // Constructor que inicializa el panel con una animación específica
    public AnimationPanel(String folder, String name, String animation) {
        this.folder = folder;
        this.name = name;
        this.animation = animation;

        // Se establece un tamaño fijo del panel
        Dimension fixedSize = new Dimension(getWidth(), getHeight());
        setPreferredSize(fixedSize);
        setMinimumSize(fixedSize);
        setMaximumSize(fixedSize);
        setOpaque(false);
        setBounds(0, 0, fixedSize.width, fixedSize.height);
        try {
            loadAnimation(animation); // Carga la animación inicial
        } catch (IOException e) {
            System.err.println("No se pudo cargar la animación: " + e.getMessage());
        }
    }

    public void setParentContainer(Container parent) {
        this.parentContainer = parent;
    }

    // Carga una animación desde disco, corta y normaliza los frames, y arranca el timer
    public void loadAnimation(String animName) throws IOException {
        String name = this.name;
        String folder = this.folder;
        String path = "src/Assets/Images/Sprites/" + folder + "/" + name + "/" + animName + ".png";
        BufferedImage spriteSheet = ImageIO.read(new File(path));

        // Corta los frames de la sprite sheet
        frames = cortarFrames(spriteSheet, folder.equalsIgnoreCase("Boss"));
        // Normaliza los frames para que tengan el mismo ancho
        frames = normalizarFrames(frames);

        currentFrame = 0;
        loopingIdle = animName.equalsIgnoreCase("Idle"); // Loop sólo si es idle

        // Si ya había un timer corriendo, se detiene
        if (timer != null) timer.stop();
        // Crea un nuevo timer para avanzar los frames cada 100ms
        timer = new Timer(100, e -> {
            currentFrame++;
            if (currentFrame >= frames.length) {
                // Si termina la animación y es Idle, vuelve a empezar
                if (loopingIdle) {
                    currentFrame = 0;
                }
                // Si es un hechizo, remueve el panel tras reproducirse
                else if (animation.equalsIgnoreCase("AttackSpell") || animation.equalsIgnoreCase("SpecialAttackSpell") || animation.equalsIgnoreCase("Light")) {
                    timer.stop();
                    if (parentContainer != null) {
                        parentContainer.remove(this);
                        parentContainer.repaint();
                    }
                }
                // Si no es ninguna especial, vuelve a Idle automáticamente
                else {
                    try {
                        loadAnimation("Idle");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            repaint(); // Fuerza redibujado con el nuevo frame
        });
        timer.start(); // Inicia la animación
    }

    private BufferedImage[] cortarFrames(BufferedImage spriteSheet, boolean reverseOrder) {
        int width = spriteSheet.getWidth();
        int height = spriteSheet.getHeight();
        boolean[] columnHasContent = new boolean[width];

        // Detecta qué columnas tienen contenido visible
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

        // Encuentra los puntos de inicio de cada frame
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

        // Corta las subimágenes según los puntos encontrados
        ArrayList<BufferedImage> frameList = new ArrayList<>();
        for (int i = 0; i < startPoints.size(); i++) {
            int startX = startPoints.get(i);
            int endX = (i + 1 < startPoints.size()) ? startPoints.get(i + 1) : width;
            int frameWidth = endX - startX;
            if (frameWidth > 1) {
                frameList.add(spriteSheet.getSubimage(startX, 0, frameWidth, height));
            }
        }

        // Si es jefe, se invierte el orden de los frames
        if (reverseOrder) {
            java.util.Collections.reverse(frameList);
        }

        return frameList.toArray(new BufferedImage[0]);
    }

    private BufferedImage[] normalizarFrames(BufferedImage[] frames) {
        int maxWidth = 0;
        int height = frames[0].getHeight();

        // Calcula el ancho máximo
        for (BufferedImage frame : frames) {
            if (frame.getWidth() > maxWidth) {
                maxWidth = frame.getWidth();
            }
        }

        // Añade relleno a los frames más angostos
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
