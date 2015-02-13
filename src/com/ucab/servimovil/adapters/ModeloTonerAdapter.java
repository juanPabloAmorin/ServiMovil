package com.ucab.servimovil.adapters;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ModeloTonerAdapter extends ArrayAdapter<String> {
	
	private Context context;
	private List<String> valores;

	public ModeloTonerAdapter(Context context, int resource,List<String> valores) {
		super(context, resource, valores);

        this.context = context;
        this.valores = valores;
	}
	
	public int getCount(){
		 return valores.size();
	}
	
	public String getItem(int position){
		   return valores.get(position);
	}
	
	
	public View getView(int position, View convertView, ViewGroup parent){
		
		TextView label = new TextView(context);
		label.setTextColor(Color.BLACK);
		label.setText(valores.get(position).toString());
		
		return label;
	}
	
   public View getDropDownView(int position, View convertView, ViewGroup parent){
		
		TextView label = new TextView(context);
		label.setTextColor(Color.BLACK);
		label.setText(valores.get(position).toString());
		
		return label;
	}
   
   public void add(String model){
	    super.add(model);
   }

}

