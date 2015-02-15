package com.ghstsch.snakesurvival;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by aaaa on 15.02.2015.
 */
public class GameScreen implements Screen {
    OrthographicCamera cam;
    ShapeRenderer shapeRenderer;
    World mainWorld;
    Body first;
    Body groundBody;

    public void init()
    {
        cam = new OrthographicCamera();
        cam.setToOrtho(false , 1920, 1080);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        mainWorld = new World(new Vector2(0, 10), true);

        BodyDef firstDef = new BodyDef();
        firstDef.type = BodyDef.BodyType.DynamicBody;
        firstDef.position.set(100,300);

        first = mainWorld.createBody(firstDef);
        CircleShape circle = new CircleShape();
        circle.setRadius(20.0f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 15.5f;
        fixtureDef.friction = 15.4f;

        Fixture fixture = first.createFixture(fixtureDef);
        circle.dispose();

        CreateStatic();
    }
    public void draw()
    {
        cam.update();
        Gdx.gl.glClearColor(0.2f, 0.3f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 0, 1);
        shapeRenderer.line(0, 0, 1900, 100);
        shapeRenderer.rect(groundBody.getPosition().x, groundBody.getPosition().y - 10.0f, 1000.0f, 20f);
        shapeRenderer.circle(first.getPosition().x, first.getPosition().y, 20.0f);
        shapeRenderer.end();
    }
    public void update(float dt)
    {
        handleInput();
        mainWorld.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }
    public void handleInput()
    {
        if(InputHandler.isKeyDown(InputHandler.W))first.applyLinearImpulse(0.0f, 100000.0f, 0.0f,0.0f,true);
        if(InputHandler.isKeyDown(InputHandler.S))first.applyLinearImpulse(0.0f, -100000.0f, 0.0f,0.0f,true);
        if(InputHandler.isKeyDown(InputHandler.D))first.applyLinearImpulse(100000.0f, 0.0f, 0.0f,0.0f,true);
        if(InputHandler.isKeyDown(InputHandler.A))first.applyLinearImpulse(-100000.0f, 0.0f, 0.0f,0.0f,true);
    }

    private void CreateStatic()
    {
        BodyDef groundBodyDef =new BodyDef();
// Set its world position
        groundBodyDef.position.set(new Vector2(0, -10));

// Create a body from the defintion and add it to the world

        groundBody = mainWorld.createBody(groundBodyDef);
// Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
// Set the polygon shape as a box which is twice the size of our view port and 20 high
// (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(1000.0f, 10.0f);
// Create a fixture from our polygon shape and add it to our ground body
        groundBody.createFixture(groundBox, 0.0f);
// Clean up after ourselves
        groundBox.dispose();
        groundBody.setTransform(40.0f,200.0f,0.0f);
    }
}
