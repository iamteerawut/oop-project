package com.oop.bar;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.physics.box2d.FixtureDef;
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
	
	Music bgmusic;

	Vector2 hand_rPos = new Vector2();
	Vector2 hand_lPos = new Vector2();
	Vector2 movement = new Vector2();
	Vector2 movement2 = new Vector2();

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
	float torque = 0.0f;
	
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
	World world = new World(new Vector2(0, -9.8f), true);
	
	/// Button ///
		public static final int BUTTON_WIDTH = 100;
		Texture credit;
		Texture credit_press;
		Texture play;
		Texture play_press;
		Texture sound_l;
		Texture sound_l_press;
		Texture sound_m;
		Texture sound_m_press;
		Texture text_area;
		Texture txt_credit;
		Texture txt_mute;
		Texture txt_play_again;
		Texture txt_unmute;
		float x_button;
		float y_button;
		float y_buttonF;
		float y_textArea;
		int button_check;
		boolean press;
		boolean pressC;
		ButtonMute buttonMute;
		ButtonCredit buttonCredit;

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
		
		bgmusic = Gdx.audio.newMusic(Gdx.files.internal("sound/bg.wav"));
		bgmusic.setLooping(true);
	    bgmusic.play();

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
//		world = new World(new Vector2(0, -9.81f), true);
		HR = new Sprite(new Texture("ANATOMY/R-Hand.png"));
		HL = new Sprite(new Texture("ANATOMY/L-Hand.png"));
		AR = new Sprite(new Texture("ANATOMY/R-Arm.png"));
		AL = new Sprite(new Texture("ANATOMY/L-Arm.png"));
		He = new Sprite(new Texture("ANATOMY/Head.png"));
		bo = new Sprite(new Texture("ANATOMY/Body.png"));
		LR = new Sprite(new Texture("ANATOMY/R-Leg.png"));
		LL = new Sprite(new Texture("ANATOMY/L-Leg.png"));


		HR.setPosition(155 , 450);
        BodyDef RhandDef = new BodyDef();
        RhandDef.type = BodyDef.BodyType.KinematicBody;
        RhandDef.position.set(HR.getX()/PPM, HR.getY()/PPM);
        Rhand = world.createBody(RhandDef);
        PolygonShape Rhandshape = new PolygonShape();
        Rhandshape.setAsBox(HR.getWidth()/2/PPM, HR.getHeight()/2/PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = Rhandshape;
        fixtureDef.density = 1f;
        Rhand.createFixture(fixtureDef);
        
		
//        HL.setPosition(200/PPM, 450/PPM);
        HL.setPosition(160,450);
		L_hand = new BodyDef();
		L_hand.type = BodyDef.BodyType.KinematicBody;
		L_hand.position.set((HL.getX())/PPM, (HL.getY())/PPM);
		Lhand = world.createBody(L_hand);
		PolygonShape shapeLhand = new PolygonShape();
		shapeLhand.setAsBox(HL.getWidth()/2/PPM, HL.getHeight()/2/PPM);
		FixtureDef fixLhand = new FixtureDef();
		fixLhand.shape = shapeLhand;
		fixLhand.density = 1f;
		Lhand.createFixture(fixLhand);
		
		
		AR.setPosition(AR.getWidth()/2, AR.getHeight()/2);
		R_arm = new BodyDef();
		R_arm.type = BodyDef.BodyType.DynamicBody;
		R_arm.position.set( (AR.getX() + AR.getWidth()/2)/PPM , ((AR.getY() + AR.getHeight()/2)/PPM)*0.7f);
		Rarm = world.createBody(R_arm);
		PolygonShape shapeRarm = new PolygonShape();
		shapeRarm.setAsBox(AR.getWidth()/2/PPM*0.5f, AR.getHeight()/2/PPM);
		FixtureDef fixRarm = new FixtureDef();
		fixRarm.shape = shapeRarm;
        fixRarm.density = 1f;
		Rarm.createFixture(fixRarm);
		
		AL.setPosition(-AL.getWidth()/2, -AL.getHeight()/2);
		L_arm = new BodyDef();
		L_arm.type = BodyDef.BodyType.DynamicBody;
		L_arm.position.set((AL.getX() + AL.getWidth()/2)/PPM, (AL.getY() + AL.getHeight()/2)/PPM);
		Larm = world.createBody(L_arm);
		PolygonShape shapeLarm = new PolygonShape();
		shapeLarm.setAsBox(AL.getWidth()/2/PPM*0.5f, AL.getHeight()/2/PPM);
		FixtureDef fixLarm = new FixtureDef();
		fixLarm.shape = shapeLarm;
		fixLarm.density = 1f;
		Larm.createFixture(fixLarm);
		
		bo.setPosition(bo.getWidth()/2, bo.getHeight()/2);
		Bo = new BodyDef();
		Bo.type = BodyDef.BodyType.DynamicBody;
		Bo.position.set((bo.getX() + bo.getWidth()/2)/PPM, (bo.getY() + bo.getHeight()/2)/PPM);
		Bod = world.createBody(Bo);
		PolygonShape shapeBody = new PolygonShape();
		shapeBody.setAsBox(bo.getWidth()/2/PPM*0.5f, bo.getHeight()/2/PPM);
		FixtureDef fixBody = new FixtureDef();
		fixBody.shape = shapeBody;
		fixBody.density = 1f;
		Bod.createFixture(fixBody);
		
		He.setPosition(He.getWidth()/2, He.getHeight()/2 + 30);
		he = new BodyDef();
		he.type = BodyDef.BodyType.DynamicBody;
		he.position.set((bo.getX() + He.getWidth()/2)/PPM, (He.getY() + He.getHeight()/2)/PPM);
		head = world.createBody(he);
		PolygonShape shapehead = new PolygonShape();
		shapehead.setAsBox(He.getWidth()/2/PPM*0.5f, He.getHeight()/2/PPM);
		FixtureDef fixhead = new FixtureDef();
		fixhead.shape = shapehead;
		fixhead.density = 1f;
		head.createFixture(fixhead);
		
		DistanceJointDef ddef = new DistanceJointDef();
		ddef.length = 1/PPM;
		ddef.collideConnected = false;
		ddef.bodyA = Rhand;
		ddef.bodyB = Rarm;
		ddef.localAnchorA.set(0, 0);
		ddef.localAnchorB.set(0,100/PPM);
		world.createJoint(ddef);
		
		DistanceJointDef ddef2 = new DistanceJointDef();
		ddef2.length = 1/PPM;
		ddef2.collideConnected = false;
		ddef2.bodyA = Lhand;
		ddef2.bodyB = Larm;
		ddef2.localAnchorA.set(0, 0);
		ddef2.localAnchorB.set(0, 100/PPM);
		world.createJoint(ddef2);
		
		DistanceJointDef ddef3 = new DistanceJointDef();
		ddef3.length = 1/PPM;
		ddef3.collideConnected = false;
		ddef3.bodyA = Bod;
		ddef3.bodyB = Larm;
		ddef3.localAnchorA.set(25/PPM, 70/PPM);
		ddef3.localAnchorB.set(0, -100/PPM);
		world.createJoint(ddef3);
		ddef3.bodyA = Bod;
		ddef3.bodyB = Rarm;
		ddef3.localAnchorA.set(-25/PPM, 70/PPM);
		ddef3.localAnchorB.set(0, -100/PPM);
		world.createJoint(ddef3);
		ddef3.collideConnected = true;
		ddef3.bodyA = Bod;
		ddef3.bodyB = head;
		ddef3.localAnchorA.set(10/PPM, 100/PPM);
		ddef3.localAnchorB.set(0, -50/PPM);
		world.createJoint(ddef3);
		
		Rhandshape.dispose(); 
		shapeLarm.dispose();
		shapeRarm.dispose();
		shapeLhand.dispose();
//		shapeBod.dispose();
		debugRenderer = new Box2DDebugRenderer();
		worldcamera = new OrthographicCamera();//(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		worldcamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		/// Button ///
		credit = new Texture("button/Credit.png");
		credit_press = new Texture("button/Credit_Press.png");
		play = new Texture("button/Play.png");
		play_press = new Texture("button/Play_Press.png");
		sound_l = new Texture("button/Sound_L.png");
		sound_l_press = new Texture("button/Sound_L_Press.png");
		sound_m = new Texture("button/Sound_M.png");
		sound_m_press = new Texture("button/Sound_M_Press.png");
		text_area = new Texture("button/Text_area.png");
		txt_credit = new Texture("button/Txt_Credit.png");
		txt_mute = new Texture("button/Txt_Mute.png");
		txt_play_again = new Texture("button/Txt_PlayAgain.png");
		txt_unmute = new Texture("button/Txt_UnMute.png");
		x_button = 640-(play.getWidth()/2);
		y_textArea = (720/2-text_area.getWidth()/2);
		y_button = -(text_area.getHeight());
		y_buttonF = -(text_area.getHeight());
		button_check = 0;
		press = false;
		pressC = false;
		buttonMute = new ButtonMute(sound_l, x_button/0.80f, y_button+play.getHeight(), txt_mute);
		buttonCredit = new ButtonCredit(credit, x_button*0.75f, y_button+play.getHeight());
		
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
		bgmusic.setLooping(true);
	    bgmusic.play();
		
		/// reset animation button
		y_button = -(text_area.getHeight());
		y_buttonF = -(text_area.getHeight());
		button_check = 0;
		buttonMute.position.y = y_button;
		buttonCredit.position.y = y_button;

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
				if(Gdx.input.getX() >= (x_button*0.75f) && Gdx.input.getX() <= ((x_button*0.75f)+play.getWidth())){
					//to credit page and Animation
					//determine parameter save state
					gameState = GameState.GameOver;
					if(pressC == true){
						buttonCredit.position.x = x_button*0.75f;
						buttonCredit.position.y = y_button;
						buttonCredit.image = credit;
						pressC = false;
					}
					else{
						buttonCredit.position.x = x_button*0.75f;
						buttonCredit.position.y = y_button;
						buttonCredit.image = credit_press;
						pressC = true;
					}
				}
				if(Gdx.input.getX() >= (x_button) && Gdx.input.getX() <= ((x_button)+play.getWidth())){
					//start
					gameState = GameState.Start;
					resetWorld();
				}
				if(Gdx.input.getX() >= (x_button/0.80f) && Gdx.input.getX() <= ((x_button/0.80f)+play.getWidth())){
					//Mute or Unmute
					//Change Animation
					gameState = GameState.GameOver;
					System.out.println("Main: "+press);
					if(press == true){
						buttonMute.position.x = x_button/0.80f;
						buttonMute.position.y = y_button;
						buttonMute.image = sound_l;
						buttonMute.txt = txt_mute;
						press = false;
						bgmusic.play();
					}
					else{
						buttonMute.position.x = x_button/0.80f;
						buttonMute.position.y = y_button;
						buttonMute.image = sound_m_press;
						buttonMute.txt = txt_unmute;
						press = true;
					    bgmusic.stop();
					}
				}
			}

		}

		//// Set Camera ////
		camera.position.x = ((x_right / 2) + (x_left / 2)) + 300;
		
		hand_r.set(x_right, 450, 20, 20);
		hand_l.set(x_left, 450, 20, 20);
		
		worldcamera.position.x = ((x_right / 2) + (x_left / 2)) + 500;

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
			Rhand.setLinearVelocity( deltaTime * speed_hand, 0f);
		}

		else if (Gdx.input.isTouched() && check == 0 && gameState == GameState.Running) {
			x_left += deltaTime * speed_hand;
			Lhand.setLinearVelocity( deltaTime * speed_hand, 0f);
		}
		else if (!Gdx.input.isTouched() && check == 0 && gameState == GameState.Running) {
			Rhand.setAwake(false);
			Lhand.setAwake(false);
			//FUCK//
//			Lhand.setLinearVelocity(0f, 0f);
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
		//worldcamera.update();
		batch.setProjectionMatrix(worldcamera.combined);
		world.step(1/60f,  6, 2);
		

		batch.begin();//		Rhand.setLinearVelocity(x_right, 0f);
		HR.setPosition((Rhand.getPosition().x * PPM) - HR.getWidth()/2, (Rhand.getPosition().y * PPM) -HR.getHeight()/2 );
		HL.setPosition((Lhand.getPosition().x * PPM) - HL.getWidth()/2, (Lhand.getPosition().y * PPM) -HL.getHeight()/2 );

        AR.setPosition((Rarm.getPosition().x * PPM) - AR.getWidth()/2, (Rarm.getPosition().y * PPM) -AR.getHeight()/2);// , (Rarm.getAngle()*PPM) - AR.getWidth()/2);
        AR.setRotation((float)Math.toDegrees(Rarm.getAngle()));
 
        AL.setPosition((Larm.getPosition().x * PPM) - AL.getWidth()/2, (Larm.getPosition().y * PPM) -AL.getHeight()/2 );
        AL.setRotation((float)Math.toDegrees(Larm.getAngle()));

        bo.setPosition( (Bod.getPosition().x * PPM) - bo.getWidth()/2, (Bod.getPosition().y * PPM) -bo.getHeight()/2 );
        bo.setRotation((float)Math.toDegrees(Bod.getAngle()));
 
        He.setPosition( (head.getPosition().x * PPM) - He.getWidth()/2, (head.getPosition().y * PPM) -He.getHeight()/2 );
        He.setRotation((float)Math.toDegrees(head.getAngle()));
        batch.setProjectionMatrix(worldcamera.combined);

		debugMatrix = batch.getProjectionMatrix().cpy().scale(PPM, PPM, 0);
		
		batch.draw(HR, HR.getX(), HR.getY(), HR.getWidth(), HR.getHeight());
        batch.draw(HL, HL.getX(), HL.getY(), HL.getWidth(), HL.getHeight());      
        batch.draw(AR, AR.getX(), AR.getY(),AR.getOriginX(), AR.getOriginY(), AR.getWidth(),AR.getHeight(),AR.getScaleX(),AR.getScaleY(),AR.getRotation());
        batch.draw(AL, AL.getX(), AL.getY(),AL.getOriginX(), AL.getOriginY(), AL.getWidth(),AL.getHeight(),AL.getScaleX(),AL.getScaleY(),AL.getRotation());       
        batch.draw(He, He.getX(), He.getY(),He.getOriginX(), He.getOriginY(), He.getWidth(),He.getHeight(),He.getScaleX(),He.getScaleY(),He.getRotation());
        batch.draw(bo, bo.getX(), bo.getY(),bo.getOriginX(), bo.getOriginY(), bo.getWidth(),bo.getHeight(),bo.getScaleX(),bo.getScaleY(),bo.getRotation());
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
			/// Animation slide ///
			if(y_buttonF < y_textArea){
				y_buttonF += Gdx.graphics.getDeltaTime() * 2000;
			}
			if(y_button < y_textArea-30){
				y_button += Gdx.graphics.getDeltaTime() * 2000;
				buttonMute.position.y = y_button;
				buttonCredit.position.y = y_button;
			}
			batch.draw(gameOver, Gdx.graphics.getWidth() / 2 - gameOver.getRegionWidth() / 2, Gdx.graphics.getHeight() / 2 - gameOver.getRegionHeight() / 2);
			batch.draw(text_area, 640-text_area.getWidth()/2, y_buttonF);

			/// Animation button when mouse pass show message			
			if(Gdx.input.getX() >= (x_button*0.75f) && Gdx.input.getX() <= ((x_button*0.75f)+play.getWidth())){
				button_check = 1;
			}
			else if(Gdx.input.getX() >= (x_button) && Gdx.input.getX() <= ((x_button)+play.getWidth())){
				button_check = 2;
			}
			else if(Gdx.input.getX() >= (x_button/0.80f) && Gdx.input.getX() <= ((x_button/0.80f)+play.getWidth())){
				button_check = 3;
			}
			/// Button credit ///
			if(Gdx.input.getX() >= (x_button*0.75f) && Gdx.input.getX() <= ((x_button*0.75f)+play.getWidth()) && Gdx.input.getY() >= 720-(play.getHeight()+y_button) && Gdx.input.getY() <= 720-y_button-50 && button_check == 1){
				batch.draw(buttonCredit.image, buttonCredit.position.x, buttonCredit.position.y);
				batch.draw(txt_credit, x_button*0.75f, y_button+play.getHeight());
			}
			batch.draw(buttonCredit.image, buttonCredit.position.x, buttonCredit.position.y);
			/// Button play again ///
			if(Gdx.input.getX() >= (x_button) && Gdx.input.getX() <= ((x_button)+play.getWidth()) && Gdx.input.getY() >= 720-(play.getHeight()+y_button) && Gdx.input.getY() <= 720-y_button-50 && button_check == 2){
				batch.draw(play, x_button, y_button);
				batch.draw(txt_play_again, x_button-25, y_button+play.getHeight());
			}
			else{
				batch.draw(play, x_button, y_button);
			}
			/// Button mute and unmute ///
			if(Gdx.input.getX() >= (x_button/0.80f) && Gdx.input.getX() <= ((x_button/0.80f)+play.getWidth()) && Gdx.input.getY() >= 720-(play.getHeight()+y_button) && Gdx.input.getY() <= 720-y_button-50 && button_check == 3){
				batch.draw(buttonMute.image, buttonMute.position.x, buttonMute.position.y);
				batch.draw(buttonMute.txt, x_button/0.80f, y_button+play.getHeight());
			}
			batch.draw(buttonMute.image, buttonMute.position.x, buttonMute.position.y);
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
	
	static class ButtonMute{
		Vector2 position = new Vector2();
		Texture image;
		Texture txt;
		
		public ButtonMute(Texture image, float x, float y, Texture txt){
			this.image = image;
			this.position.x = x;
			this.position.y = y;
			this.txt = txt;
		}
	}
	
	static class ButtonCredit{
		Vector2 position = new Vector2();
		Texture image;
		
		public ButtonCredit(Texture image, float x, float y){
			this.image = image;
			this.position.x = x;
			this.position.y = y;
		}
	}

	static enum GameState {
		Start, Running, GameOver
	}

}

