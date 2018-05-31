package com.example.pedro.tobuyit;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCarrinho extends Fragment {


      //MainActivity main = new MainActivity();
    //MainActivity main = (MainActivity)getActivity();
    ArrayList<Produto> produtosAConfirmar = new ArrayList<>();


    public FragmentCarrinho() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrinho, container, false);

        MainActivity main = (MainActivity)getActivity();

        try {
            main.carregarCarrinho(view);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // --------------- CALCUlAR PRECO TOTAL DO CARRINHO ---------------//
        TextView precoCarrinho = view.findViewById(R.id.carrinho_preco_total);
        float precoTotalCarrinho = 0;
                    // --- percorre todos os carrinhos
        for (int i = 0; i< main.produtosNoCarrinho.size(); i++) {
                        // -------- recebe o preço dos produtos e adiciona ao total
            precoTotalCarrinho += main.produtosNoCarrinho.get(i).getPreco();
            produtosAConfirmar.add(main.produtosNoCarrinho.get(i));
        }
        precoCarrinho.setText(precoTotalCarrinho + "€");


        // Inflate the layout for this fragment
        return view;
    }

}
