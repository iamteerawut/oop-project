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
	float xR=600 , xB=700 ;
	float count = 0, check;
	SpriteBatch batch;
	public GameScreen(BarProject game) {
		this.game = game;
		tex1 = new Texture("boxR.png");
		tex2 = new Texture("boxB.png");
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
			System.out.println("im in don't touch / check = "+ check);
		}
		else if (Gdx.input.isTouched() && check == 1){
				xR+=1;
				xB-=1;
				game.scrollbg.setPause(false);
				game.scrollbg.updateAndRender(delta, game.batch);
				System.out.println("im in touch / check = 0");
			}
		
		else if (Gdx.input.isTouched() && check == 0){
				xR-=1;
				xB+=1;
				game.scrollbg.setPause(false);
				game.scrollbg.updateAndRender(delta, game.batch);
				System.out.println("im in touch / check = 1");
		}

		
		game.batch.draw(tex1, xR, 300, 20, 20);
		game.batch.draw(tex2, xB, 300, 20, 20);
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
