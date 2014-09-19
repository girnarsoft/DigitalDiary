package com.girnar.online_digital_diary.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.girnar.online_digital_diary.database.DbHelper;
import com.girnar.online_digital_diary.interfaces.ImportantMethod;
import com.girnar.online_digital_diary.util.Util;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;

/**
 * @author
 * 
 */
public class HomePageActivity extends Activity implements ImportantMethod,
		OnClickListener {
	private LinearLayout personInfo, otherInfo, reminder, bookmark, sendInfo;
	private Button buttonpersonInfo, buttonotherInfo, buttonreminder, buttonbookmark, buttonsendInfo;
	ImageView image, back, home;
	private TextView header,userName;
	private Tracker tracker;
	private final String TAG = "HomePageActivity";
	private DbHelper db = new DbHelper(this);
	String WelcomeName;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EasyTracker.getInstance().setContext(getApplicationContext());
		tracker = EasyTracker.getTracker();
		tracker.trackView("Home page_Digital_Diary");
		Log.d(TAG, "on create called...");
		setContentView(R.layout.activity_home_page);
		getIds();
		addListener();
		Util.addGoogleAds(this);
		db.open();
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		int id = preferences.getInt("id", 0);
		Cursor cursor = db.getName(id);
		if(cursor!=null){
			for(int i=0;i<cursor.getCount();i++){
				WelcomeName = cursor.getString(0);
				cursor.moveToNext();
			}
		}
		db.close();
//		= getIntent().getExtras().getString("userName");
		this.userName.setText(WelcomeName);

	}

	

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		{
			LayoutInflater inflater = LayoutInflater.from(this);
			@SuppressWarnings("unused")
			View view = inflater.inflate(R.layout.activity_home_page, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Do you want to Exit")
					.setCancelable(false)
					.setPositiveButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							})
					.setNegativeButton("Exit",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									finish();
								}
							});
			builder.create();
			builder.show();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == personInfo || v == buttonpersonInfo) {
			Intent intent = new Intent(this, FrndListActivity.class);
			startActivity(intent);
			this.overridePendingTransition(R.anim.layout_animation_row_left_from_right_side, R.anim.layout_animation_row_left_slide);
		}
		if (v == otherInfo || v== buttonotherInfo) {
			Intent intent1 = new Intent(this, OtherListviewInfoActivity.class);
			startActivity(intent1);
			this.overridePendingTransition(R.anim.layout_animation_row_left_from_right_side, R.anim.layout_animation_row_left_slide);
		}
		if (v == reminder || v == buttonreminder) {
			Intent intent2 = new Intent(this, ShowReminderActivity.class);
			startActivity(intent2);
			this.overridePendingTransition(R.anim.layout_animation_row_left_from_right_side, R.anim.layout_animation_row_left_slide);
		}
		if (v == bookmark || v== buttonbookmark) {
			Intent intent3 = new Intent(this, BookmarkActivity.class);
			startActivity(intent3);
			this.overridePendingTransition(R.anim.layout_animation_row_left_from_right_side, R.anim.layout_animation_row_left_slide);
		}

		if (v == sendInfo || v == buttonsendInfo) {

			Intent intent4 = new Intent(this, SendButtonActivity.class);
			startActivity(intent4);
			this.overridePendingTransition(R.anim.layout_animation_row_left_from_right_side, R.anim.layout_animation_row_left_slide);
		}

	}

	@Override
	public void getIds() {
		// TODO Auto-generated method stub
		personInfo = (LinearLayout) findViewById(R.id.imageView_home_personal_info);
		otherInfo = (LinearLayout) findViewById(R.id.imageView_home_other_info);
		reminder = (LinearLayout) findViewById(R.id.imageView_home_remainder);
		bookmark = (LinearLayout) findViewById(R.id.imageView_home_bookmark);
		sendInfo = (LinearLayout) findViewById(R.id.imageView_home_send_info);
		
		buttonpersonInfo = (Button) findViewById(R.id.friend_info);
		buttonotherInfo = (Button) findViewById(R.id.bank_account_info);
		buttonreminder = (Button) findViewById(R.id.view_set_reminder);
		buttonbookmark = (Button) findViewById(R.id.other_info);
		buttonsendInfo = (Button) findViewById(R.id.send_info);
		back = (ImageView) findViewById(R.id.img_bck);
		home = (ImageView) findViewById(R.id.img_home);
		image = (ImageView) findViewById(R.id.img_logo);
		header = (TextView)findViewById(R.id.header_text);
		userName = (TextView)findViewById(R.id.userNameText);
		back.setVisibility(View.INVISIBLE);
		home.setVisibility(View.INVISIBLE);
		
		header.setText("DIGITAL DIARY");
		
		
	}

	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		personInfo.setOnClickListener(this);
		otherInfo.setOnClickListener(this);
		reminder.setOnClickListener(this);
		bookmark.setOnClickListener(this);
		sendInfo.setOnClickListener(this);
		buttonpersonInfo.setOnClickListener(this);
		buttonotherInfo.setOnClickListener(this);
		buttonreminder.setOnClickListener(this);
		buttonbookmark.setOnClickListener(this);
		buttonsendInfo.setOnClickListener(this);
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
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
