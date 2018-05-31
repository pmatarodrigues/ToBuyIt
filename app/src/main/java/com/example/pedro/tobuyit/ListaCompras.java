package com.example.pedro.tobuyit;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaCompras implements Serializable{

    private int id;
    private String texto;
    private Boolean concluido = false;

    public ListaCompras(int id, String texto, Boolean concluido) {
        this.id = id;
        this.texto = texto;
        this.concluido = concluido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String nome) {
        this.texto = texto;
    }

    public Boolean getConcluido() {
        return concluido;
    }

    public void setConcluido(Boolean concluido) {
        this.concluido = concluido;
    }

}
