package com.girnar.online_digital_diary.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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
public class LoginPageActivity extends Activity implements OnClickListener,
		ImportantMethod {

	private final String TAG = "LoginActivity";

	private TextView registration;
	private Button submit;
	private Tracker tracker;
	/**
	 * reference of Second class
	 */

	private EditText userName, password;

	private DbHelper db;

	View view;
	private ImageView back;
	private ImageView home;
	private TextView header;
	private TextView forgot;
	public static Activity instance;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "on create called...");
		setContentView(R.layout.activity_login_page_);
		View view = (View) findViewById(R.id.loginPage);
		setupUI(view);
		SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);

		int old_user = preferences.getInt("old_user", 0);
		if (old_user == 0) {
			Util.checkNotification(this);
			showDailogOldUser();

		}

		instance = this;
		db = new DbHelper(this);
		EasyTracker.getInstance().setContext(getApplicationContext());
		tracker = EasyTracker.getTracker();
		tracker.trackView("login page  Digital_Diary");

		getIds();
		addListener();
		Util.addGoogleAds(this);
	}

	
	@Override
	public void getIds() {
		// TODO Auto-generated method stub
		registration = (TextView) findViewById(R.id.textView_registration_login);
		submit = (Button) findViewById(R.id.button_submit);

		userName = (EditText) findViewById(R.id.editText_username_loginPage);
		password = (EditText) findViewById(R.id.editText_password_loginPage);
		forgot = (TextView) findViewById(R.id.textView_forgot_password);
		back = (ImageView) findViewById(R.id.img_bck);
		home = (ImageView) findViewById(R.id.img_home);
		back.setVisibility(View.INVISIBLE);
		home.setVisibility(View.INVISIBLE);
		header = (TextView) findViewById(R.id.header_text);
		header.setText("DIGITAL DIARY");
	}

	@Override
	public void addListener() {
		// TODO Auto-generated method stub

		registration.setOnClickListener(this);

		submit.setOnClickListener(this);
		forgot.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v == registration) {
			Intent intent = new Intent(this, RagistrationPageActivity.class);
			startActivity(intent);
			this.overridePendingTransition(
					R.anim.layout_animation_row_left_from_right_side,
					R.anim.layout_animation_row_left_slide);

		} else if (v == submit) {

			boolean result = checkLogin();

			if (result) {
				if (getIntent().getExtras().getString("auth")
						.equals("reminder")) {
					SharedPreferences preferences = PreferenceManager
							.getDefaultSharedPreferences(this);
					SharedPreferences.Editor editor = preferences.edit();
					editor.putInt("id", id);
					editor.commit();
					Intent intent1 = new Intent(this,
							ShowReminderActivity.class);
					intent1.putExtra("userName", name);
					startActivity(intent1);
					this.overridePendingTransition(
							R.anim.layout_animation_row_left_from_right_side,
							R.anim.layout_animation_row_left_slide);

					this.finish();
				} else if (getIntent().getExtras().getString("auth")
						.equals("friend")) {
					SharedPreferences preferences = PreferenceManager
							.getDefaultSharedPreferences(this);
					SharedPreferences.Editor editor = preferences.edit();
					editor.putInt("id", id);
					editor.commit();
					Intent intent1 = new Intent(this, FrndListActivity.class);
					intent1.putExtra("userName", name);
					startActivity(intent1);
					this.overridePendingTransition(
							R.anim.layout_animation_row_left_from_right_side,
							R.anim.layout_animation_row_left_slide);

					this.finish();
				} else {
					SharedPreferences preferences = PreferenceManager
							.getDefaultSharedPreferences(this);
					SharedPreferences.Editor editor = preferences.edit();
					editor.putInt("id", id);
					editor.commit();
					Intent intent1 = new Intent(this, HomePageActivity.class);
					intent1.putExtra("userName", name);
					startActivity(intent1);
					this.overridePendingTransition(
							R.anim.layout_animation_row_left_from_right_side,
							R.anim.layout_animation_row_left_slide);

					this.finish();
				}
			} else {
				Toast.makeText(this,
						"Please enter correct user name and password",
						Toast.LENGTH_LONG).show();
				userName.setText("");
				password.setText("");
			}
		} else if (v == forgot) {
			if (userName.getText().toString().length() == 0) {
				Toast.makeText(this, "First Fill Username", Toast.LENGTH_LONG)
						.show();

			} else {
				db.open();
				Cursor cursor = db.getQuestion(userName.getText().toString());
				db.close();
				if (cursor.getCount() == 0) {
					Toast.makeText(this, "Please enter correct username",
							Toast.LENGTH_LONG).show();
				} else {
					if (cursor != null) {
						for (int i = 0; i < cursor.getCount(); i++) {
							ques = cursor.getString(0);
							ans = cursor.getString(1);
							id1 = cursor.getInt(2);
							cursor.moveToNext();
						}
					}
					Intent intent1 = new Intent(this, ForgotActivity.class);
					intent1.putExtra("username", userName.getText().toString());
					intent1.putExtra("ques", ques);
					intent1.putExtra("ans", ans);
					intent1.putExtra("id", id1);
					
					startActivity(intent1);
					this.overridePendingTransition(
							R.anim.layout_animation_row_left_from_right_side,
							R.anim.layout_animation_row_left_slide);

				}
			}
		}
	}

	String ques, ans;
	int id1;

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

	private int id;

	/**
	 * @return true;
	 */
	public boolean checkLogin() {
		String uName = userName.getText().toString();
		String pass = password.getText().toString();

		db.open();
		Cursor cursor = db.checkLogin(uName, pass);
		db.close();
		if (cursor.getCount() == 0) {

			return false;
		} else {
			id = cursor.getInt(0);
			name = cursor.getString(1);
			return true;
		}
	}

	String name;

	public void showDailogOldUser() {

		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		LayoutInflater adbInflater = LayoutInflater.from(this);
		View eulaLayout = adbInflater.inflate(R.layout.checkbox1, null);
		dontShowAgain = (CheckBox) eulaLayout.findViewById(R.id.skip);
		adb.setView(eulaLayout);
		adb.setTitle("Attention");
		adb.setMessage(getString(R.string.Note));
		adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				if (dontShowAgain.isChecked()) {
					SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = preferences.edit();

					editor.putInt("old_user", 1);
					editor.commit();

				}

			}
		});

		adb.show();
	}

	public CheckBox dontShowAgain;

	public void setupUI(View view) {

		// Set up touch listener for non-text box views to hide keyboard.
		if (!(view instanceof EditText)) {

			view.setOnTouchListener(new OnTouchListener() {

				public boolean onTouch(View v, MotionEvent event) {
					Util.hideSoftKeyboard(LoginPageActivity.this);
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
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		this.overridePendingTransition(
				R.anim.layout_animation_row_right_from_left_side,
				R.anim.layout_animation_row_right_slide);
	}
}
