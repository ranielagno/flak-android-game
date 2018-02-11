package com.gdx.artillery.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import com.gdx.artillery.config.GameConfig;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Raniel Agno on 12/28/2017.
 */

public class EnemyVehicle extends EntityBase implements Pool.Poolable{

    private boolean enemyAlive = true;
    private boolean vehicleFromLeft;
    private int speed;
    private ArrayList<EnemyBullet> ammo;

    public EnemyVehicle() {
        ammo = new ArrayList<EnemyBullet>();
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

    public void addAmmo(EnemyBullet enemyBullet) {
        ammo.add(enemyBullet);
    }

    public ArrayList<EnemyBullet> getAmmo() {
        return ammo;
    }

    @Override
    public void reset() {

        ammo.clear();
    }
}
