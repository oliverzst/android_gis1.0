package com.pdt.android_gis.set;

import com.pdt.android_gis.R;
import com.pdt.android_gis.AppManager;
import com.pdt.android_gis.map.MultyLocationActivity;
import com.pdt.android_gis.set.SetServer.MyOnItemSelectedListener;
import com.pdt.android_gis.ui.ColorPickerDialog;
import com.pdt.android_gis.util.Constants;
import com.pdt.android_gis.util.MakerColor;
import com.pdt.android_gis.util.RssMapColor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

public class SetView extends Activity{
	
	Context context;
	
	TextView textview;
	
	private Spinner color60 = null;
	private Spinner color70 = null;
	private Spinner color80 = null;
	private Spinner color90 = null;
	private Spinner color100 = null;
	private Spinner color110 = null;
	private Spinner color120 = null;
	private Spinner color130 = null;
	
	private ColorPickerDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		context = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setview);
//		getWindow().setBackgroundDrawable(null);
		initView();
		
		AppManager.getAppManager().addActivity(this);
	}
	
	/**
	 * 初始化控件
	 * */
    private void initView() {
			
      color60 = (Spinner) findViewById(R.id.color60spinner);
      ArrayAdapter<CharSequence> adapter = 
  			ArrayAdapter.createFromResource(this, R.array.Color_Var, R.layout.spinner_item);
  		
  	  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
  		
  	  color60.setAdapter(adapter);	 
      color60.setSelection(0);
  	  color60.setOnItemSelectedListener(new MyOnItemSelectedListener());
      
      color70 = (Spinner) findViewById(R.id.color70button);
      color70.setAdapter(adapter);	 
  	  color70.setSelection(1);
  	  color70.setOnItemSelectedListener(new MyOnItemSelectedListener1());

      color80 = (Spinner) findViewById(R.id.color80button);
      color80.setAdapter(adapter);	 
      color80.setSelection(2);
  	  color80.setOnItemSelectedListener(new MyOnItemSelectedListener2());

      color90 = (Spinner) findViewById(R.id.color90button);
      color90.setAdapter(adapter);	 
      color90.setSelection(3);
  	  color90.setOnItemSelectedListener(new MyOnItemSelectedListener3());

      color100 = (Spinner) findViewById(R.id.color100button);
      color100.setAdapter(adapter);	 
      color100.setSelection(4);
  	  color100.setOnItemSelectedListener(new MyOnItemSelectedListener4());

      color110 = (Spinner) findViewById(R.id.color110button);
      color110.setAdapter(adapter);	 
      color110.setSelection(5);
  	  color110.setOnItemSelectedListener(new MyOnItemSelectedListener5());

      color120 = (Spinner) findViewById(R.id.color120button);
      color120.setAdapter(adapter);	 
      color120.setSelection(6);
  	  color120.setOnItemSelectedListener(new MyOnItemSelectedListener6());

      color130 = (Spinner) findViewById(R.id.color130button);
      color130.setAdapter(adapter);	 
      color130.setSelection(7);
  	  color130.setOnItemSelectedListener(new MyOnItemSelectedListener7());

			
    }
    
    /**
	 * 
	 * 颜色选择
	 *
	 */
  	public class MyOnItemSelectedListener implements OnItemSelectedListener{
  		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

  			String newColor;
  			newColor= parent.getItemAtPosition(position).toString();
  			 switch (newColor) {
  			case "绿色":
           	     color60.setBackgroundColor(MakerColor.GREEN);
           	     MakerColor.COLOR60 = 120.0f;
           	     MakerColor.color60 = MakerColor.GREEN;
           	     Constants.color60Panel.setBackgroundColor(MakerColor.GREEN);
         		break;	
             case "红色":
            	 color60.setBackgroundColor(MakerColor.RED);
            	 MakerColor.COLOR60 = 0.0f;          
            	 MakerColor.color60 = MakerColor.RED;
	             Constants.color60Panel.setBackgroundColor(MakerColor.RED);
            	 break;
             case "橙色":
            	 color60.setBackgroundColor(MakerColor.ORANGE);
            	 MakerColor.COLOR60 = 30.0f;
            	 MakerColor.color60 = MakerColor.ORANGE;
	             Constants.color60Panel.setBackgroundColor(MakerColor.ORANGE);
            	 break;
             case "黄色":
            	 color60.setBackgroundColor(MakerColor.YELLOW);
            	 MakerColor.COLOR60 = 60.0f;
            	 MakerColor.color60 = MakerColor.YELLOW;
	             Constants.color60Panel.setBackgroundColor(MakerColor.YELLOW);
          		break; 
             case "青色":
            	 color60.setBackgroundColor(MakerColor.CYAN);
            	 MakerColor.COLOR60 = 180.0f;
            	 MakerColor.color60 = MakerColor.CYAN;
	             Constants.color60Panel.setBackgroundColor(MakerColor.CYAN);
            	 break;
             case "蓝色":
            	 color60.setBackgroundColor(MakerColor.BLUE);
            	 MakerColor.COLOR60 = 240.0f;
            	 MakerColor.color60 = MakerColor.BLUE;
	             Constants.color60Panel.setBackgroundColor(MakerColor.BLUE);
            	 break;
             case "紫色":
            	 color60.setBackgroundColor(MakerColor.VIOLET);
            	 MakerColor.COLOR60 = 270.0f;
            	 MakerColor.color60 = MakerColor.VIOLET;
	             Constants.color60Panel.setBackgroundColor(MakerColor.VIOLET);
            	 break;
             case "天蓝":
            	 color60.setBackgroundColor(MakerColor.AZURE);
            	 MakerColor.COLOR60 = 210.0f;
            	 MakerColor.color60 = MakerColor.AZURE;
	             Constants.color60Panel.setBackgroundColor(MakerColor.AZURE);
            	 break;
             case "品红":
            	 color60.setBackgroundColor(MakerColor.MAGENTA);
            	 MakerColor.COLOR60 = 300.0f;
            	 MakerColor.color60 = MakerColor.MAGENTA;
	             Constants.color60Panel.setBackgroundColor(MakerColor.MAGENTA);
            	 break;
             case "玫瑰":
            	 color60.setBackgroundColor(MakerColor.ROSE);
            	 MakerColor.COLOR60 = 330.0f;
            	 MakerColor.color60 = MakerColor.ROSE;
	             Constants.color60Panel.setBackgroundColor(MakerColor.ROSE);
            	 break;
             default:
            	 break;
            }
  		}
  		public void onNothingSelected(AdapterView<?> parent) {
  			// Do nothing.    
  		}
  	}//MyOnItemSelectedListener
  	
  	public class MyOnItemSelectedListener1 implements OnItemSelectedListener{
  		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

  			String newColor;
		
//  			Toast.makeText(parent.getContext(), "newBaudRate is-" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
  			newColor= parent.getItemAtPosition(position).toString();
  			 switch (newColor) {
  			case "绿色":
          	     color70.setBackgroundColor(MakerColor.GREEN);
          	     MakerColor.COLOR70 = 120.0f;
          	     MakerColor.color70 = MakerColor.GREEN;
	             Constants.color70Panel.setBackgroundColor(MakerColor.GREEN);
        		break;	
            case "红色":
           	     color70.setBackgroundColor(MakerColor.RED);
           	     MakerColor.COLOR70 = 0.0f; 
           	     MakerColor.color70 = MakerColor.RED;
	             Constants.color70Panel.setBackgroundColor(MakerColor.RED);
           	 break;
            case "橙色":
           	     color70.setBackgroundColor(MakerColor.ORANGE);
           	     MakerColor.COLOR70 = 30.0f;
           	     MakerColor.color70 = MakerColor.ORANGE;
	             Constants.color70Panel.setBackgroundColor(MakerColor.ORANGE);
           	 break;
            case "黄色":
           	     color70.setBackgroundColor(MakerColor.YELLOW);
           	     MakerColor.COLOR70 = 60.0f;
           	     MakerColor.color70 = MakerColor.YELLOW;
	             Constants.color70Panel.setBackgroundColor(MakerColor.YELLOW);
         		break; 
            case "青色":
           	     color70.setBackgroundColor(MakerColor.CYAN);
           	     MakerColor.COLOR70 = 180.0f;
           	     MakerColor.color70 = MakerColor.CYAN;
	             Constants.color70Panel.setBackgroundColor(MakerColor.CYAN);
           	 break;
            case "蓝色":
           	     color70.setBackgroundColor(MakerColor.BLUE);
           	     MakerColor.COLOR70 = 240.0f;
           	     MakerColor.color70 = MakerColor.BLUE;
	             Constants.color70Panel.setBackgroundColor(MakerColor.BLUE);
           	 break;
            case "紫色":
           	     color70.setBackgroundColor(MakerColor.VIOLET);
           	     MakerColor.COLOR70 = 270.0f;
           	     MakerColor.color70 = MakerColor.VIOLET;
	             Constants.color70Panel.setBackgroundColor(MakerColor.VIOLET);
           	 break;
            case "天蓝":
           	     color70.setBackgroundColor(MakerColor.AZURE);
           	     MakerColor.COLOR70 = 210.0f;
           	     MakerColor.color70 = MakerColor.AZURE;
	             Constants.color70Panel.setBackgroundColor(MakerColor.AZURE);
           	 break;
            case "品红":
           	     color70.setBackgroundColor(MakerColor.MAGENTA);
           	     MakerColor.COLOR70 = 300.0f;
           	     MakerColor.color70 = MakerColor.MAGENTA;
	             Constants.color70Panel.setBackgroundColor(MakerColor.MAGENTA);
           	 break;
            case "玫瑰":
           	     color70.setBackgroundColor(MakerColor.ROSE);
           	     MakerColor.COLOR70 = 330.0f;
           	     MakerColor.color70 = MakerColor.ROSE;
	             Constants.color70Panel.setBackgroundColor(MakerColor.ROSE);
           	 break;
             default:
            	 break;
            } 
  		}
  		public void onNothingSelected(AdapterView<?> parent) {
  			// Do nothing.    
  		}
  	}
  	
  	public class MyOnItemSelectedListener2 implements OnItemSelectedListener{
  		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

  			String newColor;
		
//  			Toast.makeText(parent.getContext(), "newBaudRate is-" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
  			newColor= parent.getItemAtPosition(position).toString();
  			 switch (newColor) {
  			case "绿色":
          	     color80.setBackgroundColor(MakerColor.GREEN);
          	     MakerColor.COLOR80 = 120.0f;
          	     MakerColor.color80 = MakerColor.GREEN;
	             Constants.color80Panel.setBackgroundColor(MakerColor.GREEN);
        		break;	
            case "红色":
           	 color80.setBackgroundColor(MakerColor.RED);
           	 MakerColor.COLOR80 = 0.0f;      
           	MakerColor.color80 = MakerColor.RED;
	             Constants.color80Panel.setBackgroundColor(MakerColor.RED);
           	 break;
            case "橙色":
           	 color80.setBackgroundColor(MakerColor.ORANGE);
           	 MakerColor.COLOR80 = 30.0f;
           	MakerColor.color80 = MakerColor.ORANGE;
	             Constants.color80Panel.setBackgroundColor(MakerColor.ORANGE);
           	 break;
            case "黄色":
           	 color80.setBackgroundColor(MakerColor.YELLOW);
           	 MakerColor.COLOR80 = 60.0f;
           	MakerColor.color80 = MakerColor.YELLOW;
	             Constants.color80Panel.setBackgroundColor(MakerColor.YELLOW);
         		break; 
            case "青色":
           	 color80.setBackgroundColor(MakerColor.CYAN);
           	 MakerColor.COLOR80 = 180.0f;
           	MakerColor.color80 = MakerColor.CYAN;
	             Constants.color80Panel.setBackgroundColor(MakerColor.CYAN);
           	 break;
            case "蓝色":
           	 color80.setBackgroundColor(MakerColor.BLUE);
           	 MakerColor.COLOR80 = 240.0f;
           	MakerColor.color80 = MakerColor.BLUE;
	             Constants.color80Panel.setBackgroundColor(MakerColor.BLUE);
           	 break;
            case "紫色":
           	 color80.setBackgroundColor(MakerColor.VIOLET);
           	 MakerColor.COLOR80 = 270.0f;
           	MakerColor.color80 = MakerColor.VIOLET;
	             Constants.color80Panel.setBackgroundColor(MakerColor.VIOLET);
           	 break;
            case "天蓝":
           	 color80.setBackgroundColor(MakerColor.AZURE);
           	 MakerColor.COLOR80 = 210.0f;
           	MakerColor.color80 = MakerColor.AZURE;
	             Constants.color80Panel.setBackgroundColor(MakerColor.AZURE);
           	 break;
            case "品红":
           	 color80.setBackgroundColor(MakerColor.MAGENTA);
           	 MakerColor.COLOR80 = 300.0f;
           	MakerColor.color80 = MakerColor.MAGENTA;
	             Constants.color80Panel.setBackgroundColor(MakerColor.MAGENTA);
           	 break;
            case "玫瑰":
           	 color80.setBackgroundColor(MakerColor.ROSE);
           	 MakerColor.COLOR80 = 330.0f;
           	MakerColor.color80 = MakerColor.ROSE;
	             Constants.color80Panel.setBackgroundColor(MakerColor.ROSE);
           	 break;
             default:
            	 break;
            }  
  		}
  		public void onNothingSelected(AdapterView<?> parent) {
  			// Do nothing.    
  		}
  	}
  	
  	public class MyOnItemSelectedListener3 implements OnItemSelectedListener{
  		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

  			String newColor;
		
//  			Toast.makeText(parent.getContext(), "newBaudRate is-" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
  			newColor= parent.getItemAtPosition(position).toString();
  			 switch (newColor) {
  			case "绿色":
          	     color90.setBackgroundColor(MakerColor.GREEN);
          	     MakerColor.COLOR90 = 120.0f;
          	   MakerColor.color90 = MakerColor.GREEN;
	             Constants.color90Panel.setBackgroundColor(MakerColor.GREEN);
        		break;	
            case "红色":
           	 color90.setBackgroundColor(MakerColor.RED);
           	 MakerColor.COLOR90 = 0.0f;   
           	MakerColor.color90 = MakerColor.RED;
	             Constants.color90Panel.setBackgroundColor(MakerColor.RED);
           	 break;
            case "橙色":
           	 color90.setBackgroundColor(MakerColor.ORANGE);
           	 MakerColor.COLOR90 = 30.0f;
           	MakerColor.color90 = MakerColor.ORANGE;
	             Constants.color90Panel.setBackgroundColor(MakerColor.ORANGE);
           	 break;
            case "黄色":
           	 color90.setBackgroundColor(MakerColor.YELLOW);
           	 MakerColor.COLOR90 = 60.0f;
           	MakerColor.color90 = MakerColor.YELLOW;
	             Constants.color90Panel.setBackgroundColor(MakerColor.YELLOW);
         		break; 
            case "青色":
           	 color90.setBackgroundColor(MakerColor.CYAN);
           	 MakerColor.COLOR90 = 180.0f;
           	MakerColor.color90 = MakerColor.CYAN;
	             Constants.color90Panel.setBackgroundColor(MakerColor.CYAN);
           	 break;
            case "蓝色":
           	 color90.setBackgroundColor(MakerColor.BLUE);
           	 MakerColor.COLOR90 = 240.0f;
           	MakerColor.color90 = MakerColor.BLUE;
	             Constants.color90Panel.setBackgroundColor(MakerColor.BLUE);
           	 break;
            case "紫色":
           	 color90.setBackgroundColor(MakerColor.VIOLET);
           	 MakerColor.COLOR90 = 270.0f;
           	MakerColor.color90 = MakerColor.VIOLET;
	             Constants.color90Panel.setBackgroundColor(MakerColor.VIOLET);
           	 break;
            case "天蓝":
           	 color90.setBackgroundColor(MakerColor.AZURE);
           	 MakerColor.COLOR90 = 210.0f;
           	MakerColor.color90 = MakerColor.AZURE;
	             Constants.color90Panel.setBackgroundColor(MakerColor.AZURE);
           	 break;
            case "品红":
           	 color90.setBackgroundColor(MakerColor.MAGENTA);
           	 MakerColor.COLOR90 = 300.0f;
           	MakerColor.color90 = MakerColor.MAGENTA;
	             Constants.color90Panel.setBackgroundColor(MakerColor.MAGENTA);
           	 break;
            case "玫瑰":
           	 color90.setBackgroundColor(MakerColor.ROSE);
           	 MakerColor.COLOR90 = 330.0f;
           	MakerColor.color90 = MakerColor.ROSE;
	             Constants.color90Panel.setBackgroundColor(MakerColor.ROSE);
           	 break;
             default:
            	 break;
            }
  		}
  		public void onNothingSelected(AdapterView<?> parent) {
  			// Do nothing.    
  		}
  	}
  	
  	public class MyOnItemSelectedListener4 implements OnItemSelectedListener{
  		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

  			String newColor;
		
//  			Toast.makeText(parent.getContext(), "newBaudRate is-" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
  			newColor= parent.getItemAtPosition(position).toString();
  			 switch (newColor) {
  			case "绿色":
          	     color100.setBackgroundColor(MakerColor.GREEN);
          	     MakerColor.COLOR100 = 120.0f;
          	   MakerColor.color100 = MakerColor.GREEN;
	             Constants.color100Panel.setBackgroundColor(MakerColor.GREEN);
        		break;	
            case "红色":
           	 color100.setBackgroundColor(MakerColor.RED);
           	 MakerColor.COLOR100 = 0.0f; 
           	MakerColor.color100 = MakerColor.RED;
	             Constants.color100Panel.setBackgroundColor(MakerColor.RED);
           	 break;
            case "橙色":
           	 color100.setBackgroundColor(MakerColor.ORANGE);
           	 MakerColor.COLOR100 = 30.0f;
           	MakerColor.color100 = MakerColor.ORANGE;
	             Constants.color100Panel.setBackgroundColor(MakerColor.ORANGE);
           	 break;
            case "黄色":
           	 color100.setBackgroundColor(MakerColor.YELLOW);
           	 MakerColor.COLOR100 = 60.0f;
           	MakerColor.color100 = MakerColor.YELLOW;
	             Constants.color100Panel.setBackgroundColor(MakerColor.YELLOW);
         		break; 
            case "青色":
           	 color100.setBackgroundColor(MakerColor.CYAN);
           	 MakerColor.COLOR100 = 180.0f;
           	MakerColor.color100 = MakerColor.CYAN;
	             Constants.color100Panel.setBackgroundColor(MakerColor.CYAN);
           	 break;
            case "蓝色":
           	 color100.setBackgroundColor(MakerColor.BLUE);
           	 MakerColor.COLOR100 = 240.0f;
           	MakerColor.color100 = MakerColor.BLUE;
	             Constants.color100Panel.setBackgroundColor(MakerColor.BLUE);
           	 break;
            case "紫色":
           	 color100.setBackgroundColor(MakerColor.VIOLET);
           	 MakerColor.COLOR100 = 270.0f;
           	MakerColor.color100 = MakerColor.VIOLET;
	             Constants.color100Panel.setBackgroundColor(MakerColor.VIOLET);
           	 break;
            case "天蓝":
           	 color100.setBackgroundColor(MakerColor.AZURE);
           	 MakerColor.COLOR100 = 210.0f;
           	MakerColor.color100 = MakerColor.AZURE;
	             Constants.color100Panel.setBackgroundColor(MakerColor.AZURE);
           	 break;
            case "品红":
           	 color100.setBackgroundColor(MakerColor.MAGENTA);
           	 MakerColor.COLOR100 = 300.0f;
           	MakerColor.color100 = MakerColor.MAGENTA;
	             Constants.color100Panel.setBackgroundColor(MakerColor.MAGENTA);
           	 break;
            case "玫瑰":
           	 color100.setBackgroundColor(MakerColor.ROSE);
           	 MakerColor.COLOR100 = 330.0f;
           	MakerColor.color100 = MakerColor.ROSE;
	             Constants.color100Panel.setBackgroundColor(MakerColor.ROSE);
           	 break;
             default:
            	 break;
            }
  		}
  		public void onNothingSelected(AdapterView<?> parent) {
  			// Do nothing.    
  		}
  	}
  	
  	public class MyOnItemSelectedListener5 implements OnItemSelectedListener{
  		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

  			String newColor;
		
//  			Toast.makeText(parent.getContext(), "newBaudRate is-" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
  			newColor= parent.getItemAtPosition(position).toString();
  			 switch (newColor) {
  			case "绿色":
          	     color110.setBackgroundColor(MakerColor.GREEN);
          	     MakerColor.COLOR110 = 120.0f;
          	   MakerColor.color110 = MakerColor.GREEN;
	             Constants.color110Panel.setBackgroundColor(MakerColor.GREEN);
        		break;	
            case "红色":
           	 color110.setBackgroundColor(MakerColor.RED);
           	 MakerColor.COLOR110 = 0.0f;  
           	MakerColor.color110 = MakerColor.RED;
	             Constants.color110Panel.setBackgroundColor(MakerColor.RED);
           	 break;
            case "橙色":
           	 color110.setBackgroundColor(MakerColor.ORANGE);
           	 MakerColor.COLOR110 = 30.0f;
           	MakerColor.color110 = MakerColor.ORANGE;
	             Constants.color110Panel.setBackgroundColor(MakerColor.ORANGE);
           	 break;
            case "黄色":
           	 color110.setBackgroundColor(MakerColor.YELLOW);
           	 MakerColor.COLOR110 = 60.0f;
           	MakerColor.color110 = MakerColor.YELLOW;
	             Constants.color110Panel.setBackgroundColor(MakerColor.YELLOW);
         		break; 
            case "青色":
           	 color110.setBackgroundColor(MakerColor.CYAN);
           	 MakerColor.COLOR110 = 180.0f;
           	MakerColor.color110 = MakerColor.CYAN;
	             Constants.color110Panel.setBackgroundColor(MakerColor.CYAN);
           	 break;
            case "蓝色":
           	 color110.setBackgroundColor(MakerColor.BLUE);
           	 MakerColor.COLOR110 = 240.0f;
           	MakerColor.color110 = MakerColor.BLUE;
	             Constants.color110Panel.setBackgroundColor(MakerColor.BLUE);
           	 break;
            case "紫色":
           	 color110.setBackgroundColor(MakerColor.VIOLET);
           	 MakerColor.COLOR110 = 270.0f;
           	MakerColor.color110 = MakerColor.VIOLET;
	             Constants.color110Panel.setBackgroundColor(MakerColor.VIOLET);
           	 break;
            case "天蓝":
           	 color110.setBackgroundColor(MakerColor.AZURE);
           	 MakerColor.COLOR110 = 210.0f;
           	MakerColor.color110 = MakerColor.AZURE;
	             Constants.color110Panel.setBackgroundColor(MakerColor.AZURE);
           	 break;
            case "品红":
           	 color110.setBackgroundColor(MakerColor.MAGENTA);
           	 MakerColor.COLOR110 = 300.0f;
           	MakerColor.color110 = MakerColor.MAGENTA;
	             Constants.color110Panel.setBackgroundColor(MakerColor.MAGENTA);
           	 break;
            case "玫瑰":
           	 color110.setBackgroundColor(MakerColor.ROSE);
           	 MakerColor.COLOR110 = 330.0f;
           	MakerColor.color110 = MakerColor.ROSE;
	             Constants.color110Panel.setBackgroundColor(MakerColor.ROSE);
           	 break;
             default:
            	 break;
            }
  		}
  		public void onNothingSelected(AdapterView<?> parent) {
  			// Do nothing.    
  		}
  	}
  	
  	public class MyOnItemSelectedListener6 implements OnItemSelectedListener{
  		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

  			String newColor;
		
//  			Toast.makeText(parent.getContext(), "newBaudRate is-" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
  			newColor= parent.getItemAtPosition(position).toString();
  			 switch (newColor) {
  			case "绿色":
          	     color120.setBackgroundColor(MakerColor.GREEN);
          	     MakerColor.COLOR120 = 120.0f;
          	   MakerColor.color120 = MakerColor.GREEN;
	             Constants.color120Panel.setBackgroundColor(MakerColor.GREEN);
        		break;	
            case "红色":
           	 color120.setBackgroundColor(MakerColor.RED);
           	 MakerColor.COLOR120 = 0.0f;          
           	MakerColor.color120 = MakerColor.RED;
	             Constants.color120Panel.setBackgroundColor(MakerColor.RED);
           	 break;
            case "橙色":
           	 color120.setBackgroundColor(MakerColor.ORANGE);
           	 MakerColor.COLOR120 = 30.0f;
           	MakerColor.color120 = MakerColor.ORANGE;
	             Constants.color120Panel.setBackgroundColor(MakerColor.ORANGE);
           	 break;
            case "黄色":
           	 color120.setBackgroundColor(MakerColor.YELLOW);
           	 MakerColor.COLOR120 = 60.0f;
           	MakerColor.color120 = MakerColor.YELLOW;
	             Constants.color120Panel.setBackgroundColor(MakerColor.YELLOW);
         		break; 
            case "青色":
           	 color120.setBackgroundColor(MakerColor.CYAN);
           	 MakerColor.COLOR120 = 180.0f;
           	MakerColor.color120 = MakerColor.CYAN;
	             Constants.color120Panel.setBackgroundColor(MakerColor.CYAN);
           	 break;
            case "蓝色":
           	 color120.setBackgroundColor(MakerColor.BLUE);
           	 MakerColor.COLOR120 = 240.0f;
           	MakerColor.color120 = MakerColor.BLUE;
	             Constants.color120Panel.setBackgroundColor(MakerColor.BLUE);
           	 break;
            case "紫色":
           	 color120.setBackgroundColor(MakerColor.VIOLET);
           	 MakerColor.COLOR120 = 270.0f;
           	MakerColor.color120 = MakerColor.VIOLET;
	             Constants.color120Panel.setBackgroundColor(MakerColor.VIOLET);
           	 break;
            case "天蓝":
           	 color120.setBackgroundColor(MakerColor.AZURE);
           	 MakerColor.COLOR120 = 210.0f;
           	MakerColor.color120 = MakerColor.AZURE;
	             Constants.color120Panel.setBackgroundColor(MakerColor.AZURE);
           	 break;
            case "品红":
           	 color120.setBackgroundColor(MakerColor.MAGENTA);
           	 MakerColor.COLOR120 = 300.0f;
           	MakerColor.color120 = MakerColor.MAGENTA;
	             Constants.color120Panel.setBackgroundColor(MakerColor.MAGENTA);
           	 break;
            case "玫瑰":
           	 color120.setBackgroundColor(MakerColor.ROSE);
           	 MakerColor.COLOR120 = 330.0f;
           	MakerColor.color120 = MakerColor.ROSE;
	             Constants.color120Panel.setBackgroundColor(MakerColor.ROSE);
           	 break;
             default:
            	 break;
            }
  		}
  		public void onNothingSelected(AdapterView<?> parent) {
  			// Do nothing.    
  		}
  	}
  	
  	public class MyOnItemSelectedListener7 implements OnItemSelectedListener{
  		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

  			String newColor;
		
//  			Toast.makeText(parent.getContext(), "newBaudRate is-" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
  			newColor= parent.getItemAtPosition(position).toString();
  			 switch (newColor) {
  			case "绿色":
          	     color130.setBackgroundColor(MakerColor.GREEN);
          	     MakerColor.COLOR130 = 120.0f;
          	   MakerColor.color130 = MakerColor.GREEN;
	             Constants.color130Panel.setBackgroundColor(MakerColor.GREEN);
        		break;	
            case "红色":
           	 color130.setBackgroundColor(MakerColor.RED);
           	 MakerColor.COLOR130 = 0.0f; 
           	MakerColor.color130 = MakerColor.RED;
	             Constants.color130Panel.setBackgroundColor(MakerColor.RED);
           	 break;
            case "橙色":
           	 color130.setBackgroundColor(MakerColor.ORANGE);
           	 MakerColor.COLOR130 = 30.0f;
           	MakerColor.color130 = MakerColor.ORANGE;
	             Constants.color130Panel.setBackgroundColor(MakerColor.ORANGE);
           	 break;
            case "黄色":
           	 color130.setBackgroundColor(MakerColor.YELLOW);
           	 MakerColor.COLOR130 = 60.0f;
           	MakerColor.color130 = MakerColor.YELLOW;
	             Constants.color130Panel.setBackgroundColor(MakerColor.YELLOW);
         		break; 
            case "青色":
           	 color130.setBackgroundColor(MakerColor.CYAN);
           	 MakerColor.COLOR130 = 180.0f;
           	MakerColor.color130 = MakerColor.CYAN;
	             Constants.color130Panel.setBackgroundColor(MakerColor.CYAN);
           	 break;
            case "蓝色":
           	 color130.setBackgroundColor(MakerColor.BLUE);
           	 MakerColor.COLOR130 = 240.0f;
           	MakerColor.color130 = MakerColor.BLUE;
	             Constants.color130Panel.setBackgroundColor(MakerColor.BLUE);
           	 break;
            case "紫色":
           	 color130.setBackgroundColor(MakerColor.VIOLET);
           	 MakerColor.COLOR130 = 270.0f;
           	MakerColor.color130 = MakerColor.VIOLET;
	             Constants.color130Panel.setBackgroundColor(MakerColor.VIOLET);
           	 break;
            case "天蓝":
           	 color130.setBackgroundColor(MakerColor.AZURE);
           	 MakerColor.COLOR130 = 210.0f;
           	MakerColor.color130 = MakerColor.AZURE;
	             Constants.color130Panel.setBackgroundColor(MakerColor.AZURE);
           	 break;
            case "品红":
           	 color130.setBackgroundColor(MakerColor.MAGENTA);
           	 MakerColor.COLOR130 = 300.0f;
           	MakerColor.color130 = MakerColor.MAGENTA;
	             Constants.color130Panel.setBackgroundColor(MakerColor.MAGENTA);
           	 break;
            case "玫瑰":
           	 color130.setBackgroundColor(MakerColor.ROSE);
           	 MakerColor.COLOR130 = 330.0f;
           	MakerColor.color130 = MakerColor.ROSE;
	             Constants.color130Panel.setBackgroundColor(MakerColor.ROSE);
           	 break;
             default:
            	 break;
            }
  		}
  		public void onNothingSelected(AdapterView<?> parent) {
  			// Do nothing.    
  		}
  	}

