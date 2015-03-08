package com.ghstsch.snakesurvival.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by aaaa on 08.03.2015.
 */
public class Ground implements GameObject {
    float width;
    float height;
    static Texture texture = new Texture(Gdx.files.internal("textures/ground_2.png"), true);

    public Ground(float width, float height) {
        this.width = width;
        this.height = height;
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(0.0f, 0.0f);
    }

    @Override
    public float getAngle() {
        return 0.0f;
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, -width/2, -height/2, width, height, 1, 1, (int)width/2, (int)height/2, false, false);
        //batch.draw(texture, -width / 2, -height / 2, width, height);
    }

    @Override
    public void dispose() {

    }
}
