package com.gdx.artillery.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;


public class AssetDescriptors {

    public static final AssetDescriptor<BitmapFont> FONT =
            new AssetDescriptor<BitmapFont>(AssetPaths.FONT, BitmapFont.class);

    public static final AssetDescriptor<TextureAtlas> LAND =
            new AssetDescriptor<TextureAtlas>(AssetPaths.LAND, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> SEA =
            new AssetDescriptor<TextureAtlas>(AssetPaths.SEA, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> MENU =
            new AssetDescriptor<TextureAtlas>(AssetPaths.MENU, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> HUD =
            new AssetDescriptor<TextureAtlas>(AssetPaths.HUD, TextureAtlas.class);

    public static final AssetDescriptor<Skin> SKIN =
            new AssetDescriptor<Skin>(AssetPaths.SKIN, Skin.class);

    public static final AssetDescriptor<Sound> AIRCRAFT_HIT =
            new AssetDescriptor<Sound>(AssetPaths.AIRCRAFT_HIT, Sound.class);

    public static final AssetDescriptor<Music> GAME_SOUND =
            new AssetDescriptor<Music>(AssetPaths.GAME_SOUND, Music.class);

    public static final AssetDescriptor<Sound> JET =
            new AssetDescriptor<Sound>(AssetPaths.JET, Sound.class);

    public static final AssetDescriptor<Music> MENU_SOUND =
            new AssetDescriptor<Music>(AssetPaths.MENU_SOUND, Music.class);


    public static final AssetDescriptor<Sound> OLD_AIRCRAFT =
            new AssetDescriptor<Sound>(AssetPaths.OLD_AIRCRAFT, Sound.class);

    public static final AssetDescriptor<Sound> SHIP_MOVE =
            new AssetDescriptor<Sound>(AssetPaths.SHIP_MOVE, Sound.class);

    public static final AssetDescriptor<Sound> FIRE =
            new AssetDescriptor<Sound>(AssetPaths.FIRE, Sound.class);

    public static final AssetDescriptor<Sound> TANK_MOVE =
            new AssetDescriptor<Sound>(AssetPaths.TANK_MOVE, Sound.class);

    // all descriptors
    public static final Array<AssetDescriptor> ALL = new Array<AssetDescriptor>();

    // static init
    static {
        ALL.addAll(
                FONT,
                LAND,
                SEA,
                MENU,
                HUD,
                SKIN,
                AIRCRAFT_HIT,
                GAME_SOUND,
                JET,
                MENU_SOUND,
                OLD_AIRCRAFT,
                SHIP_MOVE,
                FIRE,
                TANK_MOVE
        );
    }

    private AssetDescriptors() {
    }
}
