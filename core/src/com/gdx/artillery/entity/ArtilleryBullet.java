package com.gdx.artillery.entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.gdx.artillery.config.GameConfig;

/**
 * Created by Raniel Agno on 12/28/2017.
 */

public class ArtilleryBullet implements Pool.Poolable{

    private static final Logger log = new Logger(ArtilleryBullet.class.getName(), Logger.DEBUG);

    private float x;
    private float y;
    private float size;
    private float angle;
    private Circle bounds;

    public ArtilleryBullet() {
        bounds = new Circle(x, y, size / 2f);
    }

    public void update(float delta) {
        x += delta * GameConfig.CANNON_BALL_SPEED * MathUtils.cosDeg(angle);
        y += delta * GameConfig.CANNON_BALL_SPEED * MathUtils.sinDeg(angle);
        updateBounds();
    }

    public void updateBounds() {
        bounds.set(x, y, size / 2);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getSize() {
        return size;
    }

    public float getAngle() {
        return angle;
    }

    public Circle getBounds() {
        return bounds;
    }

    @Override
    public void reset() {
        //this.x = 0;
        //this.y = 0;
        //this.size = 0;
        //this.angle = 0;
        //bounds.set(x, y, size / 2);
    }


}
