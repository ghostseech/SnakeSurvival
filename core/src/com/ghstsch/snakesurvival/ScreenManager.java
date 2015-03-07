package com.ghstsch.snakesurvival;

/**
 * Created by aaaa on 03.03.2015.
 */
public class ScreenManager {
    public static final int GAME_SCREEN = 1;
    public static final int MENU_SCREEN = 2;
    public static final int UPDATE_SCREEN = 3;

    ResourseManager resourseManager;

    Screen gameScreen;
    Screen menuScreen;
    Screen updateScreen;

    Screen currentScreen;
    void setScreen(int screen, boolean recreate) {
        if(screen == GAME_SCREEN) {
            if(recreate || gameScreen == null) {
                gameScreen = new GameScreen(this, resourseManager);
                gameScreen.init();
            }
            currentScreen = gameScreen;
        }
        else if(screen == MENU_SCREEN) {
            if(recreate || menuScreen == null) {
                menuScreen = new MenuScreen(this, resourseManager);
                menuScreen.init();
            }
            currentScreen = menuScreen;
        }
        else if(screen == UPDATE_SCREEN) {
            if(recreate || updateScreen == null) {
                updateScreen = new UpdateScreen(this, resourseManager);
                updateScreen.init();
            }
            currentScreen = updateScreen;
        }
    }
    void setResourse(ResourseManager resourseManager) {
        this.resourseManager = resourseManager;
    }
    void draw() {
        currentScreen.draw();
    }
    void update(float dt) {
        currentScreen.update(dt);
    }
}
