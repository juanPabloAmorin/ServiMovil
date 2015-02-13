package com.ucab.servimovil;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import net.sourceforge.zbar.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.ucab.servimovil.R;
import com.ucab.servimovil.adapters.ActivoAdapter;
import com.ucab.servimovil.adapters.ModeloTonerAdapter;
import com.ucab.servimovil.dataAcces.*;
import com.ucab.servimovil.model.*;
import com.ucab.servimovil.utils.Utils;
import com.ucab.servimovil.web.*;

import android.app.Activity;
import android.app.AlertDialog;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

public class PlanillaSalidaFragment extends Fragment {
	
	boolean carga;
	boolean envio;
	boolean error;
	boolean verify;
	
	ProgressDialog ringProgressDialog;
	String dialogTitle = "Espere un momento...";
	String dialogMessage;
	
	String descripcion;
	String dependencia;
	String dependenciaDescripcion;
	String solicitante;
	String reporte;
	String fecha;
	String observaciones;
	long deptid;
	
	ReporteService reporteService;
	
	String tonerSerial;
	Toner toner;
	LocalTransaction transaction;
	
	long idLocalTransaction;
	
	Spinner modelspinner;
	Spinner cartuchoSpinner;
	
/*	private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;
    FrameLayout preview;
    ImageScanner scanner;
    @SuppressWarnings("unused")
	private boolean barcodeScanned = false;
    private boolean previewing = true;
    
    */
    
	/*
    static {
        System.loadLibrary("iconv");
    }  */

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
		
