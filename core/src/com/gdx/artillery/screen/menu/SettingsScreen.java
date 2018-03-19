package com.gdx.artillery.screen.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.gdx.artillery.ArtilleryGame;
import com.gdx.artillery.assets.AssetDescriptors;
import com.gdx.artillery.assets.RegionNames;
import com.gdx.artillery.common.SoundController;

/**
 * Created by Raniel Agno on 1/2/2018.
 */

public class SettingsScreen extends MenuScreenBase {

    protected final SoundController soundController;

    private CheckBox music;
    private CheckBox sound;

    public SettingsScreen(ArtilleryGame game) {
        super(game);
        soundController = game.getSoundController();
    }

    @Override
    protected Actor createUI() {
        Table table = new Table();
        table.defaults().space(15);

        Skin skin = assetManager.get(AssetDescriptors.SKIN);

        TextureAtlas menuAtlas = assetManager.get(AssetDescriptors.MENU);
        TextureRegion backgroundRegion = menuAtlas.findRegion(RegionNames.MENU_BACKGROUND);

        TextureRegion backRegion = menuAtlas.findRegion(RegionNames.BUTTON_BACK);

        Label label = new Label("SETTINGS", skin);

        label.setFontScale(3,3);

        music = checkBox("MUSIC", skin);
        sound = checkBox("SFX", skin);

        music.setChecked(game.getSoundController().isMusicOn());
        sound.setChecked(game.getSoundController().isSoundOn());

        music.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundController.setMusicOn(music.isChecked());
                soundController.menuSoundPlay();
            }
        });

        sound.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundController.setSoundOn(sound.isChecked());
            }
        });

        ImageButton backButton = new ImageButton(new TextureRegionDrawable(backRegion));

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        table.add(label).row();
        table.add(music).row();
        table.add(sound).row();
        table.add(backButton);

        table.setFillParent(true);
        table.pack();

        return table;

    }

    private CheckBox checkBox(String text, Skin skin) {
        CheckBox checkBox = new CheckBox(text, skin);
        checkBox.left().pad(8);
        checkBox.getLabelCell().pad(8);
        return checkBox;
    }
}
