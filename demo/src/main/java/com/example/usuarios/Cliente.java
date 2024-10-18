package com.example.usuarios;

import java.awt.Image; 
import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.*;

import com.example.DAO.ItemDao;
import com.example.DAO.PedidoDao;
import com.example.aplicacoes.Cardapio;
import com.example.aplicacoes.ItemCard;
import com.example.aplicacoes.Pedido;

public class Cliente {
    private Cardapio cardapio;
    private int clienteId;
    private String login;
    private String senha;

    public Cliente(String login, String senha) {
        this.cardapio = new Cardapio();
        this.clienteId = gerarClienteId(); 
        this.login = login;
        this.senha = senha;
    }

    // Método para gerar um ID aleatório para o cliente
    private int gerarClienteId() {
        Random random = new Random();
        return random.nextInt(10000); 
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
        
        // Painel para organizar os botões usando BoxLayout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // Alinhamento vertical
        buttonPanel.setBackground(Color.WHITE); // Fundo branco do painel
        
        // Tamanho preferido dos botões
        Dimension buttonSize = new Dimension(300, 40);
        
        // Adicionar botão para exibir cardápio
        JButton exibirCardapioButton = criarBotao("Exibir Cardápio", "caminho/para/imagem_exibir.png", buttonSize);
        exibirCardapioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (cardapio != null) {
                        ItemDao.pegarTodos(cardapio);
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

        // Adicionar botão para exibir pedidos
        JButton exibirPedidosButton = criarBotao("Ver Pedidos", "caminho/para/imagem_pedidos.png", buttonSize);
        exibirPedidosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    exibirPedidos(); // Chama o método para exibir pedidos
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(menuFrame, "Erro ao exibir pedidos: " + ex.getMessage());
                }
            }
        });
        buttonPanel.add(exibirPedidosButton);
        
        // Adicionar botão de sair
        JButton sairButton = criarBotao("Sair", "C:\\Users\\ferna\\Desktop\\projetoCantina\\CANTINAPROJECT\\imagens\\sair.png", buttonSize);
        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose(); // Fecha a janela do menu
                JOptionPane.showMessageDialog(null, "Saindo.");
            }
        });
        buttonPanel.add(sairButton);
        
        // Adiciona o painel de botões à janela, centralizando
        menuFrame.add(buttonPanel, BorderLayout.CENTER);
        
        // Exibir a janela do menu
        menuFrame.setVisible(true);
    }

    public void fazerPedido() throws SQLException {
        if (cardapio == null || cardapio.getItens().length == 0) {
            JOptionPane.showMessageDialog(null, "O cardápio está vazio ou não está disponível.");
            return;
        }
    
        // Exibir opções do cardápio para o cliente selecionar
        String[] opcoes = Arrays.stream(cardapio.getItens()).map(ItemCard::getItem).toArray(String[]::new);
    
        // Criar JList para seleção múltipla
        JList<String> itemList = new JList<>(opcoes);
        itemList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        itemList.setVisibleRowCount(10); // Exibe até 10 itens
    
        // Colocar JList em um JScrollPane para rolagem
        JScrollPane scrollPane = new JScrollPane(itemList);
        scrollPane.setPreferredSize(new Dimension(300, 200));
    
        // Mostrar a caixa de diálogo para selecionar os itens
        int resposta = JOptionPane.showConfirmDialog(null, scrollPane, "Selecione os itens que deseja pedir:", JOptionPane.OK_CANCEL_OPTION);
        
        // Se o cliente cancelou ou não escolheu nenhum item
        if (resposta != JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(null, "Nenhum item foi selecionado.");
            return;
        }
    
        // Obter os índices dos itens selecionados
        int[] indicesSelecionados = itemList.getSelectedIndices();
        if (indicesSelecionados.length == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum item foi selecionado.");
            return;
        }
    
        // Coletar os itens selecionados
        List<ItemCard> itensSelecionados = new ArrayList<>();
        for (int indice : indicesSelecionados) {
            String itemPedido = opcoes[indice];
            ItemCard item = Arrays.stream(cardapio.getItens())
                    .filter(i -> i.getItem().equals(itemPedido))
                    .findFirst()
                    .orElse(null);
            
            if (item != null) {
                itensSelecionados.add(item);
            }
        }
    
        Pedido pedido = new Pedido(clienteId, login, itensSelecionados);
    
        // Insere o pedido no banco de dados
        PedidoDao.inserirPedido(pedido);  
        JOptionPane.showMessageDialog(null, "Pedido realizado com sucesso!");
    }

    public void exibirPedidos() throws SQLException {
        // Obtém os pedidos do cliente a partir do banco de dados
        List<Pedido> pedidos = PedidoDao.buscarPedidosPorCliente(login);
    
        if (pedidos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum pedido encontrado.");
            return;
        }
    
        // Constrói a string para exibir os pedidos
        StringBuilder sb = new StringBuilder("Pedidos realizados:\n\n");
        double totalGeral = 0.0; // Variável para armazenar o total de todos os pedidos
    
        for (Pedido pedido : pedidos) {
            sb.append("Cliente: ").append(pedido.getNomeCliente()).append("\n");
            sb.append("ID do Cliente: ").append(pedido.getClienteId()).append("\n");
            sb.append("Itens:\n");
            
            for (ItemCard item : pedido.getItens()) {
                sb.append("- ").append(item.getItem()).append(": R$ ").append(item.getPreco()).append("\n");
            }
    
            double totalPedido = pedido.calcularTotal(); // Calcular total para o pedido atual
            totalGeral += totalPedido; // Acumula o total de todos os pedidos
        }
    
        // Exibe o total geral de todos os pedidos
        sb.append("Total Geral de Todos os Pedidos: R$ ").append(totalGeral).append("\n");
    
        JOptionPane.showMessageDialog(null, sb.toString(), "Histórico de Pedidos", JOptionPane.INFORMATION_MESSAGE);
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
        botao.setVerticalTextPosition(SwingConstants.BOTTOM);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza o botão
        botao.setMargin(new Insets(10, 10, 10, 10)); // Define margens para evitar que o texto seja cortado

        return botao;
    }
}
