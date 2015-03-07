package com.ghstsch.snakesurvival;

/**
 * Created by aaaa on 07.03.2015.
 */
public class PlayerStats {
    public static final int MISSING = 0;

    int speedLevel;
    int digestionLevel;
    int poisonLevel;
    int armorLevel;

    float biomass;

    public PlayerStats() {
        speedLevel = 1;
        digestionLevel = 1;
        poisonLevel = 0;
        armorLevel = 0;
        biomass = 0.0f;
    }

    public float getStatCost(int level) {
        float cost = level * 100.0f;
        if(level == 0) cost = 400.0f;
        return cost;
    }

    public void setSpeedLevel(int val) {
        speedLevel = val;
    }
    public void setDigestionLevel(int val) {
        digestionLevel = val;
    }
    public void setPoisonLevel(int val) {
        poisonLevel = val;
    }
    public void setArmorLevel(int val) {
        armorLevel = val;
    }
    public void setBiomass(float val) {
        biomass = val;
    }
    public void addSpeedLevel(int val) {
        speedLevel += val;
    }
    public void addDigestionLevel(int val) {
        digestionLevel += val;
    }
    public void addPoisonLevel(int val) {
        poisonLevel += val;
    }
    public void addArmorLevel(int val) {
        armorLevel += val;
    }
    public void addBiomass(float val) {
        biomass += val;
    }
    public int getSpeedLevel() {
        return speedLevel;
    }
    public int getDigestionLevel() {
        return digestionLevel;
    }
    public int getPoisonLevel() {
     return poisonLevel;
    }
    public int getArmorLevel() {
        return armorLevel;
    }
    public float getBiomass() {
        return biomass;
    }
}
