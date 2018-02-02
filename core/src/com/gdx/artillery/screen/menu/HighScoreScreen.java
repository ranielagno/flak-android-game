package com.gdx.artillery.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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

public class HighScoreScreen extends MenuScreenBase {

    private static final Logger log = new Logger(HighScoreScreen.class.getName(), Logger.DEBUG);

    private final String highScore;

    public HighScoreScreen(ArtilleryGame game) {

        super(game);

        highScore = game.getScoreController().getHighScoreString();

    }

    @Override
    protected Actor createUI() {
        Table table = new Table();
        table.defaults().space(50f);

        TextureAtlas menuAtlas = assetManager.get(AssetDescriptors.MENU);
        BitmapFont font = assetManager.get(AssetDescriptors.FONT);

        TextureRegion backgroundRegion = menuAtlas.findRegion(RegionNames.MENU_BACKGROUND);
        TextureRegion backRegion = menuAtlas.findRegion(RegionNames.BUTTON_BACK);

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);

        // background
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        // high score text
        Label highScoreText = new Label("HIGH SCORE", labelStyle);

        // high score label
        Label highScoreLabel = new Label(highScore, labelStyle);

        // back button
        ImageButton backButton = new ImageButton(new TextureRegionDrawable(backRegion));

        highScoreText.setFontScale(2,2);
        highScoreLabel.setFontScale(4,4);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

        table.add(highScoreText).row();
        table.add(highScoreLabel).row();
        table.add(backButton).row();

        table.setFillParent(true);
        table.pack();

        return table;

    }

}
