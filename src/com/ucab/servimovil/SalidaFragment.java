package com.ucab.servimovil;



import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import com.ucab.servimovil.R;
import com.ucab.servimovil.adapters.ReporteAdapter;
import com.ucab.servimovil.model.ReporteService;
import com.ucab.servimovil.model.Transaccion;
import com.ucab.servimovil.model.User;
import com.ucab.servimovil.utils.Utils;
import com.ucab.servimovil.web.*;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class SalidaFragment extends Fragment {
	
	ListView listSolicitud;
	ProgressDialog ringProgressDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
		
	   return inflater.inflate(R.layout.get_report_list_layout, container, false);
		
		
	}

	public void onActivityCreated(Bundle state){
		super.onActivityCreated(state);

		listSolicitud = (ListView) getView().findViewById(R.id.listViewReportes);
		
		listSolicitud.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View v, int position,long id) {
				
				ReporteService data = (ReporteService) av.getAdapter().getItem(position);
                
                PlanillaSalidaFragment planillaFragment = new PlanillaSalidaFragment();
                Bundle newBundle = new Bundle();
        
                newBundle.putSerializable("reporte",data);
      
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String fecha = format.format(data.getFechaSolicitud());
                newBundle.putCharSequence("fecha",fecha);
                
                
                planillaFragment.setArguments(newBundle);
			    FragmentTransaction transaction = getFragmentManager().beginTransaction();
			    transaction.replace(R.id.solicitudesLayout, planillaFragment);
				transaction.commit();
				
				
			}
			
		});
		 
		
		if(HTTPConnection.verificaConexion(getActivity()) == true){
			
		     new HTTPRequest().execute();
		}else{
			Toast.makeText(getActivity(),"Comprueba tu conexión a Internet para continuar. ", Toast.LENGTH_SHORT)
	       .show();
		}
		
	} 
	
	public List<ReporteService> getSolicitudes(){
		
		InputStream inputStream = null;
		String result = "";
		List<ReporteService> report = null;
	
		try{
			HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams,HTTPConnection.TIME_OUT_CONNECTION);
            HttpConnectionParams.setSoTimeout(httpParams,HTTPConnection.TIME_OUT_SOCKET);
		    HttpClient httpclient = new DefaultHttpClient(httpParams);
	        HttpGet httpGet = new HttpGet(Syncronizer.server + "/api/Reportes/"+User.actualUser);
	        httpGet.setHeader("Accept", "application/json");
		    httpGet.setHeader("charset","utf-8");
		    HttpResponse httpResponse = httpclient.execute(httpGet);
		    inputStream = httpResponse.getEntity().getContent();
            
		    if(inputStream != null){
		    	result = Utils.convertInputStreamToString(inputStream);
		    
				try{
					report = JSONParser.parseReporteService(result);
		
				    httpclient = new DefaultHttpClient(httpParams);
					httpGet = new HttpGet(Syncronizer.server+"/api/AOTransactions");
					httpGet.setHeader("Accept", "application/json");
					httpGet.setHeader("charset","utf-8");
			        httpResponse = httpclient.execute(httpGet);
			        inputStream = httpResponse.getEntity().getContent();
			         
			         if(inputStream != null)
			         {
			        	 
			              result = Utils.convertInputStreamToString(inputStream);	 
			              	            
			              List<Transaccion> listTransaction = JSONParser.parseTransaccion(result);
			              
			              for(int i=0;i<report.size();i++){
			            	  
			            	  ReporteService reporte = report.get(i);
			            	  for(int j=0;j<listTransaction.size();j++){
			            		  
			            		  Transaccion transaction = listTransaction.get(j);
			            		  	            		  
			            		  if(transaction.getIdReporte() != 0){
			            			  if(transaction.getIdReporte() == reporte.getIdReporte()){
			            				  
			            				  report.remove(i);
			            				  i--;
			            				  break;
			            			  }
			            		  }else{
			            			  Log.e("tag_reporte","reporte es null");
			            		  }
			            	  }
			              }
			              
			         }else{
			         	 report = null;
			         }
					
					}catch(Exception e){
						Log.e("HTTP error","Error getting transactions");
						result = "error";
						report = null;
				    }
		    }else{
		    	Log.e("HTTP error","Error resultado servidesk");
				result = "error";
				report = null;
		    }
		}catch(Exception e){
			Log.e("HTTP error","Error conexión servidesk");
			result = "error";
			report = null;
		}
				
		return report;
		
	}
	
	
	 private class HTTPRequest extends AsyncTask<Void,Void,List<ReporteService>> {

			
		    @Override
		    protected void onPreExecute() {
		        ringProgressDialog = ProgressDialog.show(getActivity(),"Espera un momento...","Cargando solicitudes...", true);

		    }

		    @Override
		    protected List<ReporteService> doInBackground(Void... voids) {
		    	
		    	 try{
		             Thread.sleep(2000);
		          }catch(Exception e){
		          	 Log.e("dialog exception","error en ring dialog " +e.toString());
		          }
		        return getSolicitudes();
		    }

		    @Override
		    protected void onPostExecute(List<ReporteService> listReport) {
		    	
				ringProgressDialog.dismiss();
				TextView reportTitle = (TextView) getView().findViewById(R.id.reporteListTitle);
		    	if(listReport == null){
		    		Toast.makeText(getActivity(),"Ha ocurrido un error de conexión con el servidor. Verifique su conexión.",Toast.LENGTH_LONG).show();		    		
		    		reportTitle.setText("Intente cargar la pagina nuevamente.");
		    	}else{
		    				
                        
		    			if(listReport.size() > 0){
		    				
		    				 reportTitle.setText("Seleccione la solicitud que será atendida.");
		    				 
		    				 ReporteAdapter listAdapter = new ReporteAdapter(getActivity(),
		    							android.R.layout.simple_spinner_item,listReport);
		    							
		    				 listSolicitud.setAdapter(listAdapter); 
		    				
		    			}else{
		    			     reportTitle.setText("No tiene tareas asignadas en este momento.");
		    			}
		    			  
		    	
		    	}
		    	
		    	

		    }
		}
	 
	 
	

}
