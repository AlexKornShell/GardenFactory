package com.puzzlev2.gf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Box extends Actor {
    private float x;
    private float y;
    private float height;
    private float width;

    private TextureRegion base;

    public Box(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        base = new TextureRegion(new Texture(Gdx.files.internal("core/assets/car2.png")), 0, 0, 22, 48);

    }

    @Override
    public void act (float delta) {

    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(base, x, y, width / 2, height / 2, width, height, width / 22f, height / 48f, 0);
    }
}
