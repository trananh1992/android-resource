package com.fishjoy.model;

import android.app.Activity;
import android.util.DisplayMetrics;

// ϵͳ���õ��ĸ��ֳ���

public interface GameParas
{
	public static final int FishKindsNum = 5;		// �������
	public static final int MaxFishNum = 1;
	public static final int CAMERA_WIDTH = 800;
	public static final int CAMERA_HEIGHT = 480;
	// һ�����������˳���Ӧ(Fish.Id),����ͨ��Id�����Ӧ�������
	//����ٶ�	
	public static final float fishSpeed[] = {40, 50, 60, 70, 80};	
	//��ķ�Χ��TiledTextRegion.getWidth()/getHeight()
	public static final float fishRegion[][] = {{60,30}, {55,36}, {112,50}, {80,48}, {115,51}};
	// ����ζ�����
	public static final String fishDir[] = {"Left", "Right", "Up", "Down"};
	// ��ȡ�û��ֻ�����Ļ�ֱ���
	/*	public int getDisplayWidth(Activity act)
	{
		DisplayMetrics dm = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        return width;
	}*/
}

