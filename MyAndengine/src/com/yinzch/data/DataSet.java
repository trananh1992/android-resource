package com.yinzch.data;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.widget.Toast;

public class DataSet {

	private static DataSet singleInstance = null;
	
	public static DataSet getInstance()
	{
		if(singleInstance == null)
			singleInstance = new DataSet();
		return singleInstance;
	}
	
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
