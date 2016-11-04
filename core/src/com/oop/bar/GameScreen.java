package com.oop.bar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oop.bar.BarProject;

public class GameScreen  implements Screen {
	
	BarProject game;
	Texture tex1, tex2;
	float xR=300 , xB=400 ;
	float check;
	SpriteBatch batch;
	public GameScreen(BarProject game) {
		this.game = game;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.scrollbg.updateAndRender(delta, game.batch);
		if (Gdx.input.justTouched()) {
			if (check == 0){check = 1;}
			else if (check == 1){check = 0;}
		}
		if (!Gdx.input.isTouched()){
			game.scrollbg.setPause(true);
			game.scrollbg.updateAndRender(delta, game.batch);
		}
		else if (Gdx.input.isTouched() && check == 1){
				xR+= delta*100;
				xB-= delta*100;
				game.scrollbg.setPause(false);
				game.scrollbg.updateAndRender(delta, game.batch);
			}
		
		else if (Gdx.input.isTouched() && check == 0){
				xR-=delta*100;
				xB+=delta*100;
				game.scrollbg.setPause(false);
				game.scrollbg.updateAndRender(delta, game.batch);
		}
		if (xR == xB)
		{
			game.setScreen(new EndScreen(game));
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
