package com.ghstsch.snakesurvival;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by aaaa on 15.02.2015.
 */
public class Player extends PhysicalObject {
    Player(float x, float y, float angle, World world) {
        super(x, y, angle, world);
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
        CircleShape circle = new CircleShape();
        circle.setRadius(30.0f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;

        Fixture fixture = body.createFixture(fixtureDef);
        circle.dispose();
        body.setTransform(x, y, angle);
    }
}
