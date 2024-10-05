package com.example.aplicacoes;

import java.util.ArrayList;
import java.util.List;

public class Cardapio {
    
    public List<ItemCard> itens;

    public Cardapio() {
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(ItemCard item) {
        itens.add(item);
    }

    public void removerItem(ItemCard item) {
        itens.remove(item);
    }

    public void exibir() {
        System.out.println("\n***************************************************************************");
        System.out.println("                         CARDÁPIO SEMANAL:");
        System.out.println("*****************************************************************************");
        for (ItemCard item : itens) {
            System.out.println("- " + item.getItem() + ": " + item.getDescricao() + " - R$" + item.getPreco());
        }
        System.out.println("-----------------------------------------------------------------------------");
    }

    public ItemCard buscarItem(String nome) {
        for (ItemCard item : itens) {
            if (item.getItem().equalsIgnoreCase(nome)) {
                return item;
            }
        }
        return null;
    }
}
