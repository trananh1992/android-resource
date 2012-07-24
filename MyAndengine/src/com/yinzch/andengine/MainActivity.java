package com.yinzch.andengine;

import org.andengine.learn.BasicActivity;
import org.andengine.learn.FpsActivity;
import org.andengine.learn.MovingBallActivity;
import org.andengine.learn.Sprite01;
import org.andengine.learn.TankActivity;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        // 不能再设置布局
        //setContentView(R.layout.activity_main);
        String[] items = {"Basic Activity", "FPS Activity", "Sprite 01", "动画精灵：移动的小球",
        		"移动的坦克"};
        setListAdapter(new ArrayAdapter<String>(this,
        		android.R.layout.simple_list_item_1, items));
        getListView().setTextFilterEnabled(true);
    }
    
    // 响应菜单项单击事件:重载
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
    	Intent intent = null;			//初始化对象为null
    	switch(position)
    	{
    	case 1:
    		intent = new Intent(MainActivity.this, FpsActivity.class);
    		startActivity(intent);
    		break;
    	case 0:
    		intent = new Intent(MainActivity.this, BasicActivity.class);
    		startActivity(intent);
    		break;
    	case 2:
    		intent = new Intent(MainActivity.this, Sprite01.class);
    		startActivity(intent);
    		break;
    	case 3:
    		Toast.makeText(MainActivity.this, "TiledTextureRegion", Toast.LENGTH_LONG).show();
    		intent = new Intent(MainActivity.this, MovingBallActivity.class);
    		startActivity(intent);
    		break;
    	case 4:
    		intent = new Intent(MainActivity.this, TankActivity.class);
    		startActivity(intent);
    		break;
    		
    	default:
    			break;
    	}
    }
    
}
