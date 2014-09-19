package com.girnar.online_digital_diary.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
public class TitleBookmarkActivity extends Activity implements ImportantMethod,
		OnClickListener {
	private Button save;
	private EditText title, description;
	LinearLayout back;
	private ImageView home;
	private final String TAG = "TitleBookmarkActivity";
	private DbHelper db = new DbHelper(this);
	private Tracker tracker;
	private TextView header_text;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "on create called...");
		setContentView(R.layout.activity_title_bookmark);
		// For hide Keyboard
		View view = (View) findViewById(R.id.addNewOtherInfo);
		Util.setupUI(view, this);
		EasyTracker.getInstance().setContext(getApplicationContext());
		tracker = EasyTracker.getTracker();
		tracker.trackView("Other Information Digital_Diary");
		getIds();
		addListener();
		Util.addGoogleAds(this);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, BookmarkActivity.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		startActivity(intent);

		this.overridePendingTransition(
				R.anim.layout_animation_row_right_from_left_side,
				R.anim.layout_animation_row_right_slide);
		finish();
	}

	/**
	 * insert the value in the database
	 */
	public void databaseHelper() {

		// TODO Auto-generated method stub
		String title = this.title.getText().toString();
		String description = this.description.getText().toString();

		db.open();
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		int id = preferences.getInt("id", 0);

		db.insertinfoBookmark(title, description, id);

		db.close();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == back) {
			Intent intent = new Intent(this, BookmarkActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			startActivity(intent);

			this.overridePendingTransition(
					R.anim.layout_animation_row_right_from_left_side,
					R.anim.layout_animation_row_right_slide);
			finish();
		} else if (v == save) {

			boolean result = validate();

			if (result) {
				databaseHelper();
				Intent intent = new Intent(this, BookmarkActivity.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(intent);

				this.overridePendingTransition(
						R.anim.layout_animation_row_right_from_left_side,
						R.anim.layout_animation_row_right_slide);
				finish();

			}
		}
		if (v == home) {

			Util.homeAnimation(this);
			finish();
		}

	}

	@Override
	public void getIds() {
		// TODO Auto-generated method stub
		back = (LinearLayout) findViewById(R.id.linear_layout_img_bck);
		home = (ImageView) findViewById(R.id.img_home);
		save = (Button) findViewById(R.id.button_title_bookmark_save);
		title = (EditText) findViewById(R.id.edittext_bookmark_title);
		description = (EditText) findViewById(R.id.edittext_bookmark_description);
		header_text = (TextView) findViewById(R.id.header_text);
		header_text.setText("Other Information");
	}

	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		back.setOnClickListener(this);
		save.setOnClickListener(this);
		home.setOnClickListener(this);
	}

	@Override
	public boolean validate() {
		if (title.getText().toString().length() == 0) {
			Toast.makeText(this, "Please fill Title name", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else if (description.getText().toString().length() == 0) {
			Toast.makeText(this, "Please fill Description", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else {
			return true;
		}
	}
}
