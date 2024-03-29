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
			// 匿名子类，重写父类方法
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, 
					float pTouchAreaLocalY) 
			{
				// Innerclass通过Outerclass.this
				TankActivity.this.runOnUiThread(new Runnable(){
					@Override
					public void run()
					{
						Toast.makeText(TankActivity.this, "Hello,face!", Toast.LENGTH_SHORT).show();
					}
				});
				return true;
			}
		};
		// 注册精灵要实现触摸效果  
	    mScene.registerTouchArea(face);
		mScene.attachChild(face);
		
        // 方法2：注册触摸监听区域 
       /* mScene.registerTouchArea(tank); 		
        mScene.setOnAreaTouchListener(new IOnAreaTouchListener(){
        	@Override
        	public boolean onAreaTouched(TouchEvent pAreaTouchEvent, ITouchArea area, float t_X, float t_Y)
        	{
        		Toast.makeText(TankActivity.this, "Hello,tank!", Toast.LENGTH_SHORT).show();
        		return true;
        	}
        });*/
        
        // 方法3：设置场景监听器
        /*mScene.setOnSceneTouchListener(new IOnSceneTouchListener() // 匿名类就是一个子类，可以重写或继承父类的方法
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
                    // 松开按键后，将运动状态还原(即停止运动)  
                    physicsHandler.reset();  
                    break;  
                }  
                return true;  
            }  
        });*/
        
		return mScene;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
    
}
