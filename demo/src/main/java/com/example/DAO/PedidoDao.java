package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.aplicacoes.ItemCard;
import com.example.aplicacoes.Pedido;

public class PedidoDao {

    // Método para inserir um pedido no banco de dados
    public static void inserirPedido(Pedido pedido) throws SQLException {
        Connection conn = Conexao.conectar();
        String sql = "INSERT INTO pedido (cliente, clienteid, item, preco) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (ItemCard item : pedido.getItens()) {
                pstmt.setString(1, pedido.getNomeCliente()); 
                pstmt.setInt(2, pedido.getClienteId());   
                pstmt.setString(3, item.getItem());       
                pstmt.setDouble(4, item.getPreco());      
                pstmt.addBatch();
            }
            pstmt.executeBatch(); 
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao inserir pedido: " + e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    public static List<Pedido> buscarPedidosPorCliente(String cliente) throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT item, cliente, clienteid, preco FROM pedido WHERE cliente = ?";


        try (Connection conn = Conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, cliente);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String item = rs.getString("item");
                String client = rs.getString("cliente");
                int id = rs.getInt("clienteid");
                double preco = rs.getDouble("preco");
               
                ItemCard itemCard = new ItemCard(item, preco);
                Pedido pedido = new Pedido(id, client, Collections.singletonList(itemCard)); 
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao buscar pedidos: " + e.getMessage());
        }

        return pedidos;
    }
}



    


