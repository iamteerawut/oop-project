package com.oop.bar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
	float val_x_right;
	float val_x_left;
	Texture tex1, tex2;
	SpriteBatch batch;
	
	public Player(){
		tex1 = new Texture("boxR.png");
		tex1 = new Texture("boxB.png");
	}
}
