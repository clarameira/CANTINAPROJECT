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

    // Getters
    public String getItem() {
        return item;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return String.format("%s - %s - R$ %.2f", item, descricao, preco);
    }
}
