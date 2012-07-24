package com.yinzch.chapter03.resources;

import com.yinzch.chapter03.R;
import com.yinzch.chapter03.R.layout;
import com.yinzch.chapter03.R.menu;

import android.content.res.Resources;
import android.widget.Button;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class TestDimension extends Activity {

	private Button myButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimension);
        // 直接获得Resources实例
        Resources r = getResources();
        float w = r.getDimension(R.dimen.btn_width);
        float h = r.getDimension(R.dimen.btn_height);
        
        myButton = (Button)findViewById(R.id.testDimen02);
        myButton.setWidth((int)w);
        myButton.setHeight((int)h);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_dimension, menu);
        return true;
    }

    
}
