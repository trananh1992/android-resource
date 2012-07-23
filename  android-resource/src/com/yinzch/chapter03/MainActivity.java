package com.yinzch.chapter03;

import com.yinzch.chapter03.resources.TestBitmap;
import com.yinzch.chapter03.resources.TestColor;
import com.yinzch.chapter03.resources.TestDimension;
import com.yinzch.chapter03.resources.TestLayout;
import com.yinzch.chapter03.resources.TestMenu;
import com.yinzch.chapter03.resources.TestString;
import com.yinzch.chapter03.resources.TestXml;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

import android.app.ListActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;

// 创建ListView既可以使用组件（在资源中）也可以继承ListActivity（在代码中）
public class MainActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 不能再设置布局
        //setContentView(R.layout.activity_main);
        String[] items = {"Test Color","Test String","Test Dimension","Test XML","Test Bitmap","Test Menu","Test Layout"};
        setListAdapter(new ArrayAdapter<String>(this,
        		android.R.layout.simple_list_item_1, items));
        getListView().setTextFilterEnabled(true);
    }

    // 响应菜单项单击事件:重载
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
    	Intent intent = null;
    	switch(position)
    	{
    	case 0:
    		intent = new Intent(MainActivity.this, TestColor.class);
    		startActivity(intent);
    		break;
    	case 1:
    		intent = new Intent(MainActivity.this, TestString.class);
    		startActivity(intent);
    		break;
    	case 2:
    		intent = new Intent(MainActivity.this, TestDimension.class);
    		startActivity(intent);
    		break;
    	case 3:
    		intent = new Intent(MainActivity.this, TestXml.class);
    		startActivity(intent);
    		break;
    	case 4:
    		intent = new Intent(MainActivity.this, TestBitmap.class);
    		startActivity(intent);
    		break;
    	case 5:
    		intent = new Intent(MainActivity.this, TestMenu.class);
    		startActivity(intent);
    		break;
    	case 6:
    		intent = new Intent(MainActivity.this, TestLayout.class);
    		startActivity(intent);
    		break;
    	}
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}
