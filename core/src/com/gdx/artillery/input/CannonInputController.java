package com.gdx.artillery.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import com.gdx.artillery.config.GameConfig;
import com.gdx.artillery.entity.ArtilleryCannon;
import com.gdx.artillery.entity.ArtilleryVehicle;
import com.gdx.artillery.screen.game.GameController;
import com.gdx.artillery.screen.game.GameWorld;

/**
 * Created by Raniel Agno on 1/2/2018.
 */

public class CannonInputController {

    private static final Logger LOGGER = new Logger(GameWorld.class.getName(), Logger.DEBUG);

    // == attributes ==
    private ArtilleryCannon cannon;
    private ArtilleryVehicle vehicle;
    private GameController controller;
    private final Rectangle screenLeftSide;
    private final Rectangle screenRightSide;

    // == constructors ==
    public CannonInputController(ArtilleryCannon cannon, ArtilleryVehicle vehicle, GameController controller) {
        this.cannon = cannon;
        this.controller = controller;
        this.vehicle = vehicle;

        float halfWorldWidth = GameConfig.WORLD_WIDTH / 2;
        float startingPointY = vehicle.getY() * 2 + vehicle.getHeight();
        float controllerHeight = GameConfig.WORLD_HEIGHT - startingPointY;

        screenLeftSide = new Rectangle(0, startingPointY, halfWorldWidth, controllerHeight);
        screenRightSide = new Rectangle(halfWorldWidth, startingPointY, halfWorldWidth, controllerHeight);
    }

    // == public methods ==
    public void update(float delta) {

        cannon.setPosition(vehicle.getX() + GameConfig.CANNON_SCALE_X, GameConfig.CANNON_Y);

        float rotation = cannon.getRotation();

        for (int i = 0; i < 2; i++) {

            if (Gdx.input.isTouched(i)) {
                float screenX = Gdx.input.getX(i);
                float screenY = Gdx.input.getY(i);

                Vector2 screenCoordinates = new Vector2(screenX, screenY); // in pixels
                Vector2 worldCoordinates = controller.screenToWorld(screenCoordinates); // world units

                if (isLeftSideTouched(worldCoordinates)) {
                    rotation += GameConfig.CANNON_TURNING_SPEED * delta;
                } else if (isRightSideTouched(worldCoordinates)) {
                    rotation -= GameConfig.CANNON_TURNING_SPEED * delta;
                }

                if (rotation <= 60 && rotation >= -60)
                    cannon.setRotation(rotation);

                //LOGGER.debug("cannon x = " + cannon.getX());
                //LOGGER.debug("cannnon y = " + cannon.getY());
            }

        }
    }

    private boolean isLeftSideTouched(Vector2 worldCoordinates) {
        return screenLeftSide.contains(worldCoordinates);
    }

    private boolean isRightSideTouched(Vector2 worldCoordinates) {
        return screenRightSide.contains(worldCoordinates);
    }
}
