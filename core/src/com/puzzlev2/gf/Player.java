package com.puzzlev2.gf;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

public class Player extends Actor {
    private Garden garden;
    private Factory factory;
    private Stage sgarden;
    private Stage sfactory;
    public boolean isGarden;


    public int openedColors;
    public int happiness;

    public Player(float width, float height) {
        this.openedColors = 3;
        this.happiness = 50;
        isGarden = false;
        sgarden = new Stage();
        sfactory = new Stage();
        garden = new Garden(this, width, height);
        factory = new Factory(this, width, height);
        sgarden.addActor(garden);
        sfactory.addActor(factory);
        garden.setGroups(sgarden);
        factory.setGroups(sfactory);
    }

    @Override
    public void act(float delta) {
        if (isGarden) sgarden.act();
        else sfactory.act();
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if (isGarden) sgarden.draw();
        else sfactory.draw();
    }

    public void setCoords(ArrayList<Coordinate> coords) {
        if (isGarden) factory.workers.get(0).coords = coords;//sgarden.draw();
        else factory.workers.get(0).coords = coords;
    }

    public void gardenStep() {
        garden.updateState();
    }

    public void factoryStep() {
        factory.updateState();
    }
}
