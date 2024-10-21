package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.aplicacoes.Cardapio;
import com.example.aplicacoes.ItemCard;
public class ItemDao {

    public static void inserirItem(ItemCard item) throws SQLException { 
        String sql = "INSERT INTO cardapio (item, descricao, preco) VALUES (?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setString(1, item.getItem());
            pstmt.setString(2, item.getDescricao());
            pstmt.setDouble(3, item.getPreco());

            int linhasAfetadas = pstmt.executeUpdate();
            System.out.println(linhasAfetadas + " linha(s) inserida(s) com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir dados:");
            throw e; 
        }
    }

    public static void pegarTodos(Cardapio cardapio) throws SQLException {
        String sql = "SELECT item, descricao, preco FROM cardapio"; 

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String item = rs.getString("item");
                String descricao = rs.getString("descricao");
                double preco = rs.getDouble("preco");

                ItemCard it = new ItemCard(item, descricao, preco); 
                cardapio.adicionarItem(it); 
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar itens do cardápio: " + e.getMessage());
            throw e; 
        }
    }

    public static void atualizarItem(ItemCard item, String nomeOriginal) throws SQLException {
        String sql = "UPDATE cardapio SET item = ?, descricao = ?, preco = ? WHERE item = ?"; 
        
        try (Connection conexao = Conexao.conectar();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
        
            pstmt.setString(1, item.getItem()); 
            pstmt.setString(2, item.getDescricao());
            pstmt.setDouble(3, item.getPreco());
            pstmt.setString(4, nomeOriginal); 
    
            int linhasAfetadas = pstmt.executeUpdate();
            if (linhasAfetadas == 0) {
                System.out.println("Nenhum item encontrado com o nome: " + nomeOriginal);
            } else {
                System.out.println(linhasAfetadas + " linha(s) atualizada(s) com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar dados: " + e.getMessage());
            throw e; 
        }
    }

    public static void removerItem(String nome) throws SQLException {
        String sql = "DELETE FROM cardapio WHERE item = ?";
        
        try (Connection conexao = Conexao.conectar();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            
            pstmt.setString(1, nome);
            int linhasAfetadas = pstmt.executeUpdate();
    
            System.out.println("Tentativa de remoção do item: " + nome);
            System.out.println("Linhas afetadas: " + linhasAfetadas);
            
            if (linhasAfetadas == 0) {
                System.out.println("Nenhum item encontrado com o nome: " + nome);
            } else {
                System.out.println(linhasAfetadas + " linha(s) removida(s) com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao remover dados: " + e.getMessage());
            throw e; 
        }
    }    

}
