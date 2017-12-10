package com.hfad.bitsandpizzas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PastaFragment extends Fragment {

    public PastaFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView pastaRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_pasta, container, false);

        String[] pastaNames = new String[Pasta.pastas.length];
        int[] pastaImages = new int[Pasta.pastas.length];

        for (int i = 0; i < Pasta.pastas.length; i++) {
            pastaNames[i] = Pasta.pastas[i].getName();
            pastaImages[i] = Pasta.pastas[i].getImageResourceId();
        }

        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(pastaNames, pastaImages);
        pastaRecycler.setAdapter(adapter);

        adapter.setClickListener(new ClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), PastaDetailActivity.class);
                intent.putExtra(PastaDetailActivity.EXTRA_PASTA_ID, position);
                getActivity().startActivity(intent);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        pastaRecycler.setLayoutManager(gridLayoutManager);

        return pastaRecycler;
    }

}
