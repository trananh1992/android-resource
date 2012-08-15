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
		
		// ��ʼ3��󴴽�ʱ�䡢ʱ���ᡢ�ڵ����÷�
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
	 * ��ʾʣ��ʱ��
	 */
	protected static void createTime(TiledTextureRegion mTimeNumTextureRegion, final Scene scene, final GamePlay game) {
		// ����4��ʱ�����־��飬ÿ��1�벥��һ֡
		for (int i = 0; i < 4; i++) {
			AnimatedSprite time = new AnimatedSprite(20 * i + 70, 25,
					mTimeNumTextureRegion.clone());
			time.setSize(20, 20);
			GameControl.timeAnimatedSprites.add(time);
			scene.attachChild(GameControl.timeAnimatedSprites.get(i));
		}
		// ��ʾ��ʼʱ��ֵ
		GameControl.timeAnimatedSprites.get(1).animate(new long[]{100}, new int[]{(int) ((GameControl.GAME_TIME / 100) % 10)}, 1);
		GameControl.timeAnimatedSprites.get(2).animate(new long[]{100}, new int[]{(int) ((GameControl.GAME_TIME / 10) % 10)}, 1);
		GameControl.timeAnimatedSprites.get(3).animate(new long[]{100}, new int[]{(int) ((GameControl.GAME_TIME % 10))}, 1);
		
		// ʱ�����������1���ʼ����ʱ�䲢����
		scene.registerUpdateHandler(new TimerHandler(1.0f, true, new ITimerCallback() 
		{	
			public void onTimePassed(final TimerHandler pTimerHandler) 
			{
				GameControl.GAME_LAST_TIME += -1;
				if(GameControl.GAME_LAST_TIME < 0)	// ʣ��ʱ��Ϊ0��ֹͣ��Ϸ
				{
					Log.e("��ʾ", "ʱ����ֹ��"+(GameControl.GAME_LAST_TIME));
					game.stopGame();
					scene.unregisterUpdateHandler(pTimerHandler);
					for (int i = 1; i < 4; i++)
						GameControl.timeAnimatedSprites.get(i).stopAnimation();
				}
				else	// ����ʱ��
				{
					Log.i("ʣ��ʱ��GAME_LAST_TIME��", ""+GameControl.GAME_LAST_TIME);
					GameControl.timeAnimatedSprites.get(1).animate(new long[]{100}, new int[]{(int) ((GameControl.GAME_LAST_TIME / 100) % 10)}, 1);
					GameControl.timeAnimatedSprites.get(2).animate(new long[]{100}, new int[]{(int) ((GameControl.GAME_LAST_TIME / 10) % 10)}, 1);
					GameControl.timeAnimatedSprites.get(3).animate(new long[]{100}, new int[]{(int) ((GameControl.GAME_LAST_TIME % 10))}, 1);
				}
			}
		}));

	}
	
	/*
	 * ��ʾ�ܵ÷�
	 */
	protected static void  createTotalScore(TiledTextureRegion mScoreNumTextureRegion, Scene scene) {
		for (int i = 0; i < 4; i++) {
			AnimatedSprite score = new AnimatedSprite(20 * i + 70, 56,
					mScoreNumTextureRegion.clone());
			score.setSize(20, 26);
			GameControl.scoreAnimatedSprites.add(score);
			scene.attachChild(GameControl.scoreAnimatedSprites.get(i));
		}
		
		// ʱ���������������ǰ����������
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
	 * ��ʾ�ӵ���
	 */
	protected static void  createTotalPower(TiledTextureRegion mPowerNumTextureRegion, final Scene scene, final GamePlay game) {
		// ����4���ӵ����־���
		for (int i = 4; i < 8; i++) {
			AnimatedSprite power = new AnimatedSprite(GameControl.CAMERA_WIDTH / 2 + 50 + 20*i, GameControl.CAMERA_HEIGHT -GameControl.NUM_HEIGHT,
					mPowerNumTextureRegion.clone());
			power.setSize(20, 26);
			GameControl.scoreAnimatedSprites.add(power);
			scene.attachChild(GameControl.scoreAnimatedSprites.get(i));
		}
		// ʱ���������������ǰ�ӵ���������
		scene.registerUpdateHandler(new TimerHandler(1 / 20.0f, true, new ITimerCallback() 
		{	
			public void onTimePassed(final TimerHandler pTimerHandler) 
			{
				if (GameControl.A_TOTAL <= 0)	// �ӵ���С��0��ֹͣ��Ϸ			 
				{
					scene.registerUpdateHandler((new TimerHandler(1.0f, true, new ITimerCallback() {
						
						public void onTimePassed(TimerHandler arg0) {
							// TODO Auto-generated method stub
							Log.e("��ʾ", "�ڵ���ֹ��"+String.valueOf(GameControl.A_TOTAL));
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
				else	// ��ʾ�ڵ�����
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
