package com.fishjoy.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
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
	
	int gamepattern;			// 游戏模式
	
	private TextureRegion mBackgroundTextureRegion;
	
	// 游动鱼的列表（鱼超出边界或者被捕获后清除）
	private ArrayList<Fish> movingFish = new ArrayList<Fish>();
	// 5种鱼的TextRegion
	private ArrayList<TiledTextureRegion> FishRegion = new ArrayList<TiledTextureRegion>();
	
	@Override
	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		mEngine = new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, 
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
		return mEngine;
	}

	@Override
	public void onLoadResources() 
	{	
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		BitmapTextureAtlas mBackgroundTexture = new BitmapTextureAtlas(1024, 512, 
				TextureOptions.DEFAULT);
		mBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mBackgroundTexture, this, "background_easy.png", 0, 0);
		this.mEngine.getTextureManager().loadTextures(mBackgroundTexture);
		
		// 由工厂为5种鱼创建Textregion（纹理大小都是128*256）
		TextRegionFactory.getSingleInstance().CreateRegionForFish(FishRegion, mEngine, this);
	}

	@Override
	public Scene onLoadScene() 
	{
		mScene = new Scene();
		
		// 背景层：0
		mScene.attachChild(new Entity());
		// 将背景精灵附加到背景层
		mScene.setBackgroundEnabled(false);
		mScene.getFirstChild().attachChild(new Sprite(0, 0, mBackgroundTextureRegion));
		
		// 鱼的活动层 ：1
		mScene.attachChild(new Entity());
		// 根据不同游戏模式创建不同的初始游动序列（这里暂用简单模式）
		gamepattern = 1;
		FishFactory.getSingleInstance().createInitialPath(mScene, movingFish, FishRegion, gamepattern);
		
		// 20秒后开始随机游动序列
		
		
		/*mScene.registerUpdateHandler(new IUpdateHandler() {
			int i = 0;
			@Override
			public void reset() { }
			@Override
			public void onUpdate(final float pSecondsElapsed) 
			{
				if(movingFish.get(movingFish.size()-1).isOutOfBound() && i == 0)
				{
					i++;
					Log.d("标记", String.valueOf(i));
					creat_Circle_Group(Fish_Name.SARDINE, movingFishList);
				}
			}
		});*/
		
		return mScene;
	}
	
	@Override
	public void onLoadComplete() {
		//Toast.makeText(GameActivity.this, "我们走完了", Toast.LENGTH_LONG).show();
	}
}

