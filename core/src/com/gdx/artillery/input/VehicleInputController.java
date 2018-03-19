package com.gdx.artillery.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gdx.artillery.common.LevelState;
import com.gdx.artillery.common.SoundController;
import com.gdx.artillery.config.GameConfig;
import com.gdx.artillery.entity.ArtilleryVehicle;
import com.gdx.artillery.screen.game.GameController;
import com.gdx.artillery.screen.game.GameWorld;

/**
 * Created by Raniel Agno on 1/2/2018.
 */

public class VehicleInputController {

    // == attributes ==
    private GameWorld gameWorld;
    private ArtilleryVehicle vehicle;
    private GameController controller;
    private SoundController soundController;
    private final Rectangle screenLeftSide;
    private final Rectangle screenRightSide;

    // == constructors ==
    public VehicleInputController(GameWorld gameWorld, GameController controller) {
        this.gameWorld = gameWorld;
        this.vehicle = gameWorld.getVehicle();
        this.controller = controller;
        this.soundController = gameWorld.getSoundController();

        float halfWorldWidth = GameConfig.WORLD_WIDTH / 2;
        float controllerPortionOfWorldHeight = vehicle.getY() * 2 + vehicle.getHeight();
        screenLeftSide = new Rectangle(0, 0, halfWorldWidth, controllerPortionOfWorldHeight);
        screenRightSide = new Rectangle(halfWorldWidth, 0, halfWorldWidth, controllerPortionOfWorldHeight);
    }

    // == public methods ==
    public void update(float delta) {

        float x = vehicle.getX();
        float y = vehicle.getY();

        for (int i = 0; i < 2; i++) {

            LevelState currentLevel = gameWorld.getCurrentLevel();

            if (Gdx.input.isTouched(i)) {
                float screenX = Gdx.input.getX(i);
                float screenY = Gdx.input.getY(i);

                Vector2 screenCoordinates = new Vector2(screenX, screenY); // in pixels
                Vector2 worldCoordinates = controller.screenToWorld(screenCoordinates); // world units

                if (isLeftSideTouched(worldCoordinates)) {
                    x -= GameConfig.VEHICLE_SPEED * delta;
                } else if (isRightSideTouched(worldCoordinates)) {
                    x += GameConfig.VEHICLE_SPEED * delta;
                }

                soundChecker(currentLevel, true);

                // Logic for block vehicle to leave world bounds
                if (x <= 0)
                    x = 0;
                else if ((x + vehicle.getWidth()) >= GameConfig.WORLD_WIDTH)
                    x = GameConfig.WORLD_WIDTH - vehicle.getWidth();

                vehicle.setPosition(x, y);

            } else {
                soundChecker(currentLevel, false);
            }



        }
    }

    private void soundChecker(LevelState currentLevel, boolean play) {
        if (currentLevel == LevelState.Level1) {
            if (play) {
                soundController.tankMoveSound();
            } else {
                soundController.tankMoveSoundStop();
            }
        } else if (currentLevel == LevelState.Level2) {
            if (play) {
                soundController.shipMoveSound();
            } else {
                soundController.shipMoveSoundStop();
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
