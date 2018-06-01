package com.example.pedro.tobuyit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pedro.tobuyit.*;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class ListaComprasAdapter extends RecyclerView.Adapter<ListaComprasAdapter.MyViewHolder>{

    Activity context;
    ArrayList<ListaCompras> listaCompras;
    private static LayoutInflater inflater = null;


    public ListaComprasAdapter(ArrayList<ListaCompras> listaCompras){
        this.listaCompras = listaCompras;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_lista_listacompras, parent, false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.compra.setText(listaCompras.get(position).getTexto());
        if(listaCompras.get(position).getConcluido()){
            holder.compra.setTextColor(Color.parseColor("#B0BEC5"));
            holder.compra.setPaintFlags(holder.compra.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        holder.compra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaCompras.get(position).setConcluido(true);
                System.out.println(listaCompras.get(position).getConcluido());
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaCompras.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView compra;

        private MyViewHolder(View itemView){
            super(itemView);
            compra = (TextView) itemView.findViewById(R.id.lista_listacompras_nome);

        }
    }


}