	   return inflater.inflate(R.layout.activity_planilla, container, false);
		
		
	}
	
	public void onActivityCreated(Bundle state){
		super.onActivityCreated(state);
		
		getActivity().getWindow().setSoftInputMode(
			    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
			);
		
		carga = false;
		envio = false;
		error = false;
		verify = false;
		
		makeThePage(null);
		
	} 
	
	
	/*public String pageLoad(){
		 
		InputStream inputStream = null;
		String result = "";
		
		try{
			HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams,HTTPConnection.TIME_OUT_CONNECTION);
            HttpConnectionParams.setSoTimeout(httpParams,HTTPConnection.TIME_OUT_SOCKET);
		    HttpClient httpclient = new DefaultHttpClient(httpParams);
		    HttpGet httpGet = new HttpGet(Syncronizer.server+"/api/ModelPrinter");
		    httpGet.setHeader("Accept", "application/json");
		    httpGet.setHeader("charset","utf-8");
            HttpResponse httpResponse = httpclient.execute(httpGet);
            inputStream = httpResponse.getEntity().getContent();
         
         if(inputStream != null)
         {
              result = Utils.convertInputStreamToString(inputStream);
              
         }else{
         	 result = "error";
         }
		
		}catch(Exception e){
			Log.e("HTTP error","Error getting printer models");
			result = "error";
		}
		
		return result;
	} */
	
	public String verifyToner(){
		
		InputStream inputStream = null;
		String result = "";
		
		try{
			HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams,HTTPConnection.TIME_OUT_CONNECTION);
            HttpConnectionParams.setSoTimeout(httpParams,HTTPConnection.TIME_OUT_SOCKET);
		    HttpClient httpclient = new DefaultHttpClient(httpParams);
		    HttpGet httpGet = new HttpGet(Syncronizer.server+"/api/Toner/"+tonerSerial);
		    httpGet.setHeader("Accept", "application/json");
		    httpGet.setHeader("charset","utf-8");
            HttpResponse httpResponse = httpclient.execute(httpGet);
            inputStream = httpResponse.getEntity().getContent();
         
         if(inputStream != null)
         {
              result = Utils.convertInputStreamToString(inputStream);
              
              
         }else{
         	 result = "error";
         }
		
		}catch(Exception e){
			Log.e("HTTP error","Error getting toners");
			result = "error";
		}
		
		return result;
		
		
	}
	
	public void alertMessageSalida(String message){
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
		alertBuilder.setMessage(message);
		alertBuilder.setCancelable(true);
		alertBuilder.setPositiveButton("Ok",
	            new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int id) {
	            dialog.cancel();
	            SalidaFragment salidaFragment = new SalidaFragment();
	         	 FragmentTransaction transaction = getFragmentManager().beginTransaction();
	       	    transaction.replace(R.id.solicitudesLayout, salidaFragment);
	       	    transaction.commit();
	        }
	    });
	   
	    alertBuilder.show();
	}
	
	
	 private class HTTPRequest extends AsyncTask<Void,Void,String> {

			
		    @Override
		    protected void onPreExecute() {
		        ringProgressDialog = ProgressDialog.show(getActivity(),dialogTitle,dialogMessage, true);

		    }

		    @Override
		    protected String doInBackground(Void... voids) {
		    	
		    	try{
		             Thread.sleep(2000);
		          }catch(Exception e){
		          	 Log.e("dialog exception","error en ring dialog " +e.toString());
		          }
		        if(carga){
		        //	return pageLoad();
		        
		        }else if(verify){
		        	
		        	toner = null;
		        	return verifyToner();
		        
		        }else if(envio && !verify){
		        	
		        	return sendTransactionsInfo();
		        }
		        return "";
		    }

		    @Override
		    protected void onPostExecute(String result) {
		    	
		    	ringProgressDialog.dismiss();
		    	
		    	if(result.equals("error") || result.equals("")){
		    		Toast.makeText(getActivity(),"Ocurrio un error durante la conexión.",Toast.LENGTH_LONG).show();
		    		envio = false;
 				    verify = false;
		    		error = true;
		    	
		    	}else{
		    		
		    		if(carga){
			    		List<ModeloImpresora> listModel = JSONParser.pasePrinterModel(result);
			    		
			    		
			    		if(listModel != null && !listModel.isEmpty()){
			    			
			    			ModeloImpresora defaultModel = new ModeloImpresora();
			    			defaultModel.setIdModelo(0);
			    			defaultModel.setDescripcion(getActivity().getResources().getString(R.string.spinner_default));
			    			listModel.add(0,defaultModel); 
			    			makeThePage(listModel); 
			    			carga = false;
			    			
			    		}else{
			    			Toast.makeText(getActivity(),"Ocurrio un error durante la conexión.",Toast.LENGTH_LONG).show();
				    		error = true;
				    		envio = false;
		    				verify = false;
			    		} 
		    		
		    		}else if(verify){
		    			
		    			if(!result.equals("") && !result.equals("error")){
		    			   if(!result.equals("null")){
		    			       Toner tonerGet = JSONParser.parseToner(result);
		    			       String modelTonerSelected = cartuchoSpinner.getSelectedItem().toString();
		    			       		    			       
		    			       if(!tonerGet.getStatus().equals(getActivity().getResources().getString(R.string.toner_status_inventario))){
		    			    	  
		    			    	   Utils.alertMessage("No es posible asignar el cartucho a la solicitud. El estado actual del cartucho es de "+ tonerGet.getStatus(),getActivity());
		    			    	   envio = false;
			    				   verify = false;
		    			    	   EditText tonerText = (EditText) getView().findViewById(R.id.tonerEditText);
			    				   tonerText.setText("");
		    			       }else if(!tonerGet.getModeloToner().getDescripcion().equals(modelTonerSelected)){
		    			    	   
		    			    	   Utils.alertMessage("No es posible asignar el cartucho a la solicitud. El modelo seleccionado para la impresora no coincide con el modelo del cartucho ("+tonerGet.getModeloToner().getDescripcion()+")",getActivity());
		    			    	   envio = false;
			    				   verify = false;
		    			    	   EditText tonerText = (EditText) getView().findViewById(R.id.tonerEditText);
			    				   tonerText.setText(""); 
		    			       }
		    			       else{
		    			       
		    			    	   setSerialToText();
		    			    	   toner = tonerGet;
		    			    	   
		    			    	   if(envio){
		    			    		   verify = false;
		    			    		   transactionRegistration();
		    			    	   }
		    			       }
		    			   
		    			   }else{
		    				   Utils.alertMessage("El cartucho " + tonerSerial +" no se encuentra registrado en inventario.",getActivity());
		    				   envio = false;
		    				   verify = false;
		    				   EditText tonerText = (EditText) getView().findViewById(R.id.tonerEditText);
		    				   tonerText.setText("");
		    			   }
		    			
		    			}else{
		    				
			    			 Toast.makeText(getActivity(),"Ocurrio un error durante la conexión.",Toast.LENGTH_LONG).show();
			    			 envio = false;
		    				 verify = false;
			    			 EditText tonerText = (EditText) getView().findViewById(R.id.tonerEditText);
		    				 tonerText.setText("");

		    			}
		    	
		    		}else if(envio && !verify){

		    			 if(!result.equals("")){
		    				 
		    				 List<Transaccion> listTrans = JSONParser.parseTransaccion(result);
		    				 if(listTrans != null){
		    					 
		    					 try{
		    						 transaction.setServerTransaction(listTrans.get(0).getTransaccionId());	    						 		    						 
		    						 TransaccionDA transDa = new TransaccionDA();
		    						 transDa.updateTransaccion(transaction,getActivity());
			    					 alertMessageSalida("Se ha asignado el cartucho "+toner.getSerial()+" al la solicitud Nro. "+reporte);
			    					 
			    					
		    					 }catch(SQLiteException e){
		    						 Log.e("SQLite update","Ocurrio un error en update de transaccion local = update transaction error.");
		    						 Toast.makeText(getActivity(),"Ocurrio un error de conexion con la base de datos", Toast.LENGTH_LONG).show();
		    						 envio = false;
				    				 verify = false;
		    						 //TODO que hago si falla el update de la transaccion en local
		    						 
		    					 }
		    				 }else{
			    				 Toast.makeText(getActivity(),"Ocurrio un error durante el envio de los datos.", Toast.LENGTH_LONG).show();
                                 try{
                                	 TransaccionDA tranDa = new TransaccionDA();
                                	 tranDa.deleteTransaccion(idLocalTransaction,getActivity());
                                 }catch(SQLiteException e){
                                	 Log.e("SQLite update","Ocurrio un error en update de transaccion local = delete transaction error.");
                                	 envio = false;
      		    				     verify = false;
                                	 //TODO que hago si falla el del de la transaccion en local
                                 }
		    				 }
		    				 
		    			 }else{
		    				 
		    				 Toast.makeText(getActivity(),"Ocurrio un error durante el envio de los datos.", Toast.LENGTH_LONG).show();
		    				 envio = false;
		    				 verify = false;
		    				 Log.e("web error","El resultado es vacio");
		    				 try{
                            	 TransaccionDA tranDa = new TransaccionDA();
                            	 tranDa.deleteTransaccion(idLocalTransaction,getActivity());
                             }catch(SQLiteException e){
                            	 Log.e("SQLite update","Ocurrio un error en update de transaccion local = deletee transaction error.");
	    						 //TODO que hago si falla el del de la transaccion en local
                             }
		    			 }
		    			
		    		}
		    		
		    	
		    			
		    	}
		    	
		    }
		    
	 }
	 
	 public void makeThePage(List<ModeloImpresora> listModel){
		 
		 if(!error){
			 
		 /*   autoFocusHandler = new Handler();
	        mCamera = getCameraInstance();
	        scanner = new ImageScanner();
	        scanner.setConfig(0, Config.X_DENSITY, 3);
	        scanner.setConfig(0, Config.Y_DENSITY, 3); */
			 reporteService = new ReporteService();
		        reporteService = (ReporteService) getArguments().getSerializable("reporte");
		        
			    descripcion = reporteService.getDescripcion();
				dependencia = reporteService.getDependencia();
				dependenciaDescripcion = reporteService.getDependenciaDescripcion();
				solicitante = reporteService.getSolicitante();
				reporte = String.valueOf(reporteService.getIdReporte());
				
			    SimpleDateFormat  format = new SimpleDateFormat("dd-MM-yyyy"); 
				fecha = format.format(reporteService.getFechaSolicitud());
				deptid = reporteService.getDeptId();
				
				List<Activo> listActivos = new ArrayList<Activo>();
				
				 if(reporteService.getActivos().size() > 1){
						Activo defaultActivo = new Activo();
						defaultActivo.setRealId(0);
						defaultActivo.setNombre("SELECCIONE");
						defaultActivo.setModelString("SELECCIONE");
						listActivos.add(defaultActivo);
				}
				
				 for(int actNumber=0;actNumber<reporteService.getActivos().size();actNumber++){
					 
					 Activo activo = reporteService.getActivos().get(actNumber);
					// activo.setIdTransaccion(idLocalTransaction);
					 activo.setIdDependencia(deptid);
					 
					 listActivos.add(activo);
					 					 
				 }
	        
			
			EditText dependenciaText = (EditText) getView().findViewById(R.id.dependenciaEditText);
			dependenciaText.setText(dependencia);
			
			EditText solicitanteText = (EditText) getView().findViewById(R.id.solicitanteEditText);
			solicitanteText.setText(solicitante);

			EditText descDependenciaText = (EditText) getView().findViewById(R.id.descripcionDepEditText);
			descDependenciaText.setText(dependenciaDescripcion);
			
			EditText numeroText = (EditText) getView().findViewById(R.id.numeroEditText);
			numeroText.setText(reporte);
			
			EditText descripcionText = (EditText) getView().findViewById(R.id.solDescripcionEditText);
			descripcionText.setText(descripcion);
			
			EditText fechaText = (EditText) getView().findViewById(R.id.fechaEditText);
			fechaText.setText(fecha);
			
			cartuchoSpinner = (Spinner) getView().findViewById(R.id.cartuchoList);
			
			modelspinner = (Spinner) getView().findViewById(R.id.modelPrinterList);			
			ActivoAdapter activoAdapter = new ActivoAdapter(getActivity(),
					android.R.layout.simple_spinner_item,listActivos);		
			activoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
			modelspinner.setAdapter(activoAdapter); 
			
			modelspinner.setOnItemSelectedListener(new OnItemSelectedListener(){
				@Override
	            public void onItemSelected(AdapterView<?> arg0, View arg1,
	                    int arg2, long arg3) {
					 Activo activoSelected = (Activo) modelspinner.getSelectedItem();  
					 String modelString = activoSelected.getModelString();
					 
					 String cartuchos[] = modelString.split("/");
					 
					 List<String> modelosString = new ArrayList<String>(Arrays.asList(cartuchos));
					 
					 ModeloTonerAdapter cartuchoAdapter = new ModeloTonerAdapter(getActivity(),
								android.R.layout.simple_spinner_item,modelosString);
					 cartuchoSpinner.setAdapter(cartuchoAdapter);
	            }

	            @Override
	            public void onNothingSelected(AdapterView<?> arg0) {
	                // TODO Auto-generated method stub
	            }
			});
			
			Button scanButton = (Button) getView().findViewById(R.id.salidaScanButton);
			Button registerButton = (Button) getView().findViewById(R.id.salidaRegisterButton);
			
			scanButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
				/*	 mPreview = new CameraPreview(getActivity(), mCamera, previewCb, autoFocusCB);
			         preview = (FrameLayout) getView().findViewById(R.id.cameraPreviewSalida);
			         preview.addView(mPreview);  */
					
					try {

						Intent intent = new Intent("com.google.zxing.client.android.SCAN");
						intent.putExtra("SCAN_MODE", "QR_CODE_MODE,PRODUCT_MODE");
						startActivityForResult(intent, 0);

						} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(getActivity(), "ERROR:" + e, Toast.LENGTH_LONG).show();

						}
		
				}
			});
			
			registerButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					verify = true;
					envio = true;
					carga = false;
					transactionRegistration();

				}
			});
		}

	};
	 
	/*  public void onPause() {
	        super.onPause();
	        releaseCamera();
	    }
	 
	  public static Camera getCameraInstance(){
	        Camera c = null;
	        try {
	            c = Camera.open();
	        } catch (Exception e){
	        }
	        return c;
	    }

	    private void releaseCamera() {
	        if (mCamera != null) {
	            previewing = false;
	            mCamera.setPreviewCallback(null);
	            mCamera.release();
	            mCamera = null;
	        }
	    }

	    private Runnable doAutoFocus = new Runnable() {
	            public void run() {
	                if (previewing)
	                    mCamera.autoFocus(autoFocusCB);
	            }
	    };
	        
	   AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
	            public void onAutoFocus(boolean success, Camera camera) {
	                autoFocusHandler.postDelayed(doAutoFocus, 1000);
	            }
	        };
	       
	   
	   PreviewCallback previewCb = new PreviewCallback() {
	            public void onPreviewFrame(byte[] data, Camera camera) {
	                Camera.Parameters parameters = camera.getParameters();
	                Size size = parameters.getPreviewSize();

	                Image barcode = new Image(size.width, size.height, "Y800");
	                barcode.setData(data);

	                int result = scanner.scanImage(barcode);
	                
	                if (result != 0) {
	                    previewing = false;
	                    mCamera.setPreviewCallback(null);
	                    mCamera.stopPreview();
	                    
	                    SymbolSet syms = scanner.getResults();
	                    for (Symbol sym : syms) {
	                    	
	                    	String dataScanned = sym.getData();
	                    	tonerSerial = dataScanned;
	                    	
	                    }
	                                    		
	    					verifySerialToner(tonerSerial);
	                        barcodeScanned = true;
	                        
	                        preview.removeView(mPreview);
	                 }
	                    
	                    	                
	            }
	            
	        };
*/

	   public void verifySerialToner(String serial){
		   
		    tonerSerial = serial;
		    
		    if(HTTPConnection.verificaConexion(getActivity())){
		    	if(!verify || !envio)
		    	{
			    	carga = false;
			    	envio = false;
			    	verify = true;
		    	}
		    	dialogMessage = "Verificando serial...";
		    	new HTTPRequest().execute();
		    	
		    }else{
		    	
		    	Toast.makeText(getActivity(),"No es posible conectar a internet. Verifique la conexión.",Toast.LENGTH_LONG).show();
		    	envio = false;
				verify = false;
		    }
	   }
	
	   public void setSerialToText(){
		   
		   EditText tonerText = (EditText) getView().findViewById(R.id.tonerEditText);
		   tonerText.setText(tonerSerial);
	   }
	   
	   
	   public String sendTransactionsInfo(){
		   
		   InputStream inputStream = null;
		   String result = "";
		   
		   try{
			   
			 Spinner modelspinner = (Spinner) getView().findViewById(R.id.modelPrinterList);
			 Activo active = (Activo) modelspinner.getSelectedItem();  
			   			
			 String jsonString = "[{\"TONER\":{\"ID_TONER\":"+toner.getIdToner()+"," +
			 		"\"ID_MODELO_IMPRESORA\":"+toner.getModeloImpresora()+"}," +
			          "\"ID_ACTIVO\":"+active.getRealId()+",\"ACTIVO_FIJO\":{\"ID_ACTIVO\":"+active.getRealId()+",\"SERIAL\":\""+active.getSerial()+"\"," +
			          "\"NUMERO\":\""+active.getNumero()+"\",\"ID_DEPENDENCIA\":"+active.getIdDependencia()+",\"CONTADOR\":0}," +
			 		"\"REPORTE\":{\"ID_REPORTE\":"+reporte+",\"DESCRIPCION\":\""+descripcion+"\"," +
			 		"\"FECHA_SOLICITUD\":\""+fecha+"\"," +
			 		"\"SOLICITANTE\":\""+solicitante+"\",\"DEPENDENCIA\":{\"DESCRIPCION\":\""+dependencia+"\",\"ID_DEPENDENCIA\":"+deptid+"}},\"TIPO_TRANSACCION\":\"S\"," +
			 	    "\"ID_USUARIO\":"+User.actualUser+",\"OBSERVACIONES\":\""+observaciones+"\"," +
			 	    "\"ID_TONER\":"+toner.getIdToner()+",\"STATUS\":\"ACTIVE\"}]";
			 
			
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
	            
	            if(inputStream != null)
	                result = Utils.convertInputStreamToString(inputStream);
	            else
	                result = "";

		    
		    }catch(Exception e){
		    	Log.e("error post","Error en envio de mensaje POST");
		    }
		 	 
		 	 return result;
		   
	   }
	   
	   
	   public void transactionRegistration(){
		     EditText tonerText = (EditText) getView().findViewById(R.id.tonerEditText);
			 EditText observacionesText = (EditText) getView().findViewById(R.id.observacionesText);
			 
			 String serial = tonerText.getText().toString().trim();
			 observaciones = observacionesText.getText().toString().trim();
			 
			 Spinner modelspinner = (Spinner) getView().findViewById(R.id.modelPrinterList);
			 
			 Activo active = (Activo) modelspinner.getSelectedItem();
			
			 if(active.getRealId() != 0){
								 
				 if(serial != null && !serial.equals("")){
			 
					 if(toner != null && envio && !verify){
					     toner.setModeloImpresora(active.getRealId());
					     
						 
						 try{
							 //TODO VALIDAR QUE SE INSERTE TRANSACCION Y ACTIVOS
							 
							 LocalTransaction localTrans = new LocalTransaction();
							 localTrans.setDependencia(dependencia);
							 localTrans.setIdImpresora(active.getRealId());
							 localTrans.setIdReporte(Long.parseLong(reporte));
							 localTrans.setIdToner(toner.getIdToner());
							 localTrans.setIdUsuario(User.actualUser);
							 localTrans.setModeloImpresora(active.getNombre());
							 
							 try{
							     localTrans.setModeloToner(toner.getModeloToner().getDescripcion());
							 }catch(NullPointerException e){
								 localTrans.setModeloToner("null");
								 Log.e("Null Pointer Exception","Modelo toner  = null");
							 }
							 
							 localTrans.setObservaciones(observaciones);
							 localTrans.setSerialToner(toner.getSerial());
							 localTrans.setSolicitante(solicitante);
							 localTrans.setStatus("ACTIVE");
							 localTrans.setTipoTransaccion("S");
							 localTrans.setDescripcion(descripcion);
							 					 
							 TransaccionDA transDa = new TransaccionDA();
							 idLocalTransaction = transDa.insertTransaccion(localTrans,getActivity());
						     
						//	 for(int actNumber=0;actNumber<reporteService.getActivos().size();actNumber++){
								 
						//		 Activo activo = reporteService.getActivos().get(actNumber);
								 active.setIdTransaccion(idLocalTransaction);
								 active.setIdDependencia(deptid);
								 
								 ActivoDA activoDa = new ActivoDA();
								 activoDa.insertActivo(active,getActivity());
								 
						//	 }
							 
							 transaction = localTrans;
							 transaction.setIdTransaccion(idLocalTransaction);
							 
							 if(HTTPConnection.verificaConexion(getActivity())){
								 dialogMessage="Procesando registro...";
						         new HTTPRequest().execute();
							 }else{
								 Toast.makeText(getActivity(),"No es posible conectar a internet. Verifique su conexión.",Toast.LENGTH_LONG).show();
								 verifySerialToner(tonerSerial);
								 envio = false;
			    				 verify = false;
							 }
						 }catch(SQLiteException e){
							 Toast.makeText(getActivity(),"Ocurrio un error de conexión con la base de datos.",Toast.LENGTH_LONG).show();
							 envio = false;
		    				 verify = false;
						 }
					 
				    }else{
				    	
				    	verifySerialToner(serial);
				    }
				 }else{
					 
					 Utils.alertMessage("Por favor escanee el toner a ser entregado.",getActivity());
				 }
			
			 }else{
				 Utils.alertMessage("Por favor indique el modelo de impresora donde será llevada a cabo la instalación.",getActivity());
			 }
	   }
	   
	   
	   
	   
	   
	    
       @Override
   	public void onActivityResult(int requestCode, int resultCode, Intent data) {
   			 super.onActivityResult(requestCode, resultCode, data);
   	     
   	     if (resultCode == Activity.RESULT_OK) { 
   	
             	
             	String dataScanned = data.getStringExtra("SCAN_RESULT");
             	tonerSerial = dataScanned;           		
			    verifySerialToner(tonerSerial);
   	    
   	               
   	      } else if (resultCode == Activity.RESULT_CANCELED) {
   	    	  
   	    	  
   	    	  Toast.makeText(getActivity(),"el scaneo no fue llevado a cabo. ", Toast.LENGTH_SHORT)
 	            .show();
   	    	
   	     }
   	     
   	} 
	
}