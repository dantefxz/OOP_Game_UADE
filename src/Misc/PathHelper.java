package Misc;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Clase PathHelper
 * Se encarga de obtener rutas absolutas hacia archivos de datos (por ejemplo, "data.json"),
 * compatible tanto con ejecución desde IDE como desde archivo .jar empaquetado.
 */
public class PathHelper {

    //Devuelve la ruta absoluta al archivo de datos, compatible con ejecución desde JAR o desde IDE.
    public static Path getDataPath(String filename) {

        // Obtiene la ruta del archivo JAR o del directorio /bin o /out en IDE
        String path = PathHelper.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath();
        // Decodifica por si la ruta contiene caracteres especiales (espacios, símbolos, etc.)
        try {
            path = java.net.URLDecoder.decode(path, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace(); // Imprime cualquier error
        }

        Path dataPath;

        if (path.endsWith(".jar")) {
            // Si se está ejecutando desde un archivo .jar
            // Arreglamos la ruta malformada con prefijo "/C:/..." en Windows
            Path jarDir = Paths.get(path.startsWith("/") && path.length() > 2 && path.charAt(2) == ':' ? path.substring(1) : path).getParent();
            dataPath = jarDir.resolve(filename);
        } else {
            Path projectRoot = Paths.get("").toAbsolutePath(); // Ruta raíz del proyecto
            dataPath = projectRoot.resolve(filename); // Archivo estará al lado del proyecto
        }

        return dataPath;
    }
}
