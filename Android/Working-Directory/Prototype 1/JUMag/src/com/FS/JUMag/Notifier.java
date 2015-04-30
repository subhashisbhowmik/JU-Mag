package com.FS.JUMag;

import android.app.*;
import android.os.*;
import android.content.*;
import java.net.*;
import android.graphics.*;
import java.io.*;

public class Notifier extends Service
{

	@Override
	public IBinder onBind(Intent p1)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId)
	{
		// TODO: Implement this method
		super.onStart(intent, startId);
		
			try
			{
				DatagramSocket ds= new DatagramSocket(58624);
				byte[] bf= new byte[1024];
				DatagramPacket packet= new DatagramPacket(bf,1024);
				ds.receive(packet);
				byte[] data= packet.getData();
				StringBuilder sf= new StringBuilder();
				for(int j=0;j<data.length; j++){
					sf.append((char)data[j]);
				}
				final String dataString= sf.toString();
				
			
			
			
				if(dataString.startsWith("hi")){
				Notification.Builder b= new Notification.Builder(getApplicationContext());
				b.setContentTitle("Hi");
				b.setContentText(dataString);
				b.setAutoCancel(true);
				b.setSmallIcon(R.drawable.ic_launcher);
				Intent result= new Intent( this, MainActivity.class);
				//TaskStackBuilder sb= TaskStackBuilder.create(this);
				//sb.addParentStack(MainActivity.class);
				//sb.addNextIntent(result);
				PendingIntent pi= PendingIntent.getActivity(this,0,result,PendingIntent.FLAG_UPDATE_CURRENT);//sb.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
				b.setContentIntent(pi);
				Notification noti= b.build();				
				NotificationManager nm=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				nm.notify(1,noti);
				
				}
				
				
				
				
				
			}
			catch (Exception e)
			{}
		
	}
	

}
