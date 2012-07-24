package org.andengine.learn;

import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.MathUtils;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
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

public class TankActivity extends BaseGameActivity {

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
				new AssetBitmapTextureAtlasSource(this, "gfx/background.png"));
        BitmapTextureAtlas mBitmapTextureAtlas = new BitmapTextureAtlas(128, 256, TextureOptions.DEFAULT);  
        mSpriteTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory  
                .createTiledFromAsset(mBitmapTextureAtlas, this, "face_box.png", 0, 0, 1, 1);  
        this.mEngine.getTextureManager().loadTexture(mBitmapTextureAtlas);  
	}

	@Override
	public Scene onLoadScene() 
	{
		Scene mScene = new Scene(); 
		// 设置背景  
        mScene.setBackground(background); 
        
        final AnimatedSprite tank = new AnimatedSprite(400, 200, mSpriteTiledTextureRegion);  
        mScene.attachChild(tank);  
        
        // 注册精灵要实现触摸效果  
        mScene.registerTouchArea(tank); 
        
        // 构建运动机制  
        final PhysicsHandler physicsHandler = new PhysicsHandler(tank);  
        tank.registerUpdateHandler(physicsHandler);
        
        // 为场景注册触摸监听事件  
        mScene.setOnSceneTouchListener(new IOnSceneTouchListener() 
        {  
        	@Override
            public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) 
        	{  

                // 求得角度根据两个坐标，这里取一下坦克中心点作为坦克的坐标  
                final float angleRad = MathUtils.atan2(  
                        tank.getY() + tank.getHeight() / 2  
                                - pSceneTouchEvent.getY(),  
                        (tank.getX() + tank.getWidth() / 2)  
                                - pSceneTouchEvent.getX());  
  
                // 求出移动的速度，这里也是根据三角形的计算法则，已知角度和总速度，求出X方向速度和Y方向速度  
                // 100f：即为其总速度  
                float VelocityX = FloatMath.cos(angleRad) * 100f;  
                float VelocityY = FloatMath.sin(angleRad) * 100f;  
  
                switch (pSceneTouchEvent.getAction()) {  
                // 这里，我们只取了按下抬起时的效果，方便我们观察  
                case TouchEvent.ACTION_DOWN:  
                case TouchEvent.ACTION_MOVE:  
  
                    // 为其设置X方向速度和Y方向速度  
                    physicsHandler.setVelocity(-VelocityX, -VelocityY);  
  
                    // 为图片设置旋转角度(+ 90：为了修正旋转方向)  
                    tank.setRotation(MathUtils.radToDeg(angleRad) + 90);  
                    break;  
                case TouchEvent.ACTION_UP:  
                    // 松开按键后，将运动状态还原  
                    physicsHandler.reset();  
                    break;  
                }  
                return true;  
            }  
        });  
        
		return mScene;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
    
}
