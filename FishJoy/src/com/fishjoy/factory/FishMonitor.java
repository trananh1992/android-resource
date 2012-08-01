package com.fishjoy.factory;

import java.util.ArrayList;

import android.util.Log;

import com.fishjoy.Entity.Fish;


public class FishMonitor{

	private static FishMonitor singleInstance = null;
	
	// 获取工厂单例对象
	public static FishMonitor getSingleInstance()
	{
		if(singleInstance == null)
			singleInstance = new FishMonitor();
		return singleInstance;
	}
	
	// 注册监视器，监听鱼是否超出边界
	public void onFishMove(ArrayList<Fish> movingFish)
	{
		//Log.i("检测", "鱼是否出界");
		for(int i = 0; i < movingFish.size(); i++)
		{
			Fish fish = movingFish.get(i);
			if(fish.isOutOfBound())
			{
				Log.i("注意：", "鱼出界");
				fish.detachSelf();		// 从场景中清除
				movingFish.remove(i);	// 保证场景中鱼的数目与movingFishList的大小一致
				Log.i("提示：", "鱼被清除");
			}
		}
	}
}