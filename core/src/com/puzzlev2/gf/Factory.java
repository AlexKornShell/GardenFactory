package com.puzzlev2.gf;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Factory extends Actor {
    private float width;
    private float height;
    private ArrayList<Base> bases;
    private ArrayList<Box> boxes;
    private ArrayList<Worker> workers;

    private Group gbases;
    private Group gboxes;
    private Group gworkers;
    private Player player;

    public Factory(Player player, float width, float height) {
        this.player = player;
        this.width = width;
        this.height = height;
        bases = new ArrayList<Base>();
        boxes = new ArrayList<Box>();
        workers = new ArrayList<Worker>();

        gbases = new Group();
        gboxes = new Group();
        gworkers = new Group();

        this.generate(10, 100);
    }

    public void setGroups(Stage stage) {
        stage.addActor(gbases);
        stage.addActor(gboxes);
        stage.addActor(gworkers);
    }

    @Override
    public void act(float delta) {
        for (Worker w1 : workers) {
            for (Worker w2 : workers) {
                if (w1 != w2 && Intersector.overlaps(w1.circle, w2.circle)) {
                    float damage = w1.strength;
                    w1.strength -= w2.strength * 0.01f;
                    w2.strength -= damage * 0.01f;
                }
            }
        }

        for (Worker w : workers) {
            for (Box b : boxes) {
                if (Intersector.overlaps(w.circle, b.rectangle)) {
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

    @Override
    public void draw(Batch batch, float alpha) {

    }


    private void killFlowers() {
        int len = boxes.size();
        for (int i = 0; i < len; i++) {
            Box flower = boxes.get(i);
            if (flower.load <= 1f) {
                gboxes.removeActor(flower);
                boxes.remove(flower);
                len -= 1;
            }
        }
    }

    public void updateState() {
        int flowerCount = this.boxes.size();
        killFlowers();
        this.player.happiness *= flowerCount / this.boxes.size();
        generate((int) Math.floor((double) 3 * this.player.happiness / 100), this.player.happiness);
    }

    private void generate(int maxCount, int prob) {
        Random random = new Random();

        for (int i = 0; i < maxCount; i++) {
            if (random.nextInt(100) <= prob) {
                int boxWidth = 48;
                int boxHeight = 28;

                float x = random.nextFloat() *(width - boxWidth);
                float y = random.nextFloat() * (height - boxHeight);

                Box b = new Box(random.nextInt(this.player.openedColors) + 1, 90, x, y, boxWidth, boxHeight, 1);
                gboxes.addActor(b);
                boxes.add(b);
            }
        }
    }
}
