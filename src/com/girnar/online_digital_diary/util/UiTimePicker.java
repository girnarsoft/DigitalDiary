package com.girnar.online_digital_diary.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * @author
 * 
 */
public class UiTimePicker extends Dialog {
	/**
	 * reference of context
	 */
	public Context context;
	/**
	 * reference of view
	 */
	public View view;

	/**
	 * mHour
	 */
	public int mHour;
	/**
	 * mMinute
	 */
	public int mMinute;
	private boolean isMultiNeed;

	/**
	 * array list of integer type
	 */
	public ArrayList<ArrayList<Integer>> listOfTime;

	/**
	 * @param context
	 * @param isSupprotMultiple
	 */
	public UiTimePicker(Context context, boolean isSupprotMultiple) {
		super(context);
		this.context = context;
		this.isMultiNeed = isSupprotMultiple;

		Calendar c = Calendar.getInstance();
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);

		listOfTime = new ArrayList<ArrayList<Integer>>();
	}

	EditText editText;

	/**
	 * @param view
	 *            View of that widget which can handle returned string.
	 */
	public void showDialog(View view, EditText editText) {
		this.view = view;
		this.editText = editText;
		TimePickerDialog dialog = new TimePickerDialog(context,
				mTimeSetListener, mHour, mMinute, false);
		dialog.show();
	}

	TimePickerDialog.OnTimeSetListener mTimeSetListener = new OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker v, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;

			if (isMultiNeed) {
				ArrayList<Integer> setOfTime = new ArrayList<Integer>();
				setOfTime.add(mHour);
				setOfTime.add(mMinute);
				setOfTime.add(0);

				listOfTime.add(setOfTime);
			}

			setTime(hourOfDay, minute, view);
		}
	};

	/**
	 * @param hour
	 * @param minute
	 * @param view
	 */
	public void setTime(int hour, int minute, View view) {
		mHour = hour;
		mMinute = minute;

		if (view instanceof TextView) {
			TextView tv = (TextView) view;
			if ((tv.getTag() == null) || (tv.getTag() != null)
					&& !(tv.getTag().toString().equals("useNormalFont"))) {
				if (isMultiNeed)
					tv.setText(tv.getText().toString() + setTimeFormet() + ",");
				else
					tv.setText(setTimeFormet());
			}
		} else if (view instanceof ImageButton) {
			EditText ed = (EditText) editText;
			if ((ed.getTag() == null) || (ed.getTag() != null)
					&& !(ed.getTag().toString().equals("useNormalFont"))) {
				if (isMultiNeed)
					ed.setText(ed.getText().toString() + setTimeFormet() + ",");
				else
					ed.setText(setTimeFormet());
			}
		}
	}

	/**
	 * @param timeInMills
	 * @param view
	 */
	public void setTime(long timeInMills, View view) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMills);
		mHour = calendar.get(Calendar.HOUR_OF_DAY);
		mMinute = calendar.get(Calendar.MINUTE);

		if (view instanceof TextView) {
			TextView tv = (TextView) view;
			if ((tv.getTag() == null) || (tv.getTag() != null)
					&& !(tv.getTag().toString().equals("useNormalFont"))) {
				tv.setText(setTimeFormet());
			}
		} else if (view instanceof EditText) {
			EditText ed = (EditText) view;
			if ((ed.getTag() == null) || (ed.getTag() != null)
					&& !(ed.getTag().toString().equals("useNormalFont"))) {
				ed.setText(setTimeFormet());
			}
		}
	}

	/**
	 * @param view
	 */
	public void setTimeText(View view) {

		for (int i = 0; i < listOfTime.size(); i++) {
			Log.w("Time Picker", "Hour: " + listOfTime.get(i).get(0)
					+ " Minutes: " + listOfTime.get(i).get(1));
			setTime(listOfTime.get(i).get(0), listOfTime.get(i).get(1), view);
		}
	}

	/**
	 * @return String.valueOf(String.format("%02d", mHour-12) + ":" +
	 *         (String.valueOf(String.format("%02d", mMinute)+" PM")));
	 */
	public String setTimeFormet() {
		if (mHour > 12)
			return String.valueOf(String.format("%02d", mHour - 12) + ":"
					+ (String.valueOf(String.format("%02d", mMinute) + " PM")));

		if (mHour == 12)
			return "12" + ":"
					+ (String.valueOf(String.format("%02d", mMinute) + " PM"));

		if (mHour < 12)
			return String.valueOf(String.format("%02d", mHour) + ":"
					+ (String.valueOf(String.format("%02d", mMinute) + " AM")));

		return "";
	}

	/**
	 * @return cal_alarm.getTimeInMillis();
	 */
	public long timeInMills() {
		Date dat = new Date();// initializes to now
		Calendar cal_alarm = Calendar.getInstance();
		Calendar cal_now = Calendar.getInstance();
		cal_now.setTime(dat);
		cal_alarm.setTime(dat);
		cal_alarm.set(Calendar.HOUR_OF_DAY, mHour);// set the alarm time
		cal_alarm.set(Calendar.MINUTE, mMinute);
		cal_alarm.set(Calendar.SECOND, 0);
		if (cal_alarm.before(cal_now)) {// if its in the past increment
			cal_alarm.add(Calendar.DATE, 1);
		}

		return cal_alarm.getTimeInMillis();
	}

	/**
	 * @param mYear
	 * @param mMonth
	 * @param mDay
	 * @return cal_alarm.getTimeInMillis();
	 */
	public long timeInMills(int mYear, int mMonth, int mDay) {
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
	 * @param mHour
	 * @param mMinute
	 * @return cal_alarm.getTimeInMillis();
	 */
	public long timeInMills(int mHour, int mMinute) {
		Calendar cal_alarm = Calendar.getInstance();
		cal_alarm.set(mHour, mMinute, 0);
		return cal_alarm.getTimeInMillis();
	}

	/**
	 * @param view
	 */
	public void removeTime(View view) {
		if (!listOfTime.isEmpty()) {
			listOfTime.remove(listOfTime.size() - 1);
			setTimeText(view);
		}
	}

	/**
	 * @param timeInMills
	 */
	public void addTime(long timeInMills) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMills);

		Log.w("Row", "" + "hour " + calendar.get(Calendar.HOUR_OF_DAY)
				+ " minute " + calendar.get(Calendar.MINUTE));
		ArrayList<Integer> setOfTime = new ArrayList<Integer>();
		setOfTime.add(calendar.get(Calendar.HOUR_OF_DAY));
		setOfTime.add(calendar.get(Calendar.MINUTE));
		setOfTime.add(0);
		listOfTime.add(setOfTime);
	}

}
