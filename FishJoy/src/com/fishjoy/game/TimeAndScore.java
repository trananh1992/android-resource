package com.fishjoy.game;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class TimeAndScore {
	public static void createST(TextureRegion mSTTextureRegion, TiledTextureRegion mTimeNumTextureRegion,
				TiledTextureRegion mScoreNumTextureRegion, Scene scene) {
		Sprite st = new Sprite(0, 0, mSTTextureRegion);
		st.setSize(170, 100);
		scene.attachChild(st);
		createTime(mTimeNumTextureRegion, scene);
		createTotalScore(mScoreNumTextureRegion, scene);
	}
	
	protected static void createTime(TiledTextureRegion mTimeNumTextureRegion, Scene scene) {
		for (int i = 0; i < 4; i++) {
			AnimatedSprite time = new AnimatedSprite(20 * i + 70, 25,
					mTimeNumTextureRegion.clone());
			time.setSize(20, 20);
			GameControl.timeAnimatedSprites.add(time);
			scene.attachChild(GameControl.timeAnimatedSprites.get(i));
		}
		GameControl.timeAnimatedSprites.get(1).animate(new long[]{100000, 100000, 100000}, new int[]{2, 1, 0}, 1);
		GameControl.timeAnimatedSprites.get(2).animate(new long[]{10000, 10000, 10000, 10000, 10000, 
				10000, 10000, 10000, 10000, 10000}, new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1, 0}, 3);
		GameControl.timeAnimatedSprites.get(3).animate(new long[]{1000, 1000, 1000, 1000, 1000, 
				1000, 1000, 1000, 1000, 1000}, new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1, 0}, 30);
	}
	
	protected static void  createTotalScore(TiledTextureRegion mScoreNumTextureRegion, Scene scene) {
		for (int i = 0; i < 4; i++) {
			AnimatedSprite score = new AnimatedSprite(20 * i + 70, 56,
					mScoreNumTextureRegion.clone());
			score.setSize(20, 26);
			GameControl.scoreAnimatedSprites.add(score);
			scene.attachChild(GameControl.scoreAnimatedSprites.get(i));
		}
		
		scene.registerUpdateHandler(new TimerHandler(1 / 20.0f, true, new ITimerCallback() {
			
			public void onTimePassed(final TimerHandler pTimerHandler) {
				GameControl.scoreAnimatedSprites.get(0).animate(new long[]{100}, new int[]{(int) ((GameControl.score / 1000) % 10)}, 1);
				GameControl.scoreAnimatedSprites.get(1).animate(new long[]{100}, new int[]{(int) ((GameControl.score / 100) % 10)}, 1);
				GameControl.scoreAnimatedSprites.get(2).animate(new long[]{100}, new int[]{(int) ((GameControl.score / 10) % 10)}, 1);
				GameControl.scoreAnimatedSprites.get(3).animate(new long[]{100}, new int[]{(int) ((GameControl.score % 10))}, 1);
			}
		}));
	}
}
