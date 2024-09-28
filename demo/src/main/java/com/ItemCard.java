package com;

public class ItemCard {
    private String item;
    private String descricao;
    private double preco;

    public ItemCard(String dia, String descricao, double preco) {
        this.item = dia;
        this.descricao = descricao;
        this.preco = preco;
    }

    public String getItem() {
        return item;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "ItemCard{" +
                "ITEM ='" + item + '\'' +
                ", DESCRIÇÃO ='" + descricao + '\'' +
                ", PREÇO =" + preco +
                '}';
    }
}