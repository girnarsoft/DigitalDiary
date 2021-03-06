package com.girnar.online_digital_diary.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
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
public class OtherInfoShowActivity extends Activity implements ImportantMethod,
		OnClickListener {
	private DbHelper db = new DbHelper(this);
	private String[] data;
	private int id;
	private final String TAG = "OtherInfoShowActivity";
	private TextView person_holders_name, person_account_no, person_banks_name,
			person_location;
	private Tracker tracker;
	private Button back;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "on create called...");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_other_info_show);
		EasyTracker.getInstance().setContext(getApplicationContext());
		tracker = EasyTracker.getTracker();
		tracker.trackView("Show the Account Infomation Digital_Diary");
		getIds();
		addListener();
		db.open();
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		id = bundle.getInt("id");

		Cursor cursor = db.getInfoOtherMethod(id);
		db.close();
		if (cursor != null) {
			setData(new String[cursor.getCount()]);

			for (int i = 0; i < cursor.getCount(); i++) {
				String holders_name = cursor.getString(0);
				String account_no = cursor.getString(1);
				String banks_name = cursor.getString(2);
				String location = cursor.getString(3);

				person_holders_name.setText(holders_name);
				person_account_no.setText(account_no);
				person_banks_name.setText(banks_name);
				person_location.setText(location);

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
		person_holders_name = (TextView) findViewById(R.id.textview_other_info_show_holders_name);
		person_account_no = (TextView) findViewById(R.id.textview_other_info_show_account_no);
		person_banks_name = (TextView) findViewById(R.id.textview_other_info_show_bank_name);
		person_location = (TextView) findViewById(R.id.textview_other_info_show_location);
		back = (Button) findViewById(R.id.button_acoount_update);
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
	 * @return data;
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
