package org.andengine.learn;

import android.util.Log;

public class FishMonitor {
	
	private static FishMonitor fm = null;
	
	public static FishMonitor getInstance()
	{
		if(fm == null)
			fm = new FishMonitor();
		return fm;
	}
	
	public Boolean moveMonite(Fish f)
	{
		if(f.getX() > 240)
		{
			if(f.detachSelf())
			{
				Log.i("FishMonitor", "��һ�γɹ�ɾ����");
				return true;
			}
		}
		return false;
	}

}
