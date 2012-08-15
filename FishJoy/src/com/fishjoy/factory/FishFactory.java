package com.fishjoy.factory;

import java.util.ArrayList;
import java.util.Random;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;

import com.fishjoy.Entity.Fish;
import com.fishjoy.game.GameControl;

/**
 * FishFactory:生成鱼的各种游动路径
 *
 */

public class FishFactory{

	private static FishFactory singleInstance = null;	// 单实例对象
	private final Random rand = new Random();			// 随机数对象
	private static Engine mEngine;
	private int kind = GameControl.NormalFishNum;
	
	// 获取工厂单例对象
	public static FishFactory getSingleInstance()
	{
		if(singleInstance == null)
			singleInstance = new FishFactory();
		return singleInstance;
	}
	
	public static void setEngine(Engine e)
	{
		mEngine = e;
	}
	
	// 生成 初始 路径
	public void createInitialPath(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion, int gamepattern)
	{
		//gamepattern = 4;
		switch(gamepattern)
		{
		
		case 1:					// 简单模式
			createDiamondPath(mScene, movingFish, FishRegion);
			break;
		case 2:					// 普通模式
			createBridgePath(mScene, movingFish, FishRegion);
			break;
		case 3:					// 困难模式
			String str = "FISH JOY";
			createStringPath(mScene, movingFish, FishRegion, str);
			break;
		}
	}
	
	// 创建单个鱼:大鲨鱼或者是道具鱼
	public void createSingleFish(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion, int Id)
	{
		Fish fish = new Fish(Id, FishRegion.get(Id).clone());
		// 设置方位的同时也设置了初始坐标
		int randDir = Math.abs(rand.nextInt()) % 3;
		fish.setDirection(GameControl.fishDir[randDir]);
		fish.setCurvePath(randDir);
		// 设置鱼的动作属性后的统一处理
		fish.animate(new long[]{200,200,200,200,200,200,200,200,200,200,200,200}, 
				new int[]{0,1,2,3,4,5,6,5,4,3,2,1}, 1000);
		movingFish.add(fish);
		mScene.getChild(1).attachChild(fish);
	}
	
	// 创建初始路径：字符路径
	public void createStringPath(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion, String str)
	{
		for(int i = 0; i < GameControl.fishJoy2.length; i++)
		{
			// 创建动画精灵
			Fish fish = new Fish(1, (TiledTextureRegion) FishRegion.get(1).clone());
			fish.setDirection("Right");
			fish.setp_Y(GameControl.CAMERA_HEIGHT/8 + (float)GameControl.fishJoy2[i][1]);
			fish.setp_X(GameControl.CAMERA_WIDTH + (float)GameControl.fishJoy2[i][0]);
			fish.setLinePath(1);
			
			fish.animate(new long[]{200,200,200,200,200,200,200,200,200,200,200,200}, 
					new int[]{0,1,2,3,4,5,6,5,4,3,2,1}, 1000);
			mScene.getChild(1).attachChild(fish);
			movingFish.add(fish);
		}
	}
	
	// 创建初始路径：桥路径
	public void createBridgePath(final Scene mScene, final ArrayList<Fish> movingFish, 
			final ArrayList<TiledTextureRegion> FishRegion)
	{
		// 每隔0.05秒在上下左右四个方位创建一条鱼，总共创建400条
		mScene.registerUpdateHandler(new TimerHandler(0.5f, true, new ITimerCallback() 
		{
			private int num = 100;
			
			public void onTimePassed(TimerHandler pTimerHandler) 
			{
				int Id= 0;
				float bridge_Y = 0;
				
				for(int s = 0; s <2; s++)
				{
					Id = (1-s)*3;
					bridge_Y = GameControl.CAMERA_HEIGHT/16*(1+s*10);
					
					for(int t = 0; t < 2; t++)
					{
							Fish fish = new Fish(Id, FishRegion.get(Id).clone());
							fish.setDirection(GameControl.fishDir[t]);
							fish.setp_Y(bridge_Y);
							fish.setBridgePath(s,true);					
							fish.animate(new long[]{200,200,200,200,200,200,200,200,200,200,200,200}, 
									new int[]{0,1,2,3,4,5,6,5,4,3,2,1}, 1000);
							movingFish.add(fish);
							mScene.getChild(1).attachChild(fish);	
					}
				}
				num -= 4;
				if(num <= 0)
					mScene.unregisterUpdateHandler(pTimerHandler);
			}
		}));
		
	}
	
	// 创建初始路径：钻石路径
	public void createDiamondPath(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion)
	{
		GameControl.fishSpeed[3] = 120;
		GameControl.fishSpeed[6] = 120; 
		
		for(int t = 0; t < 5; t++)
		{
			float baseY = GameControl.CAMERA_HEIGHT / 4;
			float baseX = (float) (GameControl.CAMERA_WIDTH + t*GameControl.fishRegion[0][0] * 6.5);			
			Fish fish=null;	
			int Id;
			float para;
			for(int i = 0; i < 5; i++)
				for(int j = 0; j < 5; j++)			// i行j列
				{
					if(GameControl.diamond[i][j]==0)
						continue;
					Id = 3;
					para = 1.0f;
					if(i == 2 && j == 2)
					{
						Id = 7;
						para = 1.6f/2;
					}
					
					fish = new Fish(Id, FishRegion.get(Id).clone());
					fish.setDirection("Right");	
					fish.setp_X(baseX+j*para*GameControl.fishRegion[3][0]);
					fish.setp_Y(baseY+i*para*GameControl.fishRegion[3][1]);
					fish.setLinePath(1);							
					fish.animate(new long[]{200,200,200,200,200,200,200,200,200,200,200,200}, 
							new int[]{0,1,2,3,4,5,6,5,4,3,2,1}, 1000);
					movingFish.add(fish);
					// 将生成的鱼都附加到场景第一层——鱼层
					mScene.getChild(1).attachChild(fish);
				}
		}
		GameControl.fishSpeed[3] = 90;
		GameControl.fishSpeed[6] = 80; 
	}
	
