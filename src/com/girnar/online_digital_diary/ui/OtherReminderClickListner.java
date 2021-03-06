package com.girnar.online_digital_diary.ui;


import com.girnar.online_digital_diary.ui.R;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author
 *
 */
public abstract class OtherReminderClickListner implements OnClickListener {

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		
		case R.id.reminder_info_setTime:
			onTimeClick(view);;
			break;
			
		case R.id.reminder_info_setdate:
			onDateClick(view);
			break;
			
			
			
		case R.id.button_reminder_info_save:
			onSetRemenderClick();
			break;
			
		
			
		default:
			break;
		}
	}


	/**
	 * @param view
	 */
	public abstract void onTimeClick(View view);

	/**
	 * @param view
	 */
	public abstract void onDateClick(View view);

	/**
	 * method
	 */
	public abstract void onDescriptionClick();
	
	/**
	 * method
	 */
	public abstract void onSetRemenderClick();

	/**
	 * method
	 */
	public abstract void onDayClick();
}
