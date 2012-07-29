package org.andengine.learn;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.DelayModifier;
import org.anddev.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.anddev.andengine.entity.modifier.LoopEntityModifier;
import org.anddev.andengine.entity.modifier.LoopEntityModifier.ILoopEntityModifierListener;
import org.anddev.andengine.entity.modifier.ParallelEntityModifier;
import org.anddev.andengine.entity.modifier.RotationByModifier;
import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.LoopModifier;

import android.widget.Toast;

/**
 * Modifier�൱��IUpdateHandler����ǿ�棬����ӵ��onUpdate��ҵ����·�����
 * ��������״̬�������ܡ��Զ�ע�����ܺͳ���ʱ�������
 * ��IUpdateHandler����ҵ����£���ص�������һEntityModifier�ķ�ʽ��ҵ����и���
 * Modifierֻ��ʵ�����������࣬�ֱ���LoopModifier��SequenceModifier��ParallelModifier��
 * �޸�����͸���ȱ仯��AlphaModifier������С���ţ�ScaleModifier��
 * ����޸���ע�ᵽʵ�壬������ҵ���߳��н����޸���
 */

public class EntityModifier extends BaseGameActivity {

	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;

	private Camera mCamera;
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private TiledTextureRegion mFaceTextureRegion;

	@Override
	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
	}

	@Override
	public void onLoadResources() {
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(64, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "gfx/face_box_tiled.png", 0, 0, 2, 1);

		this.mEngine.getTextureManager().loadTexture(this.mBitmapTextureAtlas);
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene();
		scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));

		final int centerX = (CAMERA_WIDTH - this.mFaceTextureRegion.getWidth()) / 2;
		final int centerY = (CAMERA_HEIGHT - this.mFaceTextureRegion.getHeight()) / 2;

		final Rectangle rect = new Rectangle(centerX + 100, centerY, 32, 32);
		rect.setColor(1, 0, 0);

		final AnimatedSprite face = new AnimatedSprite(centerX - 100, centerY, this.mFaceTextureRegion);
		face.animate(100);
		face.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		// ����ʵ���޸�������ҵ���߳��и���ʵ��״̬ 
		final LoopEntityModifier entityModifier =
			new LoopEntityModifier(
					//EntityModifier�ļ�����֪ͨLoopEntityModifier�Ŀ�ʼ�ͽ���  
					new IEntityModifierListener() {
						@Override
						public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {
							// ��ʵ����£�ҵ���̣߳��е�����Ⱦ�̣߳���ʾ��Ϣ
							EntityModifier.this.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(EntityModifier.this, "Sequence started.", Toast.LENGTH_SHORT).show();
								}
							});
						}

						@Override
						public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
							EntityModifier.this.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(EntityModifier.this, "Sequence finished.", Toast.LENGTH_SHORT).show();
								}
							});
						}
					},
					2,	// ����ѭ��
						// ѭ���ļ�����֪ͨÿ��ѭ���Ŀ�ʼ�ͽ��� 
					new ILoopEntityModifierListener() {
						@Override
						public void onLoopStarted(final LoopModifier<IEntity> pLoopModifier, final int pLoop, final int pLoopCount) {
							EntityModifier.this.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(EntityModifier.this, "Loop: '" + (pLoop + 1) + "' of '" + pLoopCount + "' started.", Toast.LENGTH_SHORT).show();
								}
							});
						}

						@Override
						public void onLoopFinished(final LoopModifier<IEntity> pLoopModifier, final int pLoop, final int pLoopCount) {
							EntityModifier.this.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(EntityModifier.this, "Loop: '" + (pLoop + 1) + "' of '" + pLoopCount + "' finished.", Toast.LENGTH_SHORT).show();
								}
							});
						}
					},
					//ѭ��Modifier����ϵ�Modifier���Ȱ�˳��ִ��  
					new SequenceEntityModifier(
							new RotationModifier(1, 0, 90),			// 1:�ڵ�һ��ѭ����ִ��
							new AlphaModifier(2, 1, 0),
							new AlphaModifier(1, 0, 1),
							new ScaleModifier(2, 1, 0.5f),
							new DelayModifier(0.5f),
							//����ִ��
							new ParallelEntityModifier(
									new ScaleModifier(3, 0.5f, 5),
									new RotationByModifier(3, 90)
							),
							new ParallelEntityModifier(
									new ScaleModifier(3, 5, 1),
									new RotationModifier(3, 180, 0)
							)
					)
			);

		face.registerEntityModifier(entityModifier);
		rect.registerEntityModifier(entityModifier.clone());

		scene.attachChild(face);
		scene.attachChild(rect);

		return scene;
	}

	@Override
	public void onLoadComplete() {

	}
}

