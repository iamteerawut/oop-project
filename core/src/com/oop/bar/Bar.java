package com.oop.bar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Bar {
	
	BarProject game;
	TextureRegion bar;
	Array<Barja> bars = new Array<Barja>();
	
	public Bar () {
		bar = new TextureRegion(new Texture("bar.png"));
		
		for(int i = 0; i < 12; i++) {
			bars.add(new Barja(200 + i * 100, 450, bar));
		}
	}
	
	static class Barja {
		Vector2 position = new Vector2();
		TextureRegion image;

		public Barja(float x, float y, TextureRegion image) {
			this.position.x = x;
			this.position.y = y;
			this.image = image;
		}
		
	}
	
	public void update() {
		for(Barja bar: bars) {
			bar.position.x += 12 * 100;
		}
	}
	
	public void render(SpriteBatch batch) {
		for(Barja bar: bars) {
			batch.draw(bar.image, bar.position.x, bar.position.y);
		}
	}
}
