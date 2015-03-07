package com.ghstsch.snakesurvival;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by aaaa on 03.03.2015.
 */
public class UiLabel extends UiElement {
    CharSequence text;
    float size;
    UiLabel(CharSequence text, float size, float x, float y, Color color, BitmapFont font) {
        super(x, y, color, font);
        this.text = text;
        this.size = size;
    }
    public  void drawElement(SpriteBatch batch) {
        font.setColor(realcolor);
        font.setScale(size);
        font.draw(batch, text, x, y);
    }
    void setText(CharSequence text) {
        this.text = text;
    }
}
