package com.FS.JUMag;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.preference.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import org.apache.http.*;
import org.apache.http.client.entity.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.message.*;
import android.graphics.drawable.*;

public class MainActivity extends Activity
{
	AlertDialog d;
	Thread listen =null;
	final String defServerAddr="http://127.0.0.1:8080/";
	String server;
	Boolean loggedIn=false;
	final String loginlocation="JUMag/login.php";
	EditText user,pass;
	String userName="",password="";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		user=(EditText) findViewById(R.id.username);
		pass=(EditText) findViewById(R.id.password);
		server= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("server",defServerAddr);
   		loggedIn= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("loggedIn",false);
		ActionBar a=getActionBar() ;
		//a.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4033CA")));
		a.setTitle("JU Mag");
		a.setSubtitle("   Login");
	}
	
	public void login(View view){
//		
		
		password=pass.getText().toString();
		userName=user.getText().toString();
		AlertDialog.Builder builder=new AlertDialog.Builder(new ContextThemeWrapper( this, android.R.style.Theme_DeviceDefault_Light_DarkActionBar))
		//.setTitle("Test Dialogue")
		.setMessage("Logging in...")
		.setCancelable(false);
		
		d=builder.create();
		d.show();
		listen=new Thread(new Runnable(){
			public void run(){
			Login();
			}
		});
		listen.start();
	}
	
	public void signup(View view){
		startService(new Intent(this, Notifier.class));
		startActivity(new Intent(this,Signup.class));
		
	}
	
	void Login(){
		DefaultHttpClient htc= new DefaultHttpClient();
		HttpPost postData= new HttpPost(server+loginlocation);
		List<NameValuePair> data= new ArrayList<NameValuePair>();
		postData.setHeader("User-Agent","Mozilla/5.0");
		data.add(new BasicNameValuePair("user",userName));
		data.add(new BasicNameValuePair("pass",password));
		data.add(new BasicNameValuePair("todo" ,"login"));
		try
		{
			postData.setEntity(new UrlEncodedFormEntity(data));
		}
		catch (UnsupportedEncodingException e)
		{}
		try
		{
			HttpResponse response=null;
			while(response==null){
				response= htc.execute(postData);
			}
			//showToast(response.toString());
			int sc=response.getStatusLine().getStatusCode();
			//showToast(String.valueOf(sc));
			if(sc==200){
				BufferedReader bf= new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String line="";
				String str="";
				
				while((line=bf.readLine())!=null){
					str+=line;
				}
				final String returnedData=str;
				//showToast(str);
				if(returnedData.startsWith("loggedin")){
					SharedPreferences.Editor ed=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
					ed.putBoolean("loggedIn",true);
					showToast("Logged In");
				}else if(returnedData.startsWith("wrong")){
					showToast("Wrong Username or Password");
				}else{
					showToast("Connection issue");
				}
			}
		}
		catch (Exception e)
		{
			showToast("Error: Please Check your Internet Connection");
		}
		runOnUiThread(new Runnable(){

			@Override
			public void run()
			{
				// TODO: Implement this method
				d.dismiss();
			}
		});
		
	}
	
	void showToast(final String s){
		runOnUiThread(new Runnable(){

				@Override
				public void run()
				{
					// TODO: Implement this method
					Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
				}
			});
	}
	
	
}
