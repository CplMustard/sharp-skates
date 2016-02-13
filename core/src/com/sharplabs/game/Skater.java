package com.sharplabs.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Rectangle;

public class Skater {
	Array<Animation> sprites;
	Texture img;
	float showTime;

	public int size;
	public float x;
	public float y;
	public int cTime = 0;
	
	public Rectangle skaterRectangle = new Rectangle();

	public enum Direction {
		Right,
		Left,
		Down,
		Up,
		Wipeout
	}

	public Skater(final Texture image, int size) {
		this.size = size;
		x = 0;
		y = 0;
		
	  skaterRectangle.set(x,y,size,size);
		// store the image
		img = image;

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
	public void collision(Rectangle hit){
	  cTime = 1000;
	}

}
