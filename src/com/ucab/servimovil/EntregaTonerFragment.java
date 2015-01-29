package com.ucab.servimovil;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import com.ucab.servimovil.R;
import com.ucab.servimovil.adapters.ActivoAdapter;
import com.ucab.servimovil.dataAcces.TransaccionDA;
import com.ucab.servimovil.model.Activo;
import com.ucab.servimovil.model.LocalTransaction;
import com.ucab.servimovil.model.User;
import com.ucab.servimovil.utils.Utils;
import android.content.Context;
import android.graphics.*;
import android.graphics.Paint.Style;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.widget.LinearLayout.LayoutParams;

public class EntregaTonerFragment extends Fragment {
	
	 private LinearLayout mContent;
	 private signature mSignature;
	 private Button mClear, mGetSign;
	 private Bitmap mBitmap;
	 private View mView;
	 private Spinner activoSpinner; 	
	 private LocalTransaction localTransaction; 
	 private LinearLayout layout1;
	 private LinearLayout layout2;
	 private Button solRegButton;
	 private EditText solucionText;
	 private RadioGroup radioGroup;
	
	@Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
		
		layout2 = (LinearLayout) inflater.inflate( R.layout.no_entregado_layout, null );
		solRegButton = (Button) layout2.findViewById(R.id.registerSolButton);
		solucionText = (EditText) layout2.findViewById(R.id.solucionText);
		radioGroup = (RadioGroup)  layout2.findViewById(R.id.solRadioGroup);
        return inflater.inflate(R.layout.fragment_entrega, container, false);
    }
		
	@Override
	public void onActivityCreated(Bundle state){

    	super.onActivityCreated(state);
    	
    	getActivity().getWindow().setSoftInputMode(
			    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
			);
    	
    	localTransaction = new LocalTransaction();
    	
    	localTransaction = (LocalTransaction) getArguments().getSerializable("localTransaction");
    	localTransaction.setIdUsuario(User.actualUser);
 
	
    	EditText dependenciaText = (EditText) getView().findViewById(R.id.dependenciaEntregaText);
    	dependenciaText.setText(localTransaction.getDependencia());
    	
    	EditText solicitanteText = (EditText) getView().findViewById(R.id.solicitanteEntregaText);
    	solicitanteText.setText(localTransaction.getSolicitante());
    	
    	EditText modeloImpresoraText = (EditText) getView().findViewById(R.id.modeloEntregaText);
    	modeloImpresoraText.setText(localTransaction.getModeloImpresora());
    	
    	EditText tonerText = (EditText) getView().findViewById(R.id.tonerEntregaText);
    	tonerText.setText(localTransaction.getModeloToner() + "-" + localTransaction.getSerialToner());
    	
    	layout1 = (LinearLayout) getView().findViewById(R.id.linearLayoutContentFirma);    	
    	
    	activoSpinner = (Spinner) getView().findViewById(R.id.activoSpinner);
    	
    	ActivoAdapter activoAdapter = null;
    	
		 
		if(localTransaction.getActivos().size() == 1){
			activoSpinner.setClickable(false);
			 
			activoAdapter = new ActivoAdapter(getActivity(),android.R.layout.simple_spinner_item,localTransaction.getActivos());
		
		 }else{
			 Activo defaultMI = new Activo();
		     defaultMI.setIdActivo(0);
			 defaultMI.setNombre(getActivity().getResources().getString(R.string.spinner_default));
				
			 List<Activo>  activosList = new ArrayList<Activo>();
			 activosList.add(defaultMI);
			
			 for(int i=0;i<localTransaction.getActivos().size();i++){
				
				Activo activo = localTransaction.getActivos().get(i);
				activosList.add(activo);
			 }
			
			 activoAdapter = new ActivoAdapter(getActivity(),android.R.layout.simple_spinner_item,activosList);
				
		 }
		 
		 activoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
		 activoSpinner.setAdapter(activoAdapter); 
		 
		 RadioGroup rg = (RadioGroup)getView().findViewById(R.id.radioGroup);
		 rg.check(R.id.radioentregado);
     

         RectShape rect = new RectShape();
         ShapeDrawable rectShapeDrawable = new ShapeDrawable(rect);
         Paint paint = rectShapeDrawable.getPaint();
         paint.setColor(Color.GRAY);
         paint.setStyle(Style.STROKE);
         paint.setStrokeWidth(5);
        
         mContent = (LinearLayout) getView().findViewById(R.id.linearLayoutSign);
                
         mSignature = new signature(getActivity(), null);
         mSignature.setBackgroundColor(Color.WHITE);
         mContent.addView(mSignature, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
         mClear = (Button) getView().findViewById(R.id.clear);
         mGetSign = (Button) getView().findViewById(R.id.getsign);
         mGetSign.setEnabled(false);
         mView = mContent;


         mClear.setOnClickListener(new View.OnClickListener() 
         {        
            public void onClick(View v) 
            {
                mSignature.clear();
                mGetSign.setEnabled(false);
            }
         });

         mGetSign.setOnClickListener(new View.OnClickListener() 
         {        
            public void onClick(View v) 
            {
               
                    mView.setDrawingCacheEnabled(true);
                    mSignature.save(mView);

            }
         });
        
        
         rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        	       
        	 public void onCheckedChanged(RadioGroup group, int checkedId) {
        	        	        	               	     
        	            if(checkedId == R.id.radioentregado){
        	            
        	            	LinearLayout root = (LinearLayout) getView().findViewById(R.id.linearLayoutRoot);
        	            	root.removeView(layout2);
        	            	root.addView(layout1);
        	            	
        	            
        	            }else  if(checkedId == R.id.radioNoEntregado){

        	            	LinearLayout root = (LinearLayout) getView().findViewById(R.id.linearLayoutRoot);
                            root.removeView(layout1);
                            root.addView(layout2);
                           
                         	radioGroup.check(R.id.radioSiSol);
                           
        	            }
        	        }
        	    });
        
         solRegButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
	
	        	EditText observacionesText = (EditText) getView().findViewById(R.id.observacionesEntregaText);
	        	
	        	Activo activoSelected = (Activo) activoSpinner.getSelectedItem();
	        	
	        	String message = "";
	    
	        
	             if(activoSelected.getIdActivo() == 0){
	            	 
	            	 message = message + "Activo Fijo.\n";
	             }
	             
	             if(radioGroup.getCheckedRadioButtonId() == R.id.radioSiSol){
		             if(solucionText.getText().toString().equals("")){
		            	 
		            	 message = message + "Solución.\n";
		             }
	             }
	        	
	        	if(message.equals("")){
	        			      
			            try 
			            {
			

			                localTransaction.setObservaciones(observacionesText.getText().toString());			                
			                localTransaction.setStatus("COMPLETE");
			                localTransaction.setTipoTransaccion("N");
			                localTransaction.setIdActivo(activoSelected.getIdActivo());
			                
			                if(radioGroup.getCheckedRadioButtonId() == R.id.radioSiSol){
			                	localTransaction.setIsSolved("S");
			                }else{
			                	localTransaction.setIsSolved("N");
			                }
			                
			                localTransaction.setSolucion(solucionText.getText().toString());
			                
			                TransaccionDA transDa = new TransaccionDA();
			                transDa.updateTransaccion(localTransaction,getActivity());
			                
			                if(radioGroup.getCheckedRadioButtonId() == R.id.radioSiSol){
			                    Utils.alertMessage("La solicitud "+localTransaction.getIdReporte()+" se ha completado con éxito.",getActivity());
			                }else{
			                    Utils.alertMessage("La solicitud "+localTransaction.getIdReporte()+" volverá a estar disponible para ser atendida" +
			                    		" luego de sincronizar el dispositivo.",getActivity());

			                }
	   					 
		   					 ListEntregaFragment entregaFragment = new ListEntregaFragment();
		   					 
		   					 FragmentTransaction transaction = getFragmentManager().beginTransaction();
		   					 transaction.replace(R.id.entregasLayout,entregaFragment);
		   					 transaction.commit(); 
			               
			   
			            }
			            catch(Exception e) 
			            { 
			                Log.v("log_tag", e.toString()); 
			                Toast.makeText(getActivity(),"Ha ocurrido un error de conexión con la base de datos.",Toast.LENGTH_LONG).show();
			            } 
			            
	        	}else{
	        		Utils.alertMessage("Por favor indique la información requerida:\n"+message,getActivity());
	        	}
				
			}
		});

     
    }

	
	public class signature extends View 
    {
        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature(Context context, AttributeSet attrs) 
        {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void save(View v) 
        {
        
        	EditText contadorText = (EditText) getView().findViewById(R.id.contadorEditText);
        	EditText receptorText = (EditText) getView().findViewById(R.id.receptorText);
        	EditText observacionesText = (EditText) getView().findViewById(R.id.observacionesEntregaText);
        	
        	String contador = contadorText.getText().toString();
        	String receptor = receptorText.getText().toString();
        	
        	Activo activoSelected = (Activo) activoSpinner.getSelectedItem();
        	
        	String message = "";
    
             if(contador == null || contador.equals("")){
        		
        		message = message + "Contador actual.\n";
        	}
             
             if(receptor == null || receptor.equals("")){
         		
         		message = message + "Recibido por.\n";
         	}
             
             if(activoSelected.getIdActivo() == 0){
            	 
            	 message = message + "Activo Fijo.\n";
             }
        	
        	if(message.equals("")){
        			
        		try{
        			
        			int contadorNumber = Integer.parseInt(contador);
        			if(contadorNumber >= activoSelected.getContador())
        			{
	        					
				            if(mBitmap == null)
				            {
				                mBitmap =  Bitmap.createBitmap (mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);
				            }
				            Canvas canvas = new Canvas(mBitmap);
				            try 
				            {
				
				                v.draw(canvas); 
				                
				                byte[] bArray = getImageArray(mBitmap);
				                
				                localTransaction.setContador(contadorNumber);     
				                localTransaction.setObservaciones(observacionesText.getText().toString());
				                localTransaction.setFirma(bArray);             
				                localTransaction.setStatus("COMPLETE");
				                localTransaction.setTipoTransaccion("E");
				                localTransaction.setReceptor(receptor);
				                localTransaction.setIdActivo(activoSelected.getIdActivo());
				                localTransaction.setIsSolved("S");
				                localTransaction.setSolucion("CARTUCHO INSTALADO");
				                
				                TransaccionDA transDa = new TransaccionDA();
				                transDa.updateTransaccion(localTransaction,getActivity());
				                
				                Utils.alertMessage("La solicitud "+localTransaction.getIdReporte()+" se ha completado con éxito.",getActivity());
		   					 
				        		int count = transDa.getLocalTransactionCount(getActivity());
				        		
				        		if(count != 0){
				        			Utils.generateSyncNotification(getActivity(),String.valueOf(count));
				        		}
				                
			   					 ListEntregaFragment entregaFragment = new ListEntregaFragment();
			   					 
			   					 FragmentTransaction transaction = getFragmentManager().beginTransaction();
			   					 transaction.replace(R.id.entregasLayout,entregaFragment);
			   					 transaction.commit(); 
				      
				
				            }
				            catch(Exception e) 
				            { 
				                Log.v("log_tag", e.toString()); 
				                Toast.makeText(getActivity(),"Ha ocurrido un error de conexión con la base de datos.",Toast.LENGTH_LONG).show();
				            } 
        			
        			}else{
        				Log.e("error en contador","contador del activo menor que el anterior");
        				Utils.alertMessage("El contador actual debe ser mayor al contador anterior. Verifique si el activo es correcto.", getActivity());
        			}
        		
        		}catch(Exception e){
        			 Log.e("numberformat exception","error en el contador");
        		}
		            
        	}else{
        		Utils.alertMessage("Por favor indique la información requerida:\n"+message,getActivity());
        	}
        }

        public void clear() 
        {
            path.reset();
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) 
        {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) 
        {
            float eventX = event.getX();
            float eventY = event.getY();
            mGetSign.setEnabled(true);

            switch (event.getAction()) 
            {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX, eventY);
                lastTouchX = eventX;
                lastTouchY = eventY;
                return true;

            case MotionEvent.ACTION_MOVE:

            case MotionEvent.ACTION_UP:

                resetDirtyRect(eventX, eventY);
                int historySize = event.getHistorySize();
                for (int i = 0; i < historySize; i++) 
                {
                    float historicalX = event.getHistoricalX(i);
                    float historicalY = event.getHistoricalY(i);
                    expandDirtyRect(historicalX, historicalY);
                    path.lineTo(historicalX, historicalY);
                }
                path.lineTo(eventX, eventY);
                break;

            default:
                return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

      
        private void expandDirtyRect(float historicalX, float historicalY) 
        {
            if (historicalX < dirtyRect.left) 
            {
                dirtyRect.left = historicalX;
            } 
            else if (historicalX > dirtyRect.right) 
            {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) 
            {
                dirtyRect.top = historicalY;
            } 
            else if (historicalY > dirtyRect.bottom) 
            {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) 
        {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }
	
	
	private byte[] getImageArray(Bitmap bitmap){
	
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
       
        return b;
		
	}

}
