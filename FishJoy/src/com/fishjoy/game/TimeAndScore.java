package com.fishjoy.game;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;

public class TimeAndScore {
	
	public static void createST(TextureRegion mSTTextureRegion, final TiledTextureRegion mTimeNumTextureRegion,
				final TiledTextureRegion mScoreNumTextureRegion, final Scene scene, final GamePlay game) 
	{
		Sprite st = new Sprite(0, 0, mSTTextureRegion);
		st.setSize(170, 100);
		scene.attachChild(st);
		
		// 开始3秒后创建时间、时间轴、炮弹、得分
		scene.registerUpdateHandler(new TimerHandler(3.0f, new ITimerCallback(){

			public void onTimePassed(TimerHandler arg0) {
				// TODO Auto-generated method stub
				createTime(mTimeNumTextureRegion, scene, game);
				createTotalScore(mScoreNumTextureRegion, scene);
				createTotalPower(mScoreNumTextureRegion, scene, game);
			}
		}));
	}
	
	/*
	 * 显示剩余时间
	 */
	protected static void createTime(TiledTextureRegion mTimeNumTextureRegion, final Scene scene, final GamePlay game) {
		// 创建4个时间数字精灵，每隔1秒播放一帧
		for (int i = 0; i < 4; i++) {
			AnimatedSprite time = new AnimatedSprite(20 * i + 70, 25,
					mTimeNumTextureRegion.clone());
			time.setSize(20, 20);
			GameControl.timeAnimatedSprites.add(time);
			scene.attachChild(GameControl.timeAnimatedSprites.get(i));
		}
		// 显示初始时间值
		GameControl.timeAnimatedSprites.get(1).animate(new long[]{100}, new int[]{(int) ((GameControl.GAME_TIME / 100) % 10)}, 1);
		GameControl.timeAnimatedSprites.get(2).animate(new long[]{100}, new int[]{(int) ((GameControl.GAME_TIME / 10) % 10)}, 1);
		GameControl.timeAnimatedSprites.get(3).animate(new long[]{100}, new int[]{(int) ((GameControl.GAME_TIME % 10))}, 1);
		
		// 时间监听器：过1秒后开始监听时间并更新
		scene.registerUpdateHandler(new TimerHandler(1.0f, true, new ITimerCallback() 
		{	
			public void onTimePassed(final TimerHandler pTimerHandler) 
			{
				GameControl.GAME_LAST_TIME += -1;
				if(GameControl.GAME_LAST_TIME < 0)	// 剩余时间为0：停止游戏
				{
					Log.e("提示", "时间终止："+(GameControl.GAME_LAST_TIME));
					game.stopGame();
					scene.unregisterUpdateHandler(pTimerHandler);
					for (int i = 1; i < 4; i++)
						GameControl.timeAnimatedSprites.get(i).stopAnimation();
				}
				else	// 更新时间
				{
					Log.i("剩余时间GAME_LAST_TIME：", ""+GameControl.GAME_LAST_TIME);
					GameControl.timeAnimatedSprites.get(1).animate(new long[]{100}, new int[]{(int) ((GameControl.GAME_LAST_TIME / 100) % 10)}, 1);
					GameControl.timeAnimatedSprites.get(2).animate(new long[]{100}, new int[]{(int) ((GameControl.GAME_LAST_TIME / 10) % 10)}, 1);
					GameControl.timeAnimatedSprites.get(3).animate(new long[]{100}, new int[]{(int) ((GameControl.GAME_LAST_TIME % 10))}, 1);
				}
			}
		}));

	}
	
