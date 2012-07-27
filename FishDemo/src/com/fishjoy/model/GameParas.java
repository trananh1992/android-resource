package com.fishjoy.model;

import android.app.Activity;
import android.util.DisplayMetrics;

// 系统中用到的各种常量

public interface GameParas
{
	public static final int FishKindsNum = 5;		// 鱼的种类
	public static final int CAMERA_WIDTH = 800;
	public static final int CAMERA_HEIGHT = 480;
	public static final float fishSpeed[] = {40, 50, 60, 70, 80};
	public static final float fishRegion[][] = {{40, 50}, {60, 70}, {128}};
	
	// 获取用户手机的屏幕分辨率
	/*	public int getDisplayWidth(Activity act)
	{
		DisplayMetrics dm = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        return width;
	}*/
}

