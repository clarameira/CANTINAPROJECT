package com.example.usuarios;

import java.util.Scanner;
import java.util.Iterator;
import java.sql.SQLException;

import com.example.DAO.AdminDao;
import com.example.aplicacoes.Cantina;
import com.example.aplicacoes.Cardapio;
import com.example.aplicacoes.ItemCard;

public class Admin extends Usuario {
    private Cardapio cardapio;
    private Cantina cantina;
    private Scanner sc = new Scanner(System.in);
    private AdminDao admr;

    // Construtor
    public Admin(String login, String senha, Cantina cantina) {
        super(login, senha);
        this.admr = new AdminDao();
        this.cantina = cantina;
        this.cardapio = new Cardapio();
    }

    public void criaradm() throws SQLException {
        System.out.print("Login: ");
        String login = sc.nextLine();
        
        System.out.print("Senha: ");
        String senha = sc.nextLine();
    
        // Verifica se o usuário já existe
        if (usuarioExiste(login)) {
            System.out.println("Já existe um usuário com esse login.");
            return; // Saia do método se o usuário já existir
        }
    
        Admin admin = new Admin(login, senha, cantina);
        
        // Chama o método para inserir no banco de dados
        AdminDao.inserirUsuario(admin); // Insere no banco de dados
        cantina.adicionarAdmin(admin); // Adiciona à lista de administradores
        System.out.println("Administrador criado com sucesso!");
    }

    // Verifica se o usuário existe
    private boolean usuarioExiste(String login) {
        for (Admin u : cantina.admin) {
            if (u.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    // Método para fazer login
    public void loginAdmin() {
        System.out.println("\n-------------------");
        System.out.println("Login Administrador:");
        System.out.print("Login: ");
        String login = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        for (Usuario u : cantina.admin) {
            if (u instanceof Admin && login.equals(u.getLogin()) && senha.equals(u.getSenha())) {
                System.out.println("Login bem-sucedido!");
                cantina.usuarioLogado = u; // Armazena o usuário logado
                exibirMenuAdmin();
                return;
            }
        }
        System.out.println("Login ou senha incorretos.");
    }

    // Método para exibir o menu do administrador
    public void exibirMenuAdmin() {
        int opcao;
        do {
            System.out.println("     \nMenu Administrador:");
            System.out.println("      1. Exibir cardápio");
            System.out.println("      2. Adicionar ao cardápio");
            System.out.println("      3. Editar no cardápio");
            System.out.println("      4. Remover do cardápio");
            System.out.println("      5. Sair");
            System.out.print("       Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine(); // Limpa o buffer

            switch (opcao) {
                case 1:
                    cardapio.exibir();
                    break;
                case 2:
                    adicionarItemCardapio();
                    break;
                case 3:
                    // Implementar lógica para editar item do cardápio
                    break;
                case 4:
                    // Implementar lógica para remover item do cardápio
                    break;
                case 5:
                    System.out.println("Saindo.");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 5);
    }

    // Método para adicionar um item ao cardápio
    public void adicionarItemCardapio() {
        System.out.print("Insira o nome do item: ");
        String nome = sc.nextLine();
        System.out.print("Insira a descrição: ");
        String descricao = sc.nextLine();
        System.out.print("Insira o preço: ");
        double preco = sc.nextDouble();
        sc.nextLine(); // Limpa o buffer

        ItemCard novoItem = new ItemCard(nome, descricao, preco);
        cardapio.adicionarItem(novoItem);
        System.out.println("Item adicionado ao cardápio!");
    }

    // Método para deletar um administrador
    public void deletaradm() {
        System.out.print("Qual administrador deseja deletar? ");
        String nome = sc.nextLine();
        System.out.print("Qual a senha? ");
        String senha = sc.nextLine();

        Admin adminParaRemover = new Admin(nome, senha, cantina);
        Iterator<Admin> iterator = cantina.admin.iterator();
        boolean adminRemovido = false;

        while (iterator.hasNext()) {
            Admin adm = iterator.next();
            if (adm.getLogin().equals(adminParaRemover.getLogin()) && adm.getSenha().equals(adminParaRemover.getSenha())) {
                iterator.remove(); // Remove da lista
                try {
                    admr.deletar(adm); // Remove do banco de dados
                    System.out.println("Administrador deletado com sucesso.");
                    adminRemovido = true;
                } catch (SQLException e) {
                    System.out.println("Erro ao deletar administrador do banco: " + e.getMessage());
                }
                break;
            }
        }

        if (!adminRemovido) {
            System.out.println("Administrador não encontrado ou senha incorreta.");
        }
    }

    public void setLogin(String login) {
        super.setLogin(login); // Chama o método da superclasse
    }

    public void setSenha(String senha) {
        super.setSenha(senha); // Chama o método da superclasse
    }

    public Cantina getCantina() {
        return cantina; // Retorna a cantina associada
    }
}