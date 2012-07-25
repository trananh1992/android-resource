package org.andengine.learn;

import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import android.util.Log;
import android.widget.Toast;

public class MovingBallActivity extends BaseGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;

	private static final float DEMO_VELOCITY = 100.0f;

	// ===========================================================
	// Fields
	// ===========================================================

	private Camera mCamera;

	private BitmapTextureAtlas mBitmapTextureAtlas;
	private TiledTextureRegion mFaceTextureRegion;

	@Override
	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, 
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
	}

	@Override
	public void onLoadResources() {
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(64, 32, 
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				this.mBitmapTextureAtlas, this, "gfx/face_circle_tiled.png", 0, 0, 2, 1);

		this.mEngine.getTextureManager().loadTexture(this.mBitmapTextureAtlas);
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene();
		scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));

		final int centerX = (CAMERA_WIDTH - this.mFaceTextureRegion.getWidth()) / 2;
		final int centerY = (CAMERA_HEIGHT - this.mFaceTextureRegion.getHeight()) / 2;
		
		final Ball ball = new Ball(centerX, centerY, this.mFaceTextureRegion);
		
		// 构建精灵的运行机制：PhysicalHandler,设置精灵的行为
		 final PhysicsHandler physicsHandler = new PhysicsHandler(ball);
		 ball.registerUpdateHandler(physicsHandler);
		 //physicsHandler.setVelocity(DEMO_VELOCITY, -DEMO_VELOCITY);
		 
		// 帧交替
		ball.animate(100);
		scene.attachChild(ball);
		return scene;
	}

	@Override
	public void onLoadComplete() {

	}

	private static class Ball extends AnimatedSprite {
		// 精灵类的PhysicsHandler成员
		private final PhysicsHandler mPhysicsHandler;

		public Ball(final float pX, final float pY, final TiledTextureRegion pTextureRegion) {
			super(pX, pY, pTextureRegion);
			this.mPhysicsHandler = new PhysicsHandler(this);
			this.mPhysicsHandler.setVelocity(DEMO_VELOCITY, -DEMO_VELOCITY);
			this.registerUpdateHandler(this.mPhysicsHandler);
		}

		// 动作处理器
		@Override
		protected void onManagedUpdate(final float pSecondsElapsed) {
			
			//Log.e("Fuck!", "onManageUpdate the Sprite!");			// 通过这条提示信息，可以看到该函数一直在更新
			
			if(this.mX < 0) {
				//Toast.makeText(MovingBallActivity, "Hello", Toast.LENGTH_SHORT).show();
				Log.e("触碰边界!","X < 0");
				this.mPhysicsHandler.setVelocityX(DEMO_VELOCITY);
			} else if(this.mX + this.getWidth() > CAMERA_WIDTH) {
				Log.e("触碰边界!","宽度溢出");
				this.mPhysicsHandler.setVelocityX(-DEMO_VELOCITY);
			}

			if(this.mY < 0) {
				Log.e("触碰边界!","Y < 0");
				this.mPhysicsHandler.setVelocityY(DEMO_VELOCITY);
			} else if(this.mY + this.getHeight() > CAMERA_HEIGHT) {
				Log.e("触碰边界!","高度溢出");
				this.mPhysicsHandler.setVelocityY(-DEMO_VELOCITY);
			}

			super.onManagedUpdate(pSecondsElapsed);
		}
	}
}

