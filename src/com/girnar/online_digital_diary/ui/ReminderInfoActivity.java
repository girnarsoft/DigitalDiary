package com.girnar.online_digital_diary.ui;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.girnar.online_digital_diary.database.DbHelper;
import com.girnar.online_digital_diary.interfaces.ImportantMethod;
import com.girnar.online_digital_diary.recievers.NotificationReceiver;
import com.girnar.online_digital_diary.util.Application;
import com.girnar.online_digital_diary.util.UiDatePicker;
import com.girnar.online_digital_diary.util.UiTimePicker;
import com.girnar.online_digital_diary.util.Util;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;

/**
 * @author
 * 
 */
public class ReminderInfoActivity extends Activity implements ImportantMethod,
		OnClickListener, OnDateSetListener {
	private Button  save;
	private ImageButton setdate, settime;
	EditText date, time;
	LinearLayout dateLayout, timeLayout;
	private DbHelper db = new DbHelper(this);
	private EditText title, description;

	private final String TAG = "ReminderInfoActivity";
	MediaPlayer mediaPlayer;
	LinearLayout back;
	private ImageView home;
	static final int DATE_DIALOG_ID = 0;
	private int mYear, mMonth, mDay, hour, minute;
	static final int TIME_DIALOG_ID = 999;
	UiDatePicker uiDatePicker;
	UiTimePicker uiTimePicker;
	private Tracker tracker;
	private ArrayList<String> dates = new ArrayList<String>();
	private ArrayList<String> times = new ArrayList<String>();
	@SuppressWarnings("unused")
	private ArrayList<String> ids = new ArrayList<String>();
	private ArrayList<Cursor> remList = new ArrayList<Cursor>();
	private TextView header_text;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "on create called...");
		setContentView(R.layout.activity_reminder_info);
		// For hide Keyboard
		View view = (View) findViewById(R.id.newReminderPage);
		Util.setupUI(view, this);
		EasyTracker.getInstance().setContext(getApplicationContext());
		tracker = EasyTracker.getTracker();
		tracker.trackView("Reminders Digital_Diary");
		getIds();
		addListener();
		setItemsText();
		Util.addGoogleAds(this);
		Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		Application.isActivityVisible();

		uiDatePicker = new UiDatePicker(this, false);
		uiTimePicker = new UiTimePicker(this, false);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, ShowReminderActivity.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);

		this.overridePendingTransition(
				R.anim.layout_animation_row_right_from_left_side,
				R.anim.layout_animation_row_right_slide);
		finish();
	}

	
	/**
	 * insert the value in the database
	 * 
	 * @return id1
	 */
	public long databaseHelper() {
		//

		// TODO Auto-generated method stub
		String title = this.title.getText().toString();
		String description = this.description.getText().toString();
		String setdate = this.date.getText().toString();

		String settime = this.time.getText().toString();

		db.open();
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		int id = preferences.getInt("id", 0);
		long id1 = db.insertinfoReminder(title, description, setdate, settime,
				id);

		db.close();
		return id1;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v == back) {
			Intent intent = new Intent(this, ShowReminderActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);

			this.overridePendingTransition(
					R.anim.layout_animation_row_right_from_left_side,
					R.anim.layout_animation_row_right_slide);
			finish();

		}
		if (v == home) {
			Util.homeAnimation(this);
			finish();

		}

	}

	private void setItemsText() {
		Bundle bundle = getIntent().getBundleExtra("NotificationBundle");

		if (bundle == null)
			return;

		description.setText(bundle.getString("NotificationDesc"));
		uiTimePicker
				.setTime(bundle.getLong("NotificationTimeInMills"), settime);
		uiDatePicker.setDate(bundle.getLong("NotificationTimeInMills"),
				setdate, "-");

	}

	private void setOtherReminder() {

		boolean result = validate();

		long id = 0;
		if (result) {
			id = databaseHelper();

		}
		
		NotificationReceiver.setNotificationOnDateTime(this, uiTimePicker
				.timeInMills(uiDatePicker.mYear, uiDatePicker.mMonth,
						uiDatePicker.mDay), description.getText().toString(),
				(int)id);

		if (result) {
			Intent intent = new Intent(this, ShowReminderActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);

			this.overridePendingTransition(
					R.anim.layout_animation_row_right_from_left_side,
					R.anim.layout_animation_row_right_slide);
			finish();

		}

	}

	@Override
	public void getIds() {
		// TODO Auto-generated method stub
		date = (EditText) findViewById(R.id.edittext_setDate);
		time = (EditText) findViewById(R.id.edittext_setTime);

		save = (Button) findViewById(R.id.button_reminder_info_save);
		setdate = (ImageButton) findViewById(R.id.reminder_info_setdate);
		back = (LinearLayout) findViewById(R.id.linear_layout_img_bck);
		settime = (ImageButton) findViewById(R.id.reminder_info_setTime);
		home = (ImageView) findViewById(R.id.img_home);
		title = (EditText) findViewById(R.id.edittext_title_reminder);
		description = (EditText) findViewById(R.id.edittext_description_reminder);
		header_text = (TextView) findViewById(R.id.header_text);
		header_text.setText("Reminder Information");
	}

	OtherReminderClickListner reminderClickListner = new OtherReminderClickListner() {

		@Override
		public void onTimeClick(View view) {
			// TODO Auto-generated method stub
			uiTimePicker.showDialog(view, time);
		}

		@Override
		public void onDateClick(View view) {
			// TODO Auto-generated method stub

			uiDatePicker.showDialog(view, date, "-");
		}

		@Override
		public void onDescriptionClick() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSetRemenderClick() {
			// TODO Auto-generated method stub
			setOtherReminder();
		}

		@Override
		public void onDayClick() {
			// TODO Auto-generated method stub

		}

	};

	@Override
	public void addListener() {
		// TODO Auto-generated method stub

		save.setOnClickListener(reminderClickListner);
		setdate.setOnClickListener(reminderClickListner);
		settime.setOnClickListener(reminderClickListner);
		back.setOnClickListener(this);
		home.setOnClickListener(this);

	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);

		}
		switch (id) {
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, timePicker, hour, minute, false);
		}

		return null;

	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {

			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			date.setText(new StringBuilder().append(mDay).append("/")
					.append(mMonth + 1).append("/").append(mYear));

		}

	};
	private TimePickerDialog.OnTimeSetListener timePicker = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute1) {
			// TODO Auto-generated method stub
			hour = hourOfDay;
			minute = minute1;
			time.setText(new StringBuilder().append(pad(hour)).append(":")
					.append(pad(minute)));

		}
	};

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		if (title.getText().toString().length() == 0) {
			Toast.makeText(this, "Please fill title", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else if (description.getText().toString().length() == 0) {
			Toast.makeText(this, "Please fill description", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else if (date.getText().toString().length() == 0) {
			Toast.makeText(this, "Please set date", Toast.LENGTH_SHORT).show();
			return false;
		} else if (time.getText().toString().length() == 0) {
			Toast.makeText(this, "Please set time", Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @return date;
	 */
	public ArrayList<String> getDate() {
		return dates;
	}

	/**
	 * @param date
	 */
	public void setDate(ArrayList<String> date) {
		this.dates = date;
	}

	/**
	 * @return time;
	 */
	public ArrayList<String> getTime() {
		return times;
	}

	/**
	 * @param time
	 */
	public void setTime(ArrayList<String> time) {
		this.times = time;
	}

	/**
	 * @param ids
	 */
	public void setIds(ArrayList<String> ids) {
		this.ids = ids;
	}

	/**
	 * @return remList;
	 */
	public ArrayList<Cursor> getRemList() {
		return remList;
	}

	/**
	 * @param remList
	 */
	public void setRemList(ArrayList<Cursor> remList) {
		this.remList = remList;
	}
}
