package com.ghstsch.snakesurvival;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by aaaa on 17.02.2015.
 */
public class SnakeSegment extends PhysicalObject{
    SnakeSegment(float x, float y, float angle, World world) {
        super(x, y, angle, world);
    }
    static final Vector2 shape[] = new Vector2[3];
    static {
        shape[0].set(-7.0f, 0.0f);
        shape[1].set(0.0f, 7.0f);
        shape[2].set(7.0f, 0.0f);
    }
    public void draw(ShapeRenderer shapeRenderer) {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.9f, 0.8f, 0.0f, 1);
        shapeRenderer.circle(body.getPosition().x, body.getPosition().y, 30.0f);
        shapeRenderer.setColor(1.0f, 1.0f, 0.0f, 1);
        //shapeRenderer.circle(body.getPosition().x, body.getPosition().y, 30.0f);
        shapeRenderer.end();
    }
    public void update(float dt) {

    }
    public void createShape(float x, float y, float angle) {
        BodyDef firstDef = new BodyDef();
        firstDef.type = BodyDef.BodyType.DynamicBody;
        firstDef.position.set(x, y);

        body = world.createBody(firstDef);
        PolygonShape shape = new PolygonShape();

        shape.set(SnakeSegment.shape);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;

        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();
        body.setTransform(x, y, angle);
    }
}
