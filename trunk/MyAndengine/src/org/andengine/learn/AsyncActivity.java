package org.andengine.learn;

import java.util.concurrent.Callable;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import com.yinzch.andengine.R;
import com.yinzch.andengine.R.string;
//import org.anddev.andengine.util.Callback;
import android.util.Log;

public class AsyncActivity extends BaseGameActivity {
	private static final int CAMERA_WIDTH = 320;
	private static final int CAMERA_HEIGHT = 480;

	private Camera andCamera;

	/** Called when the activity is first created. */
	@Override
	public Engine onLoadEngine() {
		// TODO Auto-generated method stub
		this.andCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		// ����Engine��ȫ����ʾ���ֻ�����Ϊ���������������졾�ɶ���׿��ѵ��
		return new Engine(new EngineOptions(true, ScreenOrientation.PORTRAIT,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT),
				this.andCamera));
	}

	@Override
	public void onLoadResources() {
		// TODO Auto-generated method stub
		this.doAsync(R.string.test1, R.string.test2, 
		//new org.anddev.andengine.util.Callable<Void>() {
		new Callable<Void>() {
			// ϣ��AndEngine�첽���ص�����
			public Void call() throws Exception {
				for (int i = 0; i < Integer.MAX_VALUE; i++) {
				}
				return null;
			}
			// ��������ɺ�ص������ڴ˽���һЩ������ϵ��º���
		}, new org.anddev.andengine.util.Callback<Void>() {
			public void onCallback(final Void pCallbackValue) {
				Log.d("Callback", "over");
			}
		});
	}

	@Override
	public Scene onLoadScene() {
		// TODO Auto-generated method stub
		final Scene scene = new Scene(1);
		return scene;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
	}
}
