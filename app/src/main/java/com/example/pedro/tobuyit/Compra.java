package com.example.pedro.tobuyit;

import java.util.Date;

public class Compra {

    private Utilizador user;
    private float preco;
    private Date data;
    private String tipo;

    public Compra(Utilizador user, float preco, Date data) {
        this.user = user;
        this.preco = preco;
        this.data = data;
    }

    public Utilizador getUser() {
        return user;
    }

    public void setUser(Utilizador user) {
        this.user = user;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
