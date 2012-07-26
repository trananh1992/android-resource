package com.fishjoy.controller;

import java.util.ArrayList;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;

import com.fishjoy.activity.Fish;
import com.fishjoy.model.GameParas;
import com.fishjoy.model.Matrix;
import com.fishjoy.model.GameParas.Edge_Position;
import com.fishjoy.model.GameParas.Fish_Name;
import com.fishjoy.model.GameParas.Move_Direction;


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
					fish.setY(30);
				else if(i==1)
					fish.setY(70);
				else if(i==2)
					fish.setY(190);
				
				fish.setX(CAMERA_WIDTH / 2);
				fish.setDirection("Right");
				fish.setCirclePath();
				
				fish.animate(100);
				fish.setSize(36, 18);
				
				movingFish.add(fish);
				// 将生成的鱼都附加到场景第一层——鱼层
				mScene.getChild(1).attachChild(fish);	
		}		
	}
	
}


