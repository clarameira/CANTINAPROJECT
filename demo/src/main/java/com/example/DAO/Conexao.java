package com.example.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    
    private static final String URL = "jdbc:postgresql://localhost:5432/cantina";
    private static final String USUARIO = "postgres"; 
    private static final String SENHA = "postgres"; 

    public static Connection conectar() throws SQLException { 
        Connection conn = null;
        
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver JDBC do PostgreSQL carregado com sucesso!");

            conn = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("Conexão estabelecida com sucesso!");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC do PostgreSQL não encontrado. Verifique se a dependência está no classpath.");
            e.printStackTrace();
        }
        
        if (conn == null) {
            throw new SQLException("Não foi possível estabelecer a conexão com o banco de dados.");
        }

        return conn;
    }
}
