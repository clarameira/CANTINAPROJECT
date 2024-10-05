package com.example.usuarios;

import java.util.Scanner;

import com.example.aplicacoes.Cantina;
import com.example.aplicacoes.Cardapio;
import com.example.aplicacoes.ItemCard;

public class Admin extends Usuario{

    private Cardapio cardapio;
    private Cantina cantina;
    Scanner sc = new Scanner(System.in);
    
    public Admin(String login, String senha, Cantina cantina){
        super(login,senha);
        this.cantina =  cantina;
        this.cardapio = new Cardapio();
    }


    public void criaradm(){

        System.out.println("login: ");
        String login = sc.nextLine();
        
        System.out.println("senha: ");
        String senha = sc.nextLine();
        // sc.next();
        
         Admin admin = new Admin(login, senha, cantina);
         cantina.usuario.add(admin);

    }


    public void loginAdmin() {
        System.out.println("\n-------------------");
        System.out.println("Login Administrador:");
        String login = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        for (Usuario u : cantina.usuario) {
            if (u instanceof Admin && login.equals(u.getLogin()) && senha.equals(u.getSenha())) {
                System.out.println("Login bem-sucedido!");
                System.out.println("-------------------");
                cantina.usuarioLogado = u;
                exibirMenuAdmin();
                return;
            }
        }}

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
                sc.nextLine();
    
                switch (opcao) {
                    case 1:
                        cardapio.exibir();
                        break;
                    case 2:
                        adicionarItemCardapio();
                        break;
                    case 3:
                        
                        break;
                    case 4:
                        
                        break;
                    case 5:
                        System.out.println("Saindo.");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } while (opcao != 5);
        }
    
    public void adicionarItemCardapio() {
        System.out.print("Insira nome do item: ");
        String nome = sc.nextLine();
        System.out.print("Insira a descrição: ");
        String descricao = sc.nextLine();
        System.out.print("Insira o preço: ");
        double preco = sc.nextDouble();
        sc.nextLine();

        ItemCard novoItem = new ItemCard(nome, descricao, preco);
        cardapio.adicionarItem(novoItem);
        System.out.println("item adicionado!");
    }
}
