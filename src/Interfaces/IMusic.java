package Interfaces;

import Misc.Music;

public interface IMusic {
    Music getInstance();
    void playMusicFromResource(String resourcePath);
    void stopMusic();
}
