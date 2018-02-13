package com.gdx.artillery.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.gdx.artillery.ArtilleryGame;

/**
 * Created by Raniel Agno on 1/5/2018.
 */

public class ScoreController {

    // == constants ==
    private static final String HIGH_SCORE_KEY = "highScore";

    // == attributes ==
    private final Preferences prefs;

    private int score;
    private int highScore;

    // == constructors ==
    public ScoreController() {
        prefs = Gdx.app.getPreferences(ArtilleryGame.class.getSimpleName());
        highScore = prefs.getInteger(HIGH_SCORE_KEY, 0);
    }

    // == public methods ==
    public void reset() {
        score = 0;
    }

    public void addScore(int toAdd) {
        score += toAdd;
    }

    public void updateHighScore() {
        if(score < highScore) {
            return;
        }

        highScore = score;
        prefs.putInteger(HIGH_SCORE_KEY, highScore);
        prefs.flush();
    }

    public void setScore(int score) {
        this.score = score;
    }
    public int getScore() {
        return score;
    }

    public String getScoreString() {
        return Integer.toString(score);
    }

    public String getHighScoreString() {
        return Integer.toString(highScore);
    }

}
