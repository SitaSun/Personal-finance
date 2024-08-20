package com.example.test;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.Manifest;
public class SplashActivity extends Activity {
	private Handler mHandler = new Handler();
	private static final String TAG = "SplashActivity";

	ImageView imageview;
	TextView textview;
	int alpha = 255;
	int b = 0;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		imageview = (ImageView) this.findViewById(R.id.ImageView01);
		textview = (TextView) this.findViewById(R.id.TextView01);

		Log.v(TAG, "xiaoting splash start ...");

		imageview.setAlpha(alpha);//透明度

		new Thread(new Runnable() {
			public void run() {
				initApp();
				
				while (b < 2) {
					try {
						if (b == 0) {
							Thread.sleep(1000);
							b = 1;
						} else {
							Thread.sleep(50);
						}

						updateApp();

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}).start();

		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				imageview.setAlpha(alpha);
				imageview.invalidate();


			}
		};

	}

	public void updateApp() {
		alpha -= 5;

		if (alpha <= 0) {
			b = 2;
			Intent in = new Intent(this, LoginActivity.class);
			startActivity(in);
			this.finish();
		}

		mHandler.sendMessage(mHandler.obtainMessage());

	}
	
	public void initApp(){
		 BilldbHelper billdb=new BilldbHelper(this);
  	     billdb.FirstStart(); 	     
  	     billdb.close();
  	     
  		 
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return true;
			
		}
		return false;
	}
}