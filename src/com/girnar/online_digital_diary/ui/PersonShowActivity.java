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
public class PersonShowActivity extends Activity implements ImportantMethod,
		OnClickListener {
	private DbHelper db = new DbHelper(this);
	private String[] data;
	private final String TAG = "PersonShowActivity";
	private TextView person_name, person_address, person_birthday,
			person_mobile_no, person_aniversary, person_email, person_age,person_gender;
	private int id;
	private Tracker tracker;
	private Button update;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "on create called...");
		setContentView(R.layout.activity_person_show);
		EasyTracker.getInstance().setContext(getApplicationContext());
		tracker = EasyTracker.getTracker();
		tracker.trackView("Show the personal Information Digital_Diary");
		getIds();
		addListener();
		db.open();
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		id = bundle.getInt("id");

		Cursor cursor = db.getInfopersonMethod(id);
		db.close();
		if (cursor != null) {
			setData(new String[cursor.getCount()]);

			for (int i = 0; i < cursor.getCount(); i++) {
				
				String name = cursor.getString(0);
				String address = cursor.getString(1);
				String birthday = cursor.getString(2);
				String mobile_no = cursor.getString(3);
				String aniversary = cursor.getString(4);
				String email = cursor.getString(5);
				String age = cursor.getString(6);
				String gender = cursor.getString(8);
				
				person_name.setText(name);
				person_address.setText(address);
				person_birthday.setText(birthday);
				person_mobile_no.setText(mobile_no);
				person_aniversary.setText(aniversary);
				person_email.setText(email);
				person_age.setText(age);
				person_gender.setText(gender);
				

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
		person_name = (TextView) findViewById(R.id.textview_person_show_name);
		person_address = (TextView) findViewById(R.id.textview_person_show_address);
		person_birthday = (TextView) findViewById(R.id.textview_person_show_birthday);
		person_mobile_no = (TextView) findViewById(R.id.textview_person_show_mobile_no);
		person_aniversary = (TextView) findViewById(R.id.textview_person_show_aniversary);
		person_email = (TextView) findViewById(R.id.textview_person_show_email);
		person_age = (TextView) findViewById(R.id.textview_person_show_age);
		person_gender = (TextView) findViewById(R.id.textview_person_show_gender);
		update = (Button) findViewById(R.id.button_person_update);

	}

	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		update.setOnClickListener(this);

	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == update) {
			
			finish();
		}

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

}
