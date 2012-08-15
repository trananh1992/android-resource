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
 * FishFactory:������ĸ����ζ�·��
 *
 */

public class FishFactory{

	private static FishFactory singleInstance = null;	// ��ʵ������
	private final Random rand = new Random();			// ���������
	private static Engine mEngine;
	private int kind = GameControl.NormalFishNum;
	
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
		//gamepattern = 4;
		switch(gamepattern)
		{
		
		case 1:					// ��ģʽ
			createDiamondPath(mScene, movingFish, FishRegion);
			break;
		case 2:					// ��ͨģʽ
			createBridgePath(mScene, movingFish, FishRegion);
			break;
		case 3:					// ����ģʽ
			String str = "FISH JOY";
			createStringPath(mScene, movingFish, FishRegion, str);
			break;
		}
	}
	
	// ����������:����������ǵ�����
	public void createSingleFish(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion, int Id)
	{
		Fish fish = new Fish(Id, FishRegion.get(Id).clone());
		// ���÷�λ��ͬʱҲ�����˳�ʼ����
		int randDir = Math.abs(rand.nextInt()) % 3;
		fish.setDirection(GameControl.fishDir[randDir]);
		fish.setCurvePath(randDir);
		// ������Ķ������Ժ��ͳһ����
		fish.animate(new long[]{200,200,200,200,200,200,200,200,200,200,200,200}, 
				new int[]{0,1,2,3,4,5,6,5,4,3,2,1}, 1000);
		movingFish.add(fish);
		mScene.getChild(1).attachChild(fish);
	}
	
	// ������ʼ·�����ַ�·��
	public void createStringPath(Scene mScene, ArrayList<Fish> movingFish, 
			ArrayList<TiledTextureRegion> FishRegion, String str)
	{
		for(int i = 0; i < GameControl.fishJoy2.length; i++)
		{
			// ������������
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
	
	// ������ʼ·������·��
	public void createBridgePath(final Scene mScene, final ArrayList<Fish> movingFish, 
			final ArrayList<TiledTextureRegion> FishRegion)
	{
		// ÿ��0.05�������������ĸ���λ����һ���㣬�ܹ�����400��
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
	
	// ������ʼ·������ʯ·��
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
				for(int j = 0; j < 5; j++)			// i��j��
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
					// �����ɵ��㶼���ӵ�������һ�㡪�����
					mScene.getChild(1).attachChild(fish);
				}
		}
		GameControl.fishSpeed[3] = 90;
		GameControl.fishSpeed[6] = 80; 
	}
	
	// ������Ⱥ
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
			fish.setDirection(GameControl.fishDir[randDir]);					// ���ֵ������ζ�����
			fish.setGroupPath(i, group_Y);									// ������÷���������������ı����λ�ú�·��
			
			fish.animate(new long[]{200,200,200,200,200,200,200,200,200,200,200,200}, 
					new int[]{0,1,2,3,4,5,6,5,4,3,2,1}, 1000);
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
				// ·������
				int type = Math.abs(rand.nextInt()) % 3;
				
				// ��������һ����
				int Id = Math.abs(rand.nextInt()) % (kind-1);		
				Fish fish = new Fish(Id, FishRegion.get(Id).clone());
				// ���÷�λ��ͬʱҲ�����˳�ʼ����
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
				
				// ������Ķ������Ժ��ͳһ����
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
		// ÿ��0.05�������������ĸ���λ����һ���㣬�ܹ�����400��
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
	
	// �ڲ�������λ������һ����������
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
		
		// ��2��������������
		mScene.registerUpdateHandler(new TimerHandler(2.0f, new ITimerCallback() 
		{	
			public void onTimePassed(final TimerHandler pTimerHandler) {
				mScene.unregisterUpdateHandler(pTimerHandler);
				mScene.detachChild(fish);
			}
		}));
	}
	
}

