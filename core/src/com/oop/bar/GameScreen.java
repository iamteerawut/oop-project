package com.oop.bar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
	
	BarProject game;
	OrthographicCamera camera;
	OrthographicCamera uiCamera;
	
	TextureRegion bar;
	Array<Barja> bars = new Array<Barja>();
	
	SpriteBatch batch;
	
	float check=0;
	float x_start_r = 300;
	float x_start_l = 300;
	float x_right, x_left;
	float y_right = 450;
	float y_left = 450;
	float speed_hand = 500;
	
	int score = 0;
	
	Texture tex1 = new Texture("boxR.png");
	Texture tex2 = new Texture("boxB.png");
	
	Rectangle hand_r = new Rectangle();
	Rectangle hand_l = new Rectangle();
	Rectangle bar1 = new Rectangle();

	public GameScreen(BarProject game) {
		this.game = game;
		
	}

	public void show() {
		
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);;
		uiCamera = new OrthographicCamera();
		uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		uiCamera.update();
		
		bar = new TextureRegion(new Texture("bar.png"));
		
		for(int i = 0; i < 8; i++) {
			bars.add(new Barja((i * 250), 450, bar));
		}
		
		hand_r.set(x_right + x_start_r, y_right, 50, 50);
		hand_l.set(x_left + x_start_l, y_left, 50, 50);
		
		for(Barja bar: bars) {
			bar.position.x += 1 * 250;
			bar1.set(bar.position.x, bar.position.y, 50, 50);
			if (!Gdx.input.isTouched()) {
				if (hand_r.overlaps(bar1)) {
					score++;
					System.out.println(score);
				}
				else if (hand_l.overlaps(bar1)) {
					score++;
					System.out.println(score);
				}
			}
		}
		
		camera.position.x = (x_right / 2) + (x_left / 2) + 400;
		camera.update();
		
		game.batch.setProjectionMatrix(camera.combined);
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
				//x_left -= delta*speed_hand;
				//y_right = ((x_right*x_right) /400) + 450;
				game.scrollbg.setPause(true);
				game.scrollbg.updateAndRender(delta, game.batch);
			}
		
		else if (Gdx.input.isTouched() && check == 0){
				//x_right -=delta*speed_hand;
				x_left +=delta*speed_hand;
				//y_left = ((x_left*x_left) /400) + 450;
				game.scrollbg.setPause(true);
				game.scrollbg.updateAndRender(delta, game.batch);
		}
		
		for(Barja b: bars) {
			game.batch.draw(b.image, b.position.x, b.position.y);
		}
		
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
	
	static class Barja {
		Vector2 position = new Vector2();
		TextureRegion image;

		public Barja(float x, float y, TextureRegion image) {
			this.position.x = x;
			this.position.y = y;
			this.image = image;
		}
	}

}
