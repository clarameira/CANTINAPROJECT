package com.example.aplicacoes;

public class ItemCard {
    
    private String descricao;
    private double preco;
    private String item;

    public ItemCard(String item, String descricao, double preco) {
        this.item = item;
        this.descricao = descricao;
        this.preco = preco;
    }

    public ItemCard(String item2, double preco2) {
        this.item =item2;
        this.preco = preco2;
    }

    public String getItem() {
        return item;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return String.format("%s - %s - R$ %.2f", item, descricao, preco);
    }

}
