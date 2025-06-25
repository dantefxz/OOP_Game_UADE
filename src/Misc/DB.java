package Misc;

import Interfaces.IDB;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase DB:
 * Maneja la lógica de almacenamiento persistente de datos del jugador,
 * como jefes derrotados y desbloqueo de clases especiales, en un archivo JSON local.
 */
public class DB implements IDB {
    private JSONObject db; // Objeto que representa los datos del archivo JSON
    private Path dataPath; // Ruta al archivo de datos (data.json)

    //Constructor: inicializa la ruta del archivo y carga o crea los datos
    public DB() throws IOException {
        dataPath = PathHelper.getDataPath("data.json");
        load(); // Carga los datos al iniciar
    }

     //Carga los datos desde el archivo JSON si existe, o crea uno nuevo si no
    public void load() throws IOException {
        if (Files.exists(dataPath)) {
            String content = new String(Files.readAllBytes(dataPath));
            db = new JSONObject(content);
        } else {
            // Si el archivo no existe, inicializar estructura vacía
            db = new JSONObject();
            db.put("bossesDefeated", new JSONArray()); // Lista de jefes vencidos
            db.put("hasSpecialClass", false); // Clase especial bloqueada por defecto
            save();
        }
    }
    // Guarda el estado actual del objeto JSON al archivo
    public void save() throws IOException {
        Files.write(dataPath, db.toString(4).getBytes()); // Guarda con indentación
    }

    //Devuelve un conjunto de IDs de jefes que ya fueron vencidos
    public Set<Integer> getBossesDefeated() {
        Set<Integer> bosses = new HashSet<>();
        JSONArray array = db.getJSONArray("bossesDefeated");
        for (int i = 0; i < array.length(); i++) {
            bosses.add(array.getInt(i));
        }
        return bosses;
    }

    //Verifica si el jugador desbloqueó la clase especial
    public boolean hasSpecialClass() {
        return db.getBoolean("hasSpecialClass");
    }

    //Agrega un jefe a la lista de jefes vencidos y desbloquea la clase especial si corresponde
    public void addBossDefeated(int bossNumber) throws IOException {
        System.out.println("Agregado jefe derrotado: " + bossNumber);
        Set<Integer> bosses = getBossesDefeated();
        boolean isNew = bosses.add(bossNumber); // true si fue agregado
        if (isNew) {
            db.put("bossesDefeated", new JSONArray(bosses));
            if (bossNumber == 5) {
                setSpecialClassUnlocked(true); // Esto guarda
            } else {
                save();
            }
        }
    }

    //Establece el valor booleano de si la clase especial fue desbloqueada o no
    public void setSpecialClassUnlocked(boolean unlocked) throws IOException {
        db.put("hasSpecialClass", unlocked);
        save();
    }
}
