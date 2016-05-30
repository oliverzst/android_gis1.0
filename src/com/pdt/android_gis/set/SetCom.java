package com.pdt.android_gis.set;

import java.io.IOException;
import java.util.Random;

import tw.com.prolific.driver.pl2303.PL2303Driver;

import com.pdt.android_gis.R;
import com.pdt.android_gis.AppManager;
import com.pdt.android_gis.database.DBManager;
import com.pdt.android_gis.util.Constants;

import android.app.Activity;
import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class SetCom extends Activity {
	
	private static final boolean SHOW_DEBUG = true;
	private static final int DISP_CHAR = 0;
	private static final int LINEFEED_CODE_CRLF = 1;
    private static final int LINEFEED_CODE_LF = 2;
    PL2303Driver mSerial;
	
    String TAG = "PL2303HXD_APLog";

    private Button btWrite;
    private EditText etWrite;
    
    private Button btRead;
    private EditText etRead;

    private Button btLoopBack;
    private ProgressBar pbLoopBack;    
    private TextView tvLoopBack;
    
    private Button bSetNewVIDPID;
    private EditText etNewVIDPID;
    
    private int mDisplayType = DISP_CHAR;
    private int mReadLinefeedCode = LINEFEED_CODE_LF;
    private int mWriteLinefeedCode = LINEFEED_CODE_LF;
   
    //BaudRate.B4800, DataBits.D8, StopBits.S1, Parity.NONE, FlowControl.RTSCTS
    private PL2303Driver.BaudRate mBaudrate = PL2303Driver.BaudRate.B9600;
    private PL2303Driver.DataBits mDataBits = PL2303Driver.DataBits.D8;
    private PL2303Driver.Parity mParity = PL2303Driver.Parity.EVEN;
    private PL2303Driver.StopBits mStopBits = PL2303Driver.StopBits.S1;
    private PL2303Driver.FlowControl mFlowControl = PL2303Driver.FlowControl.OFF;
  
    private static final String ACTION_USB_PERMISSION = "com.prolific.pl2303hxdsimpletest.USB_PERMISSION"; 
    
    public Spinner PL2303HXD_BaudRate_spinner;
    public int PL2303HXD_BaudRate;
    public String PL2303HXD_BaudRate_str="B4800";
    
	private String strStr;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
//		Log.d(TAG, "Enter onCreate");
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setcom);
//		getWindow().setBackgroundDrawable(null);
		//波特率选择菜单
        PL2303HXD_BaudRate_spinner = (Spinner)findViewById(R.id.spinner1);
		
		ArrayAdapter<CharSequence> adapter = 
			ArrayAdapter.createFromResource(this, R.array.BaudRate_Var, android.R.layout.simple_spinner_item);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		PL2303HXD_BaudRate_spinner.setAdapter(adapter);		
		PL2303HXD_BaudRate_spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		
		//打开串口按钮
		Button mButton01 = (Button)findViewById(R.id.button1);
		mButton01.setOnClickListener(new Button.OnClickListener() {		
			public void onClick(View v) {
				openUsbSerial();
			}
		});
		
		//写数据按钮
        btWrite = (Button) findViewById(R.id.button2);        
        btWrite.setOnClickListener(new Button.OnClickListener() {		
			public void onClick(View v) {
				etWrite = (EditText) findViewById(R.id.editText1);
				writeDataToSerial();

			}
		});
        
        //读数据按钮
        btRead = (Button) findViewById(R.id.button3);        
        btRead.setOnClickListener(new Button.OnClickListener() {		
			public void onClick(View v) {				
				etRead = (EditText) findViewById(R.id.editText2);
				readDataFromSerial();
				//读入手台数据 存入手机数据库
				
				
			}
		});

//        btLoopBack = (Button) findViewById(R.id.button4);        
//        btLoopBack.setOnClickListener(new Button.OnClickListener() {		
//    			public void onClick(View v) {				
//    				pbLoopBack = (ProgressBar) findViewById(R.id.ProgressBar1);
//    				setProgressBarVisibility(true);
//    				pbLoopBack.setIndeterminate(false);
//    				pbLoopBack.setVisibility(View.VISIBLE);
//    				pbLoopBack.setProgress(0);
//    				tvLoopBack = (TextView) findViewById(R.id.textView2);
//    				new Thread(tLoop).start();
//    			}
//    	});        
        
     // get service
        mSerial = new PL2303Driver((UsbManager) getSystemService(Context.USB_SERVICE),
           	  	this, ACTION_USB_PERMISSION); 
        
     // check USB host function.
     		if (!mSerial.PL2303USBFeatureSupported()) {

     			Toast.makeText(this, "No Support USB host API", Toast.LENGTH_SHORT)
     					.show();

     			Log.d(TAG, "No Support USB host API");

     			mSerial = null;

     		}
        
