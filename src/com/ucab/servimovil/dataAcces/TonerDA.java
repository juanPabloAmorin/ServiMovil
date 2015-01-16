package com.ucab.servimovil.dataAcces;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ucab.servimovil.R;
import com.ucab.servimovil.bbdd.DataBase;
import com.ucab.servimovil.model.Toner;
import com.ucab.servimovil.model.TonerTemp;

public class TonerDA {

         
	public long insertToner(Toner toner, Activity activity){
        DataBase ddbb = new DataBase(activity,activity.getResources().getString(R.string.db_name),null,DataBase.version);
		SQLiteDatabase db = ddbb.getWritableDatabase();
		
		ContentValues registro = new ContentValues();
		
    	registro.put("SERIAL",toner.getSerial());
    	registro.put("NUMERO_FACTURA",toner.getNumeroFactura());
    	registro.put("MODELO",toner.getIdModelo());
    	registro.put("STATUS",toner.getStatus());  
   	    return db.insert("TONER",null,registro);
   	    
	}
	
	public long insertTonerTemp(TonerTemp toner, Activity activity){
        DataBase ddbb = new DataBase(activity,activity.getResources().getString(R.string.db_name),null,DataBase.version);
		SQLiteDatabase db = ddbb.getWritableDatabase();
		
		ContentValues registro = new ContentValues();
		
    	registro.put("SERIAL",toner.getSerial());
    	registro.put("MODELO",toner.getModelo()); 
   	    return db.insert("TONER_TEMP",null,registro);
   	    
	}
	
	public List<Toner> getRegisteredTonerList(Activity activity){
		
		   List<Toner> tonerList = new ArrayList<Toner>();
		   Toner toner;
		   String query = "SELECT * FROM TONER";
		   DataBase ddbb = new DataBase(activity,activity.getResources().getString(R.string.db_name),null,DataBase.version);
	       
		   SQLiteDatabase dataBase = ddbb.getReadableDatabase();
		   
		   Cursor cursor = dataBase.rawQuery(query,null);
		   
		   if(cursor.moveToFirst()){
			    do{
			    	toner = new Toner();
			    	toner.setIdToner(cursor.getLong(0));
			    	toner.setSerial(cursor.getString(1));
			    	toner.setNumeroFactura(cursor.getLong(2));
			    	toner.setIdModelo(cursor.getLong(3));
			    	toner.setStatus(cursor.getString(4));
			    	
			    	Log.e("insertado","id = " + cursor.getLong(0) + " serial = " + cursor.getString(1) + " status = " + cursor.getString(4) + " idMarca = " + cursor.getLong(2) + " idModelo =  " + cursor.getLong(3));
			    }while(cursor.moveToNext());
		   }else{
			   tonerList = null;
		   }
		   
		   dataBase.close();
		   return tonerList;
	}
	
	
	public List<TonerTemp> getRegisteredTonerTempList(Activity activity){
		
		   List<TonerTemp> tonerList = new ArrayList<TonerTemp>();
		   TonerTemp toner;
		   String query = "SELECT * FROM TONER_TEMP";
		   DataBase ddbb = new DataBase(activity,activity.getResources().getString(R.string.db_name),null,DataBase.version);
	       
		   SQLiteDatabase dataBase = ddbb.getReadableDatabase();
		   
		   Cursor cursor = dataBase.rawQuery(query,null);
		   
		   if(cursor.moveToFirst()){
			    do{
			    	toner = new TonerTemp();
			    	toner.setIdToner(cursor.getLong(0));
			    	toner.setSerial(cursor.getString(1));
			    	toner.setModelo(cursor.getString(2));
			    	
			    	tonerList.add(toner);
			    	
			    	toner =null;
			    	
			    }while(cursor.moveToNext());
		   }else{
			   tonerList = null;
		   }
		   
		   dataBase.close();
		   return tonerList;
	}
	
