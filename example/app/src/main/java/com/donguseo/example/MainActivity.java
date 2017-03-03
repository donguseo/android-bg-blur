package com.donguseo.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.donguseo.blur.BlurDrawable;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		View v = findViewById(R.id.image);
		View bv = findViewById(R.id.background);

		BlurDrawable bd = new BlurDrawable();
		bd.setBGView(bv);
		bd.setView(v);
		v.setBackgroundDrawable(bd);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

}
