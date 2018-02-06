package com.gdx.artillery.screen.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import com.gdx.artillery.common.GameState;
import com.gdx.artillery.screen.dialog.OverlayCallback;

/**
 * Created by Raniel Agno on 12/28/2017.
 */

public class GameController {

    // == constants
    private static final Logger LOGGER = new Logger(GameController.class.getName(), Logger.DEBUG);

    // == attributes ==
    private final GameWorld gameWorld;
    private final GameRenderer renderer;

    // == constructors ==
    public GameController(GameWorld gameWorld, GameRenderer renderer) {
        this.gameWorld = gameWorld;
        this.renderer = renderer;

    }

    // == public methods ==
    public void update(float delta) {

        gameWorld.update(delta);
    }

    public Vector2 screenToWorld(Vector2 screenCoordinates) {

        return renderer.screenToWorld(screenCoordinates);
    }

}
