package com.ghstsch.snakesurvival.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.ghstsch.snakesurvival.Worlds.WorldController;

/**
 * Created by aaaa on 06.03.2015.
 */
public class DayFinisher extends PhysicalObject {
    private WorldController controller;
    public static final float radius = 4.0f;

    static Texture texture = new Texture(Gdx.files.internal("textures/level_ender_1.png"));
    public DayFinisher(float x, float y, float angle, World world, WorldController controller) {
        super(x, y, angle, world);
        createShape(x, y, angle);
        this.controller = controller;

    }

    @Override
    public void createShape(float x, float y, float angle) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

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

    @Override
    public void update(float dt) {

    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, getPosition().x - radius, getPosition().y - radius, radius * 2, radius * 2);
    }

    @Override
    public void resolveCollision(PhysicalObject object) {
        if(object.getClass() == SnakeSegment.class) {
            if(((SnakeSegment)object).isHead()) controller.endGame();
        }
    }
    @Override
    public void dispose() {
        world.destroyBody(body);
    }
}
