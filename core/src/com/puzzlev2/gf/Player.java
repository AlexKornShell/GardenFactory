package com.puzzlev2.gf;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Player extends Actor {
    private Garden garden;
    private Factory factory;
    private Stage sgarden;
    private Stage sfactory;
    private boolean isGarden;

    public Player(Stage stage) {
        isGarden = true;
        sgarden = new Stage();
        sfactory = new Stage();
        garden = new Garden(this);
        factory = new Factory();
        sgarden.addActor(garden);
        sfactory.addActor(factory);
        garden.setGroups(sgarden);
        factory.setGroups(sfactory);
    }

    @Override
    public void act (float delta) {
        if(isGarden) sgarden.act();
        else sfactory.act();
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if(isGarden) sgarden.draw();
        else sfactory.draw();
    }
}