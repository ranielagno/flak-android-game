package com.gdx.artillery.entity;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.gdx.artillery.config.GameConfig;

/**
 * Created by Raniel Agno on 12/28/2017.
 */

public class EntityFactory {

    private Pool<ArtilleryBullet> bulletPool;

    public EntityFactory() {
        init();
    }

    private void init() {
        // create bullet pool
        bulletPool = Pools.get(ArtilleryBullet.class, 40);

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

    public EnemyAircraft createEnemyChopper() {

        EnemyAircraft chopper = new EnemyAircraft("chopper");

        float x = MathUtils.random(0, GameConfig.WORLD_WIDTH - chopper.getWidth());
        float y = MathUtils.random(GameConfig.WORLD_CENTER_Y, GameConfig.WORLD_HEIGHT - chopper.getHeight());

        chopper.setPosition(x, y);
        return chopper;
    }

    public void freePickup(ArtilleryBullet bullet) {
        bulletPool.free(bullet);
    }

}
