package com.puzzlev2.gf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Box extends Actor {
    private float x;
    private float y;
    private float height;
    private float width;

    int color;
    float strength;
    float load;

    Rectangle rectangle;

    private TextureRegion base;

    public Box(float x, float y, float width, float height, int color, int type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        rectangle = new Rectangle(x, y, width, height);
        String file;
        if (type == 0) file = "flower" + (int) width + "" + color + ".png";
        else file = "box" + (int) width + "" + color + ".png";
        base = new TextureRegion(new Texture(Gdx.files.internal(file)), 0, 0, width, height);
    }

    @Override
    public void act(float delta) {

    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(base, x, y, width / 2, height / 2, width, height, 1, 1, 0);
    }

    public void update(float load) {

    }
}
