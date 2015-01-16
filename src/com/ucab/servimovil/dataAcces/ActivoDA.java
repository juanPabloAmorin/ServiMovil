package com.ucab.servimovil.dataAcces;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ucab.servimovil.R;
import com.ucab.servimovil.bbdd.DataBase;
import com.ucab.servimovil.model.Activo;

public class ActivoDA {
	
	public long insertActivo(Activo activo,Activity activity){
		
		 DataBase ddbb = new DataBase(activity,activity.getResources().getString(R.string.db_name),null,DataBase.version);
			SQLiteDatabase db = ddbb.getWritableDatabase();
			
			ContentValues registro = new ContentValues();
	
		     registro.put("REAL_ID",activo.getRealId());
		     registro.put("SERIAL",activo.getSerial());
		     registro.put("NUMERO",activo.getNumero());
		     registro.put("ID_TRANSACCION",activo.getIdTransaccion());
		     registro.put("NOMBRE",activo.getNombre());
		     registro.put("ID_DEPENDENCIA",activo.getIdDependencia());
		     registro.put("CONTADOR",activo.getContador());

	    	 return db.insert("ACTIVO",null,registro);
	   	   

	}
	
	public List<Activo> getActivoByTransaction(long idTransaction, Activity activity){
		
		 List<Activo> activos = new ArrayList<Activo>();
	     String query = "SELECT * FROM ACTIVO WHERE ID_TRANSACCION = "+idTransaction;
		
		 DataBase ddbb = new DataBase(activity,activity.getResources().getString(R.string.db_name),null,DataBase.version);
			SQLiteDatabase db = ddbb.getReadableDatabase();
			
		Cursor cursor = db.rawQuery(query,null);
		
		 if(cursor.moveToFirst()){
    		 do{
    			Activo activo = new Activo();
    			activo.setIdActivo(cursor.getLong(0));
    			activo.setRealId(cursor.getLong(1));
    			activo.setSerial(cursor.getString(2));
    			activo.setNumero(cursor.getString(3));
    			activo.setNombre(cursor.getString(4));
    			activo.setIdTransaccion(cursor.getLong(5));
    			activo.setIdDependencia(cursor.getLong(6));
    			activo.setContador(cursor.getInt(7));

    			activos.add(activo);
    			  
    		 }while(cursor.moveToNext());
    	 }
    	 db.close();
	     return activos;
		 
	}
	
	public Activo getActivoById(long id, Activity activity){
		
	     String query = "SELECT * FROM ACTIVO WHERE ID_ACTIVO = "+id;
		
		 DataBase ddbb = new DataBase(activity,activity.getResources().getString(R.string.db_name),null,DataBase.version);
			SQLiteDatabase db = ddbb.getReadableDatabase();
			
		Cursor cursor = db.rawQuery(query,null);
		
		 Activo activo = new Activo();
		 if(cursor.moveToFirst()){
   		 do{
   			activo.setIdActivo(cursor.getLong(0));
   			activo.setRealId(cursor.getLong(1));
   			activo.setSerial(cursor.getString(2));
   			activo.setNumero(cursor.getString(3));
   			activo.setNombre(cursor.getString(4));
   			activo.setIdTransaccion(cursor.getLong(5));
			activo.setIdDependencia(cursor.getLong(6));
			activo.setContador(cursor.getInt(7));
   			
   		 }while(cursor.moveToNext());
   	 }
   	 db.close();
	     return activo;
		 
	}

}
