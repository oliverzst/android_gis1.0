package com.pdt.android_gis;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;

import com.amap.api.location.LocationManagerProxy;
import com.pdt.android_gis.R;
import com.pdt.android_gis.database.MyDatabaseHelper;
import com.pdt.android_gis.map.MultyLocationActivity;
import com.pdt.android_gis.security.NewsService;
import com.pdt.android_gis.security.RestTemplate;
import com.pdt.android_gis.util.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
/**
 * 登录首页
 * */
public class MainActivity extends Activity implements OnClickListener {

	private EditText textname = null;
	private EditText textpassword = null;
	private Button login = null;
	private long exitTime = 0;
	public static boolean loginFlag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		if (savedInstanceState == null) {
//	            getSupportFragmentManager().beginTransaction()
//	                    .add(R.id.container, new PlaceholderFragment())
//	                    .commit();
//	    }
		getWindow().setBackgroundDrawable(null);
		initView();	
		AppManager.getAppManager().addActivity(this);
		
	}
/**
 * 初始化控件
 * */
	private void initView() {

		textname = (EditText)findViewById(R.id.login);
	    textpassword = (EditText)findViewById(R.id.password1);
		
		login = (Button) findViewById(R.id.login_button);
	
		login.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View view) {
		
	    final RestTemplate restTemplate = new RestTemplate();
	    
		switch (view.getId()) {
		//后续加入登录密码验证功能
		case R.id.login_button:
			//通过服务器进行登录验证(测试)
//			if(textname.getText().toString().equals("admin") && textpassword.getText().toString().equals("admin")){
//				
//				result = true;
//				loginFlag = true;
//				Toast.makeText(MainActivity.this, R.string.ok, Toast.LENGTH_SHORT).show();
//				Intent multyIntent=new Intent(MainActivity.this,MultyLocationActivity.class);
//				startActivity(multyIntent);
//				
//			}else{
//				
//				Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
//				
//			}
			
			final Handler handle = new Handler() {
				 
		        @Override
		        public void handleMessage(Message msg) {
		            super.handleMessage(msg);
		            Intent multyIntent=new Intent(MainActivity.this,MultyLocationActivity.class);
					startActivity(multyIntent);
					loginFlag = true;
					Toast.makeText(MainActivity.this, R.string.ok, Toast.LENGTH_SHORT).show();
		        }
		        
		    };
		    
		    final Handler handle1 = new Handler() {
				 
		        @Override
		        public void handleMessage(Message msg) {
		            super.handleMessage(msg);
		            Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
		        }
		        
		    };
			
            new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Constants.result = restTemplate.httpPostDemo(textname.getText().toString(), textpassword.getText().toString());
						if(Constants.result.equals("true")){
							Message msg = new Message();
				            handle.sendMessage(msg);
						}else{
							Message msg = new Message();
							handle1.sendMessage(msg);
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
			
//			System.out.println(1);
//			try {        
//		        name = textname.getText().toString();
//		        name = new String(name.getBytes("ISO8859-1"), "UTF-8");
//		        password = textpassword.getText().toString();
//		        password = new String(password.getBytes("ISO8859-1"), "UTF-8");
//		      } catch (UnsupportedEncodingException e1) {
//		        // TODO Auto-generated catch block
//		        e1.printStackTrace();
//		      }
//		      try {
//		        result = NewsService.save(name,password);
//		      } catch (Exception e) {
//		        // TODO Auto-generated catch block
//		        e.printStackTrace();
//		      }
//		      if(result){
//		        Toast.makeText(MainActivity.this, R.string.ok, Toast.LENGTH_SHORT).show();
//		      }else{
//		        Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
//		      }

			break;

		}

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	            
//	            dbHelper.close();
				AppManager.getAppManager().AppExit(this);
	        }
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		
		super.onResume();
		
	}

}