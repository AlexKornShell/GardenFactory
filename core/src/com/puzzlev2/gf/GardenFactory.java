package com.puzzlev2.gf;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    Texture tfmorning;
    Texture tfevening;
    Texture tgmorning;
    Texture tgevening;

    ArrayList<Coordinate> coordSet = new ArrayList<Coordinate>();

    @Override
    public void create() {
        isDrawing = true;
        isGenerating = true;
        stage = new Stage();
        batch = new SpriteBatch();

        tfmorning = new Texture("badlogic.jpg");
        tfevening = new Texture("badlogic.jpg");
        tgmorning = new Texture("badlogic.jpg");
        tgevening = new Texture("badlogic.jpg");

        player = new Player(stage);
        stage.addActor(player);

        fmorning = true;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
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
                player.factoryStep();
                isGenerating = false;
            } else if (isDrawing) {
                isDrawing = false;
            } else {
                stage.act();
                if (Gdx.input.justTouched()) {
                    isGenerating = true;
                    isDrawing = true;
                    factory = false;
                    fevening = true;
                }
            }
            stage.draw();
        } else if (fevening) {
            batch.begin();
            batch.draw(tfevening, 0, 0);
            batch.end();
            if (Gdx.input.justTouched()) {
                fevening = false;
                gmorning = true;
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
        }

/*
	//	if (!coordSet.isEmpty()) {
		batch.begin();
			for (Coordinate c : coordSet) {
				batch.draw(tfmorning, c.x, c.y, 10, 10);
			}
		batch.end();
	//	}

		if (isDrawing) {
			if (Gdx.input.isTouched()) {

				if (Gdx.input.getX() < 200 && invertCoord(Gdx.input.getY()) < 200) {
					if (!coordSet.isEmpty()) {
					//	player.setCoords(coordSet);
					//	coordSet.clear();
						isDrawing = false;
					}
					if (coordSet.isEmpty()) drawTragect = true;

				} else {
					if (coordSet.isEmpty()) drawTragect = false;
				}

				if ((Gdx.input.getDeltaX() != 0 || Gdx.input.getDeltaX() != 0)) {
					coordSet.add(new Coordinate(Gdx.input.getX(), invertCoord(Gdx.input.getY())));
				}

				System.out.println("X: " + Gdx.input.getX() + " Y:" + Gdx.input.getY() + " Length : " + coordSet.size());
			} else coordSet.clear();
		} else if (!coordSet.isEmpty()) {
			coordSet.clear();
		}
		*/

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
