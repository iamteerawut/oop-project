package com.oop.bar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.oop.bar.BarProject;

public class GameScreen implements Screen {
	
	BarProject game;
	float check=0;
	SpriteBatch batch;
	float x_start_r = 300;
	float x_start_l = 300;
	float x_right, x_left;
	float y_right = 450;
	float y_left = 450;
	float speed_hand = 500;
	
	Texture tex1 = new Texture("boxR.png");
	Texture tex2 = new Texture("boxB.png");
	Rectangle hand_r = new Rectangle();
	Rectangle hand_l = new Rectangle();
	
	Rectangle bar = new Rectangle();

	public GameScreen(BarProject game) {
		this.game = game;
	}

	public void show() {
		
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
				x_right += delta*speed_hand;
				x_left -= delta*speed_hand;
				y_right = ((x_right*x_right) /400) + 450;
				game.scrollbg.setPause(false);
				game.scrollbg.updateAndRender(delta, game.batch);
			}
		
		else if (Gdx.input.isTouched() && check == 0){
				x_right -=delta*speed_hand;
				x_left +=delta*speed_hand;
				y_left = ((x_left*x_left) /400) + 450;
				game.scrollbg.setPause(false);
				game.scrollbg.updateAndRender(delta, game.batch);
		}
		
		hand_r.set(x_right+x_start_r, y_right, 20, 20);
		hand_l.set(x_left+x_start_l, y_left, 20, 20);
		
//		if (rec1.overlaps(rec2)){
//			game.setScreen(new EndScreen(game));
//		}
		
		
		game.batch.draw(tex1, x_right + x_start_r, y_right, 40, 40);
		game.batch.draw(tex2, x_left + x_start_l, y_left, 40, 40);
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
