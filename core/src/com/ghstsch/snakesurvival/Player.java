package com.ghstsch.snakesurvival;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import java.util.LinkedList;

/**
 * Created by aaaa on 15.02.2015.
 */
public class Player implements GameObject {

    static final float segmentSize = 50.0f;
    static Texture segmentTexture = new Texture("textures/snake_segment.png");;

    LinkedList<SnakeSegment> segmentList;
    World world;
    boolean left;
    boolean right;
    float snakeSpeed;
    float rotationSpeed;
    boolean dead;

    float biomass;

    float maxBiomassCost;

    int speedLevel;
    int digestionLevel;
    int fireLevel;

    Player(float x, float y, float angle, World world) {
        this.world = world;

        segmentList = new LinkedList<SnakeSegment>();
        //snake always have at least 3 segments
        segmentList.add(new SnakeSegment(x, y, 0.0f, this, world, segmentTexture));
        segmentList.get(0).body.setTransform(x, y, angle * 0.017f);
        segmentList.get(0).changeFilter(SnakeSegment.headCollisionCategory);
        addSegments(2);
        snakeSpeed = 40000.0f;
        rotationSpeed = 8000.0f;
        right = false;
        left = false;
        dead = false;
        biomass = 0.0f;
        setup(1, 1, 0);
    }
    public void moveForward() {

    }
    public void turnRight() {
        right = true;
        left = false;
    }
    public void turnLeft() {
        right = false;
        left = true;
    }
    public void draw(SpriteBatch batch) {
        for(int i = 0; i < segmentList.size(); i++) {
            segmentList.get(i).draw(batch);
        }
    }
    public void update(float dt) {

        float impulseDirX = -snakeSpeed * MathUtils.cos(getAngle() + 0.5f * 3.1417f);
        float impulseDirY = -snakeSpeed * MathUtils.sin(getAngle() + 0.5f * 3.1417f);
        getHead().getBody().applyLinearImpulse(impulseDirX, impulseDirY, getPosition().x, getPosition().y, true);
        if(right) {
            float rotationDirX = rotationSpeed * MathUtils.cos(getAngle());
            float rotationDirY = rotationSpeed * MathUtils.sin(getAngle());
            getHead().getBody().applyLinearImpulse(rotationDirX, rotationDirY, getPosition().x, getPosition().y, true);
        }
        else if(left) {
            float rotationDirX = -rotationSpeed * MathUtils.cos(getAngle());
            float rotationDirY = -rotationSpeed * MathUtils.sin(getAngle());
            getHead().getBody().applyLinearImpulse(rotationDirX, rotationDirY, getPosition().x, getPosition().y, true);
        }

        right = false;
        left = false;

        int segmentsDelta = 3 + (int)(biomass/100.0f) - segmentList.size();
        if(segmentsDelta >= 1) {
            addSegments(segmentsDelta);
        }
        if(segmentsDelta <= -1) {
            removeSegments(segmentsDelta);
        }
    }
    void addSegments(int count) {
        for(int i = 0; i < count; i++) {
            //calculate rotation and position of new segment
            float newrotate = segmentList.getLast().getAngle();
            float newx = segmentList.getLast().getPosition().x + MathUtils.cos(newrotate + 0.5f * 3.1417f) * segmentSize;
            float newy = segmentList.getLast().getPosition().y + MathUtils.sin(newrotate + 0.5f * 3.1417f) * segmentSize;

            segmentList.addLast(new SnakeSegment(newx, newy, newrotate, this, world, segmentTexture));
            //join new segment with next
            segmentList.getLast().joinToNext(segmentList.get((segmentList.size()-1) - 1));
        }
    }
    void removeSegments(int count) {
        for(int i = 0; i < count; i++) {
            if(segmentList.size() > 3) {
                segmentList.getLast().dispose();
                segmentList.remove(segmentList.getLast());
            }
        }
    }
    void resolveCollision(Filter f, SnakeSegment segment) {
        if(f.categoryBits == Fruit.collisionCategory && segment == getHead()) {
            //removeSegments(1);
            biomass += 10.0f;
        }
    }
    SnakeSegment getSegment(int id) {
        if(id >= segmentList.size()) return segmentList.getLast();
        else                         return segmentList.get(id);
    }
    SnakeSegment getHead() {
        return segmentList.get(0);
    }
    //return position of head
    public Vector2 getPosition() {
        return segmentList.get(0).getPosition();
    }
    //return rotation of head
    public float getAngle() {
        return segmentList.get(0).getAngle();
    }
    public boolean isDead() {
        return dead;
    }
    public void dispose() {
        for(int i = 0; i < segmentList.size(); i++)segmentList.get(i).dispose();
    }
    float getBiomass() {
        return biomass;
    }
    void setBiomass(float biomass) {
        this.biomass = biomass;
    }
    void setSpeed(float speed) {
        snakeSpeed = speed;
    }
    void setup(int speedLevel, int digestionLevel, int fireLevel) {
        this.speedLevel = speedLevel;
        this.digestionLevel = digestionLevel;
        this.fireLevel = fireLevel;
        if(speedLevel == 1) {
            rotationSpeed = 4000.0f;
        }
        else if(speedLevel == 2) {
            rotationSpeed = 6000.0f;
        }
        if(digestionLevel == 1) {
            maxBiomassCost = 10.0f;
        }
        else if(digestionLevel == 2) {
            maxBiomassCost = 20.0f;
        }
        if(fireLevel == 1) {

        }
        else if(fireLevel == 2) {

        }
    }
}
