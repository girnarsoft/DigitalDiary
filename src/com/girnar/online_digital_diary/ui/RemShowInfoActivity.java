package com.girnar.online_digital_diary.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.girnar.online_digital_diary.database.DbHelper;
import com.girnar.online_digital_diary.interfaces.ImportantMethod;
import com.girnar.online_digital_diary.ui.R;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;

/**
 * @author 
 *
 */
public class RemShowInfoActivity extends Activity implements ImportantMethod,OnClickListener {
	private DbHelper db = new DbHelper(this);
	private String[] data;
	private final String TAG = "RemShowInfoActivity";
	private TextView person_title, person_description, person_set_date,
			person_set_time;
	private int id;
	private Tracker tracker;
	private Button back;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "on create called...");
		setContentView(R.layout.activity_rem_show_info);
		EasyTracker.getInstance().setContext(getApplicationContext());
		tracker = EasyTracker.getTracker();
		tracker.trackView("Show information of reminders Digital_Diary");
		getIds();
		addListener();
		db.open();
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		id = bundle.getInt("id");

		Cursor cursor = db.getInfoReminderMethod(id);
		db.close();
		if (cursor != null) {
			setData(new String[cursor.getCount()]);

			for (int i = 0; i < cursor.getCount(); i++) {
				
				String title = cursor.getString(0);
				String description = cursor.getString(1);
				String set_date = cursor.getString(2);
				String set_time = cursor.getString(3);

				person_title.setText(title);
				person_description.setText(description);
				person_set_date.setText(set_date);
				person_set_time.setText(set_time);

				cursor.moveToNext();
			}
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

	@Override
	public void getIds() {
		// TODO Auto-generated method stub
		person_title = (TextView) findViewById(R.id.textview_reminder_title_show);
		person_description = (TextView) findViewById(R.id.textview_reminder_description_show);
		person_set_date = (TextView) findViewById(R.id.textview_reminder_set_date_show);
		person_set_time = (TextView) findViewById(R.id.textview_reminder_set_time_show);
		back = (Button) findViewById(R.id.button_showreminder_back);
	}

	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		back.setOnClickListener(this);
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @return data
	 */
	public String[] getData() {
		return data;
	}

	/**
	 * @param data
	 */
	public void setData(String[] data) {
		this.data = data;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == back) {

			finish();
		}
	}

}
