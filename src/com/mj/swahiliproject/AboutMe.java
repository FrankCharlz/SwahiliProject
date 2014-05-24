package com.mj.swahiliproject;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class AboutMe extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_bar);
		
		TextView shox = (TextView) findViewById(R.id.textViewBar2);
		
		shox.setText("Ya me mooow");
		shox.setBackgroundColor(Color.parseColor("#aa0101ff"));
		shox.setTextColor(Color.GREEN);
		
		
	}
	

}
