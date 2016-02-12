package com.sharplabs.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class SharpSkates extends Game {
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
