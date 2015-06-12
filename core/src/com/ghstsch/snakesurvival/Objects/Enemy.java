package com.ghstsch.snakesurvival.Objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Vector;

/**
 * Created by aaaa on 09.03.2015.
 */
public abstract class Enemy extends PhysicalObject {
    public static final int STAND = 1;
    public static final int GO = 2;
    public static final int TURN_RIGHT = 3;
    public static final int TURN_LEFT = 4;
    public static final int ATTACK_PLAYER = 5;

    protected float speed;

    protected Vector2 point;
    protected int state;
    protected boolean dead;
    protected Player player;
    protected float tmpangle;

    public Enemy(float x, float y, float angle, World world, Player player) {
        super(x, y, angle, world);
        createShape(x, y, angle);

        this.player = player;
        dead = false;
        state = 0;

    }

    public void attack(Player object, float damage) {
        object.damage(damage);
    }

    @Override
    public void update(float dt) {
        behavoir();
        //System.out.println(getAngle());
        //float a = (getPosition().x - player.getPosition().x) * (getPosition().y - player.getPosition().y);
        //double distance = Math.sqrt(((double)(a)));
        //lookAt(player.getPosition(), dt);

    }

    public void goTo(float x, float y) {
        lookAt(new Vector2(x, y));
        body.setTransform(body.getPosition(), tmpangle);

        float dirx = MathUtils.cos(getAngle());
        float diry = MathUtils.sin(getAngle());

        body.setLinearVelocity(dirx * speed, diry * speed);

    }

    public void lookAt(Vector2 point) {

        double vectorX = ((double)point.x - getPosition().x);
        double vectorY = ((double)point.y - getPosition().y);
        double dist = Math.sqrt(vectorX * vectorX + vectorY * vectorY);

        vectorX = vectorX / dist;
        vectorY = vectorY / dist;

        double nullVectorX = 1.0;
        double nullVectorY = 0.0;

        tmpangle = (float) Math.acos(vectorX * nullVectorX + vectorY * nullVectorY);
        if(vectorY < 0) tmpangle = 2.0f * 3.1417f - tmpangle;
    }

    @Override
    public boolean isDead() {
        return dead;
    }
    public abstract void behavoir();

}
