package org.andengine.learn;

import java.util.ArrayList;

import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.MathUtils;			// andengine�ĺ�����
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
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
import android.util.Log;
import android.widget.Toast;

public class TankDrag extends BaseGameActivity {

	private static final int CAMERA_WIDTH = 800;  
    private static final int CAMERA_HEIGHT = 480;  
    
    Scene mScene;
    private Camera mCamera;
    private RepeatingSpriteBackground background;
    private TiledTextureRegion mSpriteTiledTextureRegion, fishRegion; 
    private static ArrayList<AnimatedSprite> mySprite = new ArrayList<AnimatedSprite>();
    
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
		
        BitmapTextureAtlas mBitmapTextureAtlas = new BitmapTextureAtlas(512, 512, TextureOptions.DEFAULT);  
       
        mSpriteTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
        		mBitmapTextureAtlas, this, "gfx/face_box.png", 0, 0, 1, 1);  
        fishRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
        		mBitmapTextureAtlas, this, "gfx/fish_3.png", 100, 0, 2, 1); 
        
        this.mEngine.getTextureManager().loadTexture(mBitmapTextureAtlas);  
	}

	@Override
	public Scene onLoadScene() 
	{
		this.mEngine.registerUpdateHandler(new FPSLogger());
		mScene = new Scene(); 
        mScene.setBackground(background); // ���ñ���  
        
        /*final AnimatedSprite tank = new AnimatedSprite(400, 200, mSpriteTiledTextureRegion);  
        mScene.attachChild(tank);  
        // �����˶�����  
        final PhysicsHandler physicsHandler = new PhysicsHandler(tank);  
        tank.registerUpdateHandler(physicsHandler);*/
       
        // ����һ��ǰ���ַ�����Ҫע��
		final AnimatedSprite face = new AnimatedSprite(100, 0, mSpriteTiledTextureRegion){
			// ������������³�Ա
			private boolean mGrabbed;
			
			// �������࣬��д���෽��
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, 
					float pTouchAreaLocalY)
			{
				// Innerclassͨ��Outerclass.this
				//Toast.makeText(TankDrag.this, "Hello,face!", Toast.LENGTH_SHORT).show();
				switch (pSceneTouchEvent.getAction()) {  
	            case TouchEvent.ACTION_DOWN:  
	                mGrabbed = true;  
	                // ��ԭͼƬ�Ŵ�4.5����֮ǰ���õ���4����  
	                //setScale(4.5f);  
	                break;  
	  
	            case TouchEvent.ACTION_MOVE:  
	                if (mGrabbed) {  
	                    // �������þ��������  
	                    setPosition(pSceneTouchEvent.getX(),		//ͨ��TouchEventʵʱ��������  
	                            pSceneTouchEvent.getY());  
	                }  
	                break;  
	  
	            case TouchEvent.ACTION_UP:  
	                if (mGrabbed) {  
	                    mGrabbed = false;  
	                    // ��ͼƬ��ԭ��֮ǰ�����ӣ�4����С  
	                    //setScale(4f);  
	                }  
	                break;  
	            } 
				return true;
			}
		};
		//face.setScale(2f);
		face.setSize(64, 64);
		Log.i("̹�˵Ĵ�С", String.valueOf(face.getWidth()));
		// ע�ᾫ��Ҫʵ�ִ���Ч��  
	    mScene.registerTouchArea(face);
		mScene.attachChild(face);
		mySprite.add(face);
		
		AnimatedSprite shark = new AnimatedSprite(0, 0, fishRegion);
		shark.animate(100);
		shark.setSize(163/2.0f, 358/2.0f);
		mScene.attachChild(shark);
		mySprite.add(shark);
		
		this.update(face, shark);
		
		return mScene;
	}

	public void update(AnimatedSprite face, AnimatedSprite shark)
	{
		final AnimatedSprite f = face;
		final AnimatedSprite s = shark;
		
		mScene.registerUpdateHandler(new IUpdateHandler(){	// �ڲ���ֻ������final���͵��ⲿ����
			@Override
			public void onUpdate(float pSecondsElapsed) {
				// TODO Auto-generated method stub
				if(f.collidesWith(s))
					Log.i("��ײ��ʾ", "��������ײ��һ���ˣ�");
				if(mySprite.get(0).collidesWith(mySprite.get(1)))
					Log.i("��ײ��ʾ", "����������ײ��һ���ˣ�");
			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		mScene.setOnSceneTouchListener(new IOnSceneTouchListener()
		{
			@Override
			public boolean onSceneTouchEvent(Scene pScene,
					TouchEvent pSceneTouchEvent) 
			{
				/*AnimatedSprite myTank = (AnimatedSprite)mScene.getChild(0);
				Log.i("̹�˵Ĵ�С", String.valueOf(myTank.getWidth()));
				Log.i("̹�˵Ĵ�С", String.valueOf(myTank.getHeight()));
				AnimatedSprite myShark = (AnimatedSprite)mScene.getChild(1);
				Log.i("����Ĵ�С", String.valueOf(myShark.getWidth()));
				Log.i("����Ĵ�С", String.valueOf(myShark.getHeight()));*/
				return false;
			}
		});
	}
	
}






