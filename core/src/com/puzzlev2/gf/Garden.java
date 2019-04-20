package com.puzzlev2.gf;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Garden extends Actor {
    Player player;

    private ArrayList<Base> bases;
    private ArrayList<Box> boxes;
    private ArrayList<Worker> workers;

    private Group gbases;
    private Group gboxes;
    private Group gworkers;

    private boolean isDrawing;
    private boolean isMoving;

    public Garden(Player player) {
        isDrawing = false;
        this.player = player;
        bases = new ArrayList<Base>();
        boxes = new ArrayList<Box>();
        workers = new ArrayList<Worker>();

        gbases = new Group();
        gboxes = new Group();
        gworkers = new Group();
    }

    public void setGroups(Stage stage) {
        stage.addActor(gbases);
        stage.addActor(gboxes);
        stage.addActor(gworkers);
    }

    @Override
    public void act (float delta) {
        if (!isDrawing) {
            generate();
        } else if (isDrawing) {

        } else if (!isMoving) {

        } else {
            for (Worker w1 : workers) {
                for (Worker w2 : workers) {
                    if (w1 != w2 && w1.color != w2.color && Intersector.overlaps(w1.circle, w2.circle)) {
                        float damage = w1.strength;
                        w1.strength -= w2.strength * 0.1f;
                        w2.strength -= damage * 0.1f;
                    }
                }
            }

            for (Worker w : workers) {
                for (Box b : boxes) {
                    if (Intersector.overlaps(w.circle, b.rectangle) && w.color == b.color) {
                        float toLoad = b.strength - b.load;
                        if (toLoad <= w.load) {
                            w.load -= toLoad;
                            b.load += toLoad;
                        } else {
                            b.load += w.load;
                            w.load -= w.load;
                        }
                    }
                }
            }
        }

    }

    @Override
    public void draw(Batch batch, float alpha) {

    }

    public void generate() {
        Random random = new Random();
        if(random.nextInt(100) == 1) {
            Base b = new Base(random.nextInt(1000), random.nextInt(1000), 95, 100, 1, 0);
            gbases.addActor(b);
            bases.add(b);
        }
    }
}
