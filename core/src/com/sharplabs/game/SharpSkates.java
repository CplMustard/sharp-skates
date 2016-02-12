package com.sharplabs.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class SharpSkates extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	OrthographicCamera camera;
	Rectangle dude;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		dude = new Rectangle();
		dude.x = 800/2 - 64/2;
		dude.y = 0;
		dude.width = 64;
		dude.height = 64;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 5, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(img, dude.x, dude.y);
		batch.end();

		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			dude.x = touchPos.x - 64/2;
		}

		if(dude.x < 0) dude.x = 0;
		if(dude.x > 800 - 64) dude.x = 800 - 64;
	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
	}
}
