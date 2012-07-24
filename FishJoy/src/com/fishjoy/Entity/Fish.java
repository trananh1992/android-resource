package com.fishjoy.Entity;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class Fish extends AnimatedSprite{
	
	private int score;
	
	
	public Fish(float pX, float pY, TiledTextureRegion pTiledTextureRegion, Engine mEngine) {
		super(pX, pY, pTiledTextureRegion);
		// TODO Auto-generated constructor stub
	}

	public void swim() {
		
	}
	
	public int getScore() {
		return score;
	}
}
