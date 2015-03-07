package com.ghstsch.snakesurvival.Screens;

import com.ghstsch.snakesurvival.ResourseManager;

/**
 * Created by aaaa on 14.02.2015.
 */
public abstract class Screen {
    protected ScreenManager screenManager;
    protected ResourseManager resourseManager;

    public Screen(ScreenManager screenManager, ResourseManager resourseManager) {
        this.screenManager = screenManager;
        this.resourseManager = resourseManager;
    }

    public abstract void init();
    public abstract void draw();
    public abstract void update(float dt);
    public abstract void handleInput();
    public abstract void dispose();
}
