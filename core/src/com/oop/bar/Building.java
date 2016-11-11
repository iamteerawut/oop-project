package com.oop.bar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Building {
	public static final int SPEED = 100;
	public static final int WIDTH = 300;
	public static final int HEIGHT = 300;
	Texture[] listTexture;
	Texture bud1, bud2, bud3, bud4, bud5;
	
	
	float x, y;
	boolean remove = false;
	
	public Building(float x){
		this.x = Gdx.graphics.getWidth();
		this.y = Gdx.graphics.getHeight() / 2;
	}
	
	public void update(float delta){
		x -= SPEED * delta;
		if (x < -WIDTH ) {
			remove = true;
		}
	}
	
	public void render(SpriteBatch batch){
//		batch.draw(texture,  x, y );
	}

}
