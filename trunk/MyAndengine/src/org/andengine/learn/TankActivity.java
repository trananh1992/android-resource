package org.andengine.learn;

import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.MathUtils;			// andengine�ĺ�����
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
        mScene.setBackground(background); // ���ñ���  
        
        final AnimatedSprite tank = new AnimatedSprite(400, 200, mSpriteTiledTextureRegion);  
        mScene.attachChild(tank);  
        // �����˶�����  
        final PhysicsHandler physicsHandler = new PhysicsHandler(tank);  
        tank.registerUpdateHandler(physicsHandler);
       
        // ����һ��ǰ���ַ�����Ҫע��
		final AnimatedSprite face = new AnimatedSprite(100, 100, mSpriteTiledTextureRegion){
			// �������࣬��д���෽��
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, 
					float pTouchAreaLocalY) 
			{
				// Innerclassͨ��Outerclass.this
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
		// ע�ᾫ��Ҫʵ�ִ���Ч��  
	    mScene.registerTouchArea(face);
		mScene.attachChild(face);
		
        // ����2��ע�ᴥ���������� 
       /* mScene.registerTouchArea(tank); 		
        mScene.setOnAreaTouchListener(new IOnAreaTouchListener(){
        	@Override
        	public boolean onAreaTouched(TouchEvent pAreaTouchEvent, ITouchArea area, float t_X, float t_Y)
        	{
        		Toast.makeText(TankActivity.this, "Hello,tank!", Toast.LENGTH_SHORT).show();
        		return true;
        	}
        });*/
        
        // ����3�����ó���������
        /*mScene.setOnSceneTouchListener(new IOnSceneTouchListener() // ���������һ�����࣬������д��̳и���ķ���
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
                    // �ɿ������󣬽��˶�״̬��ԭ(��ֹͣ�˶�)  
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