package com.ghstsch.snakesurvival.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;


/**
 * Created by aaaa on 04.03.2015.
 */
public class Fruit extends PhysicalObject{
    public static final int APPLE = 1;
    public static final int BANANA = 2;
    public static final int ORANGE = 3;

    public static Texture appleTexture  = new Texture(Gdx.files.internal("textures/apple.png"));
    public static Texture bananaTexture = new Texture(Gdx.files.internal("textures/banana.png"));
    public static Texture orangeTexture = new Texture(Gdx.files.internal("textures/orange.png"));

    private Texture texture;
    private boolean dead;
    private float biomass;

    public Fruit(int type, float x, float y, float angle, World world) {
        super(x, y, angle, world);
        createShape(x, y, angle);
        if(type == APPLE) {
            texture = appleTexture;
            biomass = 10.0f;
        }
        else if(type == BANANA) {
            texture = bananaTexture;
            biomass = 20.0f;
        }
        else if(type == ORANGE) {
            texture = orangeTexture;
            biomass = 30.0f;
        }
        dead = false;
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
    public void update(float dt) {
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, body.getPosition().x - 30.0f, body.getPosition().y - 30.0f, body.getPosition().x, body.getPosition().y, 60.0f, 60.0f, 1.0f, 1.0f, body.getAngle() * 0.017f, 1, 1, 32, 32, false, false);
    }

    @Override
    public void resolveCollision(PhysicalObject object) {
        if(object.getClass() == SnakeSegment.class) {
            if(((SnakeSegment)object).isHead()) dead = true;
        }
    }

    @Override
    public void dispose() {
        world.destroyBody(body);
    }
    float getBiomass() {
        return biomass;
    }
}
