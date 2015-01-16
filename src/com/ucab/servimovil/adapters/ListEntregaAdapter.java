package com.ucab.servimovil.adapters;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ucab.servimovil.R;
import com.ucab.servimovil.model.ListEntregaDataObject;

public class ListEntregaAdapter extends ArrayAdapter<ListEntregaDataObject> {
	
	
	
	private Activity context;
	private List<ListEntregaDataObject> valores;

	public ListEntregaAdapter(Activity context, int resource,List<ListEntregaDataObject> valores) {
		super(context, resource, valores);

        this.context = context;
        this.valores = valores;
	}

	public int getCount(){
		 return valores.size();
	}
	
	public ListEntregaDataObject getItem(int position){
		   return valores.get(position);
	}
	

	
	public View getView(int position, View convertView, ViewGroup parent){
		
		View listItem = convertView;
		
		if(listItem == null)
	    {
		   LayoutInflater inflater = context.getLayoutInflater();
           listItem = inflater.inflate(R.layout.list_view_layout, null);
	    }
 
        TextView serialTonerText = (TextView) listItem.findViewById(R.id.listSerialText);
        serialTonerText.setText(valores.get(position).getSerial());
 
        TextView dependenciaText = (TextView)listItem.findViewById(R.id.listDependenciaText);
        dependenciaText.setText(valores.get(position).getNombreDependencia());
        
        
        if ( position % 2 == 0)
        	listItem.setBackgroundResource(R.drawable.list_view_borders_impar);
        else
        	listItem.setBackgroundResource(R.drawable.list_view_borders_par);
        
		
		return listItem; 
	}
	
  
}
