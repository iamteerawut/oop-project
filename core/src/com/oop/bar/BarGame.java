package com.oop.bar;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
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
	Texture bar_up;
	TextureRegion[] bar_break = new TextureRegion[2];
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


	Array<Bar> bars = new Array<Bar>();
	Array<Building> building = new Array<Building>();
	Array<Texture> build = new Array<Texture>();
	Array<Tree> trees = new Array<Tree>();
	Array<Texture> tree = new Array<Texture>();
<<<<<<< HEAD
	
	Array<ObjectDead> objectDeads = new Array<ObjectDead>();
	Array<TextureRegion> objectDead = new Array<TextureRegion>();
	
=======
	Array<Stone> stones = new Array<Stone>();
	Array<Texture> stone = new Array<Texture>(); 
>>>>>>> origin/master

	Rectangle hand_r = new Rectangle();
	Rectangle hand_l = new Rectangle();
	Rectangle mbar = new Rectangle();
	Rectangle recStone = new Rectangle();
	Rectangle recBody = new Rectangle();

	GameState gameState = GameState.Start;

	int check;
	int score = 0;
	int highscore = 0;
	int speed_hand = 400;
	int s;
	int y;
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
	boolean mute;

	/// Player ///
	static final float PPM = 100;
	Sprite HR;
	Sprite HL;
	Sprite AR;
	Sprite AL;
	Sprite He;
	Sprite bo;
	Sprite RL;
	Sprite LL;
	Sprite XA;
	
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
	Texture credit_ending;
	float x_button;
	float y_button;
	float y_buttonF;
	float y_textArea;
	float y_credit;
	int button_check;
	boolean press;
	boolean pressC;
	ButtonMute buttonMute;
	ButtonCredit buttonCredit;
	
	/// Count Combo ///
	int countCom;
	int countComNew;
	Texture combo;
	int countComMul;
	int countI;
	
	/// Font ///
	BitmapFont scoreBoardFont;
	BitmapFont fontBoard;
	
	/// LOGO ///
	Texture grandLogo;
	Animation grandPa;
	TextureRegion[] grandLogo2;
	TextureRegion currentFrame;
	float stateTime;
	
	Texture sheet_ready;
	Animation animation_ready;
	TextureRegion[] walk_ready;
	TextureRegion currentFrame_ready;
	float stateTime_ready;

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
//		ready = new TextureRegion(new Texture("get_ready1.4.png"));
//		gameOver = new TextureRegion(new Texture("gameover.png"));
		font = new BitmapFont(Gdx.files.internal("font/howser-72.fnt"));
		scoreFont = new BitmapFont(Gdx.files.internal("font/howser-36.fnt"));		
		bgmusic = Gdx.audio.newMusic(Gdx.files.internal("sound/bgmusic.wav"));
		mute = false;
		bar_break[0] = new TextureRegion(new Texture("bar2.png"));
		bar_break[1] = new TextureRegion(new Texture("Bar/break_bar.png"));
		bar2 = new TextureRegion(new Texture("Bar/Bar_center2.png"));
		bar_up = new Texture("Bar/Bar_up3.png");
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
		build.add(new Texture("building/balloon.png"));
		build.add(new Texture("building/GasStation.png"));
		build.add(new Texture("building/HighSchool.png"));
		build.add(new Texture("building/Hotel.png"));
		build.add(new Texture("building/Office.png"));
		build.add(new Texture("building/PurpleHouse.png"));
		build.add(new Texture("building/WindWheel.png"));
		build.add(new Texture("building/GreenHouse.png"));
		// Tree //
		tree.add(new Texture("Tree/tree1.png"));
		tree.add(new Texture("Tree/tree2.png"));
		tree.add(new Texture("Tree/tree3.png"));
		tree.add(new Texture("Tree/tree4.png"));
		tree.add(new Texture("Tree/tree5.png"));
		tree.add(new Texture("Tree/tree6.png"));
<<<<<<< HEAD
		// ObjectDead //
		objectDead.add(new TextureRegion(new Texture("Obj/break_pot1.png")));
		objectDead.add(new TextureRegion(new Texture("Obj/break_pot2.png")));
		objectDead.add(new TextureRegion(new Texture("Obj/flower1.png")));
		objectDead.add(new TextureRegion(new Texture("Obj/flower2.png")));
		objectDead.add(new TextureRegion(new Texture("Obj/flower3.png")));
		objectDead.add(new TextureRegion(new Texture("Obj/flower4.png")));
		objectDead.add(new TextureRegion(new Texture("Obj/rock1.png")));
		objectDead.add(new TextureRegion(new Texture("Obj/rock2.png")));
		objectDead.add(new TextureRegion(new Texture("Obj/rock3.png")));		
