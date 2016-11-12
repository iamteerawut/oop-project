package com.oop.bar;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

public class BarGame extends ApplicationAdapter {

	BarProject game;
	
	ShapeRenderer shapeRenderer;
	SpriteBatch batch;
	OrthographicCamera camera;
	OrthographicCamera uiCamera;
	OrthographicCamera worldcamera;
	Box2DDebugRenderer debugRenderer;
	Matrix4 debugMatrix;

	Texture background;
	Texture background2;
	TextureRegion bar;
	TextureRegion bar2;
	TextureRegion hand_right;
	TextureRegion hand_left;
	TextureRegion ready;
	TextureRegion gameOver;
	BitmapFont scoreFont;
	BitmapFont font;
	FreeTypeFontGenerator generator;
	FreeTypeFontParameter parameter;
	Boolean count = true;

	Vector2 hand_rPos = new Vector2();
	Vector2 hand_lPos = new Vector2();

	Array<Bar> bars = new Array<Bar>();
	Array<Building> building = new Array<Building>();
	Array<Texture> build = new Array<Texture>();
	Array<Tree> trees = new Array<Tree>();
	Array<Texture> tree = new Array<Texture>();

	Rectangle hand_r = new Rectangle();
	Rectangle hand_l = new Rectangle();
	Rectangle mbar = new Rectangle();

	GameState gameState = GameState.Start;

	int check;
	int score = 0;
	int highscore = 0;
	int speed_hand = 350;
	int s;
	float bar_x;
	float x_right;
	float x_left;

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

	/// Player ///
	static final float PPM = 100;
	Texture img;
	Sprite sprite;
	Sprite HR;
	Sprite HL;
	Sprite AR;
	Sprite AL;
	Sprite He;
	Sprite bo;
	Sprite LR;
	Sprite LL;
	BodyDef R_hand;
	BodyDef L_hand;
	BodyDef R_arm;
	BodyDef L_arm;
	BodyDef he;
	BodyDef Bo;
	BodyDef R_leg;
	BodyDef L_leg;
	Body Rhand;
	Body Lhand;
	Body Rarm;
	Body Larm;
	Body head;
	Body Bod;
	Body Rleg;
	Body Lleg;
	World world = new World(new Vector2(0, -9.81f), true);

	public BarGame() {

	}

