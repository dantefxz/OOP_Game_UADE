package Misc;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathHelper {

    public static Path getDataPath(String filename) {
        String path = PathHelper.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath();
        try {
            path = java.net.URLDecoder.decode(path, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Path dataPath;

        if (path.endsWith(".jar")) {
            // Arreglamos la ruta malformada con prefijo "/C:/..." en Windows
            Path jarDir = Paths.get(path.startsWith("/") && path.length() > 2 && path.charAt(2) == ':' ? path.substring(1) : path).getParent();
            dataPath = jarDir.resolve(filename);
        } else {
            Path projectRoot = Paths.get("").toAbsolutePath();
            dataPath = projectRoot.resolve(filename);
        }

        return dataPath;
    }
}
