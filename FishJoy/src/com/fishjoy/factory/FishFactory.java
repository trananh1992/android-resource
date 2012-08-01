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
 * FishFactory:������ĸ����ζ�·��
 *
 */

public class FishFactory{

	private static FishFactory singleInstance = null;	// ��ʵ������
	private final Random rand = new Random();			// ���������
	private static Engine mEngine;
	
	// ��ȡ������������
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
	
	// ���� ��ʼ ·��
	public void createInitialPath(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion, int gamepattern)
	{
		switch(gamepattern)
		{
		case 1:					// ��ģʽ
			String str = "FISH JOY";
			createStringPath(mScene, movingFish, FishRegion, str);
			break;
		case 2:					// ��ͨģʽ
			createDiamondPath(mScene, movingFish, FishRegion);
			break;
		case 3:					// ����ģʽ
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
				Log.i("��ʾ", "�����ո�");
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
						// ������������
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
				for(int j = 0; j < 5; j++)			// i��j��
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
						// �����ɵ��㶼���ӵ�������һ�㡪�����
						mScene.getChild(1).attachChild(fish);	
					}
				}
		}
	}
	
	
	public void createCirclePath(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion)
	{		
		for(int i = 0; i < 3; i++)
		{	// �������㹹����Ⱥ���ֲ���������
			// ʹ���˵�һ����,Id == 0
				Fish fish = new Fish(0, FishRegion.get(0).deepCopy());
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
		float group_Y = Math.abs(rand.nextFloat())*(GameControl.CAMERA_HEIGHT);
		int num = Math.abs(rand.nextInt()) % 5 +2;
		
		for(int i = 0; i < num; i++)
		{
			Fish fish = new Fish(0, FishRegion.get(0).deepCopy());		
			fish.setDirection(GameControl.fishDir[randDir]);					// ���ֵ������ζ�����
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
		if(movingFish.size() < GameControl.MaxFishNum)
		{
			for(int i = 0; i < GameControl.MaxFishNum-movingFish.size(); i++)		// ���䳡���е���
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
		Fish fish = new Fish(Id, FishRegion.get(Id).deepCopy());
		
		int randDir = Math.abs(rand.nextInt()) % 2;
		fish.setDirection(GameControl.fishDir[randDir]);			// ���ֵ������ζ�����
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
		Fish fish = new Fish(Id, FishRegion.get(Id).deepCopy());
		
		int randDir = Math.abs(rand.nextInt()) % 2;
		fish.setDirection(GameControl.fishDir[randDir]);			// ���ֵ������ζ�����
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
		Fish fish = new Fish(Id, FishRegion.get(Id).deepCopy());
		
		int randDir = Math.abs(rand.nextInt()) % 4;
		fish.setDirection(GameControl.fishDir[randDir]);		// ���ֵ������ζ�����
		fish.setLinePath();							// ������÷���������������ı����λ�ú�·��
		
		fish.animate(100);
		movingFish.add(fish);
		// �����ɵ��㶼���ӵ�������һ�㡪�����
		mScene.getChild(1).attachChild(fish);	
	}
	
}


