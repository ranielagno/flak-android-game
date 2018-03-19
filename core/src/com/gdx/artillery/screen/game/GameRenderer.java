package com.gdx.artillery.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.artillery.assets.AssetDescriptors;
import com.gdx.artillery.assets.RegionNames;
import com.gdx.artillery.common.GameState;
import com.gdx.artillery.config.GameConfig;
import com.gdx.artillery.entity.ArtilleryBullet;
import com.gdx.artillery.entity.ArtilleryCannon;
import com.gdx.artillery.entity.ArtilleryVehicle;
import com.gdx.artillery.entity.Background;
import com.gdx.artillery.entity.EnemyBullet;
import com.gdx.artillery.entity.EnemyVehicle;
import com.gdx.artillery.screen.dialog.DialogOverlay;
import com.jga.util.GdxUtils;

/**
 * Created by Raniel Agno on 12/28/2017.
 */

public class GameRenderer implements Disposable {

    private static final Logger log = new Logger(GameRenderer.class.getName(), Logger.DEBUG);

    // == attributes ==
    private final GameWorld gameWorld;
    private final SpriteBatch batch;
    private final AssetManager assetManager;
    private final GlyphLayout layout = new GlyphLayout();

    private OrthographicCamera camera;
    private Viewport viewport;
    private Viewport hudViewport;
    private ShapeRenderer renderer;

    private TextureRegion vehicleRegion;
    private TextureRegion backgroundRegion;
    private TextureRegion cannonRegion;
    private TextureRegion bulletRegion;
    private TextureRegion enemyRegion;
    private TextureRegion enemyMissileRegion;
    private TextureRegion bossRegion;
    private TextureRegion bossMissileRegion;

    private BitmapFont font;

    private TextureAtlas level1Atlas;
    private TextureAtlas level2Atlas;
    private TextureAtlas hudAtlas;

    TextureRegion clockRegion;
    TextureRegion scoreRegion;
    TextureRegion pauseRegion;

    private Stage hudStage;
    private DialogOverlay dialogOverlay;

    private static final Logger LOGGER = new Logger(GameWorld.class.getName(), Logger.DEBUG);


    // == constructors ==

    public GameRenderer(GameWorld gameWorld, SpriteBatch batch, AssetManager assetManager) {
        this.gameWorld = gameWorld;
        this.batch = batch;
        this.assetManager = assetManager;
        init();
    }

    // == init ==
    private void init() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        hudStage = new Stage(hudViewport, batch);

        renderer = new ShapeRenderer();

        font = assetManager.get(AssetDescriptors.FONT);

        level1Atlas = assetManager.get(AssetDescriptors.LAND);
        level2Atlas = assetManager.get(AssetDescriptors.SEA);
        hudAtlas = assetManager.get(AssetDescriptors.HUD);

