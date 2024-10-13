package com.example.aplicacoes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.usuarios.Admin;
import com.example.DAO.AdminDao;
import com.example.DAO.ItemDao;

public class CantinaSwing {
    private List<Admin> adminList;
    private AdminDao adminDao;
    private Cardapio cardapio;
    
    public CantinaSwing() {
        this.adminList = new ArrayList<>();
        this.adminDao = new AdminDao();
        this.cardapio = new Cardapio(); 
        initializeUI();
        carregarCardapio();
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
                admin.exibirMenuAdmin(); 
            } else {
                JOptionPane.showMessageDialog(null, "Login ou senha inválidos!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao validar login: " + ex.getMessage());
        }
    }

    private void createAdmin(ActionEvent e) {
        String login = JOptionPane.showInputDialog("Digite o login do novo administrador:");
        String senha = JOptionPane.showInputDialog("Digite a senha do novo administrador:");

        Admin newAdmin = new Admin(login, senha);

        try {
            if (!adminExists(login)) {
                AdminDao.inserirUsuario(newAdmin);
                JOptionPane.showMessageDialog(null, "Administrador criado com sucesso!");
                adminList.add(newAdmin); 
            } else {
                JOptionPane.showMessageDialog(null, "Já existe um usuário com esse login.");
            }
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
