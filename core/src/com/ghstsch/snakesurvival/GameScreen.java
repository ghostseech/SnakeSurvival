package com.ghstsch.snakesurvival;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by aaaa on 15.02.2015.
 */
public class GameScreen implements Screen {
    OrthographicCamera cam;
    SpriteBatch shapeRenderer;
    World mainWorld;
    Body groundBody;
    Player player;
    Box box;
    Texture boxtexture;
    Box2DDebugRenderer b2render;
    BitmapFont font1;
    public void init()
    {
        font1 = new BitmapFont(Gdx.files.internal("fonts/font_6.fnt"), Gdx.files.internal("fonts/font_6_0.png"), true);
        //b2render = new Box2DDebugRenderer(true, true, true, true, true, true);
        b2render = new Box2DDebugRenderer(true,true,false,true,false,true);
        cam = new OrthographicCamera();
        cam.setToOrtho(true , 1920, 1080);
        shapeRenderer = new SpriteBatch();

        shapeRenderer.setProjectionMatrix(cam.combined);
        mainWorld = new World(new Vector2(0, 0), true);

        CreateStatic();
        player = new Player( 500.0f, 500.0f, 30.0f, mainWorld);
        box = new Box(700.0f, 500.0f, 100.0f, 30.0f, 30.0f, mainWorld);
        boxtexture = new Texture(Gdx.files.internal("textures/box1.png"));
    }
    public void draw()
    {
        cam.update();
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
       // Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));
       /* shapeRenderer.begin(PolygonSpriteBatch.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 0, 1);
        shapeRenderer.line(0, 0, 1900, 100);
        shapeRenderer.rect(groundBody.getPosition().x, groundBody.getPosition().y - 10.0f, 1000.0f, 20f);
        shapeRenderer.end();*/

        shapeRenderer.begin();

        shapeRenderer.draw(boxtexture, groundBody.getPosition().x, groundBody.getPosition().y - 10.0f, 1000.0f, 20.0f);
        shapeRenderer.end();
        //player.draw(shapeRenderer);
        box.draw(shapeRenderer);
        b2render.render(mainWorld, cam.combined);
        shapeRenderer.begin();
        font1.setColor(0.0f, 1.0f, 1.0f,1.0f);
        font1.setScale(0.5f);
        font1.draw(shapeRenderer, "YOBA.^:", 100.0f, 100.0f);
        font1.setScale(1.0f);
        font1.draw(shapeRenderer, "абвБВгд{}.^:", 300.0f, 300.0f);
        shapeRenderer.end();
    }
    public void update(float dt)
    {
        handleInput();
        doPhysicsStep(Gdx.graphics.getDeltaTime());
        //mainWorld.step(6f, 6, 2);
    }
    public void handleInput()
    {
        if(InputHandler.isKeyDown(InputHandler.W))player.getSegment(0).getBody().applyLinearImpulse(-10000.0f * MathUtils.cos(player.getAngle() + 0.5f * 3.1417f), -10000.0f * MathUtils.sin(player.getAngle() + 0.5f * 3.1417f), player.getPosition().x, player.getPosition().y, true);
        if(InputHandler.isKeyDown(InputHandler.S))player.getSegment(0).getBody().applyLinearImpulse(10000.0f * MathUtils.cos(player.getAngle() + 0.5f * 3.1417f), 10000.0f * MathUtils.sin(player.getAngle() + 0.5f * 3.1417f), player.getPosition().x, player.getPosition().y, true);
        if(InputHandler.isKeyDown(InputHandler.D))player.getSegment(0).getBody().applyLinearImpulse(2000.0f * MathUtils.cos(player.getAngle()), 2000.0f * MathUtils.sin(player.getAngle()), player.getPosition().x, player.getPosition().y, true);
        if(InputHandler.isKeyDown(InputHandler.A))player.getSegment(0).getBody().applyLinearImpulse(-2000.0f * MathUtils.cos(player.getAngle()), -2000.0f * MathUtils.sin(player.getAngle()), player.getPosition().x, player.getPosition().y, true);
        /*if(InputHandler.isKeyDown(InputHandler.W))player.getSegment(0).getBody().applyForce(0.0f, -100000.0f * MathUtils.sin(player.getSegment(0).getAngle() + 0.5f * 3.1417f), 0.0f, 0.0f, false);
        if(InputHandler.isKeyDown(InputHandler.S))player.getSegment(0).getBody().applyForce(0.0f, 100000.0f * MathUtils.sin(player.getSegment(0).getAngle() + 0.5f * 3.1417f), 0.0f, 0.0f, false);
        if(InputHandler.isKeyDown(InputHandler.D))player.getSegment(0).getBody().applyForce(100000.0f * MathUtils.cos(player.getSegment(0).getAngle() + 0.5f * 3.1417f), 0.0f, 0.0f, 0.0f, true);
        if(InputHandler.isKeyDown(InputHandler.A))player.getSegment(0).getBody().applyForce(-100000.0f * MathUtils.cos(player.getSegment(0).getAngle() + 0.5f * 3.1417f), 0.0f, 0.0f, 0.0f, true);*/
    }
    private float accumulator = 0;

    private void doPhysicsStep(float deltaTime) {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/60.0f) {
            mainWorld.step(1/60.0f, 6, 2);
            accumulator -= 1/60.0f;
        }
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
