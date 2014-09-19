package com.girnar.online_digital_diary.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.girnar.online_digital_diary.database.DbHelper;
import com.girnar.online_digital_diary.interfaces.ImportantMethod;
import com.girnar.online_digital_diary.util.Util;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;

/**
 * @author
 * 
 */
public class SendButtonActivity extends Activity implements ImportantMethod,
		OnClickListener {
	private LinearLayout friendinfo, accountinfo, otherinfo, back;
	private ImageView home;
	private Button buttonfriendinfo, buttonaccountinfo, buttonotherinfo;
	private final String TAG = "SendAccountInfoActivity";
	private DbHelper db = new DbHelper(this);
	private Tracker tracker;
	private TextView header_text;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "on create called...");
		setContentView(R.layout.activity_send_button);
		EasyTracker.getInstance().setContext(getApplicationContext());
		tracker = EasyTracker.getTracker();
		tracker.trackView("List of Send Buttons Digital_Diary");
		Util.addGoogleAds(this);
		getIds();
		addListener();
		validate();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_send_button, menu);
		return true;
	}


	@Override
	public void getIds() {
		// TODO Auto-generated method stub
		friendinfo = (LinearLayout) findViewById(R.id.imageView_send_personal_info);
		setAccountinfo((LinearLayout) findViewById(R.id.imageView_send_other_info));
		setOtherinfo((LinearLayout) findViewById(R.id.imageView_send_bookmark));
		back = (LinearLayout) findViewById(R.id.linear_layout_img_bck);
		home = (ImageView)findViewById(R.id.img_home);
		buttonfriendinfo = (Button) findViewById(R.id.friend_info);
		buttonaccountinfo = (Button) findViewById(R.id.bank_account_info);
		buttonotherinfo = (Button) findViewById(R.id.other_info);
		header_text = (TextView)findViewById(R.id.header_text);
		header_text.setText("Send Information");
	}

	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		friendinfo.setOnClickListener(this);
		back.setOnClickListener(this);
		home.setOnClickListener(this);
		accountinfo.setOnClickListener(this);
		otherinfo.setOnClickListener(this);
		buttonfriendinfo.setOnClickListener(this);
		
		buttonaccountinfo.setOnClickListener(this);
		buttonotherinfo.setOnClickListener(this);

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
//		Util.homeAnimation(this);
		finish();
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==buttonfriendinfo || v==friendinfo){

			db.open();
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(getApplication());
			int id = preferences.getInt("id", 0);
			Cursor cursor = db.getInfoperson(id);
		
			db.close();
			if (cursor.getCount() == 0) {
				// get your custom_toast.xml layout
				Toast.makeText(this,"Friends information list is empty", Toast.LENGTH_SHORT).show();
				
			} else {
				Intent intent4 = new Intent(getApplicationContext(),
						SendFriendInformationActivity.class);
				startActivity(intent4);
				this.overridePendingTransition(R.anim.layout_animation_row_left_from_right_side, R.anim.layout_animation_row_left_slide);
			}
		
		}else if(v== buttonaccountinfo || v==accountinfo){

			db.open();
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(getApplication());
			int id = preferences.getInt("id", 0);
			Cursor cursor = db.getInfoOther(id);
			
			db.close();
			if (cursor.getCount() == 0) {
				// get your custom_toast.xml layout
				Toast.makeText(this,"Bank Account information list is empty", Toast.LENGTH_SHORT).show();
				
			} else {
				Intent intent4 = new Intent(getApplicationContext(),
						SendAccountInfoActivity.class);
				startActivity(intent4);
				this.overridePendingTransition(R.anim.layout_animation_row_left_from_right_side, R.anim.layout_animation_row_left_slide);
			}
		
		}else if(v== buttonotherinfo || v==otherinfo){

			db.open();
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(getApplication());
			int id = preferences.getInt("id", 0);
			Cursor cursor = db.getInfoBookmark(id);
			
			db.close();
			if (cursor.getCount() == 0) {
				// get your custom_toast.xml layout
				Toast.makeText(this,"other information list is empty", Toast.LENGTH_SHORT).show();
				
			} else {
				Intent intent4 = new Intent(getApplicationContext(),
						SendOtherInfoActivity.class);
				startActivity(intent4);
				this.overridePendingTransition(R.anim.layout_animation_row_left_from_right_side, R.anim.layout_animation_row_left_slide);
			}
		
		}
		else if (v == back) {
			finish();
		}

		else if (v == home) {
			finish();
		}

	}

	/**
	 * @return account info;
	 */
	public LinearLayout getAccountinfo() {
		return accountinfo;
	}

	/**
	 * @param accountinfo
	 */
	public void setAccountinfo(LinearLayout accountinfo) {
		this.accountinfo = accountinfo;
	}

	/**
	 * @return other info;
	 */
	public LinearLayout getOtherinfo() {
		return otherinfo;
	}

	/**
	 * @param otherinfo
	 */
	public void setOtherinfo(LinearLayout otherinfo) {
		this.otherinfo = otherinfo;
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		this.overridePendingTransition(
				R.anim.layout_animation_row_right_from_left_side,
				R.anim.layout_animation_row_right_slide);
	}

}
