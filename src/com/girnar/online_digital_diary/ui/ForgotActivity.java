package com.girnar.online_digital_diary.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.girnar.online_digital_diary.database.DbHelper;
import com.girnar.online_digital_diary.util.Util;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;

public class ForgotActivity extends Activity implements OnClickListener {

	String ques, ans, userName;
	EditText question, answer, newPass, newQuestion, newAnswer;
	int id;
	Button changePass, submit,submitQuestion;
	private DbHelper db = new DbHelper(this);
	private LinearLayout back;
	private ImageView home;
	private TextView header, subHeader;
	private Tracker tracker;
	private String check;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot);
		// For hide Keyboard
		View view = (View) findViewById(R.id.forgotPage);
		Util.setupUI(view, this);
		EasyTracker.getInstance().setContext(getApplicationContext());
		tracker = EasyTracker.getTracker();
		tracker.trackView("Forgot Password page Digital_Diary");
		ques = getIntent().getExtras().getString("ques");
		ans = getIntent().getExtras().getString("ans");
		id = getIntent().getExtras().getInt("id");

		question = (EditText) findViewById(R.id.editText_security_question);
		newQuestion = (EditText) findViewById(R.id.edittext_new_security_ques);
		newAnswer = (EditText) findViewById(R.id.edittext_new_security_answer);
		answer = (EditText) findViewById(R.id.editText_answer);
		newPass = (EditText) findViewById(R.id.editText_new_password);

		question.setText(ques);

		changePass = (Button) findViewById(R.id.button_forgot_submit);
		changePass.setOnClickListener(this);
		submit = (Button) findViewById(R.id.button_new_pass_submit);
		submit.setOnClickListener(this);
		submitQuestion = (Button) findViewById(R.id.button_change_question);
		submitQuestion.setOnClickListener(this);
		back = (LinearLayout) findViewById(R.id.linear_layout_img_bck);
		home = (ImageView) findViewById(R.id.img_home);
		home.setVisibility(View.INVISIBLE);
		header = (TextView) findViewById(R.id.header_text);
		subHeader = (TextView) findViewById(R.id.forgot_text);
		subHeader.setText("Security Question");
		header.setText("DIGITAL DIARY");
		back.setOnClickListener(this);
		Util.addGoogleAds(this);
		newQuestion.setVisibility(View.GONE);
		newAnswer.setVisibility(View.GONE);
		submitQuestion.setVisibility(View.GONE);
		newPass.setVisibility(View.GONE);
		submit.setVisibility(View.GONE);

		db.open();
		Cursor cursor = db.checkOldUser(getIntent().getExtras().getString(
				"username"));
		db.close();

		if (cursor != null) {
			for (int i = 0; i < cursor.getCount(); i++) {
				check = cursor.getString(0);

				cursor.moveToNext();
			}
		}
		if (check.equals("old")) {
			changePass.setText(getString(R.string.changeQuestion));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forgot, menu);
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == changePass) {
			if (changePass.getText().toString()
					.equals(getString(R.string.changeQuestion))) {
				boolean result = validate();
				if (result) {
					if (answer.getText().toString().equals(ans)) {
				answer.setFocusable(false);
				changePass.setClickable(false);
				changePass.setFocusable(false);
				changePass.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.disablebutton));
				
				newQuestion.setVisibility(View.VISIBLE);
				newAnswer.setVisibility(View.VISIBLE);
				submitQuestion.setVisibility(View.VISIBLE);
				subHeader.setText("Change Security Question");
				}
				else {
					Toast.makeText(this, "Incorrect Answer",
							Toast.LENGTH_LONG).show();
					answer.setText("");
				}
				}
				
			} else {
				boolean result = validate();
				if (result) {
					if (answer.getText().toString().equals(ans)) {
						/**/
						answer.setFocusable(false);
						changePass.setClickable(false);
						changePass.setFocusable(false);
						changePass.setBackgroundDrawable(getResources()
								.getDrawable(R.drawable.disablebutton));
						newPass.setVisibility(View.VISIBLE);
						submit.setVisibility(View.VISIBLE);
						subHeader.setText("Change Password");
					} else {
						Toast.makeText(this, "Incorrect Answer",
								Toast.LENGTH_LONG).show();
						answer.setText("");

					}
				}
			}
		} else if (v == submit) {

			if (newPass.getText().toString().length() == 0) {
				Toast.makeText(this, "Please enter password",
						Toast.LENGTH_SHORT).show();

			} else {
				db.open();
				db.UpdatePassword(id, newPass.getText().toString());
				db.close();
				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(this);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putInt("id", id);
				editor.commit();
				Intent intent1 = new Intent(this, HomePageActivity.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent1);

				this.overridePendingTransition(
						R.anim.layout_animation_row_left_from_right_side,
						R.anim.layout_animation_row_left_slide);
				LoginPageActivity.instance.finish();
				Toast.makeText(this, "Password successfully changed", Toast.LENGTH_SHORT).show();
				finish();
			}

		} else if (v == back) {

			finish();

		}else if(v==submitQuestion){

			if (newQuestion.getText().toString().length() == 0) {
				Toast.makeText(this, "Please enter Question",
						Toast.LENGTH_SHORT).show();

			}else if (newAnswer.getText().toString().length() == 0) {
				Toast.makeText(this, "Please enter Answer",
						Toast.LENGTH_SHORT).show();

			}
			else {
				db.open();
				db.UpdateSecurityQuestion(newQuestion.getText().toString(), newAnswer.getText().toString(), id);
				db.close();
				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(this);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putInt("id", id);
				editor.commit();
				Intent intent1 = new Intent(this, HomePageActivity.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent1);

				this.overridePendingTransition(
						R.anim.layout_animation_row_left_from_right_side,
						R.anim.layout_animation_row_left_slide);
				LoginPageActivity.instance.finish();
				Toast.makeText(this, "Password successfully changed", Toast.LENGTH_SHORT).show();
				finish();
			}

		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
	}

	public boolean validate() {
		// TODO Auto-generated method stub

		if (answer.getText().toString().length() == 0) {
			Toast.makeText(this, "Please enter answer", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else {
			return true;
		}
	}

	
}
