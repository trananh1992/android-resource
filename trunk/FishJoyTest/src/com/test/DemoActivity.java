package com.test;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.LoopEntityModifier;
import org.anddev.andengine.entity.modifier.PathModifier;
import org.anddev.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.anddev.andengine.entity.modifier.PathModifier.Path;
import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnAreaTouchListener;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.Scene.ITouchArea;
import org.anddev.andengine.entity.scene.background.AutoParallaxBackground;
import org.anddev.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.MathUtils;
import org.anddev.andengine.util.modifier.ease.EaseSineInOut;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;


public class DemoActivity extends BaseGameActivity //implements IOnSceneTouchListener
{
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;
	
	private Camera myCamera;
	private Engine mEngine;

	private final Scene scene = new Scene();
	
	private BitmapTextureAtlas fishTexture;
	private TiledTextureRegion fish1TextureRegion;
	private TiledTextureRegion fish2TextureRegion;
	
	private BitmapTextureAtlas 	cannonTexture;
	private TiledTextureRegion 	cannon1TextureRegion;
	private TiledTextureRegion 	cannon2TextureRegion;
	
	private BitmapTextureAtlas 	seaTexture;
	private TextureRegion 		seaTextureRegion;
	
	private BitmapTextureAtlas netTexture;
	private TiledTextureRegion netTextureRegion;

	private AnimatedSprite cannon = null;
	private AnimatedSprite cannon1 = null;
	private Fish fish = null;
	private AnimatedSprite fish2;
	
	AnimatedSprite net = null;

