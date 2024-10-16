package com.example.aplicacoes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

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

    public void exibirCardapio() {
        if (itens.isEmpty()) {
            JOptionPane.showMessageDialog(null, "O cardápio está vazio.");
            return;
        }

        JFrame cardapioFrame = new JFrame("CARDÁPIO");
        cardapioFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cardapioFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Tela cheia
        cardapioFrame.setLayout(new BorderLayout());

        String[] colunas = { "ITEM", "DESCRIÇÃO", "PREÇO (R$)" };
        DefaultTableModel tableModel = new DefaultTableModel(colunas, 0);

        // Set para evitar duplicatas
        Set<String> itensExibidos = new HashSet<>();

        // Preencher a tabela com os itens do cardápio
        for (ItemCard item : itens) {
            if (!itensExibidos.contains(item.getItem())) {
                Object[] rowData = { item.getItem(), item.getDescricao(), String.format("%.2f", item.getPreco()) };
                tableModel.addRow(rowData);
                itensExibidos.add(item.getItem());
            }
        }

        JTable tabela = new JTable(tableModel);
        tabela.setFont(new Font("SansSerif", Font.PLAIN, 16));
        tabela.setRowHeight(30);
        tabela.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 18));
        tabela.getTableHeader().setBackground(Color.WHITE);
        tabela.getTableHeader().setForeground(new Color(255, 165, 0));
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Personalizando as cores das linhas da tabela
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    c.setBackground(new Color(255, 228, 196));
                } else {
                    c.setBackground(Color.WHITE);
                }
                c.setForeground(Color.BLACK);
                return c;
            }
        };

        // Aplica o renderer às células da tabela
        for (int i = 0; i < tabela.getColumnCount(); i++) {
            tabela.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        // Adiciona a tabela dentro de um JScrollPane para habilitar rolagem
        JScrollPane scrollPane = new JScrollPane(tabela);
        cardapioFrame.add(scrollPane, BorderLayout.CENTER);

        cardapioFrame.setVisible(true);
    }

    public ItemCard[] getItens() {
        return itens.toArray(new ItemCard[0]); // Converte a lista de itens em um array
    }

}
