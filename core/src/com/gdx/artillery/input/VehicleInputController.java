package com.gdx.artillery.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gdx.artillery.ArtilleryGame;
import com.gdx.artillery.config.GameConfig;
import com.gdx.artillery.entity.ArtilleryVehicle;
import com.gdx.artillery.screen.game.GameController;

/**
 * Created by Raniel Agno on 1/2/2018.
 */

public class VehicleInputController {

    // == attributes ==
    private ArtilleryVehicle vehicle;
    private GameController controller;
    private final Rectangle screenLeftSide;
    private final Rectangle screenRightSide;

    // == constructors ==
    public VehicleInputController(ArtilleryVehicle vehicle, GameController controller) {
        this.vehicle = vehicle;
        this.controller = controller;

        float halfWorldWidth = GameConfig.WORLD_WIDTH / 2;
        float controllerPortionOfWorldHeight = vehicle.getY() * 2 + vehicle.getHeight();
        screenLeftSide = new Rectangle(0, 0, halfWorldWidth, controllerPortionOfWorldHeight);
        screenRightSide = new Rectangle(halfWorldWidth, 0, halfWorldWidth, controllerPortionOfWorldHeight);
    }

    // == public methods ==
    public void update(float delta) {

        float x = vehicle.getX();
        float y = vehicle.getY();

        if (Gdx.input.isTouched()) {
            float screenX = Gdx.input.getX();
            float screenY = Gdx.input.getY();

            Vector2 screenCoordinates = new Vector2(screenX, screenY); // in pixels
            Vector2 worldCoordinates = controller.screenToWorld(screenCoordinates); // world units

            if (isLeftSideTouched(worldCoordinates)) {
                x -= GameConfig.VEHICLE_SPEED * delta;
            } else if (isRightSideTouched(worldCoordinates)) {
                x += GameConfig.VEHICLE_SPEED * delta;
            }

            // Logic for block vehicle to leave world bounds
            if (x <= 0)
                x = 0;
            else if ((x + vehicle.getWidth()) >= GameConfig.WORLD_WIDTH )
                x = GameConfig.WORLD_WIDTH - vehicle.getWidth();

            vehicle.setPosition(x, y);

        }
    }

    private boolean isLeftSideTouched(Vector2 worldCoordinates) {
        return screenLeftSide.contains(worldCoordinates);
    }

    private boolean isRightSideTouched(Vector2 worldCoordinates) {
        return screenRightSide.contains(worldCoordinates);
    }
}
