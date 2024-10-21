package com.example.aplicacoes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.usuarios.Admin;
import com.example.usuarios.Cliente;
import com.example.DAO.AdminDao;
import com.example.DAO.ItemDao;
import com.example.DAO.ClienteDao;

public class CantinaSwing {
    private List<Admin> adminList;
    private List<Cliente> clienteList;
    private AdminDao adminDao;
    private ClienteDao clienteDao;
    private Cardapio cardapio;

    private JTextField loginField;  
    private JPasswordField senhaField;  
    private JComboBox<String> userTypeComboBox;  
    private JButton loginButton; 
    private JButton cadastroButton; 

    public CantinaSwing() {
        this.clienteList = new ArrayList<>();
        this.adminList = new ArrayList<>();
        this.adminDao = new AdminDao();
        this.clienteDao = new ClienteDao();
        this.cardapio = new Cardapio();
        initializeUI();
        carregarCardapio();
    }

    public void adicionarAdmin(Admin adm) {
        adminList.add(adm);
    }

    public void adicionarCliente(Cliente cli) {
        clienteList.add(cli);
    }

    private void initializeUI() {
        JFrame frame = new JFrame("Sistema Cantina");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);  
    
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 165, 0)); 
        JLabel headerLabel = new JLabel("Bem-vindo ao PedeAqui!");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 30));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        panel.add(headerPanel, BorderLayout.NORTH);

        
    
        // Painel central do formulário de login/cadastro
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        
        formPanel.setLayout(new GridBagLayout());  

        GridBagConstraints gbcc = new GridBagConstraints();


        JLabel imagemLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\mclar\\OneDrive\\Documentos\\Área de Trabalho\\CANTINAPROJECT-10\\imagens\\logoPedeAqui.png"); 
        Image image = imageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH); 
        imagemLabel.setIcon(new ImageIcon(image));

// Configurações do layout para a imagem
        gbcc.gridx = 0; 
        gbcc.gridy = 0; 
        gbcc.gridwidth = 2;  
        gbcc.anchor = GridBagConstraints.CENTER;  
        
        gbcc.insets = new Insets(0, 50, 50, 0);  

        formPanel.add(imagemLabel, gbcc);

        panel.add(formPanel, BorderLayout.CENTER); 

    
        // Campo de seleção de tipo de usuário 
        JLabel userTypeLabel = new JLabel("Selecione o tipo de usuário:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; 
        formPanel.add(userTypeLabel, gbc);
    
        String[] userTypes = {"Cliente", "Administrador"};
        userTypeComboBox = new JComboBox<>(userTypes);
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(userTypeComboBox, gbc);
    
        JLabel loginLabel = new JLabel("Login:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(loginLabel, gbc);
    
        loginField = new JTextField(20);  
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(loginField, gbc);
    
        JLabel senhaLabel = new JLabel("Senha:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(senhaLabel, gbc);
    
        senhaField = new JPasswordField(20);  
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(senhaField, gbc);
    
        
        loginButton = new JButton("Login");
        loginButton.setBackground(Color.ORANGE);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 12));
        loginButton.setPreferredSize(new Dimension(100, 25));
        loginButton.addActionListener(this::loginAction);
        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(loginButton, gbc);
    
        
        cadastroButton = new JButton("Cadastro");
        cadastroButton.setBackground(Color.ORANGE);
        cadastroButton.setForeground(Color.WHITE);
        cadastroButton.setFont(new Font("Arial", Font.BOLD, 12));
        cadastroButton.setPreferredSize(new Dimension(100, 25));
        cadastroButton.addActionListener(this::cadastroAction);
        gbc.gridx = 1;
        gbc.gridy = 5;
        formPanel.add(cadastroButton, gbc);
    
        panel.add(formPanel, BorderLayout.CENTER);
    
        // Painel para exibir o cardápio
        JPanel cardapioPanel = new JPanel();
        cardapioPanel.setLayout(new BorderLayout());
        cardapioPanel.setBorder(BorderFactory.createTitledBorder("Cardápio"));
        
        JTextArea cardapioTextArea = new JTextArea();
        cardapioTextArea.setEditable(false); 
        cardapioTextArea.setLineWrap(true);
        cardapioTextArea.setWrapStyleWord(true);
        cardapioTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JScrollPane scrollPane = new JScrollPane(cardapioTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        cardapioPanel.add(scrollPane, BorderLayout.CENTER);
        
        panel.add(cardapioPanel, BorderLayout.SOUTH);
    
        // Painel de rodapé
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(255, 165, 0));  
        JLabel footerLabel = new JLabel("Desenvolvido por Fernanda Rocha, Maria Clara Meira e Maria Herculana.");
        footerLabel.setForeground(Color.WHITE);
        footerPanel.add(footerLabel);
        panel.add(footerPanel, BorderLayout.SOUTH);
    
        frame.add(panel);
        frame.setVisible(true);
    }
    
    private void loginAction(ActionEvent e) {
        String userType = (String) userTypeComboBox.getSelectedItem();  
        String login = loginField.getText();  
        String senha = new String(senhaField.getPassword());  

        if ("Administrador".equals(userType)) {
            loginAdmin(login, senha);
        } else {
            loginCliente(login, senha);
        }
    }

    private void cadastroAction(ActionEvent e) {
        String userType = (String) userTypeComboBox.getSelectedItem();  
        String login = loginField.getText(); 
        String senha = new String(senhaField.getPassword()); 

        if ("Administrador".equals(userType)) {
            JOptionPane.showMessageDialog(null, "administrador não pode ser cadastrado" );
        } else {
            createCliente(login, senha);
        }
    }

    // Método para logar administrador
    private void loginAdmin(String login, String senha) {
        try {
            Admin admin = adminDao.validarLogin(login, senha);
            if (admin != null) {
                JOptionPane.showMessageDialog(null, "Login de administrador realizado com sucesso!");
                admin.exibirMenuAdmin(); 
            } else {
                JOptionPane.showMessageDialog(null, "Login ou senha de administrador inválidos!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao validar login: " );
        }
    }

    // Método para logar cliente
    private void loginCliente(String login, String senha) {
        try {
            Cliente cliente = clienteDao.validarLogin(login, senha); 
            if (cliente != null) {
                JOptionPane.showMessageDialog(null, "Login de cliente realizado com sucesso!");
                cliente.exibirMenuCliente();  
            } else {
                JOptionPane.showMessageDialog(null, "Login ou senha de cliente inválidos!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao validar login: " );
        }
    }

    // Método para cadastrar cliente
    private void createCliente(String login, String senha) {
        Cliente newCliente = new Cliente(login, senha);

        try {
            if (!clienteExists(login)) {
                clienteDao.inserirUsuario(newCliente); 
                JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
                clienteList.add(newCliente); 
            } else {
                JOptionPane.showMessageDialog(null, "Já existe um cliente com esse login.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "login já existe");
        }
    }


    // Verificar se o cliente já existe
    private boolean clienteExists(String login) {
        for (Cliente cliente : clienteList) {
            if (cliente.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    private void carregarCardapio() {
        try {
            ItemDao.pegarTodos(cardapio);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar o cardápio: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CantinaSwing::new);
    }
}

