package com.ghstsch.snakesurvival;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by aaaa on 03.03.2015.
 */
public class UiButton extends UiElement {
    boolean wasPressed;
    static Texture texture_standard = new Texture(Gdx.files.internal("ui/button_1.png"));
    static Texture texture_standard_pressed = new Texture(Gdx.files.internal("ui/button_1_pressed.png"));
    float width;
    float height;
    CharSequence text;
    int type;
    static final int standard = 1;
    Color textColor;
    UiButton(float x, float y, float width, float height, CharSequence text, int type, Color color, Color textColor, BitmapFont font) {
        super(x, y, color, font);
        wasPressed = false;
        this.width = width;
        this.height = height;
        this.text = text;
        this.type = type;
        this.textColor = textColor;
    }

    public  void drawElement(SpriteBatch batch) {
        if(type == standard) {
            if(wasPressed) batch.draw(texture_standard_pressed, x, y, width, height);
            else batch.draw(texture_standard, x, y, width, height);
        }
        font.setScale(1.0f);
        float charSize = font.getCapHeight();
        float charScale = height / charSize / 1.3f;

        font.setScale(charScale, charScale);

        float translateY = (height - font.getCapHeight()) / 2;
        float translateX = (width - (text.length() * font.getCapHeight() * 0.85f)) / 2.0f;

        font.setColor(textColor);
        font.draw(batch, text, x + translateX, y + translateY);
    }
    void press(float x, float y) {
        if((x > realx && x < realx + width) && (y > realy && y < realy + height)) wasPressed = true;
        else wasPressed = false;
    }
    boolean isClicked() {
        if(wasPressed && !Gdx.input.isTouched())
        {
            wasPressed = false;
            return true;
        }
        else return false;
    }
    CharSequence getText() {
        return text;
    }
}