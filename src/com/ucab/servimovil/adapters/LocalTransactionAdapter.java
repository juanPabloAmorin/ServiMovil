package com.ucab.servimovil.adapters;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ucab.servimovil.R;
import com.ucab.servimovil.model.LocalTransaction;

public class LocalTransactionAdapter extends ArrayAdapter<LocalTransaction> {
	

	private Activity context;
	private List<LocalTransaction> valores;

	public LocalTransactionAdapter(Activity context, int resource,List<LocalTransaction> valores) {
		super(context, resource, valores);

        this.context = context;
        this.valores = valores;
	}
	
	public int getCount(){
		 return valores.size();
	}
	
	public LocalTransaction getItem(int position){
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
        
        TextView tonerLabel = (TextView) listItem.findViewById(R.id.tonerLabel);
        tonerLabel.setText("Toner:");
        
        TextView tonerText = (TextView) listItem.findViewById(R.id.tonerText);
        tonerText.setText(valores.get(position).getSerialToner());

       
        if ( position % 2 == 0)
        	listItem.setBackgroundResource(R.drawable.list_view_borders_impar);
        else
        	listItem.setBackgroundResource(R.drawable.list_view_borders_par);
        
		
		return listItem; 
	}

}