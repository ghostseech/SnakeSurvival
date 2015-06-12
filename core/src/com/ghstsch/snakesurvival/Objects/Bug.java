package com.ghstsch.snakesurvival.Objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by aaaa on 09.03.2015.
 */
public class Bug extends Enemy {
    public Bug(float x, float y, float angle, World world, Player player) {
        super(x, y, angle, world, player);
        speed = 3.0f;
    }

    @Override
    public void createShape(float x, float y, float angle) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(1.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.7f;
        fixtureDef.friction = 0.7f;
        fixtureDef.restitution = 0.7f;

        body.setLinearDamping(0.25f);
        body.setAngularDamping(7.0f);
        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();
        body.setTransform(x, y, angle);
        body.setUserData(this);
    }

    @Override
    public void behavoir() {
        double distance = Math.sqrt((getPosition().x - player.getPosition().x) * (getPosition().x - player.getPosition().x)
                + (getPosition().y - player.getPosition().y) * (getPosition().y - player.getPosition().y));

        if(distance <= 15.0f) goTo(player.getPosition().x, player.getPosition().y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        //batch.draw(texture, body.getPosition().x - 30.0f, body.getPosition().y - 30.0f, body.getPosition().x, body.getPosition().y, 60.0f, 60.0f, 1.0f, 1.0f, body.getAngle() * 0.017f, 1, 1, 32, 32, false, false);
    }

    @Override
    public void resolveCollision(PhysicalObject object) {

    }

    @Override
    public void dispose() {
        world.destroyBody(body);
    }
}
