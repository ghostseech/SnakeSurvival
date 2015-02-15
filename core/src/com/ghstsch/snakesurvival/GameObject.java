package com.ghstsch.snakesurvival;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by aaaa on 15.02.2015.
 */
public interface GameObject {
    public void update(float dt);
    public void draw(ShapeRenderer shapeRenderer);
    public Vector2 getPosition();
    public float getAngle();
}
