package com.ghstsch.snakesurvival.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ghstsch.snakesurvival.PlayerStats;
import com.ghstsch.snakesurvival.ResourseManager;
import com.ghstsch.snakesurvival.Ui.UiButton;
import com.ghstsch.snakesurvival.Ui.UiElement;
import com.ghstsch.snakesurvival.Ui.UiLabel;
import com.ghstsch.snakesurvival.Ui.UiProcessor;

import java.util.Vector;

/**
 * Created by aaaa on 07.03.2015.
 */
public class UpdateScreen extends Screen {
    private PlayerStats stats;
    private UiProcessor uiProcessor;
    private Vector<UiElement> ui;
    private SpriteBatch batch;
    private OrthographicCamera cam;

    private UiLabel biomassIndicator;

    private UiLabel speed;
    private UiLabel armor;
    private UiLabel poison;
    private UiLabel digestion;

    private UiButton speedUp;
    private UiButton armorUp;
    private UiButton poisonUp;
    private UiButton digestionUp;

    private UiButton nextDay;

    public UpdateScreen(ScreenManager screenManager, ResourseManager resourseManager) {
        super(screenManager, resourseManager);
    }

    @Override
    public void draw() {
        cam.update();
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
        batch.begin();
        uiProcessor.draw(batch);
        batch.end();
    }

    @Override
    public void init() {
        stats = resourseManager.getPlayerStats();

        cam = new OrthographicCamera();
        cam.setToOrtho(true, 1920, 1080);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(cam.combined);

        ui = new Vector<UiElement>();

        uiProcessor = new UiProcessor();
        uiProcessor.setUi(ui);

        speed     = new UiLabel("SPEED    :", 0.5f,
                200.0f, 200.0f,
                resourseManager.getUiTextColor(),
                resourseManager.getFont(0));

        armor     = new UiLabel("ARMOR    :", 0.5f,
                200.0f, 400.0f,
                resourseManager.getUiTextColor(),
                resourseManager.getFont(0));

        poison    = new UiLabel("POSION   :", 0.5f,
                200.0f, 600.0f,
                resourseManager.getUiTextColor(),
                resourseManager.getFont(0));

        digestion = new UiLabel("DIGESTION:", 0.5f,
                200.0f, 800.0f,
                resourseManager.getUiTextColor(),
                resourseManager.getFont(0));

        speedUp = new UiButton(
                630.0f, 150.0f,
                200.0f, 120.0f,
                "BUY", UiButton.standard,
                resourseManager.getUiColor(), resourseManager.getUiTextColor(),
                resourseManager.getFont(0));

        armorUp = new UiButton(
                630.0f, 350.0f,
                200.0f, 120.0f,
                "BUY", UiButton.standard,
                resourseManager.getUiColor(), resourseManager.getUiTextColor(),
                resourseManager.getFont(0));

        poisonUp = new UiButton(
                630.0f, 550.0f,
                200.0f, 120.0f,
                "BUY", UiButton.standard,
                resourseManager.getUiColor(), resourseManager.getUiTextColor(),
                resourseManager.getFont(0));

        digestionUp = new UiButton(
                630.0f, 750.0f,
                200.0f, 120.0f,
                "BUY", UiButton.standard,
                resourseManager.getUiColor(), resourseManager.getUiTextColor(),
                resourseManager.getFont(0));

        nextDay = new UiButton(
                1600.0f, 480.0f,
                240.0f, 120.0f,
                ">", UiButton.standard,
                resourseManager.getUiColor(), resourseManager.getUiTextColor(),
                resourseManager.getFont(0));

        biomassIndicator = new UiLabel("BIOMASS:", 0.3f,
                30.0f, 30.0f,
                resourseManager.getUiTextColor(),
                resourseManager.getFont(0));

        ui.add(speed);
        ui.add(armor);
        ui.add(poison);
        ui.add(digestion);
        ui.add(speedUp);
        ui.add(armorUp);
        ui.add(poisonUp);
        ui.add(digestionUp);
        ui.add(nextDay);
        ui.add(biomassIndicator);

    }

    @Override
    public void handleInput() {
        uiProcessor.update();
    }

    @Override
    public void update(float dt) {
        handleInput();

        speed.setText    ("SPEED    :" + stats.getSpeedLevel());
        digestion.setText("DIGESTION:" + stats.getDigestionLevel());
        armor.setText    ("ARMOR    :" + stats.getArmorLevel());
        poison.setText   ("POISON   :" + stats.getPoisonLevel());

        biomassIndicator.setText("SPEED    :" + stats.getBiomass());

        Vector<UiButton> clickedList = uiProcessor.getClickedButtonList();
        for(int i = 0; i < clickedList.size(); i++) {
            if(clickedList.get(i) == speedUp) {
                if(stats.getBiomass() >= stats.getStatCost(stats.getSpeedLevel())) {
                    stats.addBiomass(-stats.getStatCost(stats.getSpeedLevel()));
                    stats.addSpeedLevel(1);
                }
            }
            else if(clickedList.get(i) == armorUp) {
                if(stats.getBiomass() >= stats.getStatCost(stats.getArmorLevel())) {
                    stats.addBiomass(-stats.getStatCost(stats.getArmorLevel()));
                    stats.addArmorLevel(1);
                }
            }
            else if(clickedList.get(i) == poisonUp) {
                if(stats.getBiomass() >= stats.getStatCost(stats.getPoisonLevel())) {
                    stats.addBiomass(-stats.getStatCost(stats.getPoisonLevel()));
                    stats.addPoisonLevel(1);
                }
            }
            else if(clickedList.get(i) == digestionUp) {
                if(stats.getBiomass() >= stats.getStatCost(stats.getDigestionLevel())) {
                    stats.addBiomass(-stats.getStatCost(stats.getDigestionLevel()));
                    stats.addDigestionLevel(1);
                }
            }
            else if(clickedList.get(i) == nextDay) {
                screenManager.setScreen(ScreenManager.GAME_SCREEN, true);
                resourseManager.getCurrentController().generateWorld(resourseManager.getCurrentController().getCurrentDay()+1);
                resourseManager.setPlayerStats(stats);
                resourseManager.getCurrentController().setPlayerStats(stats);
            }
        }
    }

    @Override
    public void dispose() {

    }
}
