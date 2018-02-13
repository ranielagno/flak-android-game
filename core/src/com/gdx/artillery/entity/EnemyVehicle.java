package com.gdx.artillery.entity;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Raniel Agno on 12/28/2017.
 */

public class EnemyVehicle extends EntityBase implements Pool.Poolable {

    private boolean vehicleFromLeft;
    private int speed;
    private boolean missileLaunched;

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

    public boolean isVehicleFromLeft() {
        return vehicleFromLeft;
    }

    public void setVehicleFromLeft(boolean vehicleFromLeft) {
        this.vehicleFromLeft = vehicleFromLeft;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setMissileLaunched(boolean missileLaunched) {
        this.missileLaunched = missileLaunched;
    }

    public boolean isMissileLaunched() {
        return missileLaunched;
    }

    @Override
    public void reset() {

        missileLaunched = false;
    }
}
