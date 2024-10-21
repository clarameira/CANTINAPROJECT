package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.usuarios.Admin;
import com.example.aplicacoes.CantinaSwing;

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
            throw e; 
        }
    }

    public void pegarTodos(CantinaSwing cantina) throws SQLException {
        Connection conn = Conexao.conectar();
        String sql = "SELECT login, senha FROM administrador"; 

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                String login = rs.getString("login");
                String senha = rs.getString("senha");

                Admin adm = new Admin(login, senha); 
                cantina.adicionarAdmin(adm); 
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
            throw e; 
        } 
    }
    
    
    public static int deletar(String admin) throws SQLException {
        String sql = "DELETE FROM administrador WHERE login = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, admin);
            return ps.executeUpdate();
        }
    }
    public static Admin buscarPorLogin(String login) throws SQLException {
        Admin admin = null;
        String sql = "SELECT * FROM administrador WHERE login = ?"; 
        
        try (Connection conn = Conexao.conectar();  
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, login); 
            
            ResultSet rs = stmt.executeQuery(); 
            
            if (rs.next()) {
                String senha = rs.getString("senha");  
                admin = new Admin(login, senha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao buscar admin por login: " + e.getMessage());
        }
        
        return admin;
    }


    public Admin validarLogin(String login, String senha) throws SQLException {
        String sql = "SELECT * FROM administrador WHERE login = ? AND senha = ?";
        
        try (Connection conexao = Conexao.conectar();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setString(1, login);
            pstmt.setString(2, senha);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Admin(rs.getString("login"), rs.getString("senha")); 
            }
        } catch (SQLException e) {
            System.err.println("Erro ao validar login:");
            e.printStackTrace();
            throw e; 
        }
        return null; 
    }
}