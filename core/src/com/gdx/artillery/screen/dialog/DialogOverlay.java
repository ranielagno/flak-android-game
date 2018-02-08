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
import com.gdx.artillery.assets.AssetDescriptors;
import com.gdx.artillery.assets.RegionNames;

/**
 * Created by Raniel on 2/5/2018.
 */

public class DialogOverlay extends Table {

    // == attributes ==
    protected final AssetManager assetManager;
    private final OverlayCallback callback;
    private Label highScoreLabel;

    // == constructor ==
    public DialogOverlay(AssetManager assetManager, OverlayCallback overlayCallback) {
        this.assetManager = assetManager;
        this.callback = overlayCallback;
        init();
    }

    // == init ==
    private void init() {

        BitmapFont font = assetManager.get(AssetDescriptors.FONT);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);

        TextureAtlas dialogAtlas = assetManager.get(AssetDescriptors.DIALOG);

        TextureRegion dialogBackgroundRegion = dialogAtlas.findRegion(RegionNames.DIALOG_BACKGROUND);
        TextureRegion dialogConqueredRegion = dialogAtlas.findRegion(RegionNames.CONQUERED);
        TextureRegion dialogContinueRegion = dialogAtlas.findRegion(RegionNames.CONTINUE);
        TextureRegion dialogDefeatedRegion = dialogAtlas.findRegion(RegionNames.DEFEATED);
        TextureRegion dialogHomeButtonRegion = dialogAtlas.findRegion(RegionNames.HOME_BUTTON);
        TextureRegion dialogResetButtonRegion = dialogAtlas.findRegion(RegionNames.RESET_BUTTON);
        TextureRegion dialogNextButtonRegion = dialogAtlas.findRegion(RegionNames.NEXT_LEVEL_BUTTON);
        TextureRegion dialogPlayButtonRegion = dialogAtlas.findRegion(RegionNames.PLAY_BUTTON);

        Table gameResponseTable = new Table();
        gameResponseTable.top();
        Image gameResponseImage = new Image(dialogConqueredRegion);
        gameResponseTable.add(gameResponseImage);

        Table scoresTable = new Table();
        Label highScoreText = new Label("HIGH SCORE: ", labelStyle);
        String highScore = highScore = "100";
        Label highScoreLabel = new Label(highScore, labelStyle);
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

        ImageButton resetButton = new ImageButton(new TextureRegionDrawable(dialogResetButtonRegion));
        resetButton.addListener(new ChangeListener() {
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
        buttonsTable.add(resetButton).bottom();
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
