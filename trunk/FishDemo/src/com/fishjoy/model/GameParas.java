package com.fishjoy.model;

import android.app.Activity;
import android.util.DisplayMetrics;

// 系统中用到的各种常量

public class GameParas
{
	public static final int FishKindsNum = 5;		// 鱼的种类
	private static GameParas singleInstance = null; // 单实例
	
	public static GameParas getInstance()
	{
		if(singleInstance == null)
			singleInstance = new GameParas();
		return singleInstance;
	}
	
	// 获取用户手机的屏幕分辨率
	public int getDisplayWidth(Activity act)
	{
		DisplayMetrics dm = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        return width;
	}
	
	public int getDisplayHeight(Activity act)
	{
		DisplayMetrics dm = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(dm);

        int height = dm.heightPixels;
        return height;
	}

}

