package Misc;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class DB {
    private JSONObject db;
    private Path dataPath;

    public DB() throws IOException {
        dataPath = PathHelper.getDataPath("data.json");
        load();
    }

    private void load() throws IOException {
        if (Files.exists(dataPath)) {
            String content = new String(Files.readAllBytes(dataPath));
            db = new JSONObject(content);
        } else {
            // Si no existe, crear data
            db = new JSONObject();
            db.put("bossesDefeated", new JSONArray());
            db.put("hasSpecialClass", false);
            save();
        }
    }

    private void save() throws IOException {
        Files.write(dataPath, db.toString(4).getBytes());
    }

    // Métodos
    public Set<Integer> getBossesDefeated() {
        Set<Integer> bosses = new HashSet<>();
        JSONArray array = db.getJSONArray("bossesDefeated");
        for (int i = 0; i < array.length(); i++) {
            bosses.add(array.getInt(i));
        }
        return bosses;
    }

    public boolean hasSpecialClass() {
        return db.getBoolean("hasSpecialClass");
    }

    //
    public void addBossDefeated(int bossNumber) throws IOException {
        Set<Integer> bosses = getBossesDefeated();
        if (!bosses.contains(bossNumber)) {
            bosses.add(bossNumber);
            db.put("bossesDefeated", new JSONArray(bosses));
            save();
        }
    }

    public void setSpecialClassUnlocked(boolean unlocked) throws IOException {
        db.put("hasSpecialClass", unlocked);
        save();
    }

    // --- Método main para pruebas ---
    public static void main(String[] args) throws IOException {
        DB db = new DB();

        System.out.println("Bosses derrotados: " + db.getBossesDefeated());
        System.out.println("Clase especial desbloqueada: " + db.hasSpecialClass());

        //db.addBossDefeated(3);

        //db.setSpecialClassUnlocked(true);

        System.out.println("Datos actualizados:");
        System.out.println("Bosses derrotados: " + db.getBossesDefeated());
        System.out.println("Clase especial desbloqueada: " + db.hasSpecialClass());
    }
}
