package com.ghstsch.snakesurvival;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;


/**
 * Created by aaaa on 04.03.2015.
 */
public class Fruit extends PhysicalObject{
    public static final float radius = 20.0f;
    public static final int APPLE = 1;
    public static Texture appleTexture;// = (Gdx.files.internal(""))
    Texture texture;
    boolean dead;
    float biomass;
    Fruit(int type, float x, float y, float angle, World world) {
        super(x, y, angle, world);
        if(type == APPLE) {
            texture = appleTexture;
            biomass = 10.0f;
        }
        dead = false;
    }

    public void createShape(float x, float y, float angle) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 2.7f;
        fixtureDef.friction = 0.7f;
        fixtureDef.restitution = 0.7f;

        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();
        body.setTransform(x, y, angle);
        body.setUserData(this);
    }

    public void update(float dt) {
    }
    public boolean isDead() {
        return dead;
    }
    public void draw(SpriteBatch batch) {

    }
    public void resolveCollision(PhysicalObject object) {
        if(object.getClass() == SnakeSegment.class) {
            if(((SnakeSegment)object).isHead()) dead = true;
        }
    }
    public void dispose() {
        world.destroyBody(body);
    }
    float getBiomass() {
        return biomass;
    }
}
