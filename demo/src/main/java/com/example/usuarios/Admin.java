package com.example.usuarios;

import java.awt.Image;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import com.example.DAO.AdminDao;
import com.example.DAO.ItemDao;
import com.example.DAO.PedidoDao;
import com.example.aplicacoes.Cardapio;
import com.example.aplicacoes.ItemCard;
import com.example.aplicacoes.Pedido;

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
        buttonPanel.setBackground(Color.WHITE); 

        buttonPanel.add(Box.createVerticalStrut(10));


        Dimension buttonSize = new Dimension(600, 200);
        JButton marcarPedidoProntoButton = criarBotao("Marcar Pedido como Pronto",
                "caminho/para/imagem_pedido_pronto.png", buttonSize);
        marcarPedidoProntoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                marcarPedidoComoPronto();
            }
        });
        buttonPanel.add(marcarPedidoProntoButton);
        buttonPanel.add(Box.createVerticalStrut(20));

        JButton exibirCardapioButton = criarBotao("Exibir Cardápio",
                "C:\\Users\\mclar\\OneDrive\\Documentos\\Área de Trabalho\\CANTINAPROJECT-7\\imagens\\exibir.png", buttonSize);
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

        JButton adicionarItemButton = criarBotao("Adicionar Item", "C:\\Users\\mclar\\OneDrive\\Documentos\\Área de Trabalho\\CANTINAPROJECT-7\\imagens\\adicionar.png", buttonSize);
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

        JButton editarItemButton = criarBotao("Editar Item", "C:\\Users\\mclar\\OneDrive\\Documentos\\Área de Trabalho\\CANTINAPROJECT-8\\imagens\\editar.png", buttonSize);
        editarItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarItemCardapio();
            }
        });
        buttonPanel.add(editarItemButton);
        buttonPanel.add(Box.createVerticalStrut(20));

        JButton removerItemButton = criarBotao("Remover Item", "C:\\Users\\mclar\\OneDrive\\Documentos\\Área de Trabalho\\CANTINAPROJECT-7\\imagens\\remover.png", buttonSize);
        removerItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerItemCardapio();
            }
        });
        buttonPanel.add(removerItemButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        
        //botão de deletar
        JButton deletarButton = criarBotao("Deletar Conta",
                "C:\\Users\\mclar\\OneDrive\\Documentos\\Área de Trabalho\\CANTINAPROJECT-7\\imagens\\sair.png", buttonSize);
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
                JOptionPane.showMessageDialog(null, "Usuário deletado.");
            }else{
                JOptionPane.showMessageDialog(null, "Ação cancelada.");
            }
        }
        });
        buttonPanel.add(deletarButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        

        JButton cadastrarButton = criarBotao("Cadastrar Novo Administrador",
                "C:\\Users\\mclar\\OneDrive\\Documentos\\Área de Trabalho\\CANTINAPROJECT-7\\imagens\\sair.png", buttonSize);
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String novoLogin = JOptionPane.showInputDialog("Digite o login do novo administrador:");
                if (novoLogin == null || novoLogin.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(menuFrame, "Login não pode estar vazio.");
                    return;
                }
        
                String novaSenha = JOptionPane.showInputDialog("Digite a senha do novo administrador:");
                if (novaSenha == null || novaSenha.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(menuFrame, "Senha não pode estar vazia.");
                    return;
                }
        
                try {
                    if (AdminDao.buscarPorLogin(novoLogin) != null) {
                        JOptionPane.showMessageDialog(menuFrame, "Administrador com esse login já existe.");
                        return;
                    }

                    Admin novoAdmin = new Admin(novoLogin, novaSenha);
                    AdminDao.inserirUsuario(novoAdmin);  
        
                    JOptionPane.showMessageDialog(menuFrame, "Novo administrador cadastrado com sucesso!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(menuFrame, "Erro ao cadastrar administrador: " + ex.getMessage());
                }
            }
        });
        buttonPanel.add(cadastrarButton);
        buttonPanel.add(Box.createVerticalStrut(20));

        
        //botão de sair
        JButton sairButton = criarBotao("Sair",
                "C:\\Users\\mclar\\OneDrive\\Documentos\\Área de Trabalho\\CANTINAPROJECT-7\\imagens\\sair.png", buttonSize);
        sairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
            }
        });
        buttonPanel.add(sairButton);
        buttonPanel.add(Box.createVerticalGlue());

        //logo PedeAqui!
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(Box.createVerticalStrut(100));

        ImageIcon imagemVerificar = new ImageIcon("C:\\Users\\mclar\\OneDrive\\Documentos\\Área de Trabalho\\CANTINAPROJECT-8\\imagens\\logoPedeAqui.png"); 
        Image img = imagemVerificar.getImage(); 
        Image novaImg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH); 
        imagemVerificar = new ImageIcon(novaImg); 

        JLabel imagemLabel = new JLabel(imagemVerificar);
        imagemLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        rightPanel.add(imagemLabel); 

        //botão de verificar pedido
    JButton verificarPedidosButton = criarBotao("Verificar Pedidos", "caminho/para/imagem_verificar.png", buttonSize);
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
        botao.setPreferredSize(tamanho); 
        botao.setFont(new Font("Arial", Font.PLAIN, 20));
        botao.setHorizontalTextPosition(SwingConstants.CENTER); 
        botao.setVerticalTextPosition(SwingConstants.BOTTOM); 
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.setMargin(new Insets(10, 10, 10, 10)); 
        return botao;
    }

    private void marcarPedidoComoPronto() {
        try {
            List<Pedido> pedidosNaoProntos = PedidoDao.buscarPedidosNaoProntos();
            if (pedidosNaoProntos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum pedido pendente.");
                return;
            }
    
            // Listando os pedidos pendentes
            StringBuilder sb = new StringBuilder();
            sb.append("Pedidos pendentes:\n");
            for (Pedido pedido : pedidosNaoProntos) {
                sb.append("Cliente ID: ").append(pedido.getClienteId())
                  .append(", Cliente: ").append(pedido.getNomeCliente())
                  .append("    Item: ").append(pedido.getItens().get(0).getItem()).append("\n");
            }
    
            // Permitindo a seleção do cliente pelo ID
            String opcao = JOptionPane.showInputDialog(sb.toString() + "\nDigite o ID do cliente para marcar todos os pedidos desse cliente como prontos:");
            if (opcao == null || opcao.trim().isEmpty()) {
                return;
            }
    
            int clienteId;
            try {
                clienteId = Integer.parseInt(opcao); 
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ID inválido.");
                return;
            }
    
            boolean clienteEncontrado = false;
            for (Pedido pedido : pedidosNaoProntos) {
                if (pedido.getClienteId() == clienteId) {
                    PedidoDao.atualizarStatusPedido(pedido.getClienteId(), true);
                    clienteEncontrado = true;
                }
            }
    
            if (clienteEncontrado) {
                JOptionPane.showMessageDialog(null, "Os pedidos do cliente foram marcados como prontos!");
            } else {
                JOptionPane.showMessageDialog(null, "Cliente com ID informado não encontrado.");
            }
    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o pedido: " + ex.getMessage());
        }
    }
    

    private void verificarPedidos() {
    try {
        List<Pedido> pedidosNaoProntos = PedidoDao.buscarPedidosNaoProntos();
        
        if (pedidosNaoProntos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum pedido pendente.");
            return;
        }
        
        String[] colunas = {"Número do Pedido", "Cliente", "Item", "Preço"};
        Object[][] dados = new Object[pedidosNaoProntos.size()][4];
        

        for (int i = 0; i < pedidosNaoProntos.size(); i++) {
            Pedido pedido = pedidosNaoProntos.get(i);
            ItemCard itemCard = pedido.getItens().get(0);  // Supondo que haja um único item por pedido
            
            dados[i][0] = pedido.getClienteId();  
            dados[i][1] = pedido.getNomeCliente();
            dados[i][2] = itemCard.getItem();
            dados[i][3] = String.format("R$ %.2f", itemCard.getPreco());
        }
        
        JTable tabelaPedidos = new JTable(dados, colunas);
        
        tabelaPedidos.setFont(new Font("Arial", Font.PLAIN, 18));  
        tabelaPedidos.setRowHeight(40);  
        tabelaPedidos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));  
        tabelaPedidos.getTableHeader().setBackground(new Color(255, 165, 0));  
        tabelaPedidos.getTableHeader().setForeground(Color.WHITE);  
        tabelaPedidos.setBackground(Color.WHITE);  
        tabelaPedidos.setGridColor(new Color(255, 165, 0));  
        
        tabelaPedidos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    cell.setBackground(new Color(255, 240, 200));  
                } else {
                    cell.setBackground(Color.WHITE);  
                }
                return cell;
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tabelaPedidos);
        
        JFrame pedidosFrame = new JFrame("Pedidos Em Andamento");
        pedidosFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        pedidosFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);  
        
        pedidosFrame.add(scrollPane, BorderLayout.CENTER);
        pedidosFrame.setVisible(true);
        
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Erro ao buscar pedidos: " + ex.getMessage());
    }
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
        if (nome == null || nome.trim().isEmpty()) {
            return;
        }

        ItemCard itemParaEditar = null;
        for (ItemCard item : cardapio.getItens()) {
            if (item.getItem().equalsIgnoreCase(nome)) {
                itemParaEditar = item;
                break; // Item encontrado
            }
        }

        if (itemParaEditar == null) {
            JOptionPane.showMessageDialog(null, "Item não encontrado no cardápio.");
            return;
        }

        String novoNome = JOptionPane.showInputDialog("Digite o novo nome do item:", itemParaEditar.getItem());
        if (novoNome == null || novoNome.trim().isEmpty())
            return;

        String novaDescricao = JOptionPane.showInputDialog("Digite a nova descrição do item:",
                itemParaEditar.getDescricao());
        if (novaDescricao == null)
            return;

        String precoInput = JOptionPane.showInputDialog("Digite o novo preço do item:", itemParaEditar.getPreco());
        if (precoInput == null)
            return;

        double novoPreco;
        try {
            novoPreco = Double.parseDouble(precoInput);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Preço inválido. Tente novamente.");
            return; // Sai do método se o preço for inválido
        }

        itemParaEditar.setItem(novoNome); // Atualiza o nome do item
        itemParaEditar.setDescricao(novaDescricao);
        itemParaEditar.setPreco(novoPreco);

        try {
            ItemDao.atualizarItem(itemParaEditar, nome); 
            JOptionPane.showMessageDialog(null, "Item atualizado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar item no banco de dados: " + e.getMessage());
        }
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
