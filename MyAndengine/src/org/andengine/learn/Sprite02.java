package org.andengine.learn;


import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
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

public class Sprite02 extends BaseGameActivity implements IOnSceneTouchListener{

	private static final int CAMERA_WIDTH = 480;		//����ǰһƪ���µ�����д�����ˣ���ܶ������
	private static final int CAMERA_HEIGHT = 320;

	private Camera mCamera;
	private BitmapTextureAtlas mBitmapTextureAtlas;		// �����������ڼ�����Դ
	// Textureregion�������ƺ�������ϵͳ֪����μ���һ������������һ������������
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

	// �ֱ�ȡ�ĸ�����������  
    // ����һ�´���mSnapdragonTextureRegionʱ�Ĳ���0��0��4��3��  
    // ���е�0��0�����������mBitmapTextureAtlas�е���ʼλ��  
    // ������Ҫ���ø�λ�ã�ʹ�ĸ�TextureRegion���ص�  
    // ͬ��������һ��mBitmapTextureAtlas��һ�Ŵ󻭲����������ϱ߻�������������ͼƬ��ÿ����һ��TextureRegion  
    // �±߽���4��3  
    // 4����������3��������  
    // �۲�һ��ͼƬ�Ϳ��Կ�����snapdragon_tiled.png���ͼƬ�Ƿ���3��4�е�
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
		
		final Scene scene = new Scene();
		scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));
		
		int num = scene.getChildCount();
		Log.i("����", String.valueOf(num));
		
		// �ȼ�һ����Ƭ
		scene.attachChild(new Entity());
		
		fish = new Fish(this.mFaceTiledTextureRegion);
		// ������ע�볡��
		scene.getFirstChild().attachChild(fish);
		
		num = scene.getChildCount();
		Log.i("����", String.valueOf(num));
		
		num = scene.getFirstChild().getChildCount();
		Log.i("����", String.valueOf(num));
		
		scene.setOnSceneTouchListener(this);			// Ҫʵ����Ӧ�Ľӿڣ�����Ҫ��������
		
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
			// ������߳�����������ɾ��������������ȥ���飬�����Ǿ��鲻��Update�Ҳ��ɼ����������������ٴ�ɾ��
			public void run() {
				/* Now it is save to remove the entity! */
				//pScene.getFirstChild().detachChild(Sprite02.this.fish);		// ������fish����Update�����Ǻ�����ɾ����
				Sprite02.this.fish.detachSelf();
			}
		});
		//pScene.detachChild(Sprite01.this.fish);
		//Sprite01.this.fish.detachSelf();		// ֱ��ɾ�������쳣������run���ȵ���һ����fish����Update
		int num = pScene.getChildCount();
		Log.i("����", String.valueOf(num));
		//pScene.getFirstChild().detachSelf();
		//Sprite01.this.fish.detachSelf();
		num = pScene.getFirstChild().getChildCount();
		Log.i("��Ƭ�ϵĺ��ӣ�", String.valueOf(num));
		//if(pScene.getFirstChild() instanceof Sprite) // False
		if(pScene.getFirstChild() instanceof Fish)	   // True
		{
			Log.i("�жϵ�Ƭ", "����Fish��");
		}

		if(pScene.getFirstChild().getFirstChild() instanceof Fish)	   // True
		{
			Log.i("�жϵ�Ƭ�ĺ���", "����Fish��");
			Sprite02.this.fish.show();
		}
		
		//if(pScene.getFirstChild().detachSelf())
		//if(pScene.getFirstChild().detachChild(Sprite02.this.fish))
		// ���ɾ�������ӳ����г�ȥ��fish���ٴ�ɾ�������
		if(Sprite02.this.fish.detachSelf())
			Log.i("�жϵ�Ƭ", "�ɹ���ȥFish��");
		num = pScene.getFirstChild().getChildCount();
		Log.i("��Ƭ�ϵĺ��ӣ�", String.valueOf(num));
		// ����fish�������Ӧ����Ȼ���ڣ�
		// ������Ƿ�����֪���Ƿ����������Fish
		Sprite02.this.fish.show();
		
		return false;
	}
	
	@Override
	public void onLoadComplete() {

	}
}