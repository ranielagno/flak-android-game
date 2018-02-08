package com.gdx.artillery.entity;

import com.badlogic.gdx.math.MathUtils;
import com.gdx.artillery.config.GameConfig;

/**
 * Created by Raniel Agno on 12/28/2017.
 */

public class EnemyVehicle extends EntityBase {

    private boolean enemyAlive = true;
    private boolean vehicleFromLeft;
    private int speed;

    public EnemyVehicle() {
    }

    public void update(float delta) {

        if (isVehicleFromLeft()) {
            x += delta * speed;
        } else {
            x -= delta * speed;
        }

        updateBounds();
    }

    public boolean isEnemyAlive() {
        return enemyAlive;
    }

    public boolean isVehicleFromLeft() {
        return vehicleFromLeft;
    }

    public void setEnemyAlive(boolean enemyAlive) {
        this.enemyAlive = enemyAlive;
    }

    public void setVehicleFromLeft(boolean vehicleFromLeft) {
        this.vehicleFromLeft = vehicleFromLeft;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
