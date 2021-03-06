package com.gdx.artillery.screen.loading;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.artillery.ArtilleryGame;
import com.gdx.artillery.assets.AssetDescriptors;
import com.gdx.artillery.common.SoundController;
import com.gdx.artillery.config.GameConfig;
import com.gdx.artillery.screen.game.GameScreen;
import com.gdx.artillery.screen.menu.HighScoreScreen;
import com.gdx.artillery.screen.menu.MenuScreen;

/**
 * Created by Raniel Agno on 12/31/2017.
 */

public class LoadingScreen extends ScreenAdapter {

    private static final Logger log = new Logger(HighScoreScreen.class.getName(), Logger.DEBUG);

    // == constants ==
    private static final float PROGRESS_BAR_WIDTH = GameConfig.WORLD_CENTER_X;
    private static final float PROGRESS_BAR_HEIGHT = 2;

    // == attributes ==
    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private float progress;
    private float waitTime = 0.75f;
    private boolean changeScreen;

    private final ArtilleryGame game;
    private final AssetManager assetManager;
    private final SpriteBatch batch;

    private Texture sed_logo;

    private final Color color = Color.BLACK;

    // == constructors ==
    public LoadingScreen(ArtilleryGame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
        this.batch = game.getBatch();
    }

    // == public methods ==
    @Override
    public void show() {

        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        Array<AssetDescriptor> assetDescriptorArray = AssetDescriptors.ALL;

        for (AssetDescriptor descriptor : assetDescriptorArray) {
            assetManager.load(descriptor);
        }

        sed_logo = new Texture(Gdx.files.internal("logo/sed.png"));

    }

    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        renderer.setProjectionMatrix(camera.combined);

        renderer.begin(ShapeRenderer.ShapeType.Filled);

        draw();

        renderer.end();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        batch.draw(sed_logo,
                GameConfig.SED_LOGO_X, GameConfig.SED_LOGO_Y,
                GameConfig.SED_LOGO_SIZE, GameConfig.SED_LOGO_SIZE);

        batch.end();

        if (changeScreen) {
            assetManager.finishLoading();
            SoundController soundController = new SoundController(assetManager);
            game.setSoundController(soundController);
            soundController.menuSoundPlay();
            game.setScreen(new MenuScreen(game));
        }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        sed_logo.dispose();
    }

    // == private methods ==
    private void update(float delta) {

        // progress is between 0 and 1
        progress = assetManager.getProgress();

        // update returns true when all assets are loaded
        if (assetManager.update()) {
            waitTime -= delta;

            if (waitTime <= 0) {
                changeScreen = true;
            }
        }
    }

    private void draw() {

        float progressBarX = (GameConfig.WORLD_WIDTH - PROGRESS_BAR_WIDTH) / 2f;
        float progressBarY = (GameConfig.WORLD_HEIGHT - PROGRESS_BAR_HEIGHT) / 4f;

        renderer.rect(progressBarX, progressBarY,
                progress * PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT);

    }

}
