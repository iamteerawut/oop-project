package com.oop.bar;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class BarGame extends ApplicationAdapter implements Screen {
	
	SpriteBatch batch;
	OrthographicCamera camera;
	
	TextureRegion bar;
	Texture background;
	Texture hand_right;
	Texture hand_left;
	BitmapFont font;
	
	Vector2 hand_rPos = new Vector2();
	Vector2 hand_lPos = new Vector2();
	
	Array<Bar> bars = new Array<Bar>();
	
	Rectangle hand_r = new Rectangle();
	Rectangle hand_l = new Rectangle();
	Rectangle mbar = new Rectangle();
	
	int check;
	int score;
	int x_right, x_left;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		
		font = new BitmapFont(Gdx.files.internal("arial.fnt"));
		
		background = new Texture("bg4.png");	
		bar = new TextureRegion(new Texture("bar.png"));
		hand_right = new Texture("boxR.png");
		hand_left = new Texture("boxB.png");
		
		resetWorld();
	}
	
	private void resetWorld() {
		hand_rPos.set(300, 450);
		hand_lPos.set(300, 450);
		camera.position.x = 400;
		
		bars.clear();
		for(int i = 0; i < 7; i++) {
			bars.add(new Bar(i * 200, 450, bar));
		}
	}
	
	private void updateWorld() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		camera.position.x = (x_right / 2) + (x_left / 2) + 400;
		
		hand_r.set(x_right + 300, 450, 50, 50);
		hand_l.set(x_left + 300, 450, 50, 50);
		
		for(Bar b: bars) {
			if(camera.position.x - b.position.x > 650 + b.image.getRegionWidth()) {
				b.position.x += 7 * 200;
				b.position.y = 450;
				b.image = bar;
				b.counted = false;
			}
			mbar.set(b.position.x, b.position.y, 10, 10);
			if (!Gdx.input.isTouched()) {
				if (hand_r.overlaps(mbar) && !hand_l.overlaps(mbar)) {
					System.out.println("Game Over");
				}
				if (!hand_r.overlaps(mbar) && hand_l.overlaps(mbar)) {
					System.out.println("Game Over");
				}
			}
		}
		
		if (Gdx.input.justTouched()) {
			if (check == 0) {
				check = 1;
			}
			else if (check == 1) {
				check = 0;
			}
		}
		if (Gdx.input.isTouched() && check == 1){
			x_right += deltaTime * 250;
		}
	
		else if (Gdx.input.isTouched() && check == 0){
			x_left += deltaTime * 250;
		}
	}
	
	private void drawWorld() {
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, camera.position.x - background.getWidth() / 2, 0);
		for(Bar bar: bars) {
			batch.draw(bar.image, bar.position.x, bar.position.y);
		}
		batch.draw(hand_right, x_right + 300, 450, 40, 40);
		batch.draw(hand_left, x_left + 300, 450, 40, 40);
		batch.end();
		
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		updateWorld();
		drawWorld();
	}

	static class Bar {
		Vector2 position = new Vector2();
		TextureRegion image;
		boolean counted;
		
		public Bar(float x, float y, TextureRegion image) {
			this.position.x = x;
			this.position.y = y;
			this.image = image;
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

}
