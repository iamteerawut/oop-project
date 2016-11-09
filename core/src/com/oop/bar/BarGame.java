package com.oop.bar;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class BarGame extends ApplicationAdapter{
	
	BarProject game;
	
	SpriteBatch batch;
	OrthographicCamera camera;
	OrthographicCamera uiCamera;
	
	Texture background;
	TextureRegion bar;
	TextureRegion hand_right;
	TextureRegion hand_left;
	TextureRegion ready;
	TextureRegion gameOver;
	BitmapFont font;
	
	Boolean count = true;
	
	Vector2 hand_rPos = new Vector2();
	Vector2 hand_lPos = new Vector2();
	
	Array<Bar> bars = new Array<Bar>();
	
	Rectangle hand_r = new Rectangle();
	Rectangle hand_l = new Rectangle();
	Rectangle mbar = new Rectangle();
	
	GameState gameState = GameState.Start;
	
	int check;
	int score = 0;
	int x_right = 200, x_left = 200;
	int speed_hand = 300;
		
	Random ran;
	
	public BarGame(){
		
	}
	
	public BarGame(BarProject game){
		this.game = game;
	}
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		uiCamera = new OrthographicCamera();
		uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		uiCamera.update();
		
		font = new BitmapFont(Gdx.files.internal("arial.fnt"));
		
		background = new Texture("bg4.png");	
		ready = new TextureRegion(new Texture("ready.png"));
		gameOver = new TextureRegion(new Texture("gameover.png"));
		
		bar = new TextureRegion(new Texture("plane1.png"));
		hand_right = new TextureRegion(new Texture("boxB.png"));
		hand_left = new TextureRegion(new Texture("boxR.png"));
		ran = new Random();
				
		resetWorld();
		
	}
	
	private void resetWorld() {
		score = 0;
//		hand_rPos.set(300, 450);
		hand_lPos.set(300, 450);
		camera.position.x = 400;
		
		bars.clear();
		
		for(int i = 0; i < 100; i++) {
			float r = ran.nextFloat()* 100;
			
			if(i == 0){
				bars.add(new Bar(200, 450, bar));
			}
//			else if(i == 1){
//				bars.add(new Bar(200, 450, bar));
//			}
			else{
				if(r < 50){
					r = 82;
				}
				bars.add(new Bar(i *100 + r + 200, 450, bar));
			}
			
		}
		
	}
	
	private void updateWorld() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		if(Gdx.input.justTouched()) {
			if(gameState == GameState.Start) {
				gameState = GameState.Running;
			}
			if(gameState == GameState.GameOver) {
				game.dispose();
//				gameState = GameState.Start;
				resetWorld();
			}
		}
		
		camera.position.x = (x_right / 2) + (x_left / 2) + 400;
		
		hand_r.set(x_right, 450, 20, 20);
		hand_l.set(x_left, 450, 20, 20);
		
		
		for(Bar b: bars) {
			if(camera.position.x - b.position.x > 650 + b.image.getRegionWidth()) {
				b.position.x += 100 * 100 + 200;
				b.position.y = 450;
				b.image = bar;
				System.out.println(b.position);
			}
			//mbar.set(b.position.x, b.position.y, 20, 20);
			System.out.println(mbar.x);
			if(!Gdx.input.isTouched()){
				mbar.set(b.position.x, b.position.y, 20, 20);
				System.out.println("  "+mbar.x);
				if((hand_r.overlaps(mbar) || hand_l.overlaps(mbar)) && count){
//					System.out.println("hand_r is : " + hand_r.x + "/" + mbar.x);
//					System.out.println("hand_l is : " + hand_l.x + "/" + mbar.x);
					score ++;	
					count = false;
				}
				
//				else if((hand_r.x > mbar.x && hand_l.x > mbar.x) && count){
//					System.out.println("!hand_c is : " + hand_check.x + "/" + mbar.x);
//						if(gameState != GameState.GameOver){
//							gameState = GameState.GameOver;
//						}
//				}
				
			}
			else count = true;
			
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
			x_right += deltaTime * speed_hand;
		}
	
		else if (Gdx.input.isTouched() && check == 0){
			x_left += deltaTime * speed_hand;
		}
	}
	
	private void drawWorld() {
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, camera.position.x - background.getWidth() / 2, 0);
		for(Bar bar: bars) {
			batch.draw(bar.image, bar.position.x, bar.position.y,20,20);
		}
		batch.draw(hand_right, x_right, 450, 20, 20);
		batch.draw(hand_left, x_left, 450, 20, 20);
		batch.end();
		
		batch.setProjectionMatrix(uiCamera.combined);
		batch.begin();		
		if(gameState == GameState.Start) {
			batch.draw(ready, Gdx.graphics.getWidth() / 2 - ready.getRegionWidth() / 2, Gdx.graphics.getHeight() / 2 - ready.getRegionHeight() / 2);
		}
		if(gameState == GameState.GameOver) {
			batch.draw(gameOver, Gdx.graphics.getWidth() / 2 - gameOver.getRegionWidth() / 2, Gdx.graphics.getHeight() / 2 - gameOver.getRegionHeight() / 2);
		}
		if(gameState == GameState.GameOver || gameState == GameState.Running) {
			font.draw(batch, "" + score, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 60);
		}
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

	static enum GameState {
		Start, Running, GameOver
	}

}
