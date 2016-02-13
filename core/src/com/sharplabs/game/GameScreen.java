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
import com.badlogic.gdx.graphics.g2d.Sprite;
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
	Sprite bg;

	//Char Tracker: Keeps Track of Sprites in Game
	//Add new Skaters to this list (Suggestion: Set Player index 0)
	public Array<Skater> skaterList = new Array();
	int skaterCount = 0; 


	public GameScreen(final SharpSkates gam) {
		this.game = gam;
		// image to be used for sprite
		img = new Texture("skater_a.png");
		dude = new Skater(img, 32, Skater.Kind.Player);
		// camera to allow for view
		camera = new OrthographicCamera();
		// false means y increases upward
		camera.setToOrtho(false, game.width, game.height);

		bg = new Sprite(new Texture("background.png"));
		bg.setCenter(game.width/2, game.height/2);
		bg.scale(0.05f);

		playTime = 0;
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
		bg.draw(game.batch);
		game.batch.draw(dude.direction(dude.dir).getKeyFrame(playTime, true), dude.x, dude.y);
		game.batch.end();

		// handle touch/mouse input
		if(Gdx.input.isTouched()) {
			// 3D because it's actually in 3D despite being in 2D
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			dude.changeTarget(touchPos.x, touchPos.y, game);
		}
		//Check Overlap
		//Intersection will equal a rectangle at the point of interesction
		for(int i = 0; i < skaterCount; i++){
			for(int j = i+1; j < skaterCount; j++){
				Rectangle intersection = new Rectangle(0,0,0,0);
				intersector.intersectRectangles(skaterList.get(i).skaterRectangle, skaterList.get(j).skaterRectangle, intersection);
				if(intersection.getHeight() != 0){
					if(skaterList.get(i).kind == Skater.Kind.Kid ^ skaterList.get(i).kind == Skater.Kind.Kid){
					    //Non-KID: kid careens
					}else if((skaterList.get(i).kind == Skater.Kind.Player || skaterList.get(i).kind == Skater.Kind.Player) && (skaterList.get(i).kind == Skater.Kind.Hooligan || skaterList.get(i).kind == Skater.Kind.Hooligan)){
					    //HOOLIGAN PLAYER: Player falls
					}else if(skaterList.get(i).kind == Skater.Kind.Kid && skaterList.get(j).kind == Skater.Kind.Kid){
					    //CHILD CHILD: Child Bounce
					}
									    
				}
			}
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
