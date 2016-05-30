package com.pdt.android_gis.set;

import java.io.UnsupportedEncodingException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pdt.android_gis.R;
import com.pdt.android_gis.AppManager;
import com.pdt.android_gis.map.MultyLocationActivity;
import com.pdt.android_gis.security.RestTemplate;
import com.pdt.android_gis.util.Constants;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class SetServer extends Activity {
	
	public Spinner upload_server_spinner;
	private Button upLoad;
	private boolean upLoadClickFlag = false;
	private Button downLoad;
	private boolean downLoadClickFlag = false;
//	private ColorPickerDialog dialog;
	Context context;
	private EditText ipaddress1;
	private EditText ipaddress2;
	private EditText ipaddress3;
	private EditText ipaddress4;
	//数据库游标
	private Cursor cursor;
	private final RestTemplate restTemplate = new RestTemplate();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		context = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setserver);
//		getWindow().setBackgroundDrawable(null);
		//服务器地址
		ipaddress1 = (EditText)findViewById(R.id.ipaddress1);
		ipaddress2 = (EditText)findViewById(R.id.ipaddress2);
		ipaddress3 = (EditText)findViewById(R.id.ipaddress3);
		ipaddress4 = (EditText)findViewById(R.id.ipaddress4);
		//波特率选择菜单
        upload_server_spinner = (Spinner)findViewById(R.id.spinner2);
		
