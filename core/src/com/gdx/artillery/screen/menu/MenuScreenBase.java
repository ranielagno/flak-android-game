package com.gdx.artillery.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.artillery.ArtilleryGame;
import com.gdx.artillery.config.GameConfig;
import com.gdx.artillery.screen.game.GameWorld;
import com.jga.util.GdxUtils;

/**
 * Created by Raniel Agno on 1/2/2018.
 */

public abstract class MenuScreenBase extends ScreenAdapter {

    private static final Logger LOGGER = new Logger(GameWorld.class.getName(), Logger.DEBUG);

    protected final ArtilleryGame game;
    protected final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;

    public MenuScreenBase(ArtilleryGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        stage.addActor(createUI());
        Gdx.input.setInputProcessor(stage);
    }

    protected abstract Actor createUI();

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {

        GdxUtils.clearScreen();

        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    protected void back() {
        game.setScreen(new MenuScreen(game));
    }

}
