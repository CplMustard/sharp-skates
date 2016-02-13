package com.sharplabs.game;

import java.lang.Math;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Intersector;

public class GameScreen implements Screen {
	final SharpSkates game;

	Texture img;
	Skater dude;
	OrthographicCamera camera;
	float playTime;

  //Char Tracker: Keeps Track of Sprites in Game
  //Add new Skaters to this list (Suggestion: Set Player index 0)
	public Array<Skater> skaterList = new Array();
	int skaterCount = 0; 
    
	float targetX;
	float targetY;
	float deltaX;
	float deltaY;

	public GameScreen(final SharpSkates gam) {
		this.game = gam;
		// image to be used for sprite
		img = new Texture("skater_a.png");
		dude = new Skater(img, 32);
		// camera to allow for view
		camera = new OrthographicCamera();
		// false means y increases upward
		camera.setToOrtho(false, game.width, game.height);

		playTime = 0;

		targetX = 0;
		targetY = 0;
		deltaX = 0;
		deltaY = 0;
	}

	public void render(float delta) {
	  //Intersector class 
		Intersector intersector = new Intersector();  
    	
		// make background ice-like
		Gdx.gl.glClearColor(game.bgRed, game.bgGreen, game.bgBlue, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		playTime += delta;

		camera.update();
		// set projection matrix to one used by camera
		game.batch.setProjectionMatrix(camera.combined);
		// set up batch
		game.batch.begin();
		game.batch.draw(dude.direction(dude.dir).getKeyFrame(playTime, true), dude.x, dude.y);
		game.batch.end();

		// handle touch/mouse input
		if(Gdx.input.isTouched()) {
			// 3D because it's actually in 3D despite being in 2D
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			// TODO: this should be some sort of "target position" instead
			dude.changeTarget(touchPos.x, touchPos.y, game);
		}
		//Check Overlap
		//Intersection will equal a rectangle at the point of interesction
		for(int i = 0; i < skaterCount; i++){
				for(int j = 0; j < skaterCount; j++){
						if(i!=j){
							Rectangle intersection = new Rectangle(0,0,0,0);
							intersector.intersectRectangles(skaterList.get(i).skaterRectangle, skaterList.get(j).skaterRectangle, intersection);
							if(intersection.getHeight() != 0){
							  //Interesction occured @
							  //Check if Player
							  //If Neither Player
							    //Move Object 1 lightly 
							    //Move Object 2 lightly
                //If Player
                  //Move non-player
                  //Slow down player							    
						  }
						}
				}
		}

		// move if appropriate
		if(Math.abs(dude.x - targetX) > game.step) dude.x += deltaX;
		if(Math.abs(dude.y - targetY) > game.step) dude.y += deltaY;

		// enforce boundaries
		if(dude.x < 0) dude.x = 0;
		if(dude.x > game.width - dude.size) dude.x = game.width - dude.size;

		if(dude.y < 0) dude.y = 0;
		if(dude.y > game.height - dude.size) dude.y = game.height - dude.size;

		if(Math.abs(deltaX) > Math.abs(deltaY)) {
			if(deltaX > 0) {
				dir = Skater.Direction.Right;
			} else if(deltaX < 0) {
				dir = Skater.Direction.Left;
			}
		} else if(Math.abs(deltaX) < Math.abs(deltaY)) {
			if(deltaY > 0) {
				dir = Skater.Direction.Up;
			} else if(deltaY < 0) {
				dir = Skater.Direction.Down;
			}

		dude.move(game);
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
