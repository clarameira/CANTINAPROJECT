package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.usuarios.Admin;
import com.example.aplicacoes.Cantina;

public class AdminDao { 
    public static void inserirUsuario(Admin admin) throws SQLException {
        String sql = "INSERT INTO administrador (login, senha) VALUES (?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setString(1, admin.getLogin());
            pstmt.setString(2, admin.getSenha());
            
            int linhasAfetadas = pstmt.executeUpdate();
            System.out.println(linhasAfetadas + " linha(s) inserida(s) com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir dados:");
            throw e; // Repropaga a exceção
        }
    }

    public void pegarTodos(Cantina cantina) throws SQLException {
        Connection conn = Conexao.conectar();
        String sql = "SELECT login, senha FROM administrador"; 

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                String login = rs.getString("login");
                String senha = rs.getString("senha");

                Admin adm = new Admin(login, senha); // Passa o cardápio
                cantina.adicionarAdmin(adm); // Adiciona à lista de administradores
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
            throw e; // Repropaga a exceção
        } 
    }

    public static int deletar(Admin admin) throws SQLException {
        String sql = "DELETE FROM administrador WHERE login = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, admin.getLogin());
            return ps.executeUpdate();
        }
    }

    public Admin validarLogin(String login, String senha) throws SQLException {
        String sql = "SELECT * FROM administrador WHERE login = ? AND senha = ?";
        
        try (Connection conexao = Conexao.conectar();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setString(1, login);
            pstmt.setString(2, senha);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Admin(rs.getString("login"), rs.getString("senha")); // Ajuste conforme sua lógica
            }
        } catch (SQLException e) {
            System.err.println("Erro ao validar login:");
            e.printStackTrace();
            throw e; // Repropaga a exceção
        }
        return null; // Retorna null se não encontrar o admin
    }
}
