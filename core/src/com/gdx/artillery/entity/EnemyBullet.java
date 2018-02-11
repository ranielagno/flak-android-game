package com.gdx.artillery.entity;

import com.badlogic.gdx.utils.Pool;
import com.gdx.artillery.config.GameConfig;

/**
 * Created by Raniel Agno on 12/29/2017.
 */

public class EnemyBullet extends EntityBase implements Pool.Poolable {

    private float rotation = 0f;

    public EnemyBullet() {

        setSize(GameConfig.ENEMY_BULLET_WIDTH, GameConfig.ENEMY_BULLET_HEIGHT);

    }

    public void update(float delta) {

        y -= GameConfig.ENEMY_BULLET_SPEED * delta;

        updateBounds();
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    @Override
    public void reset() {

    }
}
