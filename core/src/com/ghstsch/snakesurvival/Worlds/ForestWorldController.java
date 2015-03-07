package com.ghstsch.snakesurvival.Worlds;

import com.ghstsch.snakesurvival.Objects.DayFinisher;
import com.ghstsch.snakesurvival.Objects.Fruit;
import com.ghstsch.snakesurvival.Objects.Player;

/**
 * Created by aaaa on 04.03.2015.
 */
public class ForestWorldController extends WorldController {
    public ForestWorldController() {
        super();
        finalDay = 3;
    }

    @Override
    public void update(float dt) {
        world.step(dt, 6, 2);

        removeDeadObjects();
        resolveCollisions();

        for(int i = 0; i < objectList.size(); i++) objectList.get(i).update(dt);
    }

    @Override
    public void generateWorld(int day) {
        disposeObjects();

        this.day = day;
        System.out.println(day);
        player = new Player(400.0f, 400.0f, 0.0f, world);

        objectList.add(player);
        objectList.add(new Fruit(Fruit.APPLE, 100.0f, 100.0f, 30.0f, world));
        objectList.add(new Fruit(Fruit.APPLE, 300.0f, 100.0f, 30.0f, world));
        objectList.add(new Fruit(Fruit.APPLE, 200.0f, 200.0f, 30.0f, world));
        objectList.add(new Fruit(Fruit.APPLE, 100.0f, 200.0f, 30.0f, world));
        objectList.add(new Fruit(Fruit.APPLE, 800.0f, 800.0f, 30.0f, world));
        objectList.add(new Fruit(Fruit.APPLE, 900.0f, 900.0f, 30.0f, world));
        objectList.add(new Fruit(Fruit.APPLE, 800.0f, 200.0f, 30.0f, world));
        if(day == 2) objectList.add(new Fruit(Fruit.APPLE, 900.0f, 900.0f, 30.0f, world));
        objectList.add(new DayFinisher(700.0f, 700.0f, 0.0f, world, this));
        ended = false;
    }

}
