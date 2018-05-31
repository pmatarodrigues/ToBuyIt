package com.example.pedro.tobuyit;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentListaCompras extends Fragment {


    public FragmentListaCompras() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_compras, container, false);

        MainActivity main = (MainActivity)getActivity();

        int num = main.num;

        ListaComprasAdapter adapter = new ListaComprasAdapter(main.users.get(num).getListasDeCompras());
        RecyclerView myView = (RecyclerView) view.findViewById(R.id.recycler_view_listacompras);
        myView.setHasFixedSize(true);
        myView.setAdapter(adapter);
        // ------------- UTILIZAR O LAYOUT MANAGER ------------- //
        LinearLayoutManager llm = new LinearLayoutManager(main);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        myView.setLayoutManager(llm);

        System.out.println(main.users.get(num).getListasDeCompras().size());

        for(int i = 0; i < main.users.size(); i++){
            System.out.println(main.users.get(i).getUsername());
        }

        return view;
    }

}