//        Log.d(TAG, "Leave onCreate");
		
		AppManager.getAppManager().addActivity(this);
	}
	
	protected void onStop() {
//    	Log.d(TAG, "Enter onStop");
        super.onStop();        
//        Log.d(TAG, "Leave onStop");
    }    
    
    @Override
    protected void onDestroy() {
//    	Log.d(TAG, "Enter onDestroy");      
    	if(mSerial!=null) {
    		mSerial.end();
    		mSerial = null;
    	}    	
    	super.onDestroy();        
//        Log.d(TAG, "Leave onDestroy");
    }    

    public void onStart() {
//    	Log.d(TAG, "Enter onStart");
    	super.onStart();
//    	Log.d(TAG, "Leave onStart");
    }
    
    public void onResume() {
//    	Log.d(TAG, "Enter onResume"); 
        super.onResume();
        String action =  getIntent().getAction();
//    	Log.d(TAG, "onResume:"+action);
    	
        if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action))        
       	if(!mSerial.isConnected()) {
             if (SHOW_DEBUG) {
//              	  Log.d(TAG, "New instance : " + mSerial);
             }
            
    		 if( !mSerial.enumerate() ) {
    			
              	Toast.makeText(this, "no more devices found", Toast.LENGTH_SHORT).show();     
              	return;
              } else {
//                 Log.d(TAG, "onResume:enumerate succeeded!");
              }    		 
        }//if isConnected  
		Toast.makeText(this, "attached", Toast.LENGTH_SHORT).show();
       	
