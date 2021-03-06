package com.girnar.online_digital_diary.util;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author
 *
 */
public class UiDatePicker extends Dialog {

	private Context context;
	private View view;
	private String pattern = "/";
	private UiToast uiToast;
	
	/**
	 *  mYear
	 */
	public int mYear;
	/**
	 *  mMonth
	 */
	public int mMonth;
	/**
	 * mDay
	 */
	public int mDay;
	private boolean isMultiNeed ;
	
	/**
	 * Arraylist of integer type
	 */
	public ArrayList<ArrayList<Integer>> listOfDate; 
	
	/**
	 * @param context
	 * @param isSupprotMultiple
	 */
	public UiDatePicker(Context context, boolean isSupprotMultiple) {
		super(context);
		this.context = context;
		uiToast = new UiToast(context);
		this.isMultiNeed = isSupprotMultiple;
		
		listOfDate = new ArrayList<ArrayList<Integer>>();
	}

	/**
	 * 
	 * @param view View of that widget which can handle returned string.
	 * @param separator Provide a separator for returned date.
	 */
	public void showDialog(View view, EditText editText , String separator) {
		this.view = view;
		this.pattern = separator;
		this.editText = editText;
		
		Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog dialog = new DatePickerDialog(context, mDateSetListener, mYear, mMonth, mDay);
		dialog.show();
	}
	EditText editText;
	DatePickerDialog.OnDateSetListener mDateSetListener = new OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker v, int year, int monthOfYear, int dayOfMonth) {
			
			
			mYear	= year;
			mMonth	= monthOfYear;
			mDay	= dayOfMonth;
			

			if(isMultiNeed)
			{
		    ArrayList<Integer> setOfDate = new ArrayList<Integer>();
		    setOfDate.add(mYear);
		    setOfDate.add(mMonth);
		    setOfDate.add(mDay);
		    
		    listOfDate.add(setOfDate);

			}
			setDate();
		}
	};
	
	private void setDate() {
		String str = String.format("%02d", mDay) + pattern + String.format("%02d", (mMonth+1)) + pattern + String.format("%02d", mYear) + " ";
		
		if (view instanceof TextView) {
			
			TextView tv = (TextView) view;
			if((tv.getTag()==null) || (tv.getTag()!=null) && !(tv.getTag().toString().equals("useNormalFont")))
			{
				if(isMultiNeed)
					tv.setText(tv.getText().toString() + str + " ");
				else
					tv.setText(str);
			}
		} else if (view instanceof ImageButton) {
			
			EditText ed = (EditText) editText;
			if((ed.getTag()==null) || (ed.getTag()!=null) && !(ed.getTag().toString().equals("useNormalFont")))
			{
				if(isMultiNeed)
					ed.setText(ed.getText().toString() + str + " ");
				else
					ed.setText(str);
			}
		}		
	}	
	
	private void setDate(View v) {
		String str = String.format("%02d", mDay) + pattern + String.format("%02d", (mMonth+1)) + pattern + String.format("%02d", mYear);
		
		if (v instanceof TextView) {
			TextView tv = (TextView) v;
			if((tv.getTag()==null) || (tv.getTag()!=null) && !(tv.getTag().toString().equals("useNormalFont")))
			{
				if(isMultiNeed)
					tv.setText(tv.getText().toString() + str + " ");
				else
					tv.setText(str);
			}
		} else if (v instanceof EditText) {
			EditText ed = (EditText) v;
			if((ed.getTag()==null) || (ed.getTag()!=null) && !(ed.getTag().toString().equals("useNormalFont")))
			{
				if(isMultiNeed)
					ed.setText(ed.getText().toString() + str + " ");
				else
					ed.setText(str);
			}
		}		
	}
		
	private void setDate(int year, int month, int date, View view) {
		String str = String.format("%02d", date) + pattern + String.format("%02d", (month+1)) + pattern + String.format("%02d", year) + " ";
		
		if (view instanceof TextView) {
			TextView tv = (TextView) view;
			if((tv.getTag()==null) || (tv.getTag()!=null) && !(tv.getTag().toString().equals("useNormalFont")))
			{
				if(isMultiNeed)
					tv.setText(tv.getText().toString() + str + " ");
				else
					tv.setText(str);
			}
		} else if (view instanceof EditText) {
			EditText ed = (EditText) view;
			if((ed.getTag()==null) || (ed.getTag()!=null) && !(ed.getTag().toString().equals("useNormalFont")))
			{
				if(isMultiNeed)
					ed.setText(ed.getText().toString() + str + " ");
				else
					ed.setText(str);
			}
		}		
	}

	/**
	 * @param view
	 */
	public void setDateText(View view) {
		
		for (int i = 0; i < listOfDate.size(); i++) {
			setDate(listOfDate.get(i).get(0), listOfDate.get(i).get(1), listOfDate.get(i).get(2) , view);
		}
	}
	
	/**
	 * @param timeInMills
	 * @param view
	 * @param separator
	 */
	public void setDate(long timeInMills, View view, String separator) {
				
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTimeInMillis(timeInMills);
	    mYear	= calendar.get(Calendar.YEAR);
	    mMonth	= calendar.get(Calendar.MONTH);
	    mDay	= calendar.get(Calendar.DAY_OF_MONTH);
	    
		this.pattern = separator;
	    
	    setDate(view);
	}
	
	@SuppressWarnings("unused")
	private void checkPattern(String str) {
		String[] patternList = {"/", "-", "."};
		if (str.matches(patternList[0])) {
			this.pattern = str;
		} else if (str.matches(patternList[1])) {
			this.pattern = str;
		} else if (str.matches(patternList[2])) {
			this.pattern = str;
		} else {
			uiToast.showToast("Invalid Pattern please choose form these /n / - .", Toast.LENGTH_LONG);
			return;
		}
	}

	/**
	 * @param view
	 */
	public void removeDate(View view)
	{
		if(listOfDate.size() > 0)
		{
			listOfDate.remove(listOfDate.size()-1);
			setDateText(view);
		}
	}

	/**
	 * @param timeInMills
	 */
	public void addDate(long timeInMills)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMills);
		
		Log.w("Row", "" + "Year " + calendar.get(Calendar.YEAR) + " Month " + calendar.get(Calendar.MONTH) + " Day " + calendar.get(Calendar.DAY_OF_MONTH));
		ArrayList<Integer> setOfDate = new ArrayList<Integer>();
		setOfDate.add(calendar.get(Calendar.YEAR));
		setOfDate.add(calendar.get(Calendar.MONTH));
		setOfDate.add(calendar.get(Calendar.DAY_OF_MONTH));
		    
		listOfDate.add(setOfDate);
	}
}