//        ArrayAdapter<String> accountTypesAdapter = new ArrayAdapter<String>(this, R.array.UpLoad_Var, android.R.layout.simple_spinner_item); 
//        accountTypesAdapter.setDropDownViewResource(R.layout.drop_down_item);
        
		ArrayAdapter<CharSequence> adapter = 
			ArrayAdapter.createFromResource(this, R.array.UpLoad_Var, android.R.layout.simple_spinner_item);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		upload_server_spinner.setAdapter(adapter);		
		upload_server_spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		
		final SQLiteDatabase db = MultyLocationActivity.dbHelper.getWritableDatabase();
		//数据库游标
		cursor = Constants.database.query("coverdata", null, null, null, null, null, null, null);
		
		upLoad = (Button)findViewById(R.id.upload);
		upLoad.setOnClickListener(new Button.OnClickListener() {		
			public void onClick(View v) {
				
				upLoadClickFlag = true;
				//设置服务器地址
				StringBuffer IP = new StringBuffer();
	
				if(ipaddress1.getText()!=null && ipaddress2.getText()!=null && ipaddress3.getText()!=null && ipaddress4.getText()!=null){
					
					IP.append(ipaddress1.getText());
					IP.append(".");
					IP.append(ipaddress2.getText());
					IP.append(".");
					IP.append(ipaddress3.getText());
					IP.append(".");
					IP.append(ipaddress4.getText());
					Constants.ipaddress = IP.toString();
					
				}
				
				//颜色选择器
//				dialog = new ColorPickerDialog(context, getResources().getString(R.string.btn_color_picker),
//		                new ColorPickerDialog.OnColorChangedListener() {
//		             
//		            @Override
//		            public void colorChanged(int color) {
//
//		            }
//		        });
//		        dialog.show();			
			}
		});
		
		//手动上传数据到服务器
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					
					while(true){
						
						while(upLoadClickFlag && !Constants.uploadflag){
							
							if(cursor.moveToNext()){
								
								String baseid = cursor.getString(cursor.getColumnIndex("BaseId"));
								String domain = cursor.getString(cursor.getColumnIndex("DoMain"));
								double longitude = cursor.getDouble(cursor.getColumnIndex("Longitude"));
								double latitude = cursor.getDouble(cursor.getColumnIndex("Latitude"));
								double rss = cursor.getDouble(cursor.getColumnIndex("Rss"));
								int rssul = cursor.getInt(cursor.getColumnIndex("RssUI"));
								int rssdl = cursor.getInt(cursor.getColumnIndex("RssDI"));
								String rssdate = cursor.getString(cursor.getColumnIndex("RssDate"));
								restTemplate.httpPostDemo1(baseid,domain,longitude,latitude,rss,rssul,rssdl,rssdate);
								
							}
							upLoadClickFlag = false;
							
						}
						
					}					
					
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
		//自动上传数据到服务器
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					
					while(true){
						
                        while(Constants.uploadflag){
							
							if(cursor.moveToNext()){
								
								String baseid = cursor.getString(cursor.getColumnIndex("BaseId"));
								String domain = cursor.getString(cursor.getColumnIndex("DoMain"));
								double longitude = cursor.getDouble(cursor.getColumnIndex("Longitude"));
								double latitude = cursor.getDouble(cursor.getColumnIndex("Latitude"));
								double rss = cursor.getDouble(cursor.getColumnIndex("Rss"));
								int rssul = cursor.getInt(cursor.getColumnIndex("RssUI"));
								int rssdl = cursor.getInt(cursor.getColumnIndex("RssDI"));
								String rssdate = cursor.getString(cursor.getColumnIndex("RssDate"));
								restTemplate.httpPostDemo1(baseid,domain,longitude,latitude,rss,rssul,rssdl,rssdate);
								
							}
							
						}
						
					}						
					
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
		//从服务器下载数据到手机
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					
					while(true){
						
						while(downLoadClickFlag){
							
							String jsonData = restTemplate.httpPostDemo2();
//							System.out.println(jsonData);
							//解析服务器传送过来的JSON数组 基站信息
							JSONArray arr = new JSONArray(jsonData);  
							for (int i = 0; i < arr.length(); i++) {  
							    JSONObject temp = (JSONObject) arr.get(i);
							    String baseId = temp.getString("baseId");
							    String baseName = temp.getString("baseName");
							    String longitude = temp.getString("longtitude");
							    String latitude = temp.getString("latitude");
							    System.out.println(baseName);  
							    ContentValues cv = new ContentValues();
							    cv.put("BaseId", baseId);
							    cv.put("BaseName", baseName);
							    cv.put("Longitude", longitude);
							    cv.put("latitude", latitude);
							    db.insert("baseinfo", null, cv);
							}
							
							downLoadClickFlag = false;
							
						}
						
					}
					
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
		downLoad = (Button)findViewById(R.id.download);
		downLoad.setOnClickListener(new Button.OnClickListener() {		
			public void onClick(View v) {
				
				downLoadClickFlag = true;
				//设置服务器地址
				StringBuffer IP = new StringBuffer();
				if(ipaddress1.getText()!=null && ipaddress2.getText()!=null && ipaddress3.getText()!=null && ipaddress4.getText()!=null){
					
					IP.append(ipaddress1.getText());
					IP.append(".");
					IP.append(ipaddress2.getText());
					IP.append(".");
					IP.append(ipaddress3.getText());
					IP.append(".");
					IP.append(ipaddress4.getText());
					Constants.ipaddress = IP.toString();
					
				}			
				
			}
		});
		
		AppManager.getAppManager().addActivity(this);
	}
	
	/**
	 * 
	 * 服务器上传方式选择
	 *
	 */
  	public class MyOnItemSelectedListener implements OnItemSelectedListener {
  		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
  			
//  			if(null==mSerial)
//  				return;
//  			
//  			if(!mSerial.isConnected()) 
//  				return;
//  			 
//  			int baudRate=0;
  			String uploadway;
  			Toast.makeText(parent.getContext(), "上传方式已设为 " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
  			uploadway= parent.getItemAtPosition(position).toString();
  			
  			 switch (uploadway) {
             case "自动":
            	 
            	 Constants.uploadflag = true;
            	 
            	 break;
            	 
             case "手动":
            	 
            	 Constants.uploadflag = false;
            	 break;
             default:
            	 
            	 break;
            }   			 

  		}
  		public void onNothingSelected(AdapterView<?> parent) {
  			// Do nothing.    
  		}
  	}//MyOnItemSelectedListener

}
