package com.girnar.online_digital_diary.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
public class RagistrationPageActivity extends Activity implements
		ImportantMethod, OnClickListener {

	private DbHelper dbHelper1 = new DbHelper(this);
	private TextView textView;
	private DatePicker datePicker;
	private final String TAG = "RagistrationPageActivity";
	private EditText fname, username, password, question, answer;

	private LinearLayout back;
	private Tracker tracker;
	static final int DATE_DIALOG_ID = 0;
	private Button submit;
	private long id;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "on create called...");
		setContentView(R.layout.activity_ragistration__page);
		// For hide Keyboard
		View view = (View) findViewById(R.id.registrationPage);
		setupUI(view);
		// code for analytics
		EasyTracker.getInstance().setContext(getApplicationContext());
		tracker = EasyTracker.getTracker();
		tracker.trackView("registration page Digital_Diary");

		getIds();
		addListener();
		Util.addGoogleAds(this);

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	
		finish();
	}

	
	
	
	private ImageView home;
	private TextView header;

	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		// dob.setOnClickListener(this);
		
		
		
		fname.setOnClickListener(this);
		
		username.setOnClickListener(this);
		password.setOnClickListener(this);
		submit.setOnClickListener(this);
		back.setOnClickListener(this);
		// back.setOnClickListener(this);
	}

	/**
	 * to insert a value in the database through insert info method
	 */
	public long databaseHelper() {
		// TODO Auto-generated method stub
		String fName = fname.getText().toString();
		
		String userName = username.getText().toString();

		String password = this.password.getText().toString();
		String question = this.question.getText().toString();
		String answer = this.answer.getText().toString();
		
		
		
		
		
		dbHelper1.open();
		this.id = dbHelper1.insertinfo(fName, userName, password,
				question, answer);

		dbHelper1.close();
		return this.id;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == submit) {
			boolean result = validate();

			boolean checkuser = checkRegistration();
			if (result && checkuser == false) {

				long id = databaseHelper();

				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(this);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putInt("id", (int) id);
				editor.commit();
				Intent intent1 = new Intent(this, HomePageActivity.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent1.putExtra("userName", fname.getText().toString());
				startActivity(intent1);
				this.overridePendingTransition(
						R.anim.layout_animation_row_left_from_right_side,
						R.anim.layout_animation_row_left_slide);
				LoginPageActivity.instance.finish();
				finish();
			}
		} else if (v == back) {
			
			finish();

		}

	}

	@Override
	public void getIds() {
		// TODO Auto-generated method stub
		fname = (EditText) findViewById(R.id.edittext_fname);

		username = (EditText) findViewById(R.id.edittext_username);
		password = (EditText) findViewById(R.id.edittext_password);

		submit = (Button) findViewById(R.id.button_submit_registration);
		question = (EditText) findViewById(R.id.edittext_security_ques);
		answer = (EditText) findViewById(R.id.edittext_security_answer);
		back = (LinearLayout) findViewById(R.id.linear_layout_img_bck);
		home = (ImageView) findViewById(R.id.img_home);
		home.setVisibility(View.INVISIBLE);
		header = (TextView) findViewById(R.id.header_text);
		header.setText("DIGITAL DIARY");
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
	

		if (fname.getText().toString().length() == 0) {
			Toast.makeText(this, "Please fill  name", Toast.LENGTH_SHORT)
					.show();
			return false;
		}  else if (username.getText().toString().length() == 0) {
			Toast.makeText(this, "Plese fill User Name", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else if (password.getText().toString().length() == 0) {
			Toast.makeText(this, "Please fill password", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else if (question.getText().toString().length() == 0) {
			Toast.makeText(this, "Please Enter Any Security Question",
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (answer.getText().toString().length() == 0) {
			Toast.makeText(this, "Please Enter Your answer", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @return textView
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

	/**
	 * @return datePicker
	 */
	public DatePicker getDatePicker() {
		return datePicker;
	}

	/**
	 * @param datePicker
	 */
	public void setDatePicker(DatePicker datePicker) {
		this.datePicker = datePicker;
	}

	/**
	 * @return true;
	 */
	public boolean checkRegistration() {
		String uName = username.getText().toString();

		dbHelper1.open();
		Cursor cursor = dbHelper1.checkUserName(uName);

		dbHelper1.close();
		if (cursor == null || cursor.getCount() == 0) {

			return false;
		} else {
			if (cursor.getString(0).equalsIgnoreCase(uName))
				;
			Toast.makeText(this, "username already exist", Toast.LENGTH_LONG)
					.show();

			return true;

		}

	}

	public void setupUI(View view) {

		// Set up touch listener for non-text box views to hide keyboard.
		if (!(view instanceof EditText)) {

			view.setOnTouchListener(new OnTouchListener() {

				public boolean onTouch(View v, MotionEvent event) {
					Util.hideSoftKeyboard(RagistrationPageActivity.this);
					return false;
				}

			});
		}

		// If a layout container, iterate over children and seed recursion.
		if (view instanceof ViewGroup) {

			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

				View innerView = ((ViewGroup) view).getChildAt(i);

				setupUI(innerView);
			}
		}
	}
}
