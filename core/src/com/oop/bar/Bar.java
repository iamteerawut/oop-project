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
		
		bars.clear();
		for(int i = 0; i < 10; i++) {
			bars.add(new Barja(300 + i * 100, 450, bar));
		}
	}
	
	public void update() {
		for(Barja bar: bars) {
			bar.position.x += 10 * 100;
		}
	}
	
	public void render(SpriteBatch batch) {
		for(Barja b: bars) {
			game.batch.draw(b.image, b.position.x, b.position.y);
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
}
