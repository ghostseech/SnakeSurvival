package com.ghstsch.snakesurvival;

/**
 * Created by aaaa on 14.02.2015.
 */
public interface Screen {
    public void init();
    public void draw();
    public void update(float dt);
    public void handleInput();
}