	public Toner findTonerBySerial(String serial,Activity activity){
		   
		    Toner toner;
		
		    String query = "SELECT * FROM TONER WHERE SERIAL = '" + serial+"';";
			DataBase ddbb = new DataBase(activity,activity.getResources().getString(R.string.db_name),null,DataBase.version);

			SQLiteDatabase dataBase = ddbb.getReadableDatabase();
			
			 Cursor cursor = dataBase.rawQuery(query,null);
			   
			   if(cursor.moveToFirst()){
				    do{
				    	toner = new Toner();
				    	toner.setIdToner(cursor.getLong(0));
				    	toner.setSerial(cursor.getString(1));
				    	toner.setNumeroFactura(cursor.getLong(2));
				    	toner.setIdModelo(cursor.getLong(3));
				    	toner.setStatus(cursor.getString(4));
				    	
				    }while(cursor.moveToNext());
			   }else{
				   toner = null;
			   }
			   dataBase.close();
			   return toner;
	}
	
	public TonerTemp findTonerTempBySerial(String serial,Activity activity){
		   
	    TonerTemp toner;
	
	    String query = "SELECT * FROM TONER_TEMP WHERE SERIAL = '" + serial+"';";
		DataBase ddbb = new DataBase(activity,activity.getResources().getString(R.string.db_name),null,DataBase.version);

		SQLiteDatabase dataBase = ddbb.getReadableDatabase();
		
		 Cursor cursor = dataBase.rawQuery(query,null);
		   
		   if(cursor.moveToFirst()){
			    do{
			    	toner = new TonerTemp();
			    	toner.setIdToner(cursor.getLong(0));
			    	toner.setSerial(cursor.getString(1));
			    	toner.setModelo(cursor.getString(2));
			    	
			    }while(cursor.moveToNext());
		   }else{
			   toner = null;
		   }
		   dataBase.close();
		   return toner;
}
	
	public Toner findTonerById(long id,Activity activity){
		   
	    Toner toner;
	
	    String query = "SELECT * FROM TONER WHERE ID_TONER = " + id;
		DataBase ddbb = new DataBase(activity,activity.getResources().getString(R.string.db_name),null,DataBase.version);

		SQLiteDatabase dataBase = ddbb.getReadableDatabase();
		
		 Cursor cursor = dataBase.rawQuery(query,null);
		   
		   if(cursor.moveToFirst()){
			    do{
			    	toner = new Toner();
			    	toner.setIdToner(cursor.getLong(0));
			    	toner.setSerial(cursor.getString(1));
			    	toner.setNumeroFactura(cursor.getLong(2));
			    	toner.setIdModelo(cursor.getLong(3));
			    	toner.setStatus(cursor.getString(4));
			    	
			    }while(cursor.moveToNext());
		   }else{
			   toner = null;
		   }
		   dataBase.close();
		   return toner;
}
	
	public void updateStatusPorEntregar(Activity activity){
		   
		 DataBase ddbb = new DataBase(activity,activity.getResources().getString(R.string.db_name),null,DataBase.version);
		 SQLiteDatabase db = ddbb.getWritableDatabase();
		 
		 ContentValues valores = new ContentValues();
		 valores.put("STATUS",activity.getResources().getString(R.string.toner_status_por_entregar));
		 db.update("TONER", valores,null, null);
	}
	
	public void deleteTonerTemp(Activity activity){
		   
		 DataBase ddbb = new DataBase(activity,activity.getResources().getString(R.string.db_name),null,DataBase.version);
		 SQLiteDatabase db = ddbb.getWritableDatabase();
		
		 db.delete("TONER_TEMP",null,null);
	}
	
	public void deleteTonerTempBySerial(Context activity, String serial){
		   
		 DataBase ddbb = new DataBase(activity,activity.getResources().getString(R.string.db_name),null,DataBase.version);
		 SQLiteDatabase db = ddbb.getWritableDatabase();
		
		 db.delete("TONER_TEMP","SERIAL = '"+serial+"'",null);
	}
	
	
}
