package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.usuarios.Cliente;

public class ClienteDao {
 
    
    public static void inserirUsuario(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (login, senha) VALUES (?, ?)";
    
        try (Connection conexao = Conexao.conectar();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
    
            pstmt.setString(1, cliente.getLogin());
            pstmt.setString(2, cliente.getSenha());
            
            int linhasAfetadas = pstmt.executeUpdate();
            System.out.println(linhasAfetadas + " linha(s) inserida(s) com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir dados:");
            throw e; // Repropaga a exceção
        }
    }
    
   public Cliente validarLogin(String login, String senha) throws SQLException {
    String sql = "SELECT * FROM cliente WHERE login = ? AND senha = ?";
    
    try (Connection conexao = Conexao.conectar();
         PreparedStatement pstmt = conexao.prepareStatement(sql)) {

        pstmt.setString(1, login);
        pstmt.setString(2, senha);
        
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return new Cliente(rs.getString("login"), rs.getString("senha")); // Ajuste conforme sua lógica de cliente
        }
    } catch (SQLException e) {
        System.err.println("Erro ao validar login:");
        e.printStackTrace();
        throw e; // Repropaga a exceção
    }
    return null; // Retorna null se não encontrar o cliente
}

}
