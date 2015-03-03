package com.ghstsch.snakesurvival;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by aaaa on 03.03.2015.
 */
public abstract class UiElement {
    protected float x;
    protected float y;
    static final float firstAnimationTime = 2.0f;

    static final int BASIC = 1;
    static final int FIRST_ANIMATION = 2;
    int state;
    BitmapFont font;
    //animation
    float realx;
    float realy;
    Color realcolor;

    UiElement(float x, float y, Color color, BitmapFont font) {
        realx = x;
        realy = y;
        realcolor = color;
        this.x = realx;
        this.y = realy;
        this.font = font;
        state = BASIC;
    }

    public void update(float dt) {
        if(state == BASIC) {

        }
        else if(state == FIRST_ANIMATION) {
            x += realx * dt * firstAnimationTime;
            y += realy * dt * firstAnimationTime;
            if(x >= realx || y >= realy) {
                state = BASIC;
                x = realx;
                y = realy;
            }
        }
    }


    public Vector2 getPosition(){
        return new Vector2(x,y);
    }

    void startAnimation(int type) {
        x = 0.0f;
        y = 0.0f;
        state = type;
    }

    public void draw(SpriteBatch batch) {
        batch.setColor(realcolor);
        drawElement(batch);
    }
    public abstract void drawElement(SpriteBatch batch);
}
