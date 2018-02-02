package com.gdx.artillery.entity;

import com.gdx.artillery.config.GameConfig;

/**
 * Created by Raniel Agno on 12/29/2017.
 */

public class ArtilleryCannon extends EntityBase {

    private final String vehicleName;
    private float rotation = 0f;

    public ArtilleryCannon(String vehicleName) {

        this.vehicleName = vehicleName;

        if (vehicleName.equals("tank")) {

            setSize(GameConfig.CANNON_WIDTH, GameConfig.CANNON_HEIGHT);

        }
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
