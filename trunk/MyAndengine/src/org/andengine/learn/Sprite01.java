package org.andengine.learn;

import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.font.Font;

public class Sprite01 extends BaseGameActivity {

	private static final int CAMERA_WIDTH = 480;		//����ǰһƪ���µ�����д�����ˣ���ܶ������
	private static final int CAMERA_HEIGHT = 320;

	private Camera mCamera;
	private BitmapTextureAtlas mBitmapTextureAtlas;		// �����������ڼ�����Դ
	// Textureregion�������ƺ�������ϵͳ֪����μ���һ������������һ������������
	private TextureRegion mFaceTextureRegion;			

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
				
		this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, 
				this, "fish_1.png", 0, 0);

		this.mEngine.getTextureManager().loadTexture(this.mBitmapTextureAtlas);
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene();
		scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));

		/* Calculate the coordinates for the face, so its centered on the camera. */
		final int centerX = (CAMERA_WIDTH - this.mFaceTextureRegion.getWidth()) / 2;
		final int centerY = (CAMERA_HEIGHT - this.mFaceTextureRegion.getHeight()) / 2;

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
		final Sprite face = new Sprite(centerX, centerY, this.mFaceTextureRegion);
		scene.attachChild(face);
		
		///final ChangeableText text = new ChangeableText(5, 5, ,
        //        "0.0", 5);

		return scene;
	}

	@Override
	public void onLoadComplete() {

	}
}
