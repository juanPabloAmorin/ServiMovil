package com.ucab.servimovil;


import java.io.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.*;
import com.ucab.servimovil.R;
import com.ucab.servimovil.model.LDAPObject;
import com.ucab.servimovil.model.User;
import com.ucab.servimovil.model.UserService;
import com.ucab.servimovil.utils.Utils;
import com.ucab.servimovil.web.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	public String usuario;
	public String pass;
	Activity activity = this;
	ProgressDialog ringProgressDialog;
	LDAPObject ldapObject = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(User.actualUser != 0){
			grantAcces();
		}else{
			 mainActivity();	
		}
			 
			
	}

	
	public void mainActivity(){
		 
		setContentView(R.layout.activity_main);
		
		final EditText userText = (EditText) findViewById(R.id.editUsername);
		final EditText passText = (EditText) findViewById(R.id.editPass);
		
		userText.setText("");
		passText.setText("");
		
		Button sendButton = (Button) findViewById(R.id.sendButton);
		
		sendButton.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ldapObject = null;
				usuario = userText.getText().toString().trim();
				pass = passText.getText().toString().trim();
				startAutentication();
			
			
			} 
		});
		
		
	}
	
	public void startAutentication(){
		
		if(HTTPConnection.verificaConexion(this) == true){
			
			new HTTPRequest().execute();
		     
		}else{
			Toast.makeText(this,"Comprueba tu conexión a Internet para continuar. ", Toast.LENGTH_SHORT)
	       .show();
		}
	}
	
	 public String userAutentication(){
	 	    
		    InputStream inputStream = null;
		    String result = "";
		    
		    try{
		    	
		    	
		    	HttpParams httpParams = new BasicHttpParams();
	            HttpConnectionParams.setConnectionTimeout(httpParams,HTTPConnection.TIME_OUT_CONNECTION);
	            HttpConnectionParams.setSoTimeout(httpParams,HTTPConnection.TIME_OUT_SOCKET);
	            HttpClient httpclient = new DefaultHttpClient(httpParams);
	            HttpGet httpGet = new HttpGet("http://200.2.15.197/ucabandroidestu/AutLDAP?user="+usuario+"&pass="+pass);
	            httpGet.setHeader("charset","utf-8");
	            HttpResponse httpResponse = httpclient.execute(httpGet);
	            inputStream = httpResponse.getEntity().getContent();
	            if(inputStream != null)
	            {
	            	 String resultado = Utils.convertInputStreamToString(inputStream);
	            	 LDAPObject ldapObject = JSONParser.parseLdapObject(resultado);
	            	 if(ldapObject != null){
	            		 this.ldapObject = ldapObject;
	            		 if(ldapObject.getResult().equals("true")){

	            			 //TODO temporal
	            			 usuario = "erpena";
		    	
	            			 try{
						    	      httpclient = new DefaultHttpClient(httpParams);
						    	      httpGet = new HttpGet(Syncronizer.server + "/api/Usuario/"+usuario+"/");
						    	      httpGet.setHeader("Accept", "application/json");
									  httpGet.setHeader("charset","utf-8");
							          httpResponse = httpclient.execute(httpGet);
							          inputStream = httpResponse.getEntity().getContent();
						    							    	
							          if(inputStream != null){
							        	  
							        	 
							        	      String serviResult = Utils.convertInputStreamToString(inputStream);
										      if(!serviResult.trim().equals("null")){
											    	UserService user = JSONParser.parseUserService(serviResult);
											    	
											    	if(user != null){
											    		
											    		if(user.getIdUsuario() != 0){
										
															    httpclient = new DefaultHttpClient(httpParams);
															    httpGet = new HttpGet(Syncronizer.server+"/api/User/"+user.getIdUsuario());
															    httpGet.setHeader("Accept", "application/json");
															    httpGet.setHeader("charset","utf-8");
													            httpResponse = httpclient.execute(httpGet);
													            inputStream = httpResponse.getEntity().getContent();
													            
													            if(inputStream != null)
													            {
													               resultado = Utils.convertInputStreamToString(inputStream);
													  
													               if(resultado.trim().equals("null")){
													            	  
													            	   String jsonUser = "{\"ID_USUARIO\":\""+user.getIdUsuario()+"\",\"NOMBRE\":\""+user.getNombre()+"\",\"LOGIN\":\""+user.getLogin()+"\"}";										            	  				
													            	   Log.e("user",jsonUser);
													            	   
																	   httpclient = new DefaultHttpClient(httpParams);
													       		       HttpPost httpPost = new HttpPost(Syncronizer.server+"/api/User");
													       		       StringEntity se = new StringEntity(jsonUser,"UTF-8");
													       		       httpPost.setEntity(se);
													       		       httpPost.setHeader("Accept", "application/json");
													                   httpPost.setHeader("Content-type", "application/json");
													                   httpPost.setHeader("charset","UTF-8");
													                   httpResponse = httpclient.execute(httpPost);
													                   inputStream = httpResponse.getEntity().getContent();
													                   
													                   
													                   
													                   if(inputStream != null)
															            {
													                         resultado = Utils.convertInputStreamToString(inputStream);
													                         result = resultado;
													                         
													                         Log.e("inpu",resultado);
													                         
													                      
															            }else{
															            	 result = "error";
															            }
															           
													            	   
													               }else{
													            	   return resultado;
													               }
													                
													            }
													            else{
													            	result = "error";
													            }
													            
											    	   }else{
											    		   result = "NO2";
											    	   }
													                
											    	}else{
											    		result = "error";
											    	}
									    	
									    	
										      }else{
										    	  result = "error";
										      }
									    	
								    	}else{
								    		result = "NO2";
								    	}
								    	
	            			 }catch(Exception e){
	            				 
	            				 Log.e("http exception","Ocurrio un error durente conexion servidesk");
	            				 result = "error";
	            			 }
								    	
	            		 }else{
	            			 result = "NO1";
	            		 }
	            	 }else{
	            		 result = "error";
	            	 }
	            }else{
	            	result = "error";
	            }

		    
		    }catch(Exception e){
		    	Log.e("error post","Ocurrio un error durante la conexión");
		    	result = "error";
		    }
		 	 
		 	 return result;
		     
	   }
	 
	
	 private class HTTPRequest extends AsyncTask<Void,Void,String> {

			
		    @Override
		    protected void onPreExecute() {
		        ringProgressDialog = ProgressDialog.show(MainActivity.this,"Espera un momento...","Verificando datos de usuario...", true);

		    }

		    @Override
		    protected String doInBackground(Void... voids) {
		    	
		    	 try{
		             Thread.sleep(1300);
		          }catch(Exception e){
		          	 Log.e("dialog exception","error en ring dialog " +e.toString());
		          }
		        return userAutentication();
		    }

		    @Override
		    protected void onPostExecute(String result) {
		   
		    	
				ringProgressDialog.dismiss();
		    	if(result.equals("error")){
		    		Toast.makeText(activity,"Ha ocurrido un error durante la autenticación. Verifique su conexión a internet.",Toast.LENGTH_LONG).show();
		    	
		    	}else if(result.equals("NO2")){
		    		Toast.makeText(activity,"No esta autorizado para utilizar esta aplicación.",Toast.LENGTH_LONG).show();
		    	
		    	}else if(result.equals("NO1")){
		    		Toast.makeText(activity,ldapObject.getMessage(),Toast.LENGTH_LONG).show();
		    	
		    	}else{
		    		
		    		 User actualUser = JSONParser.parseUser(result);
		    		 
		    		 if(actualUser != null){
		    			 
		    			 User.actualUser = actualUser.getIdUsuario();
		    			 grantAcces();
		    			 
		    		 }else{
				         Toast.makeText(activity,"Ha ocurrido un error durante la autenticación. Verifique su conexión a internet.",Toast.LENGTH_LONG).show();

		    		 }
		    	}

		    }
		}
	
	 
	public void grantAcces(){
		
		final EditText userText = (EditText) findViewById(R.id.editUsername);
		final EditText passText = (EditText) findViewById(R.id.editPass);
		
		userText.setText("");
		passText.setText("");
		
		Intent intent = new Intent(MainActivity.this,OptionsActivity.class);
		startActivity(intent);
	}
	
	 
}
