package Interfaces;

import java.util.Set;

public interface IDB {
    void load();
    void save();
    Set<Integer> getBossesDefeated();
    boolean hasSpecialClass();
    void addBossDefeated(int bossNumber);
    void setSpecialClassUnlocked(boolean unlocked);
}
