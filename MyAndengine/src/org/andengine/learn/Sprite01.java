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
		
		/* Calculate the coordinates for the face, so its centered on the camera. */
		Toast.makeText(this, "TextureRegion���:"+ this.mFaceTiledTextureRegion.getWidth(), Toast.LENGTH_SHORT).show();
		final int centerX = (CAMERA_WIDTH - this.mFaceTiledTextureRegion.getWidth()/2) / 2;
		final int centerY = (CAMERA_HEIGHT - this.mFaceTiledTextureRegion.getHeight()) / 2;

		/* Create the face and add it to the scene.
		 * Sprite:������һ������ͼ��
		 * ���̳���RectangularShape�� ��Rectangularһ����������TextureRegion�����壬
		 * ����������ΪRectangularShapeΪ�����ṩ�˾��Σ� 
		 * TextureRegionΪ�����ṩ������ӳ�䣬TextureΪ�ṩ�˸���� 
		 * ���л�����ȾʱAndEngine�ڲ������TextureRegion�е�Texture���ã���Ҳֻ������˵���
		 * A TextureRegion defines a rectangle on the Texture. 
		 * A TextureRegion is used by Sprites to let the system know what part of 
		 *   the big Texture the Sprite is showing
		*/
		// �������Ӧ��TextureRegion���Ӧ
		// AnimatedSprite�Ǹ���ʱ�����������������������ͼƬ��Դ
		//final Sprite face = new Sprite(centerX, centerY, this.mFaceTextureRegion);
		face = new AnimatedSprite(centerX, centerY, this.mFaceTiledTextureRegion);
		face.animate(100);
		PhysicsHandler mPhysicsHandler = new PhysicsHandler(face);
		face.registerUpdateHandler(mPhysicsHandler);
		
		fish = new Fish(this.mFaceTiledTextureRegion);
		// ������ע�볡��
		scene.attachChild(fish);
		num = scene.getChildCount();
		Log.i("����", String.valueOf(num));
		//scene.getFirstChild().show();
		
		//final Line line = new Line(0, 240, 720, 240, 5.0f);
		//line.setColor(1, 0, 0);
		// ��ʼ��sceneʱ��ָ�����������
		//scene.getFirstChild().attachChild(line);
		//num = scene.getChildCount();
		//Log.i("����", String.valueOf(num));

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
			public void run() {
				/* Now it is save to remove the entity! */
				pScene.detachChild(Sprite01.this.fish);
				//Sprite01.this.face.detachSelf();
			}
		});
		//pScene.detachChild(Sprite01.this.face);
		//Sprite01.this.face.detachSelf();
		int num = pScene.getChildCount();
		Log.i("����", String.valueOf(num));
		//pScene.getFirstChild().detachSelf();
		//Sprite01.this.fish.detachSelf();
		num = pScene.getChildCount();
		Log.i("����", String.valueOf(num));
		if(pScene.getFirstChild() instanceof Fish)
		{
			Log.i("�ж�", "����Fish��");
		}
		//pScene.getFirstChild().show();
		if(pScene.getFirstChild().detachSelf())
			Log.i("�ж�", "�ɹ��Գ�Fish��");
		num = pScene.getChildCount();
		Log.i("����", String.valueOf(num));
		
		return false;
	}
	
	@Override
	public void onLoadComplete() {

	}
}
