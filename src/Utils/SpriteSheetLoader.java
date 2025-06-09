package Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class SpriteSheetLoader {

    /**
     * Carga una imagen desde el path proporcionado (debe estar en /resources)
     * @param resourcePath ruta dentro del classpath, por ejemplo "/Assets/Images/Sprites/Bosses/Necromancer_Boss/Necromancer_Boss.png"
     * @return la imagen cargada como BufferedImage
     */
    public static BufferedImage loadSpriteSheet(String resourcePath) {
        try {
            URL url = SpriteSheetLoader.class.getResource(resourcePath);
            if (url == null) {
                throw new IOException(resourcePath + " resourcePath not founded");
            }
            return ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
