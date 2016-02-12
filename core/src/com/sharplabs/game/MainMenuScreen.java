package com.sharplabs.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MainMenuScreen implements Screen {
	final SharpSkates game;

	OrthographicCamera camera;

	public MainMenuScreen(final SharpSkates gam) {
		// store game
		game = gam;

		// set up camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(214/255f, 236/255f, 235/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.begin();
		game.font.draw(game.batch, "Welcome to Sharp Skate", 100, 150);
		game.font.draw(game.batch, "Tap anywhere to begin", 100, 100);
		game.batch.end();

		if(Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
	}

	@Override
	public void resize(int width, int height){
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
	}
}
