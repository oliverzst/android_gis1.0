package com.pdt.android_gis.set;

import java.util.ArrayList;
import java.util.List;

import com.amap.api.maps.offlinemap.OfflineInitBean;
import com.pdt.android_gis.R;
import com.pdt.android_gis.R.drawable;
import com.pdt.android_gis.R.id;
import com.pdt.android_gis.R.layout;
import com.pdt.android_gis.AppManager;
import com.pdt.android_gis.map.MultyLocationActivity;
import com.pdt.android_gis.offlinemap.OfflineMapActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SetActivity extends Activity {
	
	private List<Setting> settingList = new ArrayList<Setting>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);// 涓嶆樉绀虹▼搴忕殑鏍囬鏍�
		setContentView(R.layout.activity_setting);
//		getWindow().setBackgroundDrawable(null);
		initsettings();
		SetAdapter adapter = new SetAdapter(SetActivity.this,
				R.layout.setting_item, settingList);
		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id){
				Setting setting = settingList.get(position);
				Intent multyIntent;
				switch (setting.getName()) {
				case "1":
					multyIntent=new Intent(SetActivity.this, SetView.class);
					startActivity(multyIntent);				
					break;
					
				case "2":
					multyIntent=new Intent(SetActivity.this, SetServer.class);
					startActivity(multyIntent);
					break;
					
				case "3":
					multyIntent=new Intent(SetActivity.this, SetCom.class);
					startActivity(multyIntent);
					break;
					
				case "4":
					multyIntent=new Intent(SetActivity.this, OfflineMapActivity.class);
					startActivity(multyIntent);
					break;

				default:
					break;
				}
//				Toast.makeText(setActivity.this, fruit.getName(), Toast.LENGTH_SHORT).show();
			}			
		});
		
		AppManager.getAppManager().addActivity(this);
		
	}

	private void initsettings() {
		// TODO Auto-generated method stub
		Setting view = new Setting("1", R.drawable.view);
		settingList.add(view);
		Setting server = new Setting("2", R.drawable.server);
		settingList.add(server);
		Setting serial = new Setting("3", R.drawable.serial);
		settingList.add(serial);
		Setting offlinemap = new Setting("4", R.drawable.offlinemap);
		settingList.add(offlinemap);
		
	}
	
	
}
