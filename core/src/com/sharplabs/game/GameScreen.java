package com.sharplabs.game;

import java.util.Iterator;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class GameScreen implements Screen {
	final SharpSkates game;

	Texture img;
	OrthographicCamera camera;
	Rectangle dude;

	public GameScreen(final SharpSkates gam) {
		this.game = gam;
		// image to be used for sprite
		img = new Texture("skater_a.png");
		// camera to allow for view
		camera = new OrthographicCamera();
		// false means y increases upward
		camera.setToOrtho(false, 800, 480);
		// game objects are rectangles
		dude = new Rectangle();
		dude.x = 800/2 - 64/2;
		dude.y = 0;
		dude.width = 64;
		dude.height = 64;
	}

	public void render(float delta) {
		// make background ice-like
		Gdx.gl.glClearColor(214/255f, 236/255f, 235/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		// set projection matrix to one used by camera
		game.batch.setProjectionMatrix(camera.combined);
		// set up batch
		game.batch.begin();
		game.batch.draw(img, dude.x, dude.y);
		game.batch.end();

		// handle touch/mouse input
		if(Gdx.input.isTouched()) {
			// 3D because it's actually in 3D despite being in 2D
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			// TODO: this should be some sort of "target position" instead
			dude.x = touchPos.x - dude.width/2;
			dude.y = touchPos.y - dude.height/2;
		}

		// enforce boundaries
		if(dude.x < 0) dude.x = 0;
		if(dude.x > 800 - dude.width) dude.x = 800 - dude.width;

		if(dude.y < 0) dude.y = 0;
		if(dude.y > 480 - dude.height) dude.y = 800 - dude.height;
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		// dispose of native assets
		img.dispose();
	}
}
