package com.fishjoy.controller;

import java.util.ArrayList;
import java.util.Random;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;

import com.fishjoy.activity.Fish;
import com.fishjoy.model.GameParas;

/**
 * FishFactory:生成鱼的各种游动路径
 *
 */

public class FishFactory implements GameParas{

	private static FishFactory singleInstance = null;	// 单实例对象
	private final Random rand = new Random();			// 随机数对象
	
	// 获取工厂单例对象
	public static FishFactory getSingleInstance()
	{
		if(singleInstance == null)
			singleInstance = new FishFactory();
		return singleInstance;
	}
	
	// 生成 初始 路径
	public void createInitialPath(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion, int gamepattern)
	{
		switch(gamepattern)
		{
		case 1:					// 简单模式
			createCirclePath(mScene, movingFish, FishRegion);
			break;
		case 2:					// 普通模式
			break;
		case 3:					// 困难模式
			break;
		}
	}
	
	public void createCirclePath(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion)
	{		
		for(int i = 0; i < 3; i++)
		{	// 用三条鱼构建鱼群，分布在上中下
			// 使用了第一种鱼,Id == 0
				Fish fish = new Fish(0, FishRegion.get(0).clone());
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
		float group_Y = Math.abs(rand.nextFloat())*(CAMERA_HEIGHT);
		int num = Math.abs(rand.nextInt()) % 5 +2;
		
		for(int i = 0; i < num; i++)
		{
			Fish fish = new Fish(0, FishRegion.get(0).clone());		
			fish.setDirection(fishDir[randDir]);					// 随机值：鱼的游动方向
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
		if(movingFish.size() < MaxFishNum)
		{
			for(int i = 0; i < MaxFishNum-movingFish.size(); i++)		// 补充场景中的鱼
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
		Fish fish = new Fish(Id, FishRegion.get(Id).clone());
		
		int randDir = Math.abs(rand.nextInt()) % 2;
		fish.setDirection(fishDir[randDir]);			// 随机值：鱼的游动方向
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
		Fish fish = new Fish(Id, FishRegion.get(Id).clone());
		
		int randDir = Math.abs(rand.nextInt()) % 2;
		fish.setDirection(fishDir[randDir]);			// 随机值：鱼的游动方向
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
		Fish fish = new Fish(Id, FishRegion.get(Id).clone());
		
		int randDir = Math.abs(rand.nextInt()) % 4;
		fish.setDirection(fishDir[randDir]);		// 随机值：鱼的游动方向
		fish.setLinePath();							// 这个设置放在最后，它能真正改变鱼的位置和路线
		
		fish.animate(100);
		movingFish.add(fish);
		// 将生成的鱼都附加到场景第一层——鱼层
		mScene.getChild(1).attachChild(fish);	
	}
	
}


