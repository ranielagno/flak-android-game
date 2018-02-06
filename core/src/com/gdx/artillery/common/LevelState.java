package com.gdx.artillery.common;

/**
 * Created by Raniel on 2/6/2018.
 */

public enum LevelState {
    Level1,
    Level2,
    Level3;

    // == public methods ==
    public boolean isPlayingLevel1() { return this == Level1; }
    public boolean isPlayingLevel2() { return this == Level2; }
    public boolean isPlayingLevel3() { return this == Level3; }
}
