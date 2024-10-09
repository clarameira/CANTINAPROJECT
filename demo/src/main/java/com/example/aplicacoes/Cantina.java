package com.example.aplicacoes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.usuarios.Usuario;
import com.example.usuarios.Admin;


public class Cantina {


   Scanner sc;
   public List<Admin> admin;
   private Admin adm; 
   public Usuario usuarioLogado;

   public Cantina(){
      this.sc = new Scanner(System.in);
      this.admin = new ArrayList<>();
      this.adm = new Admin(null, null, this);
   }
   
   public void adicionarAdmin(Admin adm){
    this.admin.add(adm);
   }
   
   public void iniciar() {
       
      int opcao;
      do {
        for (Admin ad : admin) {
            System.out.println(ad);
        }
          System.out.println("\n-----------------------------------------------");
          System.out.println(" Bem-vindo ao Restaurante Universitário UFERSA!");
          System.out.println("            CAMPUS PAU DOS FERROS");
          System.out.println("-----------------------------------------------\n");
          System.out.println("   1. Fazer login como administrador");
          System.out.println("   2. Fazer login como cliente");
          System.out.println("   3. Criar uma conta como administrador");
          System.out.println("   4. Criar uma conta como aluno");
          System.out.println("   5. Sair");
          System.out.print("                Escolha uma opção: ");
          opcao = sc.nextInt();
           sc.nextLine(); 

          switch (opcao) {
              case 1:
                  adm.loginAdmin();
                  break;
              case 2:
                  
                  break;
              case 3:
                  adm.criaradm();
                  break;
              case 4:
                  adm.deletaradm();
                  break;
              case 5:
                  System.out.println("Saindo.");
                  System.exit(0);
                  break;
              default:
                  System.out.println("Opção inválida!");
          }
      } while (opcao!= 5);
  }
}
