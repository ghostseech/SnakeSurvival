package com.ghstsch.snakesurvival;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.ghstsch.snakesurvival.Ui.UiButton;
import com.ghstsch.snakesurvival.Ui.UiElement;
import com.ghstsch.snakesurvival.Ui.UiLabel;
import com.ghstsch.snakesurvival.Ui.UiProcessor;

import java.util.Vector;

/**
 * Created by aaaa on 15.02.2015.
 */
public class GameScreen extends Screen {
    public static final int FOREST_WORLD = 1;

    Vector<UiElement> ingameUi;
    Vector<UiElement> pauseUi;

    UiButton pauseButton;
    UiButton continueGameButton;
    UiButton backToMenuButton;

    UiLabel biomassIndicator;

    UiProcessor uiProcessor;

    OrthographicCamera worldCam;
    OrthographicCamera uiCam;

    SpriteBatch spriteBatch;
    WorldController controller;
    Box2DDebugRenderer b2render;

    public GameScreen(ScreenManager screenManager, ResourseManager resourseManager) {
        super(screenManager, resourseManager);
    }

    @Override
    public void init()
    {
        uiProcessor = new UiProcessor();

        ingameUi = new Vector<UiElement>();
        pauseUi = new Vector<UiElement>();

        pauseButton = new UiButton(
                1750.0f, 0.0f,
                170.0f, 50.0f,
                "PAUSE", UiButton.standard,
                resourseManager.getUiColor(),
                resourseManager.getUiTextColor(),
                resourseManager.getFont(0));

        continueGameButton = new UiButton(
                200.0f, 300.0f,
                800.0f, 100.0f,
                "CONTINUE", UiButton.standard,
                resourseManager.getUiColor(),
                resourseManager.getUiTextColor(),
                resourseManager.getFont(0));

        backToMenuButton = new UiButton(
                200.0f, 500.0f,
                800.0f, 100.0f,
                "BACK TO MENU", UiButton.standard,
                resourseManager.getUiColor(),
                resourseManager.getUiTextColor(),
                resourseManager.getFont(0));

        biomassIndicator = new UiLabel("BIOMASS:", 0.3f,
                30.0f, 30.0f,
                resourseManager.getUiTextColor(),
                resourseManager.getFont(0));

        ingameUi.add(pauseButton);
        ingameUi.add(biomassIndicator);

        pauseUi.add(pauseButton);
        pauseUi.add(continueGameButton);
        pauseUi.add(backToMenuButton);
        pauseUi.add(biomassIndicator);

        uiProcessor.setUi(ingameUi);

        worldCam = new OrthographicCamera();
        worldCam.setToOrtho(true, 1920, 1080);
        uiCam = new OrthographicCamera();
        uiCam.setToOrtho(true, 1920, 1080);

        spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(worldCam.combined);

        controller = resourseManager.getCurrentController();
        b2render = new Box2DDebugRenderer(true,true,false,true,false,true);
    }

    @Override
    public void draw()
    {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));

        worldCam.position.x = controller.getPlayer().getPosition().x;
        worldCam.position.y = controller.getPlayer().getPosition().y;

        worldCam.update();

        b2render.render(controller.getWorld(), worldCam.combined);

        //Vector<GameObject> objectList = controller.getObjectList();
        //for(int i = 0; i < objectList.size(); i++) objectList.get(i).draw(spriteBatch);

        uiCam.update();
        spriteBatch.begin();
        uiProcessor.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void update(float dt)
    {
        handleInput();
        if(uiProcessor.getUi() == ingameUi) {
            controller.update(dt);
            if(controller.isGameEnded()) {
                screenManager.setScreen(ScreenManager.UPDATE_SCREEN, true);
                resourseManager.setPlayerStats(controller.getPlayer().getStats());
            }
        }

        biomassIndicator.setText("BIOMASS:" + (int)controller.getPlayer().getStats().getBiomass());
    }

    @Override
    public void handleInput()
    {
        uiProcessor.update();

        Vector<UiButton> clickedList = uiProcessor.getClickedButtonList();

        for(int i = 0; i < clickedList.size(); i++) {
            UiButton button = clickedList.get(i);
            if (button == pauseButton) {
                uiProcessor.setUi(pauseUi);
            }
            else if (button == continueGameButton) {
                uiProcessor.setUi(ingameUi);
            }
            else if (button == backToMenuButton) {
                screenManager.setScreen(ScreenManager.MENU_SCREEN, true);
            }
        }

        if(InputHandler.isKeyDown(InputHandler.D))controller.getPlayer().turnRight();
        if(InputHandler.isKeyDown(InputHandler.A))controller.getPlayer().turnLeft();
        if(Gdx.input.isTouched()) {
            if(Gdx.input.getX() > Gdx.graphics.getWidth()/2)controller.getPlayer().turnRight();
            else controller.getPlayer().turnLeft();
        }
    }

    @Override
    public void dispose() {

    }
}
