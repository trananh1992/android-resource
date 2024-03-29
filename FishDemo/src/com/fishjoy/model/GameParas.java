package com.fishjoy.model;

import android.app.Activity;
import android.util.DisplayMetrics;

// 系统中用到的各种常量

public interface GameParas
{
	public static final int FishKindsNum = 5;		// 鱼的种类
	public static final int MaxFishNum = 30;
	public static final int CAMERA_WIDTH = 800;
	public static final int CAMERA_HEIGHT = 480;
	// 一下数组参数的顺序对应(Fish.Id),可以通过Id获得相应鱼的数据
	//鱼的速度	
	public static final float fishSpeed[] = {120, 50, 60, 70, 80};	
	//鱼的范围：TiledTextRegion.getWidth()/getHeight()
	public static final float fishRegion[][] = {{60,30}, {55,36}, {112,50}, {80,48}, {115,51}};
	// 鱼的游动方向
	public static final String fishDir[] = {"Left", "Right", "Up", "Down"};
	
	public static final int groupWay[][] = {{0,0}, {0,1}, {0,2}, {1,0}, {1,1}, {1,2}};
	
	public static final int diamond[][] = {{0,0,1,0,0}, {0,1,0,1,0}, {1,0,0,0,1}, {0,1,0,1,0}, {0,0,1,0,0}};
	
	public static final int fishJoy[][] = {{1,1,1},{1,0,0},{1,1,0},{1,0,0},{1,0,0},
		{1,1,1},{0,1,0},{0,1,0},{0,1,0},{1,1,1},
		{0,1,1,1},{1,0,0,0},{0,1,1,0},{0,0,0,1},{1,1,1,0},
		{1,0,0,1},{1,0,0,1},{1,1,1,1},{1,0,0,1},{1,0,0,1},
		{0,1,1},{0,0,1},{0,0,1},{1,0,1},{0,1,1},
		{0,1,1,1,0},{1,0,0,0,1},{1,0,0,0,1},{1,0,0,0,1},{0,1,1,1,0},
		{1,0,0,0,1},{0,1,0,1,0},{0,0,1,0,0},{0,0,1,0,0},{0,0,1,0,0}};
	
	// 获取用户手机的屏幕分辨率
	/*	public int getDisplayWidth(Activity act)
	{
		DisplayMetrics dm = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        return width;
	}*/
}

