package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.aplicacoes.Cardapio;
import com.example.aplicacoes.ItemCard;
public class ItemDao {

    public static void inserirItem(ItemCard item) throws SQLException { // Adicionei throws SQLException aqui
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
            throw e; // Re-lança a exceção
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
}