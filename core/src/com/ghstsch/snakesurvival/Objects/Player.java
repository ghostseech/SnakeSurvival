package com.ghstsch.snakesurvival.Objects;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.ghstsch.snakesurvival.PlayerStats;

import java.util.LinkedList;
import java.util.Vector;

/**
 * Created by aaaa on 15.02.2015.
 */
public class Player implements GameObject {
    private static Texture segmentTexture = new Texture("textures/snake_segment.png");;
    public static final float segmentSize = 4.0f;

    private LinkedList<SnakeSegment> segmentList;
    private Vector<Poison> poisonList;

    private World world;


    private int dir;
    private boolean go;

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    private boolean dead;

    private float health;
    private float blockDamage;

    private float snakeSpeed;
    private float rotationSpeed;
    private PlayerStats stats;

    private float maxBiomassCost;

    private float poisonDamage;
    private float poisonSpeed;

    private float fireTime;
    private float fireTimer;

    private float damageTime;
    private float damageTimer;

    public Player(float x, float y, float angle, World world) {
        this.world = world;

        stats = new PlayerStats();

        segmentList = new LinkedList<SnakeSegment>();
        poisonList = new Vector<Poison>();
        //snake always have at least 3 segments/
        segmentList.add(new SnakeSegment(x, y, 0.0f, this, world, segmentTexture));
        segmentList.get(0).body.setTransform(x, y, angle * 0.017f);
        addSegments(2);

        snakeSpeed = 0.6f;
        rotationSpeed = 0.4f;

        right = false;
        left = false;

        dead = false;

        setup();

        fireTime = 1.0f;
        fireTimer = 0.0f;

        damageTime = 0.5f;
        damageTimer = 0.0f;

        dir = 1;
        go = true;
    }

    @Override
    public void draw(SpriteBatch batch) {
        for(int i = 0; i < segmentList.size(); i++) {
            segmentList.get(i).draw(batch);
        }
    }

