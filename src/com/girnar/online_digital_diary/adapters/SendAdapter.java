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
import com.girnar.online_digital_diary.ui.SendAccountInfoActivity;

/**
 * @author
 * 
 */
public class SendAdapter extends ArrayAdapter<Items> {
	private final Activity activity;
	private final ArrayList<Items> data;
	private HashMap<String, List<String>> childData;
	private static ArrayList<Integer> selected = new ArrayList<Integer>();

	/**
	 * 
	 * @param context
	 * @param data
	 */
	public SendAdapter(Activity context, ArrayList<Items> data,
			HashMap<String, List<String>> childData,boolean isNewSelected) {
		super(context, R.layout.send_adapter, data);
		this.activity = context;
		this.data = data;
		this.childData = childData;
		if(isNewSelected){
			selected=new ArrayList<Integer>();
		}

	}

	static class Holder {
		public TextView textView;
		public LinearLayout layout;
		public ImageButton button;
		public TextView accountNo;
		public TextView bankName;
		public TextView location;
		private CheckBox checkBox;
		
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = null;
		final Holder holder;

		if (convertView == null) {

			holder = new Holder();
			LayoutInflater inflater = activity.getLayoutInflater();
			view = inflater.inflate(R.layout.send_adapter, null);
			holder.textView = (TextView) view
					.findViewById(R.id.send_adapter_text);
			holder.accountNo = (TextView) view.findViewById(R.id.accountNumber);
			holder.bankName = (TextView) view.findViewById(R.id.bankName);
			holder.location = (TextView) view.findViewById(R.id.location);
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
						
						if (SendAccountInfoActivity.select_all_flag == 1) {
							// do nothing
						} else {
							SendAccountInfoActivity.setSendInfoIds(Integer
									.parseInt(data.get(data.indexOf(items))
											.getId()),true);
						}
					} else {

						System.out.println("ind is "
								+ data.get(data.indexOf(items)).getId());
						SendAccountInfoActivity.removeSendInfoIds(Integer
								.parseInt(data.get(data.indexOf(items)).getId()),true);
					}

					if (!cb.isChecked()) {
						if (SendAccountInfoActivity.select_all_flag == 1) {
							SendAccountInfoActivity.uncheckedSelectAllButton();
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
					
					System.out.println("position in button is " + pos);
					if (selected.contains(Integer.parseInt(data.get(pos)
							.getId()))) {

						selected.remove(selected.indexOf(Integer.parseInt(data
								.get(pos).getId())));
						SendAccountInfoActivity.opened_ids.remove(SendAccountInfoActivity.opened_ids.indexOf(Integer.parseInt(data
								.get(pos).getId())));
						data.get(pos).setVisible(View.GONE);
						holder.layout.setVisibility(View.GONE);
					} else {

						selected.add(Integer.parseInt(data.get(pos)
								.getId()));
						SendAccountInfoActivity.opened_ids.add(Integer.parseInt(data.get(pos)
								.getId()));

						data.get(position).setVisible(View.VISIBLE);
						holder.layout.setVisibility(View.VISIBLE);
						System.out.println("in onClick else part");
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
		holder1.accountNo.setText(childData
				.get(data.get(position).getName()).get(0));
		holder1.bankName.setText(childData.get(data.get(position).getName())
				.get(1));
		holder1.location.setText(childData.get(data.get(position).getName())
				.get(2));

		if (SendAccountInfoActivity.select_all_flag == 1) {

			data.get(position).setChecked(true);
			holder1.checkBox.setChecked(true);
			
		} else if (SendAccountInfoActivity.select_all_flag == 2) {

			if (position == data.size() - 1) {

				SendAccountInfoActivity.select_all_flag = 0;
			}
			data.get(position).setChecked(false);
			holder1.checkBox.setChecked(false);

		} else {

			holder1.checkBox.setChecked(data.get(position).isChecked());

		}
//		holder1.layout.setVisibility(data.get(position).getVisible());
		holder1.layout.setVisibility(View.GONE);
		
		if (selected.contains(Integer.parseInt(data.get(position)
				.getId()))) {

			
			data.get(position).setVisible(View.VISIBLE);
			holder1.layout.setVisibility(View.VISIBLE);
		} 


		return view;

	}

	public  LinearLayout layout;
}