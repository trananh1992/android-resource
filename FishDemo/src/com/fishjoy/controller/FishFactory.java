package com.fishjoy.controller;

import java.util.ArrayList;
import java.util.Random;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;

import com.fishjoy.activity.Fish;
import com.fishjoy.model.GameParas;

/**
 * FishFactory:������ĸ����ζ�·��
 *
 */

public class FishFactory implements GameParas{

	private static FishFactory singleInstance = null;	// ��ʵ������
	private final Random rand = new Random();			// ���������
	
	// ��ȡ������������
	public static FishFactory getSingleInstance()
	{
		if(singleInstance == null)
			singleInstance = new FishFactory();
		return singleInstance;
	}
	
	// ���� ��ʼ ·��
	public void createInitialPath(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion, int gamepattern)
	{
		switch(gamepattern)
		{
		case 1:					// ��ģʽ
			createCirclePath(mScene, movingFish, FishRegion);
			break;
		case 2:					// ��ͨģʽ
			break;
		case 3:					// ����ģʽ
			break;
		}
	}
	
	
	public void createCirclePath(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion)
	{		
		for(int i = 0; i < 3; i++)
		{	// �������㹹����Ⱥ���ֲ���������
			// ʹ���˵�һ����,Id == 0
				Fish fish = new Fish(0, FishRegion.get(0).clone());
				if(i==0)
					fish.setY(70);
				else if(i==1)
					fish.setY(130);
				else if(i==2)
					fish.setY(190);
				
				fish.setDirection("Right");		// �����ζ�����
				fish.setCirclePath();			// ��ʼ��·��
				
				fish.animate(100);
				//fish.setSize(55, 30);
				
				movingFish.add(fish);
				// �����ɵ��㶼���ӵ�������һ�㡪�����
				mScene.getChild(1).attachChild(fish);	
		}		
	}
	
	/* ��Ϸ��ʼ�������ζ����� */
	public void createRandomFish(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion)
	{
		if(movingFish.size() < MaxFishNum)
		{
			for(int i = 0; i < MaxFishNum - movingFish.size(); i++)		// ���䳡���е���
			{
				//int type = Math.abs(rand.nextInt()) % 3;
				int type = 2;
				switch(type)
				{
				case 0:			
					createFishInLine(mScene, movingFish, FishRegion);	// ֱ���ζ�
					break;
				case 1:			// Բ���ζ�
					break;
				case 2:
					createFishInCurve(mScene, movingFish, FishRegion);	// �����ζ�
					break;
					
				}
			}
		}
	}

	// �������һ��ֱ���ζ�����
	public void createFishInCurve(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion)
	{
		int Id = Math.abs(rand.nextInt()) % 5;
		Fish fish = new Fish(Id, FishRegion.get(Id).clone());
		
		fish.setY(CAMERA_HEIGHT / 2);
		fish.setDirection("Left");				// ���ֵ������ζ�����
		fish.setCurvePath();					// ������÷���������������ı����λ�ú�·��
		
		fish.animate(100);
		movingFish.add(fish);
		mScene.getChild(1).attachChild(fish);	
	}
		
	// �������һ��ֱ���ζ�����
	public void createFishInLine(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion)
	{
		int Id = Math.abs(rand.nextInt()) % 5;
		Fish fish = new Fish(Id, FishRegion.get(Id).clone());
		
		fish.setDirection(getRandomDirection());	// ���ֵ������ζ�����
		fish.setLinePath();							// ������÷���������������ı����λ�ú�·��
		
		fish.animate(100);
		movingFish.add(fish);
		// �����ɵ��㶼���ӵ�������һ�㡪�����
		mScene.getChild(1).attachChild(fish);	
	}
	
	public String getRandomDirection()
	{
		int randDir = Math.abs(rand.nextInt()) % 4;
		return fishDir[randDir];
	}
}


