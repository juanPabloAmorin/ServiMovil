package com.ucab.servimovil;


//import net.sourceforge.zbar.*;

import com.ucab.servimovil.R;

import com.ucab.servimovil.dataAcces.TonerDA;
import com.ucab.servimovil.model.TonerTemp;
import com.ucab.servimovil.utils.Utils;
import com.ucab.servimovil.web.*;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.*;

public class RegistroTonerFragment extends Fragment {
	
/*	private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;    */
    private ProgressDialog ringProgressDialog;  
    private String dialogTitle;
    private String dialogMessage;
    private EditText modelText;
    private EditText serialText;
 //   private FrameLayout preview;
  //  private ImageScanner scanner;

   /* @SuppressWarnings("unused")
	private boolean barcodeScanned = false;
    private boolean previewing = true; */
    private String jsonToner;  
    private String modelo;
    private String serial;
    
    private boolean segundaVez = false;

  /*  static {
        System.loadLibrary("iconv");
    } */
	
	int numberButton;
	String apiCall;
	String serialData;
	
	@Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nuevo_toner, container, false);
    }
		
	@Override
	public void onActivityCreated(Bundle state){

    	super.onActivityCreated(state);
    	
    	Button modelButton = (Button) getView().findViewById(R.id.modeloCodeButton);
    	Button serialButton = (Button) getView().findViewById(R.id.serialCodeButton);
    	Button registerButton = (Button) getView().findViewById(R.id.tonerRegisterButton);
    	Button backButton = (Button) getView().findViewById(R.id.regBackButton);
    	
    	modelo = "";
		serial = "";

		modelText = (EditText) getView().findViewById(R.id.modeloNuevotoner);
		serialText = (EditText) getActivity().findViewById(R.id.serialNuevotoner);

//		autoFocusHandler = new Handler();
//		mCamera = getCameraInstance();

/*		scanner = new ImageScanner();
		scanner.setConfig(0, Config.X_DENSITY, 3);
		scanner.setConfig(0, Config.Y_DENSITY, 3);
*/

    	modelButton.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				numberButton = 0;	
			/*	mPreview = new CameraPreview(getActivity(), mCamera, previewCb, autoFocusCB);
		        preview = (FrameLayout) getView().findViewById(R.id.cameraPreview);
		        preview.addView(mPreview);	 */		
				
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
       
       serialButton.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				numberButton = 1;			
			/*	mPreview = new CameraPreview(getActivity(), mCamera, previewCb, autoFocusCB);
		        preview = (FrameLayout) getView().findViewById(R.id.cameraPreview);
		        preview.addView(mPreview); */
				
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
       
       registerButton.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
		 	     modelo = modelText.getText().toString();
		 	     serial = serialText.getText().toString();
		 	     
		 	     String message = "";
		 	     
		 	     if(modelo.equals("")){
		 	    	 
		 	    	 message = message + "Modelo\n";
		 	     }
		 	     
		 	    if(serial.equals("")){
		 	    	 message = message + "Serial\n";

		 	     }
		 	    
		 	    if(message.equals("")){
		 	    	
		 	    	
			 	    	if(HTTPConnection.verificaConexion(getActivity()) == true){
	               			 apiCall = "/api/Toner/"+serial;
	               			 serialData = serial;
	               			 segundaVez = true;
	               			 dialogTitle = "Espera un momento...";
	               			 dialogMessage = "Procesando registro...";
	               			 new HTTPRequest().execute();
	               	
	               		}else{
	               			Toast.makeText(getActivity(),"Comprueba tu conexión a Internet para continuar. ", Toast.LENGTH_SHORT)
	           	            .show();
	               		}
		 	    		
		 	    
		 	    }else{
		 	    	 Utils.alertMessage("Por favor indique los datos solicitados:\n" + message,getActivity());
		 	    }
				 
			}
			
		}); 
       
       backButton.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				  NuevoTonerFragment nuevo = new NuevoTonerFragment();
				  FragmentTransaction transaction = getFragmentManager().beginTransaction();
				  
				  
				  try{
				     Bundle newBundle = new Bundle();
   					 newBundle.putLong("factura",getArguments().getLong("factura"));
   					 nuevo.setArguments(newBundle); 
				  }catch(Exception e){
					  
					  Log.e("factura2 tag", "No hay valor en factura");
				  }
				  
			     transaction.replace(R.id.registro_layout,nuevo);
			     transaction.commit();
			}
			
		}); 
	}
	
