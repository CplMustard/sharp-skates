package com.sharplabs.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class MainMenuScreen implements Screen {
	final SharpSkates game;

	OrthographicCamera camera;
	Skater dude;
	Skater gril;
	Texture dudeImg;
	Texture grilImg;
	Array<TextureRegion> reg;
	float showTime;
	Sprite bg;
	Sprite title;
	Sprite button1;
	Sprite button2;


	public MainMenuScreen(final SharpSkates gam) {
		// store game
		game = gam;

		// set up camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, game.width, game.height);

		// set up animation
		dudeImg = new Texture("skater_a.png");
		dude = new Skater(dudeImg, 32, Skater.Kind.Player, game);
		grilImg = new Texture("girl.png");
		gril = new Skater(grilImg, 32, Skater.Kind.Girl, game);

		bg = new Sprite(new Texture("background.png"));
		bg.setSize(game.width, game.height);
		bg.setCenter(game.width/2 - 80.0f, game.height/2);
		bg.setScale(0.85f, 1.0f);

		title = new Sprite(new Texture("title.png"));
		title.setCenter(game.width/2 - 64, game.height/2);
		title.scale(0.05f);

		button1 = new Sprite(new Texture("button.png"));
		button1.setCenter(216, 356);
		button1.scale(0.05f);

		button2 = new Sprite(new Texture("button.png"));
		button2.setCenter(416, 356);
		button2.scale(0.05f);

		showTime = 0;
	}

	public void render(float delta) {
		showTime += delta;
		Gdx.gl.glClearColor(game.bgRed, game.bgGreen, game.bgBlue, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.begin();
		bg.draw(game.batch);
		button1.draw(game.batch);
		button2.draw(game.batch);
		game.font.draw(game.batch, "Welcome to Sharp Skate", 100, 150);
		game.font.draw(game.batch, "Tap your preferred character", 100, 100);
		game.batch.draw(dude.direction(Skater.Direction.Down).getKeyFrame(showTime, true), 200, 340);
		game.batch.draw(gril.direction(Skater.Direction.Down).getKeyFrame(showTime, true), 400, 340);
		title.draw(game.batch);
		game.batch.end();

		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			if(touchPos.x >= 200 && touchPos.x <= 328 && touchPos.y >= 308 && touchPos.y <= 436) {
				game.setScreen(new GameScreen(game, true));
				dispose();
			} else if(touchPos.x >= 400 && touchPos.x <= 526 && touchPos.y >= 308 && touchPos.y <= 408) {
				game.setScreen(new GameScreen(game, false));
				dispose();
			}
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
	}
}
