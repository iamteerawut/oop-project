package com.oop.bar;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class BarGame extends ApplicationAdapter{
	
	BarProject game;
	
	ShapeRenderer shapeRenderer;
	SpriteBatch batch;
	OrthographicCamera camera;
	OrthographicCamera uiCamera;
	
	Texture background;
	Texture background2;
	TextureRegion bar;
	TextureRegion bar2;
	TextureRegion hand_right;
	TextureRegion hand_left;
	TextureRegion ready;
	TextureRegion gameOver;
	BitmapFont font;
	FreeTypeFontGenerator generator;
	FreeTypeFontParameter parameter;
	Boolean count = true;
	
	Vector2 hand_rPos = new Vector2();
	Vector2 hand_lPos = new Vector2();
	
	Array<Bar> bars = new Array<Bar>();
	
	Rectangle hand_r = new Rectangle();
	Rectangle hand_l = new Rectangle();
	Rectangle mbar = new Rectangle();
	
	GameState gameState = GameState.Start;
	
	int check;
	int score = -1;
	int x_right;
	int x_left;
	int speed_hand = 350;
	int s;
	float bar_x;
		
	Random ran;
	/// background ///
	public static final int DEFAULT_SPEED = 100;
	public static final int ACCELERATION = 40;
	public static final int GOAL_REACH_ACCELERATION = 200;
	
	Texture bg, bg2;
	float x1, x2;
	int speed;
	int goalSpeed;
	float imageScale;
	boolean speedPause;
	

	
	public BarGame(){
		
	}
	
	public BarGame(BarProject game){
		this.game = game;
	}
	
	@Override
	public void create() {
		
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1000, 700);
		uiCamera = new OrthographicCamera();
		uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		uiCamera.update();
		background = new Texture("background.png");
		background2 = new Texture("background.png");
		ready = new TextureRegion(new Texture("ready.png"));
		gameOver = new TextureRegion(new Texture("gameover.png"));
		font = new BitmapFont(Gdx.files.internal("howser32.fnt"));
		bar = new TextureRegion(new Texture("bar2.png"));
		bar2 = new TextureRegion(new Texture("bar.png"));
		hand_right = new TextureRegion(new Texture("boxB.png"));
		hand_left = new TextureRegion(new Texture("boxR.png"));
		ran = new Random();
		 //////////
		x1 = 0;
		x2 = background.getWidth();
		speed = 0;
		goalSpeed = DEFAULT_SPEED;
		
		
		resetWorld();
		
	}
	
	private void resetWorld() {
		score = -1;
		hand_rPos.set(300, 450);
		hand_lPos.set(300, 450);
		camera.position.x = 400;
		x_right = 155;
		x_left = 155;
		
		bars.clear();
		int prev_temp=0;
		for(int i = 0; i < 100; i++) {

			s = (ran.nextInt(4)+1)*50;
			if(i == 0){
				bars.add(new Bar(200, 450, s, 150, bar, bar2));
				prev_temp = s+200+50;
			}
			else
			{
				bars.add(new Bar(prev_temp, 450, s, prev_temp-40, bar, bar2));
				prev_temp = prev_temp+s+50;
			}	
				
		}

		
	}
	
	private void updateWorld() {
		float deltaTime = Gdx.graphics.getDeltaTime();
	
		//// BackGround ////
		batch.begin();
			if (speed < goalSpeed) {
				speed += GOAL_REACH_ACCELERATION * deltaTime;
				if (speed > goalSpeed) {
					speed = goalSpeed;
				}
				else if (speed < goalSpeed) {
					speed -= GOAL_REACH_ACCELERATION * deltaTime;
					if (speed < goalSpeed) {
						speed = goalSpeed;
					}
				}
			}
			
			x1 -= speed * Math.pow(deltaTime, 2) ;
			x2 -= speed * Math.pow(deltaTime, 2) ;
				
			if (x1 + background.getWidth()<= 0) {
					x1 = x2 + background.getWidth();
			}
			if (x2 + background.getWidth() <= 0) {
				x2 = x1 + background.getWidth();
			}
				
			batch.draw(background, x1, 0, background.getWidth(), background.getHeight());
			batch.draw(background2, x2, 0, background.getWidth(), background.getHeight());
		batch.end();
		
		if(Gdx.input.justTouched()) {
			if(gameState == GameState.Start) {
				gameState = GameState.Running;
			}
			if(gameState == GameState.Running) {
				gameState = GameState.Running;
			}
			if(gameState == GameState.GameOver) {
				gameState = GameState.Start;
				resetWorld();
			}
			
		}
		
		camera.position.x = ((x_right / 2) + (x_left / 2)) + 300;
		hand_r.set(x_right, 450, 20, 20);
		hand_l.set(x_left, 450, 20, 20);
		
		for(Bar b: bars) {
			if(camera.position.x - b.position.x > 1280 + b.image.getRegionWidth()) {
				b.position.x += (s + 50);
				b.position.y = 450;
				b.image = bar;
			}
			
			mbar.set(b.position.x, b.position.y, b.size, 20);
			if(!Gdx.input.isTouched()){
				
				if(hand_r.overlaps(mbar)) {
					if(gameState != GameState.GameOver);
					gameState = GameState.GameOver;			
				}
				if(hand_l.overlaps(mbar)) {
					if(gameState != GameState.GameOver);
					gameState = GameState.GameOver;			
				}
				if(b.position.x > hand_r.x && count) {
					score++;
					count = false;
				}
				
			}
			else count = true;
			
		}
		
		if (Gdx.input.justTouched() && gameState == GameState.Running) {
			if (check == 0) {
				check = 1;
			}
			else if (check == 1) {
				check = 0;
			}
		}
		if (Gdx.input.isTouched() && check == 1 && gameState == GameState.Running ){
			x_right += deltaTime * speed_hand;
		}
	
		else if (Gdx.input.isTouched() && check == 0 && gameState == GameState.Running ){
			x_left += deltaTime * speed_hand;
		}
	}
	
	private void drawWorld() {
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		for(Bar bar: bars) {
			batch.draw(bar.image, bar.position.x, bar.position.y, bar.size, 20);
			batch.draw(bar.image2, bar.bar_x, bar.position.y, 40, 20);
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
			font.draw(batch, "SCORE : " + score+"",  Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 60);

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
		int size;
		float bar_x;
		TextureRegion image;
		TextureRegion image2;
		boolean counted;
		
		public Bar(float x, float y, int size, float bar_x, TextureRegion image, TextureRegion image2) {
			this.position.x = x;
			this.position.y = y;
			this.image = image;
			this.image2 = image2;
			this.size = size;
			this.bar_x = bar_x;
		}
	}

	static enum GameState {
		Start, Running, GameOver
	}


}
