package com.example.pedro.tobuyit;

import android.app.Activity;
import android.content.Context;
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

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.MyViewHolder>{

    Activity context;
    ArrayList<Produto> produtosNoCarrinho;
    private static LayoutInflater inflater = null;


    public CarrinhoAdapter(ArrayList<Produto> produtosNoCarrinho){
        this.produtosNoCarrinho = produtosNoCarrinho;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_lista_carrinho, parent, false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nomeProduto.setText(produtosNoCarrinho.get(position).getNome());
        holder.precoProduto.setText(produtosNoCarrinho.get(position).getPreco() + "€");
        holder.qtdProduto.setText(String.valueOf(produtosNoCarrinho.get(position).getQtdNoCarrinho()));
        holder.imagemProduto.setImageResource(produtosNoCarrinho.get(position).getImagem());

        if(produtosNoCarrinho.get(position).getEmPromocao()){
            holder.iconPromocoes.setVisibility(View.VISIBLE);
        } else{
            holder.iconPromocoes.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return produtosNoCarrinho.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nomeProduto;
        private TextView precoProduto;
        private TextView qtdProduto;

        private ImageView imagemProduto;
        private ImageView iconPromocoes;
        private MyViewHolder(View itemView){
            super(itemView);
            nomeProduto = (TextView)itemView.findViewById(R.id.lista_carrinho_nome);
            precoProduto = (TextView) itemView.findViewById(R.id.lista_carrinho_preco);
            qtdProduto = (TextView) itemView.findViewById(R.id.lista_carrinho_quantidade);

            imagemProduto = (ImageView) itemView.findViewById(R.id.lista_carrinho_imagem);

            iconPromocoes = (ImageView) itemView.findViewById(R.id.lista_carrinho_icon_promocao);
        }
    }

}

