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
	
	private int gamepattern;			// 游戏模式
	private float timeRunning;			// 游戏进行时间
	private boolean gamePause = false;
	private boolean gameRunning = true;
	
	private TextureRegion backRegion;	// 背景纹理区域
	
	// 游动鱼的列表（鱼超出边界或者被捕获后清除）
	private ArrayList<Fish> movingFish = new ArrayList<Fish>();
	// 5种鱼的TextRegion
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
		//为背景创建TextureRegion
		backRegion = TextRegionFactory.getSingleInstance().CreateRegionForBackground(mEngine, this);
	
		// 由工厂为5种鱼创建TiledTextregion（纹理大小都是128*256）
		TextRegionFactory.getSingleInstance().CreateRegionForFish(FishRegion, mEngine, this);
		for(int i=0; i < FishRegion.size(); i++)
		{
			Toast.makeText(this, "TextureRegion宽度:"+ FishRegion.get(i).getWidth()+
					"高度："+FishRegion.get(i).getHeight(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public Scene onLoadScene() 
	{
		mScene = new Scene();
		
		// 背景层：第0层
		mScene.attachChild(new Entity());
		// 将背景精灵附加到背景层
		mScene.setBackgroundEnabled(false);
		mScene.getFirstChild().attachChild(new Sprite(0, 0, backRegion));
		
		// 鱼的活动层 ：第1层
		mScene.attachChild(new Entity());
		// 根据不同游戏模式创建不同的初始游动序列（这里暂用简单模式）
		gamepattern = 1;
		FishFactory.getSingleInstance().createInitialPath(mScene, movingFish, FishRegion, gamepattern);
		
		// 20秒后开始随机游动序列
		timeRunning = 0.0f;
		mScene.registerUpdateHandler(new TimerHandler(1 / 20.0f, true, new ITimerCallback() 
		{ //注册时间监听器
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) 
			{				
				//if (gamePause == true){}			// 暂时未添加游戏暂停逻辑
				//else if (gameRunning == true ){
					timeRunning += 1 / 20.0f;
					if(timeRunning > 20.0f)
					{
						//Log.d("时间", String.valueOf(timeRunning));
						FishFactory.getSingleInstance().createRandomPath(mScene, movingFish, FishRegion);
						FishFactory.getSingleInstance().createSingleFish(mScene, movingFish, FishRegion);
						timeRunning = 10.0f;		// 每隔10秒创建一次鱼
					}
			}
		}));
		
		return mScene;
	}
	
	@Override
	public void onLoadComplete() {
		//Toast.makeText(GameActivity.this, "我们走完了", Toast.LENGTH_LONG).show();
	}
	
}

