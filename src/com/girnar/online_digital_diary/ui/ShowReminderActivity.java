package com.girnar.online_digital_diary.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.girnar.online_digital_diary.adapters.ReminderAdapter;
import com.girnar.online_digital_diary.database.DbHelper;
import com.girnar.online_digital_diary.interfaces.ImportantMethod;
import com.girnar.online_digital_diary.recievers.NotificationReceiver;
import com.girnar.online_digital_diary.util.Util;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;

/**
 * @author
 * 
 */
public class ShowReminderActivity extends Activity implements
		OnItemClickListener, ImportantMethod, OnClickListener {
	private Button New;
	private ListView listView;
	private final String TAG = "ShowReminderActivity";
	/**
	 * reference of ShowReminderActivity class
	 */
	public static ShowReminderActivity activity;
	LinearLayout back;
	private ImageView home;

	long firetime;
	private ArrayList<String> data = new ArrayList<String>();
	private ArrayList<String> ids = new ArrayList<String>();
	private ArrayList<Long> millis = new ArrayList<Long>();
	private ArrayList<String> descs = new ArrayList<String>();
	
	private DbHelper db = new DbHelper(this);
	NotificationReceiver notificationReceiver = new NotificationReceiver();
	private Tracker tracker;
	private TextView no_acc;
	ImageButton search;
	private TextView header_text;
	private EditText editTextSearch;
	private View footerView;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "on create called...");
		activity = this;
		setContentView(R.layout.activity_show_reminder);
		// For hide Keyboard
		View view = (View) findViewById(R.id.reminders);
		Util.setupUI(view, this);
		EasyTracker.getInstance().setContext(getApplicationContext());
		tracker = EasyTracker.getTracker();
		tracker.trackView("List of Reminders Digital_Diary");
		getIds();
		addListener();
		Util.addGoogleAds(this);

		// setContentView(R.layout.custom_adapter);

		
	}

	public long timeInMills(int mYear, int mMonth, int mDay, int mHour,
			int mMinute) {
		Date dat = new Date();// initializes to now
		Calendar cal_alarm = Calendar.getInstance();
		Calendar cal_now = Calendar.getInstance();
		cal_now.setTime(dat);
		cal_alarm.set(mYear, mMonth, mDay, mHour, mMinute, 0);

		if (cal_alarm.before(cal_now)) {// if its in the past increment

			cal_alarm.add(Calendar.DATE, 1);
		}

		return cal_alarm.getTimeInMillis();
	}
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		finish();
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		ids.clear();
		data.clear();
		descs.clear();
		millis.clear();
		db.open();
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		int id = preferences.getInt("id", 0);
		Cursor cursor = db.getInfoReminder(id);
		db.close();
		if (cursor != null) {
			cursor.moveToFirst();

			for (int i = 0; i < cursor.getCount(); i++) {
				ids.add(cursor.getString(0));
				data.add(cursor.getString(1));
				descs.add(cursor.getString(4));
				String[] date_split = cursor.getString(2).split("-");

				String[] time_split = cursor.getString(3).split(":");

				String[] srr = time_split[1].split(" ");

				int day = Integer.parseInt(date_split[0]);
				int month = Integer.parseInt(date_split[1]);
				int year = Integer.parseInt(date_split[2].trim());
				int hour = Integer.parseInt(time_split[0]);
				int minute = Integer.parseInt(srr[0]);
				if (srr[1].equals("PM")) {

					hour = hour + 12;
				}

				long l = timeInMills(year, month - 1, day, hour, minute);
				millis.add(l);
				cursor.moveToNext();
			}

		}

		ReminderAdapter adapter = new ReminderAdapter(
				ShowReminderActivity.this, data, ids, "REMINDER", "reminder_id",millis,descs);
		listView.setAdapter(adapter);

		if (cursor.getCount() == 0) {

			no_acc.setVisibility(View.VISIBLE);
			listView.setVisibility(View.GONE);
		}else if (cursor.getCount() > 0) {

			no_acc.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
		}
	}

	

	

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getIds() {
		// TODO Auto-generated method stub
		New = (Button) findViewById(R.id.button_show_reminder_new);

		listView = (ListView) findViewById(R.id.show_reminder_listView);
		back = (LinearLayout) findViewById(R.id.linear_layout_img_bck);
		home = (ImageView) findViewById(R.id.img_home);
		no_acc = (TextView) findViewById(R.id.text_no_acc);
		header_text = (TextView) findViewById(R.id.header_text);
		header_text.setText("Reminders List");
		editTextSearch = (EditText) findViewById(R.id.edittext_search_reminder_info);
		footerView = getLayoutInflater()
				.inflate(R.layout.listview_footer, null);

		listView.addFooterView(footerView);
	}

	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		New.setOnClickListener(this);
		back.setOnClickListener(this);
		// listView.setOnItemClickListener(this);
		home.setOnClickListener(this);
		editTextSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				db.open();
				Cursor cursor = db.searchInfoReminder(editTextSearch.getText()
						.toString());
				db.close();
				ids.clear();
				data.clear();
				millis.clear();
				descs.clear();

				if (cursor != null) {
					cursor.moveToFirst();
					for (int i = 0; i < cursor.getCount(); i++) {
						ids.add(cursor.getString(0));
						data.add(cursor.getString(1));
						descs.add(cursor.getString(4));
						String[] date_split = cursor.getString(2).split("-");

						String[] time_split = cursor.getString(3).split(":");

						String[] srr = time_split[1].split(" ");

						int day = Integer.parseInt(date_split[0]);
						int month = Integer.parseInt(date_split[1]);
						int year = Integer.parseInt(date_split[2].trim());
						int hour = Integer.parseInt(time_split[0]);
						int minute = Integer.parseInt(srr[0]);
						if (srr[1].equals("PM")) {

							hour = hour + 12;
						}

						long l = timeInMills(year, month - 1, day, hour, minute);
						millis.add(l);
						cursor.moveToNext();
					}
				}

				ReminderAdapter adapter = new ReminderAdapter(
						ShowReminderActivity.this, data, ids, "REMINDER", "reminder_id",millis,descs);
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				if (cursor.getCount() == 0) {
					
					no_acc.setVisibility(View.VISIBLE);
					listView.setVisibility(View.GONE);
				}else if (cursor.getCount() > 0) {

					no_acc.setVisibility(View.GONE);
					listView.setVisibility(View.VISIBLE);
				}
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == New) {
			Intent intent = new Intent(this, ReminderInfoActivity.class);
			startActivity(intent);
			this.overridePendingTransition(
					R.anim.layout_animation_row_left_from_right_side,
					R.anim.layout_animation_row_left_slide);
		}
		if (v == back) {

			finish();
		}
		if (v == home) {

			finish();
		}

	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

}
