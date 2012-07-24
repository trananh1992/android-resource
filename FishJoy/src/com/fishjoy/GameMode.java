package com.fishjoy;

import com.fishjoy.game.GamePlay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GameMode extends Activity {
    /** Called when the activity is first created. */
	
	private Button simpleButton, commonButton, hardButton, backButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamemode);
        
        initialize();
    }
    
    private void initialize() {
		findViews();
		setListeners();
	}
    
    private void findViews() {
		simpleButton = (Button) findViewById(R.id.simple);
		commonButton = (Button) findViewById(R.id.common);
		hardButton = (Button) findViewById(R.id.hard);
		backButton = (Button) findViewById(R.id.back);
	}
    
    private void setListeners() {
		simpleButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(GameMode.this, GamePlay.class);
				startActivity(intent);
			}
		});
		
		commonButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(GameMode.this, GamePlay.class);
				startActivity(intent);
			}
		});

		hardButton.setOnClickListener(new OnClickListener() {
	
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(GameMode.this, GamePlay.class);
				startActivity(intent);
			}
		});

		backButton.setOnClickListener(new OnClickListener() {
		
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
