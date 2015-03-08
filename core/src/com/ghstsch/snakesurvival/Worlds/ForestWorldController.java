package com.ghstsch.snakesurvival.Worlds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.utils.Array;
import com.ghstsch.snakesurvival.Objects.*;

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

       // Array<Body> bodies = new Array<Body>();
        //world.getBodies(bodies);
       // for(int i = 0; i < world.getBodyCount(); i++) {
       //     bodies.get(i).getFixtureList().get(0).getShape().
      //  }

        world.step(dt, 1, 1);

        removeDeadObjects();
        resolveCollisions();

        for(int i = 0; i < objectList.size(); i++) objectList.get(i).update(dt);
    }

    private void generateWroldBorder() {
        for(int i = 0; i < 100; i++) {
            float x = MathUtils.cos(360.0f / 100.0f * i * 0.017f) * 250.0f;
            float y = MathUtils.sin(360.0f / 100.0f * i * 0.017f) * 250.0f;
            objectList.add(new Stone(x, y, 0, world, 30.0f, 50.0f));
        }
    }

    private void generateApples(int count) {
        for(int i = 0; i < count; i++) {
            float offset = MathUtils.random(0.0f, 250.0f);

            float x = MathUtils.cos(MathUtils.random(0.0f, 3.1417f * 2)) * offset;
            float y = MathUtils.sin(MathUtils.random(0.0f, 3.1417f * 2)) * offset;

            objectList.add(new Fruit(Fruit.APPLE, x, y, 0, world));
        }
    }

    private void generateBananas(int count) {
        for(int i = 0; i < count; i++) {
            float offset = MathUtils.random(0.0f, 250.0f);

            float x = MathUtils.cos(MathUtils.random(0.0f, 3.1417f * 2)) * offset;
            float y = MathUtils.sin(MathUtils.random(0.0f, 3.1417f * 2)) * offset;

            objectList.add(new Fruit(Fruit.BANANA, x, y, 0, world));
        }
    }

    private void generateOranges(int count) {
       for(int i = 0; i < count; i++) {
            float offset = MathUtils.random(0.0f, 250.0f);

            float x = MathUtils.cos(MathUtils.random(0.0f, 3.1417f * 2)) * offset;
            float y = MathUtils.sin(MathUtils.random(0.0f, 3.1417f * 2)) * offset;

            objectList.add(new Fruit(Fruit.ORANGE, x, y, 0, world));
        }
    }

    private void generateForest() {
        for(float i = -250.0f; i < 250.0f; i+= MathUtils.random(15.0f, 90.0f)) {
            for(float j = -250.0f; j < 250.0f; j+= MathUtils.random(15.0f, 90.0f)) {
                objectList.add(new Tree(i, j, 0, MathUtils.random(3.0f, 5.0f), world));
            }
        }
    }

    @Override
    public void generateWorld(int day) {
        disposeObjects();

        this.day = day;
        System.out.println(day);
        player = new Player(0.0f, 0.0f, 0.0f, world);

        //objectList.add(new Ground(0.0f, 0.0f, 0.0f, world, 2500.0f, 2500.0f));
        //objectList.add(new Ground(5000.0f,5000.0f));
        objectList.add(player);
        //objectList.add(new Fruit(Fruit.APPLE, 3.0f, 3.0f, 0.0f, world));
      //  objectList.add(new Fruit(Fruit.APPLE, 15.0f, 15.0f, 30.0f, world));
        /*objectList.add(new Fruit(Fruit.APPLE, 300.0f, 100.0f, 30.0f, world));
        objectList.add(new Fruit(Fruit.APPLE, 200.0f, 200.0f, 30.0f, world));
        objectList.add(new Fruit(Fruit.APPLE, 100.0f, 200.0f, 30.0f, world));
        objectList.add(new Fruit(Fruit.APPLE, 800.0f, 800.0f, 30.0f, world));
        objectList.add(new Fruit(Fruit.APPLE, 900.0f, 900.0f, 30.0f, world));
        objectList.add(new Fruit(Fruit.APPLE, 800.0f, 200.0f, 30.0f, world));
        objectList.add(new Stone(600.0f, 600.0f, 0.0f, world, 60.0f, 80.0f));
        objectList.add(new Stone(1000.0f, 1000.0f, 0.0f, world, 60.0f, 80.0f));
        objectList.add(new Stone(1000.0f, 600.0f, 0.0f, world, 60.0f, 80.0f));*/
        generateWroldBorder();
        generateForest();
        if(day == 1) {
            generateApples(40);
        }
        else if(day == 2) {
            generateApples(20);
            generateBananas(20);
        }

        objectList.add(new DayFinisher(7.0f, 7.0f, 0.0f, world, this));
        ended = false;
    }

}
