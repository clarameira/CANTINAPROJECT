package com.example.usuarios;

import java.awt.Image; 
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.BorderLayout;

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

        JFrame menuFrame = new JFrame("Menu Administrador");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        menuFrame.getContentPane().setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(255, 165, 0));
        JLabel titleLabel = new JLabel("PedeAqui!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Painel para organizar os botões à esquerda
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.WHITE); // Fundo branco do painel

        buttonPanel.add(Box.createVerticalStrut(10));

        Dimension buttonSize = new Dimension(600, 200);

        JButton exibirCardapioButton = criarBotao("Exibir Cardápio", "C:\\Users\\ferna\\Desktop\\projetoCantina\\CANTINAPROJECT\\imagens\\cardapio.png", buttonSize);
        exibirCardapioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (cardapio != null) {
                        if (cont == 0) {
                            cont++;
                            ItemDao.pegarTodos(cardapio);
                        }
                        cardapio.exibirCardapio();
                    } else {
                        JOptionPane.showMessageDialog(menuFrame, "O cardápio não está disponível.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(menuFrame, "Erro ao carregar cardápio: " + ex.getMessage());
                }
            }
        });
        buttonPanel.add(exibirCardapioButton);
        buttonPanel.add(Box.createVerticalStrut(20));

        JButton adicionarItemButton = criarBotao("Adicionar Item", "caminho/para/imagem_adicionar.png", buttonSize);
        adicionarItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    adicionarItemCardapio();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(menuFrame, "Erro ao adicionar item: " + ex.getMessage());
                }
            }
        });
        buttonPanel.add(adicionarItemButton);
        buttonPanel.add(Box.createVerticalStrut(20));

        JButton editarItemButton = criarBotao("Editar Item", "caminho/para/imagem_editar.png", buttonSize);
        editarItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarItemCardapio();
            }
        });
        buttonPanel.add(editarItemButton);
        buttonPanel.add(Box.createVerticalStrut(20));

        JButton removerItemButton = criarBotao("Remover Item", "caminho/para/imagem_remover.png", buttonSize);
        removerItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerItemCardapio();
            }
        });
        buttonPanel.add(removerItemButton);
        buttonPanel.add(Box.createVerticalStrut(20));

        JButton sairButton = criarBotao("Sair", "C:\\Users\\ferna\\Desktop\\projetoCantina\\CANTINAPROJECT\\imagens\\sair.png", buttonSize);
        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                JOptionPane.showMessageDialog(null, "Saindo.");
            }
        });
        buttonPanel.add(sairButton);
        buttonPanel.add(Box.createVerticalGlue());

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(Box.createVerticalStrut(250)); // Espaço de 150 pixels no topo do painel de pedidos

        JButton verificarPedidosButton = criarBotao("Verificar Pedidos", "caminho/para/imagem_verificar.png",
                buttonSize);
        verificarPedidosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarPedidos();
            }
        });
        rightPanel.add(verificarPedidosButton);
        rightPanel.add(Box.createVerticalGlue());

        mainPanel.add(buttonPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        menuFrame.add(mainPanel);

        menuFrame.setVisible(true);
    }

    private JButton criarBotao(String texto, String caminhoIcone, Dimension tamanho) {
    
    ImageIcon icon = new ImageIcon(caminhoIcone);
    Image img = icon.getImage();
    Image novaImg = img.getScaledInstance(25, 25, Image.SCALE_SMOOTH); 
    ImageIcon iconeRedimensionado = new ImageIcon(novaImg);

    JButton botao = new JButton("<html><center>" + texto + "</center></html>", iconeRedimensionado);
    botao.setBackground(new Color(255, 165, 0));
    botao.setForeground(Color.WHITE);
    botao.setPreferredSize(tamanho); // Tamanho do botão
    botao.setFont(new Font("Arial", Font.PLAIN, 20));
    botao.setHorizontalTextPosition(SwingConstants.CENTER); // Centraliza o texto horizontalmente
    botao.setVerticalTextPosition(SwingConstants.BOTTOM); // Coloca o texto abaixo da imagem
    botao.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza o botão
    botao.setMargin(new Insets(10, 10, 10, 10)); // Define margens para evitar que o texto seja cortado

    return botao;
}


    private void verificarPedidos() {
        JOptionPane.showMessageDialog(null, "Função de verificar pedidos ainda não implementada.");
    }

    private void adicionarItemCardapio() throws SQLException {
        String nome = JOptionPane.showInputDialog("Digite o nome do item:");
        String descricao = JOptionPane.showInputDialog("Digite a descrição do item:");

        double preco;
        while (true) {
            String precoInput = JOptionPane.showInputDialog("Digite o preço do item:");
            if (precoInput == null)
                return;
            try {
                preco = Double.parseDouble(precoInput);
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Preço inválido. Tente novamente.");
            }
        }

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
        if (nome == null || nome.trim().isEmpty())
            return;

    }

    private void removerItemCardapio() {
        String nome = JOptionPane.showInputDialog("Digite o nome do item que deseja remover:");
        if (nome == null || nome.trim().isEmpty())
            return; // Verifica se o usuário cancelou ou não digitou nada

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
