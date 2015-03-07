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
    boolean dead;

    float snakeSpeed;
    float rotationSpeed;
    PlayerStats stats;

    float maxBiomassCost;

    Player(float x, float y, float angle, World world) {
        this.world = world;

        stats = new PlayerStats();

        segmentList = new LinkedList<SnakeSegment>();
        //snake always have at least 3 segments
        segmentList.add(new SnakeSegment(x, y, 0.0f, this, world, segmentTexture));
        segmentList.get(0).body.setTransform(x, y, angle * 0.017f);
        addSegments(2);

        snakeSpeed = 40000.0f;
        rotationSpeed = 8000.0f;

        right = false;
        left = false;

        dead = false;

        setup();
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

        int segmentsDelta = 3 + (int)(stats.getBiomass()/100.0f) - segmentList.size();
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
    public void removeSegments(int count) {
        for(int i = 0; i < count; i++) {
            if(segmentList.size() > 3) {
                segmentList.getLast().dispose();
                segmentList.remove(segmentList.getLast());
            }
        }
    }
    public void resolveCollision(PhysicalObject object, SnakeSegment segment) {
        if(object.getClass() == Fruit.class && segment == getHead()) {
            stats.addBiomass(((Fruit)object).getBiomass());
        }
    }
    public SnakeSegment getSegment(int id) {
        if(id >= segmentList.size()) return segmentList.getLast();
        else                         return segmentList.get(id);
    }
    public SnakeSegment getHead() {
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

    public PlayerStats getStats() {
        return stats;
    }

    public void setStats(PlayerStats stats) {
        this.stats = stats;
    }

    void setSpeed(float speed) {
        snakeSpeed = speed;
    }

    public void resetPlayerPhysics(float x, float y, float angle) {
        segmentList.clear();
        segmentList.add(new SnakeSegment(x, y, 0.0f, this, world, segmentTexture));
        segmentList.get(0).body.setTransform(x, y, angle * 0.017f);
        addSegments(2);
    }
    public void setup() {
        int speedLevel = stats.getSpeedLevel();
        int digestionLevel = stats.getDigestionLevel();
        int poisonLevel = stats.getPoisonLevel();
        int armorLevel = stats.getArmorLevel();

        if(stats.getSpeedLevel() == 1) {
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
        if(poisonLevel == 1) {

        }
        else if(poisonLevel == 2) {

        }
        if(armorLevel == 1) {

        }
        else if(armorLevel == 2)
        {

        }
    }
}
