package com.fishjoy.factory;

import java.util.ArrayList;
import java.util.Random;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;

import com.fishjoy.Entity.Fish;
import com.fishjoy.game.CharPathData;
import com.fishjoy.game.GameControl;

/**
 * FishFactory:生成鱼的各种游动路径
 *
 */

public class FishFactory{

	private static FishFactory singleInstance = null;	// 单实例对象
	private final Random rand = new Random();			// 随机数对象
	private static Engine mEngine;
	
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
		switch(gamepattern)
		{
		case 1:					// 简单模式
			String str = "FISH JOY";
			createStringPath(mScene, movingFish, FishRegion, str);
			break;
		case 2:					// 普通模式
			createDiamondPath(mScene, movingFish, FishRegion);
			break;
		case 3:					// 困难模式
			createBridgePath(mScene, movingFish, FishRegion);
			break;
		}
	}
	
	
	public void createStringPath(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion, String str)
	{
		float nextBaseX = GameControl.CAMERA_WIDTH;
		for(int i = 0; i < str.length(); i++)
		{
			char ch = str.charAt(i);
			if(ch == ' ')
			{
				Log.i("提示", "遇到空格");
				nextBaseX += 100;
				continue;
			}
				
			CharPathData data = new CharPathData();
			int[][] c = data.get_Matrix(String.valueOf(ch));
			int column=c.length;
			int row=c[0].length;
			
			for(int t = 0; t < column; t++)
			{
				for(int j=0; j < row; j++)
				{
					if(c[t][j]!=0)
					{
						// 创建动画精灵
						Fish fish = new Fish(0, (TiledTextureRegion) FishRegion.get(0).deepCopy());
						fish.setDirection("Right");
						
						fish.setp_Y(GameControl.CAMERA_HEIGHT/4 + t*30);
						fish.setp_X(nextBaseX + j*60);
						
						fish.setLinePath();
						
						fish.animate(100);
						mScene.getChild(1).attachChild(fish);
						movingFish.add(fish);
					}
				}
			}	
			nextBaseX += row * 60;
		}
	}
	
	
	public void createBridgePath(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion)
	{
		for(int t = 0; t < 2; t++)
		{
				int Id = Math.abs(rand.nextInt()) % 5;
				Id = 0;
				float bridge_Y = Math.abs(rand.nextFloat())*(GameControl.CAMERA_HEIGHT/2);
				for(int i = 0; i < 10; i++)
				{
					Fish fish = new Fish(Id, FishRegion.get(Id).deepCopy());
					fish.setDirection(GameControl.fishDir[t]);
					fish.setp_Y(bridge_Y+GameControl.CAMERA_HEIGHT/2);
					fish.setBridgePath(i);					
					
					fish.animate(100);
					movingFish.add(fish);
					mScene.getChild(1).attachChild(fish);	
				}
		}
	}
	
	
	public void createDiamondPath(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion)
	{
		for(int t = 0; t < 5; t++)
		{
			float baseY = GameControl.CAMERA_HEIGHT / 4;
			float baseX = GameControl.CAMERA_WIDTH + t*GameControl.fishRegion[0][0]*5;
			for(int i = 0; i < 5; i++)
				for(int j = 0; j < 5; j++)			// i行j列
				{
					if(GameControl.diamond[i][j] != 0)
					{
						Fish fish = new Fish(0, FishRegion.get(0).deepCopy());
						fish.setDirection("Right");	
						fish.setp_X(baseX+j*GameControl.fishRegion[0][0]);
						fish.setp_Y(baseY+i*GameControl.fishRegion[0][1]);
						fish.setLinePath();							
						fish.animate(100);
						movingFish.add(fish);
						// 将生成的鱼都附加到场景第一层——鱼层
						mScene.getChild(1).attachChild(fish);	
					}
				}
		}
	}
	
	
	public void createCirclePath(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion)
	{		
		for(int i = 0; i < 3; i++)
		{	// 用三条鱼构建鱼群，分布在上中下
			// 使用了第一种鱼,Id == 0
				Fish fish = new Fish(0, FishRegion.get(0).deepCopy());
				if(i==0)
					fish.setp_Y(70);
				else if(i==1)
					fish.setp_Y(130);
				else if(i==2)
					fish.setp_Y(190);
				
				fish.setDirection("Right");		// 设置游动方向
				fish.setCirclePath();			// 初始化路径
				
				fish.animate(100);
				//fish.setSize(55, 30);
				
				movingFish.add(fish);
				// 将生成的鱼都附加到场景第一层——鱼层
				mScene.getChild(1).attachChild(fish);	
		}		
	}
	
	
	// 创建鱼群
	public void createFishGroup(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion)
	{
		int randDir = Math.abs(rand.nextInt()) % 2;
		float group_Y = Math.abs(rand.nextFloat())*(GameControl.CAMERA_HEIGHT);
		int num = Math.abs(rand.nextInt()) % 5 +2;
		
		for(int i = 0; i < num; i++)
		{
			Fish fish = new Fish(0, FishRegion.get(0).deepCopy());		
			fish.setDirection(GameControl.fishDir[randDir]);					// 随机值：鱼的游动方向
			fish.setGroupPath(i, group_Y);									// 这个设置放在最后，它能真正改变鱼的位置和路线
			
			fish.animate(100);
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
				int type = Math.abs(rand.nextInt()) % 3;
				switch(type)
				{
				case 0:			
					createFishInLine(mScene, movingFish, FishRegion);	// 直线游动
					break;
				case 1:			
					createFishInCircle(mScene, movingFish, FishRegion);	// 圆形游动
					break;
				case 2:
					createFishInCurve(mScene, movingFish, FishRegion);	// 曲线游动
					break;
				}
			}
		}
	}

	// 随机生成一条圆形游动的鱼
	public void createFishInCircle(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion)
	{
		int Id = Math.abs(rand.nextInt()) % 5;
		Fish fish = new Fish(Id, FishRegion.get(Id).deepCopy());
		
		int randDir = Math.abs(rand.nextInt()) % 2;
		fish.setDirection(GameControl.fishDir[randDir]);			// 随机值：鱼的游动方向
		fish.setCirclePath();							// 初始化路径
		
		fish.animate(100);
		movingFish.add(fish);
		mScene.getChild(1).attachChild(fish);	
	}
	
	// 随机生成一条直线游动的鱼
	public void createFishInCurve(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion)
	{
		int Id = Math.abs(rand.nextInt()) % 5;
		Fish fish = new Fish(Id, FishRegion.get(Id).deepCopy());
		
		int randDir = Math.abs(rand.nextInt()) % 2;
		fish.setDirection(GameControl.fishDir[randDir]);			// 随机值：鱼的游动方向
		fish.setCurvePath();							// 这个设置放在最后，它能真正改变鱼的位置和路线
		
		fish.animate(100);
		movingFish.add(fish);
		mScene.getChild(1).attachChild(fish);	
	}
		
	// 随机生成一条直线游动的鱼
	public void createFishInLine(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion)
	{
		int Id = Math.abs(rand.nextInt()) % 5;
		Fish fish = new Fish(Id, FishRegion.get(Id).deepCopy());
		
		int randDir = Math.abs(rand.nextInt()) % 4;
		fish.setDirection(GameControl.fishDir[randDir]);		// 随机值：鱼的游动方向
		fish.setLinePath();							// 这个设置放在最后，它能真正改变鱼的位置和路线
		
		fish.animate(100);
		movingFish.add(fish);
		// 将生成的鱼都附加到场景第一层——鱼层
		mScene.getChild(1).attachChild(fish);	
	}
	
}


