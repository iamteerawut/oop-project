package com.oop.bar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
	float val_x_right;
	float val_x_left;
	Texture tex1, tex2;
	SpriteBatch batch;
	
	public Player(){
		tex1 = new Texture("boxR.png");
		tex2 = new Texture("boxB.png");
	}
	
	public void render(float xR, float xB) {
		batch.draw(tex1, xR, 500, 20, 20);
		batch.draw(tex2, xB,500, 20, 20);
		
	}
}
