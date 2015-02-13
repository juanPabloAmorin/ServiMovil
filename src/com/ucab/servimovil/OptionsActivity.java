package com.ucab.servimovil;


import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.ucab.servimovil.R;
import com.ucab.servimovil.adapters.OptionsViewPagerAdapter;
import com.ucab.servimovil.dataAcces.ActivoDA;
import com.ucab.servimovil.dataAcces.TransaccionDA;
import com.ucab.servimovil.model.*;
import com.ucab.servimovil.utils.Utils;
import com.ucab.servimovil.web.*;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteException;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.Toast;

public class OptionsActivity extends FragmentActivity implements ActionBar.TabListener {


	private CharSequence tituloSec;
	List<LocalTransaction> listTransaction;

	ProgressDialog ringProgressDialog;

	private ViewPager viewPager;
	private OptionsViewPagerAdapter myAdapter;
	private ActionBar actionBar;

	private String[] stringTabs = { "Registro", "Solicitudes", "Entrega" };
	   	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setSoftInputMode(
			    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
			);
		
		setContentView(R.layout.activity_options);

		getOverflowMenu();

		viewPager = (ViewPager) findViewById(R.id.options_layout);

		actionBar = getActionBar();
		myAdapter = new OptionsViewPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(myAdapter);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		for (String tab_name : stringTabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {

				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
			
			
		}); 

		tituloSec = getResources().getString(R.string.app_name);
		actionBar.setTitle(tituloSec);
		
		TransaccionDA transactionda = new TransaccionDA();
		int count = 0;
			count =	transactionda.getLocalTransactionCount(this);
		
