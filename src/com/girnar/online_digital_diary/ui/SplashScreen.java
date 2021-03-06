package com.girnar.online_digital_diary.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.TextView;

import com.girnar.online_digital_diary.interfaces.ImportantMethod;
import com.girnar.online_digital_diary.ui.R;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;

/**
 * @author
 * 
 */
public class SplashScreen extends Activity implements
		OnFocusChangeListener, ImportantMethod {
	private int _splashTime = 3000;
	private final String TAG = "OnlineDigitalDiary";
	private TextView textView;
	private Tracker tracker;

	/** Called when the activity is first created. */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "on create called...");
		setContentView(R.layout.activity_splash_screen);
		EasyTracker.getInstance().setContext(getApplicationContext());
		tracker = EasyTracker.getTracker();
		tracker.trackView("Splash Screen_Digital_Diary");
		getIds();
		addListener();

		// thread for displaying the SplashScreen
		Thread splashTread = new Thread() {
			@Override
			public void run() {
				try {
					long lastTime = System.currentTimeMillis();
					long currTime = System.currentTimeMillis();

					long diff = (currTime - lastTime);
					if (diff < _splashTime) {
						sleep(_splashTime - diff);

					}
				} catch (InterruptedException e) {
					// do nothing
				} finally {

					handler.sendMessage(new Message());

				}
			}

		};

		splashTread.start();

	}
	
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			startActivity(new Intent(SplashScreen.this,
					LoginPageActivity.class).putExtra("auth", "simpleLogin"));
			SplashScreen.this.overridePendingTransition(
					R.anim.layout_animation_row_left_from_right_side,
					R.anim.layout_animation_row_left_slide);
			finish();
			
		};
	};
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getIds() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @return textView;
	 */
	public TextView getTextView() {
		return textView;
	}

	/**
	 * @param textView
	 */
	public void setTextView(TextView textView) {
		this.textView = textView;
	}

}
