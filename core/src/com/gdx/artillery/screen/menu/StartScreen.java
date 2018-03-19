package com.gdx.artillery.screen.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.gdx.artillery.ArtilleryGame;
import com.gdx.artillery.assets.AssetDescriptors;
import com.gdx.artillery.assets.RegionNames;
import com.gdx.artillery.screen.game.GameScreen;

/**
 * Created by Raniel Agno on 1/2/2018.
 */

public class StartScreen extends MenuScreenBase {

    private static final Logger log = new Logger(HighScoreScreen.class.getName(), Logger.DEBUG);

    public StartScreen(ArtilleryGame game) {
        super(game);
    }

    @Override
    protected Actor createUI() {
        Table table = new Table();

        TextureAtlas menuAtlas = assetManager.get(AssetDescriptors.MENU);

        TextureRegion backgroundRegion = menuAtlas.findRegion(RegionNames.MENU_BACKGROUND);
        TextureRegion playRegion = menuAtlas.findRegion(RegionNames.BUTTON_PLAY);
        TextureRegion practiceRegion = menuAtlas.findRegion(RegionNames.BUTTON_PRACTICE);
        TextureRegion directionsRegion = menuAtlas.findRegion(RegionNames.BUTTON_HOW);
        TextureRegion backRegion = menuAtlas.findRegion(RegionNames.BUTTON_BACK);

        // background
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        // play button
        ImageButton playButton = new ImageButton(new TextureRegionDrawable(playRegion));

        // practice button
        ImageButton practiceButton = new ImageButton(new TextureRegionDrawable(practiceRegion));

        // directions button
        ImageButton directionsButton = new ImageButton(new TextureRegionDrawable(directionsRegion));

        // back button
        ImageButton backButton = new ImageButton(new TextureRegionDrawable(backRegion));

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play();
            }
        });

        // TODO: Create practice mode

        directionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showDirections();
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

        table.add(playButton).row();
        table.add(practiceButton).row();
        table.add(directionsButton).row();
        table.add(backButton).row();

        table.setFillParent(true);
        table.pack();

        return table;

    }

    private void play() {
        //soundController.menuSoundStop();
        game.setScreen(new GameScreen(game));
    }

    private void showDirections() {
        game.setScreen(new DirectionsScreen(game));
    }

}
