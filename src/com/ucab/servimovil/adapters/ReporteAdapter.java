package com.ucab.servimovil.adapters;

import java.util.List;

import com.ucab.servimovil.R;
import com.ucab.servimovil.model.ReporteService;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ReporteAdapter extends ArrayAdapter<ReporteService> {
	

	private Activity context;
	private List<ReporteService> valores;

	public ReporteAdapter(Activity context, int resource,List<ReporteService> valores) {
		super(context, resource, valores);

        this.context = context;
        this.valores = valores;
	}
	
	public int getCount(){
		 return valores.size();
	}
	
	public ReporteService getItem(int position){
		   return valores.get(position);
	}
	
	
public View getView(int position, View convertView, ViewGroup parent){
		
	     View listItem = convertView;
	
	     if(listItem == null)
         {
	    		LayoutInflater inflater = context.getLayoutInflater();
	            listItem = inflater.inflate(R.layout.list_reportes_layout, null);
         }
        
        TextView numeroText = (TextView) listItem.findViewById(R.id.numeroReporteText);
        numeroText.setText(String.valueOf(valores.get(position).getIdReporte()));
        
        TextView oficinaText = (TextView) listItem.findViewById(R.id.oficinaText);
        oficinaText.setText(valores.get(position).getDependencia());
        
        TextView descripcionText = (TextView) listItem.findViewById(R.id.descripcionText);
        descripcionText.setText(valores.get(position).getDescripcion());
        
        TextView tonerText = (TextView) listItem.findViewById(R.id.tonerLabel);
        tonerText.setText("");

       
        if ( position % 2 == 0)
        	listItem.setBackgroundResource(R.drawable.list_view_borders_impar);
        else
        	listItem.setBackgroundResource(R.drawable.list_view_borders_par);
        
		
		return listItem; 
	}

}
