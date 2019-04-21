package com.puzzlev2.gf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Box extends Actor {
    private float x;
    private float y;
    private int height;
    private int width;

    int color;
    int strength;
    int load;

    Rectangle rectangle;

    private TextureRegion base;
    BitmapFont font;

    public Box(int color, int load, int strength, float x, float y, int width, int height, int type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.load = load;
        this.strength = strength;
        this.color = color;
        rectangle = new Rectangle(x, y, 1.5f * width, 1.5f * height);
        String file;
        if (type == 0) file = "flower" + width + "" + color + ".png";
        else file = "box" + color + ".png";
        base = new TextureRegion(new Texture(Gdx.files.internal(file)), 0, 0, width, height);
        font = new BitmapFont();
        font.getData().setScale(2);
    }

    @Override
    public void act(float delta) {
        //this.load -= 20;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(base, x, y, width / 2f, height / 2f, 1.5f * width, 1.5f * height, 1, 1, 0);
        font.draw(batch, "" + load + "/" + strength, x, y - 1);
    }

    public void update(float load) {

    }
}
