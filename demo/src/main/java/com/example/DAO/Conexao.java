package com.example.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    
        private static final String URL = "jdbc:postgresql://localhost:5433/cantinaproject";
        private static final String USUARIO = "postgres"; 
        private static final String SENHA = "root"; 
    
        public static Connection conectar() {
            Connection conn = null;
            
            try {
                
                Class.forName("org.postgresql.Driver");
                System.out.println("Driver JDBC do PostgreSQL carregado com sucesso!");
    
                conn = DriverManager.getConnection(URL, USUARIO, SENHA);
                System.out.println("Conexão estabelecida com sucesso!");
            } catch (ClassNotFoundException e) {
                System.err.println("Driver JDBC do PostgreSQL não encontrado. Verifique se a dependência está no classpath.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("Erro ao conectar ao PostgreSQL:");
                e.printStackTrace();
            }
            return conn;
        }
    }

