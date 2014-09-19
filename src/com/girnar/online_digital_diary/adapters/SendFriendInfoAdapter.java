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

import com.girnar.online_digital_diary.beans.Items;
import com.girnar.online_digital_diary.ui.R;
import com.girnar.online_digital_diary.ui.SendFriendInformationActivity;

public class SendFriendInfoAdapter extends ArrayAdapter<Items> {
	private final Activity activity;
	private final ArrayList<Items> data;
	private HashMap<String, List<String>> childData;
	private static ArrayList<Integer> selected;

	/**
	 * 
	 * @param context
	 * @param data
	 */
	public SendFriendInfoAdapter(Activity context, ArrayList<Items> data,
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
		public LinearLayout layout1;
		public LinearLayout layout2;
		public LinearLayout layout3;
		public ImageButton button;
		public TextView mobileNo;
		public TextView email;
		public TextView birthday;
		public TextView anniversary;
		public TextView address;
		public TextView gender;
		private CheckBox checkBox;
		
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = null;
		final Holder holder;

		// Holder holder;
		if (convertView == null) {

			holder = new Holder();
			LayoutInflater inflater = activity.getLayoutInflater();
			view = inflater.inflate(R.layout.sendfriendinfoadapter, null);
			holder.textView = (TextView) view
					.findViewById(R.id.send_adapter_text);
			holder.mobileNo = (TextView) view.findViewById(R.id.mobileNumber);
			holder.email = (TextView) view.findViewById(R.id.email);
			holder.birthday = (TextView) view.findViewById(R.id.birthday);
			holder.anniversary = (TextView) view.findViewById(R.id.anniversary);
			holder.address = (TextView) view.findViewById(R.id.address);
			holder.gender = (TextView) view.findViewById(R.id.gender);
			
			holder.layout1 = (LinearLayout) view
					.findViewById(R.id.lowerLayout1_sendAdapter);
			holder.layout2 = (LinearLayout) view
					.findViewById(R.id.lowerLayout2_sendAdapter);
			holder.layout3 = (LinearLayout) view
					.findViewById(R.id.lowerLayout3_sendAdapter);
			
			holder.button = (ImageButton) view
					.findViewById(R.id.imageView_view_more);
			holder.mobileNo.setText(childData
					.get(data.get(position).getName()).get(0));
			holder.email.setText(childData.get(data.get(position).getName())
					.get(1));
			holder.birthday.setText(childData.get(data.get(position).getName())
					.get(2));
			holder.anniversary.setText(childData
					.get(data.get(position).getName()).get(3));
			holder.address.setText(childData.get(data.get(position).getName())
					.get(4));
			holder.gender.setText(childData.get(data.get(position).getName())
					.get(5));
			
			holder.checkBox = (CheckBox) view
					.findViewById(R.id.checkBoxSendAdapter);
			
			holder.checkBox.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 CheckBox cb = (CheckBox) v ; 
					 Items items=(Items)cb.getTag();
					 
					 if (cb.isChecked()) {
						 if (SendFriendInformationActivity.select_all_flag == 1) {
								// do nothing
							} else {
								SendFriendInformationActivity.setSendInfoIds(Integer
										.parseInt(data.get(data.indexOf(items))
												.getId()),true);
							}
						} else {
							SendFriendInformationActivity
									.removeSendInfoIds(Integer
											.parseInt(data.get(data.indexOf(items))
													.getId()),true);
						}
					 if (!cb.isChecked()) {
							if (SendFriendInformationActivity.select_all_flag == 1) {
								SendFriendInformationActivity.uncheckedSelectAllButton();
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

					Items items = (Items) v.getTag();
					int pos = data.indexOf(items);
					if (selected.contains(Integer.parseInt(data.get(pos)
							.getId()))) {

						selected.remove(selected.indexOf(Integer.parseInt(data
								.get(pos).getId())));
						SendFriendInformationActivity.opened_ids.remove(SendFriendInformationActivity.opened_ids.indexOf(Integer.parseInt(data
								.get(pos).getId())));
						data.get(pos).setVisible(View.GONE);
						holder.layout1.setVisibility(View.GONE);
						holder.layout2.setVisibility(View.GONE);
						holder.layout3.setVisibility(View.GONE);
						
					
					} else {

						selected.add(Integer.parseInt(data.get(pos)
								.getId()));
						SendFriendInformationActivity.opened_ids.add(Integer.parseInt(data.get(pos)
								.getId()));
						data.get(pos).setVisible(View.VISIBLE);
						holder.layout1.setVisibility(View.VISIBLE);
						holder.layout2.setVisibility(View.VISIBLE);
						holder.layout3.setVisibility(View.VISIBLE);
						

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

		if (SendFriendInformationActivity.select_all_flag == 1) {

			data.get(position).setChecked(true);
			holder1.checkBox.setChecked(true);
			
		} else if (SendFriendInformationActivity.select_all_flag == 2) {

			if (position == data.size() - 1) {

				SendFriendInformationActivity.select_all_flag = 0;
			}
			data.get(position).setChecked(false);
			holder1.checkBox.setChecked(false);
		} else {

			holder1.checkBox.setChecked(data.get(position).isChecked());
			
		}
		holder1.layout1.setVisibility(View.GONE);
		holder1.layout2.setVisibility(View.GONE);
		holder1.layout3.setVisibility(View.GONE);

		if (selected.contains(Integer.parseInt(data.get(position)
				.getId()))) {

			
			data.get(position).setVisible(View.VISIBLE);
			holder1.layout1.setVisibility(View.VISIBLE);
			holder1.layout2.setVisibility(View.VISIBLE);
			holder1.layout3.setVisibility(View.VISIBLE);
		} 


		return view;

	}

	public static LinearLayout layout;
}