package com.ghstsch.snakesurvival;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Vector;

/**
 * Created by aaaa on 15.02.2015.
 */

public class MenuScreen implements Screen {
    OrthographicCamera cam;
    BitmapFont font;
    SpriteBatch batch;
    Vector<UiElement> startScreen;
    ScreenManager screenManager;
    int currentScreen;

    public void init(ScreenManager screenManager, BitmapFont font) {
        this.screenManager = screenManager;
        this.font = font;
        batch = new SpriteBatch();
        cam = new OrthographicCamera();
        cam.setToOrtho(true, 1920, 1080);
        batch.setProjectionMatrix(cam.combined);
        startScreen = new Vector<UiElement>();
        startScreen.add(new UiLabel("SNAKE SURVIVAL", 1.0f, 100.0f, 100.0f, new Color(1.0f, 0.0f, 0.0f, 1.0f), font));
        startScreen.add(new UiButton(200.0f, 300.0f, 800.0f, 100.0f, "START GAME", UiButton.standard, new Color(1.0f, 0.5f, 0.0f, 1.0f), new Color(0.4f, 1.0f, 1.0f, 1.0f), font));
        startScreen.add(new UiButton(200.0f, 500.0f, 800.0f, 100.0f, "EXIT", UiButton.standard, new Color(1.0f, 0.5f, 0.0f, 1.0f), new Color(0.4f, 1.0f, 1.0f, 1.0f), font));
        for(int i = 0; i < startScreen.size(); i++) {
            startScreen.get(i).startAnimation(UiElement.FIRST_ANIMATION);
        }
    }
    public void draw() {
        cam.update();
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
        batch.begin();

        for(int i = 0; i < startScreen.size(); i++) {
            startScreen.get(i).draw(batch);
        }
        batch.end();
    }

    public void update(float dt) {
        handleInput();
        for(int i = 0; i < startScreen.size(); i++) {
            startScreen.get(i).update(dt);
        }
    }
    private void handleButton(UiButton button) {
        if(button.getText() == "START GAME") {
            screenManager.setScreen(new GameScreen(), font);
        }
        else if(button.getText() == "EXIT") {
            Gdx.app.exit();
        }
    }
    public void handleInput() {
        if(Gdx.input.isTouched()) {
            float mouseX = (1920.0f / Gdx.graphics.getWidth()) * Gdx.input.getX();
            float mouseY = (1080.0f / Gdx.graphics.getHeight()) * Gdx.input.getY();
            for(int i = 0; i < startScreen.size(); i++) {
                if(startScreen.get(i).getClass() == UiButton.class) ((UiButton)startScreen.get(i)).press(mouseX, mouseY);
            }
        }
        for(int i = 0; i < startScreen.size(); i++) {
            if(startScreen.get(i).getClass() == UiButton.class) {
                if( ((UiButton) startScreen.get(i)).isClicked() ) {
                    handleButton((UiButton) startScreen.get(i));
                }
            }
        }

    }
}
