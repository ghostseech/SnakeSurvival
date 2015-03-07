package com.ghstsch.snakesurvival;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;

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
    //ingame ui
    UiButton pauseButton;
    UiLabel biomassIndicator;
    //Update screen
    UiLabel speedIndicator;
    UiLabel digestionIndicator;
    UiLabel fireIndicator;

    UiButton speedIncrease;
    UiButton digestionIncrease;
    UiButton fireIncrease;


    UiButton nextDayButton;
    float playerBiomass;
    //snake stats
    int speedLevel;
    int digestionLevel;
    int fireLevel;

    public GameScreen(int worldType) {
        this.worldType = worldType;
    }

    public void init(ScreenManager screenManager, BitmapFont font)
    {
        paused = false;
        upgradeScreen = false;
        this.screenManager = screenManager;
        this.font = font;
        //setup snake
        digestionLevel = 1;
        speedLevel = 1;
        fireLevel = 0;
        //update screen
        nextDayButton = new UiButton(1600, 480, 240, 120, ">", UiButton.standard, new Color(1.0f, 0.5f, 0.0f, 1.0f), new Color(0.4f, 1.0f, 1.0f, 1.0f), font);
        playerBiomass = 0.0f;

        speedIndicator     = new UiLabel("", 1.0f, 100.0f, 300.0f, new Color(0.4f, 0.5f, 1.0f, 1.0f), font);
        digestionIndicator = new UiLabel("", 1.0f, 100.0f, 500.0f, new Color(0.4f, 0.5f, 1.0f, 1.0f), font);
        fireIndicator      = new UiLabel("", 1.0f, 100.0f, 700.0f, new Color(0.4f, 0.5f, 1.0f, 1.0f), font);

        speedIncrease     = new UiButton(1000, 300, 240, 120, "BUY", UiButton.standard, new Color(1.0f, 0.5f, 0.0f, 1.0f), new Color(0.4f, 1.0f, 1.0f, 1.0f), font);
        digestionIncrease = new UiButton(1000, 500, 240, 120, "BUY", UiButton.standard, new Color(1.0f, 0.5f, 0.0f, 1.0f), new Color(0.4f, 1.0f, 1.0f, 1.0f), font);
        fireIncrease      = new UiButton(1000, 700, 240, 120, "BUY", UiButton.standard, new Color(1.0f, 0.5f, 0.0f, 1.0f), new Color(0.4f, 1.0f, 1.0f, 1.0f), font);

        //ingame ui
        pauseButton = new UiButton(1800.0f, 0.0f, 120.0f, 35.0f, "PAUSE", UiButton.standard, new Color(1.0f, 0.5f, 0.0f, 1.0f), new Color(0.4f, 1.0f, 1.0f, 1.0f), font);
        biomassIndicator = new UiLabel("BIOMASS:", 0.3f, 30.0f, 30.0f, new Color(0.4f, 0.5f, 1.0f, 1.0f), font);
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
            biomassIndicator.draw(spriteBatch);
            spriteBatch.end();
        }
        else {
            Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
            uiCam.update();
            spriteBatch.begin();
            biomassIndicator.draw(spriteBatch);
            nextDayButton.draw(spriteBatch);
            speedIndicator.draw(spriteBatch);
            digestionIndicator.draw(spriteBatch);
            fireIndicator.draw(spriteBatch);

            speedIncrease.draw(spriteBatch);
            digestionIncrease.draw(spriteBatch);
            fireIncrease.draw(spriteBatch);
            spriteBatch.end();
        }
    }
    public void update(float dt)
    {
        if(Gdx.input.isTouched()) {
            float mouseX = (1920.0f / Gdx.graphics.getWidth()) * Gdx.input.getX();
            float mouseY = (1080.0f / Gdx.graphics.getHeight()) * Gdx.input.getY();
            if(upgradeScreen) {
                nextDayButton.press(mouseX, mouseY);
                speedIncrease.press(mouseX, mouseY);
                digestionIncrease.press(mouseX, mouseY);
                fireIncrease.press(mouseX, mouseY);
            }
            else {
                pauseButton.press(mouseX, mouseY);
            }
        }

        if(controller.isGameEnded()) {
            upgradeScreen = true;
            paused = true;
            playerBiomass = player.getBiomass();
            controller.continueGame();
        }


        if(upgradeScreen) {
            if(paused) {
                biomassIndicator.setText("BIOMASS:" + playerBiomass);
                biomassIndicator.update(dt);
                nextDayButton.update(dt);

                speedIndicator.setText("SPEED:" + speedLevel);
                digestionIndicator.setText("DIGEST:" + digestionLevel);;
                fireIndicator.setText("FIRE:" + fireLevel);

                speedIndicator.update(dt);
                digestionIndicator.update(dt);
                fireIndicator.update(dt);

                speedIncrease.update(dt);
                digestionIncrease.update(dt);
                fireIncrease.update(dt);

                if(nextDayButton.isClicked()) {
                    paused = false;
                    controller.generateWorld(controller.getCurrentDay() + 1);
                    player = controller.getPlayer();
                    player.setBiomass(playerBiomass);
                    player.setup(speedLevel, digestionLevel, fireLevel);
                    objectList = controller.getObjectList();
                    upgradeScreen = false;
                }
                if(speedIncrease.isClicked()) {
                    if(playerBiomass >= 100.0f) {
                        speedLevel += 1;
                        System.out.println(playerBiomass);
                        playerBiomass -= 100.0f;
                        System.out.println(playerBiomass);
                    }
                }
                if(digestionIncrease.isClicked()) {
                    if(playerBiomass >= 100.0f) {
                        digestionLevel += 1;
                        playerBiomass -= 100.0f;
                    }
                }
                if(fireIncrease.isClicked()) {
                    if(playerBiomass >= 100.0f) {
                        fireLevel += 1;
                        playerBiomass -= 100.0f;
                    }
                }
            }
        }
        else {
            biomassIndicator.setText("BIOMASS:" + player.getBiomass());
            biomassIndicator.update(dt);
            pauseButton.update(dt);
            if(pauseButton.isClicked()) {
                paused = !paused;
            }
            if(!paused) {
                controller.update(dt);
                doPhysicsStep(dt);
            }
        }
        handleInput();
        Timer t;
    }
    public void handleInput()
    {
        if(InputHandler.isKeyDown(InputHandler.D))player.turnRight();
        if(InputHandler.isKeyDown(InputHandler.A))player.turnLeft();
        if(Gdx.input.isTouched()) {
            if(Gdx.input.getX() > Gdx.graphics.getWidth()/2)player.turnRight();
            else player.turnLeft();
        }
    }
    //private float accumulator = 0;

    private void doPhysicsStep(float deltaTime) {
        mainWorld.step(deltaTime, 6, 2);
    }
       /* // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/60.0f) {
            mainWorld.step(1/60.0f, 6, 2);
            accumulator -= 1/60.0f;
        }
    }
*/
}