//        Log.d(TAG, "Leave onResume"); 
    }        
    
    private void openUsbSerial() {
//      	 Log.d(TAG, "Enter  openUsbSerial");
   	   if(null==mSerial){
//   		   Log.d(TAG, "UsbSerial isNull");
   		   return;
   	   }		      	 
   	   
          if (mSerial.isConnected()) {
              if (SHOW_DEBUG) {
//                  Log.d(TAG, "openUsbSerial : isConnected ");
              }
              String str = PL2303HXD_BaudRate_spinner.getSelectedItem().toString();
              int baudRate= Integer.parseInt(str);
   		   switch (baudRate) {
                	case 9600:
                		mBaudrate = PL2303Driver.BaudRate.B9600;
                		break;
                	case 19200:
                		mBaudrate =PL2303Driver.BaudRate.B19200;
                		break;
                	case 38400:
                		mBaudrate =PL2303Driver.BaudRate.B38400;
                		break;
                	case 57600:
                		mBaudrate =PL2303Driver.BaudRate.B57600;
                		break;
                	
                	case 115200:
                		mBaudrate =PL2303Driver.BaudRate.B115200;
                		break;
                	default:
                		mBaudrate =PL2303Driver.BaudRate.B9600;
                		break;
              }   		            
//   		   Log.d(TAG, "baudRate:"+baudRate);
   		  // if (!mSerial.InitByBaudRate(mBaudrate)) {
              if (!mSerial.InitByBaudRate(mBaudrate,700)) {
           	   if(!mSerial.PL2303Device_IsHasPermission()) {
   					Toast.makeText(this, "cannot open, maybe no permission", Toast.LENGTH_SHORT).show();		
   				}
   				
                  if(mSerial.PL2303Device_IsHasPermission() && (!mSerial.PL2303Device_IsSupportChip())) {
   					Toast.makeText(this, "cannot open, maybe this chip has no support, please contact www.lzwest.com.", Toast.LENGTH_SHORT).show();
                  }
              } else {        	      
                     Toast.makeText(this, "connected", Toast.LENGTH_SHORT).show();        	   
              }
          }//isConnected
          
//          Log.d(TAG, "Leave openUsbSerial");
      }//openUsbSerial
    
    private void readDataFromSerial() {

        int len;
        byte[] rbuf = new byte[4096];
        StringBuffer sbHex=new StringBuffer();
        
//        Log.d(TAG, "Enter readDataFromSerial");

		if(null==mSerial){
//			Log.d(TAG, "UsbSerial isNull");
			return;    
		}
			           
        if(!mSerial.isConnected()) 
        	return;
        
        len = mSerial.read(rbuf);
        if(len<0) {
//        	Log.d(TAG, "Fail to bulkTransfer(read data)");
        	return;
        }

        if (len > 0) {        	
               if (SHOW_DEBUG) {
//            	   Log.d(TAG, "read len : " + len);
               }                
               //rbuf[len] = 0;
               for (int j = 0; j < len; j++) {            	   
                 //String temp=Integer.toHexString(rbuf[j]&0x000000FF);
                 //Log.i(TAG, "str_rbuf["+j+"]="+temp);
                 //int decimal = Integer.parseInt(temp, 16);
                 //Log.i(TAG, "dec["+j+"]="+decimal);
                 //sbHex.append((char)decimal);
                 //sbHex.append(temp);
            	 //sbHex.append((char) (rbuf[j]&0x000000FF));
            	   
            	 //  sbHex.append(Integer.toHexString((rbuf[j])|0xffFFff00).substring(6).toUpperCase() +" ");//��д
            	   sbHex.append(Integer.toHexString((rbuf[j])|0xffFFff00).substring(6));  
   		        }              
               etRead.setText(sbHex.toString());    
               Toast.makeText(this, "len="+len, Toast.LENGTH_SHORT).show();
        }   
        else {     	
        	 if (SHOW_DEBUG) {
//               Log.d(TAG, "read len : 0 ");
             }
        	 etRead.setText("empty");
        	 return;
        }

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        Log.d(TAG, "Leave readDataFromSerial");	
    }//readDataFromSerial
    
    private void writeDataToSerial() {
    	 
//    	Log.d(TAG, "Enter writeDataToSerial");
    	
		if(null==mSerial)
			return;
    	
    	if(!mSerial.isConnected()) 
    		return;
    	
        String strWrite = etWrite.getText().toString();
        
        /*
        //strWrite="012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";
       // strWrite = changeLinefeedcode(strWrite);
         strWrite="012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";
         if (SHOW_DEBUG) {
            Log.d(TAG, "PL2303Driver Write(" + strWrite.length() + ") : " + strWrite);
        }
        int res = mSerial.write(strWrite.getBytes(), strWrite.length());
		if( res<0 ) {
			Log.d(TAG, "setup: fail to controlTransfer: "+ res);
			return;
		} 
		
		Toast.makeText(this, "Write length: "+strWrite.length()+" bytes", Toast.LENGTH_SHORT).show();  
		*/
        // test data: 600 byte
		//strWrite="AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
		 if (SHOW_DEBUG) {
//            Log.d(TAG, "PL2303Driver Write 2(" + strWrite.length() + ") : " + strWrite);
        }
		 byte [] byWrite = new byte [strWrite.length()/2];
		 int len = 0;
		 for(int i=0;i<strWrite.length();i+=2) {
			 //Integer.parseInt(strWrite.substring(i, i+2),16);
			 byWrite[i>>1]=(byte)(Integer.parseInt(strWrite.substring(i, i+2),16));
			 len++;
		 }
		 //int res = mSerial.write(strWrite.getBytes(), strWrite.length());
		 int res = mSerial.write(byWrite,len);
		if( res<0 ) {
//			Log.d(TAG, "setup2: fail to controlTransfer: "+ res);
			return;
		} 
		
		Toast.makeText(this, "Write length: "+strWrite.length()+" bytes", Toast.LENGTH_SHORT).show(); 

//		Log.d(TAG, "Leave writeDataToSerial");
    }//writeDataToSerial
    
  //------------------------------------------------------------------------------------------------//
  //--------------------------------------LoopBack function-----------------------------------------//    
  //------------------------------------------------------------------------------------------------//    
    private static final int START_NOTIFIER = 0x100;
	private static final int STOP_NOTIFIER = 0x101;
	private static final int PROG_NOTIFIER_SMALL = 0x102;
	private static final int PROG_NOTIFIER_LARGE = 0x103;
	private static final int ERROR_BAUDRATE_SETUP = 0x8000;
	private static final int ERROR_WRITE_DATA = 0x8001;
	private static final int ERROR_WRITE_LEN = 0x8002;
	private static final int ERROR_READ_DATA = 0x8003;
	private static final int ERROR_READ_LEN = 0x8004;
	private static final int ERROR_COMPARE_DATA = 0x8005;
	
	Handler myMessageHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch(msg.what){
				case START_NOTIFIER:
					pbLoopBack.setProgress(0);
					tvLoopBack.setText("LoopBack Test start...");
					btWrite.setEnabled(false);
					btRead.setEnabled(false);
					break;
				case STOP_NOTIFIER:
					pbLoopBack.setProgress(pbLoopBack.getMax());
					tvLoopBack.setText("LoopBack Test successfully!");
					btWrite.setEnabled(true);
					btRead.setEnabled(true);
					break;
				case PROG_NOTIFIER_SMALL:
					pbLoopBack.incrementProgressBy(5);
					break;
				case PROG_NOTIFIER_LARGE:
					pbLoopBack.incrementProgressBy(10);					
					break;	
				case ERROR_BAUDRATE_SETUP:					
					tvLoopBack.setText("Fail to setup:baudrate "+msg.arg1);
					break;
				case ERROR_WRITE_DATA:
					tvLoopBack.setText("Fail to write:"+ msg.arg1);				
					break;
				case ERROR_WRITE_LEN:
					tvLoopBack.setText("Fail to write len:"+msg.arg2+";"+ msg.arg1);
					break;
				case ERROR_READ_DATA:
					tvLoopBack.setText("Fail to read:"+ msg.arg1);					
					break;
				case ERROR_READ_LEN:
					tvLoopBack.setText("Length("+msg.arg2+") is wrong! "+ msg.arg1);
					break;
				case ERROR_COMPARE_DATA:
	    	    	tvLoopBack.setText("wrong:"+ 
        	    			String.format("rbuf=%02X,byteArray1=%02X", msg.arg1, msg.arg2));
	    	    	break;
					
			}	
			super.handleMessage(msg);
		}//handleMessage
	};
	
	private void Send_Notifier_Message(int mmsg) {
		Message m= new Message();
		m.what = mmsg;
		myMessageHandler.sendMessage(m);
//		Log.d(TAG, String.format("Msg index: %04x", mmsg));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void Send_ERROR_Message(int mmsg, int value1, int value2) {
		Message m= new Message();
		m.what = mmsg;
		m.arg1 = value1;
		m.arg2 = value2;
		myMessageHandler.sendMessage(m);
//		Log.d(TAG, String.format("Msg index: %04x", mmsg));
	}	
	
	private Runnable tLoop = new Runnable() {
		public void run() {	

	    	int res = 0, len, i;
	    	Time t = new Time();
	    	byte[] rbuf = new byte[4096];    	
	    	final int mBRateValue[] = {9600, 19200, 115200};    	
	    	PL2303Driver.BaudRate mBRate[] = {PL2303Driver.BaudRate.B9600, PL2303Driver.BaudRate.B19200,PL2303Driver.BaudRate.B38400,PL2303Driver.BaudRate.B57600, PL2303Driver.BaudRate.B115200};
	    	
			if(null==mSerial)
				return;            	    	

	        if(!mSerial.isConnected()) 
	        	return;		
			
	       	t.setToNow();
	    	Random mRandom = new Random(t.toMillis(false));

			byte[] byteArray1 = new byte[256]; //test pattern-1    	
	    	mRandom.nextBytes(byteArray1);//fill buf with random bytes
	    	Send_Notifier_Message(START_NOTIFIER);    	
	    	
	    	for(int WhichBR=0;WhichBR<mBRate.length;WhichBR++) {
	    		
	    		try {
					res = mSerial.setup(mBRate[WhichBR], mDataBits, mStopBits, mParity, mFlowControl);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if( res<0 ) {
					Send_Notifier_Message(START_NOTIFIER);
					Send_ERROR_Message(ERROR_BAUDRATE_SETUP, mBRateValue[WhichBR], 0);					
					Log.d(TAG, "Fail to setup="+res);
					return;
				} 
				Send_Notifier_Message(PROG_NOTIFIER_LARGE);
	    		
				for(int times=0;times<2;times++) {
					
		    	  	len = mSerial.write(byteArray1, byteArray1.length);
		       		if( len<0 ) {
		       			Send_ERROR_Message(ERROR_WRITE_DATA, mBRateValue[WhichBR], 0);		       			
		        		Log.d(TAG, "Fail to write="+len);
		        		return;
		        	}
		        		
		        	if( len!=byteArray1.length ) {		       			
		       			Send_ERROR_Message(ERROR_WRITE_LEN, mBRateValue[WhichBR], len);		       			
		        		return;
		        	} 
		        	Send_Notifier_Message(PROG_NOTIFIER_SMALL);

		            len = mSerial.read(rbuf);
		    	    if(len<0) {
		    	    	Send_ERROR_Message(ERROR_READ_DATA, mBRateValue[WhichBR], 0);
		    	        return;
		    	    }
		    	    Log.d(TAG, "read length="+len+";byteArray1 length="+byteArray1.length);
		    	    
		    	    if(len!=byteArray1.length) {		    	    	
		    	    	Send_ERROR_Message(ERROR_READ_LEN, mBRateValue[WhichBR], len);
		    	        return;    	    	
		    	    }  		
		    	    Send_Notifier_Message(PROG_NOTIFIER_SMALL);
		    	    
		     	    for(i=0;i<len;i++) {
		    	    	if(rbuf[i]!=byteArray1[i]) {	
		    	    		Send_ERROR_Message(ERROR_COMPARE_DATA, rbuf[i], byteArray1[i]);		    	    		
		        	    	Log.d(TAG, "Data is wrong at "+ 
		        	    			String.format("rbuf[%d]=%02X,byteArray1[%d]=%02X", i, rbuf[i], i, byteArray1[i]));
		        	        return;    	    	    	    		
		    	    	}//if
		    	    }//for
		     	   Send_Notifier_Message(PROG_NOTIFIER_LARGE);
		     	    
				}//for(times)    	    
				
	    	}//for(WhichBR)
	       
	    	try {
				res = mSerial.setup(mBaudrate, mDataBits, mStopBits, mParity, mFlowControl);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if( res<0 ) {
				Send_ERROR_Message(ERROR_BAUDRATE_SETUP, 0, 0);				
				return;
			}   		    		    			
			Send_Notifier_Message(STOP_NOTIFIER);    	

		}//run()
	};//Runnable tLoop
	
	/**
	 * 
	 * 波特率选择
	 *
	 */
  	public class MyOnItemSelectedListener implements OnItemSelectedListener {
  		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
  			
  			if(null==mSerial)
  				return;
  			
  			if(!mSerial.isConnected()) 
  				return;
  			 
  			int baudRate=0;
  			String newBaudRate;
  			Toast.makeText(parent.getContext(), "newBaudRate is-" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
  			newBaudRate= parent.getItemAtPosition(position).toString();

  			try	{
  				baudRate= Integer.parseInt(newBaudRate);
  			}
  			catch (NumberFormatException e)	{
  				System.out.println(" parse int error!!  " + e);
  			}
  			
  			 switch (baudRate) {
             case 9600:
            	 mBaudrate =PL2303Driver.BaudRate.B9600;
            	 break;
             case 19200:
            	 mBaudrate =PL2303Driver.BaudRate.B19200;
            	 break;
             case 38400:
          		mBaudrate =PL2303Driver.BaudRate.B38400;
          		break;
          	case 57600:
          		mBaudrate =PL2303Driver.BaudRate.B57600;
          		break;	 
             case 115200:
            	 mBaudrate =PL2303Driver.BaudRate.B115200;
            	 break;
             default:
            	 mBaudrate =PL2303Driver.BaudRate.B9600;
            	 break;
            }   			 
  			 			 
 			int res = 0;
 			try {
 				res = mSerial.setup(mBaudrate, mDataBits, mStopBits, mParity, mFlowControl);
 			} catch (IOException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}

			if( res<0 ) {
				Log.d(TAG, "fail to setup");
				return;
			}
  		}
  		public void onNothingSelected(AdapterView<?> parent) {
  			// Do nothing.    
  		}
  	}//MyOnItemSelectedListener

}
