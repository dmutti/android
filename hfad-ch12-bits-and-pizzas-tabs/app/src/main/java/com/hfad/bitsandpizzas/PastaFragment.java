package com.hfad.bitsandpizzas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class PastaFragment extends ListFragment {

    private static final String[] pastas = { "Spaghetti Bolognese", "Lasagne" };

    public PastaFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, pastas);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
