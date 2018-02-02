package com.gdx.artillery.entity;

import com.gdx.artillery.config.GameConfig;

/**
 * Created by Raniel Agno on 12/28/2017.
 */

public class ArtilleryVehicle extends EntityBase{

    private final String vehicleName;

    public ArtilleryVehicle(String vehicleName) {

        this.vehicleName = vehicleName;

        if (vehicleName.equals("tank")) {

            setSize(GameConfig.VEHICLE_TANK_WIDTH, GameConfig.VEHICLE_TANK_HEIGHT);

        }
    }
}
