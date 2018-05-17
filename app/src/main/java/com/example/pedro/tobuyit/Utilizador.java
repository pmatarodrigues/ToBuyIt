package com.example.pedro.tobuyit;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Utilizador implements Serializable {
    private String username;
    private String password;
    private Boolean ativo;
    private String morada;
    private ArrayList<ListaCompras> listasDeCompras;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public ArrayList<ListaCompras> getListasDeCompras() {
        return listasDeCompras;
    }

    public void setListasDeCompras(ArrayList<ListaCompras> listasDeCompras) {
        this.listasDeCompras = listasDeCompras;
    }


}
