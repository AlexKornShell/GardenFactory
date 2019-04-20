package com.puzzlev2.gf;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Factory extends Actor {
    private ArrayList<Base> bases;
    private ArrayList<Box> boxes;
    private ArrayList<Worker> workers;

    private Group gbases;
    private Group gboxes;
    private Group gworkers;

    public Factory(Stage stage) {
        bases = new ArrayList<Base>();
        boxes = new ArrayList<Box>();
        workers = new ArrayList<Worker>();

        for (Base base : bases) {
            gbases.addActor(base);
        }
        for (Box box : boxes) {
            gboxes.addActor(box);
        }
        for (Worker worker : workers) {
            gworkers.addActor(worker);
        }

        stage.addActor(gbases);
        stage.addActor(gboxes);
        stage.addActor(gworkers);
    }

    @Override
    public void act (float delta) {

    }

    @Override
    public void draw(Batch batch, float alpha) {

    }

    public void generate() {
        Random random = new Random();
        if(random.nextInt(10000) == 1) {
            Base b = new Base(1, 1, 1, 1);
            gbases.addActor(b);
            bases.add(b);
        }
    }
}
