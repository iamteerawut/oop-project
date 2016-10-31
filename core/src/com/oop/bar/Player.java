package com.oop.bar;

import com.badlogic.gdx.Gdx;

public class Player {
	float val_x_right = 0;
	float val_x_left = 50;
	
	public void move(float delta, float count){
		if(count%2 == 0){
			if(Gdx.input.isTouched()){
				val_x_right += delta;
				val_x_left -= delta;
				System.out.print(val_x_right + "X");
				System.out.println(val_x_left + "Y");
				
			}
		}
		else if(count%2 != 0){
			if(Gdx.input.isTouched()){
				val_x_right -= delta;
				val_x_left += delta;
				count = 0;
				System.out.print(val_x_right);
				System.out.println(val_x_left);
			}
		}
		
		
	}
}