/*	
	  public void onPause() {
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
	                    	
	                    	if(numberButton == 0){
	                    		modelText.setText(sym.getData());
	                    	}else{
	                    		String dataScanned = sym.getData();
	                    		if(HTTPConnection.verificaConexion(getActivity()) == true){
	                    			 apiCall = "/api/Toner/"+dataScanned;
	                    			 serialData = dataScanned;
	                    			 dialogTitle= "Espera un momento...";
	                    			 dialogMessage = "Verificando serial...";
	                    			 new HTTPRequest().execute();
	                    		}else{
	                    			Toast.makeText(getActivity(),"Comprueba tu conexión a Internet para continuar. ", Toast.LENGTH_SHORT)
		            	            .show();
	                    		}
			            	            
			           
	                    	}
	                        barcodeScanned = true;
	                    }
	                    
	                    preview.removeView(mPreview);
	                    
	                }
	            }
	            

	        };

	    AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
	            public void onAutoFocus(boolean success, Camera camera) {
	                autoFocusHandler.postDelayed(doAutoFocus, 1000);
	            }
	        };
	        
	        
	        */
	    private class HTTPRequest extends AsyncTask<Void,Void,Void> {

	            @Override
	            protected void onPreExecute() {
	                ringProgressDialog = ProgressDialog.show(getActivity(),dialogTitle,dialogMessage, true);
	            }

	            @Override
	            protected Void doInBackground(Void... voids) {
	            	HTTPConnection.url = Syncronizer.server+apiCall;

	                String tonerJSON = HTTPConnection.parseInputStreamToString(HTTPConnection.GetRequest(HTTPConnection.url));
	                
	              
	                if(tonerJSON != null){
	                	
	                	jsonToner = tonerJSON.trim();
	                      
	            
	                }
	                
	                try{
		                   Thread.sleep(2000);
		                }catch(Exception e){
		                	 Log.e("dialog exception","error en ring dialog " +e.toString());
		                }
	                return null;
	            }

	            @Override
	            protected void onPostExecute(Void aVoid) {
	            
	            	ringProgressDialog.dismiss();
	            	segundaVerificacion();
	            } 
	        }
	        
	        public void segundaVerificacion(){
	        	
	        	 if(jsonToner.equals("null")){
		        	 TonerDA tonerda = new TonerDA();
	                 TonerTemp tonerTemp = tonerda.findTonerTempBySerial(serialData, getActivity());
		   			  
	                 if(tonerTemp == null){
		   			    
	                	 if(segundaVez){
	                		 try{
			                	   tonerda = new TonerDA();
			   		 	    	   
			   		 	    	   tonerTemp = new TonerTemp();
			   		 	    	   tonerTemp.setModelo(modelo);
			   		 	    	   tonerTemp.setSerial(serial);
			   		 	    	   
			   		 	    	   tonerda.insertTonerTemp(tonerTemp, getActivity());
			   		 	    	   
			   		 	    	  NuevoTonerFragment nuevo = new NuevoTonerFragment();
			   					  FragmentTransaction transaction = getFragmentManager().beginTransaction();
			   					  
			   					  try{
			   					     Bundle newBundle = new Bundle();
			   	   					 newBundle.putLong("factura",getArguments().getLong("factura"));
			   	   					 nuevo.setArguments(newBundle); 
			   					  }catch(Exception e){
			   						  
			   						  Log.e("factura2 tag", "No hay valor en factura");
			   					  }
			   					  
			   				      transaction.replace(R.id.registro_layout,nuevo);
			   				      transaction.commit();  
	                		 }catch(SQLiteException e){
	                			 Log.e("Sqlite exception","No es posible establecer conexion con la base de datos");
	                		 }
	   		 	    	   
	                		 
	                	 }else{
	                	     serialText.setText(serialData);
	                	 }
		                         
		   			  }else{
		   				  
		   				Toast.makeText(getActivity(),"El toner "+serialData+" ya se encuentra listado para su registro", Toast.LENGTH_LONG)
	    	            .show();
		   			  }
	        	 }else{
	        		  
	        		 Toast.makeText(getActivity(),"El toner "+serialData+" ya se encuentra registrado", Toast.LENGTH_LONG)
	    	            .show();
	        	 }
	   			 
	        }
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        @Override
	    	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    			 super.onActivityResult(requestCode, resultCode, data);
	    	     
	    	     if (resultCode == Activity.RESULT_OK) { 
	    	    	
	    	    	 
	    	    	 if(numberButton == 0){
                 		modelText.setText(data.getStringExtra("SCAN_RESULT"));
                 	}else{
                 		String dataScanned = data.getStringExtra("SCAN_RESULT");
                 		if(HTTPConnection.verificaConexion(getActivity()) == true){
                 			 apiCall = "/api/Toner/"+dataScanned;
                 			 serialData = dataScanned;
                 			 dialogTitle= "Espera un momento...";
                 			 dialogMessage = "Verificando serial...";
                 			 new HTTPRequest().execute();
                 		}else{
                 			Toast.makeText(getActivity(),"Comprueba tu conexión a Internet para continuar. ", Toast.LENGTH_SHORT)
	            	            .show();
                 		}
                 	}
	    	               
	    	      } else if (resultCode == Activity.RESULT_CANCELED) {
	    	    	  
	    	    	  
	    	    	  Toast.makeText(getActivity(),"el scaneo no fue llevado a cabo. ", Toast.LENGTH_SHORT)
      	            .show();
	    	    	
	    	     }
	    	     
	    	} 
	        
}
