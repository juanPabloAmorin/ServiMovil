package com.ucab.servimovil.adapters;

import java.util.List;

import com.ucab.servimovil.model.ModeloImpresora;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PrintModelAdapter extends ArrayAdapter<ModeloImpresora> {
	
	private Context context;
	private List<ModeloImpresora> valores;

	public PrintModelAdapter(Context context, int resource,List<ModeloImpresora> valores) {
		super(context, resource, valores);

        this.context = context;
        this.valores = valores;
	}
	
	public int getCount(){
		 return valores.size();
	}
	
	public ModeloImpresora getItem(int position){
		   return valores.get(position);
	}
	
	
	public View getView(int position, View convertView, ViewGroup parent){
		
		TextView label = new TextView(context);
		label.setTextColor(Color.BLACK);
		label.setText(valores.get(position).getDescripcion());
		
		return label;
	}
	
   public View getDropDownView(int position, View convertView, ViewGroup parent){
		
		TextView label = new TextView(context);
		label.setTextColor(Color.BLACK);
		label.setText(valores.get(position).getDescripcion());
		
		return label;
	}
   
   public void add(ModeloImpresora model){
	    super.add(model);
   }

}
