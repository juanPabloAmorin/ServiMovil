package com.ucab.servimovil;


import java.io.*;
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
import com.ucab.servimovil.adapters.RegisterListAdapter;
import com.ucab.servimovil.dataAcces.TonerDA;
import com.ucab.servimovil.model.*;
import com.ucab.servimovil.utils.Utils;
import com.ucab.servimovil.web.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;

public class NuevoTonerFragment extends Fragment {
	
	List <TonerTemp> listTempToner;
	long numberFactura;
	ProgressDialog ringProgressDialog;
	Button regButton;
	
	@Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        
		listTempToner = new ArrayList<TonerTemp>();
		
		return inflater.inflate(R.layout.fragment_factura_layout, container, false);
    }
	
	
public void onActivityCreated(Bundle state){
		
		super.onActivityCreated(state);
		
		getActivity().getWindow().setSoftInputMode(
			    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
			);
		
		Button startButton = (Button) getView().findViewById(R.id.startRegisterButton);
		ListView listaToners = (ListView) getView().findViewById(R.id.listToners);
		regButton = (Button) getView().findViewById(R.id.registerListButton);
		
		regButton.setClickable(true);
		EditText facturaText = (EditText) getView().findViewById(R.id.facturaEditText);

		try{
		    facturaText.setText(String.valueOf(getArguments().getLong("factura")));
		}catch(Exception e){
			Log.e("factura tag","No hay valor asignado a factura");
		}

		
		loadTonerList(listaToners);
				
		startButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				  RegistroTonerFragment registro = new RegistroTonerFragment();
				  FragmentTransaction transaction = getFragmentManager().beginTransaction();
				  
				  EditText facturaText = (EditText) getView().findViewById(R.id.facturaEditText);
				 
				  try{
					  Bundle newBundle = new Bundle();
					  newBundle.putLong("factura",Long.parseLong(facturaText.getText().toString()));    
					  registro.setArguments(newBundle);
				  }catch(Exception e){
					  Log.e("factura bundle tag","No hay valor asignado a factura"); 
				  }
				  
				  regButton.setClickable(false);
			      transaction.replace(R.id.registro_layout, registro);
			      transaction.commit();
			}
		});
		
        
		regButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				EditText facturaText = (EditText) getView().findViewById(R.id.facturaEditText);
				ListView listView = (ListView) getView().findViewById(R.id.listToners);
				
				String factura = facturaText.getText().toString();
				
				try{
					    
						if(factura.equals("")){
							Utils.alertMessage("Por favor indique el numero de factura",getActivity());
						}else if(listView.getCount() == 0){
							Utils.alertMessage("Debe agregar al menos un cartucho para su registro.",getActivity());
						}else{
							@SuppressWarnings("unused")
							long facturaNumber = Long.parseLong(factura.trim());
							confirmRegister();	 
						}
				
				}catch(NumberFormatException e){
					Log.e("NumberFormatexception","El numero de factura es invalido");
					Utils.alertMessage("El número de factura no es válido. verifique que solo tenga números",getActivity());
				}
			}
		});
           
	}  

public void confirmRegister(){
	EditText facturaText = (EditText) getView().findViewById(R.id.facturaEditText);
	ListView listView = (ListView) getView().findViewById(R.id.listToners);
	
	AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
	alertBuilder.setMessage("Se agregaran " + listView.getCount() + " cartuchos asociados a la factura Nro " + facturaText.getText().toString());
	alertBuilder.setCancelable(true);
	
	alertBuilder.setPositiveButton("Aceptar",
            new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
            registrarLista();
        }
    });
	
	alertBuilder.setNegativeButton("Cancelar",
            new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
        }
    });
   
    alertBuilder.show();
}


public void loadTonerList(ListView list){
	
	List<TonerTemp> opciones = new ArrayList<TonerTemp>();
	
	TonerDA tonerda = new TonerDA();
	opciones = tonerda.getRegisteredTonerTempList(getActivity());
	
	if(opciones == null){
		opciones = new ArrayList<TonerTemp>();
	}

	RegisterListAdapter listAdapter = new RegisterListAdapter(getActivity(),
			android.R.layout.simple_spinner_item,opciones);
			
	list.setAdapter(listAdapter);  
}

