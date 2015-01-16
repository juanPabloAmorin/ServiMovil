package com.ucab.servimovil.adapters;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.ucab.servimovil.R;
import com.ucab.servimovil.dataAcces.TonerDA;
import com.ucab.servimovil.model.TonerTemp;

public class RegisterListAdapter extends ArrayAdapter<TonerTemp> {
		
	private Activity context;
	private List<TonerTemp> valores;


	public RegisterListAdapter(Activity context, int resource,List<TonerTemp> valores) {
		super(context, resource, valores);

        this.context = context;
        this.valores = valores;
	}

	public int getCount(){
		 return valores.size();
	}
	
	public TonerTemp getItem(int position){
		   return valores.get(position);
	}
	

	
	public View getView(int position, View convertView, ViewGroup parent){
		
        View listItem = convertView;
		
		if(listItem == null)
	    {
        	LayoutInflater inflater = context.getLayoutInflater();
            listItem = inflater.inflate(R.layout.register_list_item_layout, null);
	    }
        
		TextView title = (TextView) listItem.findViewById(R.id.nombreTonerEditText);
		title.setText("Cartucho Nro " + (position+1));
		
		EditText modeloText = (EditText) listItem.findViewById(R.id.modeloRegEditText);
		modeloText.setText(valores.get(position).getModelo());
		
		EditText serialText = (EditText) listItem.findViewById(R.id.serialEditTextReg);
        serialText.setText(valores.get(position).getSerial());
        
        Button removeButton = (Button) listItem.findViewById(R.id.buttonRemove);
        removeButton.setTag(position);
        
        removeButton.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    	
                    	Integer index = (Integer) v.getTag();
                    	String serial = valores.get(index.intValue()).getSerial();
                    	
                    	TonerDA tonerDa = new TonerDA();
                    	tonerDa.deleteTonerTempBySerial(v.getContext(), serial);
                    	
                        valores.remove(index.intValue());  
                        notifyDataSetChanged();
                        
                        
                    }
                }
            );
        

        if ( position % 2 == 0)
        	listItem.setBackgroundResource(R.drawable.list_view_borders_impar);
        else
        	listItem.setBackgroundResource(R.drawable.list_view_borders_par);
 
		return listItem; 
	}
	
  
}
