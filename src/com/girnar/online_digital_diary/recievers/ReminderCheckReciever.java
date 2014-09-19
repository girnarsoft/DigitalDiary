package com.girnar.online_digital_diary.recievers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;

import com.girnar.online_digital_diary.database.DbHelper;

/**
 * @author
 * 
 */
public class ReminderCheckReciever extends BroadcastReceiver {

	DbHelper db;
	private int mYear, mMonth, mDay, hour, minute;
	// private ArrayList<String> date = new ArrayList<String>();
	// private ArrayList<String> time = new ArrayList<String>();
	private ArrayList<String> ids = new ArrayList<String>();
	private int id;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		db = new DbHelper(context);
		Date d = new Date();
		@SuppressWarnings("unused")
		CharSequence s = DateFormat.format("MMMM d, yyyy ", d.getTime());
		

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

	public long timeInMills(int mYear, int mMonth, int mDay, int mHour,
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

	/**
	 * @return mYear;
	 */
	public int getmYear() {
		return mYear;
	}

	/**
	 * @param mYear
	 */
	public void setmYear(int mYear) {
		this.mYear = mYear;
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
	public void setmMonth(int mMonth) {
		this.mMonth = mMonth;
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
	public void setmDay(int mDay) {
		this.mDay = mDay;
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
		this.hour = hour;
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
		this.minute = minute;
	}

	/**
	 * @return date;
	 */
	/*
	 * public ArrayList<String> getDate() { return date; }
	 *//**
	 * @param date
	 */
	/*
	 * public void setDate(ArrayList<String> date) { this.date = date; }
	 *//**
	 * @return time;
	 */
	/*
	 * public ArrayList<String> getTime() { return time; }
	 *//**
	 * @param time
	 */
	/*
	 * public void setTime(ArrayList<String> time) { this.time = time; }
	 */
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
}
