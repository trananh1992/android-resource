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
					fish.setp_Y(70);
				else if(i==1)
					fish.setp_Y(130);
				else if(i==2)
					fish.setp_Y(190);
				
				fish.setDirection("Right");		// �����ζ�����
				fish.setCirclePath();			// ��ʼ��·��
				
				fish.animate(100);
				//fish.setSize(55, 30);
				
				movingFish.add(fish);
				// �����ɵ��㶼���ӵ�������һ�㡪�����
				mScene.getChild(1).attachChild(fish);	
		}		
	}
	
	
	// ������Ⱥ
	public void createFishGroup(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion)
	{
		int randDir = Math.abs(rand.nextInt()) % 2;
		float group_Y = Math.abs(rand.nextFloat())*(CAMERA_HEIGHT);
		int num = Math.abs(rand.nextInt()) % 5 +2;
		
		for(int i = 0; i < num; i++)
		{
			Fish fish = new Fish(0, FishRegion.get(0).clone());		
			fish.setDirection(fishDir[randDir]);					// ���ֵ������ζ�����
			fish.setGroupPath(i, group_Y);									// ������÷���������������ı����λ�ú�·��
			
			fish.animate(100);
			movingFish.add(fish);
			mScene.getChild(1).attachChild(fish);
		}
	}	
	

	// ���������
	
	/* ��Ϸ��ʼ�������ζ����� */
	public void createRandomFish(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion)
	{
		if(movingFish.size() < MaxFishNum)
		{
			for(int i = 0; i < MaxFishNum-movingFish.size(); i++)		// ���䳡���е���
			{
				int type = Math.abs(rand.nextInt()) % 3;
				switch(type)
				{
				case 0:			
					createFishInLine(mScene, movingFish, FishRegion);	// ֱ���ζ�
					break;
				case 1:			
					createFishInCircle(mScene, movingFish, FishRegion);	// Բ���ζ�
					break;
				case 2:
					createFishInCurve(mScene, movingFish, FishRegion);	// �����ζ�
					break;
				}
			}
		}
	}

	// �������һ��Բ���ζ�����
	public void createFishInCircle(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion)
	{
		int Id = Math.abs(rand.nextInt()) % 5;
		Fish fish = new Fish(Id, FishRegion.get(Id).clone());
		
		int randDir = Math.abs(rand.nextInt()) % 2;
		fish.setDirection(fishDir[randDir]);			// ���ֵ������ζ�����
		fish.setCirclePath();							// ��ʼ��·��
		
		fish.animate(100);
		movingFish.add(fish);
		mScene.getChild(1).attachChild(fish);	
	}
	
	// �������һ��ֱ���ζ�����
	public void createFishInCurve(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion)
	{
		int Id = Math.abs(rand.nextInt()) % 5;
		Fish fish = new Fish(Id, FishRegion.get(Id).clone());
		
		int randDir = Math.abs(rand.nextInt()) % 2;
		fish.setDirection(fishDir[randDir]);			// ���ֵ������ζ�����
		fish.setCurvePath();							// ������÷���������������ı����λ�ú�·��
		
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
		
		int randDir = Math.abs(rand.nextInt()) % 4;
		fish.setDirection(fishDir[randDir]);		// ���ֵ������ζ�����
		fish.setLinePath();							// ������÷���������������ı����λ�ú�·��
		
		fish.animate(100);
		movingFish.add(fish);
		// �����ɵ��㶼���ӵ�������һ�㡪�����
		mScene.getChild(1).attachChild(fish);	
	}
	
}


