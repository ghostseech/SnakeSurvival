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

    static Texture segmentTexture;
    static {
        segmentTexture = new Texture("textures/snake_segment.png");
    }

    LinkedList<SnakeSegment> segmentList;
    float x;
    float y;
    World world;
    Player(float x, float y, World world) {
        this.x = x;
        this.y = y;
        this.world = world;

        segmentList = new LinkedList<SnakeSegment>();
        segmentList.add(new SnakeSegment(x, y, 0.0f, world, segmentTexture));
        segmentList.get(0).body.setTransform(x, y, 45.0f * 0.017f);
        addSegments(12);
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
            float newrotate = segmentList.getLast().getAngle();
            float newx = segmentList.getLast().getPosition().x + MathUtils.cos(newrotate + 0.5f * 3.1417f) * segmentSize;
            float newy = segmentList.getLast().getPosition().y + MathUtils.sin(newrotate + 0.5f * 3.1417f) * segmentSize;

            segmentList.addLast(new SnakeSegment(newx, newy, newrotate, world, segmentTexture));
            segmentList.getLast().joinToNext(segmentList.get(segmentList.size()-1 - 1));
            //segmentList.get(segmentList.size() - 1 - 1).joinToNext(segmentList.getLast());
        }
    }
    void removeSegments(int count) {

    }
    SnakeSegment getSegment(int id) {
        return segmentList.get(id);
    }
    public Vector2 getPosition() {
        return segmentList.get(0).getPosition();
    }
    public float getAngle() {
        return segmentList.get(0).getAngle();
    }
}
