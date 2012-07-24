package com.yinzch.chapter03.resources;

import com.yinzch.chapter03.R;
import com.yinzch.chapter03.R.layout;
import com.yinzch.chapter03.R.menu;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class TestMenu extends Activity {

	private MenuInflater mi;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        // 实例化MenuInflater对象，用于创建菜单
        mi = new MenuInflater(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_menu, menu);
    	// 引用菜单资源,创建菜单
    	mi.inflate(R.menu.activity_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch(item.getItemId())
    	{
    	case R.id.about:
    		aboutAlert("演示如何创建菜单");
    		break;
    	case R.id.exit:
    		exitAlert("真的要退出吗？");
    		break;
    	}
    	return true;
    }
    
    public void exitAlert(String msg)
    {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(msg)
    		.setCancelable(false)
    		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					// 结束该Activity，回到栈中的前一个Activity
					finish();
				}
			})
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {	
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					// 返回主界面
					return;
				}
			});
    	AlertDialog alert = builder.create();
    	alert.show();
    }
    
    public void aboutAlert(String str)
    {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(str)
    		.setCancelable(false)
    		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					//return;
				}
			});
    	AlertDialog alert = builder.create();
    	alert.show();
    }
}
















