package com.fishjoy.game;

import java.util.ArrayList;

import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.modifier.PathModifier;
import org.anddev.andengine.entity.modifier.PathModifier.Path;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.fishjoy.Entity.Fish;

public class CatchFish {
	
	/*
	 * 生成捕获到鱼后的分数
	 */
	protected static void createScore(Fish fishx, final Scene scene, 
			ArrayList<TiledTextureRegion> mTextureRegion) 
	{
		// 为普通鱼生成分数
		if(fishx.getType() <=7 )
		{
			GameControl.score += fishx.getScore();	// 记录相应的分数
			
			float x = fishx.getX();
			float y = fishx.getY() - 10;
			int type = fishx.getType();
			final AnimatedSprite score = new AnimatedSprite(x, y, mTextureRegion.get(type).clone());
			score.animate(500);
			score.setSize(30, 30);
			scene.attachChild(score);	
			// 过两秒后清除分数动画
			scene.registerUpdateHandler(
					new TimerHandler(2.0f, new ITimerCallback() {
												
						public void onTimePassed(final TimerHandler pTimerHandler) {
							scene.unregisterUpdateHandler(pTimerHandler);
							scene.detachChild(score);
						}
					}));
		}
	}

	/*
	 * 生成获得金币/道具的动画
	 */
	protected static void createGold(Fish fishx, final Scene scene, ArrayList<TiledTextureRegion> mTextureRegion
			, GamePlay game) {
		
		if (GameControl.musiceffect) {	
			GameControl.coinMusic.play();
		}
		
		int type = fishx.getType();
		int index = 0;
		
		float x = fishx.getX();
		float y = fishx.getY();
		
		float xx = 74;	// 默认坐标（金币区的位置）
		float yy = 75;
		
		float size_x = 45;
		float size_y = 45;
		
		switch(type){
		case 8:			// 道具鱼：加炮弹
			index = 1;
			GameControl.A_TOTAL += 50;			
			xx = GameControl.CAMERA_WIDTH / 2 + 50 + 20*5;			 // 炮弹区的位置
			yy = GameControl.CAMERA_HEIGHT -GameControl.NUM_HEIGHT;
			size_x = 25;
			size_y = 25;
			//显示游戏文本区
			game.displayText("恭喜获得炮弹");
			//TextSE ttSe = new TextSE();
			//ttSe.createTexts(mFont, mMainScene, GamePlay.this, "Start!");
			break;
		case 9:			// 道具鱼：加时间
			index = 2;
			GameControl.GAME_LAST_TIME += GameControl.AWARD_TIME[GameControl.GAMEMODE];								
			xx = 100; 	// 时间区的位置
			yy = 25;
			game.displayText("恭喜获得时间");
			break;
		default:
			size_x = size_y = 25;
		}
		final AnimatedSprite gold = new AnimatedSprite(x, y, mTextureRegion.get(index));
		Path path = new Path(2).to(x, y).to(xx, yy);
		PathModifier pModifier = new PathModifier(2, path);
		gold.registerEntityModifier(pModifier);
		gold.setSize(size_x, size_y);
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




