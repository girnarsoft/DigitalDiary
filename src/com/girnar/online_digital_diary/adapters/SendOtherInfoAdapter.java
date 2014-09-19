package com.girnar.online_digital_diary.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.girnar.online_digital_diary.adapters.SendAdapter.Holder;
import com.girnar.online_digital_diary.beans.Items;
import com.girnar.online_digital_diary.ui.R;
import com.girnar.online_digital_diary.ui.SendAccountInfoActivity;
import com.girnar.online_digital_diary.ui.SendOtherInfoActivity;

public class SendOtherInfoAdapter extends ArrayAdapter<Items> {
	private final Activity activity;
	private final ArrayList<Items> data;
	private HashMap<String, List<String>> childData;
	private static  ArrayList<Integer> selected;

	/**
	 * 
	 * @param context
	 * @param data
	 */
	public SendOtherInfoAdapter(Activity context, ArrayList<Items> data,
			HashMap<String, List<String>> childData,boolean isNewPage) {
		super(context, R.layout.send_adapter, data);
		this.activity = context;
		this.data = data;
		this.childData = childData;

		if(isNewPage)
			selected = new ArrayList<Integer>();
	}

	static class Holder {
		public TextView textView;
		public LinearLayout layout;
		public ImageButton button;
		public TextView description;
		private CheckBox checkBox;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {View view = null;
	final Holder holder;

	if (convertView == null) {

		holder = new Holder();
		LayoutInflater inflater = activity.getLayoutInflater();
		view = inflater.inflate(R.layout.send_other_info_adapter, null);
		holder.textView = (TextView) view
				.findViewById(R.id.send_adapter_text);
		
		holder.description = (TextView) view.findViewById(R.id.description);
		holder.layout = (LinearLayout) view
				.findViewById(R.id.lowerLayout_sendAdapter);
		holder.button = (ImageButton) view
				.findViewById(R.id.imageView_view_more);
		
		
		holder.checkBox = (CheckBox) view
				.findViewById(R.id.checkBoxSendAdapter);
	

		holder.checkBox.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CheckBox cb = (CheckBox) v;
				Items items = (Items) cb.getTag();
				int pos = data.indexOf(items);
				System.out.println("postion in onCLick is " + pos);
				if (cb.isChecked()) {
					// for dashed line
					
					if (SendOtherInfoActivity.select_all_flag == 1) {
						// do nothing
					} else {
						SendOtherInfoActivity.setSendInfoIds(Integer
								.parseInt(data.get(data.indexOf(items))
										.getId()),true);
					}
				} else {

					System.out.println("ind is "
							+ data.get(data.indexOf(items)).getId());
					SendOtherInfoActivity.removeSendInfoIds(Integer
							.parseInt(data.get(data.indexOf(items)).getId()),true);
				}

				if (!cb.isChecked()) {
					if (SendOtherInfoActivity.select_all_flag == 1) {
						SendOtherInfoActivity.uncheckedSelectAllButton();
						// SendAccountInfoActivity.select_all_flag=0;
					}
				}

				items.setChecked(cb.isChecked());

			}
		});

		holder.button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				System.out.println("position in button is " + position);
				Items items = (Items) v.getTag();
				int pos = data.indexOf(items);
				if (selected.contains(Integer.parseInt(data.get(pos)
						.getId()))) {

					selected.remove(selected.indexOf(Integer.parseInt(data
							.get(pos).getId())));
					SendOtherInfoActivity.opened_ids.remove(SendOtherInfoActivity.opened_ids.indexOf(Integer.parseInt(data
							.get(pos).getId())));
					data.get(pos).setVisible(View.GONE);
					holder.layout.setVisibility(View.GONE);
				} else {

					selected.add(Integer.parseInt(data.get(pos)
							.getId()));
					SendOtherInfoActivity.opened_ids.add(Integer.parseInt(data.get(pos)
							.getId()));

					System.out.println("in onClick else par "+data.get(pos).getName());
					data.get(pos).setVisible(View.VISIBLE);
					holder.layout.setVisibility(View.VISIBLE);
					
				}

			}
		});

		view.setTag(holder);
		holder.checkBox.setTag(data.get(position));
		holder.button.setTag(data.get(position));
		
	} else {
		view = convertView;
		((Holder) view.getTag()).checkBox.setTag(data.get(position));
		((Holder) view.getTag()).button.setTag(data.get(position));
	}

	final Holder holder1 = (Holder) view.getTag();

	holder1.textView.setText(data.get(position).getName());
	holder1.description.setText(childData.get(data.get(position).getName())
			.get(0));
	if (SendOtherInfoActivity.select_all_flag == 1) {

		data.get(position).setChecked(true);
		holder1.checkBox.setChecked(true);
		
	} else if (SendOtherInfoActivity.select_all_flag == 2) {

		if (position == data.size() - 1) {

			SendOtherInfoActivity.select_all_flag = 0;
		}
		data.get(position).setChecked(false);
		holder1.checkBox.setChecked(false);

	} else {

		holder1.checkBox.setChecked(data.get(position).isChecked());

	}
	System.out.println("position is "+position);
	System.out.println(data.get(position).getVisible());
//	holder1.layout.setVisibility(data.get(position).getVisible());
	holder1.layout.setVisibility(View.GONE);
	
	if (selected.contains(Integer.parseInt(data.get(position)
			.getId()))) {
		System.out.println("in mamala...");
		
		data.get(position).setVisible(View.VISIBLE);
		holder1.layout.setVisibility(View.VISIBLE);
	} 


	return view;
}

	public static LinearLayout layout;
}