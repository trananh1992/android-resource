package com.yinzch.chapter03.resources;

import com.yinzch.chapter03.R;
import com.yinzch.chapter03.R.layout;
import com.yinzch.chapter03.R.menu;

import android.os.Bundle;
import android.app.TabActivity;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;

public class TabhostActivity extends TabActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
       /* requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        
        TabHost th = getTabHost();
        // inflate:
        LayoutInflater.from(this).inflate(R.layout.activity_tabhost, th.getTabContentView(), true);
        
        th.addTab(th.newTabSpec("all").setIndicator("所有通话记录").setContent(R.id.TextView01));
        th.addTab(th.newTabSpec("ok").setIndicator("已接来电").setContent(R.id.TextView02));
        th.addTab(th.newTabSpec("cancel").setIndicator("未接来电").setContent(R.id.TextView03));
    
        // 添加事件监听器
        th.setOnTabChangedListener(
        		new OnTabChangeListener() {
					@Override
					public void onTabChanged(String tabId) {
						Toast.makeText(TabhostActivity.this, tabId, Toast.LENGTH_LONG).show();
					}
				}
        );
    }
    
}
