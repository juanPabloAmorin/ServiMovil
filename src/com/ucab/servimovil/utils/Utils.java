package com.ucab.servimovil.utils;


import java.io.*;

import com.ucab.servimovil.MainActivity;
import com.ucab.servimovil.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;

public class Utils {

	public static String convertInputStreamToString(InputStream inputStream) throws IOException{
	       BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
	       String line = "";
	       String result = "";
	       while((line = bufferedReader.readLine()) != null)
	           result += line;

	       inputStream.close();
	       return result;

	   }   
	
	public static void alertMessage(String message,Activity activity){
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);
		alertBuilder.setMessage(message);
		alertBuilder.setCancelable(true);
		alertBuilder.setPositiveButton("Ok",
	            new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int id) {
	            dialog.cancel();
	        }
	    });
	   
	    alertBuilder.show();
	}
	
	public static void generateSyncNotification(FragmentActivity activity,String number){
		
		 NotificationCompat.Builder mBuilder =
				    new NotificationCompat.Builder(activity)
				        .setSmallIcon(R.drawable.ic_sync)
				        .setLargeIcon((((BitmapDrawable)activity.getResources()
				            .getDrawable(R.drawable.ic_sync)).getBitmap()))
				        .setContentTitle("ServiMovil: Sincronización pendiente.")
				        .setContentText("Tiene transacciones pendientes por sincronizar.")
				        .setContentInfo(number)
				        .setTicker("Sincronización pendiente");
		 
		 Intent notIntent =  new Intent(activity, MainActivity.class);
	     PendingIntent contIntent =PendingIntent.getActivity(activity, 0, notIntent, 0);
		 mBuilder.setContentIntent(contIntent);
		 
		 NotificationManager mNotificationManager =  (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
	     mNotificationManager.notify(1, mBuilder.build());
	}
	
	public static void cancelNotifications(Context ctx) {
	    String notserv = Context.NOTIFICATION_SERVICE;
	    NotificationManager manager = (NotificationManager) ctx.getSystemService(notserv);
	    manager.cancelAll();
	}
}
