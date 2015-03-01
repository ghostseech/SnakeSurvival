package com.ghstsch.snakesurvival;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.*;

/**
 * Created by aaaa on 17.02.2015.
 */
public class SnakeSegment extends PhysicalObject{
    Texture texture;
    SnakeSegment(float x, float y, float angle, World world, Texture texture) {
        super(x, y, angle, world);
        this.texture = texture;
    }

    static final short collisionCategory = 0x08;
    static final short collisionMask = collisionCategory;

    static final Vector2 shape[] = new Vector2[] {
            new Vector2(-Player.segmentSize/2,  Player.segmentSize / 2),
            new Vector2(0.0f,-Player.segmentSize / 2),
            new Vector2(Player.segmentSize/2,   Player.segmentSize / 2)};

    public void draw(SpriteBatch batch) {
        batch.begin();
        batch.draw(
                texture, body.getPosition().x - Player.segmentSize / 2, body.getPosition().y - Player.segmentSize / 2,
                Player.segmentSize/2, Player.segmentSize/2,
                Player.segmentSize, Player.segmentSize,
                1.0f, 1.0f, body.getAngle() / 0.017f,
                1, 1, 256, 256, false, true);
        batch.end();
    }
    public void update(float dt) {

    }
    void joinToNext(SnakeSegment segment) {
        RevoluteJointDef jointDef = new RevoluteJointDef();
        //jointDef.bodyA = segment.getBody();
        //jointDef.bodyB = this.getBody();
        float angle = body.getAngle() - 0.5f * 3.1417f;
        jointDef.initialize(segment.getBody(), this.getBody(), new Vector2(segment.getPosition().x - MathUtils.cos(angle) * Player.segmentSize/2, segment.getPosition().y - MathUtils.sin(angle)  * Player.segmentSize/2));
        Joint joint = world.createJoint(jointDef);
    }
    public void createShape(float x, float y, float angle) {
        Filter f = new Filter();
        f.categoryBits = collisionCategory;
        f.maskBits = collisionMask;

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
        //fixture.setFilterData(f);
        shape.dispose();
        body.setTransform(x, y, angle);
    }
}
