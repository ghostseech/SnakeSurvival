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

    static final Vector2 shape[] = new Vector2[] {  //triangle shape of segment
            new Vector2( - Player.segmentSize / 2    ,   Player.segmentSize / 2), //left point of shape
            new Vector2(   0.0f                      , - Player.segmentSize / 2), //central point of shape
            new Vector2(   Player.segmentSize / 2    ,   Player.segmentSize / 2)};//right point of shape

    Player player;

    SnakeSegment(float x, float y, float angle, Player player, World world, Texture texture) {
        super(x, y, angle, world);
        this.texture = texture;
        this.player = player;
    }

    public void update(float dt) {

    }

    public void draw(SpriteBatch batch) {
        /*batch.begin();
        batch.draw(
                texture, body.getPosition().x - Player.segmentSize / 2, body.getPosition().y - Player.segmentSize / 2,
                Player.segmentSize/2, Player.segmentSize/2,
                Player.segmentSize, Player.segmentSize,
                1.0f, 1.0f, body.getAngle() / 0.017f,
                1, 1, 256, 256, false, true);
        batch.end(); */
    }
    void joinToNext(SnakeSegment segment) {
        RevoluteJointDef jointDef = new RevoluteJointDef();

        float angle = body.getAngle() - 0.5f * 3.1417f; //direction of segment
        float xoffset = - MathUtils.cos(angle) * Player.segmentSize/2;
        float yoffset = - MathUtils.sin(angle) * Player.segmentSize/2;
        jointDef.upperAngle = 30.0f * 0.017f;
        jointDef.lowerAngle = -30.0f * 0.017f;
        jointDef.initialize(
                segment.getBody(), this.getBody(), //connect this body to segment body
                new Vector2(segment.getPosition().x + xoffset, segment.getPosition().y + yoffset)); //center of back side next segment
        jointDef.enableLimit = true;
        Joint joint = world.createJoint(jointDef);
    }
    public void createShape(float x, float y, float angle) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.set(SnakeSegment.shape);

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
    public boolean isHead() {
        return player.getHead() == this;
    }

    public void resolveCollision(PhysicalObject object) {
        player.resolveCollision(object, this);
    }

    public boolean isDead() {
        return false;
    }

    public void dispose() {
        world.destroyBody(body);
    }
}
