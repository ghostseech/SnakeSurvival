package com.ghstsch.snakesurvival.Ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Vector;

/**
 * Created by aaaa on 07.03.2015.
 */
public class UiProcessor {
    Vector<UiElement> ui;
    Vector<UiButton> clickedList;
    public UiProcessor() {
        clickedList = new Vector<UiButton>();
    }
    public void setUi(Vector<UiElement> ui) {
        this.ui = ui;
        clickedList.clear();
    }
    public void draw(SpriteBatch batch) {
        if(ui != null) {
            for(int i = 0; i < ui.size(); i++) {
                ui.get(i).draw(batch);
            }
        }
    }
    public void update() {
        if (ui != null) {
            if (Gdx.input.isTouched()) {
                float mouseX = (1920.0f / Gdx.graphics.getWidth()) * Gdx.input.getX();
                float mouseY = (1080.0f / Gdx.graphics.getHeight()) * Gdx.input.getY();
                pressButtons(mouseX, mouseY);
            }
            else {
                updateClickedList();
            }
        }
    }
    public Vector<UiButton> getClickedButtonList() {
        return clickedList;
    }
    public Vector<UiElement> getUi() {
        return ui;
    }
    private void pressButtons(float x, float y) {
        for(int i = 0; i < ui.size(); i++) {
            if(ui.get(i).getClass() == UiButton.class) ((UiButton)ui.get(i)).press(x, y);
        }
    }
    private void updateClickedList() {
        for(int i = 0; i < ui.size(); i++) {
            if(ui.get(i).getClass() == UiButton.class) {
                if(((UiButton)ui.get(i)).isClicked()) {
                    clickedList.add((UiButton)ui.get(i));
                }
                else clickedList.remove(ui.get(i));
            }
        }
    }
}
