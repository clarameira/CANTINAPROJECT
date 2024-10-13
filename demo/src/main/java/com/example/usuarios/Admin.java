package com.example.usuarios;

import java.sql.SQLException;

import javax.swing.*;

import com.example.DAO.ItemDao;
import com.example.aplicacoes.Cardapio;
import com.example.aplicacoes.ItemCard;

public class Admin {
    private String login;
    private String senha;
    private Cardapio cardapio;
    private int cont;

    public Admin(String login, String senha) {
        this.login = login;
        this.senha = senha;
        this.cardapio = new Cardapio();
        this.cont = 0; 
    }

    // Getters
    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public void setCardapio(Cardapio cardapio) {
        this.cardapio = cardapio;
    }

    public void exibirMenuAdmin() throws SQLException {
        int opcao = 0; 
        do {
            String menu = "     \nMenu Administrador:\n" +
                          "      1. Exibir cardápio\n" +
                          "      2. Adicionar ao cardápio\n" +
                          "      3. Editar item no cardápio\n" +
                          "      4. Remover do cardápio\n" +
                          "      5. Sair\n" +
                          "       Escolha uma opção:";
            String input = JOptionPane.showInputDialog(menu);
            if (input == null) break; // Se o usuário cancelar, sai do loop

            try {
                opcao = Integer.parseInt(input); // Converte a entrada do usuário para um inteiro
                switch (opcao) {
                    case 1:
                    if (cardapio != null) {
                        if(cont==0){
                            cont++;
                        ItemDao.pegarTodos(cardapio);
                    }
                        cardapio.exibir(); // Chama o método para exibir cardápio
                    } else {
                        JOptionPane.showMessageDialog(null, "O cardápio não está disponível.");
                    }
                     // Chama o método para exibir cardápio
                        break;
                    case 2:
                        adicionarItemCardapio(); // Adiciona item ao cardápio
                        break;
                    case 3:
                        editarItemCardapio(); // Implementar este método para edição
                        break;
                    case 4:
                        removerItemCardapio(); 
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

    private void adicionarItemCardapio() throws SQLException {
        String nome = JOptionPane.showInputDialog("Digite o nome do item:");
        String descricao = JOptionPane.showInputDialog("Digite a descrição do item:");
        
        double preco;
        while (true) {
            String precoInput = JOptionPane.showInputDialog("Digite o preço do item:");
            if (precoInput == null) return; // Se o usuário cancelar, sai do método
            try {
                preco = Double.parseDouble(precoInput);
                break; // Sai do loop se a conversão for bem-sucedida
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Preço inválido. Tente novamente.");
            }
        }
        
        ItemCard novoItem = new ItemCard(nome, descricao, preco);
        cardapio.adicionarItem(novoItem);
        ItemDao.inserirItem(novoItem);
        JOptionPane.showMessageDialog(null, "Item adicionado com sucesso!");
    }

    private void editarItemCardapio() {
        
        JOptionPane.showMessageDialog(null, "Funcionalidade de edição não implementada ainda.");
    }

    private void removerItemCardapio() {
        String nome = JOptionPane.showInputDialog("Digite o nome do item que deseja remover:");
        if (nome == null || nome.trim().isEmpty()) return; // Verifica se o usuário cancelou ou não digitou nada
    
        boolean removido = cardapio.removerItem(nome);
        if (removido) {
            try {
                ItemDao.removerItem(nome); // Remover do banco de dados, utilizando o nome
                JOptionPane.showMessageDialog(null, "Item removido com sucesso!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao remover item do banco de dados: " + e.getMessage());
                e.printStackTrace(); 
            }
        } else {
            JOptionPane.showMessageDialog(null, "Item não encontrado no cardápio.");
        }
    }
    
    
}



