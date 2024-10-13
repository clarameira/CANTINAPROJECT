package com.example.aplicacoes;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Cardapio {
    private List<ItemCard> itens;

    public Cardapio() {
        itens = new ArrayList<>();
    }

    public void adicionarItem(ItemCard item) {
        itens.add(item);
    }

    public boolean removerItem(String nome) {
        return itens.removeIf(item -> item.getItem().equalsIgnoreCase(nome));
    }

    public void exibir() {
        if (itens.isEmpty()) {
            JOptionPane.showMessageDialog(null, "O cardápio está vazio.");
            return;
        }

        StringBuilder sb = new StringBuilder("Itens do Cardápio:\n");
        for (ItemCard item : itens) {
            sb.append(item.getItem()).append(" - ").append(item.getPreco()).append(" R$\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }
    
    public List<ItemCard> getItens() {
        return itens;
    }
}
