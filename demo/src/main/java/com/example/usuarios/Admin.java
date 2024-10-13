package com.example.usuarios;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

import com.example.DAO.ItemDao;
import com.example.aplicacoes.Cardapio;
import com.example.aplicacoes.ItemCard;

public class Admin {
    private String login;
    private String senha;
    private Cardapio cardapio;
    private int cont;

    public Admin(String login, String senha) {
        this.login = login;
        this.senha = senha;
        this.cardapio = new Cardapio();
        this.cont = 0; 
    }

    // Getters
    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public void setCardapio(Cardapio cardapio) {
        this.cardapio = cardapio;
    }

    public void exibirMenuAdmin() throws SQLException {
        // Criar a janela do menu do administrador
        JFrame menuFrame = new JFrame("Menu Administrador");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(400, 300);
        menuFrame.setLayout(new GridLayout(0, 1));
    
        // Adicionar botão para exibir cardápio
        JButton exibirCardapioButton = new JButton("Exibir cardápio");
        exibirCardapioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (cardapio != null) {
                        if (cont == 0) {
                            cont++;
                            ItemDao.pegarTodos(cardapio);
                        }
                        cardapio.exibir(); // Chama o método para exibir cardápio
                    } else {
                        JOptionPane.showMessageDialog(menuFrame, "O cardápio não está disponível.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(menuFrame, "Erro ao carregar cardápio: " + ex.getMessage());
                }
            }
        });
        menuFrame.add(exibirCardapioButton);
    
        // Adicionar botão para adicionar item ao cardápio
        JButton adicionarItemButton = new JButton("Adicionar ao cardápio");
        adicionarItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    adicionarItemCardapio(); // Adiciona item ao cardápio
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(menuFrame, "Erro ao adicionar item: " + ex.getMessage());
                }
            }
        });
        menuFrame.add(adicionarItemButton);
    
        // Adicionar botão para editar item no cardápio
        JButton editarItemButton = new JButton("Editar item no cardápio");
        editarItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarItemCardapio(); // Implementar este método para edição
            }
        });
        menuFrame.add(editarItemButton);
    
        // Adicionar botão para remover item do cardápio
        JButton removerItemButton = new JButton("Remover do cardápio");
        removerItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerItemCardapio(); 
            }
        });
        menuFrame.add(removerItemButton);
    
        // Adicionar botão de sair
        JButton sairButton = new JButton("Sair");
        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose(); // Fecha a janela do menu
                JOptionPane.showMessageDialog(null, "Saindo.");
            }
        });
        menuFrame.add(sairButton);
    
        // Exibir a janela do menu
        menuFrame.setVisible(true);
    }

    private void adicionarItemCardapio() throws SQLException {
        String nome = JOptionPane.showInputDialog("Digite o nome do item:");
        String descricao = JOptionPane.showInputDialog("Digite a descrição do item:");
        
        double preco;
        while (true) {
            String precoInput = JOptionPane.showInputDialog("Digite o preço do item:");
            if (precoInput == null) return; // Se o usuário cancelar, sai do método
            try {
                preco = Double.parseDouble(precoInput);
                break; // Sai do loop se a conversão for bem-sucedida
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Preço inválido. Tente novamente.");
            }
        }
        
        ItemCard novoItem = new ItemCard(nome, descricao, preco);
        cardapio.adicionarItem(novoItem);
        ItemDao.inserirItem(novoItem);
        JOptionPane.showMessageDialog(null, "Item adicionado com sucesso!");
    }

    private void editarItemCardapio() {
        
        JOptionPane.showMessageDialog(null, "Funcionalidade de edição não implementada ainda.");
    }

    private void removerItemCardapio() {
        String nome = JOptionPane.showInputDialog("Digite o nome do item que deseja remover:");
        if (nome == null || nome.trim().isEmpty()) return; // Verifica se o usuário cancelou ou não digitou nada
    
        boolean removido = cardapio.removerItem(nome);
        if (removido) {
            try {
                ItemDao.removerItem(nome); // Remover do banco de dados, utilizando o nome
                JOptionPane.showMessageDialog(null, "Item removido com sucesso!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao remover item do banco de dados: " + e.getMessage());
                e.printStackTrace(); 
            }
        } else {
            JOptionPane.showMessageDialog(null, "Item não encontrado no cardápio.");
        }
    }
    
    
}



