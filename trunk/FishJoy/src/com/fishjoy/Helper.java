package com.fishjoy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.Toast;

public class Helper extends Activity {
    /** Called when the activity is first created. */
	private Button next, returnButton, before;
	private AbsoluteLayout absoluteLayout;
	
	private int count;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helper);
        
        initialize();
    }
    
    private void initialize() {
    	count = 1;
		findViews();
		setListeners();
	}
    
    private void findViews() {
		next = (Button)findViewById(R.id.nexthelp);
		returnButton = (Button)findViewById(R.id.returnhelp);
		before = (Button)findViewById(R.id.backhelp);
		absoluteLayout = (AbsoluteLayout)findViewById(R.id.helplayout);
	}
    
    private void setListeners() {
		next.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stu
				
				if (count == 1){
					absoluteLayout.setBackgroundResource(R.drawable.help2);
					count ++;
				}
				else if (count == 2){
					absoluteLayout.setBackgroundResource(R.drawable.help3);
					count ++;
				}
				else if (count == 3){
					absoluteLayout.setBackgroundResource(R.drawable.help4);
					count ++;
				}
				else if (count == 4){
					absoluteLayout.setBackgroundResource(R.drawable.help5);
					count ++;
				}
				else if (count == 5){
					absoluteLayout.setBackgroundResource(R.drawable.help6);
					count ++;
				}
				else if (count == 6){
					absoluteLayout.setBackgroundResource(R.drawable.help7);
					count ++;
				}
				else if (count == 7){
					absoluteLayout.setBackgroundResource(R.drawable.help8);
					Toast.makeText(Helper.this, "这是最后一页帮助", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		before.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stu
				
				if (count == 1){
					absoluteLayout.setBackgroundResource(R.drawable.help1);
					Toast.makeText(Helper.this, "这已经是第一页帮助", Toast.LENGTH_SHORT).show();
				}
				else if (count == 2){
					absoluteLayout.setBackgroundResource(R.drawable.help2);
					count --;
				}
				else if (count == 3){
					absoluteLayout.setBackgroundResource(R.drawable.help3);
					count --;
				}
				else if (count == 4){
					absoluteLayout.setBackgroundResource(R.drawable.help4);
					count --;
				}
				else if (count == 5){
					absoluteLayout.setBackgroundResource(R.drawable.help5);
					count --;
				}
				else if (count == 6){
					absoluteLayout.setBackgroundResource(R.drawable.help6);
					count --;
				}
				else if (count == 7){
					absoluteLayout.setBackgroundResource(R.drawable.help7);
					count --;
				}
			}
		});
		
		returnButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