=======
		//Stone//
		stone.add(new Texture("rock1.png"));
		stone.add(new Texture("rock2.png"));
		stone.add(new Texture("rock3.png"));
>>>>>>> origin/master
		// Player //
		HR = new Sprite(new Texture("ANATOMY/R-Hand.png"));
		HL = new Sprite(new Texture("ANATOMY/L-Hand.png"));
		AR = new Sprite(new Texture("ANATOMY/R-Arm.png"));
		AL = new Sprite(new Texture("ANATOMY/L-Arm.png"));
		bo = new Sprite(new Texture("ANATOMY/Head-Body.png"));
		RL = new Sprite(new Texture("ANATOMY/R-Leg.png"));
		LL = new Sprite(new Texture("ANATOMY/L-Leg.png"));
		XA = new Sprite(new Texture("ANATOMY/ExtenArm.png"));
		
		/// Button ///
		credit = new Texture("button/Credit.png");
		credit_press = new Texture("button/Credit_Press.png");
		play = new Texture("button/Play.png");
//		play_press = new Texture("button/Play_Press.png");
		sound_l = new Texture("button/New_Sound_L.png");
//		sound_l_press = new Texture("button/Sound_L_Press.png");
//		sound_m = new Texture("button/Sound_M.png");
		sound_m_press = new Texture("button/New_Sound_M_Press.png");
		text_area = new Texture("button/Text_Area_New.png");
		txt_credit = new Texture("button/Txt_Credit.png");
		txt_mute = new Texture("button/Txt_Mute.png");
		txt_play_again = new Texture("button/Txt_PlayAgain.png");
		txt_unmute = new Texture("button/Txt_UnMute.png");
		credit_ending = new Texture("button/Credit_Ending.png");
		x_button = (game.WIDTH/2)-(play.getWidth()/2);
		y_textArea = ((game.HEIGHT/2)-text_area.getWidth()/2);
		y_button = -(text_area.getHeight());
		y_credit = Gdx.graphics.getHeight()+credit_ending.getHeight();
		y_buttonF = -(text_area.getHeight());
		button_check = 0;
		press = false;
		pressC = false;
		buttonMute = new ButtonMute(sound_l, x_button/0.80f, y_button+play.getHeight(), txt_mute);
		buttonCredit = new ButtonCredit(credit, x_button*0.75f, y_button+play.getHeight());
		
		/// Count Combo ///
		countCom = 0;
		countComNew = 3;
		combo = new Texture("Combo_Text.png");
		countComMul = 3;
		countI = 1;
		
		/// BoardFont ///
		fontBoard = new BitmapFont(Gdx.files.internal("font/howser-72.fnt"));
		scoreBoardFont= new BitmapFont(Gdx.files.internal("font/howser-48.fnt"));
		
		/// Logo ///
		grandLogo = new Texture(Gdx.files.internal("LogoSheet.png"));
		TextureRegion[][] tmp = TextureRegion.split(grandLogo, grandLogo.getWidth()/1, grandLogo.getHeight()/2);
		grandLogo2 = new TextureRegion[1*2];
		int index = 0;
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 1; j++){
				grandLogo2[index++] = tmp[i][j];
			}
		}
		grandPa = new Animation(0.5f, grandLogo2);
		stateTime = 0f;
		
		/// GetReaady ///
		sheet_ready = new Texture(Gdx.files.internal("getReadSheet.png"));
		TextureRegion[][] imp_r = TextureRegion.split(sheet_ready, sheet_ready.getWidth()/1, sheet_ready.getHeight()/2);
		walk_ready = new TextureRegion[1*2];
		walk_ready[0] = imp_r[0][0];
		walk_ready[1] = imp_r[1][0];
		animation_ready = new Animation(0.5f, walk_ready);
		stateTime_ready = 0f;
		
		resetWorld();
	}

	private void resetWorld() {
		score = 0;
		hand_rPos.set(300, 450);
		hand_lPos.set(300, 450);
		camera.position.x = 400;
		x_right = 155;
		x_left = 160;
		y = 450;
		bars.clear();
		building.clear();
		trees.clear();
		stones.clear();
		
		
		if (mute == true) {
			bgmusic.stop();
		}
		else {
			bgmusic.setLooping(true);
			bgmusic.play();
		}
	    
	    
		/// reset animation button
		y_button = -(text_area.getHeight());
		y_buttonF = -(text_area.getHeight());
		button_check = 0;
		buttonMute.position.y = y_button;
		buttonCredit.position.y = y_button;
		y_credit = credit_ending.getHeight();
		
		/// Count Combo ///
		countCom = 0;
		countComNew = 3;
		countComMul = 3;

		int prev_temp = 0;
		int home_old = 0;
		int ob_old = 0;
		for (int i = 0; i < 100; i++) {
			//// Random Bar ////
			s = (ran.nextInt(4) + 1) * 50;			
			if(ran.nextFloat() < 0.1){
				bar = bar_break[1];
			}else{
				bar = bar_break[0];
			}
			if (i == 0) {
				bars.add(new Bar(200, 450, s, 150, bar, bar2));
				prev_temp = s + 200 + 50;
			} else {
				bars.add(new Bar(prev_temp, 450, s, prev_temp - 30, bar, bar2));
				prev_temp = prev_temp + s + 50;
			}
			//// Random Building ////
			int b = ((ran.nextInt(5) + 1) * 20);
			int p = ran.nextInt(15);
			building.add(new Building(home_old, -150, build.get(p)));
			home_old += build.get(p).getWidth() + b;

			//// Random Tree ////
			int t = ((ran.nextInt(8) + 1) * 100);
			int a = ran.nextInt(6);
			trees.add(new Tree(i * 10 * t, -100, tree.get(a)));
			
<<<<<<< HEAD
			//// Random Object ////
			int o = ((ran.nextInt(9)+1) * 250);
			int ob = ran.nextInt(9);
			objectDeads.add(new ObjectDead(ob_old, 520, objectDead.get(ob)));
			ob_old += objectDead.get(ob).getRegionWidth()+o;
			System.out.println(o);
=======
			//// Random Stone ////
			int c = (ran.nextInt(5)+1)*10;
			int d = ran.nextInt(3);
			stones.add(new Stone(i * 10 * c , background.getHeight(), stone.get(d)));
>>>>>>> origin/master
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
				if(Gdx.input.getX() >= (x_button*0.75f) && Gdx.input.getX() <= ((x_button*0.75f)+play.getWidth()) && Gdx.input.getY() >= Gdx.graphics.getHeight()-(play.getHeight()+y_button) && Gdx.input.getY() <= Gdx.graphics.getHeight()-y_button*0.99f){
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
				if(Gdx.input.getX() >= (x_button) && Gdx.input.getX() <= ((x_button)+play.getWidth()) && Gdx.input.getY() >= Gdx.graphics.getHeight()-(play.getHeight()+y_button) && Gdx.input.getY() <= Gdx.graphics.getHeight()-y_button*0.99f){
					//start
					gameState = GameState.Start;
					resetWorld();
				}
				if(Gdx.input.getX() >= (x_button/0.80f) && Gdx.input.getX() <= ((x_button/0.80f)+play.getWidth()) && Gdx.input.getY() >= Gdx.graphics.getHeight()-(play.getHeight()+y_button) && Gdx.input.getY() <= Gdx.graphics.getHeight()-y_button*0.99f){
					//Mute or Unmute
					//Change Animation
					gameState = GameState.GameOver;
					if(press == true){
						buttonMute.position.x = x_button/0.80f;
						buttonMute.position.y = y_button;
						buttonMute.image = sound_l;
						buttonMute.txt = txt_mute;
						press = false;
						mute = false;
						bgmusic.play();
					}
					else{
						buttonMute.position.x = x_button/0.80f;
						buttonMute.position.y = y_button;
						buttonMute.image = sound_m_press;
						buttonMute.txt = txt_unmute;
						press = true;
					    mute = true;
					    bgmusic.stop();
					}
				}
			}

		}

		//// Set Camera ////
		camera.position.x = ((x_right / 2) + (x_left / 2)) + 300;
		hand_r.set(x_right, 450, 20, 20);
		hand_l.set(x_left, 450, 20, 20);
		recBody.set((x_right + x_left)/2, 450, bo.getWidth(), bo.getHeight());
		

		//// Check Touch Bar ////
		for (Bar b : bars) {
			if (camera.position.x - b.position.x > 1280 + b.image.getRegionWidth()) {
				b.position.x += (s + 50);
				b.position.y -= 2;
				b.image = bar;
			}

			mbar.set(b.position.x, b.position.y, b.size, 20);
			if (!Gdx.input.isTouched()) {
				if (hand_r.overlaps(mbar)) {
					if (gameState != GameState.GameOver){
						gameState = GameState.GameOver;
					}
				}
				if (hand_l.overlaps(mbar)) {
					if (gameState != GameState.GameOver){
						gameState = GameState.GameOver;
					}
				}
				if (b.position.x > hand_r.x && count && gameState == GameState.Running) {
					countCom++;
					if(countCom == countComNew){
						countCom = 0;
						countComMul *= countI;
						score += countComNew;
						countComNew += 3;
						countI++;
					}
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
//			if(x_right - x_left < 300){
				x_right += deltaTime * speed_hand;
//			}
			
		}

		else if (Gdx.input.isTouched() && check == 0 && gameState == GameState.Running) {
//			if((x_left - x_right) < 300){
				x_left += deltaTime * speed_hand;
//			}
			
		}
		
		/// Stone ///
//		for (Stone s : stones){
//			s.position.x += 100;
//			s.position.y -= deltaTime;
//			
//			recStone.set(s.position.x, s.position.y, 100, 100);
//			if(recBody.overlaps(recStone)){
//				if (gameState != GameState.GameOver){
//					gameState = GameState.GameOver;
//				}
//			}
//		}
		

		
		
	}

	private void drawWorld() {
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		//// Draw Building ///
		for (Building bud : building) {
			batch.draw(bud.image, bud.position.x, bud.position.y, bud.image.getWidth(), bud.image.getHeight());
		}
		//// Draw trees ///
		for (Tree tre : trees) {
			batch.draw(tre.image, tre.position.x, tre.position.y, tre.image.getWidth(), tre.image.getHeight());
		}
		//// Draw Objects ////
		for(ObjectDead obj : objectDeads){
			batch.draw(obj.image, obj.position.x, obj.position.y, obj.image.getRegionWidth(), obj.image.getRegionHeight());
		}
		
		//// Draw Body ////
		float middle = Math.abs(x_right + x_left)/2 - 80;
		batch.draw(AL, x_left - 15, y + 15 - AL.getHeight());
		batch.draw(XA, Math.min(x_left, middle) + 10, y + 15 - AL.getHeight(), Math.abs(middle - x_left), 30);
		batch.draw(LL, middle , y - 300 - LL.getHeight());
		batch.draw(bo, middle - 50, y - 10 - bo.getHeight());
		batch.draw(XA, Math.min(x_right, middle) + 10, y + 15 - AL.getHeight(), Math.abs(middle - x_right)  , 30);
		batch.draw(RL, ((Math.max(x_right, x_left) - Math.min(x_right, x_left))/2 + Math.min(x_right, x_left))- 30 , y - 300 - RL.getHeight());
		batch.draw(AR, x_right - 15, y + 15 - AR.getHeight());
		
		
		//// Draw Bar ////
		batch.draw(bar_up, x1 - 50, 460, background.getWidth() , bar_up.getHeight());
		batch.draw(bar_up, x2 - 50, 460, background.getWidth() , bar_up.getHeight());
		
		batch.draw(bar_up, x1 - 50, 400, background.getWidth() , bar_up.getHeight());
		batch.draw(bar_up, x2 - 50, 400, background.getWidth() , bar_up.getHeight());
		
		for (Bar bar : bars) {
			batch.draw(bar.image, bar.position.x + 20 , bar.position.y - 30, 60, 60);
			batch.draw(bar.image2, bar.bar_x - 15, bar.position.y - 30, 60, 60);
		}
         //// Draw Hand ////
		batch.draw(HR, x_right, y);
		batch.draw(HL, x_left, y);	
				
		
		batch.end();

		
		batch.setProjectionMatrix(uiCamera.combined);
		batch.begin();
		//// Show State Game ////

		// Font ///
		GlyphLayout scoreLayout = new GlyphLayout(font, "" + score, Color.WHITE, 0, Align.center, false);
		GlyphLayout highscoreLayout = new GlyphLayout(scoreFont, "Best\n" + highscore, Color.GOLD, 0, Align.left, false);

		scoreFont.draw(batch, highscoreLayout, Gdx.graphics.getWidth() - 210, Gdx.graphics.getHeight() - 10);
		font.draw(batch, scoreLayout, Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 15);
		
		/// Font Board ///
		GlyphLayout scoreBoardLayout = new GlyphLayout(fontBoard, "" + score, Color.BLACK, 0, Align.center, false);
		GlyphLayout highscoreBoardLayout = new GlyphLayout(scoreBoardFont, "" + highscore, Color.BLACK, 0, Align.left, false);
		/// Game State ///
		if (gameState == GameState.Start) {

			stateTime += Gdx.graphics.getDeltaTime();
			currentFrame = grandPa.getKeyFrame(stateTime, true);
			batch.draw(currentFrame, 330, 350, 1280/2, 720/3);
			
			stateTime_ready += Gdx.graphics.getDeltaTime();
			currentFrame_ready = animation_ready.getKeyFrame(stateTime_ready, true);
			batch.draw(currentFrame_ready, 340, 100, 1280/2, 720/3);
		}
		
//		if(gameState == GameState.Running){
//			if(countCom == countComMul){
//				batch.draw(combo, 500, 100, 1280/2, 720/3);
//			}
//		}
		
		if (gameState == GameState.GameOver) {
			/// Animation slide ///
			x_right = 160;
			x_left = 200;
			y -= Gdx.graphics.getDeltaTime() * 800;
	
			if(y_buttonF < y_textArea && pressC != true){

				y_buttonF += Gdx.graphics.getDeltaTime() * 2000;
			}
			if(y_button < y_textArea-30){
				y_button += Gdx.graphics.getDeltaTime() * 2000;
				buttonMute.position.y = y_button;
				buttonCredit.position.y = y_button;
			}
			batch.draw(text_area, Gdx.graphics.getWidth()/2-text_area.getWidth()/2, y_buttonF);
			batch.draw(credit_ending, Gdx.graphics.getWidth()/2-credit_ending.getWidth()/2, y_credit);
			
			font.draw(batch, scoreBoardLayout, Gdx.graphics.getWidth()/2-5, y_buttonF+370);
			scoreFont.draw(batch, highscoreBoardLayout, Gdx.graphics.getWidth()/2-35, y_buttonF+190);
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
			if(Gdx.input.getX() >= (x_button*0.75f) && Gdx.input.getX() <= ((x_button*0.75f)+play.getWidth()) && Gdx.input.getY() >= Gdx.graphics.getHeight()-(play.getHeight()+y_button) && Gdx.input.getY() <= Gdx.graphics.getHeight()-y_button*0.99f && button_check == 1){
				batch.draw(buttonCredit.image, buttonCredit.position.x, buttonCredit.position.y);
				batch.draw(txt_credit, x_button*0.75f, y_button+play.getHeight());
			}
			batch.draw(buttonCredit.image, buttonCredit.position.x, buttonCredit.position.y);
			if(pressC == true){
				if(y_buttonF >= -(text_area.getHeight())){
					y_buttonF -= Gdx.graphics.getDeltaTime() * 2000;
				}
				if(y_credit >= credit_ending.getHeight()/6){
					y_credit -= Gdx.graphics.getDeltaTime() * 2000;
				}
			}
			else{
				if(y_credit < Gdx.graphics.getHeight()){
					y_credit += Gdx.graphics.getDeltaTime() * 2000;
				}
			}
			/// Button play again ///
			if(Gdx.input.getX() >= (x_button) && Gdx.input.getX() <= ((x_button)+play.getWidth()) && Gdx.input.getY() >= Gdx.graphics.getHeight()-(play.getHeight()+y_button) && Gdx.input.getY() <= Gdx.graphics.getHeight()-y_button*0.99f && button_check == 2){
				batch.draw(play, x_button, y_button);
				batch.draw(txt_play_again, x_button-25, y_button+play.getHeight());
			}
			else{
				batch.draw(play, x_button, y_button);
			}
			/// Button mute and unmute ///
			if(Gdx.input.getX() >= (x_button/0.80f) && Gdx.input.getX() <= ((x_button/0.80f)+play.getWidth()) && Gdx.input.getY() >= Gdx.graphics.getHeight()-(play.getHeight()+y_button) && Gdx.input.getY() <= Gdx.graphics.getHeight()-y_button*0.99f && button_check == 3){
				batch.draw(buttonMute.image, buttonMute.position.x, buttonMute.position.y);
				batch.draw(buttonMute.txt, x_button/0.80f, y_button+play.getHeight());
			}
			batch.draw(buttonMute.image, buttonMute.position.x, buttonMute.position.y);
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
	static class Stone {
		Vector2 position = new Vector2();
		Texture image;

		public Stone (float x, float y, Texture image) {
			this.position.x = x;
			this.position.y = y;
			this.image = image;
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
	
	static class ObjectDead {
		Vector2 position = new Vector2();
		TextureRegion image;

		public ObjectDead(float x, float y, TextureRegion image) {
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

