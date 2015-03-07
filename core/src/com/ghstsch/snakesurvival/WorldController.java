package com.ghstsch.snakesurvival;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.Vector;

/**
 * Created by aaaa on 04.03.2015.
 */
public abstract class WorldController {
    protected World world;
    protected Vector<GameObject> objectList;
    protected Player player;
    protected boolean ended;
    protected int day;
    protected int finalDay;

    WorldController() {
        ended = false;

        world = new World(new Vector2(0, 0), true);
        objectList = new Vector<GameObject>();
        generateWorld(1);
    }

    public abstract void update(float dt);

    public abstract void generateWorld(int day);

    protected void resolveCollisions() {
        Array<Contact> contactArray = world.getContactList();
        for(int i = 0; i < world.getContactList().size; i++) {
            PhysicalObject objectA = (PhysicalObject)contactArray.get(i).getFixtureA().getBody().getUserData();
            PhysicalObject objectB = (PhysicalObject)contactArray.get(i).getFixtureB().getBody().getUserData();

            objectA.resolveCollision(objectB);
            objectB.resolveCollision(objectA);
        }
    }

    protected void removeDeadObjects() {
        for(int i = 0; i < objectList.size(); i++) {
            if(objectList.get(i).isDead()) {
                objectList.get(i).dispose();
                objectList.remove(i);
                i--;
            }
        }
    }

    public void setPlayerStats(PlayerStats stats) {
        player.setStats(stats);
    }

    public World getWorld() {
        return world;
    }

    public Player getPlayer() {
        return player;
    }

    public Vector<GameObject> getObjectList() {
        return objectList;
    }

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
    void disposeObjects() {
        for(int i = 0; i < objectList.size(); i++)objectList.get(i).dispose();
        objectList.clear();
    }
}
