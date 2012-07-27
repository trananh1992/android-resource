package org.andengine.learn;

import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
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

public class Sprite01 extends BaseGameActivity implements IOnSceneTouchListener{

	private static final int CAMERA_WIDTH = 480;		//照着前一篇文章的例子写就行了，框架都打好了
	private static final int CAMERA_HEIGHT = 320;

	private Camera mCamera;
	private BitmapTextureAtlas mBitmapTextureAtlas;		// 声明纹理用于加载资源
	// Textureregion的作用似乎就是让系统知道如何剪切一个纹理，并返回一个这样的纹理
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

	// 分别取四个动画的区域  
    // 解释一下创建mSnapdragonTextureRegion时的参数0，0，4，3，  
    // 其中的0，0代表该区域在mBitmapTextureAtlas中的起始位置  
    // 我们需要设置该位置，使四个TextureRegion不重叠  
    // 同样，想象一下mBitmapTextureAtlas是一张大画布，我们在上边画画，画了四张图片，每张是一个TextureRegion  
    // 下边解释4，3  
    // 4代表列数，3代表行数  
    // 观察一下图片就可以看见，snapdragon_tiled.png这幅图片是分了3行4列的
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
		
		final Scene scene = new Scene();
		scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));
		
		int num = scene.getChildCount();
		Log.i("层数", String.valueOf(num));
		
		/* Calculate the coordinates for the face, so its centered on the camera. */
		Toast.makeText(this, "TextureRegion宽度:"+ this.mFaceTiledTextureRegion.getWidth(), Toast.LENGTH_SHORT).show();
		final int centerX = (CAMERA_WIDTH - this.mFaceTiledTextureRegion.getWidth()/2) / 2;
		final int centerY = (CAMERA_HEIGHT - this.mFaceTiledTextureRegion.getHeight()) / 2;

		/* Create the face and add it to the scene.
		 * Sprite:精灵是一个矩形图形
		 * （继承于RectangularShape， 与Rectangular一样）并且是TextureRegion的载体，
		 * 可以这样认为RectangularShape为精灵提供了矩形， 
		 * TextureRegion为精灵提供了纹理映射，Texture为提供了覆盖物， 
		 * 进行画面渲染时AndEngine内部会调用TextureRegion中的Texture引用，但也只允许如此调用
		 * A TextureRegion defines a rectangle on the Texture. 
		 * A TextureRegion is used by Sprites to let the system know what part of 
		 *   the big Texture the Sprite is showing
		*/
		// 精灵和相应的TextureRegion相对应
		// AnimatedSprite是根据时间或者其他因素来触发更换图片资源
		//final Sprite face = new Sprite(centerX, centerY, this.mFaceTextureRegion);
		face = new AnimatedSprite(centerX, centerY, this.mFaceTiledTextureRegion);
		face.animate(100);
		PhysicsHandler mPhysicsHandler = new PhysicsHandler(face);
		face.registerUpdateHandler(mPhysicsHandler);
		
		fish = new Fish(this.mFaceTiledTextureRegion);
		// 将精灵注入场景
		scene.attachChild(fish);
		num = scene.getChildCount();
		Log.i("层数", String.valueOf(num));
		//scene.getFirstChild().show();
		
		//final Line line = new Line(0, 240, 720, 240, 5.0f);
		//line.setColor(1, 0, 0);
		// 初始化scene时不指定层数会出错
		//scene.getFirstChild().attachChild(line);
		//num = scene.getChildCount();
		//Log.i("层数", String.valueOf(num));

		num = scene.getFirstChild().getChildCount();
		Log.i("层数", String.valueOf(num));
		
		scene.setOnSceneTouchListener(this);			// 要实现响应的接口，否则要用匿名类
		
		return scene;
	}

	@Override
	public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) 
	{
		/* Removing entities can only be done safely on the UpdateThread.
		 * Doing it while updating/drawing can
		 * cause an exception with a suddenly missing entity.
		 * Alternatively, there is a possibility to run the TouchEvents on the UpdateThread by default, by doing:
		 * engineOptions.getTouchOptions().setRunOnUpdateThread(true);
		 * when creating the Engine in onLoadEngine(); */
		this.runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				/* Now it is save to remove the entity! */
				pScene.detachChild(Sprite01.this.fish);
				//Sprite01.this.face.detachSelf();
			}
		});
		//pScene.detachChild(Sprite01.this.face);
		//Sprite01.this.face.detachSelf();
		int num = pScene.getChildCount();
		Log.i("层数", String.valueOf(num));
		//pScene.getFirstChild().detachSelf();
		//Sprite01.this.fish.detachSelf();
		num = pScene.getChildCount();
		Log.i("层数", String.valueOf(num));
		if(pScene.getFirstChild() instanceof Fish)
		{
			Log.i("判断", "我是Fish！");
		}
		//pScene.getFirstChild().show();
		if(pScene.getFirstChild().detachSelf())
			Log.i("判断", "成功自除Fish！");
		num = pScene.getChildCount();
		Log.i("层数", String.valueOf(num));
		
		return false;
	}
	
	@Override
	public void onLoadComplete() {

	}
}
