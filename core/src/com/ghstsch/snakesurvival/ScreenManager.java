package com.ghstsch.snakesurvival;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by aaaa on 03.03.2015.
 */
public class ScreenManager {
    Screen currentScreen;
    void setScreen(Screen screen, BitmapFont font) {
        currentScreen = screen;
        screen.init(this, font);
    }
    void draw() {
        currentScreen.draw();
    }
    void update(float dt) {
        currentScreen.update(dt);
    }
}
