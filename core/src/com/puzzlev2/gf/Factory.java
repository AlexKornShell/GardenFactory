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
    ArrayList<Base> bases;
    ArrayList<Box> boxes;
    ArrayList<Worker> workers;

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

        this.basicPositions();

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
                    if (w1.strength > 0 && w2.strength > 0) {
                        float damage = w1.strength;
                        w1.strength -= w2.strength * 0.01f;
                        w2.strength -= damage * 0.01f;
                    }
                }
            }
        }

        for (Worker w : workers) {
            for (Box b : boxes) {
                if (w.color == b.color && Intersector.overlaps(w.circle, b.rectangle)) {
                    System.out.println(b.color + b.rectangle.getX());
                    float toLoad = b.strength - b.load;
                    if (toLoad > 0) {
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

    private void basicPositions(){
        workers.clear();
        gworkers.clear();
        bases.clear();
        gbases.clear();

        for (int i = 1; i <= this.player.openedColors; i++) {
            int x, y;
            switch (i) {
                case (1):
                    x = (int) (width / 2f - (95 / 2f));
                    y = 0;
                    break;
                case (2):
                    x = 0;
                    y = (int) (height / 2f - (100 / 2f));
                    break;
                case (3):
                    x = (int) (width - 95);
                    y = (int) (height / 2f - (100 / 2f));
                    break;
                default:
                    x = 0;
                    y = 0;
                    break;
            }
            Worker worker = new Worker(x, y, 28, 42, 21, 100,  i, 1);
            Base base = new Base(x, y, 95, 100, i, 1);
            workers.add(worker);
            gworkers.addActor(worker);
            bases.add(base);
            gbases.addActor(base);
        }
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
        this.basicPositions();
//        int flowerCount = this.boxes.size();
//        killFlowers();
//        this.player.happiness *= flowerCount / this.boxes.size();
        this.boxes.clear();
        this.gboxes.clear();
        generate((int) Math.floor((double) 15 * this.player.happiness / 100), this.player.happiness);
    }

    public int finish() {
        int delta = 0;
        for (Worker worker : workers) {
            delta -= 100 - worker.strength;
        }
        for (Box box: boxes) {
            if (box.load == box.strength) delta += 10;
        }
        return delta;
    }

    private void generate(int maxCount, int prob) {
        Random random = new Random();

        for (int i = 0; i < maxCount; i++) {
            if (random.nextInt(100) <= prob) {
                int boxWidth = (int) (48 * 1.5f);
                int boxHeight = (int) (28 * 1.5f);

                float fieldWidth = width - 200;
                float fieldHeight = height - 300;

                float x = random.nextFloat() * (fieldWidth - boxWidth) + 100;
                float y = random.nextFloat() * (fieldHeight - boxHeight) + 100;

                Box b = new Box(random.nextInt(this.player.openedColors) + 1, 0, 10, x, y, boxWidth, boxHeight, 1);
                gboxes.addActor(b);
                boxes.add(b);
            }
        }
    }
}
