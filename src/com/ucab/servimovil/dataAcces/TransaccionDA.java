package com.ucab.servimovil.dataAcces;

import java.util.ArrayList;
import java.util.List;

import com.ucab.servimovil.R;
import com.ucab.servimovil.bbdd.DataBase;
import com.ucab.servimovil.model.LocalTransaction;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TransaccionDA {
	

	public List<LocalTransaction> getOutLocalTransactionsByUser(Activity activity,long idUsuario){
		
		   List<LocalTransaction> transactionList = new ArrayList<LocalTransaction>();
		   LocalTransaction transaccion;
		   String query = "SELECT * FROM TRANSACCION WHERE TIPO_TRANSACCION = 'S' AND STATUS = 'ACTIVE' AND ID_USUARIO = "+idUsuario;
		   DataBase ddbb = new DataBase(activity,activity.getResources().getString(R.string.db_name),null,DataBase.version);
	       
		   SQLiteDatabase dataBase = ddbb.getReadableDatabase();
		   
		   Cursor cursor = dataBase.rawQuery(query,null);
		   
		   if(cursor.moveToFirst()){
			    do{
			    	transaccion = new LocalTransaction();
			    	
			    	transaccion.setIdTransaccion(cursor.getLong(0));
			    	transaccion.setTipoTransaccion(cursor.getString(2));
			    	transaccion.setIdUsuario(cursor.getLong(3));
			    	transaccion.setIdReporte(cursor.getLong(4));
			    	transaccion.setObservaciones(cursor.getString(5));
			    	transaccion.setIdToner(cursor.getLong(6));
			    	transaccion.setStatus(cursor.getString(7));
			    	transaccion.setDependencia(cursor.getString(8));
			    	transaccion.setSolicitante(cursor.getString(9));
			    	transaccion.setIdImpresora(cursor.getLong(10));
			    	transaccion.setModeloImpresora(cursor.getString(11));
			    	transaccion.setSerialToner(cursor.getString(12));
			    	transaccion.setModeloToner(cursor.getString(13));
			    	transaccion.setServerTransaction(cursor.getLong(14));
			    	transaccion.setDescripcion(cursor.getString(15));

			    	
			    	transactionList.add(transaccion);
			    	transaccion = null;
			    	
			    }while(cursor.moveToNext());
		   }
		   
		   dataBase.close();
		   return transactionList;
	}
	
	public long insertTransaccion(LocalTransaction transaccion,Activity activity){
		
		 DataBase ddbb = new DataBase(activity,activity.getResources().getString(R.string.db_name),null,DataBase.version);
			SQLiteDatabase db = ddbb.getWritableDatabase();
			
			ContentValues registro = new ContentValues();
	
		     registro.put("DEPENDENCIA",transaccion.getDependencia());
		     registro.put("ID_IMPRESORA",transaccion.getIdImpresora());
		     registro.put("ID_REPORTE",transaccion.getIdReporte());
		     registro.put("ID_TONER",transaccion.getIdToner());
		     registro.put("ID_USUARIO",transaccion.getIdUsuario());
		     registro.put("MODELO_IMPRESORA",transaccion.getModeloImpresora());
		     registro.put("MODELO_TONER",transaccion.getModeloToner());
		     registro.put("OBSERVACIONES",transaccion.getObservaciones());
		     registro.put("SERIAL_TONER",transaccion.getSerialToner());
		     registro.put("SOLICITANTE",transaccion.getSolicitante());
		     registro.put("STATUS",transaccion.getStatus());
		     registro.put("TIPO_TRANSACCION",transaccion.getTipoTransaccion());
		     registro.put("DESCRIPCION",transaccion.getDescripcion());
		     registro.put("SERVER_TRANSACTION",transaccion.getServerTransaction());
		     registro.put("ID_ACTIVO",transaccion.getIdActivo());
		     registro.put("IS_SOLVED",transaccion.getIsSolved());
		     registro.put("SOLUCION",transaccion.getSolucion());


	    	 return db.insert("TRANSACCION",null,registro);
	   	   
	   	    
	
	}
	
	public void deleteTransaccion(long idTransaccion,Activity activity){
		 DataBase ddbb = new DataBase(activity,activity.getResources().getString(R.string.db_name),null,DataBase.version);
		 SQLiteDatabase db = ddbb.getWritableDatabase();
		
		db.execSQL("PRAGMA foreign_keys = ON;"); 
		db.delete("TRANSACCION", "ID_TRANSACCION = "+idTransaccion, null);
	}
	
	public long updateTransaccion(LocalTransaction transaccion,Activity activity){
		
		 DataBase ddbb = new DataBase(activity,activity.getResources().getString(R.string.db_name),null,DataBase.version);
			SQLiteDatabase db = ddbb.getWritableDatabase();
			
			ContentValues registro = new ContentValues();
	
		     registro.put("DEPENDENCIA",transaccion.getDependencia());
		     registro.put("ID_IMPRESORA",transaccion.getIdImpresora());
		     registro.put("ID_REPORTE",transaccion.getIdReporte());
		     registro.put("ID_TONER",transaccion.getIdToner());
		     registro.put("ID_USUARIO",transaccion.getIdUsuario());
		     registro.put("MODELO_IMPRESORA",transaccion.getModeloImpresora());
		     registro.put("MODELO_TONER",transaccion.getModeloToner());
		     registro.put("OBSERVACIONES",transaccion.getObservaciones());
		     registro.put("SERIAL_TONER",transaccion.getSerialToner());
		     registro.put("SOLICITANTE",transaccion.getSolicitante());
		     registro.put("STATUS",transaccion.getStatus());
		     registro.put("TIPO_TRANSACCION",transaccion.getTipoTransaccion());
		     registro.put("DESCRIPCION",transaccion.getDescripcion());	     
		     registro.put("CONTADOR",transaccion.getContador());
		     registro.put("FIRMA",transaccion.getFirma());
		     registro.put("RECEPTOR",transaccion.getReceptor());
		     registro.put("SERVER_TRANSACTION",transaccion.getServerTransaction());
		     registro.put("ID_ACTIVO",transaccion.getIdActivo());
		     registro.put("IS_SOLVED",transaccion.getIsSolved());
		     registro.put("SOLUCION",transaccion.getSolucion());

	    	 return db.update("TRANSACCION",registro,"ID_TRANSACCION = "+transaccion.getIdTransaccion(),null);
	   	   
	   	    
	
	}
	
	
	public List<LocalTransaction> getEndLocalTransactions(Activity activity){
		
		   List<LocalTransaction> transactionList = new ArrayList<LocalTransaction>();
		   LocalTransaction transaccion;
		   String query = "SELECT * FROM TRANSACCION WHERE TIPO_TRANSACCION != 'S'";
		   DataBase ddbb = new DataBase(activity,activity.getResources().getString(R.string.db_name),null,DataBase.version);
	       
		   SQLiteDatabase dataBase = ddbb.getReadableDatabase();
		   
		   Cursor cursor = dataBase.rawQuery(query,null);
		   
		   if(cursor.moveToFirst()){
			    do{
			    	transaccion = new LocalTransaction();
			    	

	
			    	transaccion.setIdTransaccion(cursor.getLong(0));
			    	transaccion.setTipoTransaccion(cursor.getString(2));
			    	transaccion.setIdUsuario(cursor.getLong(3));
			    	transaccion.setIdReporte(cursor.getLong(4));
			    	transaccion.setObservaciones(cursor.getString(5));
			    	transaccion.setIdToner(cursor.getLong(6));
			    	transaccion.setStatus(cursor.getString(7));
			    	transaccion.setDependencia(cursor.getString(8));
			    	transaccion.setSolicitante(cursor.getString(9));
			    	transaccion.setIdImpresora(cursor.getLong(10));
			    	transaccion.setModeloImpresora(cursor.getString(11));
			    	transaccion.setSerialToner(cursor.getString(12));
			    	transaccion.setModeloToner(cursor.getString(13));
			    	transaccion.setServerTransaction(cursor.getLong(14));		    	
			    	transaccion.setDescripcion(cursor.getString(15));
			    	transaccion.setReceptor(cursor.getString(16));
			    	transaccion.setContador(cursor.getInt(17));
			    	transaccion.setFirma(cursor.getBlob(18));
			    	transaccion.setIdActivo(cursor.getLong(19));			    	
			    	transaccion.setIsSolved(cursor.getString(20));
			    	transaccion.setSolucion(cursor.getString(21));
			    	
			    	transactionList.add(transaccion);
			    	transaccion = null;
			    	
			    }while(cursor.moveToNext());
		   }
		   
		   dataBase.close();
		   return transactionList;
	}
	
	

	public int getLocalTransactionCount(Activity activity){
		
		   String query = "SELECT COUNT(*) FROM TRANSACCION";
		   DataBase ddbb = new DataBase(activity,activity.getResources().getString(R.string.db_name),null,DataBase.version);
	       
		   SQLiteDatabase dataBase = ddbb.getReadableDatabase();
		   
		   Cursor cursor = dataBase.rawQuery(query,null);
		   
		   int count = 0;
		   
		   if(cursor.moveToFirst()){

			   count = cursor.getInt(0);			 
		   }
		   
		   dataBase.close();
		   return count;
	}
}
