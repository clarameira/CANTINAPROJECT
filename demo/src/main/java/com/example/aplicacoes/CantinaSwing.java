package com.example.aplicacoes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.usuarios.Admin;
import com.example.DAO.AdminDao;
import com.example.DAO.ItemDao; // Importar ItemDao

public class CantinaSwing {
    private List<Admin> adminList;
    private AdminDao adminDao;
    private Cardapio cardapio;
    private JFrame menuFrame;

    public CantinaSwing() {
        this.adminList = new ArrayList<>();
        this.adminDao = new AdminDao();
        this.cardapio = new Cardapio(); 
        initializeUI();
        carregarCardapio(); // Carrega o cardápio do banco de dados na inicialização
    }

    private void initializeUI() {
        JFrame frame = new JFrame("Sistema Cantina");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null); 

        JButton loginButton = new JButton("Login Admin");
        loginButton.setBounds(100, 50, 200, 25); 
        loginButton.addActionListener(this::loginAdmin);
        panel.add(loginButton);

        JButton createAccountButton = new JButton("Criar Conta Admin");
        createAccountButton.setBounds(100, 100, 200, 25); 
        createAccountButton.addActionListener(this::createAdmin);
        panel.add(createAccountButton);
    }

    private void loginAdmin(ActionEvent e) {
        String login = JOptionPane.showInputDialog("Digite o login:");
        String senha = JOptionPane.showInputDialog("Digite a senha:");

        try {
            Admin admin = adminDao.validarLogin(login, senha);
            if (admin != null) {
                JOptionPane.showMessageDialog(null, "Login realizado com sucesso!");
                exibirMenuAdmin(admin); 
            } else {
                JOptionPane.showMessageDialog(null, "Login ou senha inválidos!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao validar login: " + ex.getMessage());
        }
    }

    private void exibirMenuAdmin(Admin admin) {
        int opcao = 0; 
        do {
            String menu = "     \nMenu Administrador:\n" +
                          "      1. Exibir cardápio\n" +
                          "      2. Adicionar ao cardápio\n" +
                          "      3. Editar no cardápio\n" +
                          "      4. Remover do cardápio\n" +
                          "      5. Sair\n" +
                          "       Escolha uma opção:";
            String input = JOptionPane.showInputDialog(menu);
            if (input == null) break; 
            
            try {
                opcao = Integer.parseInt(input);
                switch (opcao) {
                    case 1:
                        exibirCardapio(); 
                        break;
                    case 2:
                        adicionarItemCardapio();
                        break;
                    case 3:
                        JOptionPane.showMessageDialog(null, "Funcionalidade de edição ainda não implementada.");
                        break;
                    case 4:
                        JOptionPane.showMessageDialog(null, "Funcionalidade de remoção ainda não implementada.");
                        break;
                    case 5:
                        JOptionPane.showMessageDialog(null, "Saindo.");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opção inválida!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, insira um número válido.");
            }
        } while (opcao != 5);
    }

    private void createAdmin(ActionEvent e) {
        String login = JOptionPane.showInputDialog("Digite o login do novo administrador:");
        String senha = JOptionPane.showInputDialog("Digite a senha do novo administrador:");

        if (adminExists(login)) {
            JOptionPane.showMessageDialog(null, "Já existe um usuário com esse login.");
            return;
        }

        Admin newAdmin = new Admin(login, senha, null);

        try {
            AdminDao.inserirUsuario(newAdmin); 
            JOptionPane.showMessageDialog(null, "Administrador criado com sucesso!");
            adminList.add(newAdmin); 
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao criar administrador: " + ex.getMessage());
        }
    }

    private boolean adminExists(String login) {
        for (Admin admin : adminList) {
            if (admin.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }
    private void exibirCardapio() {
        StringBuilder cardapioText = new StringBuilder();
        cardapioText.append("\n***************************************************************************\n");
        cardapioText.append("                         CARDÁPIO SEMANAL:\n");
        cardapioText.append("*****************************************************************************\n");
        for (ItemCard item : cardapio.itens) {
            cardapioText.append("- ").append(item.getItem()).append(": ")
                        .append(item.getDescricao()).append(" - R$")
                        .append(item.getPreco()).append("\n");
        }
        cardapioText.append("-----------------------------------------------------------------------------");

        JOptionPane.showMessageDialog(null, cardapioText.toString(), "Cardápio", JOptionPane.INFORMATION_MESSAGE);
    }

    // Método para adicionar um item ao cardápio
    public void adicionarItemCardapio() {
        String nome = JOptionPane.showInputDialog("Insira o nome do item:");
        String descricao = JOptionPane.showInputDialog("Insira a descrição:");
        String precoInput = JOptionPane.showInputDialog("Insira o preço:");
    
        try {
            double preco = Double.parseDouble(precoInput); 
            ItemCard novoItem = new ItemCard(nome, descricao, preco);
            cardapio.adicionarItem(novoItem);
            ItemDao.inserirItem(novoItem); // Inserir o item no banco de dados
            JOptionPane.showMessageDialog(null, "Item adicionado ao cardápio!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Preço inválido. Por favor, insira um número.");
        } catch (SQLException e) { // Agora é seguro capturar SQLException
            JOptionPane.showMessageDialog(null, "Erro ao adicionar item ao banco de dados: " + e.getMessage());
        }
    }
    
    private void carregarCardapio() {
        try {
            ItemDao.pegarTodos(cardapio); // Carrega os itens do banco de dados
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar o cardápio: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CantinaSwing::new);
    }
}
