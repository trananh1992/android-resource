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

	private static final int CAMERA_WIDTH = 480;		//����ǰһƪ���µ�����д�����ˣ���ܶ������
	private static final int CAMERA_HEIGHT = 320;

	private Camera mCamera;
	private Scene mScene;
	private BitmapTextureAtlas mBitmapTextureAtlas;		// �����������ڼ�����Դ

	private TextureRegion mFaceTextureRegion;
	// ʹ����Ƭ���趨����
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
		// BitmapTextureAtlas�Ĳ�������С��ͼƬ�Ĵ�С���ұ���Ϊ2��������
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
                //gfx��assetĿ¼�µ�һ���ļ��У������С�face_box.png�����ͼƬ
				BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		//this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, 
		//		this, "face_circle_tiled.png", 0, 0);
		// TiledTextureRegion����ָ��ͼƬ������������(���к���)
		this.mFaceTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, 
				this, "face_circle_tiled.png", 0, 0, 2, 1);

		this.mEngine.getTextureManager().loadTexture(this.mBitmapTextureAtlas);
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
		mScene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));
		// �ȼ�һ����Ƭ
		mScene.attachChild(new Entity());
		fish = new Fish(this.mFaceTiledTextureRegion);
		// ������ע�볡��
		mScene.getFirstChild().attachChild(fish);
		int num = mScene.getChildCount();
		Log.i("����", String.valueOf(num));
		
		this.mScene.registerUpdateHandler(new IUpdateHandler()		// ���������this�������Լ��������ⲿ��ʵ��
		{
			@Override
			public void reset()
			{
				if(mScene.unregisterUpdateHandler(this))
					Log.i("UpateHandler��ʾ��", "�ɹ�ע����");
			}
			
			@Override
			public void onUpdate(float pSecondsElapsed)
			{
				Log.i("��ʾ", "��������");
				if(FishMonitor.getInstance().moveMonite(fish) == true)
				{
					Log.i("������ʾ", "��onUpdate�гɹ�detach��");
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
