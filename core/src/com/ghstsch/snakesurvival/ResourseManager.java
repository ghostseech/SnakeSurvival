package com.ghstsch.snakesurvival;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.ghstsch.snakesurvival.Worlds.WorldController;

import java.util.Vector;

/**
 * Created by aaaa on 07.03.2015.
 */
public class ResourseManager {
    private Vector<BitmapFont> fonts;
    private Color uiColor;
    private Color uiTextColor;
    private Color uiLabelColor;
    private WorldController currentController;
    private PlayerStats playerStats;

    public void init() {
        fonts = new Vector<BitmapFont>();
        uiColor = new Color(0.1f, 0.6f, 0.8f, 1.0f);
        uiTextColor = new Color(1.0f, 0.0f, 0.0f, 1.0f);
        uiLabelColor = new Color(1.0f, 0.0f, 0.0f, 1.0f);
        fonts.add(new BitmapFont(Gdx.files.internal("fonts/font_8.fnt"), Gdx.files.internal("fonts/font_8_0.png"), true, true));

        playerStats = new PlayerStats();
    }

    public void setWorldController(WorldController controller) {
        currentController = controller;
    }
    public PlayerStats getPlayerStats() {
        return playerStats;
    }
    public void setPlayerStats(PlayerStats stats) {
        playerStats.setArmorLevel(stats.getArmorLevel());
        playerStats.setPoisonLevel(stats.getPoisonLevel());
        playerStats.setDigestionLevel(stats.getDigestionLevel());
        playerStats.setSpeedLevel(stats.getSpeedLevel());
        playerStats.setBiomass(stats.getBiomass());
    }
    public WorldController getCurrentController() {
        return currentController;
    }

    public Color getUiColor() {
        return uiColor;
    }

    public Color getUiTextColor() {
        return uiTextColor;
    }

    public Color getUiLabelColor() {
        return uiLabelColor;
    }
    public BitmapFont getFont(int id) {
        return fonts.get(id);
    }
}
