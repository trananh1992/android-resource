package com.test;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;



public class Fish extends AnimatedSprite{
	
	protected PhysicsHandler mPhysicsHandler;
	protected Engine mEngine;
	
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;
	private static final float DEMO_VELOCITY = 100.0f;

	public Fish(float pX, float pY, TiledTextureRegion pTiledTextureRegion, Engine mEngine) {
		super(pX, pY, pTiledTextureRegion);
		// TODO Auto-generated constructor stub
//		this.mEngine = mEngine;
	}

	public PhysicsHandler getPhysicsHandler() {
		return this.mPhysicsHandler;
	}
	
	@Override
	public void registerUpdateHandler(IUpdateHandler pUpdateHandler) {
		// TODO Auto-generated method stub
		this.mPhysicsHandler = (PhysicsHandler) pUpdateHandler;
		super.registerUpdateHandler(pUpdateHandler);
	}

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {

		if (this.mEngine.getSecondsElapsedTotal() < 1){
			if(!border()) 
				this.mPhysicsHandler.setVelocity(100, 0);
		}
		else if(this.mEngine.getSecondsElapsedTotal() < 2){
			if(!border())
				this.mPhysicsHandler.setVelocity(-100, -100);
		}
		else if(this.mEngine.getSecondsElapsedTotal() < 4){
			if(!border())
				this.mPhysicsHandler.setVelocity(-100, 100);
		}
		else if(this.mEngine.getSecondsElapsedTotal() < 6){
			if(!border())
				this.mPhysicsHandler.setVelocity(100, 0);
		}
		else {
			border();
		}
		
		super.onManagedUpdate(pSecondsElapsed);
	}
	
	protected Boolean border() {
		if(this.mX <= 0) {
			this.mPhysicsHandler.setVelocityX(DEMO_VELOCITY);
			return true;
		} else if(this.mX + this.getWidth() >= CAMERA_WIDTH) {
			this.mPhysicsHandler.setVelocityX(-DEMO_VELOCITY);
			return true;
		}
		if(this.mY <= 0) {
			this.mPhysicsHandler.setVelocityY(DEMO_VELOCITY);
			return true;
		} else if(this.mY + this.getHeight() >= CAMERA_HEIGHT) {
			this.mPhysicsHandler.setVelocityY(-DEMO_VELOCITY);
			return true;
		}
		return false;
	}
}
