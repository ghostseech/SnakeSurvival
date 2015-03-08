package com.ghstsch.snakesurvival.Objects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.*;

/**
 * Created by aaaa on 17.02.2015.
 */
public class SnakeSegment extends PhysicalObject {
    static Texture texture = new Texture(Gdx.files.internal("textures/snake_segment.png"), true);

    private static final Vector2 shape[] = new Vector2[] {  //triangle shape of segment
            new Vector2( - Player.segmentSize / 2    ,   Player.segmentSize / 2), //left point of shape
            new Vector2(   0.0f                      , - Player.segmentSize / 2), //central point of shape
            new Vector2(   Player.segmentSize / 2    ,   Player.segmentSize / 2)};//right point of shape

    private Player player;

    public SnakeSegment(float x, float y, float angle, Player player, World world, Texture texture) {
        super(x, y, angle, world);
        createShape(x, y, angle);
        //this.texture = texture;
        this.player = player;
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(
                texture, body.getPosition().x - Player.segmentSize / 2, body.getPosition().y - Player.segmentSize / 2,
                Player.segmentSize/2, Player.segmentSize/2,
                Player.segmentSize, Player.segmentSize,
                1.0f, 1.0f, body.getAngle() * MathUtils.radiansToDegrees,
                1, 1, 256, 256, false, true);
    }

    @Override
    public void resolveCollision(PhysicalObject object) {
        player.resolveCollision(object, this);
    }

    @Override
    public void createShape(float x, float y, float angle) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.set(SnakeSegment.shape);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.2f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.4f;
        body.setLinearDamping(1.25f);
        body.setAngularDamping(30.0f);
        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();
        body.setTransform(x, y, angle);
        body.setUserData(this);
    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public void dispose() {
        world.destroyBody(body);
    }

    public void joinToNext(SnakeSegment segment) {
        RevoluteJointDef jointDef = new RevoluteJointDef();

        float angle = body.getAngle() - 0.5f * 3.1417f; //direction of segment
        float xoffset = - MathUtils.cos(angle) * Player.segmentSize/2;
        float yoffset = - MathUtils.sin(angle) * Player.segmentSize/2;

        jointDef.upperAngle = 50.0f * MathUtils.degreesToRadians;
        jointDef.lowerAngle = -50.0f * MathUtils.degreesToRadians;

        jointDef.initialize(segment.getBody(), this.getBody(), //connect this body to segment body
                        new Vector2(segment.getPosition().x + xoffset, segment.getPosition().y + yoffset));

        jointDef.enableLimit = true;

        Joint joint = world.createJoint(jointDef);
    }

    public boolean isHead() {
        return player.getHead() == this;
    }

}
