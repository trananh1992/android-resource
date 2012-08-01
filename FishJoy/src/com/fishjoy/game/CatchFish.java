package com.fishjoy.game;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.modifier.PathModifier;
import org.anddev.andengine.entity.modifier.PathModifier.Path;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class CatchFish {
	
	protected static void createScore(float x, float y, final Scene scene, TiledTextureRegion mTextureRegion) {
		final AnimatedSprite score = new AnimatedSprite(x, y, mTextureRegion);
		score.animate(500);
		score.setSize(20, 20);
		scene.attachChild(score);
		scene.registerUpdateHandler(
				new TimerHandler(2.0f, new ITimerCallback() {
											
					public void onTimePassed(final TimerHandler pTimerHandler) {
						scene.unregisterUpdateHandler(pTimerHandler);
						score.detachSelf();
					}
				}));
	}

	protected static void createGold(float x, float y, final Scene scene, TiledTextureRegion mTextureRegion) {
		final AnimatedSprite gold = new AnimatedSprite(x, y, mTextureRegion);
		Path path = new Path(2).to(x, y).to(74, 75);
		PathModifier pModifier = new PathModifier(2, path);
		gold.registerEntityModifier(pModifier);
		gold.setSize(25, 25);
		scene.attachChild(gold);
		gold.animate(333);
		scene.registerUpdateHandler(new TimerHandler(2.0f, new ITimerCallback() {
			
			public void onTimePassed(TimerHandler arg0) {
				// TODO Auto-generated method stub
				scene.detachChild(gold);
			}
		}));
	}
}
