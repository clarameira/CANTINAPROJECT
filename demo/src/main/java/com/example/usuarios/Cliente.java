package com.example.usuarios;

import java.awt.Image;
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
        verificarPedidosProntos();
        ItemDao.pegarTodos(cardapio);

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
    public void verificarPedidosProntos() {
        try {
            List<Pedido> pedidos = PedidoDao.buscarPedidosPorCliente(login);

            for (Pedido pedido : pedidos) {
                if (pedido.isPedidoPronto()) {  // Verifica se o pedido está pronto
                    JOptionPane.showMessageDialog(null, 
                        "Seu pedido #" + pedido.getClienteId() + " está pronto e já pode ser retirado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao verificar pedidos: " + e.getMessage());
        }
    }
    


    public void exibirPedidos() throws SQLException {
        // Obtém os pedidos do cliente a partir do banco de dados
        List<Pedido> pedidos = PedidoDao.buscarPedidosPorCliente(login);
    
        if (pedidos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum pedido encontrado.");
            return;
        }
    
        // Definindo um laranja mais escuro
        Color darkOrange = new Color(255, 140, 0);
    
        // Criar uma janela principal em tela cheia
        JFrame frame = new JFrame("Histórico de Pedidos");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Abre em tela cheia
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Painel principal usando BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE); // Fundo branco
    
        // Título no topo
        JLabel titleLabel = new JLabel("Pedidos Realizados");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(darkOrange); // Texto com laranja mais escuro
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centraliza o título
        mainPanel.add(titleLabel, BorderLayout.NORTH);
    
        double totalGeral = 0.0; // Variável para armazenar o total de todos os pedidos
        
        // Painel para exibir os pedidos
        JPanel pedidosPanel = new JPanel();
        pedidosPanel.setLayout(new BoxLayout(pedidosPanel, BoxLayout.Y_AXIS));
        pedidosPanel.setBackground(Color.WHITE); // Fundo branco
    
        for (Pedido pedido : pedidos) {
            // Painel para cada pedido
            JPanel pedidoPanel = new JPanel();
            pedidoPanel.setLayout(new BoxLayout(pedidoPanel, BoxLayout.Y_AXIS));
            pedidoPanel.setBorder(BorderFactory.createLineBorder(darkOrange, 2)); // Borda com laranja mais escuro
            pedidoPanel.setBackground(Color.WHITE); // Fundo branco
    
            // Informações do cliente
            JLabel clienteLabel = new JLabel("Cliente: " + pedido.getNomeCliente());
            clienteLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            clienteLabel.setForeground(darkOrange); // Texto com laranja mais escuro
            pedidoPanel.add(clienteLabel);
    
            JLabel clienteIdLabel = new JLabel("ID do Cliente: " + pedido.getClienteId());
            clienteIdLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            clienteIdLabel.setForeground(darkOrange); // Texto com laranja mais escuro
            pedidoPanel.add(clienteIdLabel);
    
            // Itens do pedido
            JLabel itensLabel = new JLabel("Itens:");
            itensLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            itensLabel.setForeground(darkOrange); // Texto com laranja mais escuro
            pedidoPanel.add(itensLabel);
    
            for (ItemCard item : pedido.getItens()) {
                JLabel itemLabel = new JLabel("- " + item.getItem() + ": R$ " + item.getPreco());
                itemLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                itemLabel.setForeground(Color.BLACK); // Texto preto
                pedidoPanel.add(itemLabel);
            }
    
            double totalPedido = pedido.calcularTotal(); // Calcular total para o pedido atual
            totalGeral += totalPedido; // Acumula o total de todos os pedidos
    
            JLabel totalPedidoLabel = new JLabel("Total do Pedido: R$ " + totalPedido);
            totalPedidoLabel.setFont(new Font("Arial", Font.BOLD, 16));
            totalPedidoLabel.setForeground(darkOrange); // Texto com laranja mais escuro
            pedidoPanel.add(totalPedidoLabel);
            
            // Se o pedido estiver pronto, exibe um botão para confirmar recebimento
            if (pedido.isPedidoPronto()) {
                JButton confirmarRecebimentoButton = new JButton("Confirmar Recebimento");
                confirmarRecebimentoButton.setFont(new Font("Arial", Font.BOLD, 16));
                confirmarRecebimentoButton.setBackground(new Color(0, 204, 102)); // Verde
                confirmarRecebimentoButton.setForeground(Color.WHITE);
                
                confirmarRecebimentoButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            // Atualiza o status do pedido para recebido
                            PedidoDao.deletarPedidoPorLogin(login);
                            JOptionPane.showMessageDialog(frame, "Pedido #" + pedido.getClienteId() + " confirmado como recebido.");
                            
                            // Remove o painel do pedido
                            pedidosPanel.remove(pedidoPanel);
                            pedidosPanel.revalidate();
                            pedidosPanel.repaint();
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(frame, "Erro ao confirmar o recebimento: " + ex.getMessage());
                        }
                    }
                });
    
                pedidoPanel.add(confirmarRecebimentoButton);
            }
    
            pedidosPanel.add(pedidoPanel);
            pedidosPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espaço entre os pedidos
        }
    
        // Adiciona o painel de pedidos dentro de um JScrollPane (barra de rolagem)
        JScrollPane scrollPane = new JScrollPane(pedidosPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(scrollPane, BorderLayout.CENTER); // Centraliza o painel de pedidos
    
        // Exibe o total geral de todos os pedidos na parte inferior da tela
        JLabel totalGeralLabel = new JLabel("Total Geral de Todos os Pedidos: R$ " + totalGeral);
        totalGeralLabel.setFont(new Font("Arial", Font.BOLD, 20));
        totalGeralLabel.setForeground(darkOrange); // Texto com laranja mais escuro
        totalGeralLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centraliza o texto
        totalGeralLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Espaçamento
        mainPanel.add(totalGeralLabel, BorderLayout.SOUTH); // Coloca o total no rodapé
    
        // Adiciona o painel principal ao frame
        frame.getContentPane().add(mainPanel);
        frame.setVisible(true); // Exibe a janela
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
