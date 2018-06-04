package com.example.pedro.tobuyit;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAMinhaConta extends Fragment {


    public FragmentAMinhaConta() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aminha_conta, container, false);

        MainActivity main = (MainActivity)getActivity();

        TextView olaUsername = view.findViewById(R.id.aminhaconta_olausername);
        TextView comprasRecentesVazio = view.findViewById(R.id.aminhaconta_comprasrecentes_vazio);

        olaUsername.setText("Olá " + main.users.get(main.num).getUsername() + "!\nBem-vindo à página de configuração da sua conta.");

        if(main.users.get(main.num).getComprasRecentes().size()!=0){
            comprasRecentesVazio.setVisibility(View.INVISIBLE);
        }

        ComprasRecentesAdapter adapter = new ComprasRecentesAdapter(main.users.get(main.num).getComprasRecentes());
        RecyclerView myView = (RecyclerView) view.findViewById(R.id.recycler_view_comprasrecentes);
        myView.setHasFixedSize(true);
        myView.setAdapter(adapter);
        // ------------- UTILIZAR O LAYOUT MANAGER ------------- //
        LinearLayoutManager llm = new LinearLayoutManager(main);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        myView.setLayoutManager(llm);

        return view;
    }

}
