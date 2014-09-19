package com.girnar.online_digital_diary.ui;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.girnar.online_digital_diary.database.DbHelper;
import com.girnar.online_digital_diary.interfaces.ImportantMethod;
import com.girnar.online_digital_diary.recievers.NotificationReceiver;
import com.girnar.online_digital_diary.util.Util;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;

/**
 * @author
 * 
 */
public class PersonalInformationActivity extends Activity implements
		OnClickListener, ImportantMethod {
	// private TextView time, date;
	ImageView image, home;
	LinearLayout back;

	private final String TAG = "PersonalInformationActivity";
	static final int DATE_DIALOG_ID = 0;
	static final int DATE_DIALOG_ID1 = 1;

	private int mYear, mMonth, mDay, year, month, day;
	private Calendar c = Calendar.getInstance();
	byte[] Image;
	private RadioGroup gender;
	private TextView textView;
	private Button save;
	private EditText name, address, birthday, mobile_no, aniversary, email,
			age;
	private DbHelper db = new DbHelper(this);
	private boolean ageIsNotCorrect = true;
	Bitmap bitmap;
	private static final int SELECT_PICTURE = 1;
	private Tracker tracker;
	private String selectedImagePath;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "on create called...");
		setContentView(R.layout.activity_personal_information);
		// For hide Keyboard
		View view = (View) findViewById(R.id.friendInformationPage);
		Util.setupUI(view, this);
		EasyTracker.getInstance().setContext(getApplicationContext());
		tracker = EasyTracker.getTracker();
		tracker.trackView("Personal Information Digital_Diary");
		getIds();
		addListener();
		Util.addGoogleAds(this);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, FrndListActivity.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);

		this.overridePendingTransition(
				R.anim.layout_animation_row_right_from_left_side,
				R.anim.layout_animation_row_right_slide);
		finish();
	}

	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case 0:

			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);

		case 1:

			return new DatePickerDialog(this, dateSetListener, mYear, mMonth,
					mDay);
		default:
			return null;
		}

	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			birthday.setText(new StringBuilder().append(mDay).append("/")
					.append(mMonth + 1).append("/").append(mYear));
			String dateOfBirth = mMonth + "-" + mDay + "-" + mYear;

			DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
			Date date = new Date();
			System.out.println(dateFormat.format(date));
			String currentDate = dateFormat.format(date);

			try {
				Calendar cal1 = new GregorianCalendar();
				Calendar cal2 = new GregorianCalendar();
				int age = 0;
				int factor = 0;

				Date date1 = new SimpleDateFormat("MM-dd-yyyy")
						.parse(dateOfBirth);

				Date date2 = new SimpleDateFormat("MM-dd-yyyy")
						.parse(currentDate);
				cal1.setTime(date1);
				cal2.setTime(date2);
				if (cal2.get(Calendar.DAY_OF_YEAR) < cal1
						.get(Calendar.DAY_OF_YEAR)) {
					factor = -1;
				}
				age = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR)
						+ factor;
				System.out.println("Your age is: " + age);
				if (age < 0) {
					Toast.makeText(PersonalInformationActivity.this,
							R.string.AgeWarning, Toast.LENGTH_SHORT).show();
					ageIsNotCorrect = false;
				} else{
					ageIsNotCorrect=true;
					PersonalInformationActivity.this.age.setText("" + age);
				}
			} catch (ParseException e) {
				System.out.println(e);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	};

	private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			aniversary.setText(new StringBuilder().append(mDay).append("/")
					.append(mMonth + 1).append("/").append(mYear));

		}

	};
	private TextView header_text;

	@Override
	public void getIds() {
		// TODO Auto-generated method stub
		image = (ImageView) findViewById(R.id.imageView_person);
		name = (EditText) findViewById(R.id.edittext_name_personal_info);
		address = (EditText) findViewById(R.id.edittext_address_personal_info);
		birthday = (EditText) findViewById(R.id.edittext_birthday_personal_info);
		mobile_no = (EditText) findViewById(R.id.edittext_mobile_no_personal_info);
		aniversary = (EditText) findViewById(R.id.edittext_aniversary_personal_info);
		email = (EditText) findViewById(R.id.edittext_email_personal_info);
		gender = (RadioGroup) findViewById(R.id.radio_group_registration);
		age = (EditText) findViewById(R.id.edittext_age_personal_info);
		save = (Button) findViewById(R.id.button_person_info_save);
		back = (LinearLayout) findViewById(R.id.linear_layout_img_bck);
		home = (ImageView) findViewById(R.id.img_home);
		header_text = (TextView) findViewById(R.id.header_text);
		header_text.setText("Friends Information");
	}

	/**
	 * insert the value in the database
	 */
	public void databaseHelper() {
		//
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		if (bitmap == null) {
			bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
		}
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

		byte[] byteArray = stream.toByteArray();
		// TODO Auto-generated method stub
		String name = this.name.getText().toString();
		String address = this.address.getText().toString();
		String birthday = this.birthday.getText().toString();

		String mobile_no = this.mobile_no.getText().toString();
		String aniversary = this.aniversary.getText().toString();

		String email = this.email.getText().toString();
		String age = this.age.getText().toString();
		int rid = this.gender.getCheckedRadioButtonId();
		RadioButton gender = (RadioButton) findViewById(rid);
		if (gender == null) {
			System.out.println("gender is null...");
		}
		String genderText = gender.getText().toString();
		db.open();
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		int id = preferences.getInt("id", 0);
		long insert_id = db.insertinfoperson(name, address, birthday,
				mobile_no, aniversary, email, age, byteArray, id, genderText);

		db.close();
		String[] birth_date_split = birthday.split("/");
		int day = Integer.parseInt(birth_date_split[0]);
		int month = Integer.parseInt(birth_date_split[1]);
		int year = Integer.parseInt(birth_date_split[2].trim());
		setBirthDayReminder(year, month, day, (int) insert_id, this.name
				.getText().toString());

		if (aniversary.equals("")) {

		} else {
			String[] aniv_date_split = aniversary.split("/");
			int aniv_day = Integer.parseInt(aniv_date_split[0]);
			int aniv_month = Integer.parseInt(aniv_date_split[1]);
			int aniv_year = Integer.parseInt(aniv_date_split[2].trim());
			setAnivReminder(aniv_year, aniv_month, aniv_day, (int) insert_id,
					this.name.getText().toString());
		}
	}

	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		save.setOnClickListener(this);

		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		birthday.setOnClickListener(this);

		birthday.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				showDialog(DATE_DIALOG_ID);

			}
		});
		gender.setOnClickListener(this);
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		aniversary.setOnClickListener(this);

		aniversary.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				showDialog(DATE_DIALOG_ID1);

			}
		});
		back.setOnClickListener(this);
		home.setOnClickListener(this);
		image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				startActivityForResult(intent, SELECT_PICTURE);
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		InputStream streams = null;
		if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK) {
			image.setVisibility(View.VISIBLE);
			try {
				// We need to recyle unused bitmaps
				if (bitmap != null) {
					bitmap.recycle();
				}
				streams = getContentResolver().openInputStream(data.getData());
				bitmap = BitmapFactory.decodeStream(streams);
				Uri selectedImageUri = data.getData();

				setSelectedImagePath(getPath(selectedImageUri));

				image.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (streams != null)
					try {
						streams.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
	}

	/**
	 * @param uri
	 * @return cursor.getString(column_index);
	 */
	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		@SuppressWarnings("deprecation")
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	private boolean validateMobileNumber() {
		String regexStr = "^[0-9]{10}$";
		String mobile = mobile_no.getText().toString();
		Pattern pettern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pettern.matcher(mobile);
		if (matcher.matches()) {
			return true;
		} else {
			if (mobile_no.getText().toString().length() == 0) {
				return true;
			}

			return false;
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v == save) {

			boolean result = validate();

			if (result) {
				databaseHelper();
				Intent intent = new Intent(this, FrndListActivity.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

				this.overridePendingTransition(
						R.anim.layout_animation_row_right_from_left_side,
						R.anim.layout_animation_row_right_slide);
				finish();

			}
		} else if (v == back) {
			Intent intent = new Intent(this, FrndListActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			startActivity(intent);

			this.overridePendingTransition(
					R.anim.layout_animation_row_right_from_left_side,
					R.anim.layout_animation_row_right_slide);
			finish();

		} else if (v == home) {

			Util.homeAnimation(this);

			finish();
		}

	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		int id = this.gender.getCheckedRadioButtonId();
		boolean isMobileValid = validateMobileNumber();
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		String abc = email.getText().toString();

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(abc);

		if (name.getText().toString().length() == 0) {
			Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else if (address.getText().toString().length() == 0) {
			Toast.makeText(this, "Please enter address", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else if (birthday.getText().toString().length() == 0) {
			Toast.makeText(this, "Plese enter birthday", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else if (!isMobileValid) {
			Toast.makeText(this, "Please enter the correct mobile no.",
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (email.equals(email.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Invalid Email",
					Toast.LENGTH_SHORT).show();
			return false;

		} else if (age.getText().toString().length() == 0) {
			Toast.makeText(this, "Please enter the Age", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else if (!ageIsNotCorrect) {
			Toast.makeText(this, R.string.AgeWarning, Toast.LENGTH_SHORT)
					.show();
			return false;
		}

		else if (!matcher.matches()) {
			Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT)
					.show();
			return false;

		} else if (id == -1) {
			Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT)
					.show();
			return false;
		}

		else {
			return true;
		}
	}

	/**
	 * @return year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @param month
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * @return day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * @param day
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * @return textview
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
	 * @return selectedImagePath
	 */
	public String getSelectedImagePath() {
		return selectedImagePath;
	}

	/**
	 * @param selectedImagePath
	 */
	public void setSelectedImagePath(String selectedImagePath) {
		this.selectedImagePath = selectedImagePath;
	}

	public void setBirthDayReminder(int year, int month, int date, int id,
			String name) {

		NotificationReceiver.setNotificationForBirthDay(this,
				Util.timeInMills1(year, month - 1, date, 6, 0), name
						+ "'s Birthday today", (int) id);
	}

	public void setAnivReminder(int year, int month, int date, int id,
			String name) {

		NotificationReceiver.setNotificationForBirthDay(this,
				Util.timeInMills1(year, month - 1, date, 6, 0), name
						+ "'s Anniversary today", (id * 1000));
	}

}
