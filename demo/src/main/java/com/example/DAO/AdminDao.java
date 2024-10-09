package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import com.example.usuarios.Admin;
import com.example.aplicacoes.Cantina;

public class AdminDao { 
    
    
    
        public static void inserirUsuario(Admin admin) {
            String sql = "INSERT INTO administrador (login, senha) VALUES (?, ?)";
    
            try (Connection conexao = Conexao.conectar();
                 PreparedStatement pstmt = conexao.prepareStatement(sql)) {
    
                pstmt.setString(1, admin.getLogin());
                pstmt.setString(2, admin.getSenha());
                
    
                int linhasAfetadas = pstmt.executeUpdate();
                System.out.println(linhasAfetadas + " linha(s) inserida(s) com sucesso!");

    
            } catch (SQLException e) {
                System.err.println("Erro ao inserir dados:");
                e.printStackTrace();
            }
        }


        public List<Admin> pegarTodos(Cantina cantina) throws SQLException{
            
            Connection conn = Conexao.conectar();
            String sql = "SELECT login, senha FROM administrador"; 

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String login = rs.getString("login");
                String senha = rs.getString("senha");

                Admin  adm = new Admin(login, senha, null); 
                cantina.adicionarAdmin(adm);
            }
           
        } catch (SQLException e) {
            e.printStackTrace(); 
        } 
        return cantina.admin;
        }


        
        public int deletar(Admin admin) throws SQLException {
            String sql = "DELETE FROM administrador WHERE login = ?";
            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, admin.getLogin());
    
            int resultado = ps.executeUpdate();
    
            return resultado;
        }
    }
    
    

