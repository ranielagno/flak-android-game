package com.gdx.artillery.screen.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.gdx.artillery.ArtilleryGame;
import com.gdx.artillery.common.GameState;
import com.gdx.artillery.common.LevelState;
import com.gdx.artillery.common.ScoreController;
import com.gdx.artillery.entity.EntityFactory;
import com.gdx.artillery.input.CannonInputController;
import com.gdx.artillery.input.VehicleInputController;
import com.gdx.artillery.screen.menu.MenuScreen;

/**
 * Created by Raniel Agno on 12/28/2017.
 */

public class GameScreen extends ScreenAdapter {

    private static final Logger LOGGER = new Logger(GameWorld.class.getName(), Logger.DEBUG);

    // == attributes ==
    private final ArtilleryGame game;
    private final AssetManager assetManager;
    private final SpriteBatch batch;

    private GameWorld gameWorld; // model
    private GameController controller; // controller
    private GameRenderer renderer; // view

    private EntityFactory factory;

    private VehicleInputController vehicleInputController;
    private CannonInputController cannonInputController;

    private final ScoreController scoreController;


    // == constructors ==
    public GameScreen(ArtilleryGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
        batch = game.getBatch();
        scoreController = game.getScoreController();
    }

    @Override
    public void show() {

        factory = new EntityFactory();
        gameWorld = new GameWorld(factory, scoreController);
        renderer = new GameRenderer(gameWorld, batch, assetManager);
        controller = new GameController(gameWorld, renderer);

        vehicleInputController = new VehicleInputController(gameWorld.getVehicle(), controller);
        cannonInputController = new CannonInputController(gameWorld.getCannon(), gameWorld.getVehicle(), controller);

        gameWorld.setGameRenderer(renderer);
        gameWorld.startLevel(LevelState.Level1);
    }

    @Override
    public void render(float delta) {

        if (gameWorld.getGameState().isMenu()) {
            game.setScreen(new MenuScreen(game));
        }

        if (gameWorld.getGameState().isPlaying()) {
            vehicleInputController.update(delta);
            cannonInputController.update(delta);
        }
        controller.update(delta);
        renderer.render(delta);

    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
