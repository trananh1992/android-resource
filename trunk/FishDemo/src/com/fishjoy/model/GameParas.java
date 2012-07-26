package com.fishjoy.model;

import android.app.Activity;
import android.util.DisplayMetrics;

// ϵͳ���õ��ĸ��ֳ���

public class GameParas
{
	public static final int FishKindsNum = 5;		// �������
	private static GameParas singleInstance = null; // ��ʵ��
	
	public static GameParas getInstance()
	{
		if(singleInstance == null)
			singleInstance = new GameParas();
		return singleInstance;
	}
	
	// ��ȡ�û��ֻ�����Ļ�ֱ���
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

