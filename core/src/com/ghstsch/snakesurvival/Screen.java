package com.ghstsch.snakesurvival;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by aaaa on 14.02.2015.
 */
public abstract class Screen {
    ScreenManager screenManager;
    ResourseManager resourseManager;

    Screen(ScreenManager screenManager, ResourseManager resourseManager) {
        this.screenManager = screenManager;
        this.resourseManager = resourseManager;
    }

    public abstract void init();
    public abstract void draw();
    public abstract void update(float dt);
    public abstract void handleInput();
    public abstract void dispose();
}
