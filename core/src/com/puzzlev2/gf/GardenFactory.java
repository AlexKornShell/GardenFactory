package com.puzzlev2.gf;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.HashSet;
import java.util.Set;

public class GardenFactory extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Stage stage;
	boolean drawTragect;
	boolean isDrawing;
	Player player;

	Set <Coordinate> coordSet = new HashSet<Coordinate>();

	@Override
	public void create () {
		stage = new Stage();
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		player = new Player(stage);
		stage.addActor(player);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();

		if (isDrawing) {
			for (Coordinate c : coordSet) {
				batch.begin();
				batch.draw(img, c.x, c.y, 10, 10);
				batch.end();
			}

			if (Gdx.input.isTouched()) {

				if (Gdx.input.getX() < 200 && invertCoord(Gdx.input.getY()) < 200) {
					if (!coordSet.isEmpty()) coordSet.clear();
					if (coordSet.isEmpty()) drawTragect = true;

				} else {
					if (coordSet.isEmpty()) drawTragect = false;
				}

				if ((Gdx.input.getDeltaX() != 0 || Gdx.input.getDeltaX() != 0)) {
					coordSet.add(new Coordinate(Gdx.input.getX(), invertCoord(Gdx.input.getY())));
				}

				System.out.println("X: " + Gdx.input.getX() + " Y:" + Gdx.input.getY() + " Length : " + coordSet.size());
			} else coordSet.clear();
		}
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	public Integer invertCoord(Integer coord){
		int screenSize = Gdx.graphics.getHeight();

		return screenSize - coord;
	}
}
