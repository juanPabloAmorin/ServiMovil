package com.ucab.servimovil.adapters;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ucab.servimovil.model.Activo;

public class ActivoAdapter extends ArrayAdapter<Activo> {
	
	private Context context;
	private List<Activo> valores;

	public ActivoAdapter(Context context, int resource,List<Activo> valores) {
		super(context, resource, valores);

        this.context = context;
        this.valores = valores;
	}

	public int getCount(){
		 return valores.size();
	}
	
	public Activo getItem(int position){
		   return valores.get(position);
	}
	
	
	public View getView(int position, View convertView, ViewGroup parent){
		
		TextView label = new TextView(context);
		label.setTextColor(Color.BLACK);
		label.setText(valores.get(position).getNombre());
		
		return label;
	}
	
  public View getDropDownView(int position, View convertView, ViewGroup parent){
		
		TextView label = new TextView(context);
		label.setTextColor(Color.BLACK);
		label.setText(valores.get(position).getNombre());
		
		return label;
	}

}
