package com.fishjoy;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.fishjoy.data.MyDbAdapter;

public class Ranking extends Activity {
    /** Called when the activity is first created. */
	private TextView scoreTextViewe;
	private TextView scoreTextViewn;
	private TextView scoreTextViewh;
	private Button rankBackButton;
	
	public MyDbAdapter db;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking);
        
        db = new MyDbAdapter(this);
        db.open();
        
        scoreTextViewe = (TextView) findViewById(R.id.scoreviewe);
        scoreTextViewn = (TextView) findViewById(R.id.scoreviewn);
        scoreTextViewh = (TextView) findViewById(R.id.scoreviewh);
        rankBackButton = (Button) findViewById(R.id.rankback);
        
        rankBackButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
        
        Cursor c = db.fetchData(1);
        c.moveToFirst();
        if (c.getCount() > 0) {
			for (int i = 0; i < c.getCount(); i++) {
				scoreTextViewe.setText("第" + (c.getCount()-i) + "名" + "   " + c.getString(1).trim() + "\n得分      " + c.getInt(2) + "\n" + "\n" + scoreTextViewe.getText() );
				c.moveToNext();
			}
		}
        
        c = db.fetchData(2);
        c.moveToFirst();
        if (c.getCount() > 0) {
			for (int i = 0; i < c.getCount(); i++) {
				scoreTextViewn.setText("第" + (c.getCount()-i) + "名" + "   " + c.getString(1) + "\n得分      " + c.getInt(2) + "\n" + "\n" + scoreTextViewn.getText() );
				c.moveToNext();
			}
		}
        
        c = db.fetchData(3);
        c.moveToFirst();
        if (c.getCount() > 0) {
			for (int i = 0; i < c.getCount(); i++) {
				scoreTextViewh.setText("第" + (c.getCount()-i) + "名" + "   " + c.getString(1) + "\n得分      " + c.getInt(2) + "\n" + "\n" + scoreTextViewh.getText());
				c.moveToNext();
			}
		}
        db.close();
    }
}