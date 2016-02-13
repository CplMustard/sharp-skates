package com.sharplabs.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class MainMenuScreen implements Screen {
	final SharpSkates game;

	OrthographicCamera camera;
	Skater dude;
	Skater gril;
	Skater hood;
	Skater kid;
	Texture dudeImg;
	Texture grilImg;
	Texture hoodImg;
	Texture kidImg;
	Array<TextureRegion> reg;
	float showTime;

	public MainMenuScreen(final SharpSkates gam) {
		// store game
		game = gam;

		// set up camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, game.width, game.height);

		// set up animation
		dudeImg = new Texture("skater_a.png");
		dude = new Skater(dudeImg, 32);
		grilImg = new Texture("girl.png");
		gril = new Skater(grilImg, 32);
		hoodImg = new Texture("hooligan.png");
		hood = new Skater(hoodImg, 32);
		kidImg = new Texture("kid.png");
		kid = new Skater(kidImg, 16);

		showTime = 0.0f;
	}

	public void render(float delta) {
		showTime += delta;
		Gdx.gl.glClearColor(game.bgRed, game.bgGreen, game.bgBlue, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.begin();
		game.font.draw(game.batch, "Welcome to Sharp Skate", 100, 150);
		game.font.draw(game.batch, "Tap anywhere to begin", 100, 100);
		game.batch.draw(dude.direction(Skater.Direction.Left).getKeyFrame(showTime, true), 100, 240);
		game.batch.draw(dude.direction(Skater.Direction.Up).getKeyFrame(showTime, true), 200, 240);
		game.batch.draw(dude.direction(Skater.Direction.Down).getKeyFrame(showTime, true), 300, 240);
		game.batch.draw(dude.direction(Skater.Direction.Right).getKeyFrame(showTime, true), 400, 240);
		game.batch.draw(dude.direction(Skater.Direction.Wipeout).getKeyFrame(showTime, true), 500, 240);
		game.batch.draw(gril.direction(Skater.Direction.Left).getKeyFrame(showTime, true), 100, 320);
		game.batch.draw(gril.direction(Skater.Direction.Up).getKeyFrame(showTime, true), 200, 320);
		game.batch.draw(gril.direction(Skater.Direction.Down).getKeyFrame(showTime, true), 300, 320);
		game.batch.draw(gril.direction(Skater.Direction.Right).getKeyFrame(showTime, true), 400, 320);
		game.batch.draw(gril.direction(Skater.Direction.Wipeout).getKeyFrame(showTime, true), 500, 320);
		game.batch.draw(hood.direction(Skater.Direction.Left).getKeyFrame(showTime, true), 100, 160);
		game.batch.draw(hood.direction(Skater.Direction.Up).getKeyFrame(showTime, true), 200, 160);
		game.batch.draw(hood.direction(Skater.Direction.Down).getKeyFrame(showTime, true), 300, 160);
		game.batch.draw(hood.direction(Skater.Direction.Right).getKeyFrame(showTime, true), 400, 160);
		game.batch.draw(hood.direction(Skater.Direction.Wipeout).getKeyFrame(showTime, true), 500, 160);
		game.batch.draw(kid.direction(Skater.Direction.Left).getKeyFrame(showTime, true), 100, 400);
		game.batch.draw(kid.direction(Skater.Direction.Up).getKeyFrame(showTime, true), 200, 400);
		game.batch.draw(kid.direction(Skater.Direction.Down).getKeyFrame(showTime, true), 300, 400);
		game.batch.draw(kid.direction(Skater.Direction.Right).getKeyFrame(showTime, true), 400, 400);
		game.batch.draw(kid.direction(Skater.Direction.Wipeout).getKeyFrame(showTime, true), 500, 400);
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
		dudeImg.dispose();
		grilImg.dispose();
		hoodImg.dispose();
		kidImg.dispose();
	}
}
