package com.FS.JUMag;

import android.app.*;
//import android.*;
import android.os.*;
import android.widget.*;
import android.widget.GridLayout.*;
import android.view.*;
import android.text.*;
import android.graphics.*;

public class Signup extends Activity
{
	
	EditText fn,mn,ln,em,pass;
	Spinner gender;
	DatePicker dp;
	int day,year,month,g;
	String first,middle,last,email,password;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.drawable.fadein,R.drawable.fadeout);
		setContentView(R.layout.signup);
		ActionBar actionbar= getActionBar();
		//actionbar.hide();
		fn=(EditText)findViewById(R.id.signupfirstName);
		mn=(EditText)findViewById(R.id.signupmiddlename);
		ln=(EditText)findViewById(R.id.signuplastname);
		em=(EditText)findViewById(R.id.signupemail);
		pass=(EditText)findViewById(R.id.signuppass);
		gender=(Spinner) findViewById(R.id.signupgender);
		dp=(DatePicker) findViewById(R.id.signupdob);
		ArrayAdapter<String> aa=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,new String[]{"Gender","Female","Male"});
		gender.setAdapter(aa);
	}
	
	public void signup(View view){
		month=dp.getMonth();
		year=dp.getYear();
		day=dp.getDayOfMonth();
		g=gender.getSelectedItemPosition();
		first=fn.getText().toString().replace(" ","");
		last=ln.getText().toString().replace(" ","");
		middle=mn.getText().toString().replace(" ","");
		email=em.getText().toString().replace(" ","");
		password=pass.getText().toString().replace(" ","");
		
		
		
	}
	
}
