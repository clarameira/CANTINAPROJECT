package com.example.usuarios;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
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
        menuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);  // Abre em tela cheia
        menuFrame.getContentPane().setBackground(Color.WHITE); // Fundo branco
        
        // Painel para organizar os botões verticalmente
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.WHITE); // Fundo branco do painel
        
        // Tamanho preferido dos botões
        Dimension buttonSize = new Dimension(1500, 300); 
        
        // Adicionar botão para exibir cardápio
        JButton exibirCardapioButton = criarBotao("Exibir Cardápio", "caminho/para/imagem_exibir.png", buttonSize);
        exibirCardapioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (cardapio != null) {
                        if (cont == 0) {
                            cont++;
                            ItemDao.pegarTodos(cardapio);
                        }
                        cardapio.exibirCardapio(); // Chama o método para exibir cardápio
                    } else {
                        JOptionPane.showMessageDialog(menuFrame, "O cardápio não está disponível.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(menuFrame, "Erro ao carregar cardápio: " + ex.getMessage());
                }
            }
        });
        buttonPanel.add(exibirCardapioButton);
        buttonPanel.add(Box.createVerticalStrut(20)); // Espaçamento entre os botões
        
        // Adicionar botão para adicionar item ao cardápio
        JButton adicionarItemButton = criarBotao("Adicionar Item", "caminho/para/imagem_adicionar.png", buttonSize);
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
        buttonPanel.add(adicionarItemButton);
        buttonPanel.add(Box.createVerticalStrut(20)); // Espaçamento entre os botões
        
        // Adicionar botão para editar item no cardápio
        JButton editarItemButton = criarBotao("Editar Item", "caminho/para/imagem_editar.png", buttonSize);
        editarItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarItemCardapio(); // Implementar este método para edição
            }
        });
        buttonPanel.add(editarItemButton);
        buttonPanel.add(Box.createVerticalStrut(20)); // Espaçamento entre os botões
        
        // Adicionar botão para remover item do cardápio
        JButton removerItemButton = criarBotao("Remover Item", "caminho/para/imagem_remover.png", buttonSize);
        removerItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerItemCardapio();
            }
        });
        buttonPanel.add(removerItemButton);
        buttonPanel.add(Box.createVerticalStrut(20)); // Espaçamento entre os botões
        
        // Adicionar botão de sair
        JButton sairButton = criarBotao("Sair", "caminho/para/imagem_sair.png", buttonSize);
        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose(); // Fecha a janela do menu
                JOptionPane.showMessageDialog(null, "Saindo.");
            }
        });
        buttonPanel.add(sairButton);
        
        // Adiciona o painel de botões à janela
        menuFrame.add(buttonPanel);
        
        // Exibir a janela do menu
        menuFrame.setVisible(true);
    }
    
    // Método auxiliar para criar botões com ícone e texto centralizados
    private JButton criarBotao(String texto, String caminhoIcone, Dimension tamanho) {
        // Usa HTML para centralizar o texto
        JButton botao = new JButton("<html><center>" + texto + "</center></html>", new ImageIcon(caminhoIcone));
        botao.setBackground(new Color(255, 165, 0)); // Laranja
        botao.setForeground(Color.WHITE); // Texto branco
        botao.setPreferredSize(tamanho); // Define o tamanho do botão
        botao.setFont(new Font("Arial", Font.PLAIN, 20)); // Tamanho da fonte
        botao.setHorizontalTextPosition(SwingConstants.CENTER); // Centraliza o texto horizontalmente
        botao.setVerticalTextPosition(SwingConstants.BOTTOM);   // Coloca o texto abaixo do ícone
        botao.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza o botão
        botao.setMargin(new Insets(10, 10, 10, 10)); // Define margens para evitar que o texto seja cortado
        return botao;
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
    
        // Verificar se o item já existe no cardápio antes de adicionar
        boolean itemExistente = false;
        for (ItemCard item : cardapio.getItens()) {
            if (item.getItem().equalsIgnoreCase(nome)) {
                itemExistente = true;
                break;
            }
        }
    
        if (!itemExistente) {
            ItemCard novoItem = new ItemCard(nome, descricao, preco);
            cardapio.adicionarItem(novoItem);
            ItemDao.inserirItem(novoItem);
            JOptionPane.showMessageDialog(null, "Item adicionado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Este item já existe no cardápio.");
        }
    }

    private void editarItemCardapio() {
        
        String nome = JOptionPane.showInputDialog("Digite o nome do item que deseja editar:");
        if (nome == null || nome.trim().isEmpty()) return;
        
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


