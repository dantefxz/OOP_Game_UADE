package Misc;

import Interfaces.IDB;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class DB implements IDB {
    private JSONObject db;
    private Path dataPath;

    public DB() throws IOException {
        dataPath = PathHelper.getDataPath("data.json");
        load();
    }

    public void load() throws IOException {
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

    public void save() throws IOException {
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
            if (getBossesDefeated().contains(5)){
                setSpecialClassUnlocked(true);
            } else {
                save();
            }
        }
    }

    public void setSpecialClassUnlocked(boolean unlocked) throws IOException {
        db.put("hasSpecialClass", unlocked);
        save();
    }
}
