package com.girnar.online_digital_diary.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.girnar.online_digital_diary.database.DbHelper;
import com.girnar.online_digital_diary.recievers.NotificationReceiver;
import com.girnar.online_digital_diary.ui.R;
import com.girnar.online_digital_diary.ui.RemShowInfoActivity;
import com.girnar.online_digital_diary.ui.UpdateAccountInfoActivity;
import com.girnar.online_digital_diary.ui.UpdateOtherInformationActivity;
import com.girnar.online_digital_diary.ui.UpdatePersonActivity;
import com.girnar.online_digital_diary.ui.UpdateReminderActivity;

/**
 * @author
 * 
 */
public class ReminderAdapter extends ArrayAdapter<String> implements
		OnClickListener {
	private final Activity activity;
	private final ArrayList<String> data, ids;
	private final DbHelper dbHelper;
	private final String table_name, table_id;
	private static int selected_id;
	private ArrayList<Long> millis = new ArrayList<Long>();
	private ArrayList<String> descs = new ArrayList<String>();

	/**
	 * @param context
	 * @param data
	 * @param ids
	 * @param table_name
	 * @param table_id
	 */
	public ReminderAdapter(Activity context, ArrayList<String> data,
			ArrayList<String> ids, String table_name, String table_id,ArrayList<Long> millis,ArrayList<String> descs) {
		super(context, R.layout.custom_adapter, data);
		this.activity = context;
		this.data = data;
		this.ids = ids;
		this.table_name = table_name;
		this.table_id = table_id;
		this.millis = millis;
		this.descs = descs;
		dbHelper = new DbHelper(context);
		selected_id = -1;
	}

	static class Holder {
		public TextView textView;
		public ImageView imageView;
		public ImageView imageView_delete;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;

		// Holder holder;
		if (view == null) {

			Holder holder = new Holder();
			LayoutInflater inflater = activity.getLayoutInflater();
			view = inflater.inflate(R.layout.custom_adapter, null);
			holder.textView = (TextView) view
					.findViewById(R.id.listView_textView);
			holder.imageView = (ImageView) view
					.findViewById(R.id.listView_imageView_update);
			holder.imageView_delete = (ImageView) view
					.findViewById(R.id.listView_imageView_delete);
			view.setTag(holder);
		}

		final Holder holder1 = (Holder) view.getTag();

		holder1.textView.setText(data.get(position));

		holder1.textView.setTag(position);
		holder1.imageView.setTag(position);
		holder1.imageView_delete.setTag(position);
		holder1.textView.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub

				int pos = (Integer) holder1.imageView.getTag();

				String id = ids.get(pos);

				Intent intent = new Intent(activity, RemShowInfoActivity.class);
				intent.putExtra("id", Integer.parseInt(id));
				activity.startActivity(intent);

				return true;
			}
		});

		holder1.textView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selected_id = (Integer) holder1.textView.getTag();
				if (imageView != null) {
					imageView.setVisibility(View.GONE);
				}
				if (imageView_delete != null) {
					imageView_delete.setVisibility(View.GONE);
				}

				holder1.imageView.setVisibility(View.VISIBLE);
				holder1.imageView_delete.setVisibility(View.VISIBLE);

				holder1.imageView
						.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								int pos = (Integer) holder1.imageView.getTag();

								Intent intent = new Intent(activity,
										UpdateReminderActivity.class);

								intent.putExtra("id",
										Integer.parseInt(ids.get(pos)));
								activity.startActivity(intent);
								activity.overridePendingTransition(
										R.anim.layout_animation_row_left_from_right_side,
										R.anim.layout_animation_row_left_slide);

							}
						});

				holder1.imageView_delete
						.setOnClickListener(ReminderAdapter.this);

				imageView = holder1.imageView;
				imageView_delete = holder1.imageView_delete;
			}
		});

		if (selected_id == position) {
			holder1.imageView.setVisibility(View.VISIBLE);
			holder1.imageView_delete.setVisibility(View.VISIBLE);
		} else {
			holder1.imageView.setVisibility(View.GONE);
			holder1.imageView_delete.setVisibility(View.GONE);
		}

		return view;

	}

	public static ImageView imageView, imageView_delete;

	@Override
	public void onClick(final View v) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(activity.getString(R.string.delete))
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id1) {
								int position = (Integer) v.getTag();
								int id = Integer.parseInt(ids.get(position));

								@SuppressWarnings("unused")
								String name = data.get(position);

								dbHelper.open();

								dbHelper.deleteMethod(id, table_name, table_id);
								
								dbHelper.close();
								NotificationReceiver.cancelAllAlarm(activity, millis.get(position),
										descs.get(position), (int) id);
//								NotificationReceiver.cancelAllAlarm(activity,
//										id);
								ids.remove(position);
								data.remove(position);
								millis.remove(position);
								descs.remove(position);
								notifyDataSetChanged();
							}

						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		builder.create();
		builder.show();

	}
}
