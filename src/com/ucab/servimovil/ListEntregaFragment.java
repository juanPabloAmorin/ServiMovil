package com.ucab.servimovil;

import java.util.ArrayList;
import java.util.List;
import com.ucab.servimovil.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.ucab.servimovil.adapters.LocalTransactionAdapter;
import com.ucab.servimovil.dataAcces.ActivoDA;
import com.ucab.servimovil.dataAcces.TransaccionDA;
import com.ucab.servimovil.model.Activo;
import com.ucab.servimovil.model.LocalTransaction;
import com.ucab.servimovil.model.User;

public class ListEntregaFragment extends Fragment  {
	
	@Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_entrega_list, container, false);
    }
		
	@Override
	public void onActivityCreated(Bundle state){

    	super.onActivityCreated(state);

    	getActivity().getWindow().setSoftInputMode(
			    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
			);
    	
    	TextView title = (TextView) getView().findViewById(R.id.entregaTitle);
    	ListView listOpciones = (ListView) getView().findViewById(R.id.listEntrega);
    	
    	TransaccionDA transDa = new TransaccionDA();
    	List<LocalTransaction> listTransaction =  new ArrayList<LocalTransaction>();

    	listTransaction = transDa.getOutLocalTransactionsByUser(getActivity(),User.actualUser);
    		
    	if(listTransaction.size()>0){
	    				
	    	LocalTransactionAdapter tranAdapter = new LocalTransactionAdapter(getActivity(),
						android.R.layout.simple_spinner_item,listTransaction);		
			listOpciones.setAdapter(tranAdapter); 
    	}else{
    		title.setText(getActivity().getResources().getString(R.string.no_toner_out));
    	}
  
    	listOpciones.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View v, int position,long id) {

                LocalTransaction data = (LocalTransaction) av.getAdapter().getItem(position);
                                
                ActivoDA activoda = new ActivoDA();
                List<Activo> listActivo = activoda.getActivoByTransaction(data.getIdTransaccion(), getActivity());
                data.setActivos(listActivo);
                                
                EntregaTonerFragment entregaTonerFragment = new EntregaTonerFragment();
                Bundle newBundle = new Bundle();
                newBundle.putCharSequence("descripcion",data.getDescripcion());
                
                newBundle.putSerializable("localTransaction",data);
                     
                entregaTonerFragment.setArguments(newBundle);
			    FragmentTransaction transaction = getFragmentManager().beginTransaction();
			    transaction.replace(R.id.entregasLayout, entregaTonerFragment);
				transaction.commit();
			 
			}
    	   
    	});
       
	}

}
