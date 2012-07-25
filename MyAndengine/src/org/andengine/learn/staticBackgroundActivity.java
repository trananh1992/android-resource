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
    private TextureRegion backgroundRegion;			// 不用TiledTextureRegion
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
        // 通过帧序列块的方式创建button，注意顺序：第一张为正常效果，第二张为按下效果  
        // 1, 2：共1列，有2行  
        buttonRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(  
                buttonTexture, this, "gfx/button_1.png", 0, 0, 1, 2);  
        // 加载一下  
        this.mEngine.getTextureManager().loadTextures(backgroundTexture, buttonTexture);
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
        final Scene scene = new Scene();
        Log.i("层数", String.valueOf(scene.getChildCount()));
        
        scene.setBackgroundEnabled(false);			// 有啥用？
        // 添加一个背景层，在背景层上添加背景精灵
        scene.attachChild(new Entity());
        scene.getFirstChild().attachChild(new Sprite(10, 0, this.backgroundRegion));
        
        AnimatedSprite buttonSprite = new AnimatedSprite(480, 300, buttonRegion);
        scene.getFirstChild().attachChild(buttonSprite);
        //buttonSprite.animate(100);
        Log.i("层数", String.valueOf(scene.getChildCount()));
        
        // 建立按钮Sprite  
        // 480, 200：显示的位置  
        // button：为按钮图片帧  
        /*ButtonSprite buttonSprite = new ButtonSprite(480, 300, buttonRegion, 
        		new OnClickListener()
        		{  
                    // 建立监听，当用户点住不放的时候，button图片会切换，但不会执行onClick里的操作  
                    // 当用户松开的时候，才会执行  
                    public void onClick(ButtonSprite pButtonSprite,  
                            float pTouchAreaLocalX, float pTouchAreaLocalY) {  
                        // 当用户点下后，我们将这个button从场景中移除掉  
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
