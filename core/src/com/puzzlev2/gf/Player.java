package com.puzzlev2.gf;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Player extends Actor {
    private Garden garden;
    private Factory factory;

    public Player(Stage stage) {
        garden = new Garden(stage);
        factory = new Factory(stage);
        stage.addActor(garden);
        stage.addActor(factory);
    }

    @Override
    public void act (float delta) {

    }

    @Override
    public void draw(Batch batch, float alpha) {

    }
}
