package com.sharplabs.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class SharpSkates extends Game {
	// constants for use throughout
	public static int width = 800;
	public static int height = 480;
	public static float bgRed = 214/255f;
	public static float bgGreen = 236/255f;
	public static float bgBlue = 235/255f;

	SpriteBatch batch;
	BitmapFont font;
	
	@Override
	public void create() {
		// default is arial
		font = new BitmapFont();
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose() {
		// dispose of native assets
		batch.dispose();
		font.dispose();
	}
}
