package com.gdx.artillery.entity;

import com.badlogic.gdx.utils.Pool;
import com.gdx.artillery.config.GameConfig;

/**
 * Created by Raniel Agno on 12/29/2017.
 */

public class EnemyBullet extends EntityBase implements Pool.Poolable {

    private float rotation = 0f;
    private boolean alive;

    public EnemyBullet() {

        setSize(GameConfig.ENEMY_BULLET_WIDTH, GameConfig.ENEMY_BULLET_HEIGHT);
        alive = true;

    }

    public void update(float delta) {

        y -= delta * GameConfig.ENEMY_BULLET_SPEED;

        updateBounds();
    }

    public boolean isAlive() {
        return alive;
    }

    @Override
    public void reset() {
        alive = false;
    }
}
