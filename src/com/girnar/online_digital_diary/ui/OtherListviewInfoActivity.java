package com.girnar.online_digital_diary.ui;

import java.util.ArrayList;

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

import com.girnar.online_digital_diary.adapters.CustomAdapter;
import com.girnar.online_digital_diary.database.DbHelper;
import com.girnar.online_digital_diary.interfaces.ImportantMethod;
import com.girnar.online_digital_diary.util.Util;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;

/**
 * @author
 * 
 */
public class OtherListviewInfoActivity extends Activity implements
		ImportantMethod, OnClickListener, OnItemClickListener {
	private Button New;
	private ListView listView;
	private final String TAG = "OtherListviewInfoActivity";
	/**
	 * reference of OtherListviewInfoActivity
	 */
	public static OtherListviewInfoActivity activity;
	private DbHelper db = new DbHelper(this);
	private Tracker tracker;
	LinearLayout back;
	private ImageView home;
	private TextView no_acc;
	ImageButton search;
	EditText editTextSearch;
	private ArrayList<String> data = new ArrayList<String>();
	private ArrayList<String> ids = new ArrayList<String>();
	private TextView header_text;
	private View footerView;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "on create called...");
		activity = this;
		setContentView(R.layout.activity_other_listview_info);
		// For hide Keyboard
		View view = (View) findViewById(R.id.bankAccounts);
		Util.setupUI(view, this);
		EasyTracker.getInstance().setContext(getApplicationContext());
		tracker = EasyTracker.getTracker();
		tracker.trackView("List of Account information Digital_Diary");
		getIds();
		addListener();
		Util.addGoogleAds(this);
		db.open();
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		int id = preferences.getInt("id", 0);
		Cursor cursor = db.getInfoOther(id);
		db.close();
		if (cursor != null) {
			cursor.moveToFirst();

			for (int i = 0; i < cursor.getCount(); i++) {
				ids.add(cursor.getString(0));
				data.add(cursor.getString(1));

				cursor.moveToNext();
			}
		}

		CustomAdapter adapter = new CustomAdapter(
				OtherListviewInfoActivity.this, data, ids, "OTHERINFO",
				"account_no_id", null, null);
		listView.setAdapter(adapter);

		if (cursor.getCount() == 0) {

			no_acc.setVisibility(View.VISIBLE);
			listView.setVisibility(View.GONE);
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		finish();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		ids.clear();
		data.clear();
		db.open();
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		int id = preferences.getInt("id", 0);
		Cursor cursor = db.getInfoOther(id);
		db.close();
		if (cursor != null) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				ids.add(cursor.getString(0));
				data.add(cursor.getString(1));

				cursor.moveToNext();
			}
		}

		CustomAdapter adapter = new CustomAdapter(
				OtherListviewInfoActivity.this, data, ids, "OTHERINFO",
				"account_no_id", null, null);
		listView.setAdapter(adapter);

		if (cursor.getCount() == 0) {

			no_acc.setVisibility(View.VISIBLE);
			listView.setVisibility(View.GONE);
		} else if (cursor.getCount() > 0) {

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
		New = (Button) findViewById(R.id.button_other_info_new_listview);
		back = (LinearLayout) findViewById(R.id.linear_layout_img_bck);
		home = (ImageView) findViewById(R.id.img_home);
		listView = (ListView) findViewById(R.id.other_info_listView);
		no_acc = (TextView) findViewById(R.id.text_no_acc);
		header_text = (TextView) findViewById(R.id.header_text);
		header_text.setText("Bank Accounts List");
		search = (ImageButton) findViewById(R.id.img_button_search);
		editTextSearch = (EditText) findViewById(R.id.edittext_account_search);
		footerView = getLayoutInflater()
				.inflate(R.layout.listview_footer, null);

		listView.addFooterView(footerView);
	}

	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		New.setOnClickListener(this);
		back.setOnClickListener(this);
		home.setOnClickListener(this);
		listView.setOnItemClickListener(this);
		search.setOnClickListener(this);
		editTextSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				db.open();
				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(OtherListviewInfoActivity.this);
				int id = preferences.getInt("id", 0);
				Cursor cursor = db.searchInfoOther(editTextSearch.getText()
						.toString(), id);
				db.close();
				ids.clear();
				data.clear();

				if (cursor != null) {
					cursor.moveToFirst();
					for (int i = 0; i < cursor.getCount(); i++) {
						ids.add(cursor.getString(0));
						data.add(cursor.getString(1));
						cursor.moveToNext();
					}
				}

				CustomAdapter adapter = new CustomAdapter(
						OtherListviewInfoActivity.this, data, ids, "OTHERINFO",
						"account_no_id", null, null);
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
			Intent intent = new Intent(this, OtherInfoActivity.class);
			startActivity(intent);
			this.overridePendingTransition(
					R.anim.layout_animation_row_left_from_right_side,
					R.anim.layout_animation_row_left_slide);
		} else if (v == back) {

			finish();
		} else if (v == home) {

			finish();
		}
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

}
