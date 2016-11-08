package com.oop.bar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class MenuScreen implements Screen{

	private static final int PLAY_BUTTON_WIDTH = 10;
	private static final int PLAY_BUTTON_HEIGHT = 10;
	private static final int EXIT_BUTTON_WIDTH = 10;
	private static final int EXIT_BUTTON_HEIGHT = 10;
	private static final int SETTING_BUTTON_WIDTH = 10;
	private static final int SETTING_BUTTON_HEIGHT = 10;
	
	BarProject game;
	
	Texture playButtonActive;
	Texture playButtonInactive;
	Texture exitButtonActive;
	Texture exitButtonInactive;
	Texture settingButtonActive;
	Texture settingButtonInactive;
	
	public MenuScreen(BarProject game) {
		this.game = game;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(.222f, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		if (Gdx.input.isTouched()) {
		}
		game.batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
