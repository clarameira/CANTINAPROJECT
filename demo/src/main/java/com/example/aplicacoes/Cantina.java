package com.example.aplicacoes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.usuarios.Admin;
import com.example.DAO.AdminDao;

public class Cantina {
    private Scanner sc;
    private List<Admin> admins;
    private AdminDao adminDao;
    private Cardapio cardapio; 

    public Cantina() {
        this.sc = new Scanner(System.in);
        this.admins = new ArrayList<>();
        this.adminDao = new AdminDao();
        this.cardapio = new Cardapio(); 
    }

    public void adicionarAdmin(Admin adm) {
        this.admins.add(adm);
    }

    public void iniciar() throws SQLException {
        adminDao.pegarTodos(this); 
        
        int opcao;
        do {
            System.out.println("\n-----------------------------------------------");
            System.out.println(" Bem-vindo ao Restaurante Universitário UFERSA!");
            System.out.println("            CAMPUS PAU DOS FERROS");
            System.out.println("-----------------------------------------------\n");
            System.out.println("   1. Fazer login como administrador");
            System.out.println("   2. Criar uma conta como administrador");
            System.out.println("   3. Sair");
            System.out.print("                Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine(); 

            switch (opcao) {
                case 1:
                    loginAdmin();
                    break;
                case 2:
                    criarAdmin();
                    break;
                case 3:
                    System.out.println("Saindo.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 3);
    }

    private void loginAdmin() {
        System.out.print("Digite o login: ");
        String login = sc.nextLine();
        System.out.print("Digite a senha: ");
        String senha = sc.nextLine();
    
        try {
            Admin admin = adminDao.validarLogin(login, senha);
            if (admin != null) {
                admin.setCardapio(cardapio); // Atualiza o Cardapio do Admin
                System.out.println("Login realizado com sucesso!");
                admin.exibirMenuAdmin();  // Chama o menu do administrador
            } else {
                System.out.println("Login ou senha inválidos!");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao validar login: " + ex.getMessage());
        }
    }
    

    private void criarAdmin() {
        System.out.print("Digite o login do novo administrador: ");
        String login = sc.nextLine();
        System.out.print("Digite a senha do novo administrador: ");
        String senha = sc.nextLine();

        Admin newAdmin = new Admin(login, senha);  // Passa o Cardapio para o novo Admin

        try {
            if (!adminExists(login)) {
                AdminDao.inserirUsuario(newAdmin);
                System.out.println("Administrador criado com sucesso!");
                admins.add(newAdmin); // Adiciona à lista de administradores
            } else {
                System.out.println("Já existe um usuário com esse login.");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao criar administrador: " + ex.getMessage());
        }
    }


    public Cardapio getCardapio() {
        return this.cardapio; // Retorna a instância de Cardapio
    }
    
    private boolean adminExists(String login) {
        for (Admin admin : admins) {
            if (admin.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }
}
