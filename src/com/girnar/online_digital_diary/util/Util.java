package com.girnar.online_digital_diary.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.girnar.online_digital_diary.database.DbHelper;
import com.girnar.online_digital_diary.recievers.NotificationReceiver;
import com.girnar.online_digital_diary.ui.HomePageActivity;
import com.girnar.online_digital_diary.ui.R;
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.ads.AdRequest.ErrorCode;

public class Util {

	private static int mYear, mMonth, mDay, hour, minute;
	// private ArrayList<String> date = new ArrayList<String>();
	// private ArrayList<String> time = new ArrayList<String>();
	private ArrayList<String> ids = new ArrayList<String>();
	private int id;
	
	public static void homeAnimation(Activity context) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(context, HomePageActivity.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);

		context.overridePendingTransition(
				R.anim.layout_animation_row_right_from_left_side,
				R.anim.layout_animation_row_right_slide);
	}
	
	public static void hideSoftKeyboard(Activity activity) {
	    InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
	    try{
	    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	    }
	    catch(Exception exception){
	    	
	    }
	}
	
	public static void setupUI(View view ,final Activity activity) {

	    //Set up touch listener for non-text box views to hide keyboard.
	    if(!(view instanceof EditText)) {

	        view.setOnTouchListener(new OnTouchListener() {

	            public boolean onTouch(View v, MotionEvent event) {
	                Util.hideSoftKeyboard(activity);
	                return false;
	            }

	        });
	    }

	    //If a layout container, iterate over children and seed recursion.
	    if (view instanceof ViewGroup) {

	        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

	            View innerView = ((ViewGroup) view).getChildAt(i);

	            setupUI(innerView,activity);
	        }
	    }
	}
	static DbHelper db;
	public static void checkNotification(Activity context) {
		// TODO Auto-generated method stub
		db = new DbHelper(context);
		Date d = new Date();
		@SuppressWarnings("unused")
		CharSequence s = DateFormat.format("MMMM d, yyyy ", d.getTime());
		
		// id = 1;

		db.open();
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		int id = preferences.getInt("id", 0);
		Cursor cursor = db.getReminderMethodInfo(id);

		if (cursor != null) {
			cursor.moveToFirst();
		
			

			Calendar calendar = Calendar.getInstance();
			setmYear(calendar.get(Calendar.YEAR));
			setmMonth(calendar.get(Calendar.MONTH));
			setmDay(calendar.get(Calendar.DAY_OF_MONTH));

			for (int i = 0; i < cursor.getCount(); i++) {

				SimpleDateFormat format = new SimpleDateFormat(
						"dd-MM-yyyy hh:mm");
				String ret_date = cursor.getString(1) + " "
						+ cursor.getString(2);
				Date date1 = null, date2 = null;
				try {
					date1 = format.parse(ret_date.trim());
					Calendar c = Calendar.getInstance();
					date2 = format.parse(format.format(c.getTime()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				long hourdiff = date1.getTime() - date2.getTime();

				if (hourdiff <= 0) {

				}

				else if (hourdiff > 0) {

					String[] date_split = cursor.getString(1).split("-");

					String[] time_split = cursor.getString(2).split(":");

					String[] srr = time_split[1].split(" ");

					int day = Integer.parseInt(date_split[0]);
					int month = Integer.parseInt(date_split[1]);
					int year = Integer.parseInt(date_split[2].trim());
					int hour = Integer.parseInt(time_split[0]);
					int minute = Integer.parseInt(srr[0]);
					if (srr[1].equals("PM")) {

						hour = hour + 12;
					}

					long l = timeInMills(year, month - 1, day, hour, minute);

					NotificationReceiver.setNotificationOnDateTime(context, l,
							"message", (int) id);
				}
				cursor.moveToNext();

			}
			db.close();
		}
	}

		public static long timeInMills(int mYear, int mMonth, int mDay, int mHour,
				int mMinute) {
			Date dat = new Date();// initializes to now
			Calendar cal_alarm = Calendar.getInstance();
			Calendar cal_now = Calendar.getInstance();
			cal_now.setTime(dat);
			cal_alarm.set(mYear, mMonth, mDay, mHour, mMinute, 0);

			if (cal_alarm.before(cal_now)) {// if its in the past increment

				cal_alarm.add(Calendar.DATE, 1);
			}

			return cal_alarm.getTimeInMillis();
		}
		
		public static long timeInMills1(int mYear, int mMonth, int mDay, int mHour,
				int mMinute) {
			Date dat = new Date();// initializes to now
			Calendar cal_alarm = Calendar.getInstance();
			Calendar cal_now = Calendar.getInstance();
			cal_now.setTime(dat);
			cal_alarm.set(mYear, mMonth, mDay, mHour, mMinute, 0);

			return cal_alarm.getTimeInMillis();
		}

	
		/**
		 * @return mYear;
		 */
		public int getmYear() {
			return mYear;
		}

		/**
		 * @param mYear
		 */
		public static void setmYear(int mYear) {
			Util.mYear = mYear;
		}

		/**
		 * @return mMonth
		 */
		public int getmMonth() {
			return mMonth;
		}

		/**
		 * @param mMonth
		 */
		public static void setmMonth(int mMonth) {
			Util.mMonth = mMonth;
		}

		/**
		 * @return mDay;
		 */
		public int getmDay() {
			return mDay;
		}

		/**
		 * @param mDay
		 */
		public static void setmDay(int mDay) {
			Util.mDay = mDay;
		}

		/**
		 * @return hour;
		 */
		public int getHour() {
			return hour;
		}

		/**
		 * @param hour
		 */
		public void setHour(int hour) {
			Util.hour = hour;
		}

		/**
		 * @return minute;
		 */
		public int getMinute() {
			return minute;
		}

		/**
		 * @param minute
		 */
		public void setMinute(int minute) {
			Util.minute = minute;
		}

		
		/**
		 * @return id;
		 */
		public ArrayList<String> getIds() {
			return ids;
		}

		/**
		 * @param ids
		 */
		public void setIds(ArrayList<String> ids) {
			this.ids = ids;
		}

		/**
		 * @return id;
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
		
		public static AdView adView;
		public static AdRequest adRequest;
		public static void addGoogleAds(Activity activity) {
			adView = new AdView(activity, AdSize.SMART_BANNER, activity.
					getResources().getString(R.string.google_ads_id));
			LinearLayout layout = (LinearLayout) activity.findViewById(R.id.googleads);
			layout.addView(adView);
			adRequest = new AdRequest();
			final LinearLayout pBar = (LinearLayout) activity.findViewById(R.id.gooleaddload);
			pBar.setVisibility(View.VISIBLE);
			// adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
			adView.setAdListener(new AdListener() {

				@Override
				public void onReceiveAd(Ad arg0) {
					pBar.setVisibility(View.GONE);
				}

				@Override
				public void onPresentScreen(Ad arg0) {
				}

				@Override
				public void onLeaveApplication(Ad arg0) {
				}

			
				@Override
				public void onDismissScreen(Ad arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
					// TODO Auto-generated method stub

				}
			});
			adView.loadAd(adRequest);

		}
		
		

}
