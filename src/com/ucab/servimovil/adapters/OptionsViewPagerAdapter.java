package com.ucab.servimovil.adapters;

import com.ucab.servimovil.ListEntregaFragment;
import com.ucab.servimovil.NuevoTonerFragment;
import com.ucab.servimovil.SalidaFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class OptionsViewPagerAdapter extends FragmentStatePagerAdapter  {
	
	 public OptionsViewPagerAdapter(FragmentManager fm) {
	        super(fm);
	    }

	    @Override
	    public Fragment getItem(int index) {

	        switch (index) {
	        case 0:
	            return new NuevoTonerFragment();
	        case 1:
	            return new SalidaFragment();
	        case 2:
	            return new ListEntregaFragment();
	        }

	        return null;
	    }

	    @Override
	    public int getCount() {
	        return 3;
	    }
	    
	    @Override
	    public int getItemPosition(Object object) {
	       return POSITION_NONE;
	    }

}
