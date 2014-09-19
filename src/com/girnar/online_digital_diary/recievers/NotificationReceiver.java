package com.girnar.online_digital_diary.recievers;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.girnar.online_digital_diary.ui.FrndListActivity;
import com.girnar.online_digital_diary.ui.LoginPageActivity;
import com.girnar.online_digital_diary.ui.R;
import com.girnar.online_digital_diary.ui.SplashScreen;

/**
 * @author
 * 
 */
public class NotificationReceiver extends BroadcastReceiver {

	private static NotificationManager nm;

	MediaPlayer mediaPlayer;
	PendingIntent contentIntent;

	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.w("TAG", "Notification fired...");
		
		nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		

	
		Notification notif = null;
		@SuppressWarnings("unused")
		NotificationManager myNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Bundle bundle = intent.getExtras().getBundle("NotificationBundle");
		int id = intent.getExtras().getInt("id");
		Log.w("ID", "" + id);

		if (bundle == null) {
			contentIntent = PendingIntent.getActivity(context, 0, new Intent(
					context, SplashScreen.class), 0);

			notif = new Notification(R.drawable.reminder_icon,
					"Sunscreen Reapplying Reminder", System.currentTimeMillis());
			notif.setLatestEventInfo(context, "Reminder",
					"Sunscreen Reapplying Reminder", contentIntent);
			notif.flags |= Notification.FLAG_AUTO_CANCEL;
			
			notif.vibrate = new long[] { 500L, 200L, 200L, 500L };
			notif.defaults |= Notification.DEFAULT_SOUND;

		}

		else {
		
			if (intent.getAction() == "reminder") {
				Log.w("Bundle", bundle + "");
				contentIntent = PendingIntent.getActivity(context, 0,
						new Intent(context, LoginPageActivity.class)
								.putExtra("NotificationBundle", bundle).putExtra("auth", "reminder"),
						PendingIntent.FLAG_UPDATE_CURRENT);

				
			} else if (intent.getAction() == "birthday") {
				Log.w("Bundle", bundle + "");
				contentIntent = PendingIntent.getActivity(context, 0,
						new Intent(context, FrndListActivity.class).putExtra(
								"NotificationBundle", bundle).putExtra("auth", "friend"),
						PendingIntent.FLAG_UPDATE_CURRENT);

				
			}
			notif = new Notification(R.drawable.reminder_icon,
					bundle.getString("NotificationDesc"),
					System.currentTimeMillis());
			notif.setLatestEventInfo(context, "Reminder",
					bundle.getString("NotificationDesc"), contentIntent);
			notif.flags |= Notification.FLAG_AUTO_CANCEL;
			notif.vibrate = new long[] { 500L, 200L, 200L, 500L };
			notif.defaults |= Notification.DEFAULT_SOUND;
		}

		nm.notify(id, notif);
	}

	/**
	 * @param context
	 * @param fireTime
	 * @param repetDelay
	 */
	public static void setAutoRepetNotification(Context context, long fireTime,
			long repetDelay) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, NotificationReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, (int) fireTime,
				i, 0);
		am.setRepeating(AlarmManager.RTC_WAKEUP, fireTime, repetDelay, pi);
	}

	/**
	 * @param context
	 * @param fireTime
	 */
	public static void setNotificationOnDateTime(Context context, long fireTime) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, NotificationReceiver.class);
		i.putExtra("id", (int) fireTime);
		i.setData(Uri.parse("" + fireTime));
		i.addCategory("" + fireTime);

		PendingIntent pi = PendingIntent.getBroadcast(context, (int) fireTime,
				i, 0);
		am.set(AlarmManager.RTC_WAKEUP, fireTime, pi);

	}

	/**
	 * @param context
	 * @param fireTime
	 * @param desc
	 * @param id
	 */
	public static void setNotificationOnDateTime(Context context,
			long fireTime, String desc, int id) {
		
		Bundle bundle = new Bundle();
		// bundle.putString("NotificationTitle", title);
		bundle.putString("NotificationDesc", desc);
		bundle.putLong("NotificationTimeInMills", fireTime);
		bundle.putInt("id", id);

		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, NotificationReceiver.class);
		i.putExtra("NotificationBundle", bundle);
		i.putExtra("id", id);
//		i.putExtra("name", "reminder");
		i.setAction("reminder");

		PendingIntent pi = PendingIntent.getBroadcast(context, id, i,
				PendingIntent.FLAG_UPDATE_CURRENT);

		am.set(AlarmManager.RTC_WAKEUP, fireTime, pi);
		
	}

	public static void setNotificationForBirthDay(Context context,
			long fireTime, String desc, int id) {
		
		Bundle bundle = new Bundle();
		// bundle.putString("NotificationTitle", title);
		bundle.putString("NotificationDesc", desc);
		bundle.putLong("NotificationTimeInMills", fireTime);
		bundle.putInt("id", id);

		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, NotificationReceiver.class);
		i.putExtra("NotificationBundle", bundle);
		i.putExtra("id", id);
		
		i.setAction("birthday");

		PendingIntent pi = PendingIntent.getBroadcast(context, id, i,
				PendingIntent.FLAG_UPDATE_CURRENT);

		am.set(AlarmManager.RTC_WAKEUP, fireTime, pi);
	}

	/**
	 * @param context
	 * @param id
	 */
	public static void cancelAllAlarm(Context context,
			long fireTime, String desc, int id) {
		
		Bundle bundle = new Bundle();
		// bundle.putString("NotificationTitle", title);
		bundle.putString("NotificationDesc", desc);
		bundle.putLong("NotificationTimeInMills", fireTime);
		bundle.putInt("id", id);

		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, NotificationReceiver.class);
		i.putExtra("NotificationBundle", bundle);
		i.putExtra("id", id);
//		i.putExtra("name", "reminder");
		i.setAction("reminder");
		PendingIntent sender = PendingIntent.getBroadcast(context, id, i,
				PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);

		if (nm == null)
			nm = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);

		nm.cancel(id);
	}
	
	/**
	 * @param context
	 * @param id
	 */
	public static void cancelBirthdayAlarm(Context context,
			long fireTime, String desc, int id) {
		
		Bundle bundle = new Bundle();
		// bundle.putString("NotificationTitle", title);
		bundle.putString("NotificationDesc", desc);
		bundle.putLong("NotificationTimeInMills", fireTime);
		bundle.putInt("id", id);

		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, NotificationReceiver.class);
		i.putExtra("NotificationBundle", bundle);
		i.putExtra("id", id);
		
		i.setAction("birthday");

		PendingIntent sender = PendingIntent.getBroadcast(context, id, i,
				PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);

		if (nm == null)
			nm = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);

		nm.cancel(id);
	}

}
