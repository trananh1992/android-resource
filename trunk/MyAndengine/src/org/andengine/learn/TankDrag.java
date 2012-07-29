package org.andengine.learn;

import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.MathUtils;			// andengine的函数库
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnAreaTouchListener;
import org.anddev.andengine.entity.scene.Scene.ITouchArea;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.anddev.andengine.entity.scene.background.RepeatingSpriteBackground;

import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import android.util.FloatMath;
import android.widget.Toast;

public class TankDrag extends BaseGameActivity {

	private static final int CAMERA_WIDTH = 800;  
    private static final int CAMERA_HEIGHT = 480;  
    
    private Camera mCamera;
    private RepeatingSpriteBackground background;
    private TiledTextureRegion mSpriteTiledTextureRegion; 
    
	@Override
	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, 
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
	}

	@Override
	public void onLoadResources() {
		this.background = new RepeatingSpriteBackground(CAMERA_WIDTH, CAMERA_HEIGHT, this.mEngine.getTextureManager(), 
				new AssetBitmapTextureAtlasSource(this, "gfx/background_3.png"));
		
        BitmapTextureAtlas mBitmapTextureAtlas = new BitmapTextureAtlas(32, 32, TextureOptions.DEFAULT);  
        mSpriteTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
        		mBitmapTextureAtlas, this, "gfx/face_box.png", 0, 0, 1, 1);  
        this.mEngine.getTextureManager().loadTexture(mBitmapTextureAtlas);  
	}

	@Override
	public Scene onLoadScene() 
	{
		this.mEngine.registerUpdateHandler(new FPSLogger());
		Scene mScene = new Scene(); 
        mScene.setBackground(background); // 设置背景  
        
        final AnimatedSprite tank = new AnimatedSprite(400, 200, mSpriteTiledTextureRegion);  
        mScene.attachChild(tank);  
        // 构建运动机制  
        final PhysicsHandler physicsHandler = new PhysicsHandler(tank);  
        tank.registerUpdateHandler(physicsHandler);
       
        // 方法一：前两种方法都要注册
		final AnimatedSprite face = new AnimatedSprite(100, 100, mSpriteTiledTextureRegion){
			// 匿名子类添加新成员
			private boolean mGrabbed;
			
			// 匿名子类，重写父类方法
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, 
					float pTouchAreaLocalY)
			{
				// Innerclass通过Outerclass.this
				Toast.makeText(TankDrag.this, "Hello,face!", Toast.LENGTH_SHORT).show();
				switch (pSceneTouchEvent.getAction()) {  
	            case TouchEvent.ACTION_DOWN:  
	                mGrabbed = true;  
	                // 将原图片放大到4.5倍（之前设置的是4倍）  
	                setScale(4.5f);  
	                break;  
	  
	            case TouchEvent.ACTION_MOVE:  
	                if (mGrabbed) {  
	                    // 从新设置精灵的坐标  
	                    setPosition(pSceneTouchEvent.getX(),		//通过TouchEvent实时更新坐标  
	                            pSceneTouchEvent.getY());  
	                }  
	                break;  
	  
	            case TouchEvent.ACTION_UP:  
	                if (mGrabbed) {  
	                    mGrabbed = false;  
	                    // 将图片还原到之前的样子，4倍大小  
	                    setScale(4f);  
	                }  
	                break;  
	            } 
				return true;
			}
		};
		// 注册精灵要实现触摸效果  
	    mScene.registerTouchArea(face);
		mScene.attachChild(face);
		return mScene;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		
	}
	
}

