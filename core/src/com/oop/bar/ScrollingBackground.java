package com.oop.bar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScrollingBackground {
	
	public static final int DEFAULT_SPEED = 250;
	public static final int ACCELERATION = 40;
	public static final int GOAL_REACH_ACCELERATION = 200;
	
	Texture bg, bg2;
	float x1, x2;
	int speed;
	int goalSpeed;
	float imageScale;
	boolean speedPause;
	
	public ScrollingBackground() {
		
		bg = new Texture("bg2.png");
		bg2 = new Texture("bg3.png");
		
		x1 = 0;
		x2 = bg.getWidth();
		
		speed = 0;
		goalSpeed = DEFAULT_SPEED;
		imageScale = BarProject.WIDTH / bg.getWidth();
		speedPause = false;
	}
	
	public void updateAndRender(float deltaTime, SpriteBatch batch) {
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
		
		if (speedPause) {
			speed = 0;
		}
		
		x1 -= speed * deltaTime;
		x2 -= speed * deltaTime;
		
		if (x1 + bg.getWidth() * imageScale <= 0) {
			x1 = x2 + bg.getWidth() * imageScale;
		}
		if (x2 + bg.getWidth() * imageScale <= 0) {
			x2 = x1 + bg.getWidth();
		}
		
		batch.draw(bg, x1, 0, BarProject.WIDTH, BarProject.HEIGHT);
		batch.draw(bg2, x2, 0, BarProject.WIDTH, BarProject.HEIGHT);
	}
	public void setPause(boolean speedPause) {
		this.speedPause = speedPause;
	}
}
