package com.ghstsch.snakesurvival.Objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by aaaa on 07.03.2015.
 */
public class Stone extends PhysicalObject {
    private Vector2 shape[];
    private float minSize;
    private float maxSize;

    public Stone(float x, float y, float angle, World world, float minSize, float maxSize) {
        super(x, y, angle, world);
        this.minSize = minSize;
        this.maxSize = maxSize;
        createShape(x, y, angle);
    }

    @Override
    public void createShape(float x, float y, float angle) {
        int pointscount = (int)(MathUtils.random(5.0f, 8.0f));
        float angleOffset = 360/pointscount;

        shape = new Vector2 [pointscount];

        for(int i = 0; i < pointscount; i++) {
            float size = MathUtils.random(minSize, maxSize);

            float xcoords = MathUtils.cos(angleOffset * 0.017f * i) * size;
            float ycoords = MathUtils.sin(angleOffset * 0.017f * i) * size;
            shape[i] = new Vector2(xcoords, ycoords);

        }

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.set(this.shape);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.7f;
        fixtureDef.friction = 4.7f;
        fixtureDef.restitution = 4.7f;

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

    }

    @Override
    public void resolveCollision(PhysicalObject object) {

    }

    @Override
    public void dispose() {
        world.destroyBody(body);
    }
}
