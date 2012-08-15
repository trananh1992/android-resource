package com.fishjoy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class FishJoy extends Activity {
    /** Called when the activity is first created. */
	
	private Button helpButton, settingButton, gameModeButton, rankingButton, exitButton, aboutButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initialize();
    }
    
    public class EditTextEnterFilter implements InputFilter{

        private Context context;
        public EditTextEnterFilter(Context context,String str){   
            this.context = context;
        }   

        public CharSequence filter(CharSequence src, int start, int end,Spanned dest, int dstart, int dend) {
            boolean bool = src.equals("\n");  
            if(!bool){
                return dest.subSequence(dstart, dstart)+src.toString();
            }
            Toast.makeText(context, "不能输入回车", Toast.LENGTH_SHORT).show();
            return dest.subSequence(dstart, dend);
        }
    }
    
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
    	if (event.getAction() == KeyEvent.ACTION_DOWN)
	    	switch (event.getKeyCode()) {
	        case KeyEvent.KEYCODE_DPAD_CENTER:
	        case KeyEvent.KEYCODE_ENTER:   
	            return true;
	        }
        return super.dispatchKeyEvent(event);
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
		aboutButton = (Button) findViewById(R.id.about);
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
		
		aboutButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(FishJoy.this, About.class);
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