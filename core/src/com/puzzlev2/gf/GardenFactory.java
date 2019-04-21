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

public class GardenFactory extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    Stage stage;
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
    Texture tfactory;
    Texture tfevening;
    Texture tgmorning;
    Texture tgarden;
    Texture tgevening;

    ArrayList<ArrayList<Coordinate>> coordSet;
    ArrayList<Boolean> coordBull;

    @Override
    public void create() {
        isDrawing = true;
        isGenerating = true;
        stage = new Stage();
        batch = new SpriteBatch();
        player = new Player(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        tfmorning = new Texture("tfmorning.jpg");
        tfactory = new Texture("fon-factory.png");
        tfevening = new Texture("tfevening.jpg");
        tgmorning = new Texture("tgmorning.jpg");
        tgarden = new Texture("fon-garden.png");
        tgevening = new Texture("tfevening.jpg");

        coordBull = new ArrayList<Boolean>();
        coordSet = new ArrayList<ArrayList<Coordinate>>();
        for (int i = 0; i < 3; i++) {
            coordSet.add(new ArrayList<Coordinate>());
            coordBull.add(false);
        }

        stage.addActor(player);

        fmorning = true;
    }

    @Override
    public void render() {
        if (garden) Gdx.gl.glClearColor(0, 1, 0, 1);
        else if (factory) Gdx.gl.glClearColor(128 / 255f, 128 / 255f, 128 / 255f, 1);
        else Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (fmorning) {
            batch.begin();
            batch.draw(tfmorning, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.end();
            if (Gdx.input.justTouched()) {
                fmorning = false;
                factory = true;
            }
        } else if (factory) {
            batch.begin();
            batch.draw(tfactory, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.end();
            if (isGenerating) {
                player.isGarden = false;
                player.factoryStep();
                isGenerating = false;
            } else if (isDrawing) {
                draw();
            } else {
                stage.act();
                if (Gdx.input.justTouched()) {
                    this.player.happiness += this.player.finishFactory();

                    for (int k = 0; k < player.factory.bases.size(); k++) {
                        coordSet.get(k).clear();
                    }

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
            batch.draw(tfevening, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.end();
            if (Gdx.input.justTouched()) {
                fevening = false;
                gmorning = true; // Todo
            }
        } else if (gmorning) {
            batch.begin();
            batch.draw(tgmorning, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.end();
            if (Gdx.input.justTouched()) {
                gmorning = false;
                garden = true;
            }
        } else if (garden) {
            batch.begin();
            batch.draw(tgarden, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.end();
            if (isGenerating) {
                player.isGarden = true;
                player.gardenStep();
                isGenerating = false;
            } else if (isDrawing) {
                draw();
            } else {
                stage.act();
                if (Gdx.input.justTouched()) {

                    for (int k = 0; k < player.factory.bases.size(); k++) {
                        coordSet.get(k).clear();
                    }

                    isGenerating = true;
                    isDrawing = true;
                    garden = false;
                    gevening = true;
                }
            }
            stage.draw();
            drawTrack();
        } else if (gevening) {
            batch.begin();
            batch.draw(tgevening, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.end();
            if (Gdx.input.justTouched()) {
                gevening = false;
                fmorning = true;
            }
        } else if (gameOver) {
            batch.begin();
            batch.draw(tgevening, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.end();
        }

    }

    public void drawTrack() {
        if (!(coordSet.get(0).size() == 0) || !coordSet.get(1).isEmpty() || !coordSet.get(2).isEmpty()) {
            batch.begin();
            ShapeRenderer shapeRenderer = new ShapeRenderer();
            for (ArrayList<Coordinate> cc : coordSet) {
                Coordinate lastC = null;
                int i =0;
                for (Coordinate c : cc) {
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
            }
            batch.end();
        }
    }

    public void draw() {
        if (Gdx.input.isTouched()) {

            for (int k = 0; k < player.factory.bases.size(); k++) {
                if (player.factory.bases.get(k).rectangle.contains(Gdx.input.getX(), invertCoord(Gdx.input.getY())) && coordBull.get(k)) {
                    if (coordSet.get(k).size() >= 10 && !coordSet.get(k).isEmpty()) {
                        player.setCoords(coordSet.get(k), k);
                        coordBull.set(k, false);
                        if (coordSet.get(0).size() >= 10 && coordSet.get(1).size() >= 10 && coordSet.get(2).size() >= 10) {
                            //if (p == 3 && !coordBull.get(0) && !coordBull.get(1) && !coordBull.get(2)) {
                            isDrawing = false;
                        }
                    }
                } else if (player.factory.bases.get(k).rectangle.contains(Gdx.input.getX(), invertCoord(Gdx.input.getY())) && !coordBull.get(k) && coordSet.get(k).isEmpty()) {
                    coordBull.set(k, true);
                }
            }

            if ((Gdx.input.getDeltaX() != 0 || Gdx.input.getDeltaX() != 0)) {
                for (int k = 0; k < player.factory.bases.size(); k++) {
                    if (coordBull.get(k)) coordSet.get(k).add(new Coordinate(Gdx.input.getX(), invertCoord(Gdx.input.getY())));
                }
            }

        } else {
            for (int k = 0; k < player.factory.bases.size(); k++) {
                if (coordBull.get(k)) coordSet.get(k).clear();
            }
        }
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
