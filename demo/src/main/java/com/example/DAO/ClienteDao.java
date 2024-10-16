package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.usuarios.Cliente;
import com.example.aplicacoes.CantinaSwing;

public class ClienteDao {
    
    public  void inserirUsuario(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (login, senha) VALUES (?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getLogin());
            pstmt.setString(2, cliente.getSenha());
            
            int linhasAfetadas = pstmt.executeUpdate();
            System.out.println(linhasAfetadas + " cliente(s) inserido(s) com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir dados:");
            throw e; // Repropaga a exceção
        }
    }

    public void pegarTodos(CantinaSwing cantina) throws SQLException {
        Connection conn = Conexao.conectar();
        String sql = "SELECT login, senha FROM cliente"; 

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                String login = rs.getString("login");
                String senha = rs.getString("senha");

                Cliente cliente = new Cliente(login, senha); // Cria um novo objeto Cliente
                cantina.adicionarCliente(cliente); // Adiciona à lista de clientes na CantinaSwing
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
            throw e; // Repropaga a exceção
        } 
    }
    
    public static int deletar(Cliente cliente) throws SQLException {
        String sql = "DELETE FROM cliente WHERE login = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cliente.getLogin());
            return ps.executeUpdate();
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
