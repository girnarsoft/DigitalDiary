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
import android.widget.AdapterView.OnItemLongClickListener;
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
import com.girnar.online_digital_diary.util.UiDatePicker;
import com.girnar.online_digital_diary.util.UiTimePicker;
import com.girnar.online_digital_diary.util.Util;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;

/**
 * @author
 * 
 */
public class FrndListActivity extends Activity implements ImportantMethod,
		OnClickListener, OnItemLongClickListener {
	private Button New;
	LinearLayout back;
	private ImageView home;
	private final String TAG = "FrndListActivity";
	/**
	 * reference of FrndListActivity class
	 */
	public static FrndListActivity activity;

	private DbHelper db = new DbHelper(this);
	private ListView listView;
	private ArrayList<String> data = new ArrayList<String>();
	private ArrayList<String> ids = new ArrayList<String>();
	UiDatePicker uiDatePicker;
	UiTimePicker uiTimePicker;
	private Tracker tracker;
	private TextView no_acc;
	ImageButton search;
	private TextView header_text;
	private EditText editTextSearch;
	View footerView;
	private ArrayList<Long> bithday_millis = new ArrayList<Long>();
	private ArrayList<Long> aniv_millis = new ArrayList<Long>();
	

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "on create called...");
		activity = this;
		setContentView(R.layout.activity_frnd_list);
		// For hide Keyboard
		View view = (View) findViewById(R.id.friendListPage);
		Util.setupUI(view, this);
		EasyTracker.getInstance().setContext(getApplicationContext());
		tracker = EasyTracker.getTracker();
		tracker.trackView("Friend information_Digital_Diary");

		getIds();
		addListener();
		Util.addGoogleAds(this);
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		int id = preferences.getInt("id", 0);
		db.open();

		Cursor cursor = db.getInfoperson(id);
		db.close();
		if (cursor != null) {
			cursor.moveToFirst();

			for (int i = 0; i < cursor.getCount(); i++) {
				ids.add(cursor.getString(0));
				data.add(cursor.getString(1));

				String[] birth_date_split = cursor.getString(3).split("/");
				int day = Integer.parseInt(birth_date_split[0]);
				int month = Integer.parseInt(birth_date_split[1]);
				int year = Integer.parseInt(birth_date_split[2].trim());
				long l = Util.timeInMills1(year, month - 1, day, 6, 0);
				bithday_millis.add(l);
				if(cursor.getString(5).equals("")){
					aniv_millis.add(null);
				}else{
				String[] aniv_date_split = cursor.getString(5).split("/");
				int aniv_day = Integer.parseInt(aniv_date_split[0]);
				int aniv_month = Integer.parseInt(aniv_date_split[1]);
				int aniv_year = Integer.parseInt(aniv_date_split[2].trim());
				long aniv_l = Util.timeInMills1(aniv_year, aniv_month - 1, aniv_day, 6, 0);
				aniv_millis.add(aniv_l);
				}
				cursor.moveToNext();
			}
		}

		CustomAdapter adapter = new CustomAdapter(FrndListActivity.this, data,
				ids, "PERSONALINFORMATION", "person_id", bithday_millis,aniv_millis);
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
		bithday_millis.clear();
		aniv_millis.clear();
		db.open();
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		int id = preferences.getInt("id", 0);
		Cursor cursor = db.getInfoperson(id);
		db.close();
		if (cursor != null) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				ids.add(cursor.getString(0));
				data.add(cursor.getString(1));
				String[] birth_date_split = cursor.getString(3).split("/");
				int day = Integer.parseInt(birth_date_split[0]);
				int month = Integer.parseInt(birth_date_split[1]);
				int year = Integer.parseInt(birth_date_split[2].trim());
				long l = Util.timeInMills1(year, month - 1, day, 6, 0);
				bithday_millis.add(l);
				if(cursor.getString(5).equals("")){
					aniv_millis.add(null);
				}else{
				String[] aniv_date_split = cursor.getString(5).split("/");
				int aniv_day = Integer.parseInt(aniv_date_split[0]);
				int aniv_month = Integer.parseInt(aniv_date_split[1]);
				int aniv_year = Integer.parseInt(aniv_date_split[2].trim());
				long aniv_l = Util.timeInMills1(aniv_year, aniv_month - 1, aniv_day, 6, 0);
				aniv_millis.add(aniv_l);
				}
				cursor.moveToNext();
			}
		}
		CustomAdapter adapter = new CustomAdapter(FrndListActivity.this, data,
				ids, "PERSONALINFORMATION", "person_id", bithday_millis,aniv_millis);
		listView.setAdapter(adapter);
		if (cursor.getCount() == 0) {
			
			no_acc.setVisibility(View.VISIBLE);
			listView.setVisibility(View.GONE);
		}else if (cursor.getCount() > 0) {

			no_acc.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == New) {
			Intent intent = new Intent(this, PersonalInformationActivity.class);
			startActivity(intent);
			this.overridePendingTransition(
					R.anim.layout_animation_row_left_from_right_side,
					R.anim.layout_animation_row_left_slide);
		}
		if (v == back) {

			finish();
		}
		if (v == home) {

			finish();
		}

	}

	@Override
	public void getIds() {

		// TODO Auto-generated method stub
		New = (Button) findViewById(R.id.add_new_friend);
		back = (LinearLayout) findViewById(R.id.linear_layout_img_bck);
		home = (ImageView) findViewById(R.id.img_home);
		no_acc = (TextView) findViewById(R.id.text_no_acc);
		listView = (ListView) findViewById(R.id.show_frnd_list_listView);
		header_text = (TextView) findViewById(R.id.header_text);
		header_text.setText("Friends List");
		editTextSearch = (EditText) findViewById(R.id.edittext_search_personal_info);
		footerView = getLayoutInflater()
				.inflate(R.layout.listview_footer, null);

		listView.addFooterView(footerView);
	}

	@Override
	public void addListener() {

		if (New == null) {

		}
		// TODO Auto-generated method stub
		New.setOnClickListener(this);
		back.setOnClickListener(this);
		home.setOnClickListener(this);
		listView.setOnItemLongClickListener(this);
		editTextSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				db.open();
				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(FrndListActivity.this);
				int id = preferences.getInt("id", 0);
				Cursor cursor = db.searchInfopersonMethod(editTextSearch
						.getText().toString(), id);
				db.close();
				ids.clear();
				data.clear();
				bithday_millis.clear();
				aniv_millis.clear();

				if (cursor != null) {
					cursor.moveToFirst();
					for (int i = 0; i < cursor.getCount(); i++) {
						ids.add(cursor.getString(0));
						data.add(cursor.getString(1));
						String[] birth_date_split = cursor.getString(3).split(
								"/");
						int day = Integer.parseInt(birth_date_split[0]);
						int month = Integer.parseInt(birth_date_split[1]);
						int year = Integer.parseInt(birth_date_split[2].trim());
						long l = Util.timeInMills1(year, month - 1, day, 6, 0);
						bithday_millis.add(l);
						if(cursor.getString(5).equals("")){
							aniv_millis.add(null);
						}else{
						String[] aniv_date_split = cursor.getString(5).split("/");
						int aniv_day = Integer.parseInt(aniv_date_split[0]);
						int aniv_month = Integer.parseInt(aniv_date_split[1]);
						int aniv_year = Integer.parseInt(aniv_date_split[2].trim());
						long aniv_l = Util.timeInMills1(aniv_year, aniv_month - 1, aniv_day, 6, 0);
						aniv_millis.add(aniv_l);
						}
						cursor.moveToNext();
					}
				}

				CustomAdapter adapter = new CustomAdapter(
						FrndListActivity.this, data, ids,
						"PERSONALINFORMATION", "person_id", bithday_millis,aniv_millis);
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				if (cursor.getCount() == 0) {

					no_acc.setVisibility(View.VISIBLE);
					listView.setVisibility(View.GONE);
				} else if (cursor.getCount() > 0) {

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
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int index,
			long arg3) {
		// TODO Auto-generated method stub

		return true;
	}

}
