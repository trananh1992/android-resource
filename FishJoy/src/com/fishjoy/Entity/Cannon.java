package com.fishjoy.Entity;

import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class Cannon extends AnimatedSprite{
	
	private int cannonLevel = 1;
	
	public Cannon(float pX, float pY, float pTileWidth, float pTileHeight,
			TiledTextureRegion pTiledTextureRegion) {
		super(pX, pY, pTileWidth, pTileHeight, pTiledTextureRegion);
		// TODO Auto-generated constructor stub
	}

	public void rotate(float angle) {
		registerEntityModifier(new RotationModifier((float) 0.2, getRotation(), angle));
	}
}
