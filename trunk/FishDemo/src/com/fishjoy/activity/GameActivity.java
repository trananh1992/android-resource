package com.fishjoy.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.sprite.TiledSprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.MathUtils;

import android.util.Log;
import android.widget.Toast;

import com.fishjoy.model.GameParas;
import com.fishjoy.controller.FishFactory;
import com.fishjoy.controller.TextRegionFactory;

public class GameActivity extends BaseGameActivity implements GameParas
{
	private Engine mEngine;
	private Camera mCamera;
	private Scene mScene;
	
	private int gamepattern;			// ��Ϸģʽ
	private float timeRunning;			// ��Ϸ����ʱ��
	private boolean gamePause = false;
	private boolean gameRunning = true;
	
	private TextureRegion backRegion;	// ������������
	
	// �ζ�����б��㳬���߽���߱�����������
	private ArrayList<Fish> movingFish = new ArrayList<Fish>();
	// 5�����TextRegion
	private ArrayList<TiledTextureRegion> FishRegion = new ArrayList<TiledTextureRegion>();
	
	@Override
	public Engine onLoadEngine() 
	{
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		mEngine = new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, 
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
		return mEngine;
	}

	@Override
	public void onLoadResources() 
	{	
		//Ϊ��������TextureRegion
		backRegion = TextRegionFactory.getSingleInstance().CreateRegionForBackground(mEngine, this);
	
		// �ɹ���Ϊ5���㴴��TiledTextregion�������С����128*256��
		TextRegionFactory.getSingleInstance().CreateRegionForFish(FishRegion, mEngine, this);
		for(int i=0; i < FishRegion.size(); i++)
		{
			Toast.makeText(this, "TextureRegion���:"+ FishRegion.get(i).getWidth()+
					"�߶ȣ�"+FishRegion.get(i).getHeight(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public Scene onLoadScene() 
	{
		mScene = new Scene();
		
		// �����㣺��0��
		mScene.attachChild(new Entity());
		// ���������鸽�ӵ�������
		mScene.setBackgroundEnabled(false);
		mScene.getFirstChild().attachChild(new Sprite(0, 0, backRegion));
		
		// ��Ļ�� ����1��
		mScene.attachChild(new Entity());
		// ���ݲ�ͬ��Ϸģʽ������ͬ�ĳ�ʼ�ζ����У��������ü�ģʽ��
		gamepattern = 1;
		FishFactory.getSingleInstance().createInitialPath(mScene, movingFish, FishRegion, gamepattern);
		
		// 20���ʼ����ζ�����
		timeRunning = 0.0f;
		mScene.registerUpdateHandler(new TimerHandler(1 / 20.0f, true, new ITimerCallback() 
		{ //ע��ʱ�������
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) 
			{				
				//if (gamePause == true){}			// ��ʱδ�����Ϸ��ͣ�߼�
				//else if (gameRunning == true ){
					timeRunning += 1 / 20.0f;
					if(timeRunning > 20.0f)
					{
						//Log.d("ʱ��", String.valueOf(timeRunning));
						FishFactory.getSingleInstance().createRandomPath(mScene, movingFish, FishRegion);
						FishFactory.getSingleInstance().createSingleFish(mScene, movingFish, FishRegion);
						timeRunning = 10.0f;		// ÿ��10�봴��һ����
					}
			}
		}));
		
		return mScene;
	}
	
	@Override
	public void onLoadComplete() {
		//Toast.makeText(GameActivity.this, "����������", Toast.LENGTH_LONG).show();
	}
	
}

