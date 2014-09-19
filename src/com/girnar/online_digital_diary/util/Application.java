package com.girnar.online_digital_diary.util;

import android.app.AlertDialog;
import android.content.Context;

/**
 * @author 
 *
 */
public class Application extends android.app.Application {

	  private static boolean activityVisible;
	    
	  /**
	 * @return activityVisible
	 */
	public static boolean isActivityVisible() {
	    return activityVisible;
	  }

	  /**
	 * method of activityResumed()
	 */
	public static void activityResumed() {
	    activityVisible = true;
	  }

	  /**
	 * method of activityPaused()
	 */
	public static void activityPaused() {
	    activityVisible = false;
	  }
	  
	  /**
	 * @param context
	 * @param title
	 * @param message
	 */
	public static void showOKAleart(Context context, String title, String message) {
		  AlertDialog.Builder builder = new AlertDialog.Builder(context);
		  builder
		  .setTitle(title)
		  .setMessage(message)
		  .setNegativeButton( "OK", null).show();
	  }
}
