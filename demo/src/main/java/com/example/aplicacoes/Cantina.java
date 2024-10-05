package com.example.aplicacoes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.usuarios.Usuario;
import com.example.usuarios.Admin;


public class Cantina {


   Scanner sc;
   public List<Usuario> usuario;
   private Admin admin; 
   public Usuario usuarioLogado;

   public Cantina(){
      this.sc = new Scanner(System.in);
      this.usuario = new ArrayList<>();
      this.admin = new Admin(null, null, this);
   }
   
   public void iniciar() {
       
      int opcao;
      do {
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
         //  sc.next(); 

          switch (opcao) {
              case 1:
                  admin.loginAdmin();
                  break;
              case 2:
                  
                  break;
              case 3:
                  admin.criaradm();
                  break;
              case 4:
              
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
