package com.fishjoy.model;

import android.app.Activity;
import android.util.DisplayMetrics;

// ϵͳ���õ��ĸ��ֳ���

public interface GameParas
{
	public static final int FishKindsNum = 5;		// �������
	public static final int CAMERA_WIDTH = 800;
	public static final int CAMERA_HEIGHT = 480;
	public static final float fishSpeed[] = {40, 50, 60, 70, 80};
	public static final float fishRegion[][] = {{40, 50}, {60, 70}, {128}};
	
	// ��ȡ�û��ֻ�����Ļ�ֱ���
	/*	public int getDisplayWidth(Activity act)
	{
		DisplayMetrics dm = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        return width;
	}*/
}

