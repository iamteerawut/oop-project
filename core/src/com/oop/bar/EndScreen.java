package com.oop.bar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class EndScreen implements Screen {

	BarProject game;
	GameScreen score;
	Texture textEnd;
	
	public EndScreen(BarProject game) {
		this.game = game;
		textEnd= new Texture(Gdx.files.internal("plane1.png"));
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		game.scrollbg.setPause(false);
		game.scrollbg.updateAndRender(delta, game.batch);
		game.batch.draw(textEnd, (BarProject.WIDTH / 2) - (textEnd.getWidth() / 2), 550, 100, 100); //จะเป็นคำประมาณว่า GameOver
		game.batch.draw(textEnd, (BarProject.WIDTH / 2) - (textEnd.getWidth() / 2), 200, 100, 100); // น่าจะเป็นปุ่ม Retry
		game.batch.draw(textEnd, (BarProject.WIDTH / 2) - (textEnd.getWidth() / 2), 50, 100, 100); // น่าจะเป็นเป็น Exit
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
