package com.gdx.artillery.screen.dialog;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.gdx.artillery.assets.AssetDescriptors;
import com.gdx.artillery.assets.RegionNames;
import com.gdx.artillery.common.GameState;
import com.gdx.artillery.screen.game.GameWorld;

/**
 * Created by Raniel on 2/5/2018.
 */

public class DialogOverlay extends Table {

    // == attributes ==
    protected final AssetManager assetManager;
    private final OverlayCallback callback;
    private GameWorld gameWorld;

    // == constructor ==
    //OverlayCallback overlayCallback, boolean gameWon
    public DialogOverlay(AssetManager assetManager, GameWorld gameWorld) {
        this.assetManager = assetManager;
        this.gameWorld = gameWorld;
        this.callback = gameWorld.getOverlayCallback();
        init();
    }

    // == init ==
    private void init() {

        GameState gameState = gameWorld.getGameState();
        BitmapFont font = assetManager.get(AssetDescriptors.FONT);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);

        TextureAtlas dialogAtlas = assetManager.get(AssetDescriptors.HUD);

        TextureRegion dialogBackgroundRegion = dialogAtlas.findRegion(RegionNames.DIALOG_BACKGROUND);

        TextureRegion dialogHomeButtonRegion = dialogAtlas.findRegion(RegionNames.HOME_BUTTON);
        TextureRegion dialogResetButtonRegion = dialogAtlas.findRegion(RegionNames.RESET_BUTTON);
        TextureRegion dialogNextButtonRegion = dialogAtlas.findRegion(RegionNames.NEXT_LEVEL_BUTTON);
        TextureRegion dialogPlayButtonRegion = dialogAtlas.findRegion(RegionNames.PLAY_BUTTON);

        TextureRegion stateRegion = null;

        if (gameState.isGameOver()) {
            stateRegion = (gameWorld.isGameWon())? dialogAtlas.findRegion(RegionNames.CONQUERED):
                    dialogAtlas.findRegion(RegionNames.DEFEATED);
        } else if (gameState.isPaused()) {
            stateRegion = dialogAtlas.findRegion(RegionNames.CONTINUE);
        }

        Table gameResponseTable = new Table();
        gameResponseTable.top();
        Image gameResponseImage = new Image(stateRegion);
        gameResponseTable.add(gameResponseImage);

        Table scoresTable = new Table();
        scoresTable.defaults().space(20);

        Label scoreText = new Label("SCORE: ", labelStyle);
        Label highScoreText = new Label("HIGH SCORE: ", labelStyle);

        String scoreString  = gameWorld.getScoreString();
        String highScore  = gameWorld.getHighScoreString();

        Label scoreLabel = new Label(scoreString, labelStyle);
        Label highScoreLabel = new Label(highScore, labelStyle);

        scoreText.setFontScaleX(2);
        scoreText.setFontScaleY(2);

        scoreLabel.setFontScaleX(2);
        scoreLabel.setFontScaleY(2);

        scoresTable.add(scoreText);
        scoresTable.add(scoreLabel).row();
        scoresTable.add(highScoreText);
        scoresTable.add(highScoreLabel).row();

        Table buttonsTable = new Table();

        ImageButton homeButton = new ImageButton(new TextureRegionDrawable(dialogHomeButtonRegion));
        homeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                callback.home();
            }
        });

        ImageButton playResetButton = null;

        if (gameState.isGameOver()) {
            playResetButton = new ImageButton(new TextureRegionDrawable(dialogResetButtonRegion));
        } else if (gameState.isPaused()) {
            playResetButton = new ImageButton(new TextureRegionDrawable(dialogPlayButtonRegion));
        }

        playResetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                callback.restart();
            }
        });

        ImageButton nextLevelButton = new ImageButton(new TextureRegionDrawable(dialogNextButtonRegion));
        nextLevelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                callback.nextLevel();
            }
        });

        buttonsTable.add(homeButton).left();
        buttonsTable.add(playResetButton).bottom();
        buttonsTable.add(nextLevelButton).right().row();

        Table dialogTable = new Table();
        dialogTable.defaults().pad(20);

        dialogTable.background(new TextureRegionDrawable(dialogBackgroundRegion));
        dialogTable.add(gameResponseTable).top().row();
        dialogTable.add(scoresTable).center().row();

        add(dialogTable).row();
        add(buttonsTable).padTop(-75).row();
        center();
        setFillParent(true);
        pack();

    }


}
