package com.pdt.android_gis.set;

import java.util.List;

import com.pdt.android_gis.R;
import com.pdt.android_gis.R.id;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SetAdapter extends ArrayAdapter<Setting> {
	
	private int resourceId;
	
	public SetAdapter(Context context, int textViewResourceId,
			List<Setting> objects){
		
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		Setting setting = getItem(position);//获取当前项的Fruit实例
		View view;
		ViewHolder viewHolder;
		
		if(convertView==null){
			
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.settingImage = (ImageView) view.findViewById(R.id.setting_image);
			viewHolder.settingName = (TextView) view.findViewById(R.id.setting_name);
			view.setTag(viewHolder);
			
		}else{
			
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
			
		}
		
		viewHolder.settingImage.setImageResource(setting.getImageId());
		viewHolder.settingName.setText(setting.getName());
		return view;
		
	}
	
	private static class ViewHolder{
		
		ImageView settingImage;
		
		TextView settingName;
		
	}

}
