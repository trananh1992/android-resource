package org.andengine.learn;

import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;
import android.widget.Toast;

public class Sprite03 extends BaseGameActivity{

	private static final int CAMERA_WIDTH = 480;		//照着前一篇文章的例子写就行了，框架都打好了
	private static final int CAMERA_HEIGHT = 320;

	private Camera mCamera;
	private Scene mScene;
	private BitmapTextureAtlas mBitmapTextureAtlas;		// 声明纹理用于加载资源

	private TextureRegion mFaceTextureRegion;
	// 使用瓦片来设定纹理
	private TiledTextureRegion mFaceTiledTextureRegion;

	private AnimatedSprite face ;
	Fish fish;
	
	@Override
	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, 
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
	}

	@Override
	public void onLoadResources() {
		// BitmapTextureAtlas的参数不能小于图片的大小，且必须为2的整数幂
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
                //gfx是asset目录下的一个文件夹，里面有“face_box.png”这个图片
				BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		//this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, 
		//		this, "face_circle_tiled.png", 0, 0);
		// TiledTextureRegion可以指定图片的行数和列数(先列后行)
		this.mFaceTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, 
				this, "face_circle_tiled.png", 0, 0, 2, 1);

		this.mEngine.getTextureManager().loadTexture(this.mBitmapTextureAtlas);
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
		mScene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));
		// 先加一个底片
		mScene.attachChild(new Entity());
		fish = new Fish(this.mFaceTiledTextureRegion);
		// 将精灵注入场景
		mScene.getFirstChild().attachChild(fish);
		int num = mScene.getChildCount();
		Log.i("层数", String.valueOf(num));
		
		this.mScene.registerUpdateHandler(new IUpdateHandler()		// 在匿名类里，this就是他自己而不是外部类实例
		{
			@Override
			public void reset()
			{
				if(mScene.unregisterUpdateHandler(this))
					Log.i("UpateHandler提示：", "成功注销！");
			}
			
			@Override
			public void onUpdate(float pSecondsElapsed)
			{
				Log.i("提示", "场景更新");
				if(FishMonitor.getInstance().moveMonite(fish) == true)
				{
					Log.i("场景提示", "在onUpdate中成功detach！");
					reset();
				}
			}
		});
		
		return mScene;
	}
	
	@Override
	public void onLoadComplete() {

	}
}