        clockRegion = hudAtlas.findRegion(RegionNames.CLOCK);
        scoreRegion = hudAtlas.findRegion(RegionNames.SCORE);
        pauseRegion = hudAtlas.findRegion(RegionNames.PAUSE_BUTTON);

    }

    // == public methods

    public void initializeLevel1() {
        backgroundRegion = level1Atlas.findRegion(RegionNames.LAND_BACKGROUND);
        cannonRegion = level1Atlas.findRegion(RegionNames.LAND_CANNON);
        vehicleRegion = level1Atlas.findRegion(RegionNames.LAND_VEHICLE);
        bulletRegion = level1Atlas.findRegion(RegionNames.LAND_BULLET);
        enemyRegion = level1Atlas.findRegion(RegionNames.LAND_ENEMY_VEHICLE);
        enemyMissileRegion = level1Atlas.findRegion(RegionNames.LAND_ENEMY_MISSILE);
        bossRegion = level1Atlas.findRegion(RegionNames.LAND_BOSS);;
        bossMissileRegion = level1Atlas.findRegion(RegionNames.LAND_BOSS_MISSILE);
    }

    public void initializeLevel2() {
        backgroundRegion = level2Atlas.findRegion(RegionNames.SEA_BACKGROUND);
        cannonRegion = level2Atlas.findRegion(RegionNames.SEA_CANNON);
        vehicleRegion = level2Atlas.findRegion(RegionNames.SEA_VEHICLE);
        bulletRegion = level1Atlas.findRegion(RegionNames.LAND_BULLET);
        enemyRegion = level2Atlas.findRegion(RegionNames.SEA_ENEMY_VEHICLE);
        enemyMissileRegion = level2Atlas.findRegion(RegionNames.SEA_ENEMY_MISSILE);
        bossRegion = level2Atlas.findRegion(RegionNames.SEA_BOSS);;
        bossMissileRegion = level2Atlas.findRegion(RegionNames.SEA_BOSS_MISSILE);
    }

    public void render(float delta) {

        GdxUtils.clearScreen();

        // render gameplay
        renderGamePlay();

        // render hud
        renderHud();

    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    public Vector2 screenToWorld(Vector2 screenCoordinates) {
        return viewport.unproject(screenCoordinates);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    private void renderGamePlay() {
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        Background background = gameWorld.getBackground();
        ArtilleryVehicle vehicle = gameWorld.getVehicle();
        ArtilleryCannon cannon = gameWorld.getCannon();
        Array<ArtilleryBullet> bullets = gameWorld.getBullets();
        Array<EnemyVehicle> enemies = gameWorld.getEnemies();
        Array<EnemyBullet> enemyBullets = gameWorld.getEnemyBullets();

        batch.draw(backgroundRegion,
                background.getX(), background.getY(),
                background.getWidth(), background.getHeight());

        for (int i = 0; i < bullets.size; i++) {

            ArtilleryBullet bullet = bullets.get(i);

            batch.draw(bulletRegion,
                    bullet.getX(), bullet.getY(),
                    bullet.getSize(), bullet.getSize());

        }

        batch.draw(cannonRegion,
                cannon.getX(), cannon.getY(), // x, y
                GameConfig.CANNON_TURNING_POINTX, GameConfig.CANNON_TURNING_POINTY, // originX, originY
                cannon.getWidth(), cannon.getHeight(), // width, height
                1.0f, 1.0f, // scaleX, scaleY
                cannon.getRotation() // rotation
        );

        for (int i = 0; i < enemies.size; i++) {

            EnemyVehicle enemy = enemies.get(i);
            float width = enemy.getWidth();
            float x = enemy.isVehicleFromLeft()? enemy.getX(): enemy.getX() + width;
            width = enemy.isVehicleFromLeft()? width: -width;
            batch.draw(enemyRegion,
                    x, enemy.getY(),
                    width, enemy.getHeight()
                    );

        }

        for (int i = 0; i < enemyBullets.size; i++) {

            EnemyBullet bullet = enemyBullets.get(i);

            batch.draw(enemyMissileRegion,
                    bullet.getX(), bullet.getY(),
                    bullet.getWidth(), bullet.getHeight());

        }


        batch.draw(vehicleRegion,
                vehicle.getX(), vehicle.getY(),
                vehicle.getWidth(), vehicle.getHeight());

        batch.end();

    }

    private void renderHud() {
        hudViewport.apply();

        GameState gameState = gameWorld.getGameState();

        if (dialogOverlay != null) {
            dialogOverlay.setVisible(false);
        }

        if (gameState.isPlaying()) {

            batch.setProjectionMatrix(hudViewport.getCamera().combined);

            batch.begin();

            drawHud();

            batch.end();

        }

        if (gameState.isGameOver() || gameState.isPaused()) {
            dialogOverlay = new DialogOverlay(assetManager, gameWorld);
            hudStage.addActor(dialogOverlay);
            Gdx.input.setInputProcessor(hudStage);
            dialogOverlay.setVisible(true);
        }

        hudStage.act();
        hudStage.draw();

    }

    private void drawHud() {

        String timeString = gameWorld.getGameTimeString();
        layout.setText(font, timeString);

        batch.draw(clockRegion,
                GameConfig.CLOCK_X, GameConfig.CLOCK_Y,
                GameConfig.HUD_SIZE, GameConfig.HUD_SIZE);

        font.draw(batch, layout,
                GameConfig.CLOCK_X + GameConfig.HUD_SIZE + 5f, GameConfig.HUD_HEIGHT - layout.height);


        String scoreString = gameWorld.getScoreString();
        layout.setText(font, scoreString);

        batch.draw(scoreRegion,
                GameConfig.SCORE_X, GameConfig.SCORE_Y,
                GameConfig.HUD_SIZE, GameConfig.HUD_SIZE);

        font.draw(batch, layout,
                GameConfig.HUD_WIDTH - 75f,
                GameConfig.HUD_HEIGHT - layout.height);


    }

}