	public BarGame(BarProject game) {
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
		font = new BitmapFont(Gdx.files.internal("font/howser-72.fnt"));
		scoreFont = new BitmapFont(Gdx.files.internal("font/howser-36.fnt"));

		bar = new TextureRegion(new Texture("bar2.png"));
		bar2 = new TextureRegion(new Texture("bar.png"));
		hand_right = new TextureRegion(new Texture("boxB.png"));
		hand_left = new TextureRegion(new Texture("boxR.png"));
		ran = new Random();

		// scrolling Background //
		x1 = 0;
		x2 = background.getWidth();
		speed = 0;
		goalSpeed = DEFAULT_SPEED;
		// Building //
		build.add(new Texture("building/YellowHouse.png"));
		build.add(new Texture("building/Apartment.png"));
		build.add(new Texture("building/mansion.png"));
		build.add(new Texture("building/Hospital.png"));
		build.add(new Texture("building/PostOffice.png"));
		build.add(new Texture("building/YellowVilla.png"));
		build.add(new Texture("building/ClockTower.png"));
		// Tree //
		tree.add(new Texture("Tree/tree1.png"));
		tree.add(new Texture("Tree/tree2.png"));
		tree.add(new Texture("Tree/tree3.png"));
		tree.add(new Texture("Tree/tree4.png"));
		tree.add(new Texture("Tree/tree5.png"));
		tree.add(new Texture("Tree/tree6.png"));

		/// Player ///
		world = new World(new Vector2(0, -9.81f), true);
		HR = new Sprite(new Texture("ANATOMY/R-Hand.png"));
		HL = new Sprite(new Texture("ANATOMY/L-Hand.png"));
		AR = new Sprite(new Texture("ANATOMY/R-Arm.png"));
		AL = new Sprite(new Texture("ANATOMY/L-Arm.png"));
		He = new Sprite(new Texture("ANATOMY/Head.png"));
		bo = new Sprite(new Texture("ANATOMY/Body.png"));
		LR = new Sprite(new Texture("ANATOMY/R-Leg.png"));
		LL = new Sprite(new Texture("ANATOMY/L-Leg.png"));

		
        HR.setPosition(155, 450);
        BodyDef RhandDef = new BodyDef();
        RhandDef.type = BodyDef.BodyType.StaticBody;
        RhandDef.position.set(HR.getX()/PPM, HR.getY()/PPM);
        Rhand = world.createBody(RhandDef);
        PolygonShape Rhandshape = new PolygonShape();
        Rhandshape.setAsBox(HR.getWidth()/2/PPM, HR.getHeight()/2/PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = Rhandshape;
        fixtureDef.density = 1f;
        //Fixture fixR_hand = 
        Rhand.createFixture(fixtureDef);
        
		
        HL.setPosition(160, 450);
		L_hand = new BodyDef();
		L_hand.type = BodyDef.BodyType.StaticBody;
		L_hand.position.set((HL.getX())/PPM, (HL.getY() )/PPM);
		Lhand = world.createBody(L_hand);
		PolygonShape shapeLhand = new PolygonShape();
		shapeLhand.setAsBox(HL.getWidth()/2/PPM, HL.getHeight()/2/PPM);
		FixtureDef fixLhand = new FixtureDef();
		fixLhand.shape = shapeLhand;
		fixLhand.density = 1f;
		Fixture fixL_hand = Lhand.createFixture(fixLhand);
		
		
		AR.setPosition(-AR.getWidth()/2, -AR.getHeight()/2);
		R_arm = new BodyDef();
		R_arm.type = BodyDef.BodyType.DynamicBody;
		R_arm.position.set((AR.getX() + AR.getWidth()/2)/PPM, (AR.getY() + AR.getHeight()/2)/PPM);
		Rarm = world.createBody(R_arm);
		PolygonShape shapeRarm = new PolygonShape();
		shapeRarm.setAsBox(AR.getWidth()/2/PPM, AR.getHeight()/2/PPM);
		FixtureDef fixRarm = new FixtureDef();
		fixRarm.shape = shapeRarm;
        fixRarm.density = 1f;
		Fixture fixR_arm = Rarm.createFixture(fixRarm);
		
		AL.setPosition(-AL.getWidth()/2, -AL.getHeight()/2);
		L_arm = new BodyDef();
		L_arm.type = BodyDef.BodyType.DynamicBody;
		L_arm.position.set((AL.getX() + AL.getWidth()/2)/PPM, (AL.getY() + AL.getHeight()/2)/PPM);
		Larm = world.createBody(L_arm);
		PolygonShape shapeLarm = new PolygonShape();
		shapeLarm.setAsBox(AL.getWidth()/2/PPM, AL.getHeight()/2/PPM);
		FixtureDef fixLarm = new FixtureDef();
		fixLarm.shape = shapeLarm;
		fixLarm.density = 1f;
		Fixture fixL_arm = Larm.createFixture(fixLarm);
		
//		bo.setPosition(350, 400);
//		Bo = new BodyDef();
//		Bo.type = BodyDef.BodyType.DynamicBody;
//		Bo.position.set(bo.getX(), bo.getY());
//		Bod = world.createBody(Bo);
//		PolygonShape shapeBod = new PolygonShape();
//		shapeBod.setAsBox(bo.getWidth(), bo.getHeight());
//		FixtureDef fixBod = new FixtureDef();
//		fixBod.shape = shapeBod;
//        fixBod.density = 0.1f;
//        fixBod.friction = 0.5f;
//        fixBod.restitution = 1f;
//		Fixture fixBo = Rarm.createFixture(fixBod);
		
		DistanceJointDef ddef = new DistanceJointDef();
		ddef.length = 10/PPM;
		ddef.collideConnected = false;
		ddef.bodyA = Rhand;
		ddef.bodyB = Rarm;
		ddef.localAnchorA.set(0, 0);
		ddef.localAnchorB.set(0,-130/PPM);
		world.createJoint(ddef);
		
		DistanceJointDef ddef2 = new DistanceJointDef();
		ddef2.length = 10/PPM;
		ddef2.collideConnected = false;
		ddef2.bodyA = Lhand;
		ddef2.bodyB = Larm;
		ddef2.localAnchorA.set(0, 0);
		ddef2.localAnchorB.set(0,-130/PPM);
		world.createJoint(ddef2);
//		
//		ddef.length = 20f;
//		ddef.collideConnected = false;
//		ddef.dampingRatio = 1f;
//		ddef.frequencyHz = 0.5f;
//		ddef.initialize(Rhand,  Rarm, new Vector2(0, 0), new Vector2(128, 0));
//		world.createJoint(ddef);
		Rhandshape.dispose(); 
		shapeLarm.dispose();
		shapeRarm.dispose();
		shapeLhand.dispose();
//		shapeBod.dispose();
		debugRenderer = new Box2DDebugRenderer();
		worldcamera = new OrthographicCamera();//(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		worldcamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		resetWorld();
	}

	private void resetWorld() {
		score = 0;
		hand_rPos.set(300, 450);
		hand_lPos.set(300, 450);
		camera.position.x = 400;
		x_right = 155;
		x_left = 160;
		bars.clear();
		building.clear();
		trees.clear();

		int prev_temp = 0;
		int home_old = 0;
		for (int i = 0; i < 100; i++) {
			//// Random Bar ////
			s = (ran.nextInt(4) + 1) * 50;
			if (i == 0) {
				bars.add(new Bar(200, 450, s, 150, bar, bar2));
				prev_temp = s + 200 + 50;
			} else {
				bars.add(new Bar(prev_temp, 450, s, prev_temp - 40, bar, bar2));
				prev_temp = prev_temp + s + 50;
			}
			//// Random Building ////
			int b = ((ran.nextInt(5) + 1) * 20);
			int p = ran.nextInt(7);
			building.add(new Building(home_old, -150, build.get(p)));
			home_old += build.get(p).getWidth() + b;

			//// Random Tree ////
			int t = ((ran.nextInt(8) + 1) * 100);
			int a = ran.nextInt(6);
			trees.add(new Tree(i * 10 * t, -100, tree.get(a)));
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
			} else if (speed < goalSpeed) {
				speed -= GOAL_REACH_ACCELERATION * deltaTime;
				if (speed < goalSpeed) {
					speed = goalSpeed;
				}
			}
		}

		x1 -= speed * Math.pow(deltaTime, 2);
		x2 -= speed * Math.pow(deltaTime, 2);

		if (x1 + background.getWidth() <= 0) {
			x1 = x2 + background.getWidth();
		}
		if (x2 + background.getWidth() <= 0) {
			x2 = x1 + background.getWidth();
		}

		batch.draw(background, x1, 0, background.getWidth(), background.getHeight());
		batch.draw(background2, x2, 0, background.getWidth(), background.getHeight());
		batch.end();

		//// GameState ////
		if (Gdx.input.justTouched()) {
			if (gameState == GameState.Start) {
				gameState = GameState.Running;
			}
			if (gameState == GameState.Running) {
				gameState = GameState.Running;
			}
			if (gameState == GameState.GameOver) {
				gameState = GameState.Start;
				resetWorld();
			}

		}

		//// Set Camera ////
		camera.position.x = ((x_right / 2) + (x_left / 2)) + 300;
		hand_r.set(x_right, 450, 20, 20);
		hand_l.set(x_left, 450, 20, 20);
		
		worldcamera.position.x = ((x_right / 2) + (x_left / 2)) + 300;

		//// Check Touch Bar ////
		for (Bar b : bars) {
			if (camera.position.x - b.position.x > 1280 + b.image.getRegionWidth()) {
				b.position.x += (s + 50);
				b.position.y = 450;
				b.image = bar;
			}

			mbar.set(b.position.x, b.position.y, b.size, 20);
			if (!Gdx.input.isTouched()) {
				if (hand_r.overlaps(mbar)) {
					if (gameState != GameState.GameOver)
						;
					gameState = GameState.GameOver;
				}
				if (hand_l.overlaps(mbar)) {
					if (gameState != GameState.GameOver)
						;
					gameState = GameState.GameOver;
				}
				if (b.position.x > hand_r.x && count && gameState == GameState.Running) {
					score++;
					count = false;
				}

			} else
				count = true;

		}

		Preferences prefs = Gdx.app.getPreferences("BarGame");
		highscore = prefs.getInteger("highscore", 0);
		if (score > highscore) {
			prefs.putInteger("highscore", score);
			prefs.flush();
		}

		//// Hand Move ////
		if (Gdx.input.justTouched() && gameState == GameState.Running) {
			if (check == 0) {
				check = 1;
			} else if (check == 1) {
				check = 0;
			}
		}
		if (Gdx.input.isTouched() && check == 1 && gameState == GameState.Running) {
			x_right += deltaTime * speed_hand;
			Rarm.applyForceToCenter(0f,10f,true);
		}

		else if (Gdx.input.isTouched() && check == 0 && gameState == GameState.Running) {
			x_left += deltaTime * speed_hand;
		}
		
		
	}

