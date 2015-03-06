package com.ghstsch.snakesurvival;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.Vector;

/**
 * Created by aaaa on 04.03.2015.
 */
public class ForestWorldController extends WorldController {
    ForestWorldController(World world) {
        super(world);
        finalDay = 3;
        fruits = new Vector<Fruit>();
    }
    Vector<Fruit> fruits;
    public Player getPlayer() {
        return player;
    }

    public void update(float dt) {
        for(int i = 0; i < objectList.size(); i++) {
            if(objectList.get(i).isDead()) {
                objectList.get(i).dispose();
                objectList.remove(i);
                i--;
            }
        }

        Array<Contact> contactArray = world.getContactList();
        for(int i = 0; i < world.getContactList().size; i++) {
            PhysicalObject objectA = (PhysicalObject)contactArray.get(i).getFixtureA().getBody().getUserData();
            PhysicalObject objectB = (PhysicalObject)contactArray.get(i).getFixtureB().getBody().getUserData();
            Filter f1 = contactArray.get(i).getFixtureA().getFilterData();
            Filter f2 = contactArray.get(i).getFixtureB().getFilterData();
            objectA.resolveCollision(f2);
            objectB.resolveCollision(f1);
        }
        for(int i = 0; i < objectList.size(); i++) objectList.get(i).update(dt);
    }
    public void generateWorld(int day) {
        this.day = day;
        player = new Player(400.0f, 400.0f, 0.0f, world);
        objectList.add(player);
        fruits.add(new Fruit(Fruit.APPLE, 100.0f, 100.0f, 30.0f, world));
        fruits.add(new Fruit(Fruit.APPLE, 300.0f, 100.0f, 30.0f, world));
        fruits.add(new Fruit(Fruit.APPLE, 200.0f, 200.0f, 30.0f, world));
        objectList.add(fruits.get(0));
        objectList.add(fruits.get(1));
        objectList.add(fruits.get(2));
        objectList.add(new DayFinisher(700.0f, 700.0f, 0.0f, world, this));
    }
    public void addObject() {

    }

}
