package com.example.pedro.tobuyit;

import java.util.ArrayList;

public class Carrinho {

    private ArrayList<Produto> produtos;
    private float precoTotal;

    public Carrinho() {
        this.produtos = produtos;
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    public float getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(float precoTotal) {
        this.precoTotal = precoTotal;
    }

    public int getTotalProdutos(){
        return produtos.size();
    }

    public void addProduto(Produto p){
        produtos.add(p);
    }
}
