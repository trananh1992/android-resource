package com.fishjoy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fishjoy.FishJoy.EditTextEnterFilter;
import com.fishjoy.game.GameControl;
import com.fishjoy.game.GamePlay;

public class GameMode extends Activity {
    /** Called when the activity is first created. */
	
	private Button simpleButton, commonButton, hardButton, backButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamemode);
        final AlertDialog.Builder builder = new Builder(this); 
        builder.setTitle("用户登录(输入为空将使用default用户)"); 
        final EditText editText = new EditText(this);

        editText.setFilters(new InputFilter[]{new EditTextEnterFilter.LengthFilter(5)});
        
        builder.setView(editText); 
        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (editText.getText().toString().length() > 0) {
					GameControl.playerName = editText.getText().toString();
				}
				else {
					GameControl.playerName = "default";
				}
			}
		}); 
        builder.setMessage("输入用户名(不超过5个字符)"); 
        builder.show();
        
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
				GameControl.GAMEMODE = 1;
				Intent intent = new Intent();
				intent.setClass(GameMode.this, GamePlay.class);
				startActivity(intent);
			}
		});
		
		commonButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				GameControl.GAMEMODE = 2;
				Intent intent = new Intent();
				intent.setClass(GameMode.this, GamePlay.class);
				startActivity(intent);
			}
		});

		hardButton.setOnClickListener(new OnClickListener() {
	
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				GameControl.GAMEMODE = 3;
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