	private void drawWorld() {
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();

		//// Draw Building ///
		for (Building bud : building) {
			batch.draw(bud.image, bud.position.x, bud.position.y, bud.image.getWidth(), bud.image.getHeight());
		}
		//// Draw Building ///
		for (Tree tre : trees) {
			batch.draw(tre.image, tre.position.x, tre.position.y, tre.image.getWidth(), tre.image.getHeight());
		}

		//// Draw Bar ////
		for (Bar bar : bars) {
			batch.draw(bar.image, bar.position.x, bar.position.y, bar.size, 20);
			batch.draw(bar.image2, bar.bar_x, bar.position.y, 40, 20);
		}
        batch.end();
		//// Draw Hand ////
		worldcamera.update();
		world.step(1/60f,  6, 2);
		HR.setPosition( x_right * PPM , (Rhand.getPosition().y * PPM) -HR.getHeight()/2 );
		AR.setPosition( x_right * PPM , (Rarm.getPosition().y * PPM) -AR.getHeight()/2 );
        HL.setPosition( x_left*PPM, Lhand.getPosition().y*PPM);
        AL.setPosition( x_left * PPM, Larm.getPosition().y * PPM);
        batch.setProjectionMatrix(worldcamera.combined);

		debugMatrix = batch.getProjectionMatrix().cpy().scale(PPM, PPM, 0);
		batch.begin();
		batch.draw(HR, HR.getX(), HR.getY(), HR.getWidth()*0.8f, HR.getHeight()*0.8f);
		batch.draw(AR, AR.getX(), AR.getY(), AR.getWidth()*0.7f, AR.getHeight()*0.7f);
        batch.draw(HL, HL.getX(), HL.getY(), HL.getWidth()*0.8f, HL.getHeight()*0.8f); 
        batch.draw(AL, AL.getX(), AL.getY(), AL.getWidth()*0.7f, AL.getHeight()*0.7f);
	
        
		batch.end();
		debugRenderer.render(world, debugMatrix);
		
		batch.setProjectionMatrix(uiCamera.combined);
                
        
		batch.begin();
		//// Show State Game ////

		// Font ///
		GlyphLayout scoreLayout = new GlyphLayout(font, "" + score, Color.WHITE, 0, Align.center, false);
		GlyphLayout highscoreLayout = new GlyphLayout(scoreFont, "Best\n" + highscore, Color.BLACK, 0, Align.left,
				false);

		scoreFont.draw(batch, highscoreLayout, Gdx.graphics.getWidth() - 210, Gdx.graphics.getHeight() - 10);
		font.draw(batch, scoreLayout, Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 15);
		if (gameState == GameState.Start) {
			batch.draw(ready, Gdx.graphics.getWidth() / 2 - ready.getRegionWidth() / 2,
					Gdx.graphics.getHeight() / 2 - ready.getRegionHeight() / 2);
		}
		if (gameState == GameState.GameOver) {
			batch.draw(gameOver, Gdx.graphics.getWidth() / 2 - gameOver.getRegionWidth() / 2,
					Gdx.graphics.getHeight() / 2 - gameOver.getRegionHeight() / 2);
		}
		// if(gameState == GameState.GameOver || gameState == GameState.Running)
		// {
		// }

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

	static class Building {
		Vector2 position = new Vector2();
		Texture image;

		public Building(float x, float y, Texture image) {
			this.position.x = x;
			this.position.y = y;
			this.image = image;
		}
	}

	static class Tree {
		Vector2 position = new Vector2();
		Texture image;

		public Tree(float x, float y, Texture image) {
			this.position.x = x;
			this.position.y = y;
			this.image = image;
		}
	}

	static enum GameState {
		Start, Running, GameOver
	}

}

