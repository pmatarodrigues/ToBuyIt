package com.example.pedro.tobuyit;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
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

import java.util.ArrayList;

public class ComprasRecentesAdapter extends RecyclerView.Adapter<ComprasRecentesAdapter.MyViewHolder>{

    Activity context;
    ArrayList<Compra> comprasRecentes;
    private static LayoutInflater inflater = null;


    public ComprasRecentesAdapter(ArrayList<Compra> comprasRecentes){
        this.comprasRecentes = comprasRecentes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_lista_comprasrecentes, parent, false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tipoCompra.setText(comprasRecentes.get(position).getTipo());
        holder.precoCompra.setText(comprasRecentes.get(position).getPreco() + "€");
        holder.dataCompra.setText(comprasRecentes.get(position).getData().toString());
        if(holder.tipoCompra.getText().toString().equals("Supermercado")) {
            holder.iconCompra.setImageResource(R.drawable.icon_promocoes);
        } else{
            holder.iconCompra.setImageResource(R.drawable.icon_inicio);
        }
    }

    @Override
    public int getItemCount() {
        return comprasRecentes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tipoCompra;
        private TextView precoCompra;
        private TextView dataCompra;
        private ImageView iconCompra;

        private MyViewHolder(View itemView){
            super(itemView);
            tipoCompra = (TextView)itemView.findViewById(R.id.lista_comprasrecentes_tipo);
            precoCompra = (TextView) itemView.findViewById(R.id.lista_comprasrecentes_preco);
            dataCompra = (TextView) itemView.findViewById(R.id.lista_comprasrecentes_data);
            iconCompra = (ImageView) itemView.findViewById(R.id.lista_comprasrecentes_icon);

        }
    }

}