    @Override
    public void update(float dt) {
        if(dir == 1) {
            getHead().getBody().setTransform(getHead().getPosition(), 0.0f);
        }
        else if(dir == 2) {
            getHead().getBody().setTransform(getHead().getPosition(), 90.0f);
        }
        else if(dir == 3) {
            getHead().getBody().setTransform(getHead().getPosition(), 180.0f);
        }
        else if(dir == 4) {
            getHead().getBody().setTransform(getHead().getPosition(), -90.0f);
        }

        if(go) {
            float impulseDirX = -snakeSpeed * MathUtils.cos(getAngle() + 0.5f * 3.1417f);
            float impulseDirY = -snakeSpeed * MathUtils.sin(getAngle() + 0.5f * 3.1417f);
            getHead().getBody().applyLinearImpulse(impulseDirX, impulseDirY, getPosition().x, getPosition().y, true);
        }

      //  if(right) {
            //getHead().getBody().setTransform(getHead().getBody().getPosition(), -45);
            //float rotationDirX = MathUtils.cos(getAngle()) * rotationSpeed;
            //float rotationDirY = MathUtils.sin(getAngle()) * rotationSpeed;
            //float xoffset = - MathUtils.cos(getAngle()) * segmentSize/2;
            //float yoffset = - MathUtils.sin(getAngle()) * segmentSize/2;
            //getHead().getBody().applyTorque(100.0f * rotationSpeed, true);
           // getHead().getBody().applyLinearImpulse(rotationDirX, rotationDirY, getPosition().x + xoffset, getPosition().y + yoffset, true);
        //}
        //else if(left) {
           // getHead().getBody().setTransform(getHead().getBody().getPosition(), 135.0f);
            //float rotationDirX = -MathUtils.cos(getAngle()) * rotationSpeed;
           // float rotationDirY = -MathUtils.sin(getAngle()) * rotationSpeed;
           // float xoffset = - MathUtils.cos(getAngle()) * segmentSize/2;
           // float yoffset = - MathUtils.sin(getAngle()) * segmentSize/2;
           // getHead().getBody().applyTorque(-100.0f * rotationSpeed, true);
            //getHead().getBody().applyLinearImpulse(rotationDirX, rotationDirY, getPosition().x + xoffset, getPosition().y + yoffset, true);
        //}


        right = false;
        left = false;

        if(fireTimer >= 0.0f) fireTimer -= dt;
        if(damageTimer >= 0.0f) damageTimer -= dt;

        for(int i = 0; i < poisonList.size(); i++) {
            poisonList.get(i).update(dt);
        }

        int segmentsDelta = 3 + (int)(stats.getBiomass()/100.0f) - segmentList.size();
        if(segmentsDelta >= 1) {
            addSegments(segmentsDelta);
        }
        if(segmentsDelta <= -1) {
            removeSegments(segmentsDelta);
        }

        for(int i = 0; i < poisonList.size(); i++)
            if(poisonList.get(i).isDead()) {
                poisonList.get(i).dispose();
                poisonList.remove(i);
                i--;
            }
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public void dispose() {
        for(int i = 0; i < segmentList.size(); i++)segmentList.get(i).dispose();
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public void turnRight() {
        right = true;
        left = false;
    }
    public void turnLeft() {
        right = false;
        left = true;
    }

    public void fire() {
        if(fireTimer <= 0.0f && stats.getPoisonLevel() >= 1) {
            float xcoord = getPosition().x - MathUtils.cos(getAngle() + 0.5f * 3.1417f) * 7.0f;
            float ycoord = getPosition().y - MathUtils.sin(getAngle() + 0.5f * 3.1417f) * 7.0f;

            float dirX = -MathUtils.cos(getAngle() + 0.5f * 3.1417f);
            float dirY = -MathUtils.sin(getAngle() + 0.5f * 3.1417f);

            poisonList.add(new Poison(poisonDamage, dirX, dirY, poisonSpeed, xcoord, ycoord, 0.0f, world));
            fireTimer = fireTime;
        }
    }

    public Vector<Poison> getPoisonList() {
        return poisonList;
    }

    public void addSegments(int count) {
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
        if(object.getClass() == Stone.class) {
            damage(20.0f);
        }
    }

    public void damage(float val) {
        if(damageTimer <= 0.0f) {
            damageTimer = damageTime;
            health -= (val - blockDamage);
        }
    }

    public float getHealth() {
        return health;
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

    public PlayerStats getStats() {
        return stats;
    }

    public void setStats(PlayerStats stats) {
        this.stats.setArmorLevel(stats.getArmorLevel());
        this.stats.setPoisonLevel(stats.getPoisonLevel());
        this.stats.setDigestionLevel(stats.getDigestionLevel());
        this.stats.setSpeedLevel(stats.getSpeedLevel());
        this.stats.setBiomass(stats.getBiomass());
        setup();
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

        health = 100.0f;
        blockDamage = 0.0f;

        if(stats.getSpeedLevel() == 1) {
            rotationSpeed = 0.5f;
            snakeSpeed = 0.6f;
        }
        else if(speedLevel == 2) {
            rotationSpeed = 0.6f;
            snakeSpeed = 0.8f;
        }
        if(digestionLevel == 1) {
            maxBiomassCost = 10.0f;
        }
        else if(digestionLevel == 2) {
            maxBiomassCost = 20.0f;
        }
        if(poisonLevel == 1) {
            poisonDamage = 30.0f;
            poisonSpeed = 0.3f;
        }
        else if(poisonLevel == 2) {
            poisonDamage = 30.0f;
            poisonSpeed = 0.3f;
        }
        if(armorLevel == 1) {
            health = 120.0f;
            blockDamage = 5.0f;
        }
        else if(armorLevel == 2)
        {
            health = 140.0f;
            blockDamage = 10.0f;
        }
    }
}
