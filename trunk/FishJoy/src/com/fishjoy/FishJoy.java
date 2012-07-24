package com.fishjoy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FishJoy extends Activity {
    /** Called when the activity is first created. */
	
	private Button helpButton, settingButton, gameModeButton, rankingButton, exitButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initialize();
    }
    
    private void initialize() {
		findViews();
		setListeners();
	}
    
    private void findViews() {
		helpButton = (Button) findViewById(R.id.help);
		settingButton = (Button) findViewById(R.id.setting);
		gameModeButton = (Button) findViewById(R.id.begin);
		rankingButton = (Button) findViewById(R.id.ranking);
		exitButton = (Button) findViewById(R.id.exit);
	}
    
    private void setListeners() {
		helpButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(FishJoy.this, Helper.class);
				startActivity(intent);
			}
		});
		
		settingButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(FishJoy.this, Setting.class);
				startActivity(intent);
			}
		});
		
		gameModeButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(FishJoy.this, GameMode.class);
				startActivity(intent);
			}
		});
		
		rankingButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(FishJoy.this, Ranking.class);
				startActivity(intent);
			}
		});

		exitButton.setOnClickListener(new OnClickListener() {
		
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}