	/*
	 * 显示总得分
	 */
	protected static void  createTotalScore(TiledTextureRegion mScoreNumTextureRegion, Scene scene) {
		for (int i = 0; i < 4; i++) {
			AnimatedSprite score = new AnimatedSprite(20 * i + 70, 56,
					mScoreNumTextureRegion.clone());
			score.setSize(20, 26);
			GameControl.scoreAnimatedSprites.add(score);
			scene.attachChild(GameControl.scoreAnimatedSprites.get(i));
		}
		
		// 时间监听器：监听当前分数并更新
		scene.registerUpdateHandler(new TimerHandler(1 / 20.0f, true, new ITimerCallback() 
		{	
			public void onTimePassed(final TimerHandler pTimerHandler) {
				GameControl.scoreAnimatedSprites.get(0).animate(new long[]{100}, new int[]{(int) ((GameControl.score / 1000) % 10)}, 1);
				GameControl.scoreAnimatedSprites.get(1).animate(new long[]{100}, new int[]{(int) ((GameControl.score / 100) % 10)}, 1);
				GameControl.scoreAnimatedSprites.get(2).animate(new long[]{100}, new int[]{(int) ((GameControl.score / 10) % 10)}, 1);
				GameControl.scoreAnimatedSprites.get(3).animate(new long[]{100}, new int[]{(int) ((GameControl.score % 10))}, 1);
			}
		}));
	}	
	
	/*
	 * 显示子弹数
	 */
	protected static void  createTotalPower(TiledTextureRegion mPowerNumTextureRegion, final Scene scene, final GamePlay game) {
		// 创建4个子弹数字精灵
		for (int i = 4; i < 8; i++) {
			AnimatedSprite power = new AnimatedSprite(GameControl.CAMERA_WIDTH / 2 + 50 + 20*i, GameControl.CAMERA_HEIGHT -GameControl.NUM_HEIGHT,
					mPowerNumTextureRegion.clone());
			power.setSize(20, 26);
			GameControl.scoreAnimatedSprites.add(power);
			scene.attachChild(GameControl.scoreAnimatedSprites.get(i));
		}
		// 时间监听器：监听当前子弹数并更新
		scene.registerUpdateHandler(new TimerHandler(1 / 20.0f, true, new ITimerCallback() 
		{	
			public void onTimePassed(final TimerHandler pTimerHandler) 
			{
				if (GameControl.A_TOTAL <= 0)	// 子弹数小于0：停止游戏			 
				{
					scene.registerUpdateHandler((new TimerHandler(1.0f, true, new ITimerCallback() {
						
						public void onTimePassed(TimerHandler arg0) {
							// TODO Auto-generated method stub
							Log.e("提示", "炮弹终止："+String.valueOf(GameControl.A_TOTAL));
							GameControl.scoreAnimatedSprites.get(4).animate(new long[]{100}, new int[]{(int) 0}, 1);
							GameControl.scoreAnimatedSprites.get(5).animate(new long[]{100}, new int[]{(int) 0}, 1);
							GameControl.scoreAnimatedSprites.get(6).animate(new long[]{100}, new int[]{(int) 0}, 1);
							GameControl.scoreAnimatedSprites.get(7).animate(new long[]{100}, new int[]{(int) 0}, 1);
						
							game.stopGame();
							scene.unregisterUpdateHandler(pTimerHandler);
							for (int j = 0; j < 4; j++)
								GameControl.scoreAnimatedSprites.get(j).stopAnimation();
						}
					} )));	
				}
				else	// 显示炮弹数量
				{
					GameControl.scoreAnimatedSprites.get(4).animate(new long[]{100}, new int[]{(int) ((GameControl.A_TOTAL / 1000) % 10)}, 1);
					GameControl.scoreAnimatedSprites.get(5).animate(new long[]{100}, new int[]{(int) ((GameControl.A_TOTAL / 100) % 10)}, 1);
					GameControl.scoreAnimatedSprites.get(6).animate(new long[]{100}, new int[]{(int) ((GameControl.A_TOTAL / 10) % 10)}, 1);
					GameControl.scoreAnimatedSprites.get(7).animate(new long[]{100}, new int[]{(int) ((GameControl.A_TOTAL % 10))}, 1);
				}
			}
		}));
	}
}
