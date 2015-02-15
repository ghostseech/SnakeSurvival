package com.ghstsch.snakesurvival;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class SnakeSurvival extends ApplicationAdapter {

	Screen currentScreen;

	@Override
	public void create () {
		currentScreen = new GameScreen();
		currentScreen.init();
	}

	@Override
	public void render () {
		InputHandler.update();
		currentScreen.update(Gdx.graphics.getDeltaTime());
		currentScreen.draw();
	}
}
