package com.example.aplicacoes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.usuarios.Admin;
import com.example.DAO.AdminDao;

public class CantinaSwing {
    private List<Admin> adminList;
    private AdminDao adminDao;
    private Cardapio cardapio; // Adicionado um objeto Cardapio
    private JFrame menuFrame; // Campo para armazenar a janela do menu

    public CantinaSwing() {
        this.adminList = new ArrayList<>();
        this.adminDao = new AdminDao();
        this.cardapio = new Cardapio(); // Inicializa o cardápio
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
            Admin admin = adminDao.validarLogin(login, senha);
            if (admin != null) {
                JOptionPane.showMessageDialog(null, "Login realizado com sucesso!");
                exibirMenuAdmin(admin); // Chama o método para exibir o menu do administrador
            } else {
                JOptionPane.showMessageDialog(null, "Login ou senha inválidos!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao validar login: " + ex.getMessage());
        }
    }
    private void exibirMenuAdmin(Admin admin) {
        int opcao = 0; // Inicializa a variável opcao
        do {
            String menu = "     \nMenu Administrador:\n" +
                          "      1. Exibir cardápio\n" +
                          "      2. Adicionar ao cardápio\n" +
                          "      3. Editar no cardápio\n" +
                          "      4. Remover do cardápio\n" +
                          "      5. Sair\n" +
                          "       Escolha uma opção:";
            String input = JOptionPane.showInputDialog(menu);
            if (input == null) break; // Se o usuário fechar a janela, sair do loop
            
            try {
                opcao = Integer.parseInt(input);
                switch (opcao) {
                    case 1:
                        exibirCardapio(); // Chama o método para exibir o cardápio
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
            double preco = Double.parseDouble(precoInput); // Converte para double
            ItemCard novoItem = new ItemCard(nome, descricao, preco);
            cardapio.adicionarItem(novoItem);
            JOptionPane.showMessageDialog(null, "Item adicionado ao cardápio!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Preço inválido. Por favor, insira um número.");
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
        Admin newAdmin = new Admin(login, senha, null); // Passar a instância correta da cantina, se necessário

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
