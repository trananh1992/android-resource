package com.fishjoy.Entity;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.modifier.PathModifier;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.fishjoy.game.GameControl;

public class Net extends AnimatedSprite{

	private int type;
	
	protected PhysicsHandler mPhysicsHandler;
	PathModifier pModifier;
	
	public Net(float pX, float pY, int type, TiledTextureRegion pTiledTextureRegion) {
		super(pX, pY, pTiledTextureRegion);
		// TODO Auto-generated constructor stub
		this.type = type;
		setSize(50 * GameControl.SCALEX + 5 * type, 50 * GameControl.SCALEY + 5 * type);
	}
	
	public int getType() {
		return type;
	}
}
