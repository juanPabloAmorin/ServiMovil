package com.ucab.servimovil.bbdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {
	
	public final static int version = 1;
	
	private String createTransaccion = "CREATE TABLE TRANSACCION(ID_TRANSACCION INTEGER PRIMARY KEY AUTOINCREMENT, FECHA_TRANSACCION TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,"+
                                       "TIPO_TRANSACCION TEXT NOT NULL,ID_USUARIO INTEGER NOT NULL,ID_REPORTE INTEGER, OBSERVACIONES TEXT,"+
			                           "ID_TONER INTEGER NOT NULL, STATUS TEXT,DEPENDENCIA TEXT,SOLICITANTE TEXT,ID_IMPRESORA INTEGER,MODELO_IMPRESORA TEXT,SERIAL_TONER TEXT,MODELO_TONER TEXT,SERVER_TRANSACTION INTEGER,DESCRIPCION TEXT," +
			                           "RECEPTOR TEXT, CONTADOR INTEGER,FIRMA BLOB,ID_ACTIVO INTEGER,IS_SOLVED TEXT,SOLUCION TEXT)";
	
	private String createActive = "CREATE TABLE ACTIVO(ID_ACTIVO INTEGER PRIMARY KEY AUTOINCREMENT,REAL_ID INTEGER NOT NULL,SERIAL TEXT,NUMERO INTEGER,NOMBRE NOT NULL," +
			                       "ID_TRANSACCION INTEGER NOT NULL,ID_DEPENDENCIA INTEGER,CONTADOR INTEGER," +
			                        "FOREIGN KEY(ID_TRANSACCION) REFERENCES TRANSACCION(ID_TRANSACCION) ON DELETE CASCADE)";
	

	private String createTonerTempString = "CREATE TABLE TONER_TEMP( ID_TONER INTEGER PRIMARY KEY AUTOINCREMENT,SERIAL TEXT NOT NULL,"+
            " MODELO TEXT NOT NULL)";
	
	public DataBase(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	
	    db.execSQL(createTonerTempString); 
		db.execSQL(createTransaccion);
		db.execSQL(createActive);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		
	}

}