public void registrarLista(){

	ListView listView = (ListView) getView().findViewById(R.id.listToners);
	
	EditText facturaText = (EditText) getView().findViewById(R.id.facturaEditText);	
	String factura = facturaText.getText().toString();
	numberFactura = Long.parseLong(factura);
	
	RegisterListAdapter adapter = (RegisterListAdapter)listView.getAdapter();
    listTempToner = new ArrayList<TonerTemp>();
	
	for(int i=0; i<listView.getCount();i++){
		   
		 TonerTemp toner = (TonerTemp) adapter.getItem(i);
		 
		 listTempToner.add(toner);
	}
	
	
	if(HTTPConnection.verificaConexion(getActivity()) == true){
		
	     new HTTPRequest().execute();
	}else{
		Toast.makeText(getActivity(),"Comprueba tu conexión a Internet para continuar. ", Toast.LENGTH_SHORT)
       .show();
	}

}


private class HTTPRequest extends AsyncTask<Void,Void,String> {

	
    @Override
    protected void onPreExecute() {
        ringProgressDialog = ProgressDialog.show(getActivity(),"Espera un momento...","Procesando registro...", true);

    }

    @Override
    protected String doInBackground(Void... voids) {
    	
    	 try{
             Thread.sleep(2000);
          }catch(Exception e){
          	 Log.e("dialog exception","error en ring dialog " +e.toString());
          }
        return registroFinal();
    }

    @Override
    protected void onPostExecute(String result) {
    	
	 	 List<Toner> tonerList = new ArrayList<Toner>();
	 	 	 	 
	 	 tonerList = JSONParser.parseJsonToner(result);
	 	 
	 	 ringProgressDialog.dismiss();
	 	 
	 	 if(tonerList != null && tonerList.size() > 0){
	 		Toast.makeText(getActivity(),"Se han registrado " + listTempToner.size() + " cartuchos con Nro de factura "+numberFactura , Toast.LENGTH_LONG)
	        .show();
	 		
	 		TonerDA  tonerda = new TonerDA();
	 		tonerda.deleteTonerTemp(getActivity());
	 		
	 		NuevoTonerFragment nuevo = new NuevoTonerFragment();
	 		  FragmentTransaction transaction = getFragmentManager().beginTransaction();
	 		  
	 		 regButton.setClickable(false);
	 	    transaction.replace(R.id.registro_layout, nuevo);
	 	    transaction.commit();
	 	    
	 	 }else{
	 		Toast.makeText(getActivity(),"Ocurrio un error durante registro", Toast.LENGTH_LONG)
	        .show();
	 	 }
     
    }
}

   public String registroFinal(){
	    	 	    
	    InputStream inputStream = null;
	    String result = "";
	    
	    String jsonString = "[";
	    for(int j=0;j<listTempToner.size();j++){
	    		  
	          TonerTemp toner = listTempToner.get(j);
	          
	         
	          jsonString = jsonString + "{\"MODELO_TONER\":{\"DESCRIPCION\":\""+toner.getModelo()+"\"}," +
	        		      "\"TRANSACCION\":[{\"TIPO_TRANSACCION\":\"R\",\"ID_USUARIO\":"+User.actualUser+"}]," +
	                       "\"SERIAL\":\""+toner.getSerial()+"\",\"NUMERO_FACTURA\":"+numberFactura+"," +
	    		           "\"STATUS\":\""+getActivity().getResources().getString(R.string.toner_status_inventario)+"\"," +
	                       "\"MODELO_IMP\":null}";
	          
	     
	          
	          if(j != listTempToner.size()-1){
	        	  jsonString = jsonString + ",";
	          }
	          
	          
	    } 
	    
	    jsonString = jsonString + "]";
	    
	     Log.e("jsonString",jsonString);
	    
	    try{
	    	HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams,HTTPConnection.TIME_OUT_CONNECTION);
            HttpConnectionParams.setSoTimeout(httpParams,HTTPConnection.TIME_OUT_SOCKET);
		    HttpClient httpclient = new DefaultHttpClient(httpParams);
		    HttpPost httpPost = new HttpPost(Syncronizer.server+"/api/TonerS");
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
 

}
