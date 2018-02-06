package com.gdx.artillery.screen.game;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.gdx.artillery.common.GameState;
import com.gdx.artillery.common.LevelState;
import com.gdx.artillery.common.ScoreController;
import com.gdx.artillery.config.GameConfig;
import com.gdx.artillery.entity.ArtilleryBullet;
import com.gdx.artillery.entity.ArtilleryCannon;
import com.gdx.artillery.entity.ArtilleryVehicle;
import com.gdx.artillery.entity.Background;
import com.gdx.artillery.entity.EnemyAircraft;
import com.gdx.artillery.entity.EntityFactory;
import com.gdx.artillery.screen.dialog.OverlayCallback;

import java.text.DecimalFormat;
import java.util.logging.Level;

/**
 * Created by Raniel Agno on 12/28/2017.
 */

public class GameWorld {

    // == constants
    private static final Logger LOGGER = new Logger(GameWorld.class.getName(), Logger.DEBUG);

    // == attributes ==
    private final EntityFactory factory;
    private final ScoreController scoreController;

    private ArtilleryVehicle vehicle;

    private Background background;
    private ArtilleryCannon cannon;

    private EnemyAircraft chopper;

    private Array<ArtilleryBullet> bullets = new Array<ArtilleryBullet>();

    private float reloadTime;
    private float gameTimer;

    private String gameTimeString = "";
    private DecimalFormat df = new DecimalFormat("00");

    private boolean playerSurviveThisLevel = false;
    private boolean playerDied = false;

    private LevelState currentLevel;

    private GameState gameState;
    private OverlayCallback callback;

    private GameRenderer renderer;

    // == constructors ==
    public GameWorld(EntityFactory factory, ScoreController scoreController) {
        this.factory = factory;
        this.scoreController = scoreController;

        callback = new OverlayCallback() {
            @Override
            public void home() {
                gameState = GameState.MENU;
            }

            @Override
            public void restart() {
                startLevel(currentLevel);
            }

            @Override
            public void nextLevel() {

                if (currentLevel == LevelState.Level1) {
                    startLevel(LevelState.Level2);
                } else if (currentLevel == LevelState.Level2) {
                    gameState = GameState.MENU;
                }
            }

        };

        init();
    }

    // == init ==
    private void init() {

        // create background
        background = factory.createBackground(0, 0);

        // create vehicle
        vehicle = factory.createVehicle("tank", GameConfig.VEHICLE_TANK_X, GameConfig.VEHICLE_TANK_Y);

        // create cannon
        cannon = factory.createCannon("tank", vehicle.getX() + GameConfig.CANNON_SCALE_X, GameConfig.CANNON_Y);

        // create enemy chopper
        chopper = factory.createEnemyChopper();

        scoreController.reset();

        resetGameTimer();

    }

    public void update(float delta) {

        reloadTime += delta;

        if (reloadTime >= GameConfig.RELOAD_TIME) {
            spawnBullet(cannon);
            reloadTime = 0;
        }

        if (isGameTimerFinished() && !gameState.isGameOver() && !gameState.isMenu()) {
            gameState = GameState.GAME_OVER;
        }

        updateGameTimer(delta);
        updateBullets(delta);
        checkEnemyHit();

    }

    // == private methods ==
    private void spawnEnemy() {

        float x = MathUtils.random(0, GameConfig.WORLD_WIDTH - chopper.getWidth());
        float y = MathUtils.random(GameConfig.WORLD_CENTER_Y, GameConfig.WORLD_HEIGHT - chopper.getHeight());

        chopper.setPosition(x, y);
        chopper.setEnemyAlive(true);

    }

    private void checkEnemyHit() {

        Rectangle enemyBounds = chopper.getBounds();

        for (int i = 0; i < bullets.size; i++) {

            ArtilleryBullet bullet = bullets.get(i);

            if (Intersector.overlaps(bullet.getBounds(), enemyBounds)) {

                chopper.setEnemyAlive(false);
                factory.freePickup(bullet);
                bullets.removeIndex(i);

                scoreController.addScore(GameConfig.HIT_ENEMY_SCORE);

                break;

            }
        }

        if (!chopper.isEnemyAlive())
            spawnEnemy();


    }

    private void spawnBullet(ArtilleryCannon cannon) {
        float cannonDegrees = 90 + cannon.getRotation();

        float sizeScaleX = ((cannon.getWidth() - GameConfig.CANNON_BALL_SIZE) / 2f);
        float startX = cannon.getX() + sizeScaleX;
        float startY = cannon.getY();

        float x = startX + (cannon.getHeight() * MathUtils.cosDeg(cannonDegrees));
        float y = startY + (cannon.getHeight() * MathUtils.sinDeg(cannonDegrees));

        ArtilleryBullet bullet = factory.createBullet("tank", x, y, cannonDegrees);
        bullets.add(bullet);
    }

    private void updateBullets(float delta) {
        for (int i = 0; i < bullets.size; i++) {
            ArtilleryBullet bullet = bullets.get(i);
            bullet.update(delta);

            if (bullet.getX() + GameConfig.CANNON_BALL_SIZE < 0 ||
                    bullet.getX() > GameConfig.WORLD_WIDTH ||
                    bullet.getY() > GameConfig.WORLD_HEIGHT) {
                factory.freePickup(bullet);

                bullets.removeIndex(i);
            }
        }

    }

    private void updateGameTimer(float delta) {

        gameTimer -= delta;

        long seconds = (long) gameTimer;
        long minutes = seconds / 60;
        seconds -= minutes * 60;

        gameTimeString = df.format(minutes) + ":" + df.format(seconds);

        if (isGameTimerFinished()) {

            scoreController.updateHighScore();

        }

    }

    // == public methods

    public boolean isGameTimerFinished() {
        return gameTimer <= 0;
    }

    public void resetGameTimer() {
        this.gameTimer = GameConfig.GAME_TIMER_MINUTE * 60f;
    }

    public Background getBackground() {
        return background;
    }

    public ArtilleryVehicle getVehicle() {
        return vehicle;
    }

    public ArtilleryCannon getCannon() {
        return cannon;
    }

    public Array<ArtilleryBullet> getBullets() {
        return bullets;
    }

    public EnemyAircraft getChopper() {
        return chopper;
    }

    public String getScoreString() {
        return scoreController.getScoreString();
    }

    public String getGameTimeString() {
        return gameTimeString;
    }

    public GameState getGameState() { return gameState; }

    public OverlayCallback getOverlayCallback() {
        return callback;
    }

    public void setGameRenderer(GameRenderer gameRenderer) {
        this.renderer = gameRenderer;
    }

    public void startLevel(LevelState levelScreen) {
        gameState = GameState.PLAYING;
        currentLevel = levelScreen;

        if (levelScreen == LevelState.Level1) {
            renderer.initializeLevel1();
        } else if (levelScreen == LevelState.Level2) {
            renderer.initializeLevel2();
        }

        resetGameTimer();

    }
}
