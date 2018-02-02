package com.gdx.artillery.screen.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import com.gdx.artillery.config.GameConfig;

/**
 * Created by Raniel Agno on 12/28/2017.
 */

public class GameController {

    // == constants
    private static final Logger LOGGER = new Logger(GameController.class.getName(), Logger.DEBUG);

    // == attributes ==
    private final GameWorld gameWorld;
    private final GameRenderer renderer;

    private enum LevelScreen {Level1, Level2, Level3}

    private LevelScreen currentLevel;

    private boolean gameOver;

    // == constructors ==
    public GameController(GameWorld gameWorld, GameRenderer renderer) {
        this.gameWorld = gameWorld;
        this.renderer = renderer;
        currentLevel = LevelScreen.Level1;
    }

    // == public methods ==
    public void update(float delta) {

        if (gameWorld.isGameTimerFinished()) {
            if (currentLevel == LevelScreen.Level1) {
                currentLevel = LevelScreen.Level2;
                renderer.initializeLevel2();
                gameWorld.resetGameTimer();
            } else if (currentLevel == LevelScreen.Level2) {
                gameOver = true;
            }
        }
        gameWorld.update(delta);
    }

    public Vector2 screenToWorld(Vector2 screenCoordinates) {

        return renderer.screenToWorld(screenCoordinates);
    }

    public boolean isGameOver() {
        return gameOver;
    }

}
