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
public class FragmentPromocoes extends Fragment {


    public FragmentPromocoes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promocoes, container, false);

        MainActivity main = (MainActivity)getActivity();

        //main.addProdutosLista(view);

        ArrayList<Produto> produtosEmPromocao = new ArrayList<>();

        for(int i = 0; i < main.produtos.size(); i++){
            if(main.produtos.get(i).getEmPromocao()){
                produtosEmPromocao.add(main.produtos.get(i));
            }
        }

        PromocoesAdapter adapter = new PromocoesAdapter(produtosEmPromocao);
        RecyclerView myView = (RecyclerView) view.findViewById(R.id.recycler_view_promocoes);
        myView.setHasFixedSize(true);
        myView.setAdapter(adapter);
        // ------------- UTILIZAR O LAYOUT MANAGER ------------- //
        LinearLayoutManager llm = new LinearLayoutManager(main);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        myView.setLayoutManager(llm);

        System.out.println(produtosEmPromocao.size());

        return view;
    }

}
