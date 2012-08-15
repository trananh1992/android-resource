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

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
		
		this.saveData();
		
		return scene;
	}

	public void saveData()
    {
    	SharedPreferences preferences = getSharedPreferences("PathSaver", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString("path", "5,3,1,1,1,1,0,0,1,1,1,1,0,0,1,0,0");
        editor.commit();
        
        String string = preferences.getString("path", "");
        String[] stringArray = string.split("\\,");
        int[] a = new int[stringArray.length];
        
        for (int i = 0; i < stringArray.length; i++) {
			a[i] = Integer.parseInt(stringArray[i]);
		}
        for(int i = 0; i < a.length; i++)
        {
        	System.out.println(a[i]+" ");
        }
    }
	
	@Override
	public void onLoadComplete() {
		Log.e(TAG,"onLoadComplete" );
		// TODO Auto-generated method stub
		
	}
}
