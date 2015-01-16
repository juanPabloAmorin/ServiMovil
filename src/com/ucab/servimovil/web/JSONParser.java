package com.ucab.servimovil.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ucab.servimovil.model.*;

import android.util.Log;

public class JSONParser {
	
		 public static JSONObject parseJSONObject(String input) {

	        JSONObject jObject = null;

	        if(input != null && !input.isEmpty()) {
	            try {
	                jObject = new JSONObject(input);
	            }
	            catch (JSONException e) {
	                Log.e("json error", "Error parsing data " + e.toString());
	            }
	        }
	        return jObject;
	    }
	 
	 public static JSONArray parseJSONArray(String input) {

	        JSONArray array = null;

	        if(input != null && !input.isEmpty()) {
	            try {
	                array = new JSONArray(input);
	            }
	            catch (JSONException e) {
	                Log.e("json error", "Error parsing data " + e.toString());
	            }
	        }
	        return array;
	    }
	  
	  public static List<Toner> parseJsonToner(String jsonString){
		  
		  List<Toner> listToners = null;
		  
		  if(jsonString != null && !jsonString.isEmpty()) {
	            try {
	            	
	            	JSONArray jsonArray = JSONParser.parseJSONArray(jsonString);
	                
                    listToners = new ArrayList<Toner>();
	                for(int i=0; i<jsonArray.length(); i++)
	                {
	                	JSONObject obj = jsonArray.getJSONObject(i);
	                	
	                	Toner toner = new Toner();
	                	toner.setIdModelo(obj.getLong("MODELO"));
	                	toner.setIdToner(obj.getLong("ID_TONER"));
	                	toner.setNumeroFactura(obj.getInt("NUMERO_FACTURA"));
	                	toner.setSerial(obj.getString("SERIAL"));
	                	toner.setStatus(obj.getString("STATUS"));
	                	
	                	listToners.add(toner);
	                	toner = null;
	                }


	            } catch(Exception e) {
	            	listToners = null;
	                Log.e("parsin json error", "Error parsing json " + e);
	            }
	        }
		    
		    return listToners;
	  }
	  
    public static List<ModeloToner> parseJsonModeloToner(String jsonString){
		  
		  List<ModeloToner> listModelToners = null;
		  
		  if(jsonString != null && !jsonString.isEmpty()) {
	            try {
	            	
	            	JSONArray jsonArray = JSONParser.parseJSONArray(jsonString);
	                
                    listModelToners = new ArrayList<ModeloToner>();
	                for(int i=0; i<jsonArray.length(); i++)
	                {
	                	JSONObject obj = jsonArray.getJSONObject(i);
	                	
	                	ModeloToner modelToner = new ModeloToner();
	                    modelToner.setDescripcion(obj.getString("DESCRIPCION"));
	                	modelToner.setIdModelo(obj.getLong("ID_MODELO"));
	                	
	                	listModelToners.add(modelToner);
	                	modelToner = null;
	                }


	            } catch(Exception e) {
	                Log.e("parsin json error", "Error parsing json " + e);
	            }
	        }
		    
		    return listModelToners;
	  }
    
    
      public static UserService parseUserService(String jsonString){
    	  
    	  UserService user = null;
    	  
    	  if(jsonString != null && !jsonString.isEmpty()){
    		  
    		  try{
    			  JSONObject obj = JSONParser.parseJSONObject(jsonString);
    			  
    			  user = new UserService();
    			  user.setIdUsuario(obj.getLong("idUsuario"));
    			  user.setLogin(obj.getString("login"));
    			  user.setNombre(obj.getString("nombre"));
    			  user.setStatus(obj.getString("status"));
    			  
    		  }catch(Exception e){
    			  Log.e("parsin json error", "Error parsing json " + e);
    			  user = null;
    		  }
    	  }
    	  
    	  return user;
    	  
      }
      
      public static User parseUser(String jsonString){
    	  
    	  User user = null;
    	  
    	  if(jsonString != null && !jsonString.isEmpty()){
    		  
    		  try{
                  JSONObject obj = JSONParser.parseJSONObject(jsonString);
    			  
    			  user = new User();
    			  user.setIdUsuario(obj.getLong("ID_USUARIO"));
    			  user.setLogin(obj.getString("LOGIN"));
    			  user.setNombre(obj.getString("NOMBRE"));
    			
    		  }catch(Exception e){
    			  Log.e("parsin json error", "Error parsing json " + e);
    			  user = null;
    		  }
    	  }
    	  
    	  return user;
      }
    
    
    
