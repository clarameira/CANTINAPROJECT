package com.example.aplicacoes;

import java.util.List;

public class Pedido {
    private int clienteId;
    private String nomeCliente;
    private double totalPreco;
    private List<ItemCard> itens; // Lista de itens do pedido

    // Construtor que aceita o ID do cliente, nome do cliente e lista de itens do cardápio
    public Pedido(int clienteId, String nomeCliente, List<ItemCard> itens) {
        this.clienteId = clienteId;
        this.nomeCliente = nomeCliente;
        this.itens = itens;
    }

    public int getClienteId() {
        return clienteId;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public List<ItemCard> getItens() {
        return itens;
    }

    // Método para obter o nome do item selecionado
    public String getItemNome() {
        if (!itens.isEmpty()) {
            return itens.get(0).getItem(); // Retorna o nome do item da lista
        }
        return ""; // Retorna vazio se não houver itens
    }

    public double getTotalPreco() {
        return totalPreco;
    }
    public double calcularTotal() {
        return itens.stream().mapToDouble(ItemCard::getPreco).sum();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido do Cliente: ").append(nomeCliente)
          .append(" (ID: ").append(clienteId).append(")\n")
          .append("Itens do Pedido:\n");
        for (ItemCard item : itens) {
            sb.append("- ").append(item.toString()).append("\n");
        }
        sb.append("Preço Total: R$ ").append(String.format("%.2f", totalPreco));
        return sb.toString();
    }
}
