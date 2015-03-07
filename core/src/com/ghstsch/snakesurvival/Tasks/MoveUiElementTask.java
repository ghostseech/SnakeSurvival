package com.ghstsch.snakesurvival.Tasks;

import com.badlogic.gdx.utils.Timer;
import com.ghstsch.snakesurvival.Ui.UiElement;

/**
 * Created by aaaa on 07.03.2015.
 */
public class MoveUiElementTask extends Timer.Task {
    UiElement element;
    MoveUiElementTask(UiElement element, float x, float y) {
        this.element = element;
    }
    public void run() {

    }
}