		if(count <= 0){
			Utils.generateSyncNotification(OptionsActivity.this,String.valueOf(count));
		}
	
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	} 
	
	private void getOverflowMenu() {

	     try {
	        ViewConfiguration config = ViewConfiguration.get(this);
	        Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
	        if(menuKeyField != null) {
	            menuKeyField.setAccessible(true);
	            menuKeyField.setBoolean(config, false);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if(item.getItemId() == R.id.action_sync){

			try {
				listTransaction = new ArrayList<LocalTransaction>();
				TransaccionDA transaccionda = new TransaccionDA();
				listTransaction = transaccionda.getEndLocalTransactions(this);

				if (listTransaction.size() == 0) {
					Utils.alertMessage("En este momento no hay transacciones por sincronizar.",this);
				} else {

					if (HTTPConnection.verificaConexion(this)) {
						new HTTPRequest().execute();

					} else {
						Toast.makeText(
								this,
								"No dispone de conexión a internet.Verifique su conexión.",
								Toast.LENGTH_LONG).show();
					}
				}
				return true;
		
		}catch(SQLiteException e){
			
			Utils.alertMessage("No es posible establecer conexión con la base de datos",this);
			Log.e("sqlite exception", "Error getting data " + e.toString());
		}
			
		}else if(item.getItemId() == R.id.action_logout){
			onBackPressed();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	   
	}
	
	private class HTTPRequest extends AsyncTask<Void,Void,String> {

		
	    @Override
	    protected void onPreExecute() {
            ringProgressDialog = ProgressDialog.show(OptionsActivity.this,"Espera un momento...","Sincronizando...", true);

	    }

	    @Override
	    protected String doInBackground(Void... voids) {
	    	  
            try{
                   Thread.sleep(2000);
                }catch(Exception e){
                	 Log.e("dialog exception","error en ring dialog " +e.toString());
                }
        
	        	return syncData();	      
	    }

	    @Override
	    protected void onPostExecute(String result) {
	    	
	    	  ringProgressDialog.dismiss();
	    	  
	    	  if(result.equals("error")){
	    		  Utils.alertMessage("Ha ocurrido un error y no fue posible completar la sincronización.",OptionsActivity.this); 
	    		  TransaccionDA transactionda = new TransaccionDA();
	    		   int count = transactionda.getLocalTransactionCount(OptionsActivity.this);
	    			
	    		   if(count != 0){
	    				Utils.generateSyncNotification(OptionsActivity.this,String.valueOf(count));
	    		   }
	    	  }else if(result.equals("ok")){
	    		  Utils.alertMessage("La sincronización fue completada con éxito.",OptionsActivity.this);
                  Utils.cancelNotifications(OptionsActivity.this);
	    	  }
	    }
	    
 }
	
	public String syncData(){
		
		 
		   InputStream inputStream = null;
		   String result = "";
		   
		   try{
			   
			    Activo activo= new Activo();
			   
		    	for(int i=0;i<listTransaction.size();i++){
		    		 
		    		LocalTransaction localTrans = listTransaction.get(i);
		    		String jsonString = "";
		    				    		
		    		ActivoDA activoda = new ActivoDA();	
		    		activo = activoda.getActivoById(localTrans.getIdActivo(),this);
		    				    		
		    		String firma64 = "";
		    		
		    		if(localTrans.getTipoTransaccion().equals("E")){
		    			
		    			firma64 = Base64.encodeToString(localTrans.getFirma(),Base64.DEFAULT);
		    		}
		    				
		
		    		jsonString = jsonString + "[{\"FECHA_TRANSACCION\":\"null\",\"TIPO_TRANSACCION\":\""+localTrans.getTipoTransaccion()+"\",\"ID_USUARIO\":"+localTrans.getIdUsuario()+"," +
		    				                  "\"ID_REPORTE\":"+localTrans.getIdReporte()+",\"OBSERVACIONES\":\""+localTrans.getObservaciones()+"\",\"ID_TONER\":"+localTrans.getIdToner()+"," +
		    						          "\"STATUS\":\""+localTrans.getStatus()+"\",\"FIRMA\":null,\"RECEPTOR\":\""+localTrans.getReceptor()+"\"," +
		    						          "\"PREV_TRANSACTION\":"+localTrans.getServerTransaction()+"," +
		    						          "\"ID_ACTIVO\":"+activo.getRealId()+",\"ACTIVO_FIJO\":{\"ID_ACTIVO\":"+activo.getRealId()+",\"SERIAL\":\""+activo.getSerial()+"\"," +
		    						          "\"NUMERO\":\""+activo.getNumero()+"\",\"ID_DEPENDENCIA\":"+activo.getIdDependencia()+",\"CONTADOR\":"+localTrans.getContador()+"},\"REPORTE\":{\"ID_REPORTE\":"+localTrans.getIdReporte()+"," +
		    						          "\"FECHA_SOLICITUD\":null,\"SOLICITANTE\":\""+localTrans.getSolicitante()+"\",\"DESCRIPCION\":\""+localTrans.getDescripcion()+"\"," +
		    						          "\"ID_DEPENDENCIA\":"+activo.getIdDependencia()+"},\"TONER\":{\"ID_TONER\":"+localTrans.getIdToner()+",\"ID_MODELO_IMPRESORA\":"+localTrans.getIdImpresora()+",\"CONTADOR_INICIAL\":"+localTrans.getContador()+"}," +
		    						           "\"USUARIO\":null,\"IS_SOLVED\":\""+localTrans.getIsSolved()+"\",\"SOLUCION\":\""+localTrans.getSolucion()+"\",\"stringBase64\":\""+firma64+"\"}]";

		    		Log.e("sync data tag",jsonString);
		    		
		    	    	HttpParams httpParams = new BasicHttpParams();
		                HttpConnectionParams.setConnectionTimeout(httpParams,HTTPConnection.TIME_OUT_CONNECTION);
		                HttpConnectionParams.setSoTimeout(httpParams,HTTPConnection.TIME_OUT_SOCKET);
			    	    HttpClient httpclient = new DefaultHttpClient(httpParams);
					    HttpPost httpPost = new HttpPost(Syncronizer.server+"/api/Transactions");
					    StringEntity se = new StringEntity(jsonString,"UTF-8");
					    httpPost.setEntity(se);
					    httpPost.setHeader("Accept", "application/json");
			            httpPost.setHeader("Content-type", "application/json");
			            httpPost.setHeader("charset","utf-8");
			            HttpResponse httpResponse = httpclient.execute(httpPost);
			            inputStream = httpResponse.getEntity().getContent();
			            
			            if(inputStream != null){
			            	result = "ok";
			                String prevResult = Utils.convertInputStreamToString(inputStream);
			                
			                List<Transaccion> list = JSONParser.parseTransaccion(prevResult);
			                
			                if(list != null && list.size() > 0){
			                	try{
			                		TransaccionDA transda = new TransaccionDA();
			                		transda.deleteTransaccion(localTrans.getIdTransaccion(), this);
			                	}catch(SQLiteException e){
			                		Log.e("SQLite error", "error de conexion con bd");
			                		result = "error";
			                		break;
			                	}
			                	
			                }else{
			                	 Log.e("parse error","Error en resultado de parse");
			                	 result = "error";
			                	 break;
			                }
			            }
			            else{
			            	result = "error";
			            	break;
			            }
			                
		    	}
		    	
		    	
		   
		   }catch(Exception e){
			   Log.e("error post","Error en envio de mensaje POST" + e.toString());
			   result = "error";
		   }
		
		   return result;
	}
	
	
	@Override
	public void onBackPressed() {
	    new AlertDialog.Builder(this)
	        .setTitle("Salir de la Aplicación")
	        .setMessage("¿Realmente desea salir de la aplicación?")
	        .setNegativeButton(android.R.string.no, null)
	        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int id) {
		        	User.actualUser = 0;
					finish();
				
		        }
		    }).create().show();
	}


	


	@Override
	public void onTabReselected(Tab tab, FragmentTransaction arg1) {
		
		android.support.v4.app.FragmentTransaction fft = this.getSupportFragmentManager().beginTransaction();

		 if(tab.getPosition() == 0){
			 fft.replace(R.id.registro_layout,myAdapter.getItem(0));
			 fft.commit();  
			  
		 }else if(tab.getPosition() == 1){
			 fft.replace(R.id.solicitudesLayout,myAdapter.getItem(1));
	       	 fft.commit();
		 }else{
			 fft.replace(R.id.entregasLayout,myAdapter.getItem(2));
		    fft.commit(); 
		 }
		
	}




	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		 viewPager.setCurrentItem(tab.getPosition());
		
	}




	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
}
