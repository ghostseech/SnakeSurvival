package com.ghstsch.snakesurvival;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

/**
 * Created by aaaa on 15.02.2015.
 */

public class MenuScreen implements Screen {
    Stage stage;
    Table table;
    TextButton exit;
    TextButton startGame;
    Label head;
    Skin skin;
    BitmapFont font;
    TextureAtlas atlas;

    public void init() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
        stage = new Stage();
        table = new Table();

        stage.addActor(table);
        skin = new Skin(atlas);

        exit = new TextButton("Exit", skin, "default");
        exit.setWidth(200.0f);
        exit.setHeight(100.0f);
        table.add(exit);

    }
    public void draw() {
        stage.draw();
    }
    public void update(float dt) {
        stage.act(Gdx.graphics.getDeltaTime());
    }
    public void handleInput() {

    }
}
