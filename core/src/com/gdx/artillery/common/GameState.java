package com.gdx.artillery.common;

/**
 * Created by Raniel on 2/5/2018.
 */

public enum GameState {
    MENU,
    PLAYING,
    PAUSE,
    GAME_OVER;

    // == public methods ==
    public boolean isMenu() { return this == MENU; }
    public boolean isPlaying() { return this == PLAYING; }
    public boolean isGameOver() { return this == GAME_OVER; }

}
