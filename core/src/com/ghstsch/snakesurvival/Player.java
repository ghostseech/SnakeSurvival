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

    Player(float x, float y, float angle, World world) {
        this.world = world;

        segmentList = new LinkedList<SnakeSegment>();
        //snake always have at least 3 segments
        segmentList.add(new SnakeSegment(x, y, 0.0f, world, segmentTexture));
        segmentList.get(0).body.setTransform(x, y, angle * 0.017f);
        addSegments(9);
    }

    public void draw(SpriteBatch batch) {
        for(int i = 0; i < segmentList.size(); i++) {
            segmentList.get(i).draw(batch);
        }
    }
    public void update(float dt) {

    }
    void addSegments(int count) {
        for(int i = 0; i < count; i++) {
            //calculate rotation and position of new segment
            float newrotate = segmentList.getLast().getAngle();
            float newx = segmentList.getLast().getPosition().x + MathUtils.cos(newrotate + 0.5f * 3.1417f) * segmentSize;
            float newy = segmentList.getLast().getPosition().y + MathUtils.sin(newrotate + 0.5f * 3.1417f) * segmentSize;

            segmentList.addLast(new SnakeSegment(newx, newy, newrotate, world, segmentTexture));
            //join new segment with next
            segmentList.getLast().joinToNext(segmentList.get((segmentList.size()-1) - 1));
        }
    }
    void removeSegments(int count) {

    }
    SnakeSegment getSegment(int id) {
        if(id >= segmentList.size()) return segmentList.getLast();
        else                         return segmentList.get(id);
    }
    SnakeSegment getHead() {
        return segmentList.getFirst();
    }
    //return position of head
    public Vector2 getPosition() {
        return segmentList.get(0).getPosition();
    }
    //return rotation of head
    public float getAngle() {
        return segmentList.get(0).getAngle();
    }
}
