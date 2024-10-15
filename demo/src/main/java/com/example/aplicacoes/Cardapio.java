package com.example.aplicacoes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        Set<String> itensExibidos = new HashSet<>(); // Usar um Set para evitar duplicatas
        StringBuilder sb = new StringBuilder("Itens do Cardápio:\n");
        for (ItemCard item : itens) {
            if (!itensExibidos.contains(item.getItem())) { // Verificar se o item já foi exibido
                sb.append(item.getItem()).append(" - ").append(item.getDescricao()).append(" - ").append(item.getPreco()).append(" R$\n");
                itensExibidos.add(item.getItem()); // Adicionar o item ao conjunto para evitar duplicatas
            }
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public ItemCard[] getItens() {
        // Supondo que 'itens' é uma List<ItemCard>
        return itens.toArray(new ItemCard[0]); // Converte a lista de itens em um array
    }

}
