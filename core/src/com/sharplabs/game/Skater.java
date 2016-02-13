package com.sharplabs.game;

import static java.lang.System.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Rectangle;

public class Skater {
	Array<Animation> sprites;
	Texture img;
	float showTime;

	float targetX;
	float targetY;
	float deltaX;
	float deltaY;

	public int size;
	public float x;
	public float y;
	public int cTime = 0;
	public boolean collided;
	
	public Rectangle skaterRectangle = new Rectangle();
	public Direction dir;
	public Kind kind;

	public enum Direction {
		Right,
		Left,
		Down,
		Up,
		Wipeout
	}

	public enum Kind {
		Player,
		Hooligan,
		Girl,
		Kid
	}

	public Skater(final Texture image, int size, Kind type) {
		this.kind = type;
		this.size = size;
		x = 0;
		y = 0;
		collided = false;
		
		skaterRectangle.set(x,y,size,size);
		// store the image
		img = image;

		dir = Direction.Right;

		sprites = new Array();

		TextureRegion tmp1 = new TextureRegion(img, 0, 0, size, size);
		TextureRegion tmp2 = new TextureRegion(img, 0, size, size, size);
		TextureRegion tmp3 = new TextureRegion(img, size, 0, size, size);
		TextureRegion tmp4 = new TextureRegion(img, size, size, size, size);
		TextureRegion tmp5 = new TextureRegion(img, size*2, 0, size, size);
		TextureRegion tmp6 = new TextureRegion(img, size*2, size, size, size);
		TextureRegion tmp7 = new TextureRegion(img, size*3, 0, size, size);
		TextureRegion tmp8 = new TextureRegion(img, size*3, size, size, size);

		// set up right animation
		sprites.add(new Animation(0.6f,
					new TextureRegion(img, 0, 0, size, size),
					new TextureRegion(img, 0, size, size, size)));
		// set up left animation
		tmp1.flip(true, false);
		tmp2.flip(true, false);
		sprites.add(new Animation(0.6f, tmp1, tmp2));
		// set up up animation
		tmp3.flip(true, false);
		sprites.add(new Animation(0.6f, new TextureRegion(img, size, 0, size, size), tmp3));
		// set up down animation
		tmp4.flip(true, false);
		sprites.add(new Animation(0.6f, new TextureRegion(img, size, size, size, size), tmp4));
		// set up wipeout animation
		tmp5.flip(true, true);
		tmp6.flip(true, true);
		tmp7.flip(true, true);
		tmp8.flip(true, true);
		sprites.add(new Animation(0.15f*(size/32f),
					new TextureRegion(img, size*2, 0, size, size),
					new TextureRegion(img, size*2, size, size, size),
					new TextureRegion(img, size*3, 0, size, size),
					new TextureRegion(img, size*3, size, size, size),
					tmp5, tmp6, tmp7, tmp8));
	}

	public Animation direction(Direction dir) {
		return sprites.get(dir.ordinal());
	}
	
	//Sets Collision Logic?
	public void collision(Rectangle hit) {
		cTime = 1000;
		collided = true;
	}

	public void changeTarget(float nx, float ny, SharpSkates game) {
		double theta = Math.atan2((nx - x), (ny - y));
		deltaX = (float)Math.sin(theta) * game.step;
		deltaY = (float)Math.cos(theta) * game.step;
		targetX = nx;
		targetY = ny;
	}

	public void move(SharpSkates game, Array<Skater> skaterList) {
		switch(kind) {
			case Player:
				playerMove(game, skaterList);
				break;
			case Hooligan:
				hooliganMove(game, skaterList);
				break;
			case Girl:
				girlMove(game, skaterList);
				break;
			case Kid:
				kidMove(game, skaterList);
				break;
			default:
				break;
		}
	}

	void playerMove(SharpSkates game, Array<Skater> skaterList) {
		if(Math.abs(x - targetX) > game.step) x += deltaX;
		if(Math.abs(y - targetY) > game.step) y += deltaY;

		if(x < 0) x = 0;
		if(x > game.width - size) x = game.width - size;

		if(y < 0) y = 0;
		if(y > game.height - size) y = game.height - size;

		float absX = Math.abs(deltaX);
		float absY = Math.abs(deltaY);
		if(absX > absY) {
			if(deltaX > 0) {
				dir = Direction.Right;
			} else if(deltaX < 0) {
				dir = Direction.Left;
			}
		} else if(absX < absY) {
			if(deltaY > 0) {
				dir = Direction.Up;
			} else if(deltaY < 0) {
				dir = Direction.Down;
			}
		}
	}

	void hooliganMove(SharpSkates game, Array<Skater> skaterList) {
		float closestTargetX = Float.MAX_VALUE;
		float closestTargetY = Float.MAX_VALUE;
		for(int i=0; i<skaterList.size; i++){
			if(skaterList.get(i).kind == Kind.Girl){
				if(Math.abs((y - skaterList.get(i).y)/(x - skaterList.get(i).x)) < Math.abs((y - closestTargetY)/(x - closestTargetX))){
					closestTargetX = skaterList.get(i).x;
					closestTargetY = skaterList.get(i).y;
				}
			}
		}
		changeTarget(closestTargetX, closestTargetY, game);

		if(Math.abs(x - targetX) > game.step) x += deltaX;
		if(Math.abs(y - targetY) > game.step) y += deltaY;

		if(x < 0) x = 0;
		if(x > game.width - size/2) x = game.width - size/2;

		if(y < 0) y = 0;
		if(y > game.height - size/2) y = game.height - size/2;

		float absX = Math.abs(deltaX);
		float absY = Math.abs(deltaY);
		if(absX > absY) {
			if(deltaX > 0) {
				dir = Direction.Right;
			} else if(deltaX < 0) {
				dir = Direction.Left;
			}
		} else if(absX < absY) {
			if(deltaY > 0) {
				dir = Direction.Up;
			} else if(deltaY < 0) {
				dir = Direction.Down;
			}
		}
	}

	void girlMove(SharpSkates game, Array<Skater> skaterList) {
		// TODO: girl movement logic
	}

	void kidMove(SharpSkates game, Array<Skater> skaterList) {
		// TODO: kid movement logic
	}
}
