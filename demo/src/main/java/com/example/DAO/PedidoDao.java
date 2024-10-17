package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.example.aplicacoes.Pedido;

public class PedidoDao {

    // Método para inserir o pedido no banco de dados
    public static void inserirPedido(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO pedidos (cliente_id, nome_cliente, total_preco) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();  // Utiliza a classe Conexao para obter a conexão
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Definir os parâmetros da consulta
            stmt.setInt(1, pedido.getid());  // ID do cliente que fez o pedido
            stmt.setString(2, pedido.getnomeCliente());  // Nome do cliente
           

            // Executar a consulta
            stmt.executeUpdate();
            System.out.println("Pedido inserido com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir o pedido: " + e.getMessage());
            throw e;  // Relançar a exceção para que seja tratada em níveis superiores
        }
    }
    
    // Outros métodos como buscar, atualizar ou deletar pedidos podem ser adicionados conforme necessário
}

