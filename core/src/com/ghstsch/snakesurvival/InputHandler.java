package com.ghstsch.snakesurvival;

/**
 * Created by aaaa on 15.02.2015.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputHandler {
    private static final int MAX_BUTTONS = 6;

    public static final int W = 0;
    public static final int S = 1;
    public static final int A = 2;
    public static final int D = 3;
    public static final int SPACE = 4;
    public static final int MOUSE = 5;

    private static int mousePosX;
    private static int mousePosY;

    static boolean keys[];
    static boolean prevKeys[];
    static {
        keys = new boolean[MAX_BUTTONS];
        prevKeys = new boolean[MAX_BUTTONS];
    }

    public static void update() {
        for(int i = 0; i < MAX_BUTTONS; i++) prevKeys[i] = keys[i];

        if (Gdx.input.isKeyPressed(Input.Keys.A)) keys[A] = true;
        else keys[A] = false;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) keys[D] = true;
        else keys[D] = false;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) keys[W] = true;
        else keys[W] = false;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) keys[S] = true;
        else keys[S] = false;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) keys[SPACE] = true;
        else keys[SPACE] = false;
        if (Gdx.input.isTouched()) keys[MOUSE] = true;
        else keys[MOUSE] = false;
    }

    public static boolean isKeyPressed(int key) {
        if(key > MAX_BUTTONS) return false;
        return keys[key] && prevKeys[key];
    }
    public static boolean isKeyDown(int key) {
        if(key > MAX_BUTTONS) return false;
        return keys[key];
    }
    public static int getMouseX() {
        return mousePosX;
    }
    public static int getMousePosY() {
        return mousePosY;
    }
}
