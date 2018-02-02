package com.gdx.artillery.screen.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.gdx.artillery.ArtilleryGame;
import com.gdx.artillery.assets.AssetDescriptors;
import com.gdx.artillery.assets.RegionNames;

/**
 * Created by Raniel Agno on 1/2/2018.
 */

public class SettingsScreen extends MenuScreenBase {

    public SettingsScreen(ArtilleryGame game) {
        super(game);
    }

    @Override
    protected Actor createUI() {
        Table table = new Table();

        TextureAtlas menuAtlas = assetManager.get(AssetDescriptors.MENU);
        TextureRegion backgroundRegion = menuAtlas.findRegion(RegionNames.MENU_BACKGROUND);

        TextureRegion backRegion = menuAtlas.findRegion(RegionNames.BUTTON_BACK);

        ImageButton backButton = new ImageButton(new TextureRegionDrawable(backRegion));

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

        table.setBackground(new TextureRegionDrawable(backgroundRegion));
        table.add(backButton);
        table.setFillParent(true);
        table.pack();

        return table;

    }
}
