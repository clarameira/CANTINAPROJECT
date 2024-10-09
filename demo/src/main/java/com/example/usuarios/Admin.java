package com.example.usuarios;

import java.util.Scanner;
import java.sql.SQLException;
import java.util.Iterator;

import com.example.DAO.AdminDao;
import com.example.aplicacoes.Cantina;
import com.example.aplicacoes.Cardapio;
import com.example.aplicacoes.ItemCard;

public class Admin extends Usuario{

    private Cardapio cardapio;
    private Cantina cantina;
    Scanner sc = new Scanner(System.in);
    private AdminDao admr;
    
    public Admin(String login, String senha, Cantina cantina){
        super(login,senha);
        this.admr = new AdminDao();
        this.cantina =  cantina;
        this.cardapio = new Cardapio();
    }


    
    public void criaradm(){
        boolean usuarioExistente = false;
        System.out.println("login: ");
        String login = sc.nextLine();
        
        System.out.println("senha: ");
        String senha = sc.nextLine();
        // sc.next();

        Admin admin = new Admin(login, senha, cantina);

        for (Admin u : cantina.admin) {
            if (u instanceof Admin && u.getLogin().equals(login)) {
                usuarioExistente = true;
                break;
            }
        }
 
        if(!usuarioExistente){
            AdminDao.inserirUsuario(admin);
            cantina.admin.add(admin); 
            System.out.println("Admin criado com sucesso!");
        }else{
            System.out.println("alguem com essa conta ja existe");
        }
         }
         

    


    public void loginAdmin() {
        System.out.println("\n-------------------");
        System.out.println("Login Administrador:");
        String login = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        for (Usuario u : cantina.admin) {
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


    public void deletaradm(){
        String nome, senha;
    
        System.out.println("Qual administrador deseja deletar?");
        nome = sc.nextLine();
        System.out.println("Qual a senha?");
        senha = sc.nextLine();
    
        Admin ad = new Admin(nome, senha, cantina);
        Iterator<Admin> iterator = cantina.admin.iterator();
    
        boolean adminRemovido = false;
    
        while (iterator.hasNext()) {
            Admin adm = iterator.next();
            
            
            if (adm.getLogin().equals(ad.getLogin()) && adm.getSenha().equals(ad.getSenha())) {
                iterator.remove(); 
                System.out.println("entrou");
    
                try {
                    admr.deletar(adm);
                    System.out.println("Administrador deletado com sucesso.");
                    
                    adminRemovido = true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Erro ao deletar administrador do banco.");
                    
                }
                break; 
                
            }
        }
    
        if (!adminRemovido) {
            System.out.println("Administrador não encontrado ou senha incorreta.");
        } 
    }
}