     public static List<ReporteService> parseReporteService(String jsonString){
    	 
    	 List<ReporteService> reportList = null;
    	 
    	 if(jsonString != null && !jsonString.isEmpty()){
    		 
    		 try{			  
    			  reportList = new ArrayList<ReporteService>();    			  
    			  JSONArray array = JSONParser.parseJSONArray(jsonString);
    			  
    			  for(int i = 0;i<array.length();i++){
    				  
    				  JSONObject obj = array.getJSONObject(i);
    				  ReporteService report = new ReporteService();
    				  
    				  List<Activo> listActivo = new ArrayList<Activo>();
    				  
    				  report.setDependencia(obj.getString("dependencia"));
    				  report.setIdReporte(obj.getLong("idReporte"));
    				  report.setSolicitante(obj.getString("solicitante"));
    				  report.setDependenciaDescripcion(obj.getString("departmentDesc"));
    				  report.setDescripcion(obj.getString("descripcion"));
    				  report.setDeptId(obj.getLong("deptid"));
    				  
    				  String fecha = obj.getString("fechaSolicitud");  				  
    				  SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); 
    				  Date date = format.parse(fecha);
    				  report.setFechaSolicitud(date);
    				  
    				  try{
		    				  JSONArray activos = obj.getJSONArray("activos");
		    				  
		    				  if(activos.length() == 0)
	    						  break;
		    				  
		    				  for(int j=0;j<activos.length();j++){
		    					  
		    					  
		    					  JSONObject activoObject = activos.getJSONObject(j);
		    					  Activo activo = new Activo();
		    					  
		    					  activo.setRealId(activoObject.getLong("idActivo"));
		    				      activo.setNombre(activoObject.getString("nombre"));
		    				      activo.setContador(activoObject.getInt("contador"));
		    					 
		    					  try{
		    					      activo.setNumero(activoObject.getString("numero"));
		    					  }catch(Exception e){
		    						  Log.e("error parse number","error parsing number in activo");
		    					  }
		    					  
		    					  try{
		    					       activo.setSerial(activoObject.getString("serial"));
		    					  }catch(Exception e){
		    						  Log.e("error parse serial","error parsing serial in activo");
		    					  }
		    					  
		    					  listActivo.add(activo);
		    					  
		    				  }
		    				  
		    				  report.setActivos(listActivo);
		    				  
		    				  reportList.add(report);
    				  
    				  }catch(Exception e){
    					  Log.e("error parsing json","No tiene activos asociados");
    				  }
    			  }
    			 
    		 }catch(Exception e){
    			 
    			 Log.e("parsin json error", "Error parsing json " + e);
   			     reportList = null;
    		 }
    		 
    	 }
    	 
