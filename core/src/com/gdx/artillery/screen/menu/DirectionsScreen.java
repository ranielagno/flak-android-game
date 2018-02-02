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

/**
 * Created by Raniel Agno on 1/2/2018.
 */

public class DirectionsScreen extends MenuScreenBase {
    private static final Logger log = new Logger(HighScoreScreen.class.getName(), Logger.DEBUG);

    public DirectionsScreen(ArtilleryGame game) {
        super(game);
    }

    protected Actor createUI() {
        Table table = new Table();

        TextureAtlas menuAtlas = assetManager.get(AssetDescriptors.MENU);

        TextureRegion backgroundRegion = menuAtlas.findRegion(RegionNames.TUTORIAL_BACKGROUND);
        TextureRegion backRegion = menuAtlas.findRegion(RegionNames.BUTTON_BACK);

        // background
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        // back button
        ImageButton backButton = new ImageButton(new TextureRegionDrawable(backRegion));

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

        table.add(backButton).row();

        table.setFillParent(true);
        table.pack();

        return table;

    }

    @Override
    protected void back() {
        game.setScreen(new StartScreen(game));
    }
}
