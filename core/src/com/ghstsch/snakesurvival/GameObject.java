package com.ghstsch.snakesurvival;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by aaaa on 15.02.2015.
 */
public interface GameObject {
    public void update(float dt);
    public void draw(SpriteBatch batch);
    public Vector2 getPosition();
    public float getAngle();
    public boolean isDead();
    public void dispose();
}
