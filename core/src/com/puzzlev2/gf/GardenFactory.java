package com.puzzlev2.gf;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GardenFactory extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    Stage stage;
    boolean drawTragect;
    boolean isDrawing;
    boolean isGenerating;
    Player player;

    boolean fmorning;
    boolean factory;
    boolean fevening;
    boolean gmorning;
    boolean garden;
    boolean gevening;

    boolean gameOver;

    Texture tfmorning;
    Texture tfevening;
    Texture tgmorning;
    Texture tgevening;

    boolean returned;

    ArrayList<Coordinate> coordSet = new ArrayList<Coordinate>();

    @Override
    public void create() {
        isDrawing = true;
        isGenerating = true;
        stage = new Stage();
        batch = new SpriteBatch();
        player = new Player(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        tfmorning = new Texture("badlogic.jpg");
        tfevening = new Texture("badlogic.jpg");
        tgmorning = new Texture("badlogic.jpg");
        tgevening = new Texture("badlogic.jpg");

        stage.addActor(player);

        fmorning = true;

        batch.setColor(Color.BLACK);
    }

    @Override
    public void render() {
        if (garden) Gdx.gl.glClearColor(0, 1, 0, 1);
        else if (factory) Gdx.gl.glClearColor(128 / 255f, 128 / 255f, 128 / 255f, 1);
        else Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (fmorning) {
            batch.begin();
            batch.draw(tfmorning, 0, 0);
            batch.end();
            if (Gdx.input.justTouched()) {
                fmorning = false;
                factory = true;
            }
        } else if (factory) {
            if (isGenerating) {
                player.isGarden = false;
                player.factoryStep();
                isGenerating = false;
            } else if (isDrawing) {
                draw();
                //isDrawing = false;
            } else {
                stage.act();
                if (Gdx.input.justTouched()) {
                    this.player.happiness += this.player.finishFactory();
                    isGenerating = true;
                    isDrawing = true;
                    factory = false;
                    fevening = true;
                    if (player.happiness <= 0) {
                        gameOver = true;
                        fevening = false;
                    }
                }
            }
            stage.draw();
            drawTrack();
        } else if (fevening) {
            batch.begin();
            batch.draw(tfevening, 0, 0);
            batch.end();
            if (Gdx.input.justTouched()) {
                fevening = false;
                fmorning = true; // Todo
            }
        } else if (gmorning) {
            batch.begin();
            batch.draw(tgmorning, 0, 0);
            batch.end();
            if (Gdx.input.justTouched()) {
                gmorning = false;
                garden = true;
            }
        } else if (garden) {
            if (isGenerating) {
                player.isGarden = true;
                player.gardenStep();
                isGenerating = false;
            } else if (isDrawing) {
                isDrawing = false;
            } else {
                stage.act();
                if (Gdx.input.justTouched()) {
                    isGenerating = true;
                    isDrawing = true;
                    garden = false;
                    gevening = true;
                }
            }
            stage.draw();
        } else if (gevening) {
            batch.begin();
            batch.draw(tgevening, 0, 0);
            batch.end();
            if (Gdx.input.justTouched()) {
                gevening = false;
                fmorning = true;
            }
        } else if (gameOver) {
            batch.begin();
            batch.draw(tgevening,  Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
            batch.end();
        }

    }

    public void drawTrack() {
        if (!coordSet.isEmpty()) {
            batch.begin();
            Coordinate lastC = null;
            int i =0;
            ShapeRenderer shapeRenderer = new ShapeRenderer();

            for (Coordinate c : coordSet) {
                //batch.draw(tfmorning, c.getX(), c.getY(), 5, 5);

                if(i != 0){
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    shapeRenderer.setColor(Color.BLACK);
                    Gdx.gl.glLineWidth(5);
                    shapeRenderer.line(c.getX(),c.getY(),lastC.getX(),lastC.getY());
                    shapeRenderer.end();
                }
                lastC = c;
                i++;
            }
            batch.end();
        }
    }

    public void draw() {
        if (Gdx.input.isTouched()) {

            if (Gdx.input.getX() < 200 && invertCoord(Gdx.input.getY()) < 200) {
                if (!coordSet.isEmpty()) {
                    player.setCoords(coordSet);
                    isDrawing = false;
                }
            }

            if ((Gdx.input.getDeltaX() != 0 || Gdx.input.getDeltaY() != 0)) {

                    coordSet.add(new Coordinate(Gdx.input.getX(), invertCoord(Gdx.input.getY())));
            }

            System.out.println("X " + Gdx.input.getX() + " Y " + Gdx.input.getY() + " Length  " + coordSet.size()
            + " DeltaX " + Gdx.input.getDeltaX() + " DeltaY " + Gdx.input.getDeltaY());
        } else coordSet.clear();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }

    public Integer invertCoord(Integer coord) {
        int screenSize = Gdx.graphics.getHeight();

        return screenSize - coord;
    }


}
