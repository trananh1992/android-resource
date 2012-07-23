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
        // ʵ����MenuInflater�������ڴ����˵�
        mi = new MenuInflater(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_menu, menu);
    	// ���ò˵���Դ,�����˵�
    	mi.inflate(R.menu.activity_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch(item.getItemId())
    	{
    	case R.id.about:
    		aboutAlert("��ʾ��δ����˵�");
    		break;
    	case R.id.exit:
    		exitAlert("���Ҫ�˳���");
    		break;
    	}
    	return true;
    }
    
    public void exitAlert(String msg)
    {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(msg)
    		.setCancelable(false)
    		.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					// ������Activity���ص�ջ�е�ǰһ��Activity
					finish();
				}
			})
			.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {	
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					// ����������
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
    		.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
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
















