package com.yinzch.chapter03.resources;

import com.yinzch.chapter03.R;
import com.yinzch.chapter03.R.layout;
import com.yinzch.chapter03.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class TestXml extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_xml, menu);
        return true;
    }

    
}
