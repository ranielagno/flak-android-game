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
import com.gdx.artillery.entity.EnemyBullet;
import com.gdx.artillery.entity.EnemyVehicle;
import com.gdx.artillery.entity.EntityFactory;
import com.gdx.artillery.screen.dialog.OverlayCallback;

import java.text.DecimalFormat;

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

    private Array<ArtilleryBullet> bullets = new Array<ArtilleryBullet>();
    private Array<EnemyVehicle> enemies = new Array<EnemyVehicle>();
    private Array<EnemyBullet> enemyBulletsStored = new Array<EnemyBullet>();
    private Array<EnemyBullet> enemyBullets = new Array<EnemyBullet>();

    private float reloadTime;
    private float gameTimer;

    private String gameTimeString = "";
    private DecimalFormat df = new DecimalFormat("00");

    private LevelState currentLevel;

    private GameState gameState;
    private OverlayCallback callback;

    private GameRenderer renderer;

    private boolean gameWon = false;

    private int currentLevelScore = 0;

    // == constructors ==
    public GameWorld(EntityFactory factory, final ScoreController scoreController) {
        this.factory = factory;
        this.scoreController = scoreController;

        callback = new OverlayCallback() {
            @Override
            public void home() {
                gameState = GameState.MENU;
            }

            @Override
            public void restart() {

                if (gameState.isGameOver()) {
                    scoreController.setScore(currentLevelScore);
                    startLevel(currentLevel);
                } else if (gameState.isPaused()) {
                    gameState = GameState.PLAYING;
                }
            }

            @Override
            public void nextLevel() {

                currentLevelScore = scoreController.getScore();

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

        scoreController.reset();

        resetGameWorld();

    }

    public void update(float delta) {

        if (isGameTimerFinished() && !gameState.isGameOver()) {
            gameState = GameState.GAME_OVER;
            gameWon = true;
            clearGameWorld();
        }

        if (gameState.isPlaying()) {

            reloadTime += delta;

            if (reloadTime >= GameConfig.RELOAD_TIME && !gameState.isGameOver()) {
                spawnBullet(cannon);
                reloadTime = 0;
            }

            updateGameTimer(delta);
            updateBullets(delta);
            updateEnemies(delta);
            spawnEnemyBullet();
            updateEnemyBullets(delta);
            checkEnemyHit();
            checkVehicleHit();
            checkMissilesHit();
        }

    }

    // == private methods ==
    private void spawnEnemyWave() {

        enemyBulletsStored.clear();

        int randomNumberOfEnemy = 0;

        if (currentLevel == LevelState.Level1) {
            randomNumberOfEnemy = MathUtils.random(1, 3);
        } else if (currentLevel == LevelState.Level2) {
            randomNumberOfEnemy = MathUtils.random(3, 5);
        } else if (currentLevel == LevelState.Level3) {
            randomNumberOfEnemy = MathUtils.random(5, 7);
        }

        // random speed
        // random position y value
        // random initial position x value

        for (int i = 0; i < randomNumberOfEnemy; i++) {

            boolean isVehicleFromLeft = MathUtils.randomBoolean();

            // Todo: Vary speed depend on level
            int speed = MathUtils.random(4, 6);

            float enemyVehicleX = 0f;
            float enemyVehicleY = MathUtils.random(GameConfig.WORLD_CENTER_Y,
                    GameConfig.WORLD_HEIGHT - GameConfig.ENEMY_VEHICLE_HEIGHT);

            // Todo: Vary enemy bullet launching spot depend on level
            float startRange = GameConfig.ENEMY_VEHICLE_HALF_WIDTH;
            float endRange = GameConfig.WORLD_WIDTH - GameConfig.ENEMY_VEHICLE_HALF_WIDTH;

            float enemyBulletX = MathUtils.random(startRange, endRange);
            float enemyBulletY = enemyVehicleY - (GameConfig.ENEMY_BULLET_HEIGHT / 2 + 0.5f);

            if (isVehicleFromLeft) {
                enemyVehicleX -= GameConfig.ENEMY_VEHICLE_WIDTH;
            } else {
                enemyVehicleX = GameConfig.WORLD_WIDTH;
            }

            EnemyVehicle enemy = factory.createEnemyVehicle(enemyVehicleX, enemyVehicleY, isVehicleFromLeft, speed);
            EnemyBullet enemyBullet = factory.createEnemyBullet(enemyBulletX, enemyBulletY);

            enemies.add(enemy);
            enemyBulletsStored.add(enemyBullet);

        }

    }

    private void checkEnemyHit() {

        for (int i = 0; i < enemies.size; i++) {

            EnemyVehicle enemyVehicle = enemies.get(i);
            Rectangle enemyBounds = enemyVehicle.getBounds();

            for (int j = 0; j < bullets.size; j++) {

                ArtilleryBullet bullet = bullets.get(j);

                if (Intersector.overlaps(bullet.getBounds(), enemyBounds)) {

                    factory.freeBullet(bullet);
                    factory.freeEnemyVehicle(enemyVehicle);

                    enemies.removeIndex(i);
                    enemyBulletsStored.removeIndex(i);
                    bullets.removeIndex(j);

                    scoreController.addScore(GameConfig.HIT_ENEMY_SCORE);

                    break;

                }

            }

        }
    }

    private void checkVehicleHit() {

        for (int i = 0; i < enemyBullets.size; i++) {

            EnemyBullet enemyBullet = enemyBullets.get(i);
            Rectangle enemyBounds = enemyBullet.getBounds();

            if (Intersector.overlaps(vehicle.getBounds(), enemyBounds)) {

                factory.freeEnemyBullet(enemyBullet);

                enemyBullets.removeIndex(i);

                gameState = GameState.GAME_OVER;

                gameWon = false;

                break;

            }

        }

    }


    private void checkMissilesHit() {

        for (int i = 0; i < bullets.size; i++) {
            ArtilleryBullet bullet = bullets.get(i);

            for (int j = 0; j < enemyBullets.size; j++) {

                EnemyBullet enemyBullet = enemyBullets.get(j);
                Rectangle enemyBounds = enemyBullet.getBounds();

                if (Intersector.overlaps(bullet.getBounds(), enemyBounds)) {

                    factory.freeEnemyBullet(enemyBullet);
                    factory.freeBullet(bullet);

                    bullets.removeIndex(i);
                    enemyBullets.removeIndex(j);

                }
            }
        }
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
                factory.freeBullet(bullet);
                bullets.removeIndex(i);
            }
        }
    }

    private void updateEnemies(float delta) {

        if (enemies.size == 0) {
            spawnEnemyWave();
        }

        for (int i = 0; i < enemies.size; i++) {
            EnemyVehicle enemy = enemies.get(i);
            enemy.update(delta);

            if (enemy.isVehicleFromLeft()) {

                if (enemy.getX() > GameConfig.WORLD_WIDTH) {

                    enemyBulletsStored.removeIndex(i);
                    enemies.removeIndex(i);
                    factory.freeEnemyVehicle(enemy);
                }

            } else {

                if ((enemy.getX() + enemy.getWidth()) < 0) {

                    enemyBulletsStored.removeIndex(i);
                    enemies.removeIndex(i);
                    factory.freeEnemyVehicle(enemy);
                }

            }

        }

    }

    private void spawnEnemyBullet() {

        for (int i = 0; i < enemies.size; i++) {

            EnemyVehicle enemyVehicle = enemies.get(i);
            EnemyBullet enemyBullet = enemyBulletsStored.get(i);

            if (!enemyVehicle.isMissileLaunched() && enemyVehicle.isVehicleFromLeft() &&
                    ((enemyVehicle.getX() + GameConfig.ENEMY_VEHICLE_HALF_WIDTH) >= enemyBullet.getX())) {
                enemyBullets.add(enemyBullet);
                enemyVehicle.setMissileLaunched(true);
            } else if (!enemyVehicle.isMissileLaunched() && !enemyVehicle.isVehicleFromLeft() &&
                    (enemyVehicle.getX() + GameConfig.ENEMY_VEHICLE_HALF_WIDTH) <= enemyBullet.getX()) {
                enemyBullets.add(enemyBullet);
                enemyVehicle.setMissileLaunched(true);
            }

        }
    }


    private void updateEnemyBullets(float delta) {

        for (int i = 0; i < enemyBullets.size; i++) {
            EnemyBullet enemyBullet = enemyBullets.get(i);
            enemyBullet.update(delta);

            if (enemyBullet.getY() < vehicle.getY()) {
                factory.freeEnemyBullet(enemyBullet);
                enemyBullets.removeIndex(i);
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

    private void clearGameWorld() {

        for (int i = 0; i < enemies.size; i++) {

            EnemyVehicle enemy = enemies.get(i);
            factory.freeEnemyVehicle(enemy);
            enemies.removeIndex(i);
        }

        for (int i = 0; i < bullets.size; i++) {

            ArtilleryBullet bullet = bullets.get(i);
            factory.freeBullet(bullet);
            bullets.removeIndex(i);
        }

        for (int i = 0; i < enemyBulletsStored.size; i++) {

            EnemyBullet bullet = enemyBulletsStored.get(i);
            factory.freeEnemyBullet(bullet);
            enemyBulletsStored.removeIndex(i);
        }

        for (int i = 0; i < enemyBullets.size; i++) {

            EnemyBullet bullet = enemyBullets.get(i);
            factory.freeEnemyBullet(bullet);
            enemyBullets.removeIndex(i);
        }
    }

    private void resetGameWorld() {
        this.gameTimer = GameConfig.GAME_TIMER_MINUTE * 60f;
        vehicle.setPosition(GameConfig.VEHICLE_TANK_X, GameConfig.VEHICLE_TANK_Y);
        cannon.setRotation(0);
        clearGameWorld();
    }

    // == public methods

    public boolean isGameTimerFinished() {
        return gameTimer <= 0;
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

    public Array<EnemyVehicle> getEnemies() {
        return enemies;
    }

    public Array<EnemyBullet> getEnemyBullets() {
        return enemyBullets;
    }

    public String getScoreString() {
        return scoreController.getScoreString();
    }

    public String getGameTimeString() {
        return gameTimeString;
    }

    public GameState getGameState() {
        return gameState;
    }

    public String getHighScoreString() {
        return scoreController.getHighScoreString();
    }

    public OverlayCallback getOverlayCallback() {
        return callback;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public void setGameRenderer(GameRenderer gameRenderer) {
        this.renderer = gameRenderer;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void startLevel(LevelState levelScreen) {
        gameState = GameState.PLAYING;
        currentLevel = levelScreen;

        if (levelScreen == LevelState.Level1) {
            renderer.initializeLevel1();
        } else if (levelScreen == LevelState.Level2) {
            renderer.initializeLevel2();
        }

        resetGameWorld();

    }
}
