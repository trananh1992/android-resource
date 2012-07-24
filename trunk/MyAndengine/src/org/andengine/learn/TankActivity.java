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
		// ���ñ���  
        mScene.setBackground(background); 
        
        final AnimatedSprite tank = new AnimatedSprite(400, 200, mSpriteTiledTextureRegion);  
        mScene.attachChild(tank);  
        
        // ע�ᾫ��Ҫʵ�ִ���Ч��  
        mScene.registerTouchArea(tank); 
        
        // �����˶�����  
        final PhysicsHandler physicsHandler = new PhysicsHandler(tank);  
        tank.registerUpdateHandler(physicsHandler);
        
        // Ϊ����ע�ᴥ�������¼�  
        mScene.setOnSceneTouchListener(new IOnSceneTouchListener() 
        {  
        	@Override
            public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) 
        	{  

                // ��ýǶȸ����������꣬����ȡһ��̹�����ĵ���Ϊ̹�˵�����  
                final float angleRad = MathUtils.atan2(  
                        tank.getY() + tank.getHeight() / 2  
                                - pSceneTouchEvent.getY(),  
                        (tank.getX() + tank.getWidth() / 2)  
                                - pSceneTouchEvent.getX());  
  
                // ����ƶ����ٶȣ�����Ҳ�Ǹ��������εļ��㷨����֪�ǶȺ����ٶȣ����X�����ٶȺ�Y�����ٶ�  
                // 100f����Ϊ�����ٶ�  
                float VelocityX = FloatMath.cos(angleRad) * 100f;  
                float VelocityY = FloatMath.sin(angleRad) * 100f;  
  
                switch (pSceneTouchEvent.getAction()) {  
                // �������ֻȡ�˰���̧��ʱ��Ч�����������ǹ۲�  
                case TouchEvent.ACTION_DOWN:  
                case TouchEvent.ACTION_MOVE:  
  
                    // Ϊ������X�����ٶȺ�Y�����ٶ�  
                    physicsHandler.setVelocity(-VelocityX, -VelocityY);  
  
                    // ΪͼƬ������ת�Ƕ�(+ 90��Ϊ��������ת����)  
                    tank.setRotation(MathUtils.radToDeg(angleRad) + 90);  
                    break;  
                case TouchEvent.ACTION_UP:  
                    // �ɿ������󣬽��˶�״̬��ԭ  
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
