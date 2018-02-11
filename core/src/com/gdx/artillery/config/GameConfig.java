package com.gdx.artillery.config;

/**
 * Created by Raniel Agno on 12/28/2017.
 */

public class GameConfig {


    // == constants ==
    public static final float WIDTH = 480f; // pixels
    public static final float HEIGHT = 800f; // pixels

    public static final float HUD_WIDTH = 480f; // world units
    public static final float HUD_HEIGHT = 800f; // world units

    public static final float WORLD_WIDTH = 16.0f; // world units
    public static final float WORLD_HEIGHT = 26.67f; // world units

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2f; // world units
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2f; // world units

    public static final float VEHICLE_TANK_WIDTH = 4f; // world units
    public static final float VEHICLE_TANK_HEIGHT = 2.85f; // world units
    public static final float VEHICLE_TANK_HALF_WIDTH = VEHICLE_TANK_WIDTH / 2f;
    public static final float VEHICLE_TANK_HALF_HEIGHT = VEHICLE_TANK_HEIGHT / 2f;
    public static final float VEHICLE_TANK_X = WORLD_CENTER_X - VEHICLE_TANK_HALF_WIDTH; // world units
    public static final float VEHICLE_TANK_Y = 1.2f; // world units
    public static final float VEHICLE_SPEED = 3f;

    public static final float CANNON_WIDTH = 0.73f;
    public static final float CANNON_HEIGHT = 2.5f;
    public static final float CANNON_HALF_HEIGHT = CANNON_HEIGHT / 2f;
    public static final float CANNON_HALF_WIDTH = CANNON_WIDTH / 2f;
    public static final float CANNON_SCALE_X = VEHICLE_TANK_HALF_WIDTH - CANNON_HALF_WIDTH;
    public static final float CANNON_Y = VEHICLE_TANK_Y + VEHICLE_TANK_HALF_HEIGHT;
    public static final float CANNON_TURNING_SPEED = 40f;
    public static final float CANNON_TURNING_POINTX = CANNON_WIDTH / 2f;
    public static final float CANNON_TURNING_POINTY = 0.5f;

    public static final float CANNON_BALL_SIZE = CANNON_WIDTH - 0.2f; // - 0.8f
    public static final float CANNON_BALL_SPEED = 12f;

    public static final float ENEMY_VEHICLE_WIDTH = 3.7f;
    public static final float ENEMY_VEHICLE_HEIGHT = 2.68f;
    public static final float ENEMY_VEHICLE_HALF_WIDTH = ENEMY_VEHICLE_WIDTH / 2f;

    public static final int HIT_ENEMY_SCORE = 10;

    public static final float GAME_TIMER_MINUTE = 1f;

    public static final float RELOAD_TIME = 1f;

    public static final float ENEMY_BULLET_WIDTH = 1f;
    public static final float ENEMY_BULLET_HEIGHT = 2.7f;
    public static final float ENEMY_BULLET_SPEED = 3f;

    // 240w / 171h = 4 / x vehicle
    // 267h / 78w = 2.5f / x cannon
    // 181w / 131h = 3.7 / x chopper

    // == constructors ==
    private GameConfig() {}

}