    	 return reportList;
     }
     
     public static List<ModeloImpresora> pasePrinterModel(String jsonString){
    	 
    	 List<ModeloImpresora> modelList = null;
    	 if(jsonString != null && !jsonString.isEmpty()){
    		 
    		  try{
    			  JSONArray jarray = JSONParser.parseJSONArray(jsonString);
    		
    			  modelList = new ArrayList<ModeloImpresora>();
    			  
    			  for(int i =0; i<jarray.length();i++){
    				  
    				  JSONObject obj = jarray.getJSONObject(i);
    				  
    				  ModeloImpresora model = new ModeloImpresora();
    				  
    				  model.setIdModelo(obj.getLong("ID_MODELO_IMPRESORA"));
    				  model.setDescripcion(obj.getString("DESCRIPCION"));
    				  
    				  modelList.add(model);				  
    				  
    			  }
    			  
    		  }catch(Exception e){
    			  
    			  Log.e("parsin json error", "Error parsing json " + e);
    			  modelList = null;
    			  
    		  }
    	 }
    	 
    	 return modelList;
    	 
     }
     
     public static List<Transaccion> parseTransaccion(String jsonString){
    	 
  
    	 List<Transaccion> listTransaction = null;
    	 
    	 if(jsonString != null && !jsonString.isEmpty()){
    		 
    		 try{
	    		 JSONArray jarray =  JSONParser.parseJSONArray(jsonString);
	    		 
	    		 listTransaction = new ArrayList<Transaccion>();
	    		 
	    		 for(int i =0;i<jarray.length();i++){
	    			 
	    		 try{	
			    			 JSONObject obj = jarray.getJSONObject(i);
			    			 
			    			 Transaccion transaction = new Transaccion();
			    			 SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); 
			    			   				  
			    			 
							  String fecha2 = obj.getString("FECHA_TRANSACCION");  				  
						      Date date2 = format.parse(fecha2);
						      transaction.setFechaTransaccion(date2); 
		   				  
					         transaction.setIdUsuario(obj.getLong("ID_USUARIO"));
					         transaction.setObservaciones(obj.getString("OBSERVACIONES"));
					         transaction.setStatus(obj.getString("STATUS"));
					         transaction.setTipoTransaccion(obj.getString("TIPO_TRANSACCION"));
					         transaction.setTransaccionId(obj.getLong("ID_TRANSACCION"));
					         transaction.setIdReporte(obj.getLong("ID_REPORTE"));
						     
					         try{
						         JSONObject tonerJson = obj.getJSONObject("TONER");
						         if(tonerJson != null){
						        	 
						        	 Toner toner = new Toner();
						        	 toner.setIdModelo(tonerJson.getLong("MODELO"));
						        	 toner.setIdToner(tonerJson.getLong("ID_TONER"));
						        	 
						        /*	 if(!tonerJson.getString("MODELO_IMPRESORA").equals("null"))
						        	     toner.setModeloImpresora(tonerJson.getLong("MODELO_IMPRESORA")); */
						        	 
						        	 toner.setNumeroFactura(tonerJson.getInt("NUMERO_FACTURA"));
						        	 toner.setSerial(tonerJson.getString("SERIAL"));
						        	 toner.setStatus(tonerJson.getString("STATUS"));
						        	 
						        	 transaction.setToner(toner);
						        	  
						         }else{
						        	 transaction.setToner(null);
						         }
					         
					         }catch(JSONException e){
					        	 transaction.setToner(null);
					         }
						         
					         try{
						         JSONObject reporteJson = obj.getJSONObject("REPORTE");
						         if(reporteJson != null){
						        	 Reporte reporte = new Reporte();
						        	 
						        	
						        	 
						        	 try{
						        		 reporte.setDependencia(reporteJson.getString("DEPENDENCIA"));
								         reporte.setSolicitante(reporteJson.getString("SOLICITANTE"));
								         reporte.setDescripcion(obj.getString("DESCRIPCION"));
						        		 
							        	 String fecha = reporteJson.getString("FECHA_SOLICITUD");  				  
								         Date date = format.parse(fecha);
								         reporte.setFechaSolicitud(date);
						        	
						        	 }catch(Exception e){
						        		 Log.e("error parsing json","error parsing reporte to json");
						        	 }
							         
							         reporte.setIdReporte(reporteJson.getLong("ID_REPORTE"));
		
							         
							         transaction.setReporte(reporte);
						        	 
						         }else{
						        	 transaction.setReporte(null);
						         }
					         
					         }catch(JSONException e){
					        	 transaction.setReporte(null);
					         }
			    			 
					         listTransaction.add(transaction);
	    		
	    		 }catch(Exception e){
	    			 Log.e("error parsin json transaction","Una mala transaccion recibida");
	    		 }
	    	   }
	    		 
	    		 
    		 }catch(Exception e){
    			 Log.e("parsin json error", "Error parsing json " + e);
   			     listTransaction = null;
    		 }
    	 }
    	 
    	 return listTransaction;
     }
     
     public static Toner parseToner(String jsonString){
    	 
    	 Toner toner = null;
    	 
    	 if(jsonString != null && !jsonString.isEmpty()){
    		 
    		 try{
    			 
    			 JSONObject obj = JSONParser.parseJSONObject(jsonString);
    			 toner = new Toner();
    			 	 
    				 toner.setIdModelo(obj.getLong("MODELO"));
    				 toner.setIdToner(obj.getLong("ID_TONER"));   	
    				
    				try{
    				   JSONObject objModelo = obj.getJSONObject("MODELO_TONER");
    				   if(objModelo != null){
    					   
    					   ModeloToner modeloToner = new ModeloToner();
    					   modeloToner.setIdModelo(objModelo.getLong("ID_MODELO"));
    					   modeloToner.setDescripcion(objModelo.getString("DESCRIPCION"));
    					   
    					   toner.setModeloToner(modeloToner);
    				   }
    				}catch(Exception e){
    					 Log.e("parsin json error", "Error parsing json " + e);
    				}
    				 
    				 try{
    				    toner.setModeloImpresora(obj.getLong("ID_MODELO_IMPRESORA"));
    				 }catch(JSONException e){
    					 toner.setModeloImpresora(0);
    				 }
    				 toner.setNumeroFactura(obj.getInt("NUMERO_FACTURA"));
    				 toner.setSerial(obj.getString("SERIAL"));
    				 toner.setStatus(obj.getString("STATUS"));
    			 
    			 
    			 
    		 }catch(Exception e){
    			 Log.e("parsin json error", "Error parsing json " + e);
   			     toner = null;
    		 }
    	 }
    	 
    	 return toner;
    	 
     }
     
     public static LDAPObject parseLdapObject(String jsonString){
   	  
    	 LDAPObject ldapObject = null;
   	  
   	  if(jsonString != null && !jsonString.isEmpty()){
   		  
   		  try{
   			  JSONObject obj = JSONParser.parseJSONObject(jsonString);
   			  
   			ldapObject = new LDAPObject();
   			ldapObject.setMessage(obj.getString("mensaje"));
   			ldapObject.setResult(obj.getString("resultado"));
   			 
   		  }catch(Exception e){
   			  Log.e("parsin json error", "Error parsing json " + e);
   			  ldapObject = null;
   		  }
   	  }
   	  
   	  return ldapObject;
   	  
     }
    

}
