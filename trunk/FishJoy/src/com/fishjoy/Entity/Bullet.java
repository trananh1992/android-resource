package com.fishjoy.Entity;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class Bullet extends AnimatedSprite{

	protected int type;
	
	public Bullet(float pX, float pY, int type, TiledTextureRegion pTiledTextureRegion) {
		super(pX, pY, pTiledTextureRegion);
		// TODO Auto-generated constructor stub
		this.type = type;
	}

	public Net generateNet(TiledTextureRegion netTextureRegion, final Scene scene) {
		// TODO Auto-generated method stub
		final Net net = new Net(getX(), getY() - getHeight(), this.type, 
				netTextureRegion.clone());
		net.animate(new long[]{100,100,100,100,100000000});
		scene.registerUpdateHandler(new TimerHandler(2.0f, new ITimerCallback() {
			
			public void onTimePassed(final TimerHandler pTimerHandler) {
				scene.unregisterUpdateHandler(pTimerHandler);
				scene.detachChild(net);
			}
		}));
		
		scene.attachChild(net);
		return net;
	}
	
	public int getType() {
		return type;
	}
}
