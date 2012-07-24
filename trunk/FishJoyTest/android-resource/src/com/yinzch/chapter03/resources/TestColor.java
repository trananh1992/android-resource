package com.yinzch.chapter03.resources;

import com.yinzch.chapter03.R;
import com.yinzch.chapter03.R.layout;
import com.yinzch.chapter03.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.TextView;

public class TestColor extends Activity {

	// �˵����
	private static final int ITEM1 = Menu.FIRST;
	private static final int ITEM2 = Menu.FIRST + 1;
	private static final int ITEM3 = Menu.FIRST + 2;
	private static final int ITEM4 = Menu.FIRST + 3;
	private static final int ITEM5 = Menu.FIRST + 4;
	private static final int ITEM6 = Menu.FIRST + 5;
	
	private TextView tv;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        // ���ñ���ɫ
        getWindow().setBackgroundDrawableResource(R.color.red_bg);
        
        // ΪTextViewע�������Ĳ˵�
        tv = (TextView)findViewById(R.id.TextView03);
        registerForContextMenu(tv);
    }
    
    // ���������Ĳ˵����Ӳ˵�
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo info)
    {
    	SubMenu sm = menu.addSubMenu(0, ITEM5, 0, "���ñ�����ɫ");
    	sm.add(0, ITEM6, 0, "��ɫ����");
    	sm.add(0, ITEM1, 0, "��ɫ����");			//ITEM1�ں��汻���ã��Բ��Բ�ͬ�˵���Ӧ�ܷ����ͻ
    	menu.add("�˳�");
    }
    
    //
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
    	switch(item.getItemId())
    	{
    	// ��֪��SubMenu�Ƿ����Ӧ
    	case ITEM5:
    		setTitle("��ѡ��TextView�ı�����ɫ");
    		break;
    	case ITEM6:
    		// ΪTextView���ñ�����ɫ
    		tv.setBackgroundColor(Color.GREEN);
    		break;
    	case ITEM1:
    		tv.setBackgroundColor(Color.YELLOW);
    		break;
    	}
    	return true;
    }
    
    // ����ѡ��˵������Ӳ˵�
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_color, menu);
    	// ��Ӳ˵�����Ӳ˵�
        menu.add(0, ITEM1, 0, "��ʼ");
        menu.add(0, ITEM2, 0, "�˳�");
        // Ϊ�Ӳ˵���Ӳ˵���
        SubMenu help = menu.addSubMenu("Help");
        help.add(0, ITEM3, 0, "Abort");
        help.add(0, ITEM4, 0, "Exit");
        return true;
    }
    
    // ��Ӧ�Ӳ˵��Ͳ˵�����¼�
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch(item.getItemId())
    	{
    		case ITEM1:
    			// ����Activity����
    			setTitle("��ʼ��Ϸ");
    			break;
    		case ITEM2:
    			setTitle("�˳���Ϸ");
    			break;
    		case ITEM3:
    			setTitle("Abort Deny");
    			break;
    		case ITEM4:
    			setTitle("Exit Deny");
    			break;
    	}
    	return true;
    }
}






