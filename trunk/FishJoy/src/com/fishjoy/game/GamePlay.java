package com.fishjoy.game;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.AutoParallaxBackground;
import org.anddev.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

public class GamePlay extends BaseGameActivity implements IOnSceneTouchListener{

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;
	
	private Camera myCamera;
	private Engine mEngine;

	private final Scene scene = new Scene();
	
	private BitmapTextureAtlas 	seaTexture;
	private TextureRegion 		seaTextureRegion;

	public Engine onLoadEngine() {
		// TODO Auto-generated method stub
		this.myCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		mEngine = new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.myCamera));
		return mEngine;
	}

	public void onLoadResources() {
		// TODO Auto-generated method stub
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("pic/");
		
		this.seaTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		this.seaTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.seaTexture,this, "background_easy.png", 0, 0);
		this.mEngine.getTextureManager().loadTextures(this.seaTexture);	
	}

	public Scene onLoadScene() {
		// TODO Auto-generated method stub
		scene.setOnSceneTouchListener(this);
		final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0f, new Sprite(0, 0, this.seaTextureRegion)));
		scene.setBackground(autoParallaxBackground);
		return scene;
	}

	
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}

}
