package Interfaces;

import java.io.IOException;
import java.util.Set;

public interface IDB {
    void load() throws IOException  ;
    void save()throws IOException  ;
    Set<Integer> getBossesDefeated()throws IOException  ;
    boolean hasSpecialClass()throws IOException  ;
    void addBossDefeated(int bossNumber) throws IOException;
    void setSpecialClassUnlocked(boolean unlocked) throws IOException;
}
