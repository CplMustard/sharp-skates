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
		// image to be used for sprite
		img = new Texture("badlogic.jpg");
		// camera to allow for view
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		// game objects are rectangles
		dude = new Rectangle();
		dude.x = 800/2 - 64/2;
		dude.y = 0;
		dude.width = 64;
		dude.height = 64;
	}

	@Override
	public void render () {
		// make background ice-like
		Gdx.gl.glClearColor(214/255f, 236/255f, 235/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		// set projection matrix to one used by camera
		batch.setProjectionMatrix(camera.combined);
		// set up batch
		batch.begin();
		batch.draw(img, dude.x, dude.y);
		batch.end();

		// handle touch/mouse input
		if(Gdx.input.isTouched()) {
			// 3D because it's actually in 3D despite being in 2D
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			dude.x = touchPos.x - 64/2;
		}

		// enforce boundaries
		if(dude.x < 0) dude.x = 0;
		if(dude.x > 800 - 64) dude.x = 800 - 64;
	}

	@Override
	public void dispose() {
		// dispose of native assets
		batch.dispose();
		img.dispose();
	}
}
