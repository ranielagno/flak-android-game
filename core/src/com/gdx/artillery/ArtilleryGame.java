package com.gdx.artillery;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.gdx.artillery.common.ScoreController;
import com.gdx.artillery.common.SoundController;
import com.gdx.artillery.screen.game.GameScreen;
import com.gdx.artillery.screen.loading.LoadingScreen;

public class ArtilleryGame extends Game {

    private AssetManager assetManager;
    private SpriteBatch batch;

    private ScoreController scoreController;
    private SoundController soundController;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        assetManager = new AssetManager();
        assetManager.getLogger().setLevel(Logger.DEBUG);

        batch = new SpriteBatch();

        scoreController = new ScoreController();

        setScreen(new LoadingScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        assetManager.dispose();
        batch.dispose();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public ScoreController getScoreController() {
        return scoreController;
    }

    public SoundController getSoundController() {
        return soundController;
    }

    public void setSoundController(SoundController soundController) {
        this.soundController = soundController;
    }
}
