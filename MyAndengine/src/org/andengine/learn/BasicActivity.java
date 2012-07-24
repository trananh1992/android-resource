package org.andengine.learn;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.entity.primitive.Line;

import android.util.Log;

public class BasicActivity extends BaseGameActivity {
	public static final String TAG = "你好，";
	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;
	
	private Camera camera;
	@Override
	public Engine onLoadEngine() {
		Log.e(TAG,"onLoadEngine" );
		this.camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, 
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.camera));
	}

	@Override
	public void onLoadResources() {
		Log.e(TAG,"onLoadResources" );
		// TODO Auto-generated method stub
		
	}

	@Override
	public Scene onLoadScene() {
		Log.e(TAG,"onLoadScene" );
		//this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene(1);
		// 设置背景颜色
		scene.setBackground(new ColorBackground(0, 0, 8784f));
		// 画一条线
		final Line line = new Line(0, 240, 720, 240, 5.0f);
		line.setColor(1, 0, 0);
		// 初始化scene时不指定层数会出错
		scene.getFirstChild().attachChild(line);
		
		return scene;
	}

	@Override
	public void onLoadComplete() {
		Log.e(TAG,"onLoadComplete" );
		// TODO Auto-generated method stub
		
	}
}
