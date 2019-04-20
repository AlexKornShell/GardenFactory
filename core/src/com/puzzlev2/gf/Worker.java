package com.puzzlev2.gf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

public class Worker extends Actor {
    private float x;
    private float y;
    private float height;
    private float width;
    private float r;

    int color;
    float strength;
    float load;
    boolean iterate;
    int i;
    ArrayList<Coordinate> coords;

    Circle circle;
    private TextureRegion base;

    public Worker(float x, float y, float width, float height, float r, int color, int type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.r = r;
        circle = new Circle();
        circle.setRadius(r);
        circle.setPosition(x - width / 2, y - height / 2);
        String file;
        if (type == 0) file = "bee" + color + ".png";
        else file = "worker" + color + ".png";
        base = new TextureRegion(new Texture(Gdx.files.internal(file)), 0, 0, width, height);

    }

    @Override
    public void act(float delta) {
        if (iterate && coords != null && i < coords.size()) {
            x = coords.get(i).x;
            y = coords.get(i).y;
            circle.setPosition(x - width / 2, y - height / 2);
            i++;
        } else if (coords != null && i == coords.size()) {
            i = 0;
            iterate = false;
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(base, x, y, width / 2, height / 2, width, height, 1, 1, 0);
    }

    public void update(float damage) {

    }
}
