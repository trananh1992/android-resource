package com.fishjoy.factory;

import java.util.ArrayList;

import android.util.Log;

import com.fishjoy.Entity.Fish;


public class FishMonitor{

	private static FishMonitor singleInstance = null;
	
	// ��ȡ������������
	public static FishMonitor getSingleInstance()
	{
		if(singleInstance == null)
			singleInstance = new FishMonitor();
		return singleInstance;
	}
	
	// ע����������������Ƿ񳬳��߽�
	public void onFishMove(ArrayList<Fish> movingFish)
	{
		//Log.i("���", "���Ƿ����");
		for(int i = 0; i < movingFish.size(); i++)
		{
			Fish fish = movingFish.get(i);
			if(fish.isOutOfBound())
			{
				Log.i("ע�⣺", "�����");
				fish.detachSelf();		// �ӳ��������
				movingFish.remove(i);	// ��֤�����������Ŀ��movingFishList�Ĵ�Сһ��
				Log.i("��ʾ��", "�㱻���");
			}
		}
	}
}