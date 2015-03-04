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
    static final int FIRST_EXIT_ANIMATION = 3;
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
        else if(state == FIRST_EXIT_ANIMATION) {
            x -= realx * dt * firstAnimationTime;
            y -= realy * dt * firstAnimationTime;
            if(x <= 0 || y <= 0) {
                state = BASIC;
                x = 0.0f;
                y = 0.0f;
            }
        }
    }


    public Vector2 getPosition(){
        return new Vector2(x,y);
    }

    void startAnimation(int type) {
        if(type == FIRST_ANIMATION) {
            x = 0.0f;
            y = 0.0f;
        }
        else if(type == FIRST_EXIT_ANIMATION) {
            x = realx;
            y = realy;
        }

        state = type;
    }
    int getState() {
        return state;
    }
    public void draw(SpriteBatch batch) {
        batch.setColor(realcolor);
        drawElement(batch);
    }
    public abstract void drawElement(SpriteBatch batch);
}
