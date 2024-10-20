package com.example.usuarios;
public class Usuario {
    protected String login;
    protected String senha;
   

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
     
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }


    public void setLogin(String login) {
        this.login = login; // Definindo o novo valor de login
    }

    public void setSenha(String senha) {
        this.senha = senha; // Definindo o novo valor de senha
    }

    @Override
    public String toString() {
        return "login: " + getLogin() + " senha: " + getSenha();
    }
}