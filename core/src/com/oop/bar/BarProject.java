package com.oop.bar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BarProject extends Game {
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	public SpriteBatch batch;
	public ScrollingBackground scrollbg;
	
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MenuScreen(this));
		this.scrollbg = new ScrollingBackground();

		
	}
	public void render () {
		super.render();
	}
	
	public void resize(int width, int height) {
		super.resize(width, height);
	}
}
