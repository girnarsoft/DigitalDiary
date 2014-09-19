package com.girnar.online_digital_diary.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * @author
 *
 */
public class UiToast extends Toast {
	Context context;
	
	/**
	 * @param context
	 */
	public UiToast(Context context) {
		super(context);
		this.context = context;
	}
	
	/**
	 * @param string
	 * @param toastLength
	 */
	public void showToast(final String string, final int toastLength) {
		((Activity) context).runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(context, string, toastLength).show();
			}
		});
	}
}
