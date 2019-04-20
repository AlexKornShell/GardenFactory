package com.puzzlev2.gf;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

public class Garden extends Actor {
    private ArrayList<Base> hives;
    private ArrayList<Box> flowers;
    private ArrayList<Worker> bees;

    private Group ghives;
    private Group gflowers;
    private Group gbees;

    public Garden(Stage stage) {
        hives = new ArrayList<Base>();
        flowers = new ArrayList<Box>();
        bees = new ArrayList<Worker>();

        for (Base hive : hives) {
            ghives.addActor(hive);
        }
        for (Box flower : flowers) {
            gflowers.addActor(flower);
        }
        for (Worker bee : bees) {
            gbees.addActor(bee);
        }

        stage.addActor(ghives);
        stage.addActor(gflowers);
        stage.addActor(gbees);
    }

    @Override
    public void act (float delta) {

    }

    @Override
    public void draw(Batch batch, float alpha) {

    }
}
