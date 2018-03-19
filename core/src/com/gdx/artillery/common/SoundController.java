package com.gdx.artillery.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Logger;
import com.gdx.artillery.ArtilleryGame;
import com.gdx.artillery.assets.AssetDescriptors;
import com.gdx.artillery.config.GameConfig;
import com.gdx.artillery.screen.game.GameWorld;

/**
 * Created by Raniel on 2/14/2018.
 */

public class SoundController {

    private static final Logger LOGGER = new Logger(GameWorld.class.getName(), Logger.DEBUG);

    // == attributes ==
    private final Preferences prefs;
    private static final String MUSIC_KEY = "isMusicOn";
    private static final String SOUND_KEY = "isSoundOn";

    private boolean musicOn;
    private boolean soundOn;

    private final AssetManager assetManager;

    private Music gameSound;
    private Music menuSound;

    private Sound aircraftHit;
    private Sound jetSound;
    private Sound oldAircraftSound;
    private Sound shipMoveSound;
    private Sound fire;
    private Sound tankMoveSound;

    // == constructors ==
    public SoundController(AssetManager assetManager) {
        this.assetManager = assetManager;
        prefs = Gdx.app.getPreferences(ArtilleryGame.class.getSimpleName());
        musicOn = prefs.getBoolean(MUSIC_KEY, true);
        soundOn = prefs.getBoolean(SOUND_KEY, true);

        init();
    }

    // == init ==
    private void init() {
        gameSound = assetManager.get(AssetDescriptors.GAME_SOUND);
        menuSound = assetManager.get(AssetDescriptors.MENU_SOUND);
        aircraftHit = assetManager.get(AssetDescriptors.AIRCRAFT_HIT);
        jetSound = assetManager.get(AssetDescriptors.JET);
        oldAircraftSound = assetManager.get(AssetDescriptors.OLD_AIRCRAFT);
        shipMoveSound = assetManager.get(AssetDescriptors.SHIP_MOVE);
        fire = assetManager.get(AssetDescriptors.FIRE);
        tankMoveSound = assetManager.get(AssetDescriptors.TANK_MOVE);

        gameSound.setLooping(true);
        menuSound.setLooping(true);
    }

    // == public methods ==

    public void hit() {
        if (soundOn) {
            aircraftHit.play(GameConfig.SFX_VOLUME);
        }
    }

    public void gameSoundPlay() {
        if (musicOn) {
            gameSound.play();
        }
    }

    public void gameSoundStop() {
        gameSound.stop();
    }

    public void jetSoundPlay() {
        if (soundOn) {
            jetSound.play(GameConfig.SFX_VOLUME);
        }
    }

    public void menuSoundPlay() {

        if (musicOn) {
            menuSound.play();
        } else {
            menuSound.pause();
        }
    }

    public void menuSoundStop() {
        menuSound.stop();
    }

    public void oldAircraftSound() {
        if (soundOn) {
            oldAircraftSound.play(GameConfig.SFX_VOLUME);
        }
    }

    public void shipMoveSound() {
        if (soundOn) {
            shipMoveSound.loop(GameConfig.SFX_VOLUME);
        }
    }

    public void shipMoveSoundStop() {
        shipMoveSound.stop();
    }

    public void fire() {
        if (soundOn) {
            fire.play(GameConfig.SFX_VOLUME);
        }
    }

    public void tankMoveSound() {
        if (soundOn) {
            tankMoveSound.loop(GameConfig.SFX_VOLUME);
        }
    }

    public void tankMoveSoundStop() {
        tankMoveSound.stop();
    }

    public void setMusicOn(boolean musicOn) {
        this.musicOn = musicOn;
        prefs.putBoolean(MUSIC_KEY, musicOn);
        prefs.flush();
    }

    public void setSoundOn(boolean soundOn) {
        this.soundOn = soundOn;
        prefs.putBoolean(SOUND_KEY, soundOn);
        prefs.flush();
    }

    public boolean isMusicOn() {
        return musicOn;
    }

    public boolean isSoundOn() {
        return soundOn;
    }

}
