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
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class BarGame extends ApplicationAdapter{
	
	private static final float GRAVITY = -20;
	
	BarProject game;
	
	SpriteBatch batch;
	OrthographicCamera camera;
	OrthographicCamera uiCamera;
	World world;
	
	Texture background;
	TextureRegion bar;
	TextureRegion bar2;
	TextureRegion tree;
	TextureRegion hand_right;
	TextureRegion hand_left;
	TextureRegion ready;
	TextureRegion gameOver;
	BitmapFont font;
	
	Boolean count = true;
	Boolean click = null;
	Vector2 hand_rPos = new Vector2();
	Vector2 hand_lPos = new Vector2();
	
	Array<Bar> bars = new Array<Bar>();
	Array<Tree> trees = new Array<Tree>();
	
	Rectangle hand_r = new Rectangle();
	Rectangle hand_l = new Rectangle();
	Rectangle mbar = new Rectangle();
	Rectangle picTree = new Rectangle();
	
	GameState gameState = GameState.Start;
	
	int check;
	int score = 0;
	int x_right = 155;
	int x_left = 155;
	float y_right = 430;
	float y_left = 430;
	int speed_hand = 350;
	int s;
	float bar_x;
	Vector2 gravity;
	Vector2 BarVelocity;
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
		
		background = new Texture("blankBG.png");	
		ready = new TextureRegion(new Texture("ready.png"));
		gameOver = new TextureRegion(new Texture("gameover.png"));
		
		bar = new TextureRegion(new Texture("bar2.png"));
		bar2 = new TextureRegion(new Texture("bar.png"));
		tree = new TextureRegion(new Texture("tree.png"));
		hand_right = new TextureRegion(new Texture("hand.png"));
		hand_left = new TextureRegion(new Texture("hand.png"));
		ran = new Random();
		world = new World(new Vector2(0, -9.81f), true);	
		gravity = new Vector2();
		BarVelocity = new Vector2();
		
		resetWorld();
		
	}
	
	private void resetWorld() {
		score = 0;
		hand_rPos.set(300, 450);
		hand_lPos.set(300, 450);
		camera.position.x = 400;
		x_right = 155;
		x_left = 155;
		y_right = 430;
		y_left = 430;
		gravity.set(0, GRAVITY);
		bars.clear();
		trees.clear();
		
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
			
			int t = ((ran.nextInt(9)+1)*100);
			trees.add(new Tree(i*1000 + t, 50, tree));

			}
		
		
	}
	
	private void updateWorld() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		if(Gdx.input.justTouched()) {
			if(gameState == GameState.Start) {
				gameState = GameState.Running;
			}
			if(gameState == GameState.Running){
				
			}
			if(gameState == GameState.GameOver) {
				gameState = GameState.Start;
				resetWorld();
			}
		}
		
		camera.position.x = (x_right / 2) + (x_left / 2) + 400;
		
		hand_r.set(x_right, 450, 20, 50);
		hand_l.set(x_left, 450, 20, 50);
		if(gameState != GameState.Start) BarVelocity.add(gravity);
		
		
		for(Bar b: bars) {
			if(camera.position.x - b.position.x > 1280 + b.image.getRegionWidth()) {
				b.position.x += (s + 50);
				b.position.y = 450;
				b.image = bar;
			}
			
			mbar.set(b.position.x, b.position.y, b.size, 20);
			if(!Gdx.input.isTouched()){
				
				if(hand_r.overlaps(mbar)) {
					if(gameState != GameState.GameOver){
					gameState = GameState.GameOver;
					BarVelocity.x = 0 ;
					}
				}
				if(hand_l.overlaps(mbar)) {
					if(gameState != GameState.GameOver){
					gameState = GameState.GameOver;
					BarVelocity.x = 0 ;
					}
					
				}
			 	if(b.position.x > hand_r.x && count) {
					score++;
					count = false;
				}
				
			}
			else count = true;
			
			for(Tree t: trees) {
				if(camera.position.x - t.position.x > 1280 + t.image.getRegionWidth()) {
					t.position.x += 1000*100;
					t.position.y = 50;
					t.image = bar;
				}
				
				picTree.set(t.position.x, t.position.y, 300, 300 );
			}
		}
		if(Gdx.input.justTouched()){
			if (check == 0) {
				check = 1;
			}
			else if (check == 1) {
				check = 0;
			}
		}
		if (Gdx.input.isTouched() && check == 1 ){
				x_right += deltaTime * speed_hand;
			}
		
		if (Gdx.input.isTouched() && check == 0 ){
				x_left += deltaTime * speed_hand;
			}
	}
	
	private void drawWorld() {
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, camera.position.x - background.getWidth() / 2, 0);
		for(Bar bar: bars) {
			batch.draw(bar.image, bar.position.x, bar.position.y, bar.size, 20);
			batch.draw(bar.image2, bar.bar_x, bar.position.y, 40, 20);
		}
		for(Tree tree : trees){
			batch.draw(tree.image, tree.position.x, tree.position.y, 300, 300);
		}
		batch.draw(hand_right, x_right, y_right, 20, 50);
		batch.draw(hand_left, x_left, y_left, 20, 50);
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
	static class Tree {
		Vector2 position = new Vector2();
		int size;
		float Tree_x;
		TextureRegion image;
		boolean counted;
		
		public Tree(float x, float y, TextureRegion image) {
			this.position.x = x;
			this.position.y = y;
			this.image = image;
		}
	}

	static enum GameState {
		Start, Running, GameOver
	}
	

}
