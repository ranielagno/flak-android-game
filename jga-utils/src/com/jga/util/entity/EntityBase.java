package com.jga.util.entity;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by goran on 26/09/2016.
 */

public abstract class EntityBase {

    // == attributes ==
    protected float x;
    protected float y;

    protected float width = 1;
    protected float height = 1;

    protected Rectangle bounds;

    // == constructors ==
    public EntityBase() {
        bounds = new Rectangle(x, y, width, height);
    }

    // == public methods ==
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateBounds();
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        updateBounds();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
        updateBounds();
    }

    public void setY(float y) {
        this.y = y;
        updateBounds();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void updateBounds() {
        bounds.setPosition(x, y);
        bounds.setSize(width, height);
    }
}
