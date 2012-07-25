package com.yinzch.fishdemo;

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

import com.yinzch.data.GameParas;

public class GameActivity extends BaseGameActivity implements GameParas
{
	private Engine mEngine;
	private Camera mCamera;
	private Scene mScene;
	
	private TextureRegion mBackgroundTextureRegion;
	
	HashMap<Fish_Name, TiledTextureRegion> allMovingFishTextureRegionMap = 
			new HashMap<Fish_Name, TiledTextureRegion>();
	private ArrayList<Fish> movingFishList = new ArrayList<Fish>();
	
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
		
		BitmapTextureAtlas SARDINETexture =new BitmapTextureAtlas(64, 256, 
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		TiledTextureRegion  SARDINETiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				SARDINETexture, this, "sardine_1.png", 0, 0, 1, 5);			// 1列5行
		// 放进容器
		allMovingFishTextureRegionMap.put(Fish_Name.SARDINE, SARDINETiledTextureRegion);
		
		this.mEngine.getTextureManager().loadTextures(mBackgroundTexture, SARDINETexture);
	}

	@Override
	public Scene onLoadScene() {
		mScene = new Scene();
		mScene.attachChild(new Entity());
		Log.i("层数", String.valueOf(mScene.getChildCount()));
		// 将背景精灵附加到背景层
		mScene.setBackgroundEnabled(false);
		mScene.getFirstChild().attachChild(new Sprite(0, 0, mBackgroundTextureRegion));
		
		Fish fish1 = new Fish(Fish_Name.SARDINE, allMovingFishTextureRegionMap.get(Fish_Name.SARDINE));
		fish1.animate(100);
		mScene.attachChild(new Entity());
		mScene.getChild(1).attachChild(fish1);
		Log.i("层数", String.valueOf(mScene.getChildCount()));
		
		return mScene;
	}

	@Override
	public void onLoadComplete() {
		
	}
}