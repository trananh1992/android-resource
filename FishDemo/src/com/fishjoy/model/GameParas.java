package com.fishjoy.model;

import android.app.Activity;
import android.util.DisplayMetrics;

// ϵͳ���õ��ĸ��ֳ���

public interface GameParas
{
	public static final int FishKindsNum = 5;		// �������
	public static final int MaxFishNum = 30;
	public static final int CAMERA_WIDTH = 800;
	public static final int CAMERA_HEIGHT = 480;
	// һ�����������˳���Ӧ(Fish.Id),����ͨ��Id�����Ӧ�������
	//����ٶ�	
	public static final float fishSpeed[] = {120, 50, 60, 70, 80};	
	//��ķ�Χ��TiledTextRegion.getWidth()/getHeight()
	public static final float fishRegion[][] = {{60,30}, {55,36}, {112,50}, {80,48}, {115,51}};
	// ����ζ�����
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
	
	// ��ȡ�û��ֻ�����Ļ�ֱ���
	/*	public int getDisplayWidth(Activity act)
	{
		DisplayMetrics dm = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        return width;
	}*/
}
