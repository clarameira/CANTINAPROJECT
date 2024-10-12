package com.example.aplicacoes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.usuarios.Admin;
import com.example.DAO.AdminDao; // Certifique-se de importar a classe AdminDao.

public class CantinaSwing {
    private List<Admin> adminList;
    private Admin adm; 
    private AdminDao adminDao; // Instância do AdminDao

    public CantinaSwing() {
        this.adminList = new ArrayList<>();
        this.adm = new Admin("admin", "admin", null); // Valores iniciais para a conta de admin
        this.adminDao = new AdminDao(); // Inicializando AdminDao
        initializeUI();
    }

    private void initializeUI() {
        // Criando a janela principal (frame)
        JFrame frame = new JFrame("Sistema Cantina");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Criando painel
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
        
        // Exibir a janela
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null); // Definindo layout nulo para controle manual dos componentes

        // Criando o botão de login
        JButton loginButton = new JButton("Login Admin");
        loginButton.setBounds(100, 50, 200, 25); // Definindo posição e tamanho do botão
        loginButton.addActionListener(this::loginAdmin);
        panel.add(loginButton);

        // Criando o botão de criar conta
        JButton createAccountButton = new JButton("Criar Conta Admin");
        createAccountButton.setBounds(100, 100, 200, 25); // Definindo posição e tamanho do botão
        createAccountButton.addActionListener(this::createAdmin);
        panel.add(createAccountButton);
    }

    private void loginAdmin(ActionEvent e) {
        // Simulação de login
        String login = JOptionPane.showInputDialog("Digite o login:");
        String senha = JOptionPane.showInputDialog("Digite a senha:");
    
        try {
            // Verificando se os dados de login estão corretos usando o AdminDao
            if (adminDao.validarLogin(login, senha)) {
                JOptionPane.showMessageDialog(null, "Login realizado com sucesso!");
                // Aqui você pode chamar um método para redirecionar para o menu do administrador
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

        // Verifica se já existe um usuário com o mesmo login
        if (adminExists(login)) {
            JOptionPane.showMessageDialog(null, "Já existe um usuário com esse login.");
            return;
        }

        // Criação do novo administrador
        Admin newAdmin = new Admin(login, senha, this.adm.getCantina());
        
        try {
            // Tente criar o administrador e salvar no banco
            AdminDao.inserirUsuario(newAdmin); // Chama o método de inserção
            JOptionPane.showMessageDialog(null, "Administrador criado com sucesso!");
            adminList.add(newAdmin); // Adicione à lista de administradores
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CantinaSwing::new);
    }
}