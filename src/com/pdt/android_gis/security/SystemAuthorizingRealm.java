package com.pdt.android_gis.security;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SystemAuthorizingRealm extends Activity {
	
//	  private EditText textname = null;
//	  private EditText textpassword = null;
//	  private Button button = null;
//	  @Override
//	  protected void onCreate(Bundle savedInstanceState) {
//	    super.onCreate(savedInstanceState);
//	    setContentView(R.layout.activity_main);
//	      
//	    textname = (EditText)findViewById(R.id.name);
//	    textpassword = (EditText)findViewById(R.id.password);
//	    button = (Button)findViewById(R.id.button);
//	      
//	    button.setOnClickListener(new mybuttonlistener());
//	      
//	  }
//	  class mybuttonlistener implements OnClickListener{
//	    boolean result=false;
//	    String name;
//	    String password;
//	    public void onClick(View v) {
//	      try {        
//	        name = textname.getText().toString();
//	        name = new String(name.getBytes("ISO8859-1"), "UTF-8");
//	        password = textpassword.getText().toString();
//	        password = new String(password.getBytes("ISO8859-1"), "UTF-8");
//	      } catch (UnsupportedEncodingException e1) {
//	        // TODO Auto-generated catch block
//	        e1.printStackTrace();
//	      }
//	      try {
//	        result = NewsService.save(name,password);
//	      } catch (Exception e) {
//	        // TODO Auto-generated catch block
//	        e.printStackTrace();
//	      }
//	      if(result){
//	        Toast.makeText(MainActivity.this, R.string.ok, Toast.LENGTH_SHORT).show();
//	      }else{
//	        Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
//	      }
//	    }
//	  }

}
