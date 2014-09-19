package com.girnar.online_digital_diary.ui;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
public class UpdatePersonActivity extends Activity implements ImportantMethod,
		OnClickListener {
	private DbHelper db = new DbHelper(this);
	private String[] data;
	private Button save;
	private final String TAG = "UpdatePersonActivity";
	ImageView image, home;
	LinearLayout back;
	byte[] image_byteArr;
	static final int DATE_DIALOG_ID = 0;
	static final int DATE_DIALOG_ID1 = 1;

	private int mYear, mMonth, mDay, year, month, day;
	private Calendar c = Calendar.getInstance();
	Bitmap bitmap;
	private static final int SELECT_PICTURE = 1;

	private String selectedImagePath;
	private EditText person_name, person_address, person_birthday,
			person_mobile_no, person_aniversary, person_email, person_age;
	private RadioGroup preson_gender;
	private RadioButton rb_male, rd_female;
	private int id;
	private String name;
	private String address;
	private String birthday;
	private String mobile_no;
	private String aniversary;
	private String email;
	private String age;
	private String gender;
	long l, aniv_l;

	private TextView header_text;
	private Tracker tracker;

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
		tracker.trackView("Update friend Information Digital_Diary");
		getIds();
		addListener();
		Util.addGoogleAds(this);
		db.open();
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		id = bundle.getInt("id");

		Cursor cursor = db.getInfopersonMethod(id);

		db.close();
		if (cursor != null) {
			setData(new String[cursor.getCount()]);
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {

				name = cursor.getString(0);

				address = cursor.getString(1);
				birthday = cursor.getString(2);
				mobile_no = cursor.getString(3);
				aniversary = cursor.getString(4);
				email = cursor.getString(5);
				age = cursor.getString(6);
				image_byteArr = cursor.getBlob(7);
				gender = cursor.getString(8);
				person_name.setText(name);
				person_address.setText(address);
				person_birthday.setText(birthday);
				person_mobile_no.setText(mobile_no);
				person_aniversary.setText(aniversary);
				person_email.setText(email);
				person_age.setText(age);
				if (gender.equals("male")) {
					rb_male.setChecked(true);
				} else {
					rd_female.setChecked(true);
				}
				BitmapFactory.Options options = new BitmapFactory.Options();
				bitmap = BitmapFactory.decodeByteArray(image_byteArr, 0,
						image_byteArr.length, options);
				image.setImageBitmap(bitmap);
				String[] birth_date_split = birthday.split("/");
				int bitth_date = Integer.parseInt(birth_date_split[0]);
				int birth_month = Integer.parseInt(birth_date_split[1]);
				int birth_year = Integer.parseInt(birth_date_split[2].trim());
				l = Util.timeInMills1(birth_year, birth_month - 1, bitth_date,
						6, 0);
				if(aniversary.equals("")){
					
				}else{
				String[] aniv_date_split = aniversary.split("/");
				int aniv_day = Integer.parseInt(aniv_date_split[0]);
				int aniv_month = Integer.parseInt(aniv_date_split[1]);
				int aniv_year = Integer.parseInt(aniv_date_split[2].trim());
				aniv_l = Util.timeInMills1(aniv_year, aniv_month - 1, aniv_day,
						6, 0);
				}

				cursor.moveToNext();
			}
		}
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

	@Override
	public void getIds() {
		// TODO Auto-generated method stub
		image = (ImageView) findViewById(R.id.imageView_person);
		person_name = (EditText) findViewById(R.id.edittext_name_personal_info);
		person_address = (EditText) findViewById(R.id.edittext_address_personal_info);
		person_birthday = (EditText) findViewById(R.id.edittext_birthday_personal_info);
		person_mobile_no = (EditText) findViewById(R.id.edittext_mobile_no_personal_info);
		person_aniversary = (EditText) findViewById(R.id.edittext_aniversary_personal_info);
		person_email = (EditText) findViewById(R.id.edittext_email_personal_info);
		person_age = (EditText) findViewById(R.id.edittext_age_personal_info);
		preson_gender = (RadioGroup) findViewById(R.id.radio_group_registration);
		save = (Button) findViewById(R.id.button_person_info_save);
		back = (LinearLayout) findViewById(R.id.linear_layout_img_bck);
		home = (ImageView) findViewById(R.id.img_home);
		header_text = (TextView) findViewById(R.id.header_text);
		header_text.setText("Update Friend Information");
		rb_male = (RadioButton) findViewById(R.id.rb_male);
		rd_female = (RadioButton) findViewById(R.id.rb_female);

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
			person_birthday.setText(new StringBuilder().append(mDay)
					.append("/").append(mMonth + 1).append("/").append(mYear));

		}

	};

	private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			person_aniversary.setText(new StringBuilder().append(mDay)
					.append("/").append(mMonth + 1).append("/").append(mYear));

		}

	};

	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		person_birthday.setOnClickListener(this);

		person_birthday.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				showDialog(DATE_DIALOG_ID);

			}
		});

		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		person_aniversary.setOnClickListener(this);

		person_aniversary.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				showDialog(DATE_DIALOG_ID1);

			}
		});
		save.setOnClickListener(this);
		back.setOnClickListener(this);
		home.setOnClickListener(this);
	}

	private boolean validateMobileNumber() {
		String regexStr = "^[0-9]{10}$";
		String mobile = person_mobile_no.getText().toString();
		Pattern pettern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pettern.matcher(mobile);
		if (matcher.matches()) {
			return true;
		} else {
			if (person_mobile_no.getText().toString().length() == 0) {
				return true;
			}

			return false;
		}

	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		boolean isMobileValid = validateMobileNumber();
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		String abc = person_email.getText().toString();
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(abc);

		if (person_name.getText().toString().length() == 0) {
			Toast.makeText(this, "Please fill name", Toast.LENGTH_SHORT).show();
			return false;
		} else if (person_address.getText().toString().length() == 0) {
			Toast.makeText(this, "Please fill address", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else if (person_birthday.getText().toString().length() == 0) {
			Toast.makeText(this, "Plese fill birthday", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else if (!isMobileValid) {
			Toast.makeText(this, "Please fill the correct mobile no.",
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (person_email.equals(person_email.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Invalid Email",
					Toast.LENGTH_SHORT).show();
			return false;

		} else if (person_age.getText().toString().length() == 0) {
			Toast.makeText(this, "Please fill the Age", Toast.LENGTH_SHORT)
					.show();
			return false;
		}

		else if (!matcher.matches()) {
			Toast.makeText(this, "Please fill valid email", Toast.LENGTH_SHORT)
					.show();
			return false;

		}

		else {
			return true;
		}

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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == save) {
			String name = person_name.getText().toString();
			String address = person_address.getText().toString();
			String birthday = person_birthday.getText().toString();
			String mobile_no = person_mobile_no.getText().toString();
			String aniversary = person_aniversary.getText().toString();
			String email = person_email.getText().toString();
			String age = person_age.getText().toString();
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] byteArray = stream.toByteArray();
			int rid = this.preson_gender.getCheckedRadioButtonId();
			RadioButton gender = (RadioButton) findViewById(rid);
			if (gender == null) {
				System.out.println("gender is null...");
			}
			String genderText = gender.getText().toString();

			db.open();
			long insert_id = db.UpdateMethod(id, name, address, birthday,
					mobile_no, aniversary, email, age, byteArray, genderText);

			db.close();
			NotificationReceiver.cancelBirthdayAlarm(this, l, name
					+ "'s Birthday today", id);
			
			NotificationReceiver.cancelBirthdayAlarm(this, aniv_l, name
					+ "'s Anniversary today", id * 1000);

			String[] birth_date_split = birthday.split("/");
			int day = Integer.parseInt(birth_date_split[0]);
			int month = Integer.parseInt(birth_date_split[1]);
			int year = Integer.parseInt(birth_date_split[2].trim());
			setBirthDayReminder(year, month, day, (int) insert_id, name);

			if (aniversary.equals("")) {

			} else {
				String[] aniv_date_split = aniversary.split("/");
				int aniv_day = Integer.parseInt(aniv_date_split[0]);
				int aniv_month = Integer.parseInt(aniv_date_split[1]);
				int aniv_year = Integer.parseInt(aniv_date_split[2].trim());
				setAnivReminder(aniv_year, aniv_month, aniv_day,
						(int) insert_id, name);
			}

			Intent intent = new Intent(this, FrndListActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			startActivity(intent);

			this.overridePendingTransition(
					R.anim.layout_animation_row_right_from_left_side,
					R.anim.layout_animation_row_right_slide);
			finish();
		}
		if (v == back) {
			Intent intent = new Intent(this, FrndListActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			startActivity(intent);

			this.overridePendingTransition(
					R.anim.layout_animation_row_right_from_left_side,
					R.anim.layout_animation_row_right_slide);
			finish();
		}
		if (v == home) {

			Util.homeAnimation(this);
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
	 * @return selectedImagePath;
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
