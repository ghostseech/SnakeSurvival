package com.ghstsch.snakesurvival;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by aaaa on 15.02.2015.
 */
public abstract class PhysicalObject implements GameObject {
    protected Body body;
    protected World world;
    public PhysicalObject(float x, float y, float angle, World world) {
        this.world = world;
        createShape(x, y, angle);
    }

    public abstract void createShape(float x, float y, float angle);
    public Body getBody() {
        return body;
    }
    public Vector2 getPosition() {
        return body.getPosition();
    }
    public float getAngle() {
        return body.getAngle();
    }
}
