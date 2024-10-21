package com.example.usuarios;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import java.awt.*;

import com.example.DAO.AdminDao;
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

    JFrame menuFrame = new JFrame("Menu Cliente");
    menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    menuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    menuFrame.setUndecorated(true); 
    menuFrame.setLocationRelativeTo(null); 
    menuFrame.getContentPane().setBackground(Color.WHITE);

    // Painel para a imagem
    JPanel imagePanel = new JPanel();
    imagePanel.setBackground(Color.WHITE);
    JLabel imageLabel = new JLabel(new ImageIcon("C:\\Users\\mclar\\OneDrive\\Documentos\\Área de Trabalho\\CANTINAPROJECT-8\\imagens\\logoPedeAqui.png")); // Adicione o caminho da sua imagem
    imagePanel.add(imageLabel);

    // Painel para os botões
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); 
    buttonPanel.setBackground(Color.WHITE);

    Dimension buttonSize = new Dimension(150, 80); 

    // Botão Exibir Cardápio
    JButton exibirCardapioButton = criarBotao("Exibir Cardápio", "C:\\Users\\mclar\\OneDrive\\Documentos\\Área de Trabalho\\CANTINAPROJECT-7\\imagens\\exibir.png", buttonSize);
    exibirCardapioButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (cardapio != null) {
                cardapio.exibirCardapio(); 
            } else {
                JOptionPane.showMessageDialog(menuFrame, "O cardápio não está disponível.");
            }
        }
    });
    buttonPanel.add(exibirCardapioButton);

    // Botão Fazer Pedido
    JButton fazerPedidoButton = criarBotao("Fazer Pedido", "C:\\Users\\mclar\\OneDrive\\Documentos\\Área de Trabalho\\CANTINAPROJECT-7\\imagens\\adicionar.png", buttonSize);
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

    // Botão Ver Pedidos
    JButton exibirPedidosButton = criarBotao("Ver Pedidos", "C:\\Users\\mclar\\OneDrive\\Documentos\\Área de Trabalho\\CANTINAPROJECT-7\\imagens\\aprovarPed.png", buttonSize);
    exibirPedidosButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                exibirPedidos(); 
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(menuFrame, "Erro ao exibir pedidos: " + ex.getMessage());
            }
        }
    });
    buttonPanel.add(exibirPedidosButton);
    
    JButton deletarButton = criarBotao("Deletar Conta","C:\\Users\\mclar\\OneDrive\\Documentos\\Área de Trabalho\\CANTINAPROJECT-12\\imagens\\remover.png", buttonSize);
        deletarButton.addActionListener(new ActionListener() {
            

            @Override
            public void actionPerformed(ActionEvent e) {

                
                int resposta = JOptionPane.showConfirmDialog(null,
                "Você tem certeza que deseja deletar sua conta?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION) {
                try {
                    AdminDao.deletar(login);
                    menuFrame.dispose();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "usuario deletado.");
            }else{
                JOptionPane.showMessageDialog(null, "Ação cancelada.");
            }
        }
        });
        buttonPanel.add(deletarButton);
        

    // Botão Sair
    JButton sairButton = criarBotao("Sair", "C:\\Users\\mclar\\OneDrive\\Documentos\\Área de Trabalho\\CANTINAPROJECT-7\\imagens\\sair.png", buttonSize);
    sairButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            menuFrame.dispose(); 
        }
    });
    buttonPanel.add(sairButton);

    

    // Painel principal que contém a imagem acima e os botões abaixo
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBackground(Color.WHITE);

    // Adiciona os painéis no layout
    mainPanel.add(imagePanel, BorderLayout.NORTH);  // A imagem fica no topo
    mainPanel.add(buttonPanel, BorderLayout.CENTER); // Os botões ficam no centro

    // Adiciona o painel principal no frame
    menuFrame.add(mainPanel, BorderLayout.CENTER);
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
        pedidosPanel.setBackground(Color.WHITE);
    
        for (Pedido pedido : pedidos) {
            // Painel para cada pedido
            JPanel pedidoPanel = new JPanel();
            pedidoPanel.setLayout(new BoxLayout(pedidoPanel, BoxLayout.Y_AXIS));
            pedidoPanel.setBorder(BorderFactory.createLineBorder(darkOrange, 2)); 
            pedidoPanel.setBackground(Color.WHITE); 
    
            // Informações do cliente
            JLabel clienteLabel = new JLabel("Cliente: " + pedido.getNomeCliente());
            clienteLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            clienteLabel.setForeground(darkOrange);
            pedidoPanel.add(clienteLabel);
    
            JLabel clienteIdLabel = new JLabel("ID do Cliente: " + pedido.getClienteId());
            clienteIdLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            clienteIdLabel.setForeground(darkOrange); 
            pedidoPanel.add(clienteIdLabel);
    
            // Itens do pedido
            JLabel itensLabel = new JLabel("Itens:");
            itensLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            itensLabel.setForeground(darkOrange); 
            pedidoPanel.add(itensLabel);
    
            for (ItemCard item : pedido.getItens()) {
                JLabel itemLabel = new JLabel("- " + item.getItem() + ": R$ " + item.getPreco());
                itemLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                itemLabel.setForeground(Color.BLACK); 
                pedidoPanel.add(itemLabel);
            }
    
            double totalPedido = pedido.calcularTotal(); // Calcular total para o pedido atual
            totalGeral += totalPedido; // Acumula o total de todos os pedidos
    
            JLabel totalPedidoLabel = new JLabel("Total do Pedido: R$ " + totalPedido);
            totalPedidoLabel.setFont(new Font("Arial", Font.BOLD, 16));
            totalPedidoLabel.setForeground(darkOrange); 
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
        totalGeralLabel.setForeground(darkOrange); 
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
        botao.setPreferredSize(tamanho); 
        botao.setFont(new Font("Arial", Font.PLAIN, 20));
        botao.setHorizontalTextPosition(SwingConstants.CENTER); // Centraliza o texto horizontalmente
        botao.setVerticalTextPosition(SwingConstants.BOTTOM);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza o botão
        botao.setMargin(new Insets(10, 10, 10, 10)); // Define margens para evitar que o texto seja cortado

        return botao;
    }
}
