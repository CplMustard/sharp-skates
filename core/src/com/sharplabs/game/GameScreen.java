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
	Texture img2;
	Skater dude;
	Skater gril;
	Skater hood;
	Skater kid;
	Texture grilImg;
	Texture hoodImg;
	Texture kidImg;
	OrthographicCamera camera;
	float playTime;
	Sprite bg;

	//Char Tracker: Keeps Track of Sprites in Game
	//Add new Skaters to this list (Suggestion: Set Player index 0)
	public Array<Skater> skaterList = new Array();
	int skaterCount = 0; 


	public GameScreen(final SharpSkates gam) {
		this.game = gam;
		// add skaters to list
		skaterList.add(new Skater(new Texture("skater_a.png"), 32, Skater.Kind.Player, game));
		skaterList.add(new Skater(new Texture("hooligan.png"), 32, Skater.Kind.Hooligan, game));
		skaterList.add(new Skater(new Texture("hooligan.png"), 32, Skater.Kind.Hooligan, game));
		skaterList.add(new Skater(new Texture("girl.png"), 32, Skater.Kind.Girl, game));
		skaterList.add(new Skater(new Texture("kid.png"), 16, Skater.Kind.Kid, game));
		skaterList.add(new Skater(new Texture("kid.png"), 16, Skater.Kind.Kid, game));
		skaterCount = 6;

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
		for(int i = 0; i < skaterCount; i++) {
			Skater skate = skaterList.get(i);
			game.batch.draw(skate.direction(skate.dir).getKeyFrame(playTime, true), skate.x, skate.y);
		}
		game.batch.end();

		// handle touch/mouse input
		if(Gdx.input.isTouched()) {
			// 3D because it's actually in 3D despite being in 2D
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			skaterList.get(0).changeTarget(touchPos.x, touchPos.y, game);
		}

		//Check Overlap
		//Intersection will equal a rectangle at the point of interesction
		for(int i = 0; i < skaterCount; i++){
			for(int j = i+1; j < skaterCount; j++){
				Rectangle intersection = new Rectangle(0,0,0,0);
				intersector.intersectRectangles(skaterList.get(i).skaterRectangle, skaterList.get(j).skaterRectangle, intersection);
				if(intersection.getHeight() != 0 && skaterList.get(i).collided == false && skaterList.get(j).collided == false){
				  System.out.println("Collision");
				  float iTargetX = skaterList.get(i).targetX;
					float iTargetY = skaterList.get(i).targetY;
					float jTargetX = skaterList.get(j).targetX;
					float jTargetY = skaterList.get(j).targetY;
					if(skaterList.get(i).collided == true || skaterList.get(j).collided == true){
					  System.out.println("Collided Check");
					  if(skaterList.get(i).collided == true && skaterList.get(j).collided == true){
					    //BOTH COLLIDED: Bounce
					    skaterList.get(i).collision(jTargetX,jTargetY,(float)1,game);
					    skaterList.get(j).collision(iTargetX,iTargetY,(float)1,game);
					  }else if(skaterList.get(i).collided == true && skaterList.get(i).kind == Skater.Kind.Kid){
					    //I IS COLLIDED KID -> J: J goes flying
					    skaterList.get(i).collision(jTargetX,jTargetY,(float)0.5,game);
					    skaterList.get(j).collision(iTargetX,iTargetY,(float)2,game);
					  }else if(skaterList.get(j).collided == true && skaterList.get(j).kind == Skater.Kind.Kid){
					    //J IS COLLIDED KID -> I: I goes flying
					    skaterList.get(i).collision(jTargetX,jTargetY,(float)2,game);
					    skaterList.get(j).collision(iTargetX,iTargetY,(float)0.5,game);
					  }else if(skaterList.get(i).collided == true && skaterList.get(i).kind != Skater.Kind.Kid){
					    //I IS COLLIDED ADULT -> J: J slides
					    skaterList.get(i).collision(jTargetX,jTargetY,(float)0.5,game);
					    skaterList.get(j).collision(iTargetX,iTargetY,(float)1,game);
					  }else if(skaterList.get(j).collided == true && skaterList.get(j).kind != Skater.Kind.Kid){
					    //J IS COLLIDED ADULT -> I: I slides
					    skaterList.get(i).collision(jTargetX,jTargetY,(float)1,game);
					    skaterList.get(j).collision(iTargetX,iTargetY,(float)0.5,game);
					  }
					}else if(skaterList.get(i).kind == Skater.Kind.Kid ^ skaterList.get(j).kind == Skater.Kind.Kid){
					    //KIDvsNON-KID: kid careens
					    System.out.println(skaterList.get(i).kind);
					    if(skaterList.get(i).kind == Skater.Kind.Kid){
					      //KID INDEX I
					      skaterList.get(i).collision(jTargetX,jTargetY,(float)2,game);
					      //skaterList.get(j).bounce(skaterList.get(i).targetX,skaterList.get(i).targetY,(float)1); //DOES NOT COLLIDE keeps going
					    }else if(skaterList.get(j).kind == Skater.Kind.Kid){
					      //KID INDEX J
					      //skaterList.get(i).bounce(skaterList.get(j).targetX,skaterList.get(j).targetY,(float)1); //DOES NOT COLLIDE kees going
					      skaterList.get(j).collision(iTargetX,iTargetY,(float)2,game);
					    }
					}else if((skaterList.get(i).kind == Skater.Kind.Player || skaterList.get(i).kind == Skater.Kind.Player) && (skaterList.get(i).kind == Skater.Kind.Hooligan || skaterList.get(i).kind == Skater.Kind.Hooligan)){
					    //HOOLIGAN PLAYER: Player falls
					    if(skaterList.get(i).kind == Skater.Kind.Player){
					      //KID INDEX I
					      skaterList.get(i).collision(jTargetX,jTargetY,(float)0.5,game);
					      //skaterList.get(j).bounce(skaterList.get(i).targetX,skaterList.get(i).targetY,(float)1); //DOES NOT COLLIDE keeps going
					    }else if(skaterList.get(j).kind == Skater.Kind.Player){
					      //KID INDEX J
					      //skaterList.get(i).bounce(skaterList.get(j).targetX,skaterList.get(j).targetY,(float)1); //DOES NOT COLLIDE kees going
					      skaterList.get(j).collision(iTargetX,iTargetY,(float)0.5,game);
					    }
					}else if(skaterList.get(i).kind == Skater.Kind.Kid && skaterList.get(j).kind == Skater.Kind.Kid){
					    //CHILD CHILD: Child Bounce
					    skaterList.get(i).bounce(jTargetX,jTargetY,(float)1,game);
					    skaterList.get(j).bounce(iTargetX,iTargetY,(float)1,game);
					}else{
					    //ADULTvsADULT: Both fall slow slide
					    skaterList.get(i).collision(jTargetX,jTargetY,(float)0.5,game);
					    skaterList.get(j).collision(iTargetX,iTargetY,(float)0.5,game);
					}									    
				}
			}
		}
		for(int i = 0; i < skaterCount; i++) {
			skaterList.get(i).move(game, playTime, skaterList);
		}
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
