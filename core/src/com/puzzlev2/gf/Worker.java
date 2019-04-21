package com.puzzlev2.gf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    int load;
    boolean iterate;
    float i;
    ArrayList<Coordinate> coords;

    Circle circle;
    private TextureRegion base;
    BitmapFont font;

    public Worker(float x, float y, int width, int height, float r, int load, int color, int type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.r = r;
        this.strength = load;
        this.load = load;
        this.color = color;
        circle = new Circle();
        circle.setRadius(1.5f * r);
        circle.setPosition(x - width / 2, y - height / 2);
        String file;
        if (type == 0) file = "bee" + color + ".png";
        else file = "worker" + color + ".png";
        base = new TextureRegion(new Texture(Gdx.files.internal(file)), 0, 0, width, height);
        font = new BitmapFont();
    }

    @Override
    public void act(float delta) {
        if (coords != null && i < coords.size()) {
            x = coords.get((int) i).x;
            y = coords.get((int) i).y;
            circle.setPosition(x - width / 2, y - height / 2);
            i+=0.25;
        } else if (coords != null && i == coords.size()) {
            i = 0;
            coords = null;
            iterate = false;
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(base, x, y, width / 2, height / 2, 1.5f * width, 1.5f * height, 1.5f, 1.5f, 0);
        font.draw(batch, "" + load + "/" + (int) strength, x, y - 1);
    }

    public void update(float damage) {

    }
}
