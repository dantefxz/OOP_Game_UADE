package Misc;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Graphics;
import java.net.URL;

/**
 * Panel personalizado que permite establecer una imagen de fondo.
 * Utilizado principalmente para fondos de pantalla en el menú o durante el combate.
 */

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    //Constructor que carga la imagen desde un path dentro de los recursos del proyecto
    public BackgroundPanel(String imagePath) {
        URL imageUrl = getClass().getResource(imagePath);
        if (imageUrl != null) {
            backgroundImage = new ImageIcon(imageUrl).getImage();
        } else {
            System.err.println("No se pudo encontrar la imagen: " + imagePath);
        }
    }

    //Método sobrescrito que dibuja la imagen de fondo ajustada al tamaño del panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

}
