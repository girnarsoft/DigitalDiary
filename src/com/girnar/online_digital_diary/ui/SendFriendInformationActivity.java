package com.girnar.online_digital_diary.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.girnar.online_digital_diary.adapters.SendFriendInfoAdapter;
import com.girnar.online_digital_diary.beans.Items;
import com.girnar.online_digital_diary.database.DbHelper;
import com.girnar.online_digital_diary.interfaces.ImportantMethod;
import com.girnar.online_digital_diary.util.Util;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;

/**
 * @author
 * 
 */
public class SendFriendInformationActivity extends Activity implements
		OnItemClickListener, ImportantMethod, OnCheckedChangeListener,
		OnClickListener {
	private final String TAG = "SendListViewActivity";
	private ListView listView;
	private Tracker tracker;
	private DbHelper db = new DbHelper(this);
	private int id;
	ArrayList<Items> items = new ArrayList<Items>();
	HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();
	private static CheckBox selectAll;
	public static int select_all_flag = 0;
	private ImageView home;
	private LinearLayout back;
	private TextView header_text;
	private ArrayList<Integer> selected_ids_for_send = new ArrayList<Integer>();
	private String selected_text_for_send = "";
	private int index;
	private Button btn_sendAccountInfo;
	private EditText editTextSearch;
	public static ArrayList<String> checked_ids;
	

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "on create called...");
		setContentView(R.layout.activity_send_account_info);
		send_ids=new ArrayList<Integer>();
		opened_ids=new ArrayList<Integer>();
		checked_ids = new ArrayList<String>();
		View view = (View) findViewById(R.id.activity_send_account_info);
		Util.setupUI(view, this);
		EasyTracker.getInstance().setContext(getApplicationContext());
		tracker = EasyTracker.getTracker();
		tracker.trackView("Send the Friends information Digital_Diary");
		getIds();
		addListener();
		Util.addGoogleAds(this);
		db.open();
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		int id = preferences.getInt("id", 0);
		Cursor cursor = db.getInfoperson(id);
		db.close();
		if (cursor != null) {
			cursor.moveToFirst();

			for (int i = 0; i < cursor.getCount(); i++) {
				Items item = new Items();
				item.setName(cursor.getString(1));
				item.setId(cursor.getString(0));
				item.setChecked(false);
				item.setVisible(View.GONE);
				List<String> detail = new ArrayList<String>();
				detail.add(cursor.getString(4));
				detail.add(cursor.getString(6));
				detail.add(cursor.getString(3));
				detail.add(cursor.getString(5));
				detail.add(cursor.getString(2));
				detail.add(cursor.getString(9));
				listDataChild.put(item.getName(), detail);
				items.add(item);
				cursor.moveToNext();
			}
		}

		SendFriendInfoAdapter adapter = new SendFriendInfoAdapter(
				SendFriendInformationActivity.this, items, listDataChild,true);
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();

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
		listView = (ListView) findViewById(R.id.sendinfo_listView_account);
		selectAll = (CheckBox) findViewById(R.id.checkBoxSendAllAccountInfo);
		back = (LinearLayout) findViewById(R.id.linear_layout_img_bck);
		home = (ImageView) findViewById(R.id.img_home);
		header_text = (TextView) findViewById(R.id.header_text);
		header_text.setText("Send Friend Info");
		btn_sendAccountInfo = (Button) findViewById(R.id.button_send_account_information);
		editTextSearch = (EditText) findViewById(R.id.edittext_search_info);
	}

	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		listView.setOnItemClickListener(this);
		selectAll.setOnClickListener(this);
		back.setOnClickListener(this);
		home.setOnClickListener(this);
		btn_sendAccountInfo.setOnClickListener(this);
		editTextSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				clearSendInfoIds();
				db.open();
				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(SendFriendInformationActivity.this);
				int id = preferences.getInt("id", 0);
				Cursor cursor = db.searchInfopersonMethod(editTextSearch
						.getText().toString(), id);
				db.close();
				listDataChild.clear();
				items.clear();

				if (cursor != null) {
					cursor.moveToFirst();
					for (int i = 0; i < cursor.getCount(); i++) {
						Items item = new Items();
						item.setName(cursor.getString(1));
						item.setId(cursor.getString(0));
						if (checked_ids.contains(item.getId() + "checked")){
							SendFriendInformationActivity.setSendInfoIds(
									Integer.parseInt(item.getId()), false);
							item.setChecked(true);
						}
						else
							item.setChecked(false);
						item.setVisible(View.GONE);
						List<String> detail = new ArrayList<String>();
						detail.add(cursor.getString(4));
						detail.add(cursor.getString(6));
						detail.add(cursor.getString(3));
						detail.add(cursor.getString(5));
						detail.add(cursor.getString(2));
						detail.add(cursor.getString(9));
						listDataChild.put(item.getName(), detail);
						items.add(item);
						
						cursor.moveToNext();
					}
				}

				SendFriendInfoAdapter adapter = new SendFriendInfoAdapter(
						SendFriendInformationActivity.this, items,
						listDataChild,false);
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();

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
	public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		
	}

	public static void uncheckedSelectAllButton() {
		// TODO Auto-generated method stub
		selectAll.setChecked(false);
		select_all_flag = 0;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == back) {
			finish();
		}

		else if (v == home) {
			Util.homeAnimation(this);
			finish();
		} else if (v == btn_sendAccountInfo) {

			if (select_all_flag == 1) {
				for (int i = 0; i < items.size(); i++) {
					index = i;
					selected_ids_for_send.add(Integer.parseInt(items.get(index)
							.getId()));
				}

			} else {
				for (int i = 0; i < send_ids.size(); i++) {
					for (int j = 0; j < items.size(); j++) {
						if (Integer.parseInt(items.get(j).getId()) == send_ids
								.get(i)) {
							index = j;
							break;
						}
					}

					selected_ids_for_send.add(Integer.parseInt(items.get(index)
							.getId()));
				}

			}

		
			for (int i = 0; i < selected_ids_for_send.size(); i++) {
//				int id = Integer.parseInt(this.items.get(i).getId());

				db.open();
				Cursor cursor = db.getInfopersonMethod((selected_ids_for_send.get(i)));
				db.close();

				String name = cursor.getString(0);
				String address = cursor.getString(1);
				String birthday = cursor.getString(2);
				String mobile = cursor.getString(3);
				String aniv = cursor.getString(4);
				String email = cursor.getString(5);
				String age = cursor.getString(6);

				String text = (i + 1) + ". \tName := " + name + "\n\tAddress :="
						+ address + "\n\tB'Day := " + birthday
						+ "\n\tMobile no := " + mobile + "\n\tAnniversary :="
						+ aniv + "\n\tEmail := " + email + "\n\tAge := " + age+"\n";

				selected_text_for_send += text;
			}
			if(selected_ids_for_send.size()==0){
				Toast.makeText(this, "Please select Friend Informations for send", Toast.LENGTH_LONG).show();	
				}else{

			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			emailIntent.setType("plain/text");
			emailIntent.putExtra("id", id);
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
					new String[] { "to@email.com" });
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"Subject");

			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
					selected_text_for_send);

			// need this to prompts email client only
			emailIntent.setType("message/rfc822");

			startActivity(Intent.createChooser(emailIntent, "Send mail..."));
			selected_ids_for_send.clear();
			selected_text_for_send="";
				}
		}
		else if(v==selectAll){
			CheckBox cb = (CheckBox) v;
			 if (cb.isChecked()) {
				clearSendInfoIds();
				select_all_flag = 1;
				db.open();
				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(this);
				int id = preferences.getInt("id", 0);
				Cursor cursor = db.getInfoperson(id);
				db.close();
				ArrayList<Items> items = new ArrayList<Items>();
				listDataChild.clear();
				items.clear();

				if (cursor != null) {
					cursor.moveToFirst();
					for (int i = 0; i < cursor.getCount(); i++) {
						Items item = new Items();
						item.setName(cursor.getString(1));
						item.setId(cursor.getString(0));
						item.setChecked(true);
						if(opened_ids.contains(Integer.parseInt(item.getId())))
						item.setVisible(View.VISIBLE);
						else
						item.setVisible(View.GONE);
						List<String> detail = new ArrayList<String>();
						detail.add(cursor.getString(4));
						detail.add(cursor.getString(6));
						detail.add(cursor.getString(3));
						detail.add(cursor.getString(5));
						detail.add(cursor.getString(2));
						detail.add(cursor.getString(9));
						listDataChild.put(item.getName(), detail);
						items.add(item);
						SendFriendInformationActivity.setSendInfoIds(
								Integer.parseInt(item.getId()), true);
						cursor.moveToNext();
					}
				}
				SendFriendInfoAdapter adapter = new SendFriendInfoAdapter(
						SendFriendInformationActivity.this, items, listDataChild,false);
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			} else {
				select_all_flag = 2;
				clearSendInfoIds();
				clearCheckedIds();
				for(int i=0;i<items.size();i++){
					if(opened_ids.contains(Integer.parseInt(items.get(i).getId())))
						items.get(i).setVisible(View.VISIBLE);
						else
						items.get(i).setVisible(View.GONE);
				}
				SendFriendInfoAdapter adapter = new SendFriendInfoAdapter(
						SendFriendInformationActivity.this, items, listDataChild,false);
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
		}
	}

	public static ArrayList<Integer> send_ids;

	public static void setSendInfoIds(int id, boolean flag) {
		// TODO Auto-generated method stub
		if (flag) {
			checked_ids.add(id + "checked");
		}
		System.out.println("id is " + id);
		send_ids.add(id);
	}

	public static void removeSendInfoIds(int id, boolean flag) {
		// TODO Auto-generated method stub
		if (flag) {
			checked_ids.remove(checked_ids.indexOf(id + "checked"));
		}
		send_ids.remove(send_ids.indexOf(id));
	}


	public static ArrayList<Integer> opened_ids;

	public static void setSendOpendIds(int id) {
		// TODO Auto-generated method stub
		
		opened_ids.add(id);
	}

	public static void removeSendOpenedIds(int id) {
		// TODO Auto-generated method stub
		opened_ids.remove(opened_ids.indexOf(id));
	}

	public static void clearSendInfoIds() {
		// TODO Auto-generated method stub
		send_ids.clear();
	}
	public static void clearCheckedIds() {
		// TODO Auto-generated method stub
		checked_ids.clear();
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		selected_text_for_send="";
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		select_all_flag=0;
		
	}

}
