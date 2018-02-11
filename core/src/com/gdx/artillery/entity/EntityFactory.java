package com.gdx.artillery.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.gdx.artillery.config.GameConfig;

/**
 * Created by Raniel Agno on 12/28/2017.
 */

public class EntityFactory {

    private Pool<ArtilleryBullet> bulletPool;
    private Pool<EnemyVehicle> enemyVehiclePool;
    private Pool<EnemyBullet> enemyBulletPool;

    public EntityFactory() {
        init();
    }

    private void init() {
        // create bullet pool
        bulletPool = Pools.get(ArtilleryBullet.class, 40);
        enemyVehiclePool = Pools.get(EnemyVehicle.class, 7);
        enemyBulletPool = Pools.get(EnemyBullet.class, 40);

    }

    public Background createBackground(float x, float y) {
        Background background = new Background();
        background.setPosition(0,0);
        background.setSize(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        return background;
    }

    public ArtilleryVehicle createVehicle(String vehicleName, float x, float y) {
        ArtilleryVehicle vehicle = new ArtilleryVehicle(vehicleName);
        vehicle.setPosition(x, y);
        return vehicle;
    }

    public ArtilleryCannon createCannon(String vehicleName, float x, float y) {
        ArtilleryCannon cannon = new ArtilleryCannon(vehicleName);
        cannon.setPosition(x, y);
        return cannon;
    }

    public ArtilleryBullet createBullet(String vehicleName, float x, float y, float angle) {
        ArtilleryBullet bullet = bulletPool.obtain();
        bullet.setPosition(x, y);
        bullet.setAngle(angle);
        bullet.setSize(GameConfig.CANNON_BALL_SIZE);
        return bullet;
    }

    public EnemyVehicle createEnemyVehicle(float x, float y, boolean isVehicleFromLeft, int speed) {

        EnemyVehicle enemyVehicle = enemyVehiclePool.obtain();
        enemyVehicle.setPosition(x, y);
        enemyVehicle.setSize(GameConfig.ENEMY_VEHICLE_WIDTH, GameConfig.ENEMY_VEHICLE_HEIGHT);
        enemyVehicle.setVehicleFromLeft(isVehicleFromLeft);
        enemyVehicle.setSpeed(speed);

        return enemyVehicle;
    }

    public EnemyBullet createEnemyBullet(float x, float y) {

        EnemyBullet bullet = enemyBulletPool.obtain();
        bullet.setPosition(x, y);
        return bullet;
    }

    public void freeBullet(ArtilleryBullet bullet) {
        bulletPool.free(bullet);
    }

    public void freeEnemyVehicle(EnemyVehicle enemyVehicle) {
        enemyVehiclePool.free(enemyVehicle);
    }

    public void freeEnemyBullet(EnemyBullet enemyBullet) {
        enemyBulletPool.free(enemyBullet);
    }

}
