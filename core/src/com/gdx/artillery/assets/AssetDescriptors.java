package com.gdx.artillery.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


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

    public static final AssetDescriptor<TextureAtlas> LOGO =
            new AssetDescriptor<TextureAtlas>(AssetPaths.LOGO, TextureAtlas.class);

    public static final AssetDescriptor<Skin> SKIN =
            new AssetDescriptor<Skin>(AssetPaths.SKIN, Skin.class);

    private AssetDescriptors() {
    }
}