	int n = 1;
	
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		}

	public Engine onLoadEngine() {
		// TODO Auto-generated method stub
		this.myCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		mEngine = new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.myCamera));
		return mEngine;
	}

	public void onLoadResources() {
		this.fishTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.BILINEAR);

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.fish1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fishTexture, this, "fish_1.png", 200, 180, 4, 2);
		this.fish2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fishTexture, this, "fish_2.png", 10, 10, 1, 6);
		
		this.cannonTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		this.cannon1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.cannonTexture, this, "08levelpao.png", 0, 0, 1, 1);
		this.cannon2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.cannonTexture, this, "09levelpao.png", 200, 0, 1, 1);
		
		this.netTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		this.netTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.netTexture, this, "nets.png", 0, 0, 1, 5);
	
		this.seaTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.DEFAULT);
		this.seaTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.seaTexture,this, "sea.png", 0, 0);
		this.mEngine.getTextureManager().loadTextures(this.seaTexture, this.fishTexture, this.cannonTexture, this.netTexture);	
	}

	public Scene onLoadScene() 
	{
		//scene.setOnSceneTouchListener(this);
		// 背景
		final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(5.0f, new Sprite(0, 0, this.seaTextureRegion)));
		scene.setBackground(autoParallaxBackground);

		final int centerX = (CAMERA_WIDTH - this.fish1TextureRegion.getWidth()) / 2;
		final int centerY = (CAMERA_HEIGHT - this.fish1TextureRegion.getHeight()) / 2;
		/*
		fish = new Fish(centerX, centerY, this.fish1TextureRegion, mEngine);
		final PhysicsHandler physicsHandler = new PhysicsHandler(fish);
		fish.registerUpdateHandler(physicsHandler);
		*/
		fish2 = new AnimatedSprite(200, 100, this.fish2TextureRegion);
			
		SharedPreferences preferences = getSharedPreferences("PathSaver", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString("path", "10,10,10,200,200,200,300,300,300,10,10,10");
        editor.commit();
        
        String string = preferences.getString("path", "");
        String[] stringArray = string.split("\\,");
        int[] a = new int[stringArray.length];
        
        for (int i = 0; i < stringArray.length; i++) {
        	Log.v("kk", stringArray[i]);
			a[i] = Integer.parseInt(stringArray[i]);
		}
        
        final Path path = new Path(5).to(a[0], a[1]).to(a[2], a[3]).to(a[4], a[5]).to(a[6], a[7]).to(a[8], a[9]);
        
		//Add the proper animation when a waypoint of the path is passed. 
		fish2.registerEntityModifier(new LoopEntityModifier(new PathModifier(30, path, null, new IPathModifierListener() {
			
			public void onPathStarted(final PathModifier pPathModifier, final IEntity pEntity) {
				Debug.d("onPathStarted");
			}

			
			public void onPathWaypointStarted(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) {
				Debug.d("onPathWaypointStarted:  " + pWaypointIndex);
				switch(pWaypointIndex) {
					case 0:
						fish2.animate(new long[]{200, 200, 200, 200, 200, 200}, 0, 5, true);
						break;
					case 1:
						fish2.animate(new long[]{200, 200, 200, 200, 200, 200}, 0, 5, true);
						break;
					case 2:
						fish2.animate(new long[]{200, 200, 200, 200, 200, 200}, 0, 5, true);
						break;
					case 3:
						fish2.animate(new long[]{200, 200, 200, 200, 200, 200}, 0, 5, true);
						break;
				}
			}

			
			public void onPathWaypointFinished(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) {
				Debug.d("onPathWaypointFinished: " + pWaypointIndex);
			}

			
			public void onPathFinished(final PathModifier pPathModifier, final IEntity pEntity) {
				Debug.d("onPathFinished");
			}
		}, EaseSineInOut.getInstance())));
		
		//fish.animate(100);
		fish2.animate(1000);
		
		//scene.attachChild(fish);
		scene.attachChild(fish2);
		
		cannon = new AnimatedSprite(200, 200, this.cannon1TextureRegion);
		cannon1 = new AnimatedSprite(200, 200, this.cannon2TextureRegion);

		cannon.setScale((float) 0.5);
		cannon1.setScale((float) 0.5);
		
		scene.attachChild(cannon1);
		scene.attachChild(cannon);
		
		// 为炮台注册触摸事件监听器
		scene.registerTouchArea(cannon);
		scene.registerTouchArea(cannon1);
		
		scene.setOnAreaTouchListener(new IOnAreaTouchListener() {
			
			public boolean onAreaTouched(TouchEvent arg0, ITouchArea arg1, float arg2,
					float arg3) {
				// TODO Auto-generated method stub
				 if (arg0.getAction() == TouchEvent.ACTION_DOWN) {  
			            AnimatedSprite ta = (AnimatedSprite) arg1;
			            
			            if (n == 1) {
				            ta.setVisible(false);			// 使一个炮台可见，另一个不可见
				            cannon1.setVisible(true);
				            n = 2;
						}
			            else {
				            ta.setVisible(true);
				            cannon1.setVisible(false);
							n = 1;
						}
			        }  
			        return true; 
			}
		});
		return scene;
	}

	/*public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
		// TODO Auto-generated method stub
		float angle = MathUtils.atan2(cannon.getY() + cannon.getHeight()/2 - arg1.getY(), 
				(cannon.getX() + cannon.getWidth()/2 - arg1.getX()));
		cannon.registerEntityModifier(new RotationModifier((float) 0.2, cannon.getRotation(), 
				MathUtils.radToDeg(angle) - 90 ));
		cannon1.registerEntityModifier(new RotationModifier((float) 0.2, cannon.getRotation(), 
				MathUtils.radToDeg(angle) - 90 ));

		net = new AnimatedSprite((cannon.getX() + cannon.getWidth()/2), (cannon.getY() + cannon.getHeight()/2), this.netTextureRegion);
		scene.attachChild(net);

		PhysicsHandler physicsHandler = new PhysicsHandler(net);
		net.registerUpdateHandler(physicsHandler);
		
		Path path1 = new Path(2).to((cannon.getX() + cannon.getWidth()/2), (cannon.getY() + cannon.getHeight()/2)).to(arg1.getX(), arg1.getY());
		net.registerEntityModifier(new PathModifier(2, path1));
		
		
		scene.postRunnable(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				if(net.collidesWith(fish)){
					fish.setVisible(false);
				}
				if(net.collidesWith(fish2)){
					fish2.setVisible(false);
				}
			}
		});
		return false;
	}*/

}




