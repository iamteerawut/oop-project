package com.oop.bar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScrollingBackground {
	
	public static final int DEFAULT_SPEED = 250;
	public static final int ACCELERATION = 40;
	public static final int GOAL_REACH_ACCELERATION = 200;
	
	Texture background, background2;
	float x1, x2;
	int speed;
	int goalSpeed;
	float imageScale;
	boolean speedPause;
	
	public ScrollingBackground() {
		
		background = new Texture("background.png");
		background2 = new Texture("background.png");
		
		x1 = 0;
		x2 = background.getWidth();
		speed = 10;
		goalSpeed = DEFAULT_SPEED;
		imageScale = 1;
		speedPause = false;
	}
	
	public void updateAndRender(float deltaTime, SpriteBatch batch) {
		batch.begin();
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
//		if (speed < goalSpeed) {
//			speed += GOAL_REACH_ACCELERATION * deltaTime;
//			if (speed > goalSpeed) {
//				speed = goalSpeed;
//			}
//			else if (speed < goalSpeed) {
//				speed -= GOAL_REACH_ACCELERATION * deltaTime;
//				if (speed < goalSpeed) {
//					speed = goalSpeed;
//				}
//			}
//		}
//		
//		if (speedPause) {
//			speed = 0;
//		}
//		
//		x1 -= speed * deltaTime;
//		x2 -= speed * deltaTime;
//		
//		if (x1 + bg.getWidth() * imageScale <= 0) {
//			x1 = x2 + bg.getWidth() * imageScale;
//		}
//		if (x2 + bg.getWidth() * imageScale <= 0) {
//			x2 = x1 + bg.getWidth();
//		}
//		
//		batch.draw(bg, x1, 0, BarProject.WIDTH, BarProject.HEIGHT);
//		batch.draw(bg2, x2, 0, BarProject.WIDTH, BarProject.HEIGHT);
//		batch.end();
	}
	public void setPause(boolean speedPause) {
		this.speedPause = speedPause;
	}
}
