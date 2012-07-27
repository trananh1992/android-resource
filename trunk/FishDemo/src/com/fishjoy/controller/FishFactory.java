package com.fishjoy.controller;

import java.util.ArrayList;

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

	private static FishFactory singleInstance = null;
	
	// 获取工厂单例对象
	public static FishFactory getSingleInstance()
	{
		if(singleInstance == null)
			singleInstance = new FishFactory();
		return singleInstance;
	}
	
	//
	public void createSingleFish(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion)
	{
		Fish fish = new Fish(2, FishRegion.get(1).clone());		// 使用第二种鱼，Id=2，index=1
		fish.setY(CAMERA_HEIGHT / 2.0f);
		fish.setDirection("Left");			// 设置鱼的初始方向
		fish.setLinePath();					// 这个设置放在最后，它能真正改变鱼的位置和路线
		
		fish.animate(100);
		//fish.setSize(55, 30);
		
		movingFish.add(fish);
		// 将生成的鱼都附加到场景第一层——鱼层
		mScene.getChild(1).attachChild(fish);	
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
			// 使用了第一种鱼,Id == 1
				Fish fish = new Fish(1, FishRegion.get(0).clone());
				if(i==0)
					fish.setY(70);
				else if(i==1)
					fish.setY(130);
				else if(i==2)
					fish.setY(190);
				
				fish.setDirection("Right");		// 设置游动方向
				fish.setCirclePath();			// 初始化路径
				
				fish.animate(100);
				//fish.setSize(55, 30);
				
				movingFish.add(fish);
				// 将生成的鱼都附加到场景第一层——鱼层
				mScene.getChild(1).attachChild(fish);	
		}		
	}
	
	/* 游戏开始后的随机游动序列 */
	public void createRandomPath(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion)
	{
	}
	
}


