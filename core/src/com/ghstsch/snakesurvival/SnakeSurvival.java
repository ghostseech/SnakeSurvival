package com.ghstsch.snakesurvival;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class SnakeSurvival extends ApplicationAdapter {

	BitmapFont font;
	ScreenManager screenManager;
	@Override
	public void create () {
		font = new BitmapFont(Gdx.files.internal("fonts/font_8.fnt"), Gdx.files.internal("fonts/font_8_0.png"), true);
		screenManager = new ScreenManager();
		screenManager.setScreen(new MenuScreen(), font);
	}

	@Override
	public void render () {
		InputHandler.update();
		screenManager.update(Gdx.graphics.getDeltaTime());
		screenManager.draw();
	}
}
