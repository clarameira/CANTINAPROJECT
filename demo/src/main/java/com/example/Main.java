package com.example;

import com.example.aplicacoes.Cantina;

import java.sql.SQLException;

import com.example.DAO.AdminDao;
public class Main {
    public static void main(String[] args) throws SQLException {
        
        AdminDao inicio = new AdminDao();
        Cantina cantina = new Cantina();
        inicio.pegarTodos(cantina);
        cantina.iniciar();

    }
}
