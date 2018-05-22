package com.example.pedro.tobuyit;

import java.io.Serializable;

public class Produto implements Serializable{

    private int id;
    private String nome;
    private float preco;
    private int imagem;
    private Boolean emPromocao;
    private String aviso;
    private String curiosidade;
    private Boolean esgotado;

    public Produto(int id, String nome, float preco, int imagem, Boolean emPromocao, String aviso, String curiosidade) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.imagem = imagem;
        this.emPromocao = emPromocao;
        this.aviso = aviso;
        this.curiosidade = curiosidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public Boolean getEmPromocao() {
        return emPromocao;
    }

    public void setEmPromocao(Boolean emPromocao) {
        this.emPromocao = emPromocao;
    }

    public String getAviso() {
        return aviso;
    }

    public void setAviso(String aviso) {
        this.aviso = aviso;
    }

    public String getCuriosidade() {
        return curiosidade;
    }

    public void setCuriosidade(String curiosidade) {
        this.curiosidade = curiosidade;
    }

    public Boolean getEsgotado() {
        return esgotado;
    }

    public void setEsgotado(Boolean esgotado) {
        this.esgotado = esgotado;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }
}
