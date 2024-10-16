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
    private AdminDao adminDao;
    private ClienteDao clienteDao;
    private Cardapio cardapio;

    private JTextField loginField;  // Campo de texto para login
    private JPasswordField senhaField;  // Campo de texto para senha
    private JComboBox<String> userTypeComboBox;  // Campo de seleção do tipo de usuário
    private JButton loginButton;  // Botão de login
    private JButton cadastroButton;  // Botão de cadastro

    public CantinaSwing() {
        this.adminList = new ArrayList<>();
        this.adminDao = new AdminDao();
        this.clienteDao = new ClienteDao();
        this.cardapio = new Cardapio();
        initializeUI();
        carregarCardapio();
    }
public void adicionarAdmin(Admin adm){
    adminList.add(adm);
}
    private void initializeUI() {
        JFrame frame = new JFrame("Sistema Cantina");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  // Abre em tela cheia
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);  // Fundo branco

        // Painel para o cabeçalho
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 165, 0));  // Laranja
        JLabel headerLabel = new JLabel("Bem-vindo ao Sistema Cantina");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 30));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Painel central para o formulário de login/cadastro
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Campo de seleção de tipo de usuário (Cliente ou Administrador)
        JLabel userTypeLabel = new JLabel("Selecione o tipo de usuário:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(userTypeLabel, gbc);

        String[] userTypes = {"Cliente", "Administrador"};
        userTypeComboBox = new JComboBox<>(userTypes);
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(userTypeComboBox, gbc);

        // Campo de texto para login
        JLabel loginLabel = new JLabel("Login:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(loginLabel, gbc);

        loginField = new JTextField(20);  // Campo de login
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(loginField, gbc);

        // Campo de texto para senha
        JLabel senhaLabel = new JLabel("Senha:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(senhaLabel, gbc);

        senhaField = new JPasswordField(20);  // Campo de senha
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(senhaField, gbc);

        // Botão de Login
        loginButton = new JButton("Login");
        loginButton.setBackground(Color.ORANGE);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 12));
        loginButton.setPreferredSize(new Dimension(100, 25));
        loginButton.addActionListener(this::loginAction);
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(loginButton, gbc);

        // Botão de Cadastro
        cadastroButton = new JButton("Cadastro");
        cadastroButton.setBackground(Color.ORANGE);
        cadastroButton.setForeground(Color.WHITE);
        cadastroButton.setFont(new Font("Arial", Font.BOLD, 12));
        cadastroButton.setPreferredSize(new Dimension(100, 25));
        cadastroButton.addActionListener(this::cadastroAction);
        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(cadastroButton, gbc);

        panel.add(formPanel, BorderLayout.CENTER);

        // Painel para exibir o cardápio
        JPanel cardapioPanel = new JPanel();
        cardapioPanel.setLayout(new BorderLayout());
        cardapioPanel.setBorder(BorderFactory.createTitledBorder("Cardápio"));
        
        JTextArea cardapioTextArea = new JTextArea();
        cardapioTextArea.setEditable(false); // Não permitir edição
        cardapioTextArea.setLineWrap(true);
        cardapioTextArea.setWrapStyleWord(true);
        cardapioTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JScrollPane scrollPane = new JScrollPane(cardapioTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        cardapioPanel.add(scrollPane, BorderLayout.CENTER);
        
        panel.add(cardapioPanel, BorderLayout.SOUTH);

        // Painel de rodapé
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(255, 165, 0));  // Laranja
        JLabel footerLabel = new JLabel("Desenvolvido por Fernanda Rocha, Maria Clara Meira e Maria Herculana.");
        footerLabel.setForeground(Color.WHITE);
        footerPanel.add(footerLabel);
        panel.add(footerPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    // Método que lida com o botão de login
    private void loginAction(ActionEvent e) {
        String userType = (String) userTypeComboBox.getSelectedItem();  // Obter o tipo de usuário selecionado
        String login = loginField.getText();  // Obter o login
        String senha = new String(senhaField.getPassword());  // Obter a senha

        if ("Administrador".equals(userType)) {
            loginAdmin(login, senha);
        } else {
            loginCliente(login, senha);
        }
    }

    // Método que lida com o botão de cadastro
    private void cadastroAction(ActionEvent e) {
        String userType = (String) userTypeComboBox.getSelectedItem();  // Obter o tipo de usuário selecionado
        String login = loginField.getText();  // Obter o login
        String senha = new String(senhaField.getPassword());  // Obter a senha

        if ("Administrador".equals(userType)) {
            createAdmin(login, senha);
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
                admin.exibirMenuAdmin();  // Exibir menu para o administrador
            } else {
                JOptionPane.showMessageDialog(null, "Login ou senha de administrador inválidos!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao validar login: " + ex.getMessage());
        }
    }

 private void loginCliente(String login, String senha) {
    try {
        Cliente cliente = clienteDao.validarLogin(login, senha); // Validação do login do cliente
        if (cliente != null) {
            JOptionPane.showMessageDialog(null, "Login de cliente realizado com sucesso!");
            cliente.exibirMenuCliente();  // Exibir menu para o cliente
        } else {
            JOptionPane.showMessageDialog(null, "Login ou senha de cliente inválidos!");
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Erro ao validar login: " + ex.getMessage());
    }
}


    // Método para cadastrar administrador
    private void createAdmin(String login, String senha) {
        Admin newAdmin = new Admin(login, senha);

        try {
            if (!adminExists(login)) {
                AdminDao.inserirUsuario(newAdmin);
                JOptionPane.showMessageDialog(null, "Administrador cadastrado com sucesso!");
                adminList.add(newAdmin);
            } else {
                JOptionPane.showMessageDialog(null, "Já existe um administrador com esse login.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar administrador: " + ex.getMessage());
        }
    }

    // Método para cadastrar cliente
    private void createCliente(String login, String senha) {
        // Adicione aqui a lógica para cadastrar um novo cliente
        JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
    }

    // Verificar se o administrador já existe
    private boolean adminExists(String login) {
        for (Admin admin : adminList) {
            if (admin.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    // Carregar cardápio (apenas exemplo)
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
