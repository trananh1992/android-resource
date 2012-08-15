package com.fishjoy;

import com.fishjoy.game.GameControl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Setting extends Activity {
    /** Called when the activity is first created. */
	private Button musiceffect, bgmusic, back;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        
        initialize();
    }
    
    private void initialize() {
		findViews();
		if (!GameControl.musiceffect) {
			musiceffect.setBackgroundResource(R.drawable.off_1);
		}
		else {
			musiceffect.setBackgroundResource(R.drawable.on_1);
		}
		
		if (!GameControl.musicNeeded) {
			bgmusic.setBackgroundResource(R.drawable.off_1);
		}
		else {
			bgmusic.setBackgroundResource(R.drawable.on_1);
		}
		setListeners();
	}
    
    private void findViews() {
		musiceffect = (Button) findViewById(R.id.musiceffect);
		bgmusic = (Button) findViewById(R.id.bgmusic);
		back = (Button) findViewById(R.id.settingback);
	}
    
    private void setListeners() {
		musiceffect.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeMEButton();
			}
		});
		
		bgmusic.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeBGButton();
			}
		});
		
		back.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
    
    private void changeMEButton() {
    	if (GameControl.musiceffect) {
			musiceffect.setBackgroundResource(R.drawable.off_1);
			GameControl.musiceffect = false;
		}
		else {
			musiceffect.setBackgroundResource(R.drawable.on_1);
			GameControl.musiceffect = true;
		}
	}
    
    private void changeBGButton() {
    	if (GameControl.musicNeeded) {
			bgmusic.setBackgroundResource(R.drawable.off_1);
			GameControl.musicNeeded = false;
		}
		else {
			bgmusic.setBackgroundResource(R.drawable.on_1);
			GameControl.musicNeeded = true;
		}
	}
}