//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		//颜色选择功能 选色罗盘
//		case R.id.color70button:
//			
//			dialog = new ColorPickerDialog(context, MakerColor.COLOR70,
//	                getResources().getString(R.string.btn_color_picker),
//	                new ColorPickerDialog.OnColorChangedListener() {
//	             
//	            @Override
//	            public void colorChanged(int color) {
//	            	color70.setBackgroundColor(color);
//	            	MakerColor.COLOR70 = color;
//	            	System.out.println(Integer.toHexString(color));
//	            	Constants.color70Panel.setBackgroundColor(color);
//	            }
//	        });
//	        dialog.show();
//	        
//			break;
//		case R.id.color80button:
//			
//			dialog = new ColorPickerDialog(context, MakerColor.COLOR80,
//	                getResources().getString(R.string.btn_color_picker),
//	                new ColorPickerDialog.OnColorChangedListener() {
//	             
//	            @Override
//	            public void colorChanged(int color) {
//	            	color80.setBackgroundColor(color);
//	            	MakerColor.COLOR80 = color;
//	            	Constants.color80Panel.setBackgroundColor(color);
//	            }
//	        });
//	        dialog.show();
//	        
//			break;
//		case R.id.color90button:
//			
//			dialog = new ColorPickerDialog(context, MakerColor.COLOR90,
//	                getResources().getString(R.string.btn_color_picker),
//	                new ColorPickerDialog.OnColorChangedListener() {
//	             
//	            @Override
//	            public void colorChanged(int color) {
//	            	color90.setBackgroundColor(color);
//	            	MakerColor.COLOR90 = color;
//	            	Constants.color90Panel.setBackgroundColor(color);
//	            }
//	        });
//	        dialog.show();
//	        
//			break;
//		case R.id.color100button:
//			
//			dialog = new ColorPickerDialog(context, MakerColor.COLOR100,
//	                getResources().getString(R.string.btn_color_picker),
//	                new ColorPickerDialog.OnColorChangedListener() {
//	             
//	            @Override
//	            public void colorChanged(int color) {
//	            	color100.setBackgroundColor(color);
//	            	MakerColor.COLOR100 = color;
//	            	Constants.color100Panel.setBackgroundColor(color);
//	            }
//	        });
//	        dialog.show();
//	        
//			break;
//		case R.id.color110button:
//			
//			dialog = new ColorPickerDialog(context, MakerColor.COLOR110,
//	                getResources().getString(R.string.btn_color_picker),
//	                new ColorPickerDialog.OnColorChangedListener() {
//	             
//	            @Override
//	            public void colorChanged(int color) {
//	            	color110.setBackgroundColor(color);
//	            	MakerColor.COLOR110 = color;
//	            	Constants.color110Panel.setBackgroundColor(color);
//	            }
//	        });
//	        dialog.show();
//	        
//			break;
//		case R.id.color120button:
//			
//			dialog = new ColorPickerDialog(context, MakerColor.COLOR120,
//	                getResources().getString(R.string.btn_color_picker),
//	                new ColorPickerDialog.OnColorChangedListener() {
//	             
//	            @Override
//	            public void colorChanged(int color) {
//	            	color120.setBackgroundColor(color);
//	            	MakerColor.COLOR120 = color;
//	            	Constants.color120Panel.setBackgroundColor(color);
//	            }
//	        });
//	        dialog.show();
//	        
//			break;
//		case R.id.color130button:
//			
//			dialog = new ColorPickerDialog(context, MakerColor.COLOR130,
//	                getResources().getString(R.string.btn_color_picker),
//	                new ColorPickerDialog.OnColorChangedListener() {
//	             
//	            @Override
//	            public void colorChanged(int color) {
//	            	color130.setBackgroundColor(color);
//	            	MakerColor.COLOR130 = color;
//	            	Constants.color130Panel.setBackgroundColor(color);
//	            }
//	        });
//	        dialog.show();
//	        
//		
//		}
//		
//	}
	
}