	// 创建鱼群
	public void createFishGroup(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion)
	{
		int randDir = Math.abs(rand.nextInt()) % 2;
		float group_Y = Math.abs(rand.nextFloat())*(GameControl.CAMERA_HEIGHT) + GameControl.CAMERA_HEIGHT/8;
		int num = Math.abs(rand.nextInt()) % 5 +2;
		int Id = Math.abs(rand.nextInt()) % (kind-1);
		for(int i = 0; i < num; i++)
		{
			Fish fish = new Fish(Id, FishRegion.get(Id).clone());		
			fish.setDirection(GameControl.fishDir[randDir]);					// 随机值：鱼的游动方向
			fish.setGroupPath(i, group_Y);									// 这个设置放在最后，它能真正改变鱼的位置和路线
			
			fish.animate(new long[]{200,200,200,200,200,200,200,200,200,200,200,200}, 
					new int[]{0,1,2,3,4,5,6,5,4,3,2,1}, 1000);
			movingFish.add(fish);
			mScene.getChild(1).attachChild(fish);
		}
	}	
	

	// 随机创建鱼
	
	/* 游戏开始后的随机游动序列 */
	public void createRandomFish(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion)
	{
		if(movingFish.size() < GameControl.MaxFishNum)
		{
			for(int i = 0; i < GameControl.MaxFishNum-movingFish.size(); i++)		// 补充场景中的鱼
			{
				// 路线类型
				int type = Math.abs(rand.nextInt()) % 3;
				
				// 事先生成一条鱼
				int Id = Math.abs(rand.nextInt()) % (kind-1);		
				Fish fish = new Fish(Id, FishRegion.get(Id).clone());
				// 设置方位的同时也设置了初始坐标
				int randDir = Math.abs(rand.nextInt()) % 4;
				fish.setDirection(GameControl.fishDir[randDir]);
				
				switch(type)
				{
				case 0:
					fish.setLinePath(randDir);
					break;
				case 1:
					fish.setCurvePath(randDir);
					break;
				case 2:
					randDir = Math.abs(rand.nextInt()) % 2;
					fish.setDirection(GameControl.fishDir[randDir]);
					fish.setCirclePath(randDir);
					break;
				}
				
				// 设置鱼的动作属性后的统一处理
				fish.animate(new long[]{200,200,200,200,200,200,200,200,200,200,200,200}, 
						new int[]{0,1,2,3,4,5,6,5,4,3,2,1}, 1000);
				movingFish.add(fish);
				mScene.getChild(1).attachChild(fish);
			}
		}
	}
	
	public void createRainbowPath(final Scene mScene, final ArrayList<Fish> movingFish, 
			final ArrayList<TiledTextureRegion> FishRegion)
	{
		// 每隔0.05秒在上下左右四个方位创建一条鱼，总共创建400条
		mScene.registerUpdateHandler(new TimerHandler(0.5f, true, new ITimerCallback() 
		{
			private int num = 200;
			
			public void onTimePassed(TimerHandler pTimerHandler) 
			{
				int Id= Math.abs(rand.nextInt()) % 5;
				float bridge_Y = GameControl.CAMERA_HEIGHT/16;
					
					for(int t = 0; t < 2; t++)
					{
							Fish fish = new Fish(Id, FishRegion.get(Id).clone());
							fish.setDirection(GameControl.fishDir[t]);
							fish.setp_Y(GameControl.CAMERA_HEIGHT/16*(1+14*t));
							fish.setBridgePath(t,false);					
							fish.animate(new long[]{200,200,200,200,200,200,200,200,200,200,200,200}, 
									new int[]{0,1,2,3,4,5,6,5,4,3,2,1}, 1000);
							movingFish.add(fish);
							mScene.getChild(1).attachChild(fish);	
					}
				num -= 4;
				if(num <= 0)
					mScene.unregisterUpdateHandler(pTimerHandler);
			}
		}));
		
	}
	
	// 在捕获的鱼的位置生成一条挣扎的鱼
	public void  createCapturedFish(Fish fishx, final Scene mScene, ArrayList<TiledTextureRegion> FishRegion)
	{
		int type1 = fishx.getType();
		int type2 = type1 + GameControl.NormalFishNum + GameControl.MagicFishNum;
		float x = fishx.getX()+(GameControl.fishRegion[type1][0]-GameControl.fishRegion[type2][0])/2;
		float y = fishx.getY()+(GameControl.fishRegion[type1][1]-GameControl.fishRegion[type2][1])/2;
		float r = fishx.getRotation();
		final Fish fish = new Fish(x,y, FishRegion.get(type2).clone());
		fish.setRotation(r);
		mScene.attachChild(fish);
		fish.animate(new long[]{200,200}, new int[]{0,1}, 1000);
		//fish.setSize(36 * GameControl.SCALEX, 18 * GameControl.SCALEY);
		
		// 过2秒后将挣扎的鱼清除
		mScene.registerUpdateHandler(new TimerHandler(2.0f, new ITimerCallback() 
		{	
			public void onTimePassed(final TimerHandler pTimerHandler) {
				mScene.unregisterUpdateHandler(pTimerHandler);
				mScene.detachChild(fish);
			}
		}));
	}
	
}

