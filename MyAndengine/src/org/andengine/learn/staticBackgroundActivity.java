package org.andengine.learn;

import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.MathUtils;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;

import android.util.Log;

public class staticBackgroundActivity extends BaseGameActivity 
{
	private static final int CAMERA_WIDTH = 800;  
    private static final int CAMERA_HEIGHT = 480;  
    private Camera mCamera;
    
    private BitmapTextureAtlas backgroundTexture;
    private TextureRegion backgroundRegion;			// ����TiledTextureRegion
    private TiledTextureRegion buttonRegion;

	@Override
	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, 
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
	}

	@Override
	public void onLoadResources() {
		
		backgroundTexture = new BitmapTextureAtlas(1024, 512, TextureOptions.DEFAULT);  
		backgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				backgroundTexture, this, "gfx/background.png", 0, 0);  
                //.createTiledFromAsset(mBitmapTextureAtlas, this, "background.png", 0, 0);
        
		BitmapTextureAtlas buttonTexture = new BitmapTextureAtlas(256, 256, TextureOptions.DEFAULT);  
        // ͨ��֡���п�ķ�ʽ����button��ע��˳�򣺵�һ��Ϊ����Ч�����ڶ���Ϊ����Ч��  
        // 1, 2����1�У���2��  
        buttonRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(  
                buttonTexture, this, "gfx/button_1.png", 0, 0, 1, 2);  
        // ����һ��  
        this.mEngine.getTextureManager().loadTextures(backgroundTexture, buttonTexture);
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
        final Scene scene = new Scene();
        Log.i("����", String.valueOf(scene.getChildCount()));
        
        scene.setBackgroundEnabled(false);			// ��ɶ�ã�
        // ���һ�������㣬�ڱ���������ӱ�������
        scene.attachChild(new Entity());
        scene.getFirstChild().attachChild(new Sprite(10, 0, this.backgroundRegion));
        
        AnimatedSprite buttonSprite = new AnimatedSprite(480, 300, buttonRegion);
        scene.getFirstChild().attachChild(buttonSprite);
        //buttonSprite.animate(100);
        Log.i("����", String.valueOf(scene.getChildCount()));
        
        // ������ťSprite  
        // 480, 200����ʾ��λ��  
        // button��Ϊ��ťͼƬ֡  
        /*ButtonSprite buttonSprite = new ButtonSprite(480, 300, buttonRegion, 
        		new OnClickListener()
        		{  
                    // �������������û���ס���ŵ�ʱ��buttonͼƬ���л���������ִ��onClick��Ĳ���  
                    // ���û��ɿ���ʱ�򣬲Ż�ִ��  
                    public void onClick(ButtonSprite pButtonSprite,  
                            float pTouchAreaLocalX, float pTouchAreaLocalY) {  
                        // ���û����º����ǽ����button�ӳ������Ƴ���  
                        scene.detachChild(buttonSprite);  
                 }  
        });*/  
        
        return scene;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		
	}
}
