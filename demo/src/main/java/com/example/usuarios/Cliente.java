package com.example.usuarios;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.*;

import com.example.DAO.ItemDao;
import com.example.DAO.PedidoDao;
import com.example.aplicacoes.Cardapio;
import com.example.aplicacoes.ItemCard;
import com.example.aplicacoes.Pedido;

public class Cliente {
    private Cardapio cardapio;
    private int cont;
    private String login;
    private String senha;

    public Cliente(String login, String senha) {
        this.cardapio = new Cardapio();
        this.cont = 0;
        this.login = login;
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public void exibirMenuCliente() throws SQLException {
        // Criar a janela do menu do cliente
        JFrame menuFrame = new JFrame("Menu Cliente");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);  // Abre em tela cheia
        menuFrame.getContentPane().setBackground(Color.WHITE); // Fundo branco
        
        // Painel para organizar os botões verticalmente
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.WHITE); // Fundo branco do painel
        
        // Tamanho preferido dos botões
        Dimension buttonSize = new Dimension(100, 25); 
        
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

        // Adicionar botão para fazer pedido
        JButton fazerPedidoButton = criarBotao("Fazer Pedido", "caminho/para/imagem_pedido.png", buttonSize);
        fazerPedidoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    fazerPedido();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(menuFrame, "Erro ao fazer pedido: " + ex.getMessage());
                }
            }
        });
        buttonPanel.add(fazerPedidoButton);
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

    // Método para fazer o pedido
    public void fazerPedido() throws SQLException {
        if (cardapio == null || cardapio.getItens().length == 0) {
            JOptionPane.showMessageDialog(null, "O cardápio está vazio ou não está disponível.");
            return;
        }

        // Exibir opções do cardápio para o cliente selecionar
        String[] opcoes = Arrays.stream(cardapio.getItens()).map(ItemCard::getItem).toArray(String[]::new);
        String itemSelecionado = (String) JOptionPane.showInputDialog(
                null,
                "Selecione o item que deseja pedir:",
                "Fazer Pedido",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        // Se o cliente cancelou ou não escolheu um item
        if (itemSelecionado == null || itemSelecionado.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum item foi selecionado.");
            return;
        }

        // Procura o item no cardápio
        ItemCard itemPedido = Arrays.stream(cardapio.getItens())
            .filter(item -> item.getItem().equals(itemSelecionado))
            .findFirst()
            .orElse(null);

        if (itemPedido != null) {
            // Cria um novo pedido e insere no banco de dados
            Pedido pedido = new Pedido(this.login, itemPedido.getItem(), cont);
            PedidoDao.inserirPedido(pedido);

            JOptionPane.showMessageDialog(null, "Pedido realizado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao selecionar o item do cardápio.");
        }
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
}


