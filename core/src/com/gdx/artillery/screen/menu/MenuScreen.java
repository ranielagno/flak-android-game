package com.gdx.artillery.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.gdx.artillery.ArtilleryGame;
import com.gdx.artillery.assets.AssetDescriptors;
import com.gdx.artillery.assets.RegionNames;
import com.gdx.artillery.screen.game.GameScreen;

/**
 * Created by Raniel Agno on 1/1/2018.
 */

public class MenuScreen extends MenuScreenBase {

    public MenuScreen(ArtilleryGame game) {
        super(game);
    }

    @Override
    protected Actor createUI() {
        Table table = new Table();

        TextureAtlas menuAtlas = assetManager.get(AssetDescriptors.MENU);

        //skin = assetManager.get(AssetDescriptors.SKIN);

        TextureRegion backgroundRegion = menuAtlas.findRegion(RegionNames.MENU_BACKGROUND);
        TextureRegion logoRegion = menuAtlas.findRegion(RegionNames.FLAK);
        TextureRegion battleButtonRegion = menuAtlas.findRegion(RegionNames.BUTTON_BATTLE);
        TextureRegion scoreButtonRegion = menuAtlas.findRegion(RegionNames.BUTTON_SCORE);
        TextureRegion settingsButtonRegion = menuAtlas.findRegion(RegionNames.BUTTON_SETTINGS);
        TextureRegion quitButtonRegion = menuAtlas.findRegion(RegionNames.BUTTON_QUIT);

        Image image = new Image(logoRegion);
        ImageButton battleButton = new ImageButton(new TextureRegionDrawable(battleButtonRegion));
        ImageButton scoreButton = new ImageButton(new TextureRegionDrawable(scoreButtonRegion));
        ImageButton settingsButton = new ImageButton(new TextureRegionDrawable(settingsButtonRegion));
        ImageButton quitButton = new ImageButton(new TextureRegionDrawable(quitButtonRegion));

        battleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play();
            }
        });

        scoreButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showHighScore();
            }
        });

        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showSettings();
            }
        });

        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        table.setBackground(new TextureRegionDrawable(backgroundRegion));
        table.add(image).row();
        table.add(battleButton).row();
        table.add(scoreButton).row();
        table.add(settingsButton).row();
        table.add(quitButton).row();
        table.setFillParent(true);
        table.pack();

        return table;

    }

    private void play() {
        game.setScreen(new GameScreen(game));
    }

    private void showHighScore() {
        game.setScreen(new HighScoreScreen(game));
    }

    private void showSettings() {
        game.setScreen(new SettingsScreen(game));
    }

}
