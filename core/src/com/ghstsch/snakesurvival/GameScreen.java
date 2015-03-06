package com.ghstsch.snakesurvival;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.Vector;

/**
 * Created by aaaa on 15.02.2015.
 */
public class GameScreen implements Screen {
    public static final int FOREST_WORLD = 1;

    OrthographicCamera worldCam;
    OrthographicCamera uiCam;

    SpriteBatch spriteBatch;
    World mainWorld;
    Player player;
    Box2DDebugRenderer b2render;
    BitmapFont font;
    ScreenManager screenManager;
    WorldController controller;
    Vector<GameObject> objectList;
    int worldType;
    boolean paused;
    boolean upgradeScreen;
    UiButton pauseButton;

    public GameScreen(int worldType) {
        this.worldType = worldType;
    }

    public void init(ScreenManager screenManager, BitmapFont font)
    {
        paused = false;
        upgradeScreen = false;
        this.screenManager = screenManager;
        this.font = font;
        //b2render = new Box2DDebugRenderer(true, true, true, true, true, true);
        b2render = new Box2DDebugRenderer(true,true,false,true,false,true);

        worldCam = new OrthographicCamera();
        worldCam.setToOrtho(true, 1920, 1080);
        uiCam = new OrthographicCamera();
        uiCam.setToOrtho(true, 1920, 1080);

        spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(worldCam.combined);

        mainWorld = new World(new Vector2(0, 0), true);

        if(worldType == FOREST_WORLD) {
            controller = new ForestWorldController(mainWorld);
        }

        controller.generateWorld(1);
        player = controller.getPlayer();
        objectList = controller.getObjectList();

        pauseButton = new UiButton(1800.0f, 0.0f, 120.0f, 35.0f, "PAUSE", UiButton.standard, new Color(1.0f, 0.5f, 0.0f, 1.0f), new Color(0.4f, 1.0f, 1.0f, 1.0f), font);
    }
    public void draw()
    {
        if(!upgradeScreen) {
            worldCam.update();
            worldCam.position.x = player.getPosition().x;
            worldCam.position.y = player.getPosition().y;
            Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
            b2render.render(mainWorld, worldCam.combined);

            uiCam.update();
            spriteBatch.begin();
            pauseButton.draw(spriteBatch);
            spriteBatch.end();
        }
        else {
            Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
        }
    }
    public void update(float dt)
    {
        if(!paused) {
            controller.update(dt);
            doPhysicsStep(dt);
        }
        if(controller.isGameEnded()) {
            upgradeScreen = true;
            paused = true;
        }
        if(upgradeScreen && paused) {
            //update buttons of upgrade screen
        }
        else if(upgradeScreen && !paused) {
            controller.generateWorld(1);
            player = controller.getPlayer();
            objectList = controller.getObjectList();
            upgradeScreen = false;
        }
        handleInput();

        pauseButton.update(dt);

        if(Gdx.input.isTouched()) {
            float mouseX = (1920.0f / Gdx.graphics.getWidth()) * Gdx.input.getX();
            float mouseY = (1080.0f / Gdx.graphics.getHeight()) * Gdx.input.getY();
            pauseButton.press(mouseX, mouseY);
        }
        if(pauseButton.isClicked()) {
            paused = !paused;
        }
    }
    public void handleInput()
    {
        if(InputHandler.isKeyDown(InputHandler.D))player.turnRight();
        if(InputHandler.isKeyDown(InputHandler.A))player.turnLeft();
    }
    private float accumulator = 0;

    private void doPhysicsStep(float deltaTime) {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/60.0f) {
            mainWorld.step(1/60.0f, 6, 2);
            accumulator -= 1/60.0f;
        }
    }

}
