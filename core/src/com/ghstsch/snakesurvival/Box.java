package com.ghstsch.snakesurvival;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
/**
 * Created by aaaa on 15.02.2015.
 */
public class Box extends PhysicalObject {
    float sizeX, sizeY;

    Box(float x, float y, float sizeX, float sizeY, float angle, World world) {
        super(x, y, angle, world);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        pastCreateShape(x, y, angle);
    }
    public void draw(SpriteBatch batch) {
       /* shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 0, 1);
        shapeRenderer.rect(body.getPosition().x-sizeX/2, body.getPosition().y - sizeY/2 , sizeX/2 , sizeY/2, sizeX, sizeY, 1.0f, 1.0f, body.getAngle() / 0.017f);
        shapeRenderer.end();*/

    }
    public void createShape(float x, float y, float angle) {

    }
    private void pastCreateShape(float x, float y, float angle) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(x, y));
        bodyDef.angle = angle * 0.017f;
        body = world.createBody(bodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(sizeX/2, sizeY/2);
        body.createFixture(groundBox, 0.0f);
        groundBox.dispose();
       // body.setTransform(x, y, angle);
    }
    public void update(float dt) {

    }
}
