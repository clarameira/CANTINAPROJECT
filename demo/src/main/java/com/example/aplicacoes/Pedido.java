package com.example.aplicacoes;

public class Pedido {
    
    private String item;
    private String nomeCliente;
    private int id;

    public Pedido(String item, String nomeCliente, int id){
        this.item = item;
        this.nomeCliente = nomeCliente;
        this.id = id;
    }

    public String getitem() {
        return item;
    }
    public String getnomeCliente() {
        return nomeCliente;
    }
    public int getid() {
        return id;
    }
}
