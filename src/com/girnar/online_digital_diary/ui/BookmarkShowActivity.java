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
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;

/**
 * @author
 * 
 */
public class BookmarkShowActivity extends Activity implements ImportantMethod,OnClickListener {
	private DbHelper db = new DbHelper(this);
	@SuppressWarnings("unused")
	private String[] data;
	private Tracker tracker;

	private final String TAG = "BookmarkShowActivity";
	private TextView person_title, person_description;
	private int id;
	private Button back;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "on create called...");
		setContentView(R.layout.activity_bookmark_show);
		EasyTracker.getInstance().setContext(getApplicationContext());
		tracker = EasyTracker.getTracker();
		tracker.trackView("List of Other information_Digital_Diary");

		getIds();
		addListener();
		db.open();
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		id = bundle.getInt("id");

		Cursor cursor = db.getInfoBookmarkMethod(id);
		db.close();
		if (cursor != null) {
			data = new String[cursor.getCount()];

			for (int i = 0; i < cursor.getCount(); i++) {
				
				String title = cursor.getString(0);
				String description = cursor.getString(1);

				person_title.setText(title);
				person_description.setText(description);

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
		person_title = (TextView) findViewById(R.id.textview_bookmark_show_title);
		person_description = (TextView) findViewById(R.id.textview_bookmark_show_description);
		back = (Button) findViewById(R.id.button_other_back);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == back) {

			finish();
		}
	}

}