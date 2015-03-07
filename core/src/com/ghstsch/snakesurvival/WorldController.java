package com.ghstsch.snakesurvival;

import com.badlogic.gdx.physics.box2d.World;

import java.util.Vector;

/**
 * Created by aaaa on 04.03.2015.
 */
public abstract class WorldController {
    World world;
    Vector<GameObject> objectList;
    Player player;
    boolean ended;
    int day;
    int finalDay;

    WorldController(World world) {
        this.world = world;
        ended = false;
        objectList = new Vector<GameObject>();
    }
    public Player getPlayer() {
        return player;
    }
    public Vector<GameObject> getObjectList() {
        return objectList;
    }
    public abstract void update(float dt);
    //public abstract void processCollision(PhysicalObject a, PhysicalObject b);
    public abstract void generateWorld(int day);
    public int getFinalDay() {
        return finalDay;
    }
    public int getCurrentDay() {
        return day;
    }
    boolean isGameEnded() {
        return ended;
    }
    void endGame() {
        ended = true;
    }
    void continueGame() {
        ended = false;
    }
}
