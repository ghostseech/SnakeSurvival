package com.ghstsch.snakesurvival;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by aaaa on 14.02.2015.
 */
public interface Screen {
    public void init(ScreenManager screenManager, BitmapFont font);
    public void draw();
    public void update(float dt);
    public void handleInput();
}
