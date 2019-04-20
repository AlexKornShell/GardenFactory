package com.puzzlev2.gf;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.Random;

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

        ghives = new Group();
        gflowers = new Group();
        gbees = new Group();

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

    public void generate() {
        Random random = new Random();
        if(random.nextInt(100) == 1) {
            Base b = new Base(random.nextInt(1000), random.nextInt(1000), 22, 48);
            ghives.addActor(b);
            hives.add(b);
        }
    }
}
