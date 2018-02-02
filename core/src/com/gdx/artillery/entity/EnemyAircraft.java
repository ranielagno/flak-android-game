package com.gdx.artillery.entity;

import com.gdx.artillery.config.GameConfig;

/**
 * Created by Raniel Agno on 12/28/2017.
 */

public class EnemyAircraft extends EntityBase{

    private final String enemyAircraftName;
    private boolean enemyAlive = true;

    public EnemyAircraft(String enemyAircraftName) {

        this.enemyAircraftName = enemyAircraftName;

        if (enemyAircraftName.equals("chopper")){
            setSize(GameConfig.ENEMY_CHOPPER_WIDTH, GameConfig.ENEMY_CHOPPER_HEIGHT);
        }

    }

    public boolean isEnemyAlive() {
        return enemyAlive;
    }

    public void setEnemyAlive(boolean enemyAlive) {
        this.enemyAlive = enemyAlive;
    }

}
