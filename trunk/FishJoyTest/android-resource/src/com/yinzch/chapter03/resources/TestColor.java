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

	// 菜单项常量
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
        // 设置背景色
        getWindow().setBackgroundDrawableResource(R.color.red_bg);
        
        // 为TextView注册上下文菜单
        tv = (TextView)findViewById(R.id.TextView03);
        registerForContextMenu(tv);
    }
    
    // 创建上下文菜单和子菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo info)
    {
    	SubMenu sm = menu.addSubMenu(0, ITEM5, 0, "设置背景颜色");
    	sm.add(0, ITEM6, 0, "绿色背景");
    	sm.add(0, ITEM1, 0, "黄色背景");			//ITEM1在后面被重用，以测试不同菜单响应能否处理冲突
    	menu.add("退出");
    }
    
    //
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
    	switch(item.getItemId())
    	{
    	// 不知道SubMenu是否会响应
    	case ITEM5:
    		setTitle("请选择TextView的背景颜色");
    		break;
    	case ITEM6:
    		// 为TextView设置背景颜色
    		tv.setBackgroundColor(Color.GREEN);
    		break;
    	case ITEM1:
    		tv.setBackgroundColor(Color.YELLOW);
    		break;
    	}
    	return true;
    }
    
    // 创建选项菜单及其子菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_color, menu);
    	// 添加菜单项或子菜单
        menu.add(0, ITEM1, 0, "开始");
        menu.add(0, ITEM2, 0, "退出");
        // 为子菜单添加菜单项
        SubMenu help = menu.addSubMenu("Help");
        help.add(0, ITEM3, 0, "Abort");
        help.add(0, ITEM4, 0, "Exit");
        return true;
    }
    
    // 响应子菜单和菜单项单击事件
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch(item.getItemId())
    	{
    		case ITEM1:
    			// 设置Activity标题
    			setTitle("开始游戏");
    			break;
    		case ITEM2:
    			setTitle("退出游戏");
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






