package com.ghstsch.snakesurvival.Objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by aaaa on 08.03.2015.
 */
public class Poison extends  PhysicalObject {
    private static final float radius = 0.5f;
    private float damage;
    private boolean dead;
    private float dirX;
    private float dirY;
    private float speed;

    public Poison(float damage, float dirX, float dirY, float speed, float x, float y, float angle, World world) {
        super(x, y, angle, world);
        createShape(x, y, angle);
        this.damage = damage;
        this.dirX = dirX;
        this.dirY = dirY;
        this.speed = speed;

        dead = false;
    }

    @Override
    public void update(float dt) {
        body.setLinearVelocity(dirX * speed * 50.0f, dirY * speed * 50.0f);
        //body.applyLinearImpulse(new Vector2(dirX * speed / 4, dirY * speed / 4), getPosition(), true);
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public void draw(SpriteBatch batch) {
        //batch.draw(texture, body.getPosition().x - 30.0f, body.getPosition().y - 30.0f, body.getPosition().x, body.getPosition().y, 60.0f, 60.0f, 1.0f, 1.0f, body.getAngle() * 0.017f, 1, 1, 32, 32, false, false);
    }

    @Override
    public void resolveCollision(PhysicalObject object) {
        dead = true;
    }

    @Override
    public void dispose() {
        world.destroyBody(body);
    }

    @Override
    public void createShape(float x, float y, float angle) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);
        //body.setBullet(true);
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.7f;
        fixtureDef.friction = 0.7f;
        fixtureDef.restitution = 0.7f;

        //body.setLinearDamping(0.25f);
        //body.setAngularDamping(7.0f);
        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();
        body.setTransform(x, y, angle);
        body.setUserData(this);
    }
}
