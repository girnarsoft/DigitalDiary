package com.girnar.online_digital_diary.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
public class OtherInfoActivity extends Activity implements ImportantMethod,
		OnClickListener {
	private AutoCompleteTextView banks_name;
	private EditText account_no, location, holders_name;
	private DbHelper db = new DbHelper(this);
	private Button save;
	private final String TAG = "OtherInfoActivity";
	private Tracker tracker;
	LinearLayout back;
	// private LinearLayout linearLayout;
	private ImageView home;
	private String bank[] = { "Punjab National Bank", "State Bank Of Bikaner",
			"State Bank Of India", "Union Bank", "Allahabad Bank",
			"Andhra Bank", "Bank of Baroda", "Bank of India",
			"Bank of Maharashtra", "Canara Bank", "Central Bank of India",
			"Corporation Bank", "Dena Bank", "IDBI Bank", "Indian Bank",
			"Indian Overseas Bank", "Oriental Bank of Commerce",
			"Punjab and Sind Bank", "Syndicate Bank", "UCO Bank",
			"Union Bank of India", "United Bank of India", "Vijaya Bank",
			"Punjab & Sind Bank", "UCO_Bank", "ECGC",
			"State Bank of Bikaner & Jaipur", "State Bank of Hyderabad",
			"State Bank of Mysore", "State Bank of Patiala",
			"State Bank of Travancore", "State Bank of Saurashtra",
			"State bank of Indore", "Axis Bank", "Federal Bank",
			"Karnataka Bank", "South Indian Bank", "ABN Amro Bank",
			"HDFC Bank", "Karur Vysya Bank", "YES Bank",
			"Catholic Syrian Bank", "ICICI Bank", "Kotak Mahindra Bank",
			"IndusInd Bank", "Lakshmi Vilas Bank", "Dhanlaxmi Bank",
			"ING Vysya Bank", "Tamilnadu Mercantile Bank",
			"Development Credit Bank", "Abu Dhabi Commercial Bank",
			"Australia and New Zealand Bank", "Bank Internasional Indonesia",
			"Bank of America NA", "Bank of Bahrain and Kuwait",
			"Bank of Ceylon", "Bank of Nova Scotia",
			"Bank of Tokyo Mitsubishi UFJ", "Barclays Bank PLC", "BNP Paribas",
			"Calyon Bank", "Chinatrust Commercial Bank", "Citibank N.A.",
			"Credit Suisse", "Commonwealth Bank of Australia", "DBS Bank",
			"DCB Bank now RHB Bank", "Deutsche Bank AG", "FirstRand Bank",
			"HSBC", "JPMorgan Chase Bank", "Krung Thai Bank",
			"Mashreq Bank psc", "Mizuho Corporate Bank",
			"Royal Bank of Scotland", "Shinhan Bank", "Société Générale",
			"Sonali Bank", "Standard Chartered Bank",
			"State Bank of Mauritius", "UBS", "Woori Bank",
			"ABN AMRO Bank N.V. - Royal Bank of Scotland",
			"National Australia Bank" };
	private TextView header_text;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "on create called...");
		setContentView(R.layout.activity_other_info);
		// For hide Keyboard
		View view = (View) findViewById(R.id.addNewBankAccountInfoPage);
		Util.setupUI(view, this);
		EasyTracker.getInstance().setContext(getApplicationContext());
		tracker = EasyTracker.getTracker();
		tracker.trackView("Account Information  Digital_Diary");
		getIds();
		addListener();
		Util.addGoogleAds(this);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getApplicationContext(), R.layout.custom_spinner, bank);

		banks_name.setThreshold(1);
		banks_name.setAdapter(adapter);

	}

	@Override
	public void getIds() {
		// TODO Auto-generated method stub
		banks_name = (AutoCompleteTextView) findViewById(R.id.auto_bank_name_other_info);

		back = (LinearLayout) findViewById(R.id.linear_layout_img_bck);
		home = (ImageView) findViewById(R.id.img_home);
		save = (Button) findViewById(R.id.button_other_submit);
		account_no = (EditText) findViewById(R.id.edittext_other_account_no);
		holders_name = (EditText) findViewById(R.id.edittext_other_holders_name);
		location = (EditText) findViewById(R.id.edittext_other_location);
		// linearLayout = (LinearLayout)
		// findViewById(R.id.layout_custom_spinner);
		header_text = (TextView) findViewById(R.id.header_text);
		header_text.setText("Bank Account Information");
	}

	/**
	 * get the value from the database
	 */
	public void databaseHelper() {
		//

		// TODO Auto-generated method stub
		String account_no = this.account_no.getText().toString();
		String holders_name = this.holders_name.getText().toString();
		String banks_name = this.banks_name.getEditableText().toString();
		String location = this.location.getText().toString();

		db.open();
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		int id = preferences.getInt("id", 0);
		db.insertinfoOther(holders_name, account_no, banks_name, location, id);

		db.close();
	}

	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		// cancel.setOnClickListener(this);
		save.setOnClickListener(this);
		back.setOnClickListener(this);
		home.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stubca

		if (v == back) {
			Intent intent = new Intent(this, OtherListviewInfoActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);

			this.overridePendingTransition(
					R.anim.layout_animation_row_right_from_left_side,
					R.anim.layout_animation_row_right_slide);
			finish();
		}
		if (v == save) {

			boolean result = validate();

			if (result) {
				databaseHelper();
				Intent intent = new Intent(this,
						OtherListviewInfoActivity.class)
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
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, OtherListviewInfoActivity.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);

		this.overridePendingTransition(
				R.anim.layout_animation_row_right_from_left_side,
				R.anim.layout_animation_row_right_slide);
		finish();
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		if (account_no.getText().toString().length() == 0) {
			Toast.makeText(this, "Please fill account no.", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else if (holders_name.getText().toString().length() == 0) {
			Toast.makeText(this, "Please fill holder name", Toast.LENGTH_SHORT)
					.show();
			return false;

		} else if (banks_name.getText().toString().length() == 0) {
			Toast.makeText(this, "Please fill bank name", Toast.LENGTH_SHORT)
					.show();
			return false;

		} else if (location.getText().toString().length() == 0) {
			Toast.makeText(this, "Please fill location", Toast.LENGTH_SHORT)
					.show();
			return false;

		}

		else {
			return true;
		}

	}
}
