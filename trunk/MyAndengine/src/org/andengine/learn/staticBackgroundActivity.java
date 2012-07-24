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

public class staticBackgroundActivity extends BaseGameActivity 
{
	private static final int CAMERA_WIDTH = 800;  
    private static final int CAMERA_HEIGHT = 480;  
    private Camera mCamera;
    private RepeatingSpriteBackground background;
    //private TiledTextureRegion mSpriteTiledTextureRegion;
    private TextureRegion mSpriteTiledTextureRegion;

	@Override
	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, 
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
	}

	@Override
	public void onLoadResources() {
		
        BitmapTextureAtlas mBitmapTextureAtlas = new BitmapTextureAtlas(1024, 512, TextureOptions.DEFAULT);  
        //BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        
        mSpriteTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
        		mBitmapTextureAtlas, this, "gfx/background.png", 0, 0);  
                //.createTiledFromAsset(mBitmapTextureAtlas, this, "background.png", 0, 0);
        
        this.mEngine.getTextureManager().loadTexture(mBitmapTextureAtlas);
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
        final Scene scene = new Scene();
        scene.setBackground(background);
        //scene.setBackgroundEnabled(true);
        //scene.getLayer(0).addEntity(new Sprite(0, 10, this.mSpriteTiledTextureRegion));
        return scene;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		
	}
